package qaboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import utils.JSFunction;

@WebServlet("/qaview.do")
public class QAViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		QABoardDAO dao = new QABoardDAO();
		String idx = req.getParameter("idx");
		
		dao.updateVisitCount(idx);
		QABoardDTO dto = dao.selectView(idx);
		
		dao.close();
		
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		
		String ext = null, fileName = dto.getSfile(), mimeType = null;
		if(fileName!= null) {
			ext = fileName.substring(fileName.lastIndexOf(".")+1);
		}
		String[] extArray1 = {"png", "jpg", "gif", "pcx", "bmp"};
		String[] extArray2 = {"mp3", "wav"};
		String[] extArray3 = {"mp4", "avi", "wmv"};
		
		if(mimeContains(extArray1, ext)) {
			mimeType = "img";
		}
		else if(mimeContains(extArray2, ext)) {
			mimeType = "audio";
		}
		else if(mimeContains(extArray3, ext)) {
			mimeType = "video";
		}
		req.setAttribute("mimeType", mimeType);
		req.setAttribute("dto", dto);
		
		// 댓글 페이징처리
		int pageSize = 6;
		int pageNum = 1;
		
		if (req.getParameter("page") != null) {
			pageNum = Integer.parseInt(req.getParameter("page"));
		}
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		
		// 댓글 조회
		QACommentDAO commentDao = new QACommentDAO();
		Map<String, Object> map = new HashMap<>();
		map.put("board_idx", Integer.parseInt(idx));
		map.put("start", start);
		map.put("end", end);
		
		List<QACommentDTO> commentList = commentDao.selectListPage(map);
		
		int totalComments = commentDao.getTotalComments(Integer.parseInt(idx));
        int totalPages = (int) Math.ceil(totalComments / (double) pageSize);
		
		// 댓글 목록을 요청 속성에 설정
		req.setAttribute("totalPages", totalPages);
        req.setAttribute("commentList", commentList);
        req.setAttribute("currentPage", pageNum);
        req.setAttribute("dto", dto);
        
		// 댓글 작성 처리
		String action = req.getParameter("action");

		if ("write".equals(action)) {
		    HttpSession session = req.getSession();
		    
		    // 로그인 체크
		    if (session.getAttribute("user") == null) {
		        JSFunction.alertLocation(resp, "로그인 후 이용해주세요.", "./login.jsp");
		        return;
		    }
		    
		    // 댓글 작성
		    MemberDTO user = (MemberDTO) session.getAttribute("user");
		    String content = req.getParameter("content");
		    String parentIdx = req.getParameter("parentIdx");

		    QACommentDTO commentDto = new QACommentDTO();
		    commentDto.setBoardIdx(Integer.parseInt(idx));
			if (user != null) {
				String id = user.getId();
				commentDto.setId(id);
			}
		    commentDto.setContent(content);
		    commentDto.setParentIdx(parentIdx != null ? Integer.parseInt(parentIdx) : 0);
		    commentDto.setIsDeleted("N");
		    commentDto.setLikes(0);

		    // 댓글 DB에 삽입
		    int result = commentDao.insertComment(commentDto);
		    if (result > 0) {
		        // 댓글 작성 후, 댓글 목록 갱신
		        commentList = commentDao.selectList(map);
		        req.setAttribute("commentList", commentList);
		    } else {
		        req.setAttribute("error", "댓글 작성에 실패했습니다.");
		    }

		    req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);
		    
		} else if ("editForm".equals(action)) { // 댓글 수정 폼
		    // 댓글 수정 폼 처리
		    req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);

		} else if ("edit".equals(action)) { // 댓글 수정 처리
		    String commentIdx = req.getParameter("commentIdx");
		    String content = req.getParameter("content");
		    
		    HttpSession session = req.getSession();
		    MemberDTO user = (MemberDTO) session.getAttribute("user");
		    
		    if (commentIdx != null && content != null && user != null) {
		        // 댓글 수정 처리
		        QACommentDTO commentDto = commentDao.selectView(Integer.parseInt(commentIdx));
		        
		        if (commentDto != null && commentDto.getId() != null && commentDto.getId().equals(user.getId())) {
		        	commentDto.setContent(content);
		        	int result = commentDao.updateComment(commentDto);

		        	if (result > 0) {
		        		// 댓글 수정 후, 해당 게시글로 리다이렉트
		        		resp.sendRedirect("qaview.do?idx=" + idx);
		        	} else {
		        		req.setAttribute("error", "댓글 수정에 실패했습니다.");
		        	}
		        } else {
		            // 로그인한 사용자와 댓글 작성자가 다르면 수정 불가
		            req.setAttribute("error", "댓글을 수정할 권한이 없습니다.");
		            req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);
		        }
		    }
		} else if ("delete".equals(action)) { // 댓글 삭제 처리
		    String commentIdx = req.getParameter("commentIdx");

		    // 댓글 삭제 (isDeleted 'Y'로 업데이트)
		    int result = commentDao.deleteComment(Integer.parseInt(commentIdx));
		    if (result > 0) {
		        // 댓글 삭제 후, 댓글 목록 갱신
		        commentList = commentDao.selectList(map);
		        req.setAttribute("commentList", commentList);
		    } else {
		        req.setAttribute("error", "댓글 삭제에 실패했습니다.");
		    }

		    req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);

		} else {
		    // 기본 페이지 요청 처리
		    req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);
		}
	}
	public boolean mimeContains(String[] strArr, String ext) {
		boolean retValue = false;
		for(String s : strArr) {
			if(s.equalsIgnoreCase(ext))
				retValue =true;
		}
		return retValue;
	}
}