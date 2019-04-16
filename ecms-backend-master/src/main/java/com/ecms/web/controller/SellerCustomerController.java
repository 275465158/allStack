package com.ecms.web.controller;

import com.ecms.bean.SellerCustomerAdditional;
import com.ecms.bean.SellerCustomerIndex;
import com.ecms.bean.SellerCustomerInfo;
import com.ecms.bean.SellerCustomerTable;
import com.ecms.common.BusinessConstants;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerCustomerEntity;
import com.ecms.excel.BaseTemplate;
import com.ecms.excel.SellerCustomerTemplate;
import com.ecms.service.*;
import com.ecms.utils.*;
import org.apache.poi.util.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("seller/customer")
public class SellerCustomerController extends BaseController<SellerCustomerEntity, SellerCustomerInfo> {

    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private SellerPayTypeService sellerPayTypeService;
    @Resource
    private SellerPayRuleService sellerPayRuleService;
    @Resource
    private SellerInvoiceService sellerInvoiceService;
    @Resource
    private SellerDeliveryService sellerDeliveryService;

    @Override
    protected BaseService<SellerCustomerEntity, SellerCustomerInfo> getBaseService() {
        return sellerCustomerService;
    }

    @RequestMapping("table")
    public Result<SellerCustomerTable> table(int pageNum, SellerCustomerInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<SellerCustomerInfo> pager = sellerCustomerService.getModelList(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE, info);
        List<String> provinces = sellerCustomerService.getProvinces();
        SellerCustomerTable table = new SellerCustomerTable();
        table.setPager(pager);
        table.setProvinces(provinces);
        return ResultUtils.success(table);
    }

    @RequestMapping("index")
    public Result<SellerCustomerIndex> index(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        SellerCustomerIndex index = sellerCustomerService.getIndexById(id);
        index.setDeliveryInfos(sellerDeliveryService.getValidList());
        index.setInvoiceInfos(sellerInvoiceService.getValidList());
        index.setPayRuleInfos(sellerPayRuleService.getValidList());
        index.setPayTypeInfos(sellerPayTypeService.getValidList());
        return ResultUtils.success(index);
    }

    @RequestMapping("additional")
    public Result<SellerCustomerAdditional> getAdditionalById(int id) {
        return ResultUtils.success(sellerCustomerService.getAdditionalById(id));
    }

    @RequestMapping("save")
    public Result<Void> save(@RequestBody SellerCustomerIndex index) {
        index.getCustomerInfo().setSellCustomerNumber(sellerCustomerService.getNextNumber());
        sellerCustomerService.save(index);
        return ResultUtils.success();
    }

    @RequestMapping("export/search")
    public void exportList(SellerCustomerInfo info, HttpServletRequest request, HttpServletResponse response) {
        ExchangeUtils.convertBlank2Null(info);
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = File.createTempFile("客户列表", ".xlsx");
            List<? extends BaseTemplate> list = sellerCustomerService.getExportList(info);
            ExcelExportUtil.exportToFile(file.getPath(), list);
            fis = new FileInputStream(file);
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected List<SellerCustomerTemplate> getTemplateForDownload() {
        List<SellerCustomerTemplate> list = new ArrayList<>();
        list.add(new SellerCustomerTemplate());
        return list;
    }

    @Override
    protected void uploadData(MultipartFile file) throws IOException {
        List<Object> list = ExcelImportUtil.importExcel(file.getInputStream(), SellerCustomerTemplate.class);
        sellerCustomerService.upload(list);
    }

    @RequestMapping("export/template")
    @Override
    public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            InputStream is = FileUtils.getInputStream("ecms-model", "客户模板.xlsx");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
