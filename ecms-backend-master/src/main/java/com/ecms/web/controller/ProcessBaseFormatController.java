package com.ecms.web.controller;

import com.ecms.bean.ProcessBaseFormatInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcessBaseFormatEntity;
import com.ecms.service.ProcessBaseFormatService;
import javax.annotation.Resource;

import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("processBaseFormat")
public class ProcessBaseFormatController extends BaseController<ProcessBaseFormatEntity, ProcessBaseFormatInfo> {

    //基础格式
    private static Integer DEDAULT_BASE_FLAG = 1;
    //其他格式
    private static Integer UN_DEDAULT_BASE_FLAG = 0;

    @Resource
    private ProcessBaseFormatService processBaseFormatService;

    @Override
    protected com.ecms.service.BaseService<ProcessBaseFormatEntity, ProcessBaseFormatInfo> getBaseService() {
        return processBaseFormatService;
    }

    /**
     * 查询基础格式
     * @param processBaseFormatInfo
     * @return
     */
    @RequestMapping("/searchBaseFormat")
    public Result<List<ProcessBaseFormatInfo>> searchBaseFormat(ProcessBaseFormatInfo processBaseFormatInfo){
        ExchangeUtils.convertBlank2Null(processBaseFormatInfo);
        processBaseFormatInfo.setDefaultBaseFormat(DEDAULT_BASE_FLAG);
        processBaseFormatInfo.setStatus(1);
        List<ProcessBaseFormatInfo> processBaseFormatInfoList = processBaseFormatService.getListBy(processBaseFormatInfo);
        return ResultUtils.success(processBaseFormatInfoList);
    }

    /**
     * 查询不是基础的格式
     * @param processBaseFormatInfo
     * @return
     */
    @RequestMapping("/searchFormat")
    public Result<List<ProcessBaseFormatInfo>> searchFormat(ProcessBaseFormatInfo processBaseFormatInfo){
        ExchangeUtils.convertBlank2Null(processBaseFormatInfo);
        processBaseFormatInfo.setDefaultBaseFormat(UN_DEDAULT_BASE_FLAG);
        processBaseFormatInfo.setStatus(1);
        List<ProcessBaseFormatInfo> processBaseFormatInfoList = processBaseFormatService.getListBy(processBaseFormatInfo);
        return ResultUtils.success(processBaseFormatInfoList);
    }
}