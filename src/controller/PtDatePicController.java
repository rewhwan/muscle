package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PT;

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
	
	@FXML private TextField ptDateTxFRegi; //ptDatePick.fxml의 예약 부분 

	TextField [] ptDateArray = new TextField[42];

	private LocalDateTime ldt = LocalDateTime.now();
	private Calendar cal = Calendar.getInstance();
	
	//테스트용 버튼  
	@FXML private Button btnTest1;
	@FXML private Button btnTest2;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//PT날짜 고르는 창의 테이블 뷰 컬럼 초기화 
		tablePtDatePicInitialize();
		
		//예약버튼을 누르면 예약되게 하는 이벤트
		ptDateTxFRegi.setOnMouseClicked( event-> {handleTxFRegi(event);});
		
		//달력 날짜 설정 초기화 
		initCalender();
		
		//선택한 트레이너의 스케줄을 테이블로  가져오기
		trainerScheduleTotalList();
		
		btnTest1.setOnAction(e ->{
			ldt = ldt.plusMonths(1);
			System.out.println(ldt);
			initCalender();
		});
		
		
	}//end of initialize
	
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

				for (int i = firstday; i < lastDay + firstday; i++) {
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
						
						if (e.getClickCount() == 1) {
							ObservableList<PT> dbClsByDateList = PtDAO.selectClassDataByDate(date);
							ptTraTable.setItems(dbClsByDateList);					
							
							if(jj!=ii){						
								ptDateArray[ii].setStyle("-fx-background-color: orange");		
								ptDateArray[jj].setStyle("-fx-background-color: white");								
							}					
							jj=ii;
						}
					});
				}
	}
		
		//예약버튼을 누르면 예약되게 하는 이벤트
		private void handleTxFRegi(Event event) {
			PT pt = ptTraTable.getSelectionModel().getSelectedItem();
	}

		//PT날짜 고르는 창의 테이블 뷰 컬럼 초기화 
		private void tablePtDatePicInitialize() {
			obsPTdate = FXCollections.observableArrayList();
			TableColumn colPtDate = new TableColumn("날짜");
			colPtDate.setPrefWidth(100);
			colPtDate.setStyle("-fx-allignment: CENTER");
			colPtDate.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn colTitle = new TableColumn("시간");
			colTitle.setPrefWidth(250);
			colTitle.setStyle("-fx-allignment: CENTER");
			colTitle.setCellValueFactory(new PropertyValueFactory<>("time"));

			TableColumn colContents = new TableColumn("트레이너");
			colContents.setPrefWidth(250);
			colContents.setStyle("-fx-allignment: CENTER");
			colContents.setCellValueFactory(new PropertyValueFactory<>("trainer_id"));

			/*tableNotice.getColumns().addAll(colNo, colTitle, colContents);
			tableNotice.setItems(obsListNo);*/
			ptTraTable.getColumns().addAll(colPtDate,colTitle,colContents);
			ptTraTable.setItems(obsPTdate);
		
		}
}
