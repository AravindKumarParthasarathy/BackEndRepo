package com.msciq.storage.service.impl;

import com.google.api.gax.paging.Page;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.msciq.storage.service.GCPStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GCPStorageServiceImpl implements GCPStorageService {
    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    public void createBucket(String bucketName) {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        storage.create(BucketInfo.newBuilder(bucketName).build());
    }

    public Datastore createOrGetNamespace(String namespaceName) {
        return DatastoreOptions.newBuilder()
                .setNamespace(namespaceName)
                .setProjectId(projectId)
                .build()
                .getService();
    }

    @Override
    public void createFolderInsideBucket(String bucketName, String folderPath) {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Bucket bucket = storage.get(bucketName);
        Storage.BucketGetOption.fields(Storage.BucketField.METAGENERATION);
        bucket.create(folderPath, "".getBytes());
    }
}
