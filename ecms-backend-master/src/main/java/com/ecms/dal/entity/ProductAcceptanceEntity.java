package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_acceptance")
public class ProductAcceptanceEntity extends BaseCompanyAndStatusEntity {
    private String name;

    private String owner;
    @Column(name="inspection_diagram")
    private String inspectionDiagram;

    public String getInspectionDiagram() {
        return inspectionDiagram;
    }

    public void setInspectionDiagram(String inspectionDiagram) {
        this.inspectionDiagram = inspectionDiagram;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}