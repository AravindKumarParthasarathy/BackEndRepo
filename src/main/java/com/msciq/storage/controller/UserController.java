package com.msciq.storage.controller;

import com.msciq.storage.model.ResetPassword;
import com.msciq.storage.model.User;
import com.msciq.storage.model.request.LoginDTO;
import com.msciq.storage.model.request.UserDTO;
import com.msciq.storage.model.response.LoginResponse;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.UserService;
import com.msciq.storage.service.impl.GCPStorageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fpa")
@Slf4j
public class UserController {

    @Autowired
    GCPStorageServiceImpl gcpStorageService;

    @Autowired
    UserService userService;

    /**
     * This method is used to create a User in the respective Company
     *
     * @param user - model
     * @return Successful message on creating the new User
     */
    @PostMapping("/create/user")
    public ResponseDTO createUser(
            @RequestBody User user
    ) {
        try {
            return userService.createUser(user);

        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message("Error while creating group of company - " + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }

    /**
     * This method is used to retrieve a User from the given company
     *
     * @param id - id of the User
     * @return User details will be returned
     */
    @GetMapping("/get/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * This method is used to retrieve a User from the given company
     *
     * @return List of Users will be returned
     */
    @GetMapping("/get/user")
    public List<User> getUser() {
        return userService.getListofUsers();
    }

    /**
     * This method is used to update the given User
     *
     * @param user - model
     * @return updated user details
     * @Param companyName - name of the Company
     */
    @PutMapping("/user/update")
    public User updateUser(@PathVariable String companyName,
                           @RequestBody User user
    ) {
        return userService.updateUser(user);
    }

    /**
     * This method is used to remove the given user
     *
     * @param id - id of the user
     * @return
     */
    @PutMapping("/user/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        return userService.removeUser(id);
    }

    /**
     * This method is used to create a User in the firebase authentication
     *
     * @param user - model
     * @return LoginResponse - model
     */
    @PostMapping("/user/sign-up")
    public LoginResponse userSignUp(
            @Valid @RequestBody User user,
            @RequestParam String token
    ) {
        return userService.userSignUp(user,token);
    }

    /**
     * This method is used to invite new User to teh system
     *
     * @param orgName
     * @param users
     * @return ResponseDTO
     */
    @PostMapping("/{orgName}/invite-users")
    public ResponseDTO userSignUp(
            @PathVariable String orgName,
            @RequestBody @Valid List<UserDTO> users
    ) {
        return userService.inviteUsers(orgName, users);
    }

    /**
     * This method is used to reset the password in firebase authentication platform
     *
     * @return Successful or Error message will be shown
     */

    @PostMapping("/user/reset-password")
    public String userResetPassword(@RequestBody ResetPassword resetPassword) {
        return userService.userResetPassword(resetPassword);
    }

    /**
     * This method is used to login with user name and password
     *
     * @param loginDTO - model
     * @return LoginResponse - model
     */
    @PostMapping("/user/login")
    public LoginResponse userLogin(@RequestBody LoginDTO loginDTO) {
        return userService.userLogin(loginDTO);
    }


}
