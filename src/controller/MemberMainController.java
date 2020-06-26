package controller;

import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import model.Member;
import model.Notice;
import model.PT;
import model.QuestionMember;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.sun.webkit.ContextMenu.ShowContext;

public class MemberMainController implements Initializable {
	private final File DIR = new File("C:/images");
	
	//커밋테스트
	
	// 관리자문의 관련 id
	@FXML private Button btnQuestionWrite;
	@FXML private TableView<QuestionMember> tableQuestion;
	@FXML private Button btnSearch;
	@FXML private TextField txtSearch;
	
	//공지사항 관련
	@FXML private TextField txtNoSearch;
	@FXML private Button btnNoSearch;
	@FXML private Button btnNoRegist;
	@FXML private TableView tableNotice;
	
	//회원권 신청 버튼
	@FXML private Button btnOne; //1개월 밑의 8만원 버튼
	
	//pt 관련
	@FXML private TableView tablePT;
	@FXML private ImageView imgPt;
	@FXML private Button btnPTDelete;
	@FXML private Button btnPTApply;

	//회원정보 관련
	@FXML private Label lblId;
	@FXML private Label lblName;
	@FXML private Label lblPhoneNo;
	@FXML private Label lblBirth;
	@FXML private Label lblAddress;
	@FXML private Label lblMail;

	@FXML private PasswordField pwfMemPW;
	@FXML private TextField txtMemName;
	@FXML private TextField txtMemGender;
	@FXML private TextField txtMemPhoneNo;
	@FXML private TextField txtMemBirth;
	@FXML private TextField txtMemAddr;
	@FXML private TextField txtMemMail;
	@FXML private Button btnMemChange;
	@FXML private Tab memberInfo;
	
	@FXML private TableView tableMemInfo;
	
	
	//운동정보 관련 
	@FXML private Button btnChestPress;
	@FXML private Button btnSeatedDip;
	
	
	
	public Stage stage;
	private ObservableList<QuestionMember> obsList;
	private ObservableList<Notice> obsListNo;
	private ObservableList<Member> obsListPT;
	private ObservableList<Member> obsListMember;
	private ToggleGroup group;
	private int tableViewQuestionSelectedIndex;
	private File selectFile;
	private File directorySave;

	public static Member memberLogin;

	public Stage primarystage = null;



