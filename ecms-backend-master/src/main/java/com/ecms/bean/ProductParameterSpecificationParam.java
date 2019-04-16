package com.ecms.bean;

import java.util.List;

public class ProductParameterSpecificationParam {

    private Integer id;

    private String name;

    private List<Parameter> parameterList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }

    public static class Parameter {
        private String parameterName;
        private String parameterUnit;

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterUnit() {
            return parameterUnit;
        }

        public void setParameterUnit(String parameterUnit) {
            this.parameterUnit = parameterUnit;
        }
    }
}


