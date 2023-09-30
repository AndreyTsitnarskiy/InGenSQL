package com.example.ingensql;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private CreateGridPanel createGridPanel;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("SQL Insert Generator");

        // Создаем начальное окно для ввода количества полей
        VBox initialLayout = new VBox(10);
        initialLayout.setPadding(new Insets(10));
        Label labelTable = new Label("Введите схему и таблицу в формате <schema>.<table>");
        TextField tableNameField = new TextField();
        Label labelCountGenInsert = new Label("Введите количество генераций");
        TextField countInsertGenTextField = new TextField();
        Label label = new Label("Введите количество полей для инсерта:");
        TextField fieldCountTextField = new TextField();
        Button continueButton = new Button("Продолжить");

        continueButton.setOnAction(event -> {
            try {
                int fieldCount = Integer.parseInt(fieldCountTextField.getText());
                int insertCount = Integer.parseInt(countInsertGenTextField.getText());
                String tableName = String.valueOf(tableNameField.getText());
                createGridPanel = new CreateGridPanel(tableName, fieldCount, insertCount);
                createGridPanel.initializeFieldInputScene(primaryStage);
            } catch (NumberFormatException e) {
                // Обработка ошибки ввода некорректного числа
                createGridPanel.showAlert("Ошибка", "Введите корректное число полей.");
            }
        });

        initialLayout.getChildren().addAll(labelTable, tableNameField, labelCountGenInsert, countInsertGenTextField, label, fieldCountTextField, continueButton);
        Scene initialScene = new Scene(initialLayout, 300, 200);
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}