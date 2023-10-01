package com.example.ingensql.model;

import com.example.ingensql.exeption.ErrorUtils;
import com.example.ingensql.factory.FieldValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextModel extends FieldValue {
    private final int LENGTH = 10; // Длина случайной строки
    private final String CHARSETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public TextModel() {
    }

    public List<String> getRandom(int count){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++){
            result.add(randomString());
        }
        return result;
    }

    public List<String> getRandomRange(String start, String finish, int count){
        List<String> result = new ArrayList<>();
        int s = Integer.valueOf(start);
        int f = Integer.valueOf(finish);
        for (int i = 0; i < count; i++){
            int length = getRange(s, f);
            result.add(getRandomStringGetLength(length));
        }
        return result;
    }

    public String getRandomStringGetLength(int length){
        Random random = new Random();
        StringBuilder randomText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARSETS.length());
            char randomChar = CHARSETS.charAt(index);
            randomText.append(randomChar);
        }
        return String.valueOf(randomText);
    }

    private int getRange(int start, int finish){
        return start + (new Random().nextInt(finish - start + 1));
    }

    private String randomString(){
        Random random = new Random();
        StringBuilder randomText = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARSETS.length());
            char randomChar = CHARSETS.charAt(index);
            randomText.append(randomChar);
        }
        return String.valueOf(randomText);
    }
}
