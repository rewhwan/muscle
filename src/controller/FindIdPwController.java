package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Member;

import java.net.URL;
import java.util.ResourceBundle;

public class FindIdPwController implements Initializable {

    public Stage primarystage = null;

    //ID찾기 관련 오브젝트
    @FXML TextField findIdName;
    @FXML TextField findIdPhone;
    @FXML Button btnFindId;
    @FXML Label findIdLabel;

    //ID 찾기 입력창 컨테이너
    @FXML VBox vboxFindIdTxt;
    @FXML HBox hboxFindIdName;
    @FXML HBox hboxFindIdPhone;

    //Pw찾기 관련 오브젝트
    @FXML TextField findPwId;
    @FXML TextField findPwName;
    @FXML TextField findPwPhone;
    @FXML Button btnFindPw;
    @FXML Label findPwLabel;

    //PW 찾기 입력창 컨테이너
    @FXML VBox vboxFindPwTxt;
    @FXML HBox hboxFindPwId;
    @FXML HBox hboxFindPwName;
    @FXML HBox hboxFindPwPhone;

    MemberDAO memberDAO = new MemberDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //아이디 찾기 버튼 클릭 이벤트 생성
        btnFindId.setOnAction(e -> handleBtnFindIdAction(e));

        //Pw 찾기 버튼 클릭 이벤트 생성
        btnFindPw.setOnAction(e -> handleBtnFindPwAction(e));

    }

    //비밀번호 찾기 이벤트 함수
    private void handleBtnFindPwAction(ActionEvent e) {

        hboxFindPwId.getStyleClass().set(0,"hboxBorderGrey");
        hboxFindPwName.getStyleClass().set(0,"hboxBorderGrey");
        hboxFindPwPhone.getStyleClass().set(0,"hboxBorderOff");

        //공백 입력시 오류 처리
        if(findPwId.getText().trim().equals("") || findPwName.getText().trim().equals("") || findPwPhone.getText().trim().equals("")) {
            vboxFindPwTxt.getStyleClass().set(0,"vboxRed");
            findPwLabel.setTextFill(Color.RED);
            findPwLabel.setText("정보를 빠짐없이 입력해주세요");
            return;
        }

        //입력값을 객체로 생성
        Member findMember = new Member (findPwId.getText().trim(),findPwName.getText().trim(),findPwPhone.getText().trim());

        //쿼리문의 결과값을 저장합니다.
        Member result = memberDAO.findMember(findMember,btnFindPw.getId());

        if(result == null) {
            //쿼리문 결과값이 없을때 처리문
            vboxFindPwTxt.getStyleClass().set(0,"vboxRed");
            findPwLabel.setText("ID,이름,전화번호를 제대로 입력해 주세요.");
            return;
        }else {
            //쿼리문 결과값이 있을때 처리문
            vboxFindPwTxt.getStyleClass().set(0,"vboxGreen");
            findPwLabel.setText("");
        }

        //ID 정보를 alert창으로 보여줍니다.
        AlertUtill.showInformationAlert("PW 찾기 완료","PW = \""+result.getPw()+"\"",result.getName()+"님의 Pw를 찾았습니다.");

    }

    //아이디 찾기 이벤트 함수
    private void handleBtnFindIdAction(ActionEvent e) {

        //공백을 입력했는지 알려주는 변수
        boolean blankFlag = false;

        if(findIdName.getText().trim().equals("") && findIdPhone.getText().trim().equals("")) {
            findIdLabel.setText("이름과 핸드폰번호를 입력해주세요.");
            blankFlag=true;
        }else if (findIdName.getText().trim().equals("")){
            findIdLabel.setText("이름을 입력해주세요.");
            blankFlag=true;
        }else if (findIdPhone.getText().trim().equals("")) {
            findIdLabel.setText("핸드폰 번호를 입력해주세요.");

            blankFlag=true;
        }

        //공백 입력시 중복되는 처리문
        if(blankFlag) {
            findIdLabel.setTextFill(Color.RED);
            hboxFindIdName.setStyle("-fx-border-color: rgba(128,128,128,0.7);");
            hboxFindIdPhone.setStyle("-fx-border-width: 0");
            vboxFindIdTxt.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            return;
        }

        //입력받은 정보로 객체 생성
        Member findMember = new Member(findIdName.getText().trim(),findIdPhone.getText().trim());

        Member result = memberDAO.findMember(findMember,btnFindId.getId());

        findIdLabel.setTextFill(Color.RED);
        hboxFindIdName.setStyle("-fx-border-color: rgba(128,128,128,0.7);");
        hboxFindIdPhone.setStyle("-fx-border-width: 0");

        if(result == null) {
            //쿼리문 결과값이 없을때 처리문
            vboxFindIdTxt.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            findIdLabel.setText("이름과 핸드폰번호가 일치하지 않습니다.");
            return;
        }else {
            //쿼리문 결과값이 있을때 처리문
            vboxFindIdTxt.setStyle("-fx-border-color: green; -fx-border-width: 2px");
            findIdLabel.setText("");
        }

        //ID 정보를 alert창으로 보여줍니다.
        AlertUtill.showInformationAlert("ID 찾기 완료","ID : "+result.getId(),result.getName()+"님의 ID를 찾았습니다.");

    }
}
