package com.example.ingensql.model;

import com.example.ingensql.factory.FieldValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoubleModel extends FieldValue {
    public DoubleModel() {
    }

    public List<Double> getRandom(int count){
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(new Random().nextDouble());
        }
        return result;
    }

    public List<Double> getRandomRange(String start, String finish, int count){
        List<Double> result = new ArrayList<>();
        double s = Double.valueOf(start);
        double f = Double.valueOf(finish);
        for (int i = 0; i < count; i++){
            result.add(s + (f - s + 1.0) * new Random().nextDouble());
        }
        return result;
    }
}
