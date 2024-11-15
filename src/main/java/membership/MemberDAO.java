package membership;

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
	
}
