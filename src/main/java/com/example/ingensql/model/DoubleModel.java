package com.example.ingensql.model;

import com.example.ingensql.exeption.ErrorUtils;
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

    public List<Double> getRandomUnique(int count){
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            double var = new Random().nextDouble();
            if(!result.contains(var)){
                result.add(var);
            }
        }
        return result;
    }

    public List<Double> getRandomRange(String start, String finish, int count){
        List<Double> result = new ArrayList<>();
        try {

            double s = Double.valueOf(start);
            double f = Double.valueOf(finish);
            if (f - s > 0) {
                for (int i = 0; i < count; i++) {
                    result.add(s + (f - s + 1.0) * new Random().nextDouble());
                }
            } else {
                ErrorUtils.showErrorDialog("Неверный ввод ", "Начальное числ больше предидущего");
            }
        } catch (NumberFormatException e) {
            ErrorUtils.showErrorDialog("Неверный ввод ", "Введен не Double");
        }
        return result;
    }

    public List<Double> getRandomRangeUnique(String start, String finish, int count){
        List<Double> result = new ArrayList<>();
        try {

            double s = Double.valueOf(start);
            double f = Double.valueOf(finish);
            if (f - s > 0) {
                for (int i = 0; i < count; i++) {
                    double var = s + (f - s + 1.0) * new Random().nextDouble();
                    if(!result.contains(var)){
                        result.add(var);
                    }
                }
            } else {
                ErrorUtils.showErrorDialog("Неверный ввод ", "Начальное числ больше предидущего");
            }
        } catch (NumberFormatException e) {
            ErrorUtils.showErrorDialog("Неверный ввод ", "Введен не Double");
        }
        return result;
    }
}
