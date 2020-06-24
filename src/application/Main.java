package application;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
		Parent view = fxmlLoader.load();
		LoginController loginController = fxmlLoader.getController();

		//컨트롤러에 스테이지를 넘겨줌
		loginController.primarystage = primaryStage;
		Scene scene = new Scene(view);
		scene.getStylesheets().add("/view/log.css");
		primaryStage.setTitle("PT 시스템");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	
}
