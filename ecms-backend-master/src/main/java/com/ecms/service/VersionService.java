package com.ecms.service;

import com.ecms.dal.entity.VersionEntity;
import com.ecms.manager.VersionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VersionService {

    @Resource
    private VersionManager versionManager;

    public VersionEntity getLatestVersion(int appType) {
        VersionEntity entity = versionManager.getLatestVersion(appType);
        return entity;
    }
}
