package com.example.ingensql.model;

import com.example.ingensql.factory.FieldValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DateModel extends FieldValue {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate MIN_DATE_TIME = LocalDate.of(1970, 1, 1);
    private final LocalDate MAX_DATE_TIME = LocalDate.of(2030, 12, 31);

    public DateModel() {
    }

    public List<LocalDate> getRandom(int count) {
        List<LocalDate> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(randomDate());
        }
        return result;
    }

    public List<LocalDate> getRandomRange(String start, String finish, int count) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate startDateTime = LocalDate.parse(start, formatter);
        LocalDate finishDateTime = LocalDate.parse(finish, formatter);
        long startSeconds = startDateTime.toEpochDay();
        long finishSeconds = finishDateTime.toEpochDay();

        for (int i = 0; i < count; i++) {
            long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, finishSeconds + 1);
            result.add(LocalDate.ofEpochDay(randomSeconds));
        }
        return result;
    }

    private LocalDate randomDate() {
        long minSeconds = MIN_DATE_TIME.toEpochDay();
        long maxSeconds = MAX_DATE_TIME.toEpochDay();
        long randomSeconds = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds + 1);
        return LocalDate.ofEpochDay(randomSeconds);
    }
}