package com.example.ingensql;

import java.util.*;

public class InsertGenerator {

    private final Map<String, List<?>> allMapValues;
    private final List<String> tablesList;
    private final int countInsert;

    public InsertGenerator(Map<String, List<?>> allMapValues, List<String> tablesList, int countInsert) {
        this.allMapValues = allMapValues;
        this.tablesList = tablesList;
        this.countInsert = countInsert;
    }

    public String generateFullListInserts() {
        StringBuilder result = new StringBuilder();

        for (String tableName : tablesList) {
            List<?> columnValues = allMapValues.get(tableName);
            if (columnValues != null) {
                if (columnValues.isEmpty()) {
                    continue;
                }

                StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName + " (");
                for (String columnName : allMapValues.keySet()) {
                    insertStatement.append(columnName).append(", ");
                }
                insertStatement.setLength(insertStatement.length() - 2); // Удаляем последнюю запятую и пробел
                insertStatement.append(") VALUES ");

                for (int i = 0; i < countInsert; i++) {
                    insertStatement.append("(");
                    for (String columnName : allMapValues.keySet()) {
                        Object value = allMapValues.get(columnName).get(i);
                        if (value instanceof String) {
                            insertStatement.append("'").append(value).append("', ");
                        } else {
                            insertStatement.append(value).append(", ");
                        }
                    }
                    insertStatement.setLength(insertStatement.length() - 2); // Удаляем последнюю запятую и пробел
                    insertStatement.append("), ");
                }
                insertStatement.setLength(insertStatement.length() - 2); // Удаляем последнюю запятую и пробел
                insertStatement.append(";\n");

                result.append(insertStatement);
            }
        }

        return result.toString();
    }
}
