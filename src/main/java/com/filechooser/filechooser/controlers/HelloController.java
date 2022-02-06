package com.filechooser.filechooser.controlers;

import com.filechooser.filechooser.utils.FileDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {

    private FileDataManager fileDataManager;
    private  XYChart.Series series;
    private File file = null;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private BarChart barChart;

    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws FileNotFoundException {
        Window window = ((Node)event.getTarget()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        this.file = fileChooser.showOpenDialog(window);
        System.out.println(file);
        fileDataManager = new FileDataManager(file);
        System.out.println(fileDataManager.getCharacterPercentageMap());
        generateCharacterGraph();
        comboBox.getSelectionModel().select(0);
    }

    @FXML
    protected  void  onComboBoxAction(ActionEvent event){
            switch (comboBox.getSelectionModel().getSelectedItem().toLowerCase(Locale.ROOT)){
                case "characters" ->{
                    if (file != null){
                        generateCharacterGraph();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("No file has been opened");
                        alert.showAndWait();
                    }
                }
                case "words" ->{
                    if (file != null){
                        generateWordGraph();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("No file has been opened");
                        alert.showAndWait();
                    }
                }
                default -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Invalid Selection");
                    alert.showAndWait();
                }
            }
    }

    private void generateCharacterGraph() {
        if (!fileDataManager.getCharacterPercentageMap().isEmpty()){
            this.series = new XYChart.Series<>();

            for (Character a:fileDataManager.getCharacterPercentageMap().keySet()) {
                String s = " "+a;
                series.getData().add(new XYChart.Data<>(s, fileDataManager.getCharacterPercentageMap().get(a)));
            }
            barChart.getData().clear();
            barChart.getData().add(series);
        }
    }

    private void generateWordGraph(){
        if (!fileDataManager.getWordPercantageMap().isEmpty()){
            this.series = new XYChart.Series<>();

            for (String a:fileDataManager.getWordPercantageMap().keySet()) {

                series.getData().add(new XYChart.Data<>(a, fileDataManager.getWordPercantageMap().get(a)));
            }
            barChart.getData().clear();
            barChart.getData().add(series);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.getItems().add("Characters");
        comboBox.getItems().add("Words");

        x.setLabel("Character/Word");
        y.setLabel("Percentage %");
        x.setAnimated(false);
        y.setAnimated(false);

    }
}