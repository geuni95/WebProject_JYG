package freeboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import utils.JSFunction;


public class WriteController extends HttpServlet{
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
		
		req.getRequestDispatcher("/freeboard/write.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요.", "./login.jsp");
			return;
		}
		
		FreeboardDTO dto = new FreeboardDTO();
		
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		if (user != null) {
			String userId = user.getId();
			dto.setId(userId);
		}
		
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));

		FreeboardDAO dao = new FreeboardDAO();
		int result = dao.insertWriter(dto);

		//더미데이터 100개 입력하기
//		for(int i=1; i<=100;i++) {
//			dto.setTitle(req.getParameter("title")+"-"+i);
//			dao.insertWriter(dto);
//		}
		
		dao.close();
		

		if (result ==1){
			resp.sendRedirect("./list.do");
		}
		else {
			JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.", "./write.do");
		}
	}
}
