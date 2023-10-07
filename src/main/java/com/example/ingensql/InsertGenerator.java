package com.example.ingensql;

import java.time.LocalDateTime;
import java.util.*;

public class InsertGenerator {

    private final List<List<?>> allMapValues;
    private final List<String> tablesList;
    private final int countInsert;
    private final String tableName;

    public InsertGenerator(List<List<?>> allMapValues, List<String> tablesList, int countInsert, String tableName) {
        this.allMapValues = allMapValues;
        this.tablesList = tablesList;
        this.countInsert = countInsert;
        this.tableName = tableName;
    }

    public String generateFullListInserts() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < countInsert; i++) {
            result.append(startString(tablesList, tableName));
            result.append("(");
            for (List<?> list : allMapValues) {
                Object value = list.get(i);
                if (value instanceof String || value instanceof LocalDateTime) {
                    result.append("'").append(value).append("', ");
                } else {
                    result.append(value).append(", ");
                }
            }
            result.setLength(result.length() - 2);
            result.append(");\n");
        }
        return result.toString();
    }

    public void clearAll(List<List<?>> all, List<String> tablesList){
        for (List<?> list : all) {
            list.clear();
        }
        tablesList.clear();
    }

    private String startString(List<String> columnNames, String tableName) {
        StringBuilder result = new StringBuilder();
        result.append("INSERT INTO " + tableName + " (");
        for (String columnName : columnNames) {
            result.append(columnName).append(", ");
        }
        result.setLength(result.length() - 2); // Удаляем последнюю запятую и пробел
        result.append(") VALUES ");
        return result.toString();
    }
}

