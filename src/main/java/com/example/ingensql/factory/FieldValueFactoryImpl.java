package com.example.ingensql.factory;

import com.example.ingensql.field_values.TypeField;
import com.example.ingensql.model.*;

public class FieldValueFactoryImpl implements FieldValueFactory{
    private TypeField type;

    public FieldValueFactoryImpl(TypeField type) {
        this.type = type;
    }

    @Override
    public FieldValue createValue(TypeField type) {
        switch (type) {
            case INTEGER:
                return new IntegerModel();
            case DOUBLE:
                return new DoubleModel();
            case BOOLEAN:
                return new BooleanModel();
            case NULL:
                return new NullModel();
            case TEXT:
                return new TextModel();
            case DATETIME:
                return new DateTimeModel();
            case DATE:
                return new DateModel();
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип поля: " + type);
        }
    }
}
