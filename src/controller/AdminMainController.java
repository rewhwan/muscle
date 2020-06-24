package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Member;
import model.Notice;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    @FXML Button btnTest;
    @FXML Button btnLogout;

    //공지사항등록 텍스트 필드
    @FXML TextField txtNoticeTitle;
    @FXML TextArea txtNoticeContents;
    @FXML Button btnNoticeRegist;
    //공지사항 테이블
    @FXML TableView tableNotice;
    //공지사항을 저장해서 받아오는 변수
    private ObservableList<Notice> noticeObsList = FXCollections.observableArrayList();

    public Stage primarystage;

    //로그인 정보
    public Member memberlogin = null;

    //DB처리를 도와주는 객체들
    NoticeDAO noticeDAO = new NoticeDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //공지사항 테이블값 초기화
        tableViewColumnInitiallize();
        //DB에서 공지사항을 전부 가져와 줍니다.
        noticeGetTotalList();

        //회원정보 테스트
        btnTest.setOnAction(e->System.out.println(memberlogin));

        //로그아웃 테스트
        btnLogout.setOnAction(e->handleBtnLogout());

        //공지사항 등록
        btnNoticeRegist.setOnAction(e->handleBtnNoticeRegist(e));

    }

    private void noticeGetTotalList() {

        //DB에서 공지사항에 대한 정보를 가져옵니다.
        ArrayList<Notice> arrayList = noticeDAO.getTotalList();

        //공지사항이 없을때 예외처리
        if(arrayList == null) return;

        noticeObsList.clear();

        for(int i=0; i< arrayList.size(); i++) {
            Notice notice = arrayList.get(i);
            noticeObsList.add(notice);
        }
    }

    private void tableViewColumnInitiallize() {

        //테이블뷰 UI객체 컬럼 초기화
        TableColumn colNo = new TableColumn("No");
        colNo.setPrefWidth(52);
        colNo.setStyle("-fx-alignment: CENTER");
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn colTitle = new TableColumn("제목");
        colTitle.setPrefWidth(170);
        colTitle.setStyle("-fx-alignment: CENTER");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn colContents = new TableColumn("내용");
        colContents.setPrefWidth(320);
        colContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

        TableColumn colCreatedBy = new TableColumn("작성자");
        colCreatedBy.setStyle("-fx-alignment: CENTER");
        colCreatedBy.setPrefWidth(80);
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("created_by"));

        TableColumn colCreatedAt = new TableColumn("작성일");
        colCreatedAt.setStyle("-fx-alignment: CENTER");
        colCreatedAt.setPrefWidth(150);
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        tableNotice.getColumns().addAll(colNo, colTitle, colContents, colCreatedBy, colCreatedAt);
        tableNotice.setItems(noticeObsList);

    }

    private void handleBtnNoticeRegist(ActionEvent e) {

        Notice notice = new Notice(txtNoticeTitle.getText().trim(), txtNoticeContents.getText().trim());

        int result = noticeDAO.noticeRegist(notice, memberlogin);

        if (result != 0) {
            noticeGetTotalList();
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
