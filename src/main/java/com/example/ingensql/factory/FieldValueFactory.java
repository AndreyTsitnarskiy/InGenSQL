package com.example.ingensql.factory;

import com.example.ingensql.field_values.TypeField;

public interface FieldValueFactory {
    FieldValue createValue(TypeField typeField);
}
