package com.example.ingensql;

import com.example.ingensql.field_values.GenType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateGridPanel {

    private VBox fieldInputVBox;
    private int fieldCount;

    public CreateGridPanel(int fieldCount) {
        this.fieldCount = fieldCount;
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
                String selectedType = String.valueOf(typeComboBox.getValue());
                int columnIndex = GridPane.getColumnIndex(typeComboBox);
                if ("INT".equals(selectedType)) {
                    // Добавляем ComboBox для выбора целочисленных опций
                    ComboBox<GenType> intOptionsComboBox = new ComboBox<>();
                    intOptionsComboBox.setItems(FXCollections.observableArrayList(GenType.values()));
                    gridPane.add(intOptionsComboBox, columnIndex + 1, finalI);
                } else {
                    // Если выбран другой тип, скрываем ComboBox для целочисленных опций
                    Node intOptionsComboBox = getNodeByRowColumnIndex(finalI, columnIndex, gridPane);
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
        });

        ScrollPane scrollPane = new ScrollPane();
        fieldInputVBox = new VBox(10, gridPane, generateButton, scrollPane);
        fieldInputVBox.setPadding(new Insets(10));
        Scene fieldInputScene = new Scene(fieldInputVBox, 500, 400);
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

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getFieldInputVBox() {
        return fieldInputVBox;
    }

    public int getFieldCount() {
        return fieldCount;
    }
}
