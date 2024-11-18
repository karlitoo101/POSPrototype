package com.mycompany.systemprototype.pos;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author QCU
 */
public class ForesightController implements Initializable {

    @FXML
    private ToggleButton exit;

    @FXML
    private ToggleButton home;

    @FXML
    private ToggleButton inv;

    @FXML
    private ToggleButton staffs;

    @FXML
    private ToggleButton stats;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private TableView tView;
    
    @FXML
    private Button pSold;

    @FXML
    private Button refresh;
    
    @FXML
    private Button tSales;

    @FXML
    private Button toPdf;
    
    private boolean wtRefresh = true;
    
    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void home(ActionEvent event) {
        if (home.isSelected()) {
            home.setSelected(true);
        } else {
            home.setSelected(true);
        }
    }

    @FXML
    void staffs(ActionEvent event) {

    }

    @FXML
    void stats(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
//        barchart.getData().addAll(elements);

        XYChart.Series data = new XYChart.Series();

        data.getData().add(new XYChart.Data("January", 100));
        data.getData().add(new XYChart.Data("February", 100));
        data.getData().add(new XYChart.Data("March", 100));
        data.getData().add(new XYChart.Data("April", 144));
        data.getData().add(new XYChart.Data("May", 1122));
        data.getData().add(new XYChart.Data("June", 100));
        data.getData().add(new XYChart.Data("July", 100));
        data.getData().add(new XYChart.Data("August", 100));
        data.getData().add(new XYChart.Data("September", 100));
        data.getData().add(new XYChart.Data("October", 100));
        data.getData().add(new XYChart.Data("November", 100));
        data.getData().add(new XYChart.Data("December", 1000));

        barchart.getData().addAll(data);

        group.getToggles().addAll(home, inv, staffs, stats, exit);

    }
    
    @FXML
    void pSold(ActionEvent event) {
        wtRefresh = true;
        tSales.setUnderline(false);
        pSold.setUnderline(true);
        tSales.setDisable(false);
        pSold.setDisable(true);
        
        sold();
    }

    @FXML
    void refresh(ActionEvent event) {
        if (wtRefresh) {
            sold();
        } else {
            sales();
        }
    }
    
    @FXML
    void tSales(ActionEvent event) {
        wtRefresh = false;
        tSales.setUnderline(true);
        pSold.setUnderline(false);
        tSales.setDisable(true);
        pSold.setDisable(false);
        
        sales();
    }

    @FXML
    void toPdf(ActionEvent event) {

    }
    
    void sold() {
        // Get data from BarChart
        ObservableList<Series<String, Number>> seriesList = barchart.getData();
        ObservableList<String> categories = xAxis.getCategories();

// Create TableView columns
        tView.getColumns().clear();

// Category column
        TableColumn<String, String> categoryCol = new TableColumn<>("Month");
        categoryCol.setCellValueFactory(cellData -> {
            Object value = cellData.getValue();
            if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                return new SimpleStringProperty((String) map.get("Category"));
            }
            return new SimpleStringProperty("");
        });
        tView.getColumns().add(categoryCol);

// Series columns
        for (Series<String, Number> series : seriesList) {
            // Series quantity column
            TableColumn<String, Number> quantityCol = new TableColumn<>("Products Sold (Qty)");
            quantityCol.setCellValueFactory(cellData -> {
                Object value = cellData.getValue();
                if (value instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) value;
                    return new SimpleObjectProperty<>((Number) map.get(series.getName()));
                }
                return new SimpleObjectProperty<>(0);
            });
            tView.getColumns().add(quantityCol);
            
        }

// Populate TableView with data
        tView.getItems().clear();
        for (String category : categories) {
            ObservableMap<String, Object> row = FXCollections.observableHashMap();
            row.put("Category", category);
            for (Series<String, Number> series : seriesList) {
                for (Data<String, Number> data : series.getData()) {
                    if (data.getXValue().equals(category)) {
                        row.put(series.getName(), data.getYValue());
                    }
                }
            }
            tView.getItems().add(row);
        }
    }
    
    void sales() {
        // Get data from BarChart
        ObservableList<Series<String, Number>> seriesList = barchart.getData();
        ObservableList<String> categories = xAxis.getCategories();

// Create TableView columns
        tView.getColumns().clear();

// Category column
        TableColumn<String, String> categoryCol = new TableColumn<>("Month");
        categoryCol.setCellValueFactory(cellData -> {
            Object value = cellData.getValue();
            if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                return new SimpleStringProperty((String) map.get("Category"));
            }
            return new SimpleStringProperty("");
        });
        tView.getColumns().add(categoryCol);

// Series columns
        for (Series<String, Number> series : seriesList) {
            // Series quantity column
            TableColumn<String, Number> quantityCol = new TableColumn<>("Products Sold (Qty)");
            quantityCol.setCellValueFactory(cellData -> {
                Object value = cellData.getValue();
                if (value instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) value;
                    return new SimpleObjectProperty<>((Number) map.get(series.getName()));
                }
                return new SimpleObjectProperty<>(0);
            });
            tView.getColumns().add(quantityCol);

            // Series sales column
            TableColumn<String, Number> salesCol = new TableColumn<>("Product Total Sales");
            salesCol.setCellValueFactory(cellData -> {
                Object value = cellData.getValue();
                if (value instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) value;
                    Number quantity = (Number) map.get(series.getName());
                    double sales = quantity.doubleValue() * 40;
                    return new SimpleObjectProperty<>(sales);
                }
                return new SimpleObjectProperty<>(0);
            });
            tView.getColumns().add(salesCol);
        }

// Populate TableView with data
        tView.getItems().clear();
        for (String category : categories) {
            ObservableMap<String, Object> row = FXCollections.observableHashMap();
            row.put("Category", category);
            for (Series<String, Number> series : seriesList) {
                for (Data<String, Number> data : series.getData()) {
                    if (data.getXValue().equals(category)) {
                        row.put(series.getName(), data.getYValue());
                    }
                }
            }
            tView.getItems().add(row);
        }
    }

}
