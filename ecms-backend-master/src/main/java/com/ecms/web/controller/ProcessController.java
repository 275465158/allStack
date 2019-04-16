package com.ecms.web.controller;

import com.ecms.bean.ProcessBaseFormatInfo;
import com.ecms.bean.ProcessFormatInfo;
import com.ecms.bean.ProcessInfo;
import com.ecms.bean.ProcessToolTypeInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcessEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static com.ecms.common.BusinessConstants.DEFAULT_PROCESS;

/**
 * @author zhengli
 * 2018/9/3
 */
@RestController
@RequestMapping("process")
public class ProcessController extends BaseController<ProcessEntity,ProcessInfo> {

	@Resource
	private ProcessService processService;

	@Resource
	private ProcessToolTypeService processToolTypeService;

	@Resource
	private ProcessFormatService processFormatService;

	@Resource
	private ProcessBaseFormatService processBaseFormatService;

	@Override
	protected BaseService<ProcessEntity,ProcessInfo> getBaseService() {
		return processService;
	}

	/**
	 * 查询所有工艺
	 * @param pageNum
	 * @param processInfo
	 */
	@RequestMapping("/searchProcessPager")
	public Result<Pager<ProcessInfo>> searchProcessPager(int pageNum,int pageSize, ProcessInfo processInfo ){
		ExchangeUtils.convertBlank2Null(processInfo);
		Pager<ProcessInfo> pager = processService.getModelListByLike(pageNum,pageSize,processInfo,true);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/searchList")
	public Result<List<ProcessInfo>> searchList(){
		List<ProcessInfo> list = processService.getValidList();
		if (list.size()!=0){
			for (ProcessInfo processInfo : list){
				ProcessFormatInfo processFormatInfo = new ProcessFormatInfo();
				processFormatInfo.setProcessId(processInfo.getId());
				List<ProcessFormatInfo> processFormatInfoList = processFormatService.getListBy(processFormatInfo);
				if (processFormatInfoList.size()!=0) {
					processInfo.setProcessFormatInfoList(processFormatInfoList);
				}
				JSONObject jsonObject = new JSONObject();
				for (ProcessFormatInfo info : processFormatInfoList) {
					jsonObject.put(info.getProcessFormatName(), "");
				}
				processInfo.setContentJson(jsonObject.toString());
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 停用/启用工艺
	 * @param id
	 */
	@RequestMapping("/updateStatus")
	public Result<Void> updateStatus(Integer id){
		processService.updateStatus(id);
		return ResultUtils.success();
	}

	/**
	 * 设置默认工艺
	 * @param id
	 */
	@RequestMapping("/updateDefaultProcess")
	public Result<Void> updateDefaultProcess(Integer id){
		//先把所有工艺都设置成没有默认
		ProcessInfo processInfo = new ProcessInfo();
		processService.updateDefaultProcess();
		//然后更新要默认的工艺中的默认表示
		processInfo.setId(id);
		processInfo.setDefaultProcess(DEFAULT_PROCESS);
		processService.saveOrUpdate(processInfo);
		return ResultUtils.success();
	}

	/**
	 * 根据id查询一条记录
	 * @param id
	 */
	@RequestMapping("/searchOne")
	public Result<ProcessInfo> searchOne(Integer id){
		ProcessInfo processInfo = processService.getModel(id);
		return ResultUtils.success(processInfo);
	}

	/**
	 * 新增工艺
	 * @param processInfo
	 * @return
	 */
	@RequestMapping("/saveProcess")
	public Result<Void> saveProcess(ProcessInfo processInfo){
		ExchangeUtils.convertBlank2Null(processInfo);
		//首先往工艺表插入数据，并且返回工艺编号
		Integer nextNumber = processService.getNextNumber();
		String processCode = "P"+nextNumber;
		processInfo.setProcessCode(processCode);
		ProcessEntity processEntity = processService.insertBy(processInfo);
		if(processEntity!=null){
			//查询基础格式
			ProcessBaseFormatInfo processBaseFormatInfo = new ProcessBaseFormatInfo();
			//DefaultBaseFormat字段为1的时候表示该格式为基础格式
			processBaseFormatInfo.setDefaultBaseFormat(1);
			List<ProcessBaseFormatInfo> processBaseFormatInfoList = processBaseFormatService.getListBy(processBaseFormatInfo);
			//把查询出来的基础格式循环添加到工艺格式表中
			for (ProcessBaseFormatInfo pbfi : processBaseFormatInfoList){
				ProcessFormatInfo processFormatInfo = new ProcessFormatInfo();
				processFormatInfo.setProcessId(processEntity.getId());
				processFormatInfo.setProcessFormatName(pbfi.getProcessFormatName());
				processFormatService.saveOrUpdate(processFormatInfo);
			}
			//获取前端传入的格式列表
			List<String> processFormatInfoList = processInfo.getProcessOtherFormatList();
			if (processFormatInfoList!=null){
				for(int j=0;j<processFormatInfoList.size();j++){
					//把新增的工艺的工艺编号赋值给工艺格式表的ProcessId字段，然后循环添加到工艺格式表中
					ProcessFormatInfo processFormatInfo = new ProcessFormatInfo();
					processFormatInfo.setProcessId(processEntity.getId());
					processFormatInfo.setProcessFormatName(processFormatInfoList.get(j));
					processFormatService.saveOrUpdate(processFormatInfo);
				}
			}
			//获取前端传入的工具类型列表
			if (processInfo.getToolTypeSelectList() != null && processInfo.getToolTypeSelectList().size()!=0){
				List<Map<String,Object>> processToolTypeInfoList = processInfo.getToolTypeSelectList();
				if (processToolTypeInfoList.size()!=0){
					for (int i=0;i<processToolTypeInfoList.size();i++){
						Map<String,Object> map = processToolTypeInfoList.get(i);
						//把新增的工艺的工艺编号赋值给工艺格式表的ProcessId字段，然后循环添加到工艺工具类型表中
						ProcessToolTypeInfo processToolTypeInfo = new ProcessToolTypeInfo();
						processToolTypeInfo.setProcessId(processEntity.getId());
						processToolTypeInfo.setToolTypeId(Integer.parseInt(map.get("id").toString()));
						processToolTypeInfo.setToolTypeName(map.get("toolTypeName").toString());
						if (map.get("defaultModelNumber").equals("true")){
							processToolTypeInfo.setDefaultModelNumber(1);
						}else{
							processToolTypeInfo.setDefaultModelNumber(0);
						}
						processToolTypeService.saveOrUpdate(processToolTypeInfo);
					}
				}
			}
		}
		return ResultUtils.success();
	}
}