	public MemberMainController() {
		this.obsList = FXCollections.observableArrayList();
		this.obsListPT = FXCollections.observableArrayList();
		this.obsListNo = FXCollections.observableArrayList();
		this.obsListMember = FXCollections.observableArrayList();
		this.stage = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 관리자문의 글쓰기 버튼 이벤트
		btnQuestionWrite.setOnAction(event-> { handleBtnQuestionWriteAction(event);	});

		//관리자문의의 테이블뷰 컬럼 초기화 QuestionMember 필드와 연겨 ㄹ
		tableQuestionColumnInitialize();
		
		//회원정보 데이터 초기화 
		memberInfoInitialize();
		
		//DB의 question테이블에서 데이터를 가져와 관리자 문의 테이블에 넣음 
		questionTotalList();

		//관리자 문의 검색 버튼 이벤트 등록 및 핸들러
		btnSearch.setOnAction( event-> { handleBtnSearchAction(event);});

		//관리자 문의 테이블을 더블 클릭하면 창이 나오게하는 이벤트 및 핸들러
		tableQuestion.setOnMouseClicked( event-> {handleQuestionDoubleClick( event);});
//--------------------------------------------------------------------------------------------		
		//공지사항의 테이블 뷰 컬럼 초기화 Notice 필드와 연결
		tableNoticeColumnInitialize();

		//공지사항의 검색 버튼 이벤트 등록 및 핸들러
		btnNoSearch.setOnAction( event-> { handleBtnNoSearch(event); });

		//공지사항의 등록버튼 이벤트 등록 및 핸들러
		btnNoRegist.setOnAction( event-> {	handleBtnNoRegist(event);});

		//공지사항 테이블 뷰 마우스 클릭 시 이벤트 처리 
		tableQuestion.setOnMousePressed(event-> {handleTableViewQuestionPressAction(event);});

		//공지사항 내용 가져오기	
		noticeTotalList();
//---------------------------------------------------------------------------------------------		
		//PT의 테이블 뷰 컬럼 초기화 PT필드와 연결
		tablePTColumnInitialize();
		
		// db의 pt 테이블의 데이터에서 데이터를 가져와서 PT 테이블에 넣음 
		ptTotalList();
		
		//pt테이블에서 트레이너를 선택하고 신청을 누르면 창이 뜨고 그 트레이너의 그 날짜의 일정을 보여줌
		btnPTApply.setOnMouseClicked( event-> {	handleBtnPtApplyAction(event);	});
		
		//PT날짜 고르는 창의 테이블 뷰 컬럼 초기화
		//tablePtDatePicInitialize();
//---------------------------------------------------------------------------------------------	
		
		//회원권 신청에서 1개월의 8만원 버튼을 눌렀을 때의 이벤트 처리
		btnOne.setOnAction(event -> {System.out.println(memberLogin);});
//---------------------------------------------------------------------------------------------
		
		//회원정보 회원정보 수정 버튼 이벤트 등록 및 핸들러
		btnMemChange.setOnAction( event-> {	handleBtnMemChangeAction(event);});
		
		//회원정보 회원권,pt잔여 횟수 금액 테이블
		tableMemInfoInitialize();
//---------------------------------------------------------------------------------------------		

		//운동정보에서 버튼을 누르면 팝업창이 뜨고 그 운동의 동영상이 나오게 이벤트 처리
		btnChestPress.setOnAction( event-> { handleBtnChestPressAction(event);});
		//btnSeatedDip.setOnAction(event -> an	);
	}// end of initialize
	
	
	//회원정보 회원권,pt잔여 횟수 금액 테이블
	private void tableMemInfoInitialize() {
		
		/*TableColumn colRegDay = new TableColumn("등록일");
		colRegDay.setPrefWidth(100);
		colRegDay.setStyle("-fx-allignment: CENTER");
		colRegDay.setCellValueFactory(new PropertyValueFactory<>("created_at"));

		TableColumn colTitle = new TableColumn("만료일");
		colTitle.setPrefWidth(250);
		colTitle.setStyle("-fx-allignment: CENTER");
		colTitle.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn colContents = new TableColumn("내용");
		colContents.setPrefWidth(250);
		colContents.setStyle("-fx-allignment: CENTER");
		colContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

		tableMemInfo.getColumns().addAll(colRegDay, colTitle, colContents);
		tableMemInfo.setItems(obsListNo);*/
		
	}


	//pt테이블에서 트레이너를 선택하고 신청을 누르면 창이 뜨고 그 트레이너의 그 날짜의 일정을 보여줌
	private void handleBtnPtApplyAction(Event event) {
		int trainerSelect =0;
		
		trainerSelect=tablePT.getSelectionModel().getSelectedIndex();
		if(trainerSelect>=0) {
			Parent root;
			ObservableList<PT> obsPTdate = FXCollections.observableArrayList();
			
			try {
		
				root=FXMLLoader.load(getClass().getResource("/view/ptDatePick.fxml"));
				Scene scene = new Scene(root);
				Stage ptDate = new Stage(StageStyle.UTILITY);
		
				ptDate.initModality(Modality.WINDOW_MODAL);
				ptDate.initOwner(stage);
				ptDate.setScene(scene);
				ptDate.setResizable(false);
				ptDate.setTitle("PT 날짜 선택창");
				ptDate.showAndWait();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("PT 날짜 선택창에 문제 발생");
				alert.setHeaderText("PT 날짜 선택창 점검 하세요");
				alert.setContentText("다음에는 주의하세요"+e.getMessage());
				alert.showAndWait();
			} 
			
		}else {
			AlertUtill.showInformationAlert("트레이너선택", "트레이너를 선택하세요", "트레이너를 선택하세요");
		}

	}

