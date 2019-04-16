package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.DeliveryDetailInfo;
import com.ecms.bean.DeliveryInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.DeliveryEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.DeliveryManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

@Service
public class DeliveryService extends BaseService<DeliveryEntity, DeliveryInfo> {

	@Resource
	private DeliveryManager manager;
	@Resource
	private DeliveryDetailService detailService;
	@Override
	protected BaseManager<DeliveryEntity> getManager() {
		return manager;
	}

	/**
	 * @author alan.wu
	 * 重写分页列表
	 */
	public Pager<DeliveryInfo> getModelList(int pageNum, int pageSize, DeliveryInfo info){
		DeliveryEntity entity = ExchangeUtils.exchangeObject(info, DeliveryEntity.class);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<DeliveryEntity> list = getManager().getListBy(pageNum, pageSize, entity);
		List<DeliveryInfo> modelList = ExchangeUtils.exchangeList(list, DeliveryInfo.class);
		modelList.stream().forEach(model->{
			//检索明细项，并嵌入
			DeliveryDetailInfo searchDetail = new DeliveryDetailInfo();
			searchDetail.setDeliveryId(model.getId());
			List<DeliveryDetailInfo> listDetail = detailService.getListBy(searchDetail);
			model.setTotalQty(listDetail.stream().map(DeliveryDetailInfo::getQty).reduce(0.0f,Float::sum));
		});
		Pager<DeliveryInfo> pager = new Pager<DeliveryInfo>(pageNum, pageSize, modelList.size(), modelList);
		return pager;
	}
}
