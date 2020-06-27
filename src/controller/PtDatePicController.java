package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Member;
import model.PT;
import model.PTMember;

public class PtDatePicController implements Initializable {
	
	@FXML private TextField ptDate10;
	@FXML private TextField ptDate11;
	@FXML private TextField ptDate12;
	@FXML private TextField ptDate13;
	@FXML private TextField ptDate14;
	@FXML private TextField ptDate15;
	@FXML private TextField ptDate16;
	
	@FXML private TextField ptDate20;
	@FXML private TextField ptDate21;
	@FXML private TextField ptDate22;
	@FXML private TextField ptDate23;
	@FXML private TextField ptDate24;
	@FXML private TextField ptDate25;
	@FXML private TextField ptDate26;
	
	@FXML private TextField ptDate30;
	@FXML private TextField ptDate31;
	@FXML private TextField ptDate32;
	@FXML private TextField ptDate33;
	@FXML private TextField ptDate34;
	@FXML private TextField ptDate35;
	@FXML private TextField ptDate36;
	
	@FXML private TextField ptDate40;
	@FXML private TextField ptDate41;
	@FXML private TextField ptDate42;
	@FXML private TextField ptDate43;
	@FXML private TextField ptDate44;
	@FXML private TextField ptDate45;
	@FXML private TextField ptDate46;
	
	@FXML private TextField ptDate50;
	@FXML private TextField ptDate51;
	@FXML private TextField ptDate52;
	@FXML private TextField ptDate53;
	@FXML private TextField ptDate54;
	@FXML private TextField ptDate55;
	@FXML private TextField ptDate56;
	
	@FXML private TextField ptDate60;
	@FXML private TextField ptDate61;
	@FXML private TextField ptDate62;
	@FXML private TextField ptDate63;
	@FXML private TextField ptDate64;
	@FXML private TextField ptDate65;
	@FXML private TextField ptDate66;

	@FXML private TableView<PT> ptTraTable;
	ObservableList<PT>obsPTdate;
	private Integer jj=0;
	
	//예약하기 텍스트 필드  
	@FXML private TextField ptDateTxFRegi; //ptDatePick.fxml의 예약 부분 
	
	TextField [] ptDateArray = new TextField[42];

	private LocalDateTime ldt = LocalDateTime.now();
	private Calendar cal = Calendar.getInstance();

	//달력의 현재 월을 표시해주는 라벨
	@FXML private Label labelCalenderMonth;

	//선택한 트레이너의 정보를 가지고 있는 변수
	public static Member trainerInfo = null;

	//달력 넘기는 버튼
	@FXML private Button btnLastMonth;
	@FXML private Button btnNextMonth;
	
	//시간 선택하는 콤보박스
	@FXML private ComboBox cmbPTTime;
	 private ObservableList<PTMember> PTTime = FXCollections.observableArrayList();
	 
	//달력에서 선택한 날짜를 가지고 있는 변수
	private String selectDate = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//PT날짜 고르는 창의 테이블 뷰 컬럼 초기화 
		tablePtDatePicInitialize();
		
		//예약버튼을 누르면 예약되게 하는 이벤트
		ptDateTxFRegi.setOnMouseClicked( event-> handleptDateTxfRegiAction());
		
		//달력 날짜 설정 초기화 
		initCalender();
		
		//선택한 트레이너의 스케줄을 테이블로  가져오기
		trainerScheduleTotalList();

