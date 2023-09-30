package com.example.ingensql;

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
    private Set<String> columnName = new TreeSet<>();
    private GenType genType;
    private Map<String, List<Integer>> integerMap = new HashMap<>();

    public CreateGridPanel(String tableName, int fieldCount, int countInsert) {
        this.tableName = tableName;
        this.fieldCount = fieldCount;
        this.countInsert = countInsert;
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
                columnName.add(nameTextField.getText());
                String selectedType = String.valueOf(typeComboBox.getValue());
                int columnIndex = GridPane.getColumnIndex(typeComboBox);
                if ("INT".equals(selectedType)) {
                    // Добавляем ComboBox для выбора целочисленных опций
                    ComboBox<GenType> intOptionsComboBox = new ComboBox<>();
                    intOptionsComboBox.setItems(FXCollections.observableArrayList(GenType.values()));
                    gridPane.add(intOptionsComboBox, columnIndex + 1, finalI);
                    intOptionsComboBox.setOnAction(event1 -> {
                       String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
                       int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
                       if("RANDOM".contains(selectedGenType)){
                           IntegerModel integerModel = new IntegerModel();
                           List<Integer> integerList = integerModel.getRandom(countInsert);
                           integerMap.put(nameTextField.getText(), integerList);
                       } else if("RANDOM_RANGE".contains(selectedGenType)){
                           TextField startIntField = new TextField();
                           TextField finishIntField = new TextField();
                           Button button = new Button("Подтвердить");
                           gridPane.add(startIntField, columnIndexThreeColumn + 1, finalI);
                           gridPane.add(finishIntField, columnIndexThreeColumn + 2, finalI);
                           gridPane.add(button, columnIndexThreeColumn + 3, finalI);
                           button.setOnAction(even -> {
                           IntegerModel integerModel = new IntegerModel();
                           List<Integer> integerList = integerModel.getRandomRange(Integer.valueOf(startIntField.getText()),
                                   Integer.valueOf(finishIntField.getText()), countInsert);
                           integerMap.put(nameTextField.getText(), integerList);
                           System.out.println(integerList.size());
                           System.out.println(integerMap.size());
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

    public VBox getFieldInputVBox() {
        return fieldInputVBox;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public CreateGridPanel() {
    }
}
