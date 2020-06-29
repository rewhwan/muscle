package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Member;
import model.QuestionMember;

import java.net.URL;
import java.util.ResourceBundle;

public class AnswerRegistController implements Initializable {



    //기본 스테이지 변수
    public Stage primarystage;

    //질문 객체 저장
    public QuestionMember question;

    //로그인 정보 객체 저장
    public Member memberLogin;

    //DB에 작업을 도와주는 변수들


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


}
