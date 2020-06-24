package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Member;
import model.Notice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    @FXML Button btnTest;
    @FXML Button btnLogout;

    //공지사항등록 텍스트 필드
    @FXML TextField txtNoticeTitle;
    @FXML TextArea txtNoticeContents;
    @FXML Button btnNoticeRegist;

    public Stage primarystage;

    public Member memberlogin = null;

    //DB처리를 도와주는 객체들
    NoticeDAO noticeDAO = new NoticeDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //회원정보 테스트
        btnTest.setOnAction(e->System.out.println(memberlogin));

        //로그아웃 테스트
        btnLogout.setOnAction(e->handleBtnLogout());

        //공지사항 등록
        btnNoticeRegist.setOnAction(e->handleBtnNoticeRegist(e));

    }

    private void handleBtnNoticeRegist(ActionEvent e) {

        Notice notice = new Notice(txtNoticeTitle.getText().trim(), txtNoticeContents.getText().trim());

        int result = noticeDAO.noticeRegist(notice, memberlogin);

        if (result != 0) {
            AlertUtill.showInformationAlert("공지사항 등록 성공","공지사항 등록에 성공하셨습니다.","");
        }

    }

    private void handleBtnLogout() {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent view = null;
        try {
            view = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(view);
        scene.getStylesheets().add("/view/login.css");
        stage.setTitle("PT 시스템");
        stage.setScene(scene);
        LoginController loginController = fxmlLoader.getController();

        loginController.primarystage = stage;

        primarystage.close();
        stage.show();

    }
}
