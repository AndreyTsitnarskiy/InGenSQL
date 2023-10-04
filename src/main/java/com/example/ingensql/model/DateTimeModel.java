package com.example.ingensql.model;

import com.example.ingensql.factory.FieldValue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DateTimeModel extends FieldValue {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
    private final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0);
    private final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(2030, 12, 31, 23, 59);

    public DateTimeModel() {
    }

    public List<LocalDateTime> getRandom(int count) {
        List<LocalDateTime> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(randomDateTime());
        }
        return result;
    }

    public List<LocalDateTime> getRandomRange(String start, String finish, int count) {
        List<LocalDateTime> result = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime finishDateTime = LocalDateTime.parse(finish, formatter);
        long startSeconds = startDateTime.toEpochSecond(ZoneOffset.UTC);
        long finishSeconds = finishDateTime.toEpochSecond(ZoneOffset.UTC);

        for (int i = 0; i < count; i++) {
            long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, finishSeconds + 1);
            result.add(LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC));
        }
        return result;
    }

    private LocalDateTime randomDateTime() {
        long minSeconds = MIN_DATE_TIME.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = MAX_DATE_TIME.toEpochSecond(ZoneOffset.UTC);
        long randomSeconds = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds + 1);
        return LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC);
    }
}
