package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.QueryPriceEntity;
import com.ecms.dal.mapper.QueryPriceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryPriceManager extends BaseManager<QueryPriceEntity> {

	@Resource
	private QueryPriceMapper mapper;
	@Override
	protected BasicMapper<QueryPriceEntity> getMapper() {
		return mapper;
	}

	public String getNextId(){
		if(	mapper.getNextFileid()!=null){
			return 	mapper.getNextFileid();
		}else {
			return "00000001";
		}

	}

}
