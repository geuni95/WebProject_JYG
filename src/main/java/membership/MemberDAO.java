package membership;

import java.sql.SQLException;

import common.DBConnPool;
import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class MemberDAO extends DBConnPool {
	
	DBConnPool pool = new DBConnPool();
	
	public int insertLogin(MemberDTO dto) {
		int applyResult = 0;
		try {
			String query = "INSERT INTO member ("
					+ "id, pass, name, email, phone)"
					+ " VALUES (?, ?, ?, ?, ?)";
			
			con = pool.con;
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPass());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			psmt.setString(5, dto.getPhone());
			
			psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("insert 예외 발생");
			e.printStackTrace();
		}
		finally {
			pool.close();
		}
		return applyResult;
	}
	
	public MemberDTO getMemberDTO(String uid, String upass) {
		
		MemberDTO dto = new MemberDTO();
		
		String query = "SELECT * FROM member WHERE id=? and pass=?";
		
		try {
	        System.out.println("쿼리 실행: " + query);
			psmt = con.prepareStatement(query);
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
                dto.setName(rs.getString("name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                System.out.println("아이디: " + dto.getId() + ", 비밀번호: " + dto.getPass());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	public MemberDTO getIdMemberDTO(String uid) {
		
		MemberDTO dto = new MemberDTO();
		
		String query = "SELECT * FROM member WHERE id=?";
		
		try {
	        System.out.println("쿼리 실행: " + query);
			psmt = con.prepareStatement(query);
			psmt.setString(1, uid);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
                dto.setName(rs.getString("name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                System.out.println("아이디: " + dto.getId() + ", 비밀번호: " + dto.getPass());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
    // 이름과 이메일로 아이디를 찾는 메서드
    public MemberDTO getMemberByNameAndEmail(String name, String email) {
        MemberDTO dto = null;
        
        // 이름과 이메일을 통해 사용자를 찾기 위한 쿼리
        String query = "SELECT * FROM member WHERE name=? AND email=?";
        
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, name);   // 이름 조건
            psmt.setString(2, email);  // 이메일 조건
            rs = psmt.executeQuery();
            
            // 결과가 있으면 아이디를 반환
            if (rs.next()) {
                dto = new MemberDTO();
                dto.setId(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dto;  // 아이디가 있으면 MemberDTO 객체 반환, 없으면 null
    }
	
	public boolean updateMember(MemberDTO memberDTO) {
        String query = "UPDATE member SET name=?, email=?, phone=? WHERE id=?";
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, memberDTO.getName());
            psmt.setString(2, memberDTO.getEmail());
            psmt.setString(3, memberDTO.getPhone());
            psmt.setString(4, memberDTO.getId());

            int result = psmt.executeUpdate();

            return result > 0;  // 성공적으로 업데이트되었으면 true 반환
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
    public void close() {
        try {
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
