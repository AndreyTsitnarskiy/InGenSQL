package com.example.ingensql;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InsertGenerator {

    private final String START_STRING = "INSERT INTO ";
    private final String VALUES = " VALUES ";
    private StringBuilder result = new StringBuilder();
    public StringBuilder generateFullListInserts(Map<String, List<Integer>> integerMap, String tableName, int countInsert) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> val1 = integerMap.get(0);
        List<Integer> val2 = integerMap.get(1);
        String intoAndTableName = START_STRING + tableName + " (";
        for (int i = 0; i < integerMap.size(); i++) {
            if (i != integerMap.size()) {
                stringBuilder.append(intoAndTableName)
                        .append(integerMap.get(i))
                        .append(", ");
            }
            stringBuilder.append(integerMap.get(i))
                    .append(")")
                    .append(VALUES)
                    .append(" (");
        }
        for (int i = 0; i < countInsert; i++) {
                result.append(val1.get(i)).append(", ").append(val2.get(i)).append(");\n");
        }
        System.out.println(result);
        return result;
    }

    public InsertGenerator() {
    }
}
