package controller;

import model.Answer;
import model.Member;
import model.QuestionMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAO {

    //DB연결에 필요한 것들 전역변수로 선언
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    //쿼리문 결과값 반환을 위한 전역변수 선언
    ArrayList<Member> arrayList = null;
    int returnValue = 0;

    //답변을 DB에 등록해주는 함수
    public int answerRegist (QuestionMember question , String contents, Member memberLogin) {
        try {
            con = DBUtill.getConnection();

            if(con != null) {
                System.out.println("AnswerDAO.answerRegist : DB 연결 성공");
            }else {
                System.out.println("AnswerDAO.answerRegist : DB 연결 실패");
            }

            //쿼리문
            String query = "INSERT INTO answer VALUES(?,?,?,now())";

            pstmt = con.prepareStatement(query);

            pstmt.setInt(1,question.getNo());
            pstmt.setString(2, contents);
            pstmt.setString(3,memberLogin.getId());

            returnValue = pstmt.executeUpdate();

            if(returnValue != 0) {
                AlertUtill.showInformationAlert("답변등록 성공","답변등록에 성공하였습니다.","확인을 누르면 메인 화면으로 돌아갑니다.");
            }else {
                throw new Exception(question.getNo()+"번 답변 등록 문제 있음");
            }
        } catch (Exception e) {
            // 경고창이 만들어짐
            AlertUtill.showWarningAlert("답변등록 실패","답변등록에 실패하였습니다.","문제사항 : "+e.getMessage());
        } finally {
            try {
                if(pstmt != null)pstmt.close();
                if(con != null)con.close();

            } catch (SQLException e) {
                System.out.println("AnswerDAO.answerRegist "+e.getMessage());
            }
        }

        return returnValue;
    }

    //DB에서 답변내용을 가져오는 함수
    public ArrayList<Answer> getAnswer (QuestionMember selectQuestion) {

        ArrayList<Answer> arrayList = new ArrayList<>();

        try {
            con = DBUtill.getConnection();

            if(con != null) {
                System.out.println("AnswerDAO.getAnswer : DB 연결 성공");
            }else {
                System.out.println("AnswerDAO.getAnswer : DB 연결 실패");
            }

            //쿼리문
            String query = "select * from answer where question_no = ?";

            pstmt = con.prepareStatement(query);
            
            pstmt.setInt(1, selectQuestion.getNo());
     
            rs = pstmt.executeQuery();

            if(rs.next()){
                Answer answer = new Answer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                arrayList.add(answer);
            }else {
                arrayList.add(new Answer(0,"아직 답변이 등록되지 않았습니다.","",""));
                System.out.println(arrayList);
                return arrayList;
            }

        } catch (Exception e) {
            // 경고창이 만들어짐
            AlertUtill.showWarningAlert("답변 가져오기 실패","답변 가져오기에 실패하였습니다.","문제사항 : "+e.getMessage());
        } finally {
            try {
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
                if(rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println("AnswerDAO.getAnswer "+e.getMessage());
            }
        }

        return arrayList;
    }
   
}
