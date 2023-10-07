package com.example.ingensql;

import com.example.ingensql.factory.FieldValue;
import com.example.ingensql.factory.FieldValueFactory;
import com.example.ingensql.factory.FieldValueFactoryImpl;
import com.example.ingensql.field_values.*;
import com.example.ingensql.field_values.TypeField;
import com.example.ingensql.model.*;
import com.example.ingensql.model.DateModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateGridPanel {

    private VBox fieldInputVBox;
    private int fieldCount;
    private int countInsert;
    private String tableName;
    private FieldValueFactory fieldValueFactory;
    private List<List<?>> allMapValues = new ArrayList<>();
    private List<String> tablesList = new ArrayList<>();

    public CreateGridPanel(int countInsert, int fieldCount, String tableName, TypeField fieldType) {
        this.fieldCount = fieldCount;
        this.countInsert = countInsert;
        this.tableName = tableName;
        this.fieldValueFactory = new FieldValueFactoryImpl(fieldType);
    }

    public void initializeFieldInputScene(Stage primaryStage) {
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

                FieldValue fieldValue = fieldValueFactory.createValue(typeComboBox.getValue());

                if (fieldValue instanceof IntegerModel) {
                    addIntegerValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof DoubleModel) {
                    addDoubleValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof TextModel) {
                    addTextValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof BooleanModel) {
                    addBooleanValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof DateTimeModel) {
                    addDateTimeValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof DateModel) {
                    addDateValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else if (fieldValue instanceof NullModel) {
                    addNullValueOptions(gridPane, finalI, columnIndex, nameTextField);
                } else {
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
            InsertGenerator insertGenerator = new InsertGenerator(allMapValues, tablesList, countInsert, tableName);
            String text = insertGenerator.generateFullListInserts();
            System.out.println(text);
            insertGenerator.clearAll(allMapValues, tablesList);
            saveFileToPath(primaryStage, text);
        });

        fieldInputVBox = new VBox(10, gridPane, generateButton);
        fieldInputVBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(fieldInputVBox);
        Scene fieldInputScene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(fieldInputScene);
    }

    private void saveToFile(File file, String text) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFileToPath(Stage primaryStage, String text) {
        primaryStage.setTitle("Сохранить файл");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.sql)", "*.sql"));

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            saveToFile(file, text);
        }
    }

    private void getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        gridPane.getChildren().removeIf(nodes -> {
            Integer nodeRow = GridPane.getRowIndex(nodes);
            Integer nodeColumn = GridPane.getColumnIndex(nodes);
            return nodeRow != null && nodeColumn != null &&
                    nodeRow.equals(row) && nodeColumn > column;
        });
    }

    private void addIntegerValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName) {
        IntegerModel integerModel = new IntegerModel();
        ComboBox<IntegerGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(IntegerGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Integer> integerList = integerModel.getRandom(countInsert);
                    allMapValues.add(integerList);
                    tablesList.add(columnName.getText());
                });
            } else if ("UNIQUE_RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Integer> integerList = integerModel.getRandomUnique(countInsert);
                    allMapValues.add(integerList);
                    tablesList.add(columnName.getText());
                });
            } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<Integer> integerList = integerModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(integerList);
                    tablesList.add(columnName.getText());
                });
            } else if ("UNIQUE_RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<Integer> integerList = integerModel.getRandomRangeUnique(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(integerList);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    private void addDoubleValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName) {
        DoubleModel doubleModel = new DoubleModel();
        ComboBox<DoubleGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(DoubleGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Double> doubleList = doubleModel.getRandom(countInsert);
                    allMapValues.add(doubleList);
                    tablesList.add(columnName.getText());
                });
            } else if ("UNIQUE_RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Double> doubleList = doubleModel.getRandomUnique(countInsert);
                    allMapValues.add(doubleList);
                    tablesList.add(columnName.getText());
                });
            } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<Double> doubleList = doubleModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(doubleList);
                    tablesList.add(columnName.getText());
                });
            } else if ("UNIQUE_RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<Double> doubleList = doubleModel.getRandomRangeUnique(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(doubleList);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    private void addBooleanValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName) {
        BooleanModel booleanModel = new BooleanModel();
        ComboBox<BooleanGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(BooleanGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Boolean> booleans = booleanModel.getRandom(countInsert);
                    allMapValues.add(booleans);
                    tablesList.add(columnName.getText());
                });
            } else if ("ONLY_TRUE".contains(selectedGenType)) {
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<Boolean> booleans = booleanModel.getAllTrue(countInsert);
                    allMapValues.add(booleans);
                    tablesList.add(columnName.getText());
                });
            } else if ("ONLY_FALSE".contains(selectedGenType)) {
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<Boolean> booleans = booleanModel.getAllFalse(countInsert);
                    allMapValues.add(booleans);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    public void addTextValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName){
        TextModel textModel = new TextModel();
        ComboBox<TextGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(TextGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<String> textList = textModel.getRandom(countInsert);
                    allMapValues.add(textList);
                    tablesList.add(columnName.getText());
                });
            } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<String> textList = textModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(textList);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    public void addDateTimeValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName){
        DateTimeModel dateTimeModel = new DateTimeModel();
        ComboBox<DateTimeGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(DateTimeGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<LocalDateTime> dateTimeList = dateTimeModel.getRandom(countInsert);
                    allMapValues.add(dateTimeList);
                    tablesList.add(columnName.getText());
                });
            } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<LocalDateTime> dateTimeList = dateTimeModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(dateTimeList);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    public void addDateValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName){
        DateModel dateModel = new DateModel();
        ComboBox<DateGenType> intOptionsComboBox = new ComboBox<>();
        intOptionsComboBox.setItems(FXCollections.observableArrayList(DateGenType.values()));
        gridPane.add(intOptionsComboBox, columnIndex + 1, row);

        intOptionsComboBox.setOnAction(event1 -> {
            String selectedGenType = String.valueOf(intOptionsComboBox.getValue());
            int columnIndexThreeColumn = GridPane.getColumnIndex(intOptionsComboBox);
            Button buttonAction = new Button("Сформировать");
            if ("RANDOM".contains(selectedGenType)) {
                getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
                gridPane.add(buttonAction, columnIndexThreeColumn + 1, row);
                buttonAction.setOnAction(even -> {
                    List<LocalDate> dateTimeList = dateModel.getRandom(countInsert);
                    allMapValues.add(dateTimeList);
                    tablesList.add(columnName.getText());
                });
            } else if ("RANDOM_RANGE".contains(selectedGenType)) {
                TextField startTextField = new TextField();
                TextField finishTextField = new TextField();
                gridPane.add(startTextField, columnIndexThreeColumn + 1, row);
                gridPane.add(finishTextField, columnIndexThreeColumn + 2, row);
                gridPane.add(buttonAction, columnIndexThreeColumn + 3, row);
                buttonAction.setOnAction(even -> {
                    List<LocalDate> dateTimeList = dateModel.getRandomRange(startTextField.getText(), finishTextField.getText(), countInsert);
                    allMapValues.add(dateTimeList);
                    tablesList.add(columnName.getText());
                });
            }
        });
    }

    public void addNullValueOptions(GridPane gridPane, int row, int columnIndex, TextField columnName){
        getNodeByRowColumnIndex(row, columnIndex + 1, gridPane);
        NullModel nullModel = new NullModel();
        Button buttonAction = new Button("Сформировать");
        gridPane.add(buttonAction, columnIndex + 1, row);
        buttonAction.setOnAction(even -> {
            List<String> nullModelRandom = nullModel.getNullList(countInsert);
            allMapValues.add(nullModelRandom);
            tablesList.add(columnName.getText());
        });
    }

    public CreateGridPanel() {
    }

}
