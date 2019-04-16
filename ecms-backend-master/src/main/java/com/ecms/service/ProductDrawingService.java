package com.ecms.service;

import com.ecms.bean.ProductDrawingInfo;
import com.ecms.dal.entity.ProductDrawingEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductDrawingManager;
import com.ecms.utils.FileUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductDrawingService extends BaseService<ProductDrawingEntity, ProductDrawingInfo> {

    @Value("${env}")
    private String env;

    @Resource
    private ProductDrawingManager productDrawingManager;

    @Override
    protected BaseManager<ProductDrawingEntity> getManager() {
        return productDrawingManager;
    }

    public void save(ProductDrawingInfo info) {
        info.setOwner(LoginFilter.getCurrentUser().getUserName());
        getEntityAfterSaveOrUpdate(info);
    }

    @Override
    public String uploadFileSimple(MultipartFile file) {
        return FileUtils.uploadFile(file, env, "drawing/");
    }

    public void remove(int id) {
        ProductDrawingEntity entity = new ProductDrawingEntity();
        entity.setId(id);
        entity.setStatus(STATUS_INVALID);
        getManager().updateByPrimaryKeySelective(entity);
    }

    @Override
    protected void setCustomProperties(List<ProductDrawingInfo> list) {
    }

    @Override
    public InputStream getFileInputStream(String filePath) {
        return FileUtils.getInputStream(env, filePath);
    }

}