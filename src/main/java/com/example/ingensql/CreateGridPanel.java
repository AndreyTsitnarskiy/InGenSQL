package com.example.ingensql;

import com.example.ingensql.factory.FieldValue;
import com.example.ingensql.factory.FieldValueFactory;
import com.example.ingensql.factory.FieldValueFactoryImpl;
import com.example.ingensql.field_values.GenType;
import com.example.ingensql.field_values.TypeField;
import com.example.ingensql.model.IntegerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

    public CreateGridPanel(int fieldCount, int countInsert, String tableName, TypeField fieldType) {
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
                    ComboBox<GenType> intOptionsComboBox = new ComboBox<>();
                    intOptionsComboBox.setItems(FXCollections.observableArrayList(GenType.values()));
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
                        } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                            TextField startTextField = new TextField();
                            TextField finishTextField = new TextField();
                            gridPane.add(startTextField,columnIndexThreeColumn + 1, finalI);
                            gridPane.add(finishTextField,columnIndexThreeColumn + 2, finalI);
                            gridPane.add(buttonAction,columnIndexThreeColumn + 3, finalI);
                            buttonAction.setOnAction(even -> {
                                List<Integer> integerList = integerModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                                integerMap.put(nameTextField.getText(), integerList);
                            });
                        }
                    });
                } else {
                    // Если выбран другой тип, скрываем ComboBox для целочисленных опций
                    Node intOptionsComboBox = getNodeByRowColumnIndex(finalI, columnIndex + 1, gridPane);
                    if (intOptionsComboBox != null) {
                        gridPane.getChildren().remove(intOptionsComboBox);
                    }
                }
                // Другие условия для других типов полей можно добавить аналогичным образом
            });

            gridPane.add(nameLabel, 0, i);
            gridPane.add(nameTextField, 1, i);
            gridPane.add(typeLabel, 2, i);
            gridPane.add(typeComboBox, 3, i);
        }

        Button generateButton = new Button("Сгенерировать SQL Insert");
        generateButton.setOnAction(event -> {
            // Здесь вы можете сгенерировать SQL Insert на основе введенных данных
            // И отобразить его или выполнить какую-либо другую логику
            InsertGenerator insertGenerator = new InsertGenerator();
            String text = String.valueOf(insertGenerator.generateFullListInserts(integerMap, tableName, countInsert));
        });

        fieldInputVBox = new VBox(10, gridPane, generateButton);
        fieldInputVBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(fieldInputVBox);
        Scene fieldInputScene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(fieldInputScene);
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    private void clearGridPane(GridPane gridPane) {
        gridPane.getChildren().clear();
    }

    public VBox getFieldInputVBox() {
        return fieldInputVBox;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public CreateGridPanel() {
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getCountInsert() {
        return countInsert;
    }

    public void setCountInsert(int countInsert) {
        this.countInsert = countInsert;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public FieldValueFactory getFieldValueFactory() {
        return fieldValueFactory;
    }

    public void setFieldValueFactory(FieldValueFactory fieldValueFactory) {
        this.fieldValueFactory = fieldValueFactory;
    }
}
