package membership;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/findprocess.do")
public class FindIdProcess extends HttpServlet{
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
        String id = req.getParameter("id");
        
        MemberDAO dao = new MemberDAO();
        MemberDTO dto = dao.getMemberDTO(id, null);  // 비밀번호는 입력하지 않음

        if (dto != null && dto.getId() != null) {
            String password = dto.getPass();
            req.setAttribute("successMessage", "아이디에 해당하는 비밀번호는: " + password);
            req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "해당 아이디는 존재하지 않습니다.");
            req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
        }
	}
}
