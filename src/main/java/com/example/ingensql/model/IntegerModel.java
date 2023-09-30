package com.example.ingensql.model;

import com.example.ingensql.field_values.GenType;

import java.util.Random;

public class IntegerModel {
    private int a;
    private int b;

    public void createField(){

    }

    private int getRandom(){
        return new Random().nextInt();
    }

    private int getRandomRange(int start, int finish){
        return (int) (Math.random() * ++start) + finish;
    }

    public IntegerModel(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public IntegerModel() {
    }
}
