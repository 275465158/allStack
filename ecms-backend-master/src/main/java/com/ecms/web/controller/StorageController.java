package com.ecms.web.controller;

import com.ecms.bean.StorageInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.StorageEntity;
import com.ecms.service.BaseService;
import com.ecms.service.StorageService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("storage")
public class StorageController extends BaseController<StorageEntity, StorageInfo> {
	@Resource
	private StorageService storageService;
	@Override
	protected BaseService<StorageEntity, StorageInfo> getBaseService() {
		return storageService;
	}
	/**按货架号查找（模糊）
	 * @param storageNum
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("selectByStorageId")
	public Result<Pager<StorageInfo>> list(Integer storageNum,Integer pageNum){ 
		StorageInfo info=new StorageInfo();
		info.setStorageNum(storageNum+"");
		ExchangeUtils.convertBlank2Null(info);
		Pager<StorageInfo> pager = getBaseService().getModelListByLike(pageNum, DEFAULT_PAGE_SIZE, info, false);
		return ResultUtils.success(pager);
	}
	
	/**插入/修改
	 * @param storageInfo
	 * @return
	 */
	@RequestMapping("insertbatch")
	public Result<Void> insertStorageRack(StorageInfo storageInfo){
		StorageInfo tempinfo=new StorageInfo();
		tempinfo.setRepertoryId(storageInfo.getRepertoryId());
		tempinfo.setStorageNum(storageInfo.getStorageNum());
		List<StorageInfo> StorageRackIdList=storageService.getListBy(tempinfo);
		if (!StorageRackIdList.isEmpty()) {
			return ResultUtils.failure("该仓库已有该货架号");
		}else {
			Integer storageLayer;
			Integer storageRow;
			Integer storageColumn;
			if (storageInfo.getStorageLayer() != null){
				storageLayer = Integer.parseInt(storageInfo.getStorageLayer());
			}else {
				storageLayer = 1;
			}
			if (storageInfo.getStorageRow() != null){
				storageRow = Integer.parseInt(storageInfo.getStorageRow());
			}else {
				storageRow = 1;
			}
			if (storageInfo.getStorageColumn() != null){
				storageColumn = Integer.parseInt(storageInfo.getStorageColumn());
			}else {
				storageColumn = 1;
			}
			for (int i=1;i<=storageLayer;i++){
				for (int j=1;j<=storageRow;j++){
					for (int k=1;k<=storageColumn;k++){
						StorageInfo si = new StorageInfo();
						si.setStorageLayer(""+i);
						si.setStorageRow(""+j);
						si.setStorageColumn(""+k);
						si.setStorageNum(storageInfo.getStorageNum());
						si.setRepertoryId(storageInfo.getRepertoryId());
						si.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
						storageService.saveOrUpdate(si);
					}
				}
			}
		}
		return ResultUtils.success();
		
	}

	@RequestMapping("lastStorage")
	public Result<StorageInfo> getLast(@RequestParam(value="repertoryId",required=false)int repertoryId){
		StorageInfo storage = new StorageInfo();
		if(repertoryId>0) {
			storage = storageService.getLastColumn(repertoryId);
		}else {
			storage.setStorageColumn("1");
			storage.setStorageRow("1");
			storage.setStorageLayer("1");
			storage.setStorageNum("1");
		}
		return ResultUtils.success(storage);
	}

	@RequestMapping("getJson")
	public Result<String> getJson(@RequestParam(value="repertoryId", required=false) int repertoryId){
		return ResultUtils.success(storageService.getJson(repertoryId));
	}
	
	@RequestMapping("info")
	public Result<StorageInfo> info(@RequestParam(value = "id", required = false) int id) {
		StorageInfo storage = new StorageInfo();
		if (id > 0) {
			storage = storageService.getModel(id);
		}
		return ResultUtils.success(storage);
	}

	@RequestMapping("getStorageNumList")
	public Result<List<Map<String,Object>>> getStorageNumList(StorageInfo storageInfo){
		ExchangeUtils.convertBlank2Null(storageInfo);
		List<Map<String,Object>> storageNumList = storageService.getStorageNumList(storageInfo.getRepertoryId());
		return ResultUtils.success(storageNumList);
	}

	@RequestMapping("getStorageRowList")
	public Result<List<Map<String,Object>>> getStorageRowList(StorageInfo storageInfo){
		ExchangeUtils.convertBlank2Null(storageInfo);
		List<Map<String,Object>> storageRowList = storageService.getStorageRowList(storageInfo.getRepertoryId(),storageInfo.getStorageNum());
		return ResultUtils.success(storageRowList);
	}


