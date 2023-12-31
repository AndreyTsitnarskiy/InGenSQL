package com.example.ingensql.model;

import com.example.ingensql.exeption.ErrorUtils;
import com.example.ingensql.factory.FieldValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerModel extends FieldValue {

    public List<Integer> getRandom(int count){
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            int num = new Random().nextInt();
            integerList.add(num);
        }
        return integerList;
    }

    public List<Integer> getRandomUnique(int count){
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            int num = new Random().nextInt();
            if(!integerList.contains(num)) {
                integerList.add(num);
            }
        }
        return integerList;
    }

    public List<Integer> getRandomRange(String start, String finish, int count){
        List<Integer> integerList = new ArrayList<>();
        try {
            int s = Integer.valueOf(start);
            int f = Integer.valueOf(finish);
            if (f - s > 0) {
                for (int i = 0; i < count; i++) {
                    int num = (int) (Math.random() * ++s) + f;
                    integerList.add(num);
                }
            } else {
                ErrorUtils.showErrorDialog("Ошибка ввода диапазона",
                        "Cтартовое значение больше конечного\n" +
                                "Количество создаваемых Insert меньше чем диапазон");
            }
        } catch (Exception e){
            ErrorUtils.showErrorDialog("Ошибка ввода",
                    "Введено не целое число");
        }
        return integerList;
    }

    public List<Integer> getRandomRangeUnique(String start, String finish, int count){
        List<Integer> integerList = new ArrayList<>();
        try {
            int s = Integer.valueOf(start);
            int f = Integer.valueOf(finish);
            if (f - s > 0) {
                for (int i = 0; i < count; i++) {
                    int num = (int) (Math.random() * ++s) + f;
                    if(!integerList.contains(num)){
                        integerList.add(num);
                    }
                }
            } else {
                ErrorUtils.showErrorDialog("Ошибка ввода диапазона",
                        "Cтартовое значение больше конечного\n" +
                                "Количество создаваемых Insert меньше чем диапазон");
            }
        } catch (Exception e){
            ErrorUtils.showErrorDialog("Ошибка ввода",
                    "Введено не целое число");
        }
        return integerList;
    }

    public IntegerModel() {
    }
}
