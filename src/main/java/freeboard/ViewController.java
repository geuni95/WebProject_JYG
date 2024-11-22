package freeboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.FreeViewManager;

public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	String idx = req.getParameter("idx");
	
	boolean isView = FreeViewManager.handlePostView(req, resp, idx);
	
	if (isView) {
		System.out.println("게시물 조회수 증가: " + idx);
	} else {
		System.out.println("이미 조회한 게시물입니다. 조회수가 증가되지 않았습니다.");
	}
	
	FreeboardDAO dao = new FreeboardDAO();
	FreeboardDTO dto = dao.selectView(idx);
	dao.close();
	
	dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));

	req.setAttribute("dto", dto);
	
	req.getRequestDispatcher("/freeboard/view.jsp").forward(req, resp);
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
