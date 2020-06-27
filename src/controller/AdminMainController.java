package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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

    //PT 테이블
    @FXML TableView tablePT;
    //PT신청정보를 저장해서 받아오는 변수
    private ObservableList<PTMember> PTMemberObsList = FXCollections.observableArrayList();
    //테스트 콤보박스
    @FXML ComboBox comboTime;
    private ObservableList<PTMember> PTTime = FXCollections.observableArrayList();
    //PT신청정보 파이챠트
    @FXML PieChart PTPieChart;
    //PT신청정보 바챠트
    @FXML BarChart PTBarChart;

    public Stage primarystage;

    //로그인 정보
    public static Member memberlogin = null;

    //DB처리를 도와주는 객체들
    NoticeDAO noticeDAO = new NoticeDAO();
    PtDAO ptDAO = new PtDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //공지사항 테이블값 초기화
        noticeTableViewColumnInitiallize();
        //DB에서 공지사항을 전부 가져와 줍니다.
        noticeGetTotalList();

        //PT신청정보 테이블값 초기화
        PTTableViewColumnInitiallize();
        //DB에서 PT신청 내용을 가져옵니다.
        PTGetTotalList(LoginController.memberLogin);
        //콤보박스 테스트
        comboTimeInitiallize();

        //PT정보에 따라서 파이차트를 만들어줍니다.
        PTPieChartInitiallize();
        //PT정보에 따라서 바챠트를 만들어줍니다.
        PTBarChartInitiallize();

        //회원정보 테스트
        btnTest.setOnAction(e->System.out.println(memberlogin));

        //로그아웃 테스트
        btnLogout.setOnAction(e->handleBtnLogout());

        //공지사항 등록
        btnNoticeRegist.setOnAction(e->handleBtnNoticeRegist(e));

    }

    private void PTBarChartInitiallize() {
        ObservableList<PTBarChart> PTBarChartResult = PtDAO.getPTBarChart();
        ObservableList PTBarChart = FXCollections.observableArrayList();
        XYChart.Series series = new XYChart.Series();
        series.setName("이용횟수");

        for(int i=0; i< PTBarChartResult.size();i++) {
            PTBarChart PTBarChartModel = PTBarChartResult.get(i);
            PTBarChart.add(new XYChart.Data(PTBarChartModel.getMember_id(),PTBarChartModel.getCnt()));
        }
        series.setData(PTBarChart);
        this.PTBarChart.getData().add(series);
    }

    private void PTPieChartInitiallize() {
        ObservableList<PTPieChart> PTPieChartResult = PtDAO.getPTPieChart();
        ObservableList PTPieChart = FXCollections.observableArrayList();

        for(int i=0;i < PTPieChartResult.size();i++) {
            PTPieChart PTPieChartModel = PTPieChartResult.get(i);
            PTPieChart.add(new PieChart.Data(PTPieChartModel.getTime(), PTPieChartModel.getCnt()));
        }

        this.PTPieChart.setData(PTPieChart);
    }

    private void comboTimeInitiallize() {
        comboTime.getItems().add("10:00");
        comboTime.getItems().add("11:00");
        comboTime.getItems().add("13:00");
        comboTime.getItems().add("14:00");
        comboTime.getItems().add("15:00");
        comboTime.getItems().add("16:00");
        comboTime.getItems().add("17:00");
        comboTime.getItems().add("18:00");

        PTTime = PtDAO.getPTTimeCombo();

        for(int i= 0;i < PTTime.size();i++) {
            comboTime.getItems().remove(PTTime.get(i).getTime());
        }


    }

    private void PTGetTotalList(Member memberlogin) {
        PTMemberObsList.clear();

        //DB에서 공지사항에 대한 정보를 가져옵니다.
        ArrayList<PTMember> PTMemberArrayList = ptDAO.getTrainerPTList(memberlogin.getId());
        //공지사항이 없을때 예외처리
        if(PTMemberArrayList == null) {
            return;
        }

        for(int i=0; i<PTMemberArrayList.size(); i++) {
            PTMember PTMember = PTMemberArrayList.get(i);
            PTMemberObsList.add(PTMember);
        }
    }

    private void PTTableViewColumnInitiallize() {

        //테이블뷰 UI객체 컬럼 초기화
        TableColumn colDate = new TableColumn("날짜");
        colDate.setPrefWidth(130);
        colDate.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn colTime = new TableColumn("시간");
        colTime.setPrefWidth(110);
        colTime.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn colName = new TableColumn("신청자 이름");
        colName.setPrefWidth(110);
        colName.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn colPhone = new TableColumn("핸드폰");
        colPhone.setPrefWidth(160);
        colPhone.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn colCreatedBy = new TableColumn("작성자");
        colCreatedBy.setPrefWidth(100);
        colCreatedBy.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("created_by"));

        TableColumn colCreatedAt = new TableColumn("작성일");
        colCreatedAt.setPrefWidth(207);
        colCreatedAt.setStyle("-fx-font-size:18px; -fx-alignment: CENTER");
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        tablePT.setFixedCellSize(45);

        tablePT.getColumns().addAll(colDate, colTime, colName, colPhone, colCreatedBy, colCreatedAt);
        tablePT.setItems(PTMemberObsList);
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

    private void noticeTableViewColumnInitiallize() {

        //테이블뷰 UI객체 컬럼 초기화
        TableColumn colNo = new TableColumn("No");
        colNo.setPrefWidth(52);
        colNo.setStyle("-fx-alignment: CENTER");
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn colTitle = new TableColumn("제목");
        colTitle.setPrefWidth(163);
        colTitle.setStyle("-fx-alignment: CENTER");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn colContents = new TableColumn("내용");
        colContents.setPrefWidth(310);
        colContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

        TableColumn colCreatedBy = new TableColumn("작성자");
        colCreatedBy.setStyle("-fx-alignment: CENTER");
        colCreatedBy.setPrefWidth(80);
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("created_by"));

        TableColumn colCreatedAt = new TableColumn("작성일");
        colCreatedAt.setStyle("-fx-alignment: CENTER");
        colCreatedAt.setPrefWidth(150);
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        tableNotice.setFixedCellSize(45);

        tableNotice.getColumns().addAll(colNo, colTitle, colContents, colCreatedBy, colCreatedAt);
        tableNotice.setItems(noticeObsList);

    }

    private void handleBtnNoticeRegist(ActionEvent e) {

        Notice notice = new Notice(txtNoticeTitle.getText().trim(), txtNoticeContents.getText().trim());

        int result = noticeDAO.noticeRegist(notice, memberlogin);

        if (result != 0) {
            noticeGetTotalList();
            AlertUtill.showInformationAlert("공지사항 등록 성공","공지사항 등록에 성공하셨습니다.","");
            txtNoticeTitle.clear();
            txtNoticeContents.clear();
        }

    }

    private void handleBtnLogout() {
        Stage stage = new Stage();

        Optional<ButtonType> result = AlertUtill.showConfirmationAlert("로그아웃 확인","정말 로그아웃 하시겠어요?","로그아웃을 하시려면 엔터키 혹은 OK버튼을 눌러주세요.");

        if(result.get() == ButtonType.CANCEL) return;

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
