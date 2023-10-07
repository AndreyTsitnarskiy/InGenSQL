package com.example.ingensql.model;

import com.example.ingensql.factory.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class NullModel extends FieldValue {

    public NullModel() {
    }

    public List<String> getNullList(int count){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(null);
        }
        return result;
    }
}
