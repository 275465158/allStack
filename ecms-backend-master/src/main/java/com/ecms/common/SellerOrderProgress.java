package com.ecms.common;

import com.ecms.exceptions.CommonBusinessException;

public enum SellerOrderProgress {

    BOM             (0, "BOM制作"),
    CONFIRM         (1, "订单确认"),
    WAIT_PRODUCE    (2, "待生产"),
    PRODUCING       (3, "生产中"),
    WAIT_DELIVERY   (4, "待发货"),
    WAIT_PAYMENT    (5, "待收款"),
    FINISH          (6, "完成订单");

    private int id;
    private String name;

    SellerOrderProgress(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getNameById(int id) {
        for (SellerOrderProgress progress : SellerOrderProgress.values()) {
            if (id == progress.getId()) {
                return progress.getName();
            }
        }
        throw new CommonBusinessException("没找到对应的进度申明, id=" + id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