	@RequestMapping("getStorageLayerList")
	public Result<List<Map<String,Object>>> getStorageLayerList(StorageInfo storageInfo){
		ExchangeUtils.convertBlank2Null(storageInfo);
		List<Map<String,Object>> storageLayerList = storageService.getStorageLayerList(storageInfo.getRepertoryId(),storageInfo.getStorageNum(),storageInfo.getStorageRow());
		return ResultUtils.success(storageLayerList);
	}

	@RequestMapping("getStorageColumnList")
	public Result<List<Map<String,Object>>> getStorageColumnList(StorageInfo storageInfo){
		ExchangeUtils.convertBlank2Null(storageInfo);
		List<Map<String,Object>> storageColumnList = storageService.getStorageColumnList(storageInfo.getRepertoryId(),storageInfo.getStorageNum(),storageInfo.getStorageRow(),storageInfo.getStorageLayer());
		return ResultUtils.success(storageColumnList);
	}

	@RequestMapping("getStorageList")
	public Result<List<StorageInfo>> getStorageList(StorageInfo storageInfo){
		ExchangeUtils.convertBlank2Null(storageInfo);
		List<Map<String,Object>> mapList =  storageService.getStorageNumList(storageInfo.getRepertoryId());
		List<StorageInfo> list = new ArrayList<>();
		if (mapList.size()!=0){
			for (int i=0;i<mapList.size();i++){
				Map<String,Object> map = storageService.getMaxStorage(mapList.get(i).get("storageNum").toString(),storageInfo.getRepertoryId());
				StorageInfo si = new StorageInfo();
				if (storageInfo.getStorageNum()!=null){
					if (storageInfo.getStorageNum().equals(map.get("storageNum"))){
						si.setStorageNum(mapList.get(i).get("storageNum").toString());
						si.setStorageLayer(map.get("storageLayer").toString());
						si.setStorageRow(map.get("storageRow").toString());
						si.setStorageColumn(map.get("storageColumn").toString());
						list.add(si);
						return ResultUtils.success(list);
					}
				}else{
					si.setStorageNum(mapList.get(i).get("storageNum").toString());
					si.setStorageLayer(map.get("storageLayer").toString());
					si.setStorageRow(map.get("storageRow").toString());
					si.setStorageColumn(map.get("storageColumn").toString());
					list.add(si);
				}
			}
		}
		return ResultUtils.success(list);
	}

	@RequestMapping("updatebatch")
	public Result<Void> updatebatch(StorageInfo storageInfo){
		StorageInfo si = new StorageInfo();
		si.setStorageNum(storageInfo.getStorageNum());
		si.setRepertoryId(storageInfo.getRepertoryId());
		//先查询出当前仓库下的这个货架号的数据
		List<StorageInfo> list = storageService.getListBy(si);
		if (list.size()!=0){
			for (int i=0;i<list.size();i++){
				//然后删除数据
				storageService.deleteBy(list.get(i).getId());
			}
		}
		//最后新增数据
		Integer storageLayer;
		Integer storageRow;
		Integer storageColumn;
		if (storageInfo.getStorageLayer() != null){
			storageLayer = Integer.parseInt(storageInfo.getStorageLayer());
		}else {
			storageLayer = 1;
		}
		if (storageInfo.getStorageRow() != null){
			storageRow = Integer.parseInt(storageInfo.getStorageRow());
		}else {
			storageRow = 1;
		}
		if (storageInfo.getStorageColumn() != null){
			storageColumn = Integer.parseInt(storageInfo.getStorageColumn());
		}else {
			storageColumn = 1;
		}
		for (int i=1;i<=storageLayer;i++){
			for (int j=1;j<=storageRow;j++){
				for (int k=1;k<=storageColumn;k++){
					StorageInfo si1 = new StorageInfo();
					si1.setStorageLayer(""+i);
					si1.setStorageRow(""+j);
					si1.setStorageColumn(""+k);
					si1.setStorageNum(storageInfo.getStorageNum());
					si1.setRepertoryId(storageInfo.getRepertoryId());
					si1.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					storageService.saveOrUpdate(si1);
				}
			}
		}

		return ResultUtils.success();
	}

	@RequestMapping("deleteStorage")
	public Result<Void> deleteStorage(StorageInfo storageInfo){
		StorageInfo si = new StorageInfo();
		si.setRepertoryId(storageInfo.getRepertoryId());
		si.setStorageNum(storageInfo.getStorageNum());
		//先查询出当前仓库下的这个货架号的数据
		List<StorageInfo> list = storageService.getListBy(si);
		if (list.size()!=0){
			for (int i=0;i<list.size();i++){
				//然后删除数据
				storageService.deleteBy(list.get(i).getId());
			}
		}
		return ResultUtils.success();
	}
}
