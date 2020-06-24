package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.PT;

public class PtDAO {
	
	public ArrayList<PT> getTotalList(){
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PT> arrayList = null;
		
		try {
			 //DBUtill에 있는 정보로 DB에 접속합니다.
			con = DBUtill.getConnection();
			if(con !=null) {
				System.out.println("RootController.PtDAO: DB 연결성공");
			} else {
				System.out.println("RootController.PtDAO: DB 연결 실패");
			}
			String query = "select * from personaltraining";
			pstmt= con.prepareStatement(query);
			rs= pstmt.executeQuery();
			
			arrayList = new ArrayList<PT>();
			while(rs.next()) {
				PT pt = new PT(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) );
				arrayList.add(pt);
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("PTDAO TotalList 점검요망");
			alert.setHeaderText("PTDAO TotalList에 문제 발생");
			alert.setContentText("문제사항" + e.getMessage());
			alert.showAndWait();
		} finally {
			try {
				if(rs!=null) rs.close();
				if (pstmt != null) 	pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				System.out.println("RootController.DAOTotalLoadList:" + e.getMessage());
			}
		}
		return arrayList;
	}

	
}
