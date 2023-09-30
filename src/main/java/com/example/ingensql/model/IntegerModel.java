package com.example.ingensql.model;

import com.example.ingensql.field_values.GenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerModel extends Model{
    private int a;
    private int b;
    private int countGen;

    public List<Integer> getRandom(int count){
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            int num = new Random().nextInt();
            integerList.add(num);
        }
        return integerList;
    }

    public List<Integer> getRandomRange(int start, int finish, int count){
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * ++start) + finish;
            integerList.add(num);
        }
        return integerList;
    }

    public IntegerModel(int a, int b, int countGen) {
        this.a = a;
        this.b = b;
        this.countGen = countGen;
    }

    public IntegerModel(int countGen) {
        this.countGen = countGen;
    }

    public IntegerModel() {
    }
}
