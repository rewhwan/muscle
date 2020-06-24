package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TipForWorkoutController implements Initializable {

	@FXML
	private WebView webView;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnClose;
	
	private WebEngine engine;

	
	public Stage stage;
	public Stage primaryStage = null;
	
	
	 Scene scene = null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		engine = webView.getEngine();
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				engine.load("https://www.youtube.com/watch?v=32ULd1Tb1WA");

			}
		});

		btnClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleBtnCloseAction(event);
				
			}
		});

	}

	private void handleBtnCloseAction(ActionEvent event) {
		engine.load("");
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/tipForWork.fxml"));
			Scene scene = new Scene(root);
			Stage closeStage = (Stage) btnClose.getScene().getWindow();
			closeStage.close();
        } catch (IOException e) {
        }

	}
}