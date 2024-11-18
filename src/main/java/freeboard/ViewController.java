package freeboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	FreeboardDAO dao = new FreeboardDAO();
	String idx = req.getParameter("idx");
	dao.updateVisitCount(idx);
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
