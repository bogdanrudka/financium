package com.financium.menager.validation;

public interface FieldValueExists {

    /**
     * Check whether given values exists for given field.
     *
     * @param value     validated value
     * @param fieldName name of the field
     * @return true if no field with this value, false otherwise
     */
    boolean fieldValueExists(Object value, String fieldName);
}