	//DB의 question테이블에서 데이터를 가져와 관리자 문의 테이블에 넣음 
	private void questionTotalList() {
		QuestionDAO qDAO = new QuestionDAO();
		ArrayList<QuestionMember> arrayListQM = qDAO.getTotalList();
		if(arrayListQM ==null) {
			return;
		}
 		for(int i=0; i<arrayListQM.size(); i++) {
 			QuestionMember qm = arrayListQM.get(i);
 			obsList.add(qm);
 		}

	}

	//관리자 문의 테이블을 더블 클릭하면 창이 나오게하는 이벤트 및 핸들러
	private void handleTableViewQuestionPressAction(Event event) {
		tableViewQuestionSelectedIndex=tableQuestion.getSelectionModel().getSelectedIndex();
		
	}
	
	//db에서 공지사항 내용을 가져옴 
	private void noticeTotalList() {
		NoticeDAO ntDAO = new NoticeDAO();
		ArrayList<Notice> arrayList = ntDAO.getTotalList();
		if(arrayList ==null) {
			return;
		}
 		for(int i=0; i<arrayList.size(); i++) {
 			Notice nt = arrayList.get(i);
 			obsListNo.add(nt);
 		}
	}
	
	//운동정보에서 버튼을 누르면 팝업창이 뜨고 그 운동의 동영상이 나오게 이벤트 처리
	private void handleBtnChestPressAction(ActionEvent event) {
		Parent root;
		
			try {
				root=FXMLLoader.load(getClass().getResource("/view/tipForWork.fxml"));
				Scene scene = new Scene(root);
				Stage WorkOut = new Stage(StageStyle.UTILITY);
				
				//Button btnClose = (Button) scene.lookup("#btnClose");
				//btnClose.setOnAction(e-> {WorkOut.close();});
				
				WorkOut.initModality(Modality.WINDOW_MODAL);
				WorkOut.initOwner(stage);
				WorkOut.setScene(scene);
				WorkOut.setResizable(false);
				WorkOut.setTitle("운동정보창");
				WorkOut.showAndWait();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("운동정보 등록에 문제 발생");
				alert.setHeaderText("공지사항 등록 점검 하세요");
				alert.setContentText("다음에는 주의하세요"+e.getMessage());
				alert.showAndWait();
			}

	}
	//db 멤버에서 트레이너의 데이터를 가져와서 화면에 띄운다 
	private void ptTotalList() {
		MemberDAO memberDAO = new MemberDAO();
		ArrayList<Member> arrayList = memberDAO.findTrainer();

		if(arrayList ==null) {
			return;
		}
 		for(int i=0; i<arrayList.size(); i++) {
 			Member m = arrayList.get(i);
 			obsListPT.add(m);
 		}
		
	}