		//달력 버튼 클릭시 이벤트
		btnNextMonth.setOnAction(e -> btnMonthAction(e));
		btnLastMonth.setOnAction(e -> btnMonthAction(e));
		
	}//end of initialize
	
	 private void comboTimeInitiallize(String date, Member trainerInfo) {
		 ObservableList<String> obsList = FXCollections.observableArrayList();
		 cmbPTTime.setItems(obsList);
		 
		 cmbPTTime.getItems().add("10:00");
		 cmbPTTime.getItems().add("11:00");
		 cmbPTTime.getItems().add("13:00");
		 cmbPTTime.getItems().add("14:00");
		 cmbPTTime.getItems().add("15:00");
		 cmbPTTime.getItems().add("16:00");
		 cmbPTTime.getItems().add("17:00");
		 cmbPTTime.getItems().add("18:00");

		 PTTime = PtDAO.getPTTimeCombo(date, trainerInfo.getId());

		 for(int i= 0;i < PTTime.size();i++) {	
			 cmbPTTime.getItems().remove(PTTime.get(i).getTime());
		 }


    }

	//달력의 달을 바꾸어주는 버튼 클릭시 사용하는 함수
	private void btnMonthAction(ActionEvent event) {
		//클릭한 버튼의 id 값에 따라서 실행하는 실행문을 다르게 해줍니다.
		switch (((Button)event.getSource()).getId()) {
			case "btnNextMonth": ldt = ldt.plusMonths(1); break;
			case "btnLastMonth": ldt = ldt.minusMonths(1); break;
		}
		System.out.println(ldt);
		initCalender();
	}

	//선택한 트레이너의 스케줄을 테이블로  가져오기
		private void trainerScheduleTotalList() {
		
		
	}

		//달력에 날짜 설정 초기화
		private void initCalender() {
		ptDateArray = new TextField[] { ptDate10, ptDate11, ptDate12, ptDate13, ptDate14, ptDate15, ptDate16,
			 ptDate20, ptDate21, ptDate22, ptDate23, ptDate24, ptDate25, ptDate26,
			 ptDate30, ptDate31, ptDate32, ptDate33, ptDate34, ptDate35, ptDate36,
			 ptDate40, ptDate41, ptDate42, ptDate43, ptDate44, ptDate45, ptDate46,
			 ptDate50, ptDate51, ptDate52, ptDate53, ptDate54, ptDate55, ptDate56,
			 ptDate60, ptDate61, ptDate62, ptDate63, ptDate64, ptDate65, ptDate66
		};
		
			 	final int year = ldt.getYear();
				final int month = ldt.getMonthValue();
				int day = ldt.getDayOfMonth();

				//달력의 현재월과 연도를 보여줍니다.
				labelCalenderMonth.setText(year+"년 "+month +"월");

				cal.set(year, month - 1, 1);
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				int firstday = 0;
				int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			switch (dayOfWeek) {
				case 1:
					firstday = 0;
					break;
				case 2:
					firstday = 1;
					break;
				case 3:
					firstday = 2;
					break;
				case 4:
					firstday = 3;
					break;
				case 5:
					firstday = 4;
					break;
				case 6:
					firstday = 5;
					break;
				case 7:
					firstday = 6;
					break;
				}
				int j = 0;

				for (int i = 0; i < 42; i++) {

					if (i >= firstday && i < firstday + lastDay) {
						j++;
						ptDateArray[i].setText("" + j);
						ptDateArray[i].setStyle("-fx-cursor: hand; ");

						final int ii = i;
						ptDateArray[i].setOnMouseClicked(e -> {
							
							String month1 = "";
							if (month < 10) {
								month1 = "0" + month;
							}
							String day1 = "";
							if (Integer.parseInt(ptDateArray[ii].getText()) < 10) {
								day1 = "0" + ptDateArray[ii].getText();
							} else {
								day1 = ptDateArray[ii].getText();

							}

							String date = year + "-" + month1 + "-" + day1;
							selectDate = date;

							if (e.getClickCount() == 1) {
								ObservableList<PT> dbClsByDateList = PtDAO.getTrainerPTDateList(date, trainerInfo);
								comboTimeInitiallize(date, trainerInfo);
								ptTraTable.setItems(dbClsByDateList);

								if(jj!=ii){
									ptDateArray[ii].setStyle("-fx-background-color: orange");
									ptDateArray[jj].setStyle("-fx-background-color: white");
								}
								jj=ii;
							}
						});
					}else {
						ptDateArray[i].setText("");
					}


					
				}
	}
		
		//예약버튼을 누르면 예약되게 하는 이벤트
		private void handleptDateTxfRegiAction() {
			PtDAO PTDAO = new PtDAO();
			PTDAO.reservationPT(MemberMainController.memberLogin, trainerInfo, selectDate,cmbPTTime.getSelectionModel().getSelectedItem().toString());
		}

		//PT날짜 고르는 창의 테이블 뷰 컬럼 초기화 
		private void tablePtDatePicInitialize() {
			obsPTdate = FXCollections.observableArrayList();
			TableColumn colPtDate = new TableColumn("날짜");
			colPtDate.setPrefWidth(120);
			colPtDate.setStyle("-fx-alignment: CENTER");
			colPtDate.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn colTitle = new TableColumn("시간");
			colTitle.setPrefWidth(100);
			colTitle.setStyle("-fx-alignment: CENTER");
			colTitle.setCellValueFactory(new PropertyValueFactory<>("time"));

			TableColumn colContents = new TableColumn("회원아이디");
			colContents.setPrefWidth(120);
			colContents.setStyle("-fx-alignment: CENTER");
			colContents.setCellValueFactory(new PropertyValueFactory<>("member_id"));

			ptTraTable.getColumns().addAll(colPtDate,colTitle,colContents);
			ptTraTable.setItems(obsPTdate);
		
		}
}
