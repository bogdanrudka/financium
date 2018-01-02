package com.financium.menager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "tags")
public class Tag extends BaseDomain {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
