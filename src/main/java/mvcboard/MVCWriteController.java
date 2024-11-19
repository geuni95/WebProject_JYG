package mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import utils.JSFunction;

@WebServlet("/mvcwrite.do")

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 100,
		maxRequestSize = 1024 * 1024 * 1000
)

public class MVCWriteController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요.",
					"./login.jsp");
			return;
		}
		
		req.getRequestDispatcher("/mvcboard/write.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요.", "./login.jsp");
			return;
		}
		
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
		String originalFileName = "";
		try {
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		}
		catch (Exception e) {
			JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "./mvcwrite.do");
			return;
		}
		
		MVCBoardDTO dto = new MVCBoardDTO();
		
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		if (user != null) {
			String userId = user.getId();
			dto.setId(userId);
		}
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		
		if(originalFileName != "") {
			
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
		}
		
		MVCBoardDAO dao = new MVCBoardDAO();
		
		int result = dao.insertWriter(dto);
		
		dao.close();
		
		if (result ==1){
			resp.sendRedirect("./mvclist.do");
		}
		else {
			JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.", "./mvcwrite.do");
		}
	}
}
