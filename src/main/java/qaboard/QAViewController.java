package qaboard;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	
    // 댓글 조회
    QACommentDAO commentDao = new QACommentDAO();
    Map<String, Object> map = Map.of("board_idx", Integer.parseInt(idx));
    List<QACommentDTO> commentList = commentDao.selectList(map);

    req.setAttribute("commentList", commentList);

    // 댓글 작성 처리
    String action = req.getParameter("action");
    if ("write".equals(action)) {
        // 댓글 작성
        String id = (String) req.getSession().getAttribute("userId");
        String content = req.getParameter("content");
        String parentIdx = req.getParameter("parentIdx");
        
        QACommentDTO commentDto = new QACommentDTO();
        commentDto.setBoardIdx(Integer.parseInt(idx));
        commentDto.setId(id);
        commentDto.setContent(content);
        commentDto.setParentIdx(parentIdx != null ? Integer.parseInt(parentIdx) : 0);
        commentDto.setIsDeleted("N");
        commentDto.setLikes(0);

        int result = commentDao.insertComment(commentDto);
        if (result > 0) {
            resp.sendRedirect("qaview.do?idx=" + idx); // 댓글 작성 후 다시 해당 게시글로 리다이렉트
        } else {
            req.setAttribute("error", "댓글 작성에 실패했습니다.");
        }
    }

    req.getRequestDispatcher("qaboard/view.jsp").forward(req, resp);
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