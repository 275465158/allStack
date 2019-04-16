package com.ecms.service;

import com.ecms.bean.ProductJobGuideInfo;
import com.ecms.dal.entity.ProductJobGuideEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductJobGuideManager;
import com.ecms.utils.FileUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ProductJobGuideService extends BaseService<ProductJobGuideEntity, ProductJobGuideInfo> {

    @Value("${env}")
    private String env;

    @Resource
    private ProductJobGuideManager productJobGuideManager;

    @Override
    protected BaseManager<ProductJobGuideEntity> getManager() {
        return productJobGuideManager;
    }

    @Override
    protected void setCustomProperties(List<ProductJobGuideInfo> list) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (ProductJobGuideInfo info : list) {
            info.setCreatedDate(format.format(info.getCreated()));
        }
    }

    @Override
    public String uploadFileSimple(MultipartFile file) {
        return FileUtils.uploadFile(file, env, "job-guide/");
    }

    public void save(ProductJobGuideInfo info) {
        if (info.getId() == null) {
            info.setOwner(LoginFilter.getCurrentUser().getUserName());
        }
        getEntityAfterSaveOrUpdate(info);
    }

    public void remove(int id) {
        ProductJobGuideEntity entity = new ProductJobGuideEntity();
        entity.setId(id);
        entity.setStatus(STATUS_INVALID);
        getManager().updateByPrimaryKeySelective(entity);
    }

    @Override
    public InputStream getFileInputStream(String filePath) {
        return FileUtils.getInputStream(env, filePath);
    }

}