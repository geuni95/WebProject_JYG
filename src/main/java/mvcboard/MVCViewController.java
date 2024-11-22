package mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.MVCViewManager;

@WebServlet("/mvcview.do")
public class MVCViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	MVCBoardDAO dao = new MVCBoardDAO();
	String idx = req.getParameter("idx");
	
	boolean isView = MVCViewManager.handlePostView(req, resp, idx);
	
	if (isView) {
		System.out.println("게시물 조회수 증가: " + idx);
	} else {
		System.out.println("이미 조회한 게시물입니다. 조회수가 증가되지 않았습니다.");
	}
	MVCBoardDTO dto = dao.selectView(idx);
	
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
	System.out.println("MIME타입= "+ mimeType);
	req.setAttribute("mimeType", mimeType);
	req.setAttribute("dto", dto);
	req.getRequestDispatcher("mvcboard/view.jsp").forward(req, resp);
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