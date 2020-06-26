package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PT;
import model.PTMember;


public class PtDAO {

    //전역변수
    static Connection con = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;

    //PT 신청 정보를 전부 가져와줍니다.
    public ArrayList<PT> getTotalList() {
        ArrayList<PT> arrayList = new ArrayList<PT>();

        try {
            //DBUtill에 있는 정보로 DB에 접속합니다.
            con = DBUtill.getConnection();
            if (con != null) {
                System.out.println("controller.PtDAO: DB 연결성공");
            } else {
                System.out.println("controller.PtDAO: DB 연결 실패");
            }
            String query = "select * from personaltraining";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                PT pt = new PT(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                arrayList.add(pt);
            }

        } catch (Exception e) {
            AlertUtill.showWarningAlert("PTDAO TotalList 점검요망", "PTDAO TotalList에 문제 발생", "문제사항 " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("PTDAO.finallyGetTotalList:" + e.getMessage());
            }
        }
        return arrayList;
    }

    //로그인한 트레이너의 정보에 맞춰서 해당 트레이너로 신청된 PT정보를 전부 불러와 줍니다.
    public ArrayList<PTMember> getTrainerPTList(String memberID) {

        ArrayList<PTMember> PTMemberArrayList = new ArrayList<>();

        try {
            //DB 연결
            con = DBUtill.getConnection();
            if (con != null) {
                System.out.println("controller.PtDAO: DB 연결성공");
            } else {
                System.out.println("controller.PtDAO: DB 연결 실패");
            }

            //쿼리문
            String query = "SELECT PT.`no`, PT.date, TIME_FORMAT(PT.time,\"%p %l:%i\") time, MB.`name`, MB.phone, PT.created_by, PT.created_at  FROM personaltraining AS PT INNER JOIN member AS MB ON MB.id = PT.member_id WHERE trainer_id = ? ORDER BY time ASC";
            //쿼리문 준비
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,  memberID);
            //쿼리실행 결과값 저장
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PTMember PTMember = new PTMember (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                PTMemberArrayList.add(PTMember);
            }

        } catch (Exception e) {
            AlertUtill.showWarningAlert("PTDAO getTrainerPTList 점검요망", "PTDAO getTrainerPTList 문제발생", "문제사항!! " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("PTDAO.finallyGetTrainerPTList:" + e.getMessage());
            }
        }

        return PTMemberArrayList;
    }
    
    //달력에서 날짜를 선택하면 해당일의 PT 신청 정보를 가져온다.
    public static ObservableList<PT> selectClassDataByDate(String date) {
		ObservableList<PT> dbClsByDateList = FXCollections.observableArrayList();
		

		ResultSet rs = null;
		try {
			try {
				con = DBUtill.getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(con !=null) {
				System.out.println("controller.PtDAO: DB 연결성공");
			} else {
				System.out.println("controller.PtDAO: DB 연결 실패");
			}
			String query = "select date, time, created_at, trainer_id from personaltraining where date like?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+date+"%");
			
			rs = pstmt.executeQuery();

			int resultCount = 0;
			while (rs.next()) {
				PT pt = new PT(rs.getString(1), rs.getString(2) , rs.getString(3), rs.getString(4));
				dbClsByDateList.add(pt);
			}
			if (resultCount == 0) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// .6 CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println("RootController.PtDAO.selectClassDataByDate:" + e.getMessage());
			}
		}
		return dbClsByDateList;
	}
    
    //해당일에 PT가 신청된 시간을 불러옵니다.
    public static ObservableList<PTMember> getPTTimeCombo() {
        ObservableList<PTMember> PTTimeCombo = FXCollections.observableArrayList();

        ResultSet rs = null;
        try {
            con = DBUtill.getConnection();
            if (con != null) {
                System.out.println("controller.PtDAO: DB 연결성공");
            } else {
                System.out.println("controller.PtDAO: DB 연결 실패");
            }

            //쿼리문
            String query = "SELECT DISTINCT TIME_FORMAT(time,\"%H:%i\") AS time FROM personaltraining AS PT WHERE date = \"2020-06-25\" ORDER BY time ASC";

            //쿼리문 준비
            pstmt = con.prepareStatement(query);
            //쿼리실행 결과값 저장
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PTMember PTMember = new PTMember (rs.getString(1));
                PTTimeCombo.add(PTMember);
            }


        }catch (Exception e) {
            AlertUtill.showWarningAlert("PTDAO getTrainerPTList 점검요망", "PTDAO getTrainerPTList 문제발생", "문제사항!! " + e.getMessage());
        }
        return PTTimeCombo;
    }
    
    //회원정보 창의 테이블에 pt 신청한 내용. 트레이너 날짜 시간을 가져온다
    public static ArrayList<PT> getMyPTInfo() {
    	ArrayList<PT> myPTInfo = new ArrayList<PT>();

        ResultSet rs = null;
        try {
            con = DBUtill.getConnection();
            if (con != null) {
                System.out.println("controller.PtDAO: DB 연결성공");
            } else {
                System.out.println("controller.PtDAO: DB 연결 실패");
            }

            //쿼리문
            String query = "SELECT * from personaltraining where member_id ='hh'";

            //쿼리문 준비
            pstmt = con.prepareStatement(query);
            //쿼리실행 결과값 저장
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PT pt = new PT (rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
                myPTInfo.add(pt);
            }


        }catch (Exception e) {
            AlertUtill.showWarningAlert("PTDAO getMyPTInfo 점검요망", "PTDAO getMyPTInfo 문제발생", "문제사항!! " + e.getMessage());
        }
        return myPTInfo;
    }
    
}
