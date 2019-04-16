package com.ecms.common;

import com.ecms.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemConfig {

    @Value("${ossEndpoint}")
    private String ossEndpoint;

    @PostConstruct
    public void init() {
        FileUtils.setEndpoint(ossEndpoint);
    }

}