	//회원정보 회원정보 수정 버튼 이벤트 등록 및 핸들러
	private void handleBtnMemChangeAction(ActionEvent event) {
		Parent root;

		try {
			root = FXMLLoader.load(getClass().getResource("/view/member_info_popup.fxml"));
			Scene scene = new Scene(root);
			Stage chPopStage = new Stage(StageStyle.UTILITY);

			Button btnChaPopRegi = (Button) scene.lookup("#btnChaPopRegi");
			Button btnChaPopCancel = (Button) scene.lookup("#btnChaPopCancel");

			TextField txtChaPopId = (TextField) scene.lookup("#txtChaPopId");
			PasswordField pwfChaPopPw = (PasswordField) scene.lookup("#pwfChaPopPw");
			TextField txtChaPopName = (TextField) scene.lookup("#txtChaPopName");
			TextField txtChaPopPhoneNo = (TextField) scene.lookup("#txtChaPopPhoneNo");
			TextField txtChaPopBirth = (TextField) scene.lookup("#txtChaPopBirth");
			TextField txtChaPopAddr = (TextField) scene.lookup("#txtChaPopAddr");
			TextField txtChaPopMail = (TextField) scene.lookup("#txtChaPopMail");
			
			//텍스트 필드 기본값 세팅
			txtChaPopId.setText(lblId.getText());
			pwfChaPopPw.setText(memberLogin.getPw().toString());
			txtChaPopName.setText(lblName.getText());
			txtChaPopPhoneNo.setText(lblPhoneNo.getText());
			txtChaPopBirth.setText(lblBirth.getText());
			txtChaPopAddr.setText(lblAddress.getText());
			txtChaPopMail.setText(lblMail.getText());
			//변경된 내용을 등록하는 버튼 이벤트 
			btnChaPopRegi.setOnAction( e-> {
		
					chPopStage.close();
	
					lblId.setText(txtChaPopId.getText());
					lblName.setText(txtChaPopName.getText());
					lblPhoneNo.setText(txtChaPopPhoneNo.getText());
					lblBirth.setText(txtChaPopBirth.getText());
					lblAddress.setText(txtChaPopAddr.getText());
					lblMail.setText(txtChaPopMail.getText());
					
				Member member = new Member(txtChaPopId.getText(), pwfChaPopPw.getText(),txtChaPopName.getText(),
						txtChaPopPhoneNo.getText(), txtChaPopBirth.getText(), txtChaPopAddr.getText()
						,txtChaPopMail.getText());
				MemberDAO mD = new MemberDAO();
				int count = mD.updateMemInfo(member);
	
			});

			btnChaPopCancel.setOnAction( e-> {chPopStage.close();});

			chPopStage.initModality(Modality.WINDOW_MODAL);
			chPopStage.initOwner(stage);
			chPopStage.setScene(scene);
			chPopStage.setResizable(false);
			chPopStage.setTitle("문의 창");
			chPopStage.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("공지사항 등록에 문제 발생");
			alert.setHeaderText("공지사항 등록 점검 하세요");
			alert.setContentText("다음에는 주의하세요");
			alert.showAndWait();
		}

	}
	
	//회원정보를 회원정보 창에 띄우는 것 
	private void memberInfoInitialize() {

		lblId.setText(LoginController.memberLogin.getId());
		lblName.setText(LoginController.memberLogin.getName());
		lblPhoneNo.setText(LoginController.memberLogin.getPhone());
		lblBirth.setText(LoginController.memberLogin.getBirth());
		lblAddress.setText(LoginController.memberLogin.getAddress());
		lblMail.setText(LoginController.memberLogin.getMail());
		
	}

