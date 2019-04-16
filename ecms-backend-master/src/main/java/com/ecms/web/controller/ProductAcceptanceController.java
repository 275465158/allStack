package com.ecms.web.controller;

import com.ecms.bean.MaterialCheckInfo;
import com.ecms.bean.ProductAcceptanceDetailInfo;
import com.ecms.bean.ProductAcceptanceInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import com.ecms.service.MaterialCheckService;
import com.ecms.service.ProductAcceptanceDetailService;
import com.ecms.service.ProductAcceptanceService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.FileUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONObject;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("productAcceptance")
public class ProductAcceptanceController extends BaseController<ProductAcceptanceEntity, ProductAcceptanceInfo> {
	@Value("${env}")
	private String env;
	@Resource
    private ProductAcceptanceService productAcceptanceService;

    @Resource
	private ProductAcceptanceDetailService productAcceptanceDetailService;

	@Resource
	private MaterialCheckService materialCheckService;

    @Override
    protected com.ecms.service.BaseService<ProductAcceptanceEntity, ProductAcceptanceInfo> getBaseService() {
        return productAcceptanceService;
    }
    /* (non-Javadoc)
     * 重写list方法，调用了日期格式化函数
     * @see com.ecms.web.controller.BaseController#list(int, com.ecms.bean.BaseInfo)
     */
    @RequestMapping("list")
	public Result<Pager<ProductAcceptanceInfo>> list(int pageNum, ProductAcceptanceInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		//设置时间格式
		info.getCreated();
		Pager<ProductAcceptanceInfo> pager = getBaseService().getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		return ResultUtils.success(pager);
	}

    /**插入/修改
	 * @param 
	 * @return
	 */
	@RequestMapping("insertaAccpetance")
	public Result<Void> insertaAccpetance(ProductAcceptanceInfo info
			){
		ProductAcceptanceInfo tempinfo=new ProductAcceptanceInfo();
		tempinfo.setName(info.getName());
		tempinfo.setStatus(STATUS_DEFAULT);
		List<ProductAcceptanceInfo> ProductAcceptanceInfoList = productAcceptanceService.getListBy(tempinfo);
		if (ProductAcceptanceInfoList.size() != 0) {
			return ResultUtils.failure("验收名称不可重复!");
		}else {
			productAcceptanceService.save(info);
			return ResultUtils.success();
		}
	}
	@RequestMapping("uploadSimple")
	public Result<String> uploadSimple(MultipartFile file) {
		String result = "";
		if (file != null) {
			try {
				result = FileUtils.uploadFile(file, env, "inspectionDiagram");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return ResultUtils.failure("上传文件失败:" + e.getMessage());
			}
		}
		return ResultUtils.success(result);
	}
	@RequestMapping("download")
	public void download(String filePath, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		response.setCharacterEncoding(request.getCharacterEncoding());
		if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg") || filePath.endsWith(".jpe")) {
			response.setContentType("image/jpeg");
		} else if (filePath.endsWith(".png")) {
			response.setContentType("image/png");
		} else {
			response.setContentType("application/octet-stream");
		}
		InputStream is = null;
		try {
			is = FileUtils.getInputStream(env, filePath);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	  /**修改
		 * @param 
		 * @return
		 */
		@RequestMapping("updataAccpetance")
		public Result<Void> updataAccpetance(ProductAcceptanceInfo info
				){/*1.如果名字被修改了，则需要判断修改的名字是否重复
				2.如果名字没被修改，直接调用update方法
				*/
			if (info.getIseditname().equals("1")) {
				//修改了
				ProductAcceptanceInfo tempinfo=new ProductAcceptanceInfo();
				tempinfo.setName(info.getName());
				List<ProductAcceptanceInfo>  ProductAcceptanceInfoList=productAcceptanceService.getListBy(tempinfo);
				for(int i=0;i<ProductAcceptanceInfoList.size();i++) {
					if(ProductAcceptanceInfoList.get(i).getStatus()!=1) {
						ProductAcceptanceInfoList.remove(i);
					}
				}
				if (ProductAcceptanceInfoList.size()!=0) {
					return ResultUtils.failure("验收名称不可重复!");
				}else {
					productAcceptanceService.save(info);
					return ResultUtils.success();
				}
			}else {
				//未修改
				productAcceptanceService.save(info);
				return ResultUtils.success();
			}
//			productAcceptanceService.updata(info);
//			return ResultUtils.success();
			
			
			
		}

		/**逻辑删除
		 * @return
		 */
		@RequestMapping("deleteAccpetance")
		public Result<Void> deleteAccpetance(int id){
			ProductAcceptanceInfo info=new ProductAcceptanceInfo();
			info.setStatus(0);
			info.setId(id);
			productAcceptanceService.saveOrUpdate(info);
			return ResultUtils.success();
		}
		/**模糊查询
		 * @return
		 */
		@RequestMapping("selectAccpetance")
		public Result<Pager<ProductAcceptanceInfo>> selectAccpetance(ProductAcceptanceInfo info,int pageNum){
			ExchangeUtils.convertBlank2Null(info);
			Pager<ProductAcceptanceInfo> pager=getBaseService().getModelListByLike(pageNum, DEFAULT_PAGE_SIZE, info, false);
			
			return ResultUtils.success(pager);
		}

		@RequestMapping("searchAccpetList")
		public Result<List<ProductAcceptanceInfo>> searchAccpetList(){
			List<ProductAcceptanceInfo> list = productAcceptanceService.getValidList();
			if (list.size()!=0) {
				for (int i = 0; i < list.size(); i++) {
					ProductAcceptanceDetailInfo productAcceptanceDetailInfo = new ProductAcceptanceDetailInfo();
					productAcceptanceDetailInfo.setAcceptanceId(list.get(i).getId());
					list.get(i).setProductAcceptanceDetailInfoList(productAcceptanceDetailService.getListBy(productAcceptanceDetailInfo));
				}
			}
			return ResultUtils.success(list);
		}

		@RequestMapping("searchAccpetDetailByMaterialId")
		public Result<ProductAcceptanceInfo> searchAccpetDetailByMaterialId(Integer materialId){
			MaterialCheckInfo mci = new MaterialCheckInfo();
			mci.setMaterialId(materialId);
			mci.setStatus(STATUS_DEFAULT);
			mci.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			List<MaterialCheckInfo> mciList = materialCheckService.getListBy(mci);
			JSONObject jsonObject = new JSONObject();
			if (mciList.size()!=0){
				Integer checkId = mciList.get(0).getCheckId();
				ProductAcceptanceDetailInfo padi = new ProductAcceptanceDetailInfo();
				padi.setAcceptanceId(checkId);
				padi.setStatus(STATUS_DEFAULT);
				List<ProductAcceptanceDetailInfo> padiList = productAcceptanceDetailService.getListBy(padi);
				if (padiList.size()!=0){
					for (int i=0;i<padiList.size();i++){
						jsonObject.put(padiList.get(i).getCheckName(),"");
					}
				}
			}
			ProductAcceptanceInfo pai = new ProductAcceptanceInfo();
			pai.setContentJson(jsonObject.toString());
			return ResultUtils.success(pai);
		}

}