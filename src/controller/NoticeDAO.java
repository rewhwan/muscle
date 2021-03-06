package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Member;
import model.Notice;


public class NoticeDAO {
	
	//DB연결에 필요한 것들 전역변수로 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//쿼리문 결과값 반환을 위한 전역변수 선언
	ArrayList<Member> arrayList = null;
	int returnValue = 0;


	//DB 내용을 가져오는 함수
	public ArrayList<Notice> getTotalList(){

		ArrayList<Notice> arrayList = null;
		
		try {
			 //DBUtill에 있는 정보로 DB에 접속합니다.
			con = DBUtill.getConnection();
			if(con != null) {
				System.out.println("NoticeDAO.getTotalList: DB 연결성공");
			} else {
				System.out.println("NoticeDAO.getTotalList: DB 연결 실패");
			}
			String query = "select no, title, contents, created_by , DATE_FORMAT(created_at,\"%Y-%m-%d %H:%i:%S\") AS created_at from notice ORDER BY no DESC";

			pstmt= con.prepareStatement(query);
			rs= pstmt.executeQuery();
			
			arrayList = new ArrayList<Notice>();
			while(rs.next()) {
				Notice nt = new Notice(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				arrayList.add(nt);
			}
			
		} catch (Exception e) {
			AlertUtill.showErrorAlert("NoticeDAO getTotalList 점검요망","NoticeDAO getTotalList에 문제 발생" ,"문제사항" + e.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if (pstmt != null) 	pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("NoticeDAO.getTotalList :" + e.getMessage());
			}
		}
		return arrayList;
	}

	//DB 공지사항 등록 함수
	public int noticeRegist (Notice notice, Member memberLogin) {

		try {
			con = DBUtill.getConnection();
			//DB연결 체크
			if(con != null) {
				System.out.println("NoticeDAO.noticeRegist: DB 연결성공");
			} else {
				System.out.println("NoticeDAO.noticeRegist: DB 연결 실패");
			}

			//쿼리문
			String query = "INSERT INTO notice VALUES (null,? , ? , ? ,now())";

			//쿼리문 준비
			pstmt = con.prepareStatement(query);
			//쿼리문 세팅
			pstmt.setString(1,notice.getTitle());
			pstmt.setString(2,notice.getContents());
			pstmt.setString(3,memberLogin.getId());

			//실행 쿼리문 갯수 반환
			returnValue = pstmt.executeUpdate();

		} catch (Exception e) {
			AlertUtill.showErrorAlert("NoticeDAO noticeRegist 점검요망","NoticeDAO noticeRegist 점검요망" ,"문제사항" + e.getMessage());
		} finally {
			try {
				if (pstmt != null) 	pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("NoticeDAO noticeRegist :" + e.getMessage());
			}
		}

		return returnValue;

	}

	//DB 공지사항 삭제 함수
	public int noticeDelete (Notice selectedNotice) {

		try {
			con = DBUtill.getConnection();
			//DB연결 체크
			if(con != null) {
				System.out.println("NoticeDAO.noticeDelete: DB 연결성공");
			} else {
				System.out.println("NoticeDAO.noticeDelete: DB 연결 실패");
			}

			//쿼리문
			String query = "DELETE FROM notice WHERE no = ?";

			//쿼리문 준비
			pstmt = con.prepareStatement(query);
			//쿼리문 세팅
			pstmt.setInt(1,selectedNotice.getNo());

			//실행 쿼리문 갯수 반환
			returnValue = pstmt.executeUpdate();
		} catch (Exception e) {
			AlertUtill.showErrorAlert("NoticeDAO noticeDelete 점검요망","NoticeDAO noticeDelete에 문제 발생" ,"문제사항 "+e.getMessage());
		} finally {
			try {
				if (pstmt != null) 	pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("NoticeDAO noticeDelete :" + e.getMessage());
			}
		}

		return returnValue;
	}

	//DB 공지사항 수정 함수
	public int noticeUpdate (Notice selectedNotice, String title, String contents) {
		try {
			con = DBUtill.getConnection();
			//DB연결 체크
			if(con != null) {
				System.out.println("NoticeDAO.noticeUpdate: DB 연결성공");
			} else {
				System.out.println("NoticeDAO.noticeUpdate: DB 연결 실패");
			}

			//쿼리문
			String query = "UPDATE notice SET title = ? , contents = ? WHERE no = ?";

			//쿼리문 준비
			pstmt = con.prepareStatement(query);
			//쿼리문 세팅
			pstmt.setString(1, title);
			pstmt.setString(2,contents);
			pstmt.setInt(3, selectedNotice.getNo());

			int returnValue = pstmt.executeUpdate();

			if(returnValue != 0) {
				AlertUtill.showInformationAlert("공지사항 수정 성공","공지사항 수정에 성공하였습니다.","확인버튼을 누르면 창을 닫습니다.");
			}else {
				throw new Exception(selectedNotice.getNo()+"번 수정 문제 있음");
			}

		} catch (Exception e) {
			// 경고창이 만들어짐
			AlertUtill.showWarningAlert("공지사항 수정 실패","공지사항 수정에 실패하였습니다.","문제사항 : "+e.getMessage());
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();

			} catch (SQLException e) {
				System.out.println("NoticeDAO noticeUpdate"+e.getMessage());
			}
		}


		return returnValue;
	}
}
