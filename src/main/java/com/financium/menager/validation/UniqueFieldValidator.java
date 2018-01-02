package com.financium.menager.validation;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueFieldValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;
    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> serviceClassName = unique.service();
        String serviceQualifier = unique.serviceQualifier();

        fieldName = unique.fieldName();
        if (!serviceQualifier.equals("")) {
            service = applicationContext.getBean(serviceQualifier, serviceClassName);
        } else {
            service = applicationContext.getBean(serviceClassName);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return service.fieldValueExists(value, fieldName);
    }
}
