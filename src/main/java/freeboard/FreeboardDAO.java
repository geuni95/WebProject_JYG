package freeboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class FreeboardDAO extends DBConnPool {
	
 
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM free_board";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement(); 
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	
	public List<FreeboardDTO> selectList(Map<String,Object> map){
		List<FreeboardDTO> board = new Vector<FreeboardDTO>();
		
		String query = "SELECT * FROM free_board ";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY idx DESC ";
		
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			while(rs.next()) {
				FreeboardDTO dto = new FreeboardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setDowncount(rs.getInt(6));
				dto.setVisitcount(rs.getInt(7));
				
				board.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board;
	}
	
	public int insertWriter(FreeboardDTO dto) {
		int result = 0;
		try {
			String query = 
					"INSERT INTO free_board ( "
					+ " idx, id, title, content)"
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL,?,?,?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public FreeboardDTO selectView(String idx) {
		FreeboardDTO dto = new FreeboardDTO();
		String query = "SELECT Bo.*, Me.name FROM free_board Bo "
				+ " INNER JOIN member Me ON Bo.id=Me.id"
				+ " WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);	
			psmt.setString(1, idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setDowncount(rs.getInt(6));
				dto.setVisitcount(rs.getInt(7));
				dto.setName(rs.getString(8));
			}
		}
		catch(Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	public void updateVisitCount(String idx) {
		String query = "UPDATE free_board SET "
					 + " visitcount=visitcount+1 "
					 + " WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			int result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	public void downCountPlus(String idx) {
		String sql = "UPDATE free_board SET "
				+ " downcount=downcount+1 "
				+ " WHERE idx=? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		}
		catch (Exception e) {}
	}
	
	
	public int deletePost(String idx) {
		int result = 0;
		try {
			String query = "DELETE FROM free_board WHERE idx=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public int updatePost(FreeboardDTO dto) {
		int result = 0;
		try {
			String query = "UPDATE free_board"
					+ " SET title=?, content=?"
					+ " WHERE idx=? and id=?";
					
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getContent());
				psmt.setString(3, dto.getIdx());
				psmt.setString(4, dto.getId());
				
				result = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	public List<FreeboardDTO> selectListPage(Map<String,Object> map){
		List<FreeboardDTO> board = new Vector<FreeboardDTO>();
		
		String query = 
				"SELECT * FROM ( "
				+ " SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "		SELECT * FROM free_board ";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += "	ORDER BY idx DESC "
				+ "		)TB"
				+ " ) "
				+ " WHERE rNum BETWEEN ? AND ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			while(rs.next()) {
				FreeboardDTO dto = new FreeboardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setDowncount(rs.getInt(6));
				dto.setVisitcount(rs.getInt(7));
				
				board.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board;
	}
}