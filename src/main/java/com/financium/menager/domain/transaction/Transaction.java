package com.financium.menager.domain.transaction;

import com.financium.menager.domain.BaseDomain;
import com.financium.menager.domain.Category;
import com.financium.menager.domain.Tag;
import com.financium.menager.domain.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE")
public abstract class Transaction extends BaseDomain {
    @JoinColumn(name = "OWNER_ID", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "VALUE", nullable = false)
    private Double value;

    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name= "CATEGORY")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany
    @JoinTable(name = "ACCOUNT_TO_TAG",
            joinColumns = @JoinColumn(table = "transaction", name = "TRANSACTION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(table = "tag", name = "TAG_ID", referencedColumnName = "ID")
    )
    private Set<Tag> tags;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
