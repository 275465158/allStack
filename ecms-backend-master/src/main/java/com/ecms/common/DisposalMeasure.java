package com.ecms.common;

import com.ecms.exceptions.CommonBusinessException;

public enum DisposalMeasure {
    DOANGIN(1,"返工"),REPAIR(2, "返修"),COMPROMISE(3,"让步回用"),REJECTED(4, "报废"),CHANGED(5,"物料转换");
    private int id;
    private String name;

    DisposalMeasure(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static String getNameById(int id){
        for(DisposalMeasure measure :DisposalMeasure.values()){
            if(id==measure.getId()){
                return measure.getName();
            }
        }
        throw new CommonBusinessException("没有对应的处理措施");
    }

    public static DisposalMeasure getById(int id){
        for(DisposalMeasure measure :DisposalMeasure.values()){
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
