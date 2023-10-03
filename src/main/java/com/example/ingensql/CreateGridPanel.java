package com.example.ingensql;

import com.example.ingensql.factory.FieldValue;
import com.example.ingensql.factory.FieldValueFactory;
import com.example.ingensql.factory.FieldValueFactoryImpl;
import com.example.ingensql.field_values.DoubleGenType;
import com.example.ingensql.field_values.IntegerGenType;
import com.example.ingensql.field_values.TypeField;
import com.example.ingensql.model.DoubleModel;
import com.example.ingensql.model.IntegerModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class CreateGridPanel {

    private VBox fieldInputVBox;
    private int fieldCount;
    private int countInsert;
    private String tableName;
    private FieldValueFactory fieldValueFactory;
    private Set<String> columnName = new TreeSet<>();
    private Map<String, List<Integer>> integerMap = new HashMap<>();
    private Map<String, List<Double>> doubleMap = new HashMap<>();

    public CreateGridPanel(int countInsert, int fieldCount, String tableName, TypeField fieldType) {
        this.fieldCount = fieldCount;
        this.countInsert = countInsert;
        this.tableName = tableName;
        this.fieldValueFactory = new FieldValueFactoryImpl(fieldType);
    }

    public void initializeFieldInputScene(Stage primaryStage) {
        // Создаем окно для ввода полей и их типов
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < fieldCount; i++) {
            Label nameLabel = new Label("Имя поля " + (i + 1) + ":");
            TextField nameTextField = new TextField();
            Label typeLabel = new Label("Тип поля " + (i + 1) + ":");
            ComboBox<TypeField> typeComboBox = new ComboBox<>();
            typeComboBox.setItems(FXCollections.observableArrayList(TypeField.values()));

            final int finalI = i;
            typeComboBox.setOnAction(event -> {
                int columnIndex = GridPane.getColumnIndex(typeComboBox);
                // Создаем объект FieldValue с использованием фабрики
                FieldValue fieldValue = fieldValueFactory.createValue(typeComboBox.getValue());

                if (fieldValue instanceof IntegerModel) {
                    // Добавляем ComboBox для выбора целочисленных опций
                    IntegerModel integerModel = new IntegerModel();
                    ComboBox<IntegerGenType> intOptionsComboBox = new ComboBox<>();
                    intOptionsComboBox.setItems(FXCollections.observableArrayList(IntegerGenType.values()));
                    gridPane.add(intOptionsComboBox, columnIndex + 1, finalI);

                    intOptionsComboBox.setOnAction(event1 -> {
                        String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
                        int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
                        Button buttonAction = new Button("Сформировать");
                        if ("RANDOM".contains(selectedGenType)) {
                            gridPane.add(buttonAction, columnIndexThreeColumn + 1, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Integer> integerList = integerModel.getRandom(countInsert);
                                integerMap.put(nameTextField.getText(), integerList);
                            });
                        } else if ("UNIQUE_RANDOM".contains(selectedGenType)) {
                            gridPane.add(buttonAction, columnIndexThreeColumn + 1, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Integer> integerList = integerModel.getRandomUnique(countInsert);
                                integerMap.put(nameTextField.getText(), integerList);
                            });
                        } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                            TextField startTextField = new TextField();
                            TextField finishTextField = new TextField();
                            gridPane.add(startTextField, columnIndexThreeColumn + 1, finalI);
                            gridPane.add(finishTextField, columnIndexThreeColumn + 2, finalI);
                            gridPane.add(buttonAction, columnIndexThreeColumn + 3, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Integer> integerList = integerModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                                integerMap.put(nameTextField.getText(), integerList);
                            });
                        } else if ("UNIQUE_RANDOM_RANGE".contains(selectedGenType)) {
                            TextField startTextField = new TextField();
                            TextField finishTextField = new TextField();
                            gridPane.add(startTextField, columnIndexThreeColumn + 1, finalI);
                            gridPane.add(finishTextField, columnIndexThreeColumn + 2, finalI);
                            gridPane.add(buttonAction, columnIndexThreeColumn + 3, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Integer> integerList = integerModel.getRandomRangeUnique(startTextField.getText(), finishTextField.getText(), countInsert);
                                integerMap.put(nameTextField.getText(), integerList);
                            });
                        } else {
                            getNodeByRowColumnIndex(finalI, columnIndexThreeColumn, gridPane);
                        }
                    });
                } else if (fieldValue instanceof DoubleModel) {
                    DoubleModel doubleModel = new DoubleModel();
                    ComboBox<DoubleGenType> intOptionsComboBox = new ComboBox<>();
                    intOptionsComboBox.setItems(FXCollections.observableArrayList(DoubleGenType.values()));
                    gridPane.add(intOptionsComboBox, columnIndex + 1, finalI);

                    intOptionsComboBox.setOnAction(event1 -> {
                        String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
                        int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
                        Button buttonAction = new Button("Сформировать");
                        if ("RANDOM".contains(selectedGenType)) {
                            gridPane.add(buttonAction, columnIndexThreeColumn + 1, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Double> doubleList = doubleModel.getRandom(countInsert);
                                doubleMap.put(nameTextField.getText(), doubleList);
                            });
                        } else if ("UNIQUE_RANDOM".contains(selectedGenType)) {
                            gridPane.add(buttonAction, columnIndexThreeColumn + 1, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Double> doubleList = doubleModel.getRandomUnique(countInsert);
                                doubleMap.put(nameTextField.getText(), doubleList);
                            });
                        } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                            TextField startTextField = new TextField();
                            TextField finishTextField = new TextField();
                            gridPane.add(startTextField, columnIndexThreeColumn + 1, finalI);
                            gridPane.add(finishTextField, columnIndexThreeColumn + 2, finalI);
                            gridPane.add(buttonAction, columnIndexThreeColumn + 3, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Double> doubleList = doubleModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                                doubleMap.put(nameTextField.getText(), doubleList);
                            });
                        } else if ("UNIQUE_RANDOM_RANGE".contains(selectedGenType)) {
                            TextField startTextField = new TextField();
                            TextField finishTextField = new TextField();
                            gridPane.add(startTextField, columnIndexThreeColumn + 1, finalI);
                            gridPane.add(finishTextField, columnIndexThreeColumn + 2, finalI);
                            gridPane.add(buttonAction, columnIndexThreeColumn + 3, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Double> doubleList = doubleModel.getRandomRangeUnique(startTextField.getText(), finishTextField.getText(), countInsert);
                                doubleMap.put(nameTextField.getText(), doubleList);
                            });

                        } else {
                            getNodeByRowColumnIndex(finalI, columnIndexThreeColumn, gridPane);
                        }
                    });
                } else {
                    // Если выбран другой тип, скрываем ComboBox для целочисленных опций
                    getNodeByRowColumnIndex(finalI, columnIndex, gridPane);
                }
            });

            gridPane.add(nameLabel, 0, i);
            gridPane.add(nameTextField, 1, i);
            gridPane.add(typeLabel, 2, i);
            gridPane.add(typeComboBox, 3, i);
        }

        Button generateButton = new Button("Сгенерировать SQL Insert");
        generateButton.setOnAction(event -> {
            InsertGenerator insertGenerator = new InsertGenerator();
            String text = String.valueOf(insertGenerator.generateFullListInserts(integerMap, tableName, countInsert));
        });

        fieldInputVBox = new VBox(10, gridPane, generateButton);
        fieldInputVBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(fieldInputVBox);
        Scene fieldInputScene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(fieldInputScene);
    }

    private void getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        gridPane.getChildren().removeIf(nodes -> {
            Integer nodeRow = GridPane.getRowIndex(nodes);
            Integer nodeColumn = GridPane.getColumnIndex(nodes);
            return nodeRow != null && nodeColumn != null &&
                    nodeRow.equals(row) && nodeColumn > column;
        });

    }

    public CreateGridPanel() {
    }

}
