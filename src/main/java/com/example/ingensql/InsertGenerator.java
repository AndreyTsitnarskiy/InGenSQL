package com.example.ingensql;

import java.util.*;

public class InsertGenerator {

    private final String START_STRING = "INSERT INTO ";
    private final String VALUES = " VALUES ";

    public String generateFullListInserts(Map<String, List<?>> mapList, List<String> tableNames, int countInsert) {
        StringBuilder result = new StringBuilder();

        for (String tableName : tableNames) {
            StringBuilder stringBuilder = new StringBuilder();
            String intoAndTableName = START_STRING + tableName + " (";

            // Получаем список полей для текущей таблицы
            List<String> columnNames = getColumnNames(mapList, tableName);

            for (int i = 0; i < columnNames.size(); i++) {
                stringBuilder.append(intoAndTableName)
                        .append(columnNames.get(i));
                if (i != columnNames.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")").append(VALUES);

            for (int i = 0; i < countInsert; i++) {
                stringBuilder.append(" (");
                for (int j = 0; j < columnNames.size(); j++) {
                    Object value = mapList.get(columnNames.get(j)).get(i);
                    stringBuilder.append(value);
                    if (j != columnNames.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append(")");
                result.append(stringBuilder.toString()).append(";\n");
                stringBuilder.setLength(0); // Очищаем StringBuilder
            }
        }

        return result.toString();
    }

    // Метод для получения списка полей для конкретной таблицы
    private List<String> getColumnNames(Map<String, List<?>> mapList, String tableName) {
        if (mapList.containsKey(tableName)) {
            // Используем первую запись (List<?>) в мапе, чтобы получить список полей
            Map.Entry<String, List<?>> entry = mapList.entrySet().iterator().next();
            List<?> values = entry.getValue();
            if (!values.isEmpty() && values.get(0) instanceof String) {
                return (List<String>) values;
            }
        }
        return Collections.emptyList(); // Возвращаем пустой список, если таблица не найдена
    }

    public InsertGenerator() {
    }
}
