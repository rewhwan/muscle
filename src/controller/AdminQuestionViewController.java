package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Member;
import model.QuestionMember;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminQuestionViewController implements Initializable {
    //답변 등록 버튼
    @FXML Button btnAnswerRegist;
    //닫기 버튼
    @FXML Button btnExit;
    //라벨
    @FXML Label labelTitle;
    @FXML Label labelContents;
    //텍스트 에어리어
    @FXML TextArea txtAnswerContents;

    QuestionMember selectedQuestion = null;
    Member memberlogin = null;

    //DAO
    AnswerDAO answerDAO = new AnswerDAO();

    //스테이지
    public Stage primaryStage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noticeInitiallize(AdminMainController.selectedQuestion);

        //등록버튼 이벤트 함수
        btnAnswerRegist.setOnAction(e -> handleBtnAnswerRegistAction());

        //닫기버튼 이벤트 함수
        btnExit.setOnAction(e -> primaryStage.close());
    }

    //답변 등록버튼 클릭시 실행하는 함수
    private void handleBtnAnswerRegistAction() {

        Optional<ButtonType> result = AlertUtill.showConfirmationAlert("답변등록 확인","정말 "+selectedQuestion.getNo()+"번의 질문에 답변을 등록하시겠습니까?","답변을 등록하시려면 엔터키 혹은 OK버튼을 눌러주세요.");
        //취소버튼 클릭시 발생하는 이벤트
        if(result.get() == ButtonType.CANCEL) return;

        int resultValue = answerDAO.answerRegist(selectedQuestion,txtAnswerContents.getText(),memberlogin);

        if(resultValue != 0) {
            primaryStage.close();
        }
    }

    private void noticeInitiallize(QuestionMember selectedQuestion) {
        //라벨값을 초기화 시켜준다.
        labelTitle.setText(selectedQuestion.getTitle());
        labelContents.setText(selectedQuestion.getContents());
    }
}
