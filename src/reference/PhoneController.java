package reference;

import java.net.URL;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.Image.*;
import javafx.scene.image.ImageView;

public class PhoneController implements Initializable {
	// 1. 멤버 변수를 결정
	@FXML private ListView livPhoneName;
	@FXML private TableView tblPhoneName;
	@FXML private ImageView imgPhone;
	@FXML private Button btnOk;
	@FXML private Button btnCancel;
	// 2.디폴트 생성자
	public PhoneController() {
		System.out.println("I'm RootController Constructor");
	}
	// 3.getter/setter 없음
	// 4.멤버 함수 없음//// 멤버함수에는 인스턴스 정적멤버 핸들러가 있다.
	// 5.오버라이드
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * ObservableList<String> obsList=FXCollections.observableArrayList();
		 * obsList.add("갤럭시S1"); obsList.add("갤럭시S2"); obsList.add("갤럭시S3");
		 * obsList.add("갤럭시S4"); obsList.add("갤럭시S5"); obsList.add("갤럭시S6");
		 * obsList.add("갤럭시S7");
		 * 
		 * livPhoneName.setItems(obsList);
		 */
		livPhoneName.setItems(
				FXCollections.observableArrayList("갤럭시S1", "갤럭시S2", "갤럭시S3", "갤럭시S4", "갤럭시S5", "갤럭시S6", "갤럭시S7"));
		
		//2.테이블뷰 내용을 입력한다 
		//-> 1.클래스를 만든다 그래서 Phone클래스 만들었음 
		//-> 2. 	ObservableList 진행하고 객체를 만들어 입력한다 
		//-> 3. 테이블 뷰에 등록한다
		ObservableList<Phone> obsList= FXCollections.observableArrayList();
		obsList.add(new Phone("갤럭시S1", "phone01.png"));
		obsList.add(new Phone("갤럭시S2", "phone02.png"));
		obsList.add(new Phone("갤럭시S3", "phone03.png"));
		obsList.add(new Phone("갤럭시S4", "phone04.png"));
		obsList.add(new Phone("갤럭시S5", "phone05.png"));
		obsList.add(new Phone("갤럭시S6", "phone06.png"));
		obsList.add(new Phone("갤럭시S7", "phone07.png"));
		tblPhoneName.setItems(obsList); // 테이블 뷰에 들어가도록 매치시켜야된다 
		//3. table 컬럼 1과 table 컬럼2를 Phone 객체 필드 명과 연결해야된다
		TableColumn tcSmartPhone = (TableColumn) tblPhoneName.getColumns().get(0); // 테이블 뷰에서 스마트폰을 가져온다 
		tcSmartPhone.setCellValueFactory(new PropertyValueFactory("smartPhone"));
		tcSmartPhone.setStyle("-fx-alignment: CENTER");
		TableColumn tcImage= (TableColumn) tblPhoneName.getColumns().get(1);
		tcImage.setCellValueFactory(new PropertyValueFactory("image"));
		tcImage.setStyle("-fx-alignment: CENTER");
		//4.리스트 뷰를 클릭하면 이벤트 등록 property 속성을 사용한다 
		livPhoneName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tblPhoneName.getSelectionModel().select(newValue.intValue());
				tblPhoneName.scrollTo(newValue.intValue());
			}
		});
		//5. 테이블 뷰가 바뀌면 이미지도 바뀌게 하는 이벤트를 등록한다 property 속성을 사용한다 
		tblPhoneName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Phone>() {

			@Override
			public void changed(ObservableValue<? extends Phone> observable, Phone oldValue, Phone newValue) {
				String imageName = newValue.getImage();
				imgPhone.setImage(new Image(getClass().getResource("images/"+imageName).toString()));
			}
		});
		//6.취소 버튼을 누르면 닫기 기능 이벤트
		btnCancel.setOnAction(event -> Platform.exit());
		//7.확인 버튼을 누르면 선택된 정보 가져오기
		btnOk.setOnAction(event -> handleBtnOkAction(event));
	}
	public void handleBtnOkAction(ActionEvent event) {
		String listName = livPhoneName.getSelectionModel().getSelectedItem().toString();
		Phone phone =(Phone) tblPhoneName.getSelectionModel().getSelectedItem();
		//String imageName = phone.getImage();
		String imageName = imgPhone.getImage().toString();
		System.out.println(listName);
		System.out.println(phone.toString());
		System.out.println(imageName);
		
	}
	

}
