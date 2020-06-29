package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.QuestionMember;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminQuestionViewController implements Initializable {

    //닫기 버튼
    @FXML Button btnExit;
    //라벨
    @FXML Label labelTitle;
    @FXML Label labelContents;

    //스테이지
    public Stage primaryStage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noticeInitiallize(AdminMainController.selectedQuestion);

        btnExit.setOnAction(e -> primaryStage.close());
    }

    private void noticeInitiallize(QuestionMember selectedQuestion) {
        //라벨값을 초기화 시켜준다.
        labelTitle.setText(selectedQuestion.getTitle());
        labelContents.setText(selectedQuestion.getContents());
    }
}
