package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Location;
import com.msciq.storage.model.response.ResponseDTO;

import java.util.List;

public interface LocationService {

    /**
     * This method will be used to create a group company in the given tenant namespace
     *
     * @param location   - location entity which should be created in the given tenant namespace
     * @param datastore - datastore object of the tenant namespace where the location entity is created
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    public ResponseDTO saveLocationInGivenNamespace(Location location, Datastore datastore);

    /**
     * This method will be used to get the list of group company entities by tenant name
     *
     * @param tenantName - tenant name from where the list of group companies should be fetched
     * @param name - name of the location
     *
     * @return Location
     *      location entity from the given tenant namespace
     */
    Location getLocation(String tenantName, String name);

    /**
     * This method will be used to get the list of location entities by tenant name
     *
     * @param tenantName - tenant name from where the list of locations should be fetched
     *
     * @return List<Location>
     *      list of location entities from the given tenant namespace
     */
    List<Location> getListofLocations(String tenantName);

}
