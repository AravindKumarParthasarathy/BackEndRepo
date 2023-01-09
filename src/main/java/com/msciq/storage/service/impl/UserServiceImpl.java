package com.msciq.storage.service.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.msciq.storage.model.*;
import com.msciq.storage.model.request.LoginDTO;
import com.msciq.storage.model.request.UserDTO;
import com.msciq.storage.model.response.LoginResponse;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.RolePermissionMappingRepository;
import com.msciq.storage.repository.UserRepository;
import com.msciq.storage.repository.UserRoleMappingRepository;
import com.msciq.storage.security.Actions;
import com.msciq.storage.service.EmailService;
import com.msciq.storage.service.RolePermissionMappingService;
import com.msciq.storage.service.UserManagementService;
import com.msciq.storage.service.UserService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.common.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private RolePermissionMappingRepository rolePermissionMappingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private UserManagementService userManagementService;


    public ResponseDTO createUser(User user) {
        try {
            user.setActive(true);
            user.setVerified(true);
            User userCreated = (User) userRepository.save(user);
            return ResponseDTO.builder()
                    .message("user " + user + " created successfully")
                    .isError(false)
                    .build();
        } catch (Exception e) {
        return ResponseDTO.builder()
                .message("Error while creating user - " + e.getMessage())
                .isError(true)
                .build();
        }
    }

    @Override
    public User getUser(Long id) {
        Optional optional =userRepository.findById(id);
        return (User) optional.get();
    }

    public List<User> getListofUsers() {
       return userRepository.findAll();
    }
    public User updateUser(User user) {
        return (User) userRepository.save(user);
    }

    public String removeUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setActive(false);
        userRepository.save(user.get());
        return id +" is successfully deleted";
    }

    public String saveUserInGivenNamespace(User user, Datastore datastore) {
        Key taskKey = datastore.newKeyFactory()
                .setKind("User")
                .newKey(String.valueOf(UUID.randomUUID()));
        Entity newEntity = Entity.newBuilder(taskKey)
                .set("firstName", user.getFirstName())
                .set("lastName", user.getLastName())
                .set("email", user.getEmail())
                .set("phoneNumber", user.getPhoneNumber())
                .set("isActive", false)
                .set("isVerified", false)
                .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("updatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("createdBy", "fpa")
                .set("modifiedBy", "fpa")
                .build();
        datastore.put(newEntity);
        // Company companyCreated = (Company) gcpStorageRepository.save(company);
        return user.getFirstName() + " is successfully created";
    }

    public LoginResponse userSignUp(User user,String token) {
        LoginResponse loginResponse=new LoginResponse();
        List<UserRoleMapping> userRoleMappings = new ArrayList<>();

        try{
            log.info(" user sign-up start");
            log.info(token);
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedString =  new String(decodedBytes);
            JSONObject jsonObject = new JSONObject(decodedString);
            // validate the token
            if(user.getEmail().equals(jsonObject.get("email"))){
                RestTemplate restTemplate = new RestTemplate();
                String url= Constants.FIREBASE_BASE_URL+"signUp?key="+ Constants.FIREBASE_API_KEY;
                log.info(url);
                User response = restTemplate.postForObject(url, user, User.class);
                log.info(" user sign-up response");
                log.info(String.valueOf(response));
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setEmail(user.getEmail());
                loginDTO.setPassword(user.getPassword());
                user.setUserType(Constants.SIGN_UP_USER_DEFAULT_TYPE);
                user.setActive(true);
                user.setVerified(true);
                user.setCreatedDt(LocalDateTime.now(ZoneOffset.UTC));
                user.setLastUpdatedDt(LocalDateTime.now(ZoneOffset.UTC));
                user.setCreatedBy(String.valueOf(jsonObject.get("organizationName")));
                user.setLastUpdatedBy(String.valueOf(jsonObject.get("organizationName")));
                user.setPassword(Base64.getEncoder()
                        .encodeToString(user.getPassword().getBytes()));
                // added user details in Datastore
                User userCreated =  userRepository.save(user);

                Map<String,Map<String, Set<Actions>>> claimsData = new HashMap<>();

                     userRoleMappings.add(UserRoleMapping.builder()
                            .userId(userCreated.getId().toString())
                            .roleName(Constants.SIGN_UP_USER_DEFAULT_TYPE)
                            .build());

                    Map<String, Set<Actions>>  permissionObject = rolePermissionMappingService.userClaimData(Constants.SIGN_UP_USER_DEFAULT_TYPE);
                    claimsData.put(Constants.SIGN_UP_USER_DEFAULT_TYPE,permissionObject);
                // map user and roles in user role mapping table
                 userRoleMappingRepository.saveAll(userRoleMappings);
                 // set claim in firebase
                userManagementService.setTokenClaims(user.getEmail(), claimsData);
                loginResponse =  userLogin(loginDTO);
            }else{
                loginResponse.setError(true);
                loginResponse.setMessage(ErrorMessage.UNAUTHORIZED);
            }
            return loginResponse;
        }catch (Exception e){
            log.error(ErrorMessage.ERROR+e.getMessage());
            if(e.getMessage().contains("WEAK_PASSWORD")){
                loginResponse.setMessage("Password should be at least 6 characters");
            }else if(e.getMessage().contains("EMAIL_EXISTS")){
                loginResponse.setMessage("User email already exists");
            } else if(e.getMessage().contains("MISSING_EMAIL")){
                loginResponse.setMessage("Email is mandatory");
            } else{
                loginResponse.setMessage(ErrorMessage.ERROR+e.getMessage());
            }
            loginResponse.setError(true);
            return loginResponse;
        }
    }

    public String userResetPassword(ResetPassword resetPassword) {
        try{
        log.info(" user reset password start");
        resetPassword.setRequestType("PASSWORD_RESET");
        RestTemplate restTemplate = new RestTemplate();
        String url= Constants.FIREBASE_BASE_URL+"sendOobCode?key="+Constants.FIREBASE_API_KEY;
        log.info(String.valueOf(resetPassword));
        ResetPassword response = restTemplate.postForObject(url, resetPassword, ResetPassword.class);
        log.info(" user sing-up response");
        log.info(String.valueOf(response));
        return SuccessMessage.RESET_PASSWORD_SUCCESS;
        }catch (Exception e){
            return ErrorMessage.RESET_PASSWORD_ERROR+e.getMessage();
        }
    }

    public  LoginResponse userLogin(LoginDTO loginDTO){
        LoginResponse response = new LoginResponse();

        try{
            log.info(" user login start");
            loginDTO.setReturnSecureToken(true);
            RestTemplate restTemplate = new RestTemplate();
            String url= Constants.FIREBASE_BASE_URL+"signInWithPassword?key="+Constants.FIREBASE_API_KEY;
            log.info(String.valueOf(loginDTO));
            response = restTemplate.postForObject(url, loginDTO, LoginResponse.class);
            response.setMessage(SuccessMessage.SUCCESS);
            log.info(" user login end");
            return response;
        }catch (Exception e){
            log.error(ErrorMessage.ERROR+e.getMessage());
            response.setError(true);
            response.setMessage(ErrorMessage.ERROR+e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO inviteUsers(String orgName, List<UserDTO> users) {
        List<UserRoleMapping> userRoleMappings = new ArrayList<>();
        List<User> usersToBeCreated = new ArrayList<>();
        try {
            for (UserDTO user : users) {
                User newUser = new User();
                RestTemplate restTemplate = new RestTemplate();
                user.setPassword("default");
                String url = Constants.FIREBASE_BASE_URL + "signUp?key=" + Constants.FIREBASE_API_KEY;
                User response = restTemplate.postForObject(url, user, User.class);
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setEmail(user.getEmail());
                newUser.setEmail(user.getEmail());
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setActive(true);
                newUser.setVerified(true);
                newUser.setPassword(Base64.getEncoder()
                        .encodeToString(user.getPassword().getBytes()));
                newUser.setCreatedDt(LocalDateTime.now(ZoneOffset.UTC));
                newUser.setLastUpdatedDt(LocalDateTime.now(ZoneOffset.UTC));
                newUser.setCreatedBy(orgName);
                newUser.setLastUpdatedBy(orgName);
                usersToBeCreated.add(newUser);
                User userCreated = userRepository.save(newUser);
                log.info("userid -> " + userCreated.getId() + " -- ", userCreated.getId().toString());
                log.info("roles -> " + user.getRoles().size());
                Map<String,Map<String, Set<Actions>>> claimsData = new HashMap<>();
                for (String role: user.getRoles()) {
                    userRoleMappings.add(UserRoleMapping.builder()
                                    .userId(userCreated.getId().toString())
                                    .roleName(role)
                                    .build());
                    Map<String, Set<Actions>>  permissionObject = rolePermissionMappingService.userClaimData(role);
                    claimsData.put(role,permissionObject);
                }
                userRoleMappingRepository.saveAll(userRoleMappings);
                userManagementService.setTokenClaims(user.getEmail(), claimsData);

            }
            for (User user: usersToBeCreated) {
                sendMailToOrganization(user);
            }
            return ResponseDTO.builder()
                    .message(String.format(SuccessMessage.SUCCESSFULLY_SAVED, "users"))
                    .isError(false)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .build();
        }
    }

    private void sendMailToOrganization(User user) {
        EmailTemplate emailTemplate = EmailTemplate.builder()
                .recipient(user.getEmail())
                .recipientName(user.getFirstName())
                .subject(Constants.WELCOME_TO_MSCIQ)
                .build();
        emailService.sendResetPasswordEmail(emailTemplate);
    }
}
