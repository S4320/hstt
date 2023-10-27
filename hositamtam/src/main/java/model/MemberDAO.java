package model;

import java.sql.*;
import java.util.*;

public class MemberDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	public MemberDAO() {
		String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
		String jdbc_url = "jdbc:oracle:thin:@localhost:1521:XE";

		if (conn == null) {
			try {
				Class.forName(jdbc_driver);
				conn = DriverManager.getConnection(jdbc_url, "scott", "tiger");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ㄱ.회원 가입 / db에 등록된 아이디와 중복 검사.
	// ㄴ.회원 정보 조회
	// ㄷ.회원 정보 수정
	
	// ㄱ.회원 가입
	public int joinMember(MemberDO memberDO) {
		int rowCount = 0;

		sql = "INSERT INTO member (id, nickname, passwd, birthdate, gender, exp, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, memberDO.getId());
            pstmt.setString(2, memberDO.getNickname());
            pstmt.setString(3, memberDO.getPasswd());
            pstmt.setDate(4, new java.sql.Date(memberDO.getBirthdate().getTime()));
            pstmt.setString(5, memberDO.getGender());
            pstmt.setInt(6, memberDO.getExp());
            pstmt.setInt(7, memberDO.getGrade());

			rowCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}
	
	// ㄴ.회원 정보 조회
	// 
	public ArrayList<MemberDO> getMember() {
		ArrayList<MemberDO> memberList = new ArrayList<MemberDO>();

			sql = "SELECT * FROM member";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				MemberDO memberDO = new MemberDO(); // MemberDO 객체 생성

				memberDO.setId(rs.getString("id"));
                memberDO.setNickname(rs.getString("nickname"));
                memberDO.setPasswd(rs.getString("passwd"));
                memberDO.setBirthdate(rs.getDate("birthdate"));
                memberDO.setGender(rs.getString("gender"));
                memberDO.setExp(rs.getInt("exp"));
                memberDO.setGrade(rs.getInt("grade"));

                memberList.add(memberDO);
	            }
			
			// '내가 등록한 점포, 내가 작성한 글, 찜한 점포, 작성 리뷰 조회' 기능추가해야함.
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return memberList;
	}

	// ㄷ.회원 정보 수정
	public int updateMember(MemberDO memberDO) {
		int rowCount = 0;

		sql = "UPDATE member SET nickname = '새로운닉네임', passwd = '새로운비밀번호' WHERE id = '사용자아이디'";

		try {
			pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, memberDO.getId());
            pstmt.setString(2, memberDO.getNickname());
            pstmt.setString(3, memberDO.getPasswd());

			rowCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}
}
