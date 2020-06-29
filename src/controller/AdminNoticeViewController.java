package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Notice;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminNoticeViewController implements Initializable {

    //수정버튼
    @FXML Button btnModify;
    //닫기버튼
    @FXML Button btnExit;
    //텍스트 필드 & 에어리어
    @FXML TextField txtTitle;
    @FXML TextArea txtContents;

    //스테이지
    public Stage primaryStage = null;

    //수정 버튼 플래그 -> 텍스트필드 잠금 풀기 & 수정 기능
    Boolean modify_flag = false;
    //DB 입력을 도와줄 객체
    NoticeDAO noticeDAO = new NoticeDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //공지사항 초기값 불러와주기
        setNoticeContentsInitiallize(AdminMainController.selectedNotice);

        //수정 버튼 이벤트
        btnModify.setOnAction(e -> handleBtnModifyAction(e));

        //닫기 버튼 이벤트
        btnExit.setOnAction(e -> primaryStage.close());
    }

    private void handleBtnModifyAction(ActionEvent e) {

        if(modify_flag == false) {
            Optional<ButtonType> result = AlertUtill.showConfirmationAlert("공지사항 수정 확인","공지사항을 수정하시겠어요?","공지사항을 수정하시려면 OK버튼 혹은 엔터키를 입력하세요.");
            if(result.get() == ButtonType.CANCEL) return;
            txtTitle.setDisable(false);
            txtContents.setDisable(false);
            modify_flag = true;
        } else  if(modify_flag = true) {
            int result = noticeDAO.noticeUpdate(AdminMainController.selectedNotice, txtTitle.getText().trim(), txtContents.getText().trim());

            primaryStage.close();
        }
    }

    private void setNoticeContentsInitiallize(Notice selectedNotice) {
        txtTitle.setText(selectedNotice.getTitle());
        txtTitle.setDisable(true);
        txtContents.setText(selectedNotice.getContents());
        txtContents.setDisable(true);
    }
}
