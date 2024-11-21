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
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        System.out.println("입력한 아이디: " + id);  // 디버깅용 출력
        System.out.println("입력한 이름: " + name);  // 디버깅용 출력
        System.out.println("입력한 아이디: " + email);  // 디버깅용 출력
        
        
        MemberDAO dao = new MemberDAO();

        // 아이디를 입력한 경우
        if (id != null && !id.isEmpty()) {
            MemberDTO dto = dao.getIdMemberDTO(id);
            if (dto != null && dto.getId() != null) {
                String password = dto.getPass();
                req.setAttribute("successMessage", "아이디에 해당하는 비밀번호는: " + password);
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "해당 아이디는 존재하지 않습니다.");
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            }
        }
        // 이름과 이메일을 입력한 경우
        else if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            MemberDTO dto = dao.getMemberByNameAndEmail(name, email);
            if (dto != null && dto.getId() != null) {
                req.setAttribute("successMessage", "아이디는: " + dto.getId());
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "해당 이름과 이메일에 일치하는 아이디가 없습니다.");
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "아이디나 이름과 이메일을 모두 입력해주세요.");
            req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
        }
    }
}
