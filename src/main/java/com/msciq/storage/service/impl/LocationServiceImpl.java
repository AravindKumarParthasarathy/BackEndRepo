package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.Location;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.LocationRepository;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.service.LocationService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private GCPStorageService gcpStorageService;
    @Override
    public ResponseDTO saveLocationInGivenNamespace(Location location, Datastore datastore) {
        try{
        Key taskKey = datastore.newKeyFactory()
                .setKind("Location")
                .newKey(location.getName() + UUID.randomUUID());
        Entity newEntity = Entity.newBuilder(taskKey)
                .set("code", location.getCode())
                .set("name", location.getName())
                .set("address", location.getAddress())
                .set("isActive", true)
                .set("inactiveDate", "")
                .set("activeFromDate", location.getActiveFromDate() != null
                        ? String.valueOf(location.getActiveFromDate())
                        : String.valueOf(Constants.DEFAULT_ACTIVE_FROM_DATE))
                .set("createdBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("lastUpdatedBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .build();
            datastore.put(newEntity);
            return ResponseDTO.builder()
                    .message("location " + location.getName() + " created successfully")
                    .isError(false)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error while creating location - " + e.getMessage())
                    .isError(true)
                    .build();
        }
    }

    @Override
    public Location getLocation(String tenantName, String name) {
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Location")
                .setFilter(StructuredQuery.PropertyFilter.eq("name", name))
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            return buildLocationEntity(resultEntity);
        }
        return null;
    }

    @Override
    public List<Location> getListofLocations(String tenantName) {
        List<Location> locationList = new ArrayList<>();
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Location")
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            locationList.add(buildLocationEntity(resultEntity));
        }
        return locationList;
    }

    private static Location buildLocationEntity(Entity resultEntity) {
        return Location.builder()
                .activeFromDate(LocalDateTime.parse(resultEntity.getString("activeFromDate")))
                .code(resultEntity.getString("code"))
                .name(resultEntity.getString("name"))
                .address(resultEntity.getString("address"))
                .createdDt(LocalDateTime.parse(resultEntity.getString("createdDt")))
                .isActive(resultEntity.getBoolean("isActive"))
                .inactiveDate(!Validator.isEmptyString(resultEntity.getString("inactiveDate"))
                        ? LocalDateTime.parse(resultEntity.getString("inactiveDate"))
                        : null)
                .createdBy(resultEntity.getString("createdBy"))
                .lastUpdatedBy(resultEntity.getString("lastUpdatedBy"))
                .lastUpdatedDt(LocalDateTime.parse(resultEntity.getString("lastUpdatedDt")))
                .id(resultEntity.getKey().getName())
                .build();
    }

    /*@Override
    public Location createLocation(Location location, String companyName) {
        Location locationCreated = (Location) locationRepository.save(location);
        return locationCreated;
    }

    @Override
    public Location getLocation(String companyName, String id) {
        Optional optional = locationRepository.findById(Long.valueOf(id));
        return (Location) optional.get();
    }

    @Override
    public Location updateLocation(Location location) {
        return (Location) locationRepository.save(location);
    }

    @Override
    public String removeLocation(String id) {
        locationRepository.deleteById(Long.valueOf(id));
        return id + " is successfully deleted";
    }

    @Override
    public List<Location> getListofLocations(String companyName) {
        return locationRepository.findAll();
    }
    */
}
