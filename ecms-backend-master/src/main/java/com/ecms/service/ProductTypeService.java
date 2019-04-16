package com.ecms.service;

import com.ecms.bean.ProductTypeInfo;
import com.ecms.dal.entity.ProductTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductTypeManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class ProductTypeService extends BaseService<ProductTypeEntity, ProductTypeInfo> {
	@Resource
	private ProductTypeManager productTypeManager;
	@Override
	protected BaseManager<ProductTypeEntity> getManager() {
		// TODO Auto-generated method stub
		return productTypeManager;
	}

	@Override
	protected String getRedisNumberKeyPrefix() {
		return "num_product_";
	}


	/**
	 * @Author:pratice
	 * @date 2019/1/10
	 * @Description:
	 */
	public ProductTypeInfo getProductTypeByNumber(String number,int companyId){
		return productTypeManager.getProductTypeByNumber(number,companyId);
	}

	/**
	 * @Author:pratice
	 * @date 2019/1/3
	 * @Description: 询价系统重写
	 */
//	public Pager<ProductTypeInfo> getModelListForQuery(int pageNum, int pageSize, ProductTypeInfo info) {
//		ProductTypeEntity entity = ExchangeUtils.exchangeObject(info, ProductTypeEntity.class );
//		if (entity instanceof BaseCompanyAndStatusEntity) {
//			((BaseCompanyAndStatusEntity) entity).setCompanyId(LoginFilter.getCurrentUser().getParentId());
//		}
//		List<ProductTypeEntity> list = getManager().getListBy(pageNum, pageSize, entity);
//		long total = getManager().getTotal(entity);
//		List<ProductTypeInfo> modelList = ExchangeUtils.exchangeList(list, ProductTypeInfo.class );
//		setCustomProperties(modelList);
//		Pager<ProductTypeInfo> pager = new Pager<ProductTypeInfo>(pageNum, pageSize, total, modelList);
//		return pager;
//	}
//	/**
//	 * @Author:pratice
//	 * @date 2019/1/3
//	 * @Description: 询价系统重写
//	 */
//	public Pager<ProductTypeInfo> getModelListByLikeForQuery(int pageNum, int pageSize, ProductTypeInfo info, boolean ignoreStatus) {
//		Condition condition = new Condition(eClass);
//		Example.Criteria criteria = condition.createCriteria();
//		if (info instanceof BaseCompanyAndStatusInfo) {
//			if (!ignoreStatus) {
//				criteria.andEqualTo("status", STATUS_DEFAULT);
//			}
//			criteria.andEqualTo("companyId", LoginFilter.getCurrentUser().getParentId());
//		}
//		try {
//			List<Field> fields = new ArrayList<>();
//			getAllField(fields, info.getClass());
//			for (Field field : fields) {
//				field.setAccessible(true);
//				Object value = field.get(info);
//				if (value != null) {
//					if (value instanceof String) {
//						if (StringUtils.isNotBlank((String)value)) {
//							criteria.andLike(field.getName(), "%" + (String)value + "%");
//						}
//					} else {
//						criteria.andEqualTo(field.getName(), value);
//					}
//				}
//			}
//		} catch (Exception e) {
//			throw new CommonBusinessException(e.getMessage(), e);
//		}
//		long total = getManager().getTotal(condition);
//		List<ProductTypeEntity> list = getManager().getByCondition(pageNum, pageSize, condition);
//		List<ProductTypeInfo> modelList = ExchangeUtils.exchangeList(list, ProductTypeInfo.class);
//		setCustomProperties(modelList);
//		Pager<ProductTypeInfo> pager = new Pager<ProductTypeInfo>(pageNum, pageSize, total, modelList);
//		return pager;
//	}

	public String getTypeSting(int id,int companyId) {
		return productTypeManager.getType(id,companyId);
	}

	@Transactional
	public void saveProductType(ProductTypeInfo productTypeInfo){
		ProductTypeEntity pte = ExchangeUtils.exchangeObject(productTypeInfo,ProductTypeEntity.class);
		//获取当前公司下的最大number
		Integer number = productTypeManager.getNextProductTypeNumber();
		String numberStr = frontCompWithZore(number,2);
		pte.setNumber(numberStr);
		productTypeManager.insertBy(pte);
	}

	/**
	 * 自动补零
	 * @param sourceDate 参数
	 * @param formatLength 长度
	 * @return
	 */
	public static String frontCompWithZore(int sourceDate, int formatLength) {
		/**
		 * 0 指前面补充零
		 * formatLength 字符总长度为 formatLength
		 * d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

}
