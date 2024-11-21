package qaboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class QACommentDAO extends DBConnPool {

    // 댓글 개수 조회
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM qacomment WHERE board_idx = ?";
        if (map.get("searchWord") != null) {
            query += " AND " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%'";
        }
        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, (int) map.get("board_idx"));
            rs = psmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("댓글 카운트 중 예외 발생");
            e.printStackTrace();
        }
        return totalCount;
    }

    // 댓글 목록 조회
    public List<QACommentDTO> selectList(Map<String, Object> map) {
        List<QACommentDTO> comments = new Vector<QACommentDTO>();

        String query = "SELECT * FROM qacomment WHERE board_idx = ? ";
        if (map.get("searchWord") != null) {
            query += " AND " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }
        query += " ORDER BY idx DESC ";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, (int) map.get("board_idx"));
            rs = psmt.executeQuery();
            while (rs.next()) {
                QACommentDTO dto = new QACommentDTO();
                dto.setIdx(rs.getInt(1));
                dto.setBoardIdx(rs.getInt(2));
                dto.setId(rs.getString(3));
                dto.setContent(rs.getString(4));
                dto.setPostdate(rs.getDate(5));
                dto.setParentIdx(rs.getInt(6));
                dto.setIsDeleted(rs.getString(7));
                dto.setLikes(rs.getInt(8));

                comments.add(dto);
            }
        } catch (Exception e) {
            System.out.println("댓글 조회 중 예외 발생");
            e.printStackTrace();
        }
        return comments;
    }

    // 댓글 작성
    public int insertComment(QACommentDTO dto) {
        int result = 0;
        try {
            String query = "INSERT INTO qacomment (idx, board_idx, id, content, parent_idx, is_deleted, likes) "
                         + " VALUES (seq_qaboard_num.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, dto.getBoardIdx());
            psmt.setString(2, dto.getId());
            psmt.setString(3, dto.getContent());
            psmt.setInt(4, dto.getParentIdx());
            psmt.setString(5, dto.getIsDeleted());
            psmt.setInt(6, dto.getLikes());

            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 입력 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 조회 (상세보기)
    public QACommentDTO selectView(int idx) {
        QACommentDTO dto = new QACommentDTO();
        String query = "SELECT * FROM qacomment WHERE idx = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idx);
            rs = psmt.executeQuery();

            if (rs.next()) {
                dto.setIdx(rs.getInt(1));
                dto.setBoardIdx(rs.getInt(2));
                dto.setId(rs.getString(3));
                dto.setContent(rs.getString(4));
                dto.setPostdate(rs.getDate(5));
                dto.setParentIdx(rs.getInt(6));
                dto.setIsDeleted(rs.getString(7));
                dto.setLikes(rs.getInt(8));
            }
        } catch (Exception e) {
            System.out.println("댓글 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        return dto;
    }

    // 댓글 수정
    public int updateComment(QACommentDTO dto) {
        int result = 0;
        try {
            // content만 수정하도록 쿼리 변경
            String query = "UPDATE qacomment SET content = ? WHERE idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getContent());  // 댓글 내용 수정
            psmt.setInt(2, dto.getIdx());         // 댓글 고유 ID

            result = psmt.executeUpdate();  // 쿼리 실행
        } catch (Exception e) {
            System.out.println("댓글 수정 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 삭제
    public int deleteComment(int idx) {
        int result = 0;
        try {
            String query = "DELETE FROM qacomment WHERE idx = ?";
            psmt = con.prepareStatement(query);
            psmt.setInt(1, idx);
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 목록 페이지 처리 (페이징)
    public List<QACommentDTO> selectListPage(Map<String, Object> map) {
        List<QACommentDTO> comments = new Vector<QACommentDTO>();

        String query = "SELECT * FROM ( "
                     + " SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "   SELECT * FROM qacomment WHERE board_idx = ? ";
        if (map.get("searchWord") != null) {
            query += " AND " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }
        query += " ORDER BY idx DESC "
               + " ) TB "
               + " ) WHERE rNum BETWEEN ? AND ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, (int) map.get("board_idx"));
            psmt.setInt(2, (int) map.get("start"));
            psmt.setInt(3, (int) map.get("end"));
            rs = psmt.executeQuery();

            while (rs.next()) {
                QACommentDTO dto = new QACommentDTO();
                dto.setIdx(rs.getInt(1));
                dto.setBoardIdx(rs.getInt(2));
                dto.setId(rs.getString(3));
                dto.setContent(rs.getString(4));
                dto.setPostdate(rs.getDate(5));
                dto.setParentIdx(rs.getInt(6));
                dto.setIsDeleted(rs.getString(7));
                dto.setLikes(rs.getInt(8));

                comments.add(dto);
            }
        } catch (Exception e) {
            System.out.println("댓글 목록 페이징 조회 중 예외 발생");
            e.printStackTrace();
        }
        return comments;
    }
    
    public int getTotalComments(int boardIdx) {
        int totalComments = 0;
        String query = "SELECT COUNT(*) FROM qacomment WHERE board_idx = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, boardIdx);
            rs = psmt.executeQuery();

            if (rs.next()) {
                totalComments = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalComments;
    }
    
    public boolean hasLiked(int commentIdx, String userId) {
        boolean hasLiked = false;
        String query = "SELECT COUNT(*) FROM comment_likes WHERE comment_idx = ? AND id = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, commentIdx);
            psmt.setString(2, userId);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
            	int cnt = rs.getInt(1);
            	if(cnt>0) {
                hasLiked = true;
            	}
            }
        } catch (Exception e) {
            System.out.println("좋아요 여부 확인 중 예외 발생");
            e.printStackTrace();
        }
        return hasLiked;
    }

    // 사용자가 좋아요를 눌렀을 때 댓글 좋아요 수 1 증가
    public int increaseLikesForUser(int commentIdx, String userId) {
        int result = 0;

        // 댓글에 좋아요를 추가하는 과정
        String insertQuery = "INSERT INTO comment_likes (comment_idx, id) VALUES (?, ?)";

        try {
            psmt = con.prepareStatement(insertQuery);
            psmt.setInt(1, commentIdx);
            psmt.setString(2, userId);
            result = psmt.executeUpdate();
            
            // 좋아요 수 증가
            if (result > 0) {
                String updateQuery = "UPDATE qacomment SET likes = likes + 1 WHERE idx = ?";
                psmt = con.prepareStatement(updateQuery);
                psmt.setInt(1, commentIdx);
                psmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("좋아요 추가 중 예외 발생");
            e.printStackTrace();
        }

        return result;
    }

    // 사용자가 좋아요를 취소할 때 댓글 좋아요 수 1 감소
    public int decreaseLikesForUser(int commentIdx, String userId) {
        int result = 0;

        // 댓글에 대해 사용자가 눌렀던 좋아요를 취소
        String deleteQuery = "DELETE FROM comment_likes WHERE comment_idx = ? AND id = ?";

        try {
            psmt = con.prepareStatement(deleteQuery);
            psmt.setInt(1, commentIdx);
            psmt.setString(2, userId);
            result = psmt.executeUpdate();

            // 좋아요 수 감소
            if (result > 0) {
                String updateQuery = "UPDATE qacomment SET likes = likes - 1 WHERE idx = ?";
                psmt = con.prepareStatement(updateQuery);
                psmt.setInt(1, commentIdx);
                psmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("좋아요 취소 중 예외 발생");
            e.printStackTrace();
        }

        return result;
    }
}