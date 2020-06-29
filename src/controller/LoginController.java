package controller;

//JavaFX 임포트
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javafx.stage.StageStyle;
import model.Member;

//Initializable 임포트
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField txtId;
    @FXML private PasswordField txtPassword;

    //버튼
    @FXML private Button btnLogin;
    @FXML private Button btnJoin;
    @FXML private Button btnIdPwFind;

    //라벨
    @FXML private Label labelAlert;

    //HBOX
    @FXML private HBox hboxId;
    @FXML private HBox hboxPw;

    //컨트롤러에서 스테이지를 받기위한 멤버변수
    public Stage primarystage = null;

    //로그인 정보
    public static Member memberLogin = null;

    //화면설정에 필요한 변수들을 전역변수로 세팅
    FXMLLoader fxmlLoader = null;
    Parent view = null;
    Scene scene = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //로그인 버튼 이벤트 설정
        btnLogin.setOnAction(e -> handleBtnLoginAction());

        //회원가입 버튼 이벤트 설정
        btnJoin.setOnAction(e -> handleBtnJoinAction(e));

        //ID PW 찾기 버튼 이벤트 설정
        btnIdPwFind.setOnAction(e -> handelBtnIdPwFindAction(e));

        //텍스트 필드에서 엔터키 입력시 로그인 기능을 수행하도록 이벤트 설정
        txtId.setOnKeyPressed(e -> handleEnterPressAction(e));
        txtPassword.setOnKeyPressed(e -> handleEnterPressAction(e));
    }

    //ID PW 찾기 버튼 이벤트 함수
    private void handelBtnIdPwFindAction(ActionEvent e) {
        //회원가입창 세팅
        fxmlLoader = new FXMLLoader(getClass().getResource("/view/find_idpw.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException e1) {
        }
        scene = new Scene(view);

        Stage findModal = new Stage(StageStyle.UTILITY);
        findModal.setTitle("ID/PW 찾기");
        findModal.setScene(scene);
        findModal.initModality(Modality.NONE);
        findModal.initOwner(primarystage);
        findModal.setResizable(false);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("/view/find_idpw.css");

        //아이디 패스워드 찾기창 컨트롤러 호츨
        FindIdPwController findIdPwController = fxmlLoader.getController();
        findIdPwController.primarystage = findModal;

        //아이디 비밀번호 찾기 창을 모달창으로 띄워줌
        findModal.show();

    }

    //엔터키 입력시 처리 이벤트 함수
    private void handleEnterPressAction(KeyEvent e) {

        //엔터키 입력시 로그인 기능을 수행하도록함
        if (e.getCode().equals(KeyCode.ENTER)) {
            handleBtnLoginAction();
        }

    }

    //회원가입 이벤트 함수
    private void handleBtnJoinAction(ActionEvent e) {

        //회원가입창 세팅
        fxmlLoader = new FXMLLoader(getClass().getResource("/view/join.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException e1) {
        }
        scene = new Scene(view);

        Stage joinStage = new Stage(StageStyle.UNDECORATED);
        joinStage.setTitle("회원가입");
        joinStage.initStyle(StageStyle.TRANSPARENT);
        joinStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("/view/join.css");

        JoinController joinConroller = fxmlLoader.getController();
        joinConroller.primarystage = joinStage;


        //로그인창을 닫고 회원가입창을 열어줌
        primarystage.close();
        joinStage.show();

    }

    //로그인 이벤트 함수
    private void handleBtnLoginAction() {

        //아이디와 패스워드를 입력해 주었는지 확인을 먼저 한다.
        if(txtId.getText().trim().equals("") && txtPassword.getText().trim().equals("")) {
            labelAlert.setTextFill(Color.RED);
            labelAlert.setText("ID값과 PW값이 입력되지 않았습니다.");
            hboxId.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            hboxPw.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            return;
        } else if(txtId.getText().trim().equals("")) {
            labelAlert.setTextFill(Color.RED);
            labelAlert.setText("ID값이 입력되지 않았습니다.");
            hboxId.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            hboxPw.setStyle("-fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
            return;
        } else if(txtPassword.getText().trim().equals("")) {
            labelAlert.setTextFill(Color.RED);
            labelAlert.setText("PW값이 입력되지 않았습니다.");
            hboxId.setStyle("-fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
            hboxPw.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px;");
            return;
        }

        MemberDAO memberDAO = new MemberDAO();
        ArrayList<Member> arrayList = memberDAO.Login(txtId.getText().trim(),txtPassword.getText().trim());

        //로그인 성공여부 분기점
        if(arrayList.isEmpty()) {
            labelAlert.setTextFill(Color.RED);
            labelAlert.setText("ID와 PW를 확인해주세요.");
            hboxId.setStyle("-fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
            hboxPw.setStyle("-fx-border-color: white; -fx-border-width: 0px 0px 2px 0px;");
            txtPassword.clear();
            return;
        }else {
            AlertUtill.showInformationAlert("로그인 성공",arrayList.get(0).getName()+"님 안녕하세요.","오늘도 좋은 하루되세요 ^^.");
        }

        //로그인한 회원의 정보를 저장해둠
        memberLogin = arrayList.get(0);

        //다음화면으로 넘어가기 위한 스테이지 생성
        Stage stage = new Stage();

        if(memberLogin.getTrainer_flag().equals("t")){
            //트레이너 전용 화면 세팅 -> 내가 설계한 화면으로 넘어가면 됨
            fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_main.fxml"));
            stage.setTitle("PT 시스템 (ver.T)");
        }else {
            //유저 전용 화면 세팅 -> 용천이형 화면으로 넘어가면 됨
            fxmlLoader = new FXMLLoader(getClass().getResource("/view/member_main.fxml"));
            stage.setTitle("PT 시스템 (ver.C)");
        }

        try {
            view = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(view);
        scene.getStylesheets().add("/view/admin.css");
        stage.setScene(scene);

        //로그인창 닫기
        primarystage.close();

        if(memberLogin.getTrainer_flag().equals("t")){
            //트레이너 화면 컨트롤러를 불러와줌
            AdminMainController adminMainController = fxmlLoader.getController();
            adminMainController.memberlogin = memberLogin;
            adminMainController.primarystage = stage;
        }else {
            //유저 화면 컨트롤러를 불러와줌
            MemberMainController memberMainController = fxmlLoader.getController();
            MemberMainController.memberLogin = memberLogin;
            memberMainController.primarystage = stage;
        }

        //새로 세팅한 창을 보여주기
        stage.show();

    }

}
