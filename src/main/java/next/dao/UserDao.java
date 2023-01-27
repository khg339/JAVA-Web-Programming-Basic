package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException { //insert 메소드
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"; //sql문
            pstmt = con.prepareStatement(sql); //연결에 sql문 준비시킴
            pstmt.setString(1, user.getUserId()); //첫번째 물음표 값
            pstmt.setString(2, user.getPassword()); //두번째 물음표 값
            pstmt.setString(3, user.getName()); //세번째 물음표 값
            pstmt.setString(4, user.getEmail()); //네번째 물음표 값

            pstmt.executeUpdate(); //값을 모두 대입한 결과 실행(insert 문이기 때문에 executeUpdate)
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public void update(User user) throws SQLException { //update 메소드
        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = ConnectionManager.getConnection();
            String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            pstmt.executeUpdate();//값을 모두 대입한 결과 실행(update 문이기 때문에 executeUpdate)
        }finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public List<User> findAll() throws SQLException { //모든 user 반환 메소드
        // TODO 구현 필요함.
        List<User> users = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM USERS";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery(); //값을 모두 대입한 결과 실행(select 문이기 때문에 executeQuery)

            while(rs.next()){
                User user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
                users.add(user);
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return users;
    }

    public User findByUserId(String userId) throws SQLException { //userId로 검색
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?"; //sql문
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery(); ////값을 모두 대입한 결과 실행(select 문이기 때문에 executeQuery)

            User user = null;
            if (rs.next()) { //결과 집합을 하나씩 검사(다음 값이 있으면 true, 없으면 false 반환)
                //해당 user 저장
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
