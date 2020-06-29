package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Member;

import model.QuestionMember;

public class QuestionDAO {
	
	//DB연결에 필요한 것들 전역변수로 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//쿼리문 결과값 반환을 위한 전역변수 선언
	ArrayList<QuestionMember> arrayList = null;
	int returnValue = 0;

	//DB에서 질문 내용을 가져오는 함수 
	public ArrayList<QuestionMember> getTotalList(){
		ArrayList<QuestionMember> arrayListQM =null;
	
		try {
			con = DBUtill.getConnection();
			if(con!=null) {
				System.out.println("QuestionDAO.getTotalList : DB 연결 성공");
			}else {
				System.out.println("QuestionDAO.getTotalList : DB 연결 실패");
			}
			String query = "select no, title, contents, created_by ,DATE_FORMAT(created_at,\"%Y-%m-%d %H:%i:%S\") AS created_at from question";
			pstmt= con.prepareStatement(query);
			rs= pstmt.executeQuery();
			
			arrayListQM = new ArrayList<QuestionMember>();
			while(rs.next()) {
				QuestionMember qm = new QuestionMember(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5));
				arrayListQM.add(qm);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("QuestionDAO getTotalList 점검요망");
			alert.setHeaderText("QuestionDAO getTotalList에 문제 발생");
			alert.setContentText("문제사항" + e.getMessage());
			alert.showAndWait();
		}finally {
			try {
				if(rs!=null) rs.close();
				if (pstmt != null) 	pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("QuestionDAO.getTotalList:" + e.getMessage());
			}
		}
		return arrayListQM;
	}

	//DB에 질문을 등록하는 함수
	public int questionRegist(QuestionMember questionMember, Member member) {
		
		try {
			
			con = DBUtill.getConnection();
			
			if(con != null) {
				System.out.println("QuestionDAO.questionRegist : DB 연결 성공");
			}else {
				System.out.println("QuestionDAO.questionRegist : DB 연결 실패");
			}
			
			String query = "INSERT INTO question VALUES (null,?,?,?,NOW())";
			
			//쿼리문 실행 준비
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, questionMember.getTitle());
			pstmt.setString(2, questionMember.getContents());
			pstmt.setString(3, member.getId());
			
			returnValue = pstmt.executeUpdate();
			
			if(returnValue != 0) {
				AlertUtill.showInformationAlert("질문 등록 성공", "질문등록에 성공하였습니다.", "");
			}else {
				throw new Exception(questionMember.getTitle()+" 질문 삽입 문제 있음");
			}
			
		} catch (Exception e) {
			AlertUtill.showErrorAlert("삽입 점검요망", "삽입 문제발생", "문제사항"+e.getMessage());
		} finally {
			try {
				if(pstmt != null) {pstmt.close();}
				if(con != null) {con.close();}
			}catch (SQLException e) {
				System.out.println("QuestionDAO.questionRegist :"+e.getMessage());
			}
		
		}
	
		return returnValue;
	}
	
	//내용보기
	public ObservableList<QuestionMember> getQuestionView(String title) {
		ObservableList<QuestionMember> list = FXCollections.observableArrayList();
		String dml = "select * from medicinetbl where no=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		QuestionMember qm = null;

		try {
			con = DBUtill.getConnection();
			pstmt = con.prepareStatement(dml);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qm = new QuestionMember(rs.getString(1), rs.getString(2));
				list.add(qm);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	
	//답변이 등록되지 않은 질문들의 목록을 가져옵니다.
	public ArrayList<QuestionMember> getQuestionNonAnswer () {

		ArrayList<QuestionMember> arrayList = new ArrayList<>();

		try {
			con = DBUtill.getConnection();
			if(con != null) {
				System.out.println("QuestionDAO.getQuestionNonAnswer : DB 연결 성공");
			}else {
				System.out.println("QuestionDAO.getQuestionNonAnswer : DB 연결 실패");
			}

			//쿼리문
			String query = "SELECT Q.no, Q.title, Q.contents, Q.created_by, DATE_FORMAT(Q.created_at,\"%Y-%m-%d %H:%i:%S\") AS created_at  FROM question AS Q LEFT JOIN answer AS A ON no = qustion_no WHERE qustion_no IS NULL";
			//쿼리문 준비
			pstmt= con.prepareStatement(query);
			//결과값 반환
			rs= pstmt.executeQuery();

			while (rs.next()) {
				QuestionMember question = new QuestionMember(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				arrayList.add(question);
			}

		} catch (Exception e) {
			AlertUtill.showWarningAlert("QuestionDAO getQuestionNonAnswer 점검요망", "QuestionDAO getQuestionNonAnswer 문제발생", "문제사항 " + e.getMessage());
		} finally {
			try {
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("QuestionDAO getQuestionNonAnswer : "+e.getMessage());
			}
		}

		return arrayList;
	}
	
}
