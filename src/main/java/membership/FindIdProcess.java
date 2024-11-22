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
        
        MemberDAO dao = new MemberDAO();
        MemberDTO dto = null;

        // 아이디를 입력한 경우
        if (id != null && !id.isEmpty()) {
            // 아이디에 해당하는 회원 정보 가져오기
            dto = dao.getIdMemberDTO(id);
            if (dto != null && dto.getId() != null) {
                // 해당 아이디에 대한 이메일 가져오기
                String memberEmail = dto.getEmail();  // 회원 이메일 주소 가져오기
                String password = dto.getPass();  // 해당 아이디의 비밀번호

                // 이메일 전송을 위한 파라미터 설정
                req.setAttribute("from", "wkddprms@naver.com");  // 발신자 이메일 설정 (관리자 이메일)
                req.setAttribute("to", memberEmail);  // 받는 사람 이메일 설정
                req.setAttribute("subject", "비밀번호 찾기 결과");  // 제목
                req.setAttribute("content", "안녕하세요, 요청하신 비밀번호는: " + password);  // 이메일 내용
                req.setAttribute("format", "text");
                System.out.println("to: " + memberEmail);
                // 이메일을 전송하기 위해 email.do로 이동
                req.getRequestDispatcher("/email.do").forward(req, resp);
            } else {
                req.setAttribute("error", "해당 아이디는 존재하지 않습니다.");
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            }
        } 
        // 이름과 이메일을 입력한 경우
        else if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
        	dto = dao.getMemberByNameAndEmail(name, email);
            if (dto != null && dto.getId() != null) {
                req.setAttribute("successMessage", "아이디는: " + dto.getId());
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "해당 이름과 이메일에 일치하는 아이디가 없습니다.");
                req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "아이디와 이름과 이메일을 모두 입력해주세요.");
            req.getRequestDispatcher("/findlogin.jsp").forward(req, resp);
        }
    }
}