	//memeber 테이블에서 트레이너의 이름과 성별 멘트를 가져와서 테이블에 띄운다 
	private void tablePTColumnInitialize() {
		//멤버에서 트레이너의 이름 성별 멘트 직책을 가져온다 
		obsListPT = FXCollections.observableArrayList();
		
		tablePT.setItems(obsListPT);
		
		TableColumn colName =  new TableColumn("이름");
		colName.setPrefWidth(80);
		colName.setStyle("-fx-allignment: CENTER");
		colName.setCellValueFactory(new PropertyValueFactory("name"));

		TableColumn colGend= new TableColumn("성별");
		colGend.setPrefWidth(110);
		colGend.setStyle("-fx-allignment: CENTER");
		colGend.setCellValueFactory(new PropertyValueFactory("gender"));

		TableColumn colPositon= new TableColumn("직책");
		colPositon.setPrefWidth(140);
		colPositon.setStyle("-fx-allignment: CENTER");
		colPositon.setCellValueFactory(new PropertyValueFactory("position"));
		
	    TableColumn colMent= new TableColumn("멘트");
	    colMent.setPrefWidth(120);
	    colMent.setStyle("-fx-allignment: CENTER");
	    colMent.setCellValueFactory(new PropertyValueFactory("ment"));

		tablePT.getColumns().addAll(colName, colGend, colPositon, colMent);
		
		tablePT.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Member>() {

			@Override
			public void changed(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
				String imgName= newValue.getId();
				imgPt.setImage(new Image(getClass().getResource("/image/"+imgName+".jpg").toString()));
			}//
		});
	
	}

	//공지사항의 등록버튼 이벤트 등록 및 핸들러
	private void handleBtnNoRegist(ActionEvent event) {
		Parent root;

		try {
			root = FXMLLoader.load(getClass().getResource("/view/member_notice_regist.fxml"));
			Scene scene = new Scene(root);
			Stage noticeStage = new Stage(StageStyle.UTILITY);
			Button btnNoRegi = (Button) scene.lookup("#btnNoRegi");
			Button btnNoCancel = (Button) scene.lookup("#btnNoCancel");

			TextField txtNoTitle = (TextField) scene.lookup("#txtNoTitle");
			TextArea txtNoContents = (TextArea) scene.lookup("#txtNoContents");

			btnNoRegi.setOnAction( e-> {
						
				Notice notice = new Notice(txtNoTitle.getText().trim(),txtNoContents.getText().trim());

					noticeStage.close();
					System.out.println(notice);
//					obsListNo.add(notice);

			});

			btnNoCancel.setOnAction( e-> {noticeStage.close();});

			noticeStage.initModality(Modality.WINDOW_MODAL);
			noticeStage.initOwner(stage);
			noticeStage.setScene(scene);
			noticeStage.setResizable(false);
			noticeStage.setTitle("문의 창");
			noticeStage.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("공지사항 등록에 문제 발생");
			alert.setHeaderText("공지사항 등록 점검 하세요");
			alert.setContentText("다음에는 주의하세요");
			alert.showAndWait();
		}

	}

	//공지사항의 검색 버튼 이벤트 등록 및 핸들러
	private void handleBtnNoSearch(ActionEvent event) {
		try {
		if(txtNoSearch.getText().trim().equals("")) throw new Exception();

			for(Notice nt : obsListNo) {
				if(nt.getTitle().equals(txtNoSearch.getText())) {
					tableNotice.getSelectionModel().select(nt);
				}
			}
		} catch(Exception e) {
			Alert alert =new Alert(AlertType.ERROR);
			alert.setTitle("검색 단어에 문제 발생");
			alert.setHeaderText("검색 단어 점검 하세요");
			alert.setContentText("다음에는 주의하세요");
			alert.showAndWait();
		}

	}


	//공지사항의 테이블 뷰 컬럼 초기화 Notice 필드와 연결
	private void tableNoticeColumnInitialize() {
		TableColumn colNo = new TableColumn("번호");
		colNo.setPrefWidth(100);
		colNo.setStyle("-fx-allignment: CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

		TableColumn colTitle = new TableColumn("제목");
		colTitle.setPrefWidth(250);
		colTitle.setStyle("-fx-allignment: CENTER");
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn colContents = new TableColumn("내용");
		colContents.setPrefWidth(250);
		colContents.setStyle("-fx-allignment: CENTER");
		colContents.setCellValueFactory(new PropertyValueFactory<>("contents"));

		tableNotice.getColumns().addAll(colNo, colTitle, colContents);
		tableNotice.setItems(obsListNo);

	}

	//관리자문의 검색 버튼 이벤트 등록 및 핸들러
	private void handleBtnSearchAction(ActionEvent event) {

		try {
		if(txtSearch.getText().trim().equals("")) throw new Exception();

		for (QuestionMember qm : obsList) {
			if(qm.getTitle().equals(txtSearch.getText())) {
				tableQuestion.getSelectionModel().select(qm);
			}
		}

		}catch(Exception e) {
			Alert alert =new Alert(AlertType.ERROR);
			alert.setTitle("검색 입력문제 발생");
			alert.setHeaderText("검색 단어 점검 하세요");
			alert.setContentText("다음에는 주의하세요");
			alert.showAndWait();
		}
	}

	//관리자문의 테이블 설정
	private void tableQuestionColumnInitialize() {

		TableColumn colNo = new TableColumn("번호");
		colNo.setPrefWidth(50);
		colNo.setStyle("-fx-alignment: CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colTitle = new TableColumn("제목");
		colTitle.setPrefWidth(200);
		colTitle.setStyle("-fx-alignment: CENTER");
		colTitle.setCellValueFactory(new PropertyValueFactory("title"));

		TableColumn colContents = new TableColumn("내용");
		colContents.setPrefWidth(200);
		colContents.setStyle("-fx-alignment: CENTER");
		colContents.setCellValueFactory(new PropertyValueFactory("contents"));

		TableColumn colCreateBy = new TableColumn("작성자");
		colCreateBy.setPrefWidth(200);
		colCreateBy.setStyle("-fx-alignment: CENTER");
		colCreateBy.setCellValueFactory(new PropertyValueFactory("created_by"));

		TableColumn colCreateAt = new TableColumn("작성일");
		colCreateAt.setPrefWidth(200);
		colCreateAt.setStyle("-fx-alignment: CENTER");
		colCreateAt.setCellValueFactory(new PropertyValueFactory("created_at"));

		tableQuestion.getColumns().addAll(colNo, colTitle, colContents,
				colCreateBy, colCreateAt);
		tableQuestion.setItems(obsList);

	}

	// 관리자문의 글쓰기 버튼 이벤트
	private void handleBtnQuestionWriteAction(ActionEvent event) {
		Parent root;

		try {
			root = FXMLLoader.load(getClass().getResource("/view/member_qustion_regist.fxml"));
			Scene scene = new Scene(root);
			Stage queStage = new Stage(StageStyle.UTILITY);
			Button btnCancel = (Button) scene.lookup("#btnCancel");
			Button btnRegist = (Button) scene.lookup("#btnRegist");
			Button btnList = (Button) scene.lookup("#btnList");
			TextField txtTitle = (TextField) scene.lookup("#txtTitle");
			TextArea txtContents = (TextArea) scene.lookup("#txtContents");

			btnRegist.setOnAction( e-> {
				
				QuestionDAO questionDAO = new QuestionDAO();
				QuestionMember qm = new QuestionMember(txtTitle.getText().trim(),txtContents.getText().trim());
				int returnValue = questionDAO.questionRegist(qm, memberLogin);

				
					queStage.close();
					System.out.println("잘등록?獰?");
					obsList.add(qm);

			});

			btnCancel.setOnAction( e-> {
				queStage.close();

			});

			queStage.initModality(Modality.WINDOW_MODAL);
			queStage.initOwner(stage);
			queStage.setScene(scene);
			queStage.setResizable(false);
			queStage.setTitle("문의 창");
			queStage.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러발생");
		}

	}
	
	//관리자 문의 테이블을 더블 클릭하면 창이 나오게하는 이벤트 및 핸들러
	private void handleQuestionDoubleClick(MouseEvent event) {
		Parent root;
		try {
			if (event.getClickCount() != 2) {
				return;
			
			}
			root = FXMLLoader.load(getClass().getResource("/view/member_question_doubleclick.fxml"));
			Stage queStage = new Stage(StageStyle.UTILITY);
			queStage.initModality(Modality.WINDOW_MODAL);
			queStage.initOwner(stage);
			queStage.setTitle("문의 내용보기");
			Button btnCancel = (Button) root.lookup("#btnCancel");
			TextField txtTitle = (TextField) root.lookup("#txtTitle");
			TextArea txtContents = (TextArea) root.lookup("#txtContents");
			Scene scene = new Scene(root);
			queStage.setScene(scene);
			queStage.show();
			//int num=tableQuestion.getSelectionModel().getSelectedItem().getT();
			String title = tableQuestion.getSelectionModel().getSelectedItem().getTitle();
			QuestionDAO qd=new QuestionDAO();
			txtTitle.setText(obsList.get(tableViewQuestionSelectedIndex).getTitle());
			txtContents.setText(obsList.get(tableViewQuestionSelectedIndex).getContents());
			btnCancel.setOnAction(event2 -> {
				queStage.close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
