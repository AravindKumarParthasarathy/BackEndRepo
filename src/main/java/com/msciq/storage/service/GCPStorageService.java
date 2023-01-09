package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;

public interface GCPStorageService {

    /**
     * This method will create a bucket in google cloud
     *
     * @param bucketName which should be created in google cloud
     */
    void createBucket(String bucketName);

    /**
     * This method will create a namespace in google cloud data store
     *
     * @param namespaceName
     *
     * @return Datastore
     *      If the namespace is not already present,then creates a namespace in datastore and returns the newly created object
     *      if the namespace is already present, it returns the existing datastore object
     */
    Datastore createOrGetNamespace(String namespaceName);

    /**
     * This method will create a folder inside the bucket
     *
     * @param bucketName where the folder should be created
     * @param folderPath which should be created inside the bucket
     */
    void createFolderInsideBucket(String bucketName, String folderPath);
}
