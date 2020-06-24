package controller;

import javafx.scene.control.Alert;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO {

    //DB연결에 필요한 것들 전역변수로 선언
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    //쿼리문 결과값 반환을 위한 전역변수 선언
    ArrayList<Member> arrayList = null;
    int returnValue = 0;

    //로그인 하려는 계정의 정보를 가져와줌
    public ArrayList<Member> Login(String id,String password) {

        try {
            //DBUtill에 있는 정보로 DB에 접속합니다.
            con = DBUtill.getConnection();

            //DB 연결 성공여부 체크
            if(con != null) {
                System.out.println("MemberDAO.Login : DB 연결 성공");
            }else {
                System.out.println("MemberDAO.Login : DB 연결 실패");
            }

            //쿼리문
            String query = "SELECT * FROM member WHERE id = binary ? AND pw = binary ?";

            //쿼리문 실행준비
            pstmt = con.prepareStatement(query);
            //매개변수로 받은 id값과 password값을 쿼리문에 삽입
            pstmt.setString(1,id);
            pstmt.setString(2,password);

            //쿼리문을 실행하고 나온 결과값을 resultset으로 받는다.
            rs = pstmt.executeQuery();

            arrayList = new ArrayList<Member>();

            while(rs.next()) {
                Member member = new Member(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                arrayList.add(member);
            }

        } catch (Exception e) {
            AlertUtill.showWarningAlert("오류!","알 수 없는 오류가 발생했습니다.","관리자에게 문의해 주시기 바랍니다.");
        } finally {
            try {
                if(rs != null)	rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                System.out.println("MemberDAO.Login"+e.getMessage());
            }
        }

        return arrayList;
    }

    //아이디 중복 체크를 도와줌
    public ArrayList<Member> IdDuplicateCheck(String id) {

        try {
            //DBUtill에 있는 정보로 DB에 접속
            con = DBUtill.getConnection();

            //DB 연결 성공여부 체크
            if(con != null) {
                System.out.println("MemberDAO.IdDuplicateCheck : DB 연결 성공");
            }else {
                System.out.println("MemberDAO.IdDuplicateCheck : DB 연결 실패");
            }

            //쿼리문
            String query = "SELECT * FROM member WHERE id = ?";

            //쿼리문 실행준비
            pstmt = con.prepareStatement(query);

            //매개변수로 받은 id값과 password값을 쿼리문에 삽입
            pstmt.setString(1,id);

            //쿼리문을 실행하고 나온 결과값을 resultset으로 받는다.
            rs = pstmt.executeQuery();

            arrayList = new ArrayList<Member>();

            while(rs.next()) {
                Member member = new Member(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                arrayList.add(member);
            }

        } catch (Exception e) {
            AlertUtill.showWarningAlert("오류!","알 수 없는 오류가 발생했습니다.","관리자에게 문의해 주시기 바랍니다.");
        }finally {
            try {
                if(rs != null)	rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                System.out.println("MemberDAO.IdDuplicateCheck"+e.getMessage());
            }
        }

        return arrayList;
    }

    //회원가입 함수
    public int Join(Member member) {

        try {

            con = DBUtill.getConnection();

            if(con != null) {
                System.out.println("MemberDAO.Join : DB 연결 성공");
            }else {
                System.out.println("MemberDAO.Join : DB 연결 실패");
            }

            //쿼리문
            String query = "INSERT INTO member(id,pw,name,gender,phone,trainer_flag) VALUES(?,?,?,?,?,?)";

            //쿼리문 실행 준비
            pstmt = con.prepareStatement(query);

            pstmt.setString(1,member.getId());
            pstmt.setString(2,member.getPw());
            pstmt.setString(3,member.getName());
            pstmt.setString(4,member.getGender());
            pstmt.setString(5,member.getPhone());
            pstmt.setString(6,member.getTrainer_flag());

            //쿼리문을 실행하고 결과값을 반환
            returnValue = pstmt.executeUpdate();

            if(returnValue != 0) {
                AlertUtill.showInformationAlert("회원가입 성공","회원가입에 성공하였습니다.","확인버튼을 누르면 로그인 화면으로 돌아갑니다.");
            }else {
                throw new Exception(member.getName()+"번 삽입 문제 있음");
            }

        } catch (Exception e) {
            // 경고창이 만들어짐
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("삽입 점검요망");
            alert.setHeaderText("삽입 문제발생!\n RootController.BtnOkAction" + e.getMessage());
            alert.setContentText("문제사항 : "+e.getMessage());
            alert.showAndWait();
        } finally {
            try {
                if(pstmt != null)pstmt.close();
                if(con != null)con.close();

            } catch (SQLException e) {
                System.out.println("RootController.BtnOkAction"+e.getMessage());
            }
        }

        return returnValue;
    }

    //회원 정보 찾기 함수
    public Member findMember(Member fm, String btnName) {

        //결과값을 반환하기 위한 변수
        Member member = null;

        try {
            con = DBUtill.getConnection();

            //DB 연결 성공여부 체크
            if(con != null) {
                System.out.println("MemberDAO.findMember : DB 연결 성공");
            }else {
                System.out.println("MemberDAO.findMember : DB 연결 실패");
            }


            switch (btnName) {
                //ID 찾기에 관련된 쿼리문
                case "btnFindId" : {
                    //쿼리문
                    String query = "SELECT * FROM member WHERE name = ? AND phone = ? AND trainer_flag = 'f'";

                    //쿼리문 실행준비
                    pstmt = con.prepareStatement(query);

                    //매개변수로 받은 이름과 핸드폰번호를 쿼리문에 삽입
                    pstmt.setString(1,fm.getName());
                    pstmt.setString(2,fm.getPhone());
                    break;
                }

                case "btnFindPw" : {
                    //쿼리문
                    String query = "SELECT * FROM member WHERE id = ? AND name = ? AND phone = ? AND trainer_flag = 'f'";

                    //쿼리문 실행준비
                    pstmt = con.prepareStatement(query);

                    //매개변수로 받은 이름과 핸드폰번호를 쿼리문에 삽입
                    pstmt.setString(1,fm.getId());
                    pstmt.setString(2,fm.getName());
                    pstmt.setString(3,fm.getPhone());
                    break;
                }
            }


            //쿼리문의 결과를 resultset으로 받는다
            rs = pstmt.executeQuery();

            while(rs.next()) {
                member = new Member(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
            }

        } catch (Exception e) {
            AlertUtill.showWarningAlert("오류!","알 수 없는 오류가 발생했습니다.","관리자에게 문의해 주시기 바랍니다.");
        }finally {
            try {
                if(rs != null)	rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                System.out.println("MemberDAO.IdDuplicateCheck"+e.getMessage());
            }
        }

        return member;
    }
}
