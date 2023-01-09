package com.msciq.storage.controller;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Location;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.LocationService;
import com.msciq.storage.service.impl.GCPStorageServiceImpl;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.validator.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fpa")
public class LocationController {

    @Autowired
    GCPStorageServiceImpl gcpStorageService;

    @Autowired
    LocationService locationService;

    @Autowired
    LocationValidator locationValidator;
    Datastore datastore;

    /**
     * This method is used to create the location
     *
     * @param location - model
     * @return successful message on creating the location
     */
    @PostMapping("/{organizationName}/location/create")
    public ResponseDTO createLocation(@PathVariable String organizationName,
                                        @RequestBody Location location
    ) {
        try{
            validateLocationRequest(location);
            if(locationService.getLocation(organizationName, location.getName())==null){
                datastore = gcpStorageService.createOrGetNamespace(Constants.ORGANIZATION_PREFIX + organizationName.toLowerCase().replaceAll("\\s", "_"));
                return locationService.saveLocationInGivenNamespace(location, datastore);
            }else{
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.LOCATION_ALREADY_EXISTS, location.getName()))
                        .isError(true)
                        .build();
            }
        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.LOCATION_ERROR + exception.getMessage())
                    .isError(true)
                    .build();
        }

    }
    /**
     * This method is used to retrieve a Location from the given company
     *
     * @param name - name of the Location
     * @Param organizationName - name of the Tenant
     * @return Location details will be returned*/
    @GetMapping("/{organizationName}/get/location/{name}")
    public Location getLocation(@PathVariable String organizationName,
                        @PathVariable String name
    ) {
        return locationService.getLocation(organizationName, name);
    }

    /**
     * This method is used to retrieve the list of Locations from the given organization
     *
     * @Param organizationName - name of the Tenant
     * @return List of Locations will be returned*/

    @GetMapping("/{organizationName}/get/locations")
    public List<Location> getLocation(@PathVariable String organizationName
    ) {
        return locationService.getListofLocations(organizationName);
    }

    private void validateLocationRequest(Location location) {
        locationValidator.checkIfLocationCodeIsValid(location.getCode());
        locationValidator.checkIfLocationNameIsValid(location.getName());
        locationValidator.checkIfCompanyCodeIsValid(location.getCompanyCode());
    }
}
