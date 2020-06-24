package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JoinController implements Initializable {

    //버튼
    @FXML private Button btnBack;
    @FXML private Button btnIdChk;
    @FXML private Button btnJoin;
    @FXML private Button btnClose;

    //라벨
    @FXML private Label labelId;
    @FXML private Label labelPw;
    @FXML private Label labelName;
    @FXML private Label labelPhone;


    //텍스트필드
    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;

    @FXML private HBox hboxId;
    @FXML private VBox vboxPw;
    @FXML private VBox vboxName;
    @FXML private VBox vboxPhone;

    //패스워드 필드
    @FXML private PasswordField txtPassword;

    //라디오버튼
    @FXML private RadioButton rdoMale;
    @FXML private RadioButton rdoFemale;


    //스테이지 관련 변수
    public Stage primarystage = null;
    FXMLLoader fxmlLoader = null;
    Parent view = null;
    Scene scene = null;

    //라디오버튼 그룹 생성
    private ToggleGroup genderGroup;

    //Member DB
    MemberDAO memberDAO = new MemberDAO();
    //DB쿼리문 처리이후 실행문장수를 받아주는 변수
    int returnValue = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //라디오 버튼 그룹 및 초기화 함수
        radioButtonGenderInitialize();

        //로그인 화면으로 돌아가는 버튼 이벤트 함수
        btnBack.setOnAction(e -> handleBtnBackAction(e));

        //아이디 중복체크 이벤트 함수
        btnIdChk.setOnAction(e -> handleBtnIdChkAction(e));

        //회원가입 버튼 클릭 이벤트 함수
        btnJoin.setOnAction(e -> handleBtnJoinAction(e));

        //닫기 버튼 클릭 이벤트 함수
        btnClose.setOnAction(e -> Platform.exit());

    }

    //라디오버튼 그룹생성 및 초기값 설정
    private void radioButtonGenderInitialize() {
        genderGroup = new ToggleGroup();
        rdoMale.setToggleGroup(genderGroup);
        rdoFemale.setToggleGroup(genderGroup);
        rdoMale.setSelected(true);
    }

    //회원가입 버튼 클릭시 DB에 새로운 정보를 등록하도록 하는 함수
    private void handleBtnJoinAction(ActionEvent e) {

        //패스워드를 입력하지 않았을때 오류처리
        if(txtPassword.getText().trim().equals("")){
            labelPw.setTextFill(Color.RED);
            labelPw.setText("비밀번호를 입력해주세요.");
            vboxPw.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
        }else {
            labelPw.setText("");
            vboxPw.setStyle("-fx-border-color: black; -fx-border-width: 0px 0px 2px 0px;");
        }

        //이름을 입력하지 않았을때 오류처리
        if(txtName.getText().trim().equals("")) {
            labelName.setTextFill(Color.RED);
            labelName.setText("이름을 입력해주세요.");
            vboxName.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
        }else {
            labelName.setText("");
            vboxName.setStyle("-fx-border-color: black; -fx-border-width: 0px 0px 2px 0px;");
        }

        //핸드폰 번호를 입력하지 않았을때 오류처리
        if(txtPhone.getText().trim().equals("")) {
            labelPhone.setTextFill(Color.RED);
            labelPhone.setText("핸드폰 번호를 입력해주세요.");
            vboxPhone.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
        }else {
            labelPhone.setText("");
            vboxPhone.setStyle("-fx-border-color: black; -fx-border-width: 0px 0px 2px 0px;");
        }

        //ID 중복체크 실행 및 공백 오류 처리
        boolean flag = handleBtnIdChkAction(e);

        //ID 중복체크에서 발생하는 오류를 처리
        if (!flag || txtPassword.getText().trim().equals("") || txtName.getText().trim().equals("") || txtPhone.getText().trim().equals("")) return;

        //입력된 값들을 객체로 만든다.
        Member member = new Member(txtId.getText(),txtPassword.getText(),txtName.getText(),((RadioButton)genderGroup.getSelectedToggle()).getText(),txtPhone.getText());

        //DB에 새로운 회원을 등록합니다.
        returnValue = memberDAO.Join(member);

        //로그인 화면으로 돌아갑니다.
        handleBtnBackAction(e);

    }

    //아이디 중복체크 버튼 클릭시 실행하는 함수
    private boolean handleBtnIdChkAction(ActionEvent e) {

        //아이디를 입력하지 않았을때 오류 처리
        if (txtId.getText().trim().equals("")){
            labelId.setTextFill(Color.RED);
            labelId.setText("아이디를 입력해주세요.");
            hboxId.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            return false;
        }

        //DB에 동일한 아이디가 있는지 확인
        ArrayList<Member> arrayList = memberDAO.IdDuplicateCheck(txtId.getText().trim());

        //아이디 중복에 따른 분기점
        if(arrayList.isEmpty()) {
            //중복되는 ID가 없을때 처리문
            labelId.setText("중복되는 ID가 없습니다.");
            labelId.setTextFill(Color.GREEN);
            hboxId.setStyle("-fx-border-color: green; -fx-border-width: 0px 0px 2px 0px;");
        }else {
            //중복되는 ID가 있을때 처리문
            labelId.setText("중복되는 ID가 이미 존재합니다.");
            labelId.setTextFill(Color.RED);
            hboxId.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            return false;
        }

        return true;
    }

    //돌아가기 버튼 클릭시 실행하는 함수
    private void handleBtnBackAction(ActionEvent e) {

        //회원가입창 세팅
        fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        System.out.println(getClass().getResource("/view/login.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException e1) {
        }
        scene = new Scene(view);
        scene.getStylesheets().add("/view/login.css");

        //로그인 스테이지 생성
        Stage loginStage = new Stage(StageStyle.DECORATED);
        loginStage.setTitle("PT 시스템");
        loginStage.setScene(scene);

        //로그인 컨트롤러에 스테이지를 넘겨줍니다.
        LoginController loginController = fxmlLoader.getController();
        loginController.primarystage = loginStage;

        //로그인창을 닫고 회원가입창을 열어줌
        primarystage.close();
        loginStage.show();

    }

}
