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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    @FXML Button btnRefresh;
    @FXML Button btnLogout;

    //공지사항등록 텍스트 필드
    @FXML TextField txtNoticeTitle;
    @FXML TextArea txtNoticeContents;
    @FXML Button btnNoticeRegist;
    @FXML Button btnNoticeDelete;
    //공지사항 테이블
    @FXML TableView tableNotice;
    //공지사항 테이블에서 선택된 컬럼의 정보를 가져오는 변수
    static Notice selectedNotice = null;
    //공지사항을 저장해서 받아오는 변수
    private ObservableList<Notice> noticeObsList = FXCollections.observableArrayList();


    //PT 테이블
    @FXML TableView tablePT;
    //PT 버튼
    @FXML Button btnPTDelete;
    //PT신청정보를 저장해서 받아오는 변수
    private ObservableList<PTMember> PTMemberObsList = FXCollections.observableArrayList();
    //PT신청정보 파이챠트
    @FXML PieChart PTPieChart;
    //PT신청정보 바챠트
    @FXML BarChart PTBarChart;
    //선택된 PT정보
    PTMember selectedPT = null;

    //Question 질문 테이블
    @FXML TableView tableQuestion;
    private ObservableList<QuestionMember> questionObsList = FXCollections.observableArrayList();
    //Question 테이블에서 선택된 컬럼의 정보를 가져오는 변수
    public static QuestionMember selectedQuestion = null;


    //기본 스테이지 변수
    public Stage primarystage;

    //로그인 정보
    public static Member memberlogin = null;

    //DB처리를 도와주는 객체들
    NoticeDAO noticeDAO = new NoticeDAO();
    PtDAO ptDAO = new PtDAO();
    QuestionDAO questionDAO = new QuestionDAO();
    AnswerDAO answerDAO = new AnswerDAO();

    //화면설정에 필요한 변수들을 전역변수로 세팅
    FXMLLoader fxmlLoader = null;
    Parent view = null;
    Scene scene = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //공지사항 테이블값 초기화
        noticeTableViewColumnInitiallize();
        //DB에서 공지사항을 전부 가져와 줍니다.
        noticeGetTotalList();
        //공지사항 등록
        btnNoticeRegist.setOnAction(e->handleBtnNoticeRegist(e));
        //공지사항 삭제
        btnNoticeDelete.setOnAction(e -> handleBtnNoticeDelete(e,selectedNotice));
        //공지사항 테이블 선택시 이벤트
        tableNotice.setOnMousePressed(e -> handleTableNoticePressAction(e));

        //PT신청정보 테이블값 초기화
        PTTableViewColumnInitiallize();
        //DB에서 PT신청 내용을 가져옵니다.
        PTGetTotalList(LoginController.memberLogin);
        //PT정보에 따라서 파이차트를 만들어줍니다.
        PTPieChartInitiallize();
        //PT정보에 따라서 바챠트를 만들어줍니다.
        PTBarChartInitiallize();
        //PT테이블 클릭시 발생하는 이벤트 함수
        tablePT.setOnMousePressed(e -> handleTablePTPressAction(e));
        //PT삭제버튼 클릭시 발생하는 이벤트
        btnPTDelete.setOnAction(e -> handleBtnPTDeleteAction(e));

        //Question 테이블컬럼 초기화
        questionTableViewColumnInitiallize();
        //DB에서 Question을 전부 가져와줍니다.
        questionTotalList();
        //Question 테이블 선택시 이벤트
        tableQuestion.setOnMousePressed(e -> handleTableQuestionPressAction(e));

        //새로고침 이벤트 함수
        btnRefresh.setOnAction(e -> handleBtnTestAction(e));
        //로그아웃 버튼
        btnLogout.setOnAction(e -> handleBtnLogout());

    }

    private void handleTablePTPressAction(MouseEvent e) {
        selectedPT = (PTMember) tablePT.getSelectionModel().getSelectedItem();
    }

    private void handleBtnPTDeleteAction(ActionEvent e) {
        Optional<ButtonType> result = AlertUtill.showConfirmationAlert("삭제작업 확인요청","정말로 "+selectedPT.getName()+"님의 PT 신청내역을 삭제하시겠어요?","삭제를 하시려면 엔터키나 OK 버튼을 눌러주세요");

        if(result.get() == ButtonType.CANCEL) return;

        if(selectedPT == null) {
            AlertUtill.showInformationAlert("PT정보가 없음", "삭제하고자 하는 PT 정보가 없습니다.", "삭제하고자 하는 PT를 테이블에서 먼저 선택해 주세요.");
        }
        ptDAO.deletePT(memberlogin, selectedPT);

        PTGetTotalList(memberlogin);
    }

    public void handleBtnTestAction(ActionEvent e) {
        questionTotalList();
        noticeGetTotalList();
        PTGetTotalList(memberlogin);
    }

    //Question테이블 클릭시 발생하는 이벤트 함수
    private void handleTableQuestionPressAction(MouseEvent e) {
        selectedQuestion = (QuestionMember) tableQuestion.getSelectionModel().getSelectedItem();

        if(e.getClickCount() != 2) return;

        fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_question_view.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        scene = new Scene(view);

        Stage questionModal = new Stage(StageStyle.UTILITY);
        questionModal.setTitle("질문 내용보기");
        questionModal.setScene(scene);
        questionModal.initModality(Modality.WINDOW_MODAL);
        questionModal.initOwner(primarystage);
        questionModal.setResizable(false);
        scene.setFill(Color.TRANSPARENT);

        //공지사항 창의 컨트롤러 불러오기
        AdminQuestionViewController adminQuestionViewController = fxmlLoader.getController();
        adminQuestionViewController.primaryStage = questionModal;
        adminQuestionViewController.selectedQuestion = selectedQuestion;
        adminQuestionViewController.memberlogin = memberlogin;

        questionModal.setOnHidden(event -> {
            questionObsList.clear();
            questionTotalList();
        });

        //공지사항 내용 창 보여주기
        questionModal.show();
    }

    //답변 등록버튼 클릭시 이벤트 함수
    private void handelBtnAnswerRegistAction() {
        if(selectedQuestion == null) {
            AlertUtill.showWarningAlert("btnAnswerRegist 오류","수정할 데이터가 없습니다.","수정할 데이터를 먼저 테이블에서 선택해 주세요.");
            return;
        }

        fxmlLoader = new FXMLLoader(getClass().getResource("/view/answer_regist.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException e) {
        }
        scene = new Scene(view);

        Stage answerModal = new Stage(StageStyle.UTILITY);
        answerModal.setTitle("Answer 등록");
        answerModal.setScene(scene);
        answerModal.initModality(Modality.WINDOW_MODAL);
        answerModal.initOwner(primarystage);
        answerModal.setResizable(false);
        scene.setFill(Color.TRANSPARENT);

        //답변 입력창 컨트롤러 호출
        AnswerRegistController answerRegistController = fxmlLoader.getController();
        answerRegistController.primarystage = answerModal;
        answerRegistController.question = selectedQuestion;
        answerRegistController.memberLogin = memberlogin;

        //답변 입력창 모달창을 보여줍니다.
        answerModal.show();

    }

    //질문 내용을 전부 가져와 줍니다.
    private void questionTotalList() {

        //DB에서 공지사항에 대한 정보를 가져옵니다.
        ArrayList<QuestionMember> questionArrayList = questionDAO.getQuestionNonAnswer();

        //공지사항이 없을때 예외처리
        if(questionArrayList == null) {
            System.out.println("hh");
            return;
        }

        questionObsList.clear();

        for(int i=0; i<questionArrayList.size(); i++) {
            QuestionMember questionMember = questionArrayList.get(i);
            questionObsList.add(questionMember);
        }

    }

    //질문 테이블 칼럼 세팅
    private void questionTableViewColumnInitiallize() {
        //테이블뷰 UI객체 컬럼 초기화
        TableColumn colNo = new TableColumn("no");
        colNo.setPrefWidth(100);
        colNo.setStyle("-fx-font-size:16px; -fx-alignment: CENTER");
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn colTitle = new TableColumn("제목");
        colTitle.setPrefWidth(252);
        colTitle.setStyle("-fx-font-size:16px; -fx-alignment: CENTER");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn colContents = new TableColumn("내용");
        colContents.setPrefWidth(520);
        colContents.setStyle("-fx-font-size:16px; -fx-alignment: CENTER");
        colContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

        TableColumn colCreatedBy = new TableColumn("작성자");
        colCreatedBy.setPrefWidth(120);
        colCreatedBy.setStyle("-fx-font-size:16px; -fx-alignment: CENTER");
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("created_by"));

        TableColumn colCreatedAt = new TableColumn("작성일");
        colCreatedAt.setPrefWidth(200);
        colCreatedAt.setStyle("-fx-font-size:16px; -fx-alignment: CENTER");
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        tableQuestion.setFixedCellSize(50);

        tableQuestion.getColumns().addAll(colNo, colTitle, colContents, colCreatedBy, colCreatedAt);
        tableQuestion.setItems(questionObsList);

    }

    //공지사항 삭제버튼 클릭시 이벤트 함수
    private void handleBtnNoticeDelete(ActionEvent e, Notice selectedNotice) {

        //테이블 뷰에서 선택을 하지 않았을 경우 오류처리
        if(selectedNotice == null) {
            AlertUtill.showErrorAlert("공지사항 삭제 오류","테이블 뷰에서 선택된 데이터가 없습니다.","삭제할 공지사항을 먼저 선택해 주세요");
            return;
        }

        int result = noticeDAO.noticeDelete(selectedNotice);

        if(result != 0) {
            AlertUtill.showInformationAlert("공지사항 삭제 성공","공지사항 삭제에 성공했습니다.",selectedNotice.getNo()+"번 공지사항 삭제됨");
            this.selectedNotice = null;
        }

        noticeGetTotalList();

    }

    //공지사항 테이블 클릭시 어느것을 선택했는지 알려줌
    private void handleTableNoticePressAction(MouseEvent e) {
        selectedNotice = (Notice) tableNotice.getSelectionModel().getSelectedItem();

        if(e.getClickCount() != 2) return;

        fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_notice_view.fxml"));
        try {
            view = fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        scene = new Scene(view);

        Stage noticeModal = new Stage(StageStyle.UTILITY);
        noticeModal.setTitle("공지사항 관람 & 수정");
        noticeModal.setScene(scene);
        noticeModal.initModality(Modality.WINDOW_MODAL);
        noticeModal.initOwner(primarystage);
        noticeModal.setResizable(false);
        scene.setFill(Color.TRANSPARENT);

        //공지사항 창의 컨트롤러 불러오기
        AdminNoticeViewController adminNoticeViewController = fxmlLoader.getController();
        adminNoticeViewController.primaryStage = noticeModal;

        //공지사항 입력이후 창이 닫기면 실행되는 이벤트
        noticeModal.setOnHidden(event -> noticeGetTotalList());

        //공지사항 내용 창 보여주기
        noticeModal.show();
    }

    //전체회원의 PT통계를 바챠트로 보여줍니다.
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

    // 전체회원의 PT시간 통계를 파이챠트로 보여줍니다.
    private void PTPieChartInitiallize() {
        ObservableList<PTPieChart> PTPieChartResult = PtDAO.getPTPieChart();
        ObservableList PTPieChart = FXCollections.observableArrayList();

        for(int i=0;i < PTPieChartResult.size();i++) {
            PTPieChart PTPieChartModel = PTPieChartResult.get(i);
            PTPieChart.add(new PieChart.Data(PTPieChartModel.getTime(), PTPieChartModel.getCnt()));
        }

        this.PTPieChart.setData(PTPieChart);
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

        if(txtNoticeTitle.getText().trim().equals("") || txtNoticeContents.getText().trim().equals("")) {
            AlertUtill.showErrorAlert("공지사항 등록 실패","제목 또는 내용이 빈칸입니다.","내용을 입력하거나 공백이 아닌 다른값을 기입해 주세요.");
            return;
        }

        Notice notice = new Notice(txtNoticeTitle.getText().trim(), txtNoticeContents.getText().trim());

        int result = noticeDAO.noticeRegist(notice, memberlogin);

        if (result != 0) {
            noticeGetTotalList();
            AlertUtill.showInformationAlert("공지사항 등록 성공","공지사항 등록에 성공하셨습니다.","");
            txtNoticeTitle.clear();
            txtNoticeContents.clear();
        }

    }

    //로그아웃 버튼 클릭시 실행하는 함수
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
