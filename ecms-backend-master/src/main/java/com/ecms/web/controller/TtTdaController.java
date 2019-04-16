package com.ecms.web.controller;

import com.ecms.bean.TtTdaInfo;
import com.ecms.dal.entity.TtTdaEntity;
import com.ecms.service.TtTdaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ttTda")
public class TtTdaController extends BaseController<TtTdaEntity, TtTdaInfo> {
    @Resource
    private TtTdaService ttTdaService;

    @Override
    protected com.ecms.service.BaseService<TtTdaEntity, TtTdaInfo> getBaseService() {
        return ttTdaService;
    }
}