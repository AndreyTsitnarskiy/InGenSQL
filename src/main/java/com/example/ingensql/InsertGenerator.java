package com.example.ingensql;

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
        for (int i = 0; i < countInsert; i++){
            result.append(startString(tablesList, tableName));
            result.append("(");
            for (List<?> list : allMapValues)  {
                result.append(list.get(i)).append(", ");
            }
            result.setLength(result.length() - 2); // Удаляем последнюю запятую и пробел
            result.append(");\n");
        }
        return result.toString();
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
