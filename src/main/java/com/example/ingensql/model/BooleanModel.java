package com.example.ingensql.model;

import com.example.ingensql.factory.FieldValue;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BooleanModel extends FieldValue {

    public BooleanModel() {
    }

    public List<Boolean> getRandom(int count){
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(new Random().nextBoolean());
        }
        return result;
    }

    public List<Boolean> getAllTrue(int count){
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(true);
        }
        return result;
    }
    public List<Boolean> getAllFalse(int count){
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(false);
        }
        return result;
    }
}
