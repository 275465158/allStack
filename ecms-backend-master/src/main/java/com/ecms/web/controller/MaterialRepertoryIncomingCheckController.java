package com.ecms.web.controller;

import com.ecms.bean.MaterialRepertoryIncomingCheckInfo;
import com.ecms.bean.UnqualifiedTypeListInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.MaterialRepertoryIncomingCheckEntity;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.FileUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STAY_INBOUND_STATUS;

/**
 * 检测
 * @author Richie
 * @version 2018/10/25 17:19
 */
@RestController
@RequestMapping("materialRepertoryIncomingCheck")
public class MaterialRepertoryIncomingCheckController extends BaseController<MaterialRepertoryIncomingCheckEntity, MaterialRepertoryIncomingCheckInfo> {

    @Value("${env}")
    private String env;

    @Resource
    private MaterialRepertoryIncomingCheckService materialRepertoryIncomingCheckService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private UnqualifiedTypeListService unqualifiedTypeListService;

    @Resource
    private IncomingUnqualifiedService incomingUnqualifiedService;

    @Override
    protected BaseService<MaterialRepertoryIncomingCheckEntity, MaterialRepertoryIncomingCheckInfo> getBaseService() {
        return materialRepertoryIncomingCheckService;
    }

    /**
     * 物料检测保存
     * @param materialRepertoryIncomingCheckInfo
     */
    @RequestMapping("/saveIncomingCheckout")
    public Result saveIncomingCheckout(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        ExchangeUtils.convertBlank2Null(materialRepertoryIncomingCheckInfo);
        Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
        Integer incomingUnqualifiedNumber = incomingUnqualifiedService.getNextNumber();
        materialRepertoryIncomingCheckService.saveIncomingCheckout(materialRepertoryIncomingCheckInfo,incomingCheckNumber,incomingUnqualifiedNumber);
        return ResultUtils.success();
    }

    /**
     * 查询待入库列表
     * @param pageNum
     * @param pageSize
     * @param materialRepertoryIncomingCheckInfo
     * @return
     */
    @RequestMapping("/getStayInboundPage")
    public Result<Pager<MaterialRepertoryIncomingCheckInfo>> getStayInboundPage(int pageNum,int pageSize,MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        materialRepertoryIncomingCheckInfo.setInboundStutas(STAY_INBOUND_STATUS);
        materialRepertoryIncomingCheckInfo.setRepertoryId(null);
        ExchangeUtils.convertBlank2Null(materialRepertoryIncomingCheckInfo);
        Pager<MaterialRepertoryIncomingCheckInfo> pager = materialRepertoryIncomingCheckService.getModelListByLike(pageNum,pageSize,materialRepertoryIncomingCheckInfo,true);

        List<MaterialRepertoryIncomingCheckInfo> materialRepertoryIncomingCheckInfoList = pager.getList();
        if(materialRepertoryIncomingCheckInfoList.size() != 0){
            for(MaterialRepertoryIncomingCheckInfo mrici : materialRepertoryIncomingCheckInfoList){
                EmployeeEntity ee = employeeService.getEntity(mrici.getOperatingPersonnelId());
                if(ee != null){
                    mrici.setCheckPersonName(ee.getName());
                }
                Integer materialId = mrici.getMaterialId();
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mrici.setOriginalMaterial(mbe.getOriginalMaterial());
                Integer supplierId = mrici.getSupplierId();
                if(supplierId != null && !"".equals(supplierId)){
                    SupplierEntity se = supplierService.getEntity(supplierId);
                    if(se != null){
                        mrici.setSupplierName(se.getSupplierName());
                    }
                }
                mrici.setEmployeeId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrici.setOriginalMaterial(mbe.getOriginalMaterial());
            }
        }
        return ResultUtils.success(pager);
    }

    /**
     * 来料检测记录
     * @param pageNum
     * @param pageSize
     * @param materialRepertoryIncomingCheckInfo
     * @return
     */
    @RequestMapping("/getCheckLogPager")
    public Result<Pager<MaterialRepertoryIncomingCheckInfo>> getCheckLogPager(int pageNum,int pageSize,MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        materialRepertoryIncomingCheckInfo.setRepertoryId(null);
        ExchangeUtils.convertBlank2Null(materialRepertoryIncomingCheckInfo);
        Pager<MaterialRepertoryIncomingCheckInfo> pager = materialRepertoryIncomingCheckService.getModelListByLike(pageNum,pageSize,materialRepertoryIncomingCheckInfo,true);

        List<MaterialRepertoryIncomingCheckInfo> materialRepertoryIncomingCheckInfoList = pager.getList();
        if(materialRepertoryIncomingCheckInfoList.size() != 0){
            for(MaterialRepertoryIncomingCheckInfo mrici : materialRepertoryIncomingCheckInfoList){
                EmployeeEntity ee = employeeService.getEntity(mrici.getOperatingPersonnelId());
                if(ee != null){
                    mrici.setCheckPersonName(ee.getName());
                }
                Integer materialId = mrici.getMaterialId();
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mrici.setOriginalMaterial(mbe.getOriginalMaterial());
                Integer supplierId = mrici.getSupplierId();
                if(supplierId != null && !"".equals(supplierId)){
                    SupplierEntity se = supplierService.getEntity(supplierId);
                    if(se != null){
                        mrici.setSupplierName(se.getSupplierName());
                    }
                }
                mrici.setEmployeeId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrici.setOriginalMaterial(mbe.getOriginalMaterial());
            }
        }
        return ResultUtils.success(pager);
    }

    /****
     * 来料检测记录明细
     * @param id
     * @return
     */
    @RequestMapping("/searchById")
    public Result<MaterialRepertoryIncomingCheckInfo> searchById(Integer id){
        MaterialRepertoryIncomingCheckInfo mrici = materialRepertoryIncomingCheckService.getModel(id);
        Integer supplierId = mrici.getSupplierId();
        if (supplierId!=null){
            mrici.setSupplierName(supplierService.getModel(supplierId).getSupplierName());
        }
        Integer materialId = mrici.getMaterialId();
        if (materialId != null){
            mrici.setOriginalMaterial(materialBomService.getModel(materialId).getOriginalMaterial());
            mrici.setParamValue(materialBomService.getParamValue(materialId));
        }
        UnqualifiedTypeListInfo utli = new UnqualifiedTypeListInfo();
        utli.setCheckId(id);
        //来源：来料
        utli.setCheckSource(0);
        List<UnqualifiedTypeListInfo> utliList = unqualifiedTypeListService.getListBy(utli);
        mrici.setUtliList(utliList);
        return ResultUtils.success(mrici);
    }


    /**
     * 不合格评审-让步使用
     * @param materialRepertoryIncomingCheckInfo
     * @return
     */
    @RequestMapping("/concessionUse")
    public Result<Void> concessionUse(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        materialRepertoryIncomingCheckService.concessionUse(materialRepertoryIncomingCheckInfo);
        return ResultUtils.success();
    }

    @RequestMapping("/materialChange")
    public Result<Void> materialChange(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        materialRepertoryIncomingCheckService.materialChange(materialRepertoryIncomingCheckInfo);
        return ResultUtils.success();
    }

    /***
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile")
    public Result<String> uploadFile(MultipartFile file){
        String fileName = "";
        if (file != null) {
            try {
                fileName = FileUtils.uploadFile(file, env, "check-file");
            } catch (Exception e) {
                return ResultUtils.failure("上传附件失败");
            }
        }
        return ResultUtils.success(fileName);
    }
}
