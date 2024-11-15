package membership;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@WebServlet("/LoginProcess.do")
public class LoginProcess extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = dao.getMemberDTO(id, pass);
		
        if (dto.getId() != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", dto);
            resp.sendRedirect("index.jsp");
        }
		else{
			req.setAttribute("LoginErrMsg", "로그인 오류입니다.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		
		dao.close();
	}
}
