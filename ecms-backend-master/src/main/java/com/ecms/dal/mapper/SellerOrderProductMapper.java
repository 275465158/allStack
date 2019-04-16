package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerOrderProductEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SellerOrderProductMapper extends BasicMapper<SellerOrderProductEntity> {

    @Update("UPDATE seller_order_product SET material_status=1 WHERE order_id=#{orderId}")
    void updateSellerOrderProductStatusByOrderId(Integer orderId);

    @Select("SELECT order_id AS orderId FROM seller_order_product WHERE product_id = #{productId} AND material_status = 1 AND status = 1 ")
    List<Integer> getListByStatus(@Param("productId")Integer productId);
}
