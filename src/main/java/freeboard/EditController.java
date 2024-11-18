package freeboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import utils.JSFunction;


public class EditController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("user") == null){
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요", "./login.jsp");
			return;
		}
		
		//게시물 얻어오기 : '열람'에서 사용했던 메서드를 그대로 호출
		String idx = req.getParameter("idx");
		FreeboardDAO dao = new FreeboardDAO();
		FreeboardDTO dto = dao.selectView(idx);
		
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		String userId = user.getId();
		
		//작성자 본인 확인 : DTO에 저장된 id와 로그인 아이디를 비교
		if(!dto.getId().equals(userId)) {
			//작성자 본인이 아니라면 경고창을 띄운 후 뒤로 이동한다.
			JSFunction.alertBack(resp, "작성자 본인만 수정할 수 있습니다.");
			return;
		}
		
		//작성자 본인이라면 request영역에 DTO를 저장한 후 포워드한다.
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/freeboard/edit.jsp").forward(req, resp);
	}
	
	//수정 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//로그인 확인
		HttpSession session = req.getSession();
		//로그인 전이라면 로그인 페이지로 이동
		
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		String userId = user.getId();
		
		if(session.getAttribute("user")==null) {
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요", "./login.jsp");
			return;
		}
		//작성자 본인 확인 : 수정폼의 hidden속성으로 추가한 내용으로 비교
		if(!req.getParameter("id").equals(userId)) {
			JSFunction.alertBack(resp, "작성자 본인만 수정할 수 있습니다.");
			return;
		}
	
		String idx = req.getParameter("idx");
		
		//사용자가 수정한 내용
		String title =req.getParameter("title");
		String content = req.getParameter("content");
		
		FreeboardDTO dto = new FreeboardDTO();
		dto.setIdx(idx);
		dto.setId(userId);
		dto.setTitle(title);
		dto.setContent(content);
		
		
		FreeboardDAO dao = new FreeboardDAO();
		int result = dao.updatePost(dto);
		dao.close();
		// 성공 or 실패?
		if (result == 1) { //수정 성공
			//수정에 성공하면 '열람'페이지로 이동해서 수정된 내용을 확인한다.
			resp.sendRedirect("./view.do?idx="+idx);
		}
		else { // 수정 실패 : 경고창을 띄운다.
			JSFunction.alertLocation(resp, "수정에 실패했습니다.", "./view.do?idx="+idx);
		}
	}
}
