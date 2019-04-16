/**
 * 
 */
package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterielProduceDetailInfo;
import com.ecms.dal.entity.MaterielProduceDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielProduceDetailManager;
import com.ecms.utils.ExchangeUtils;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
@Service
public class MaterielProduceDetailService extends BaseService<MaterielProduceDetailEntity, MaterielProduceDetailInfo> {

	@Resource
	private MaterielProduceDetailManager manager;
	@Resource
	private MaterialBomService bomService;
	/* (non-Javadoc)
	 * @see com.ecms.service.BaseService#getManager()
	 */
	@Override
	protected BaseManager<MaterielProduceDetailEntity> getManager() {
		return manager;
	}

	@Override
	public List<MaterielProduceDetailInfo> getListBy(MaterielProduceDetailInfo info){
		MaterielProduceDetailEntity entity = ExchangeUtils.exchangeObject(info, eClass);
		List<MaterielProduceDetailEntity> entityList = manager.getListBy(entity);
		List<MaterielProduceDetailInfo> list = ExchangeUtils.exchangeList(entityList, iClass);
		list.stream().forEach(detail->{
			detail.setMaterialBomModal(bomService.getMaterialBomModal(detail.getMaterialId()));
		});
		return list;
	}
}
