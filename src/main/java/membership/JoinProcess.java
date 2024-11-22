package membership;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/JoinProcess.do")
public class JoinProcess extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPass(pass);
		dto.setName(name);
		dto.setEmail(email);
		dto.setPhone(phone);
		
		MemberDAO dao = new MemberDAO();
		dao.insertLogin(dto);
		dao.close();
		
		req.setAttribute("successMessage", "회원가입하셨습니다.");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
		
		
	}
}
