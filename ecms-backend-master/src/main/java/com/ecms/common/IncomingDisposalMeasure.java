package com.ecms.common;

import com.ecms.exceptions.CommonBusinessException;

public enum IncomingDisposalMeasure {
    RETURN(1,"退货"),EXCHANGE(2, "换货"),CONCESSIONS(3,"让步回用"),INTERNALLOSS(4, "内部损耗"),CHANGED(5,"物料转换");
    private int id;
    private String name;

    IncomingDisposalMeasure(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static String getNameById(int id){
        for(IncomingDisposalMeasure measure :IncomingDisposalMeasure.values()){
            if(id==measure.getId()){
                return measure.getName();
            }
        }
        throw new CommonBusinessException("没有对应的处理措施");
    }

    public static IncomingDisposalMeasure getById(int id){
        for(IncomingDisposalMeasure measure :IncomingDisposalMeasure.values()){
            if(id==measure.getId()){
                return measure;
            }
        }
        throw new CommonBusinessException("没有对应的处理措施");
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
