package com.financium.menager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "category")
public class Category extends BaseDomain {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
