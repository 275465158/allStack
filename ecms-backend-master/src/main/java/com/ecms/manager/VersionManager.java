package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.VersionEntity;
import com.ecms.dal.mapper.VersionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class VersionManager extends BaseManager<VersionEntity> {

    @Resource
    private VersionMapper versionMapper;

    @Override
    protected BasicMapper<VersionEntity> getMapper() {
        return versionMapper;
    }

    public VersionEntity getLatestVersion(int appType) {
        return versionMapper.selectLatestVersion(appType);
    }
}
