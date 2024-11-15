package membership;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProfileUpdateProcess.do")
public class profileUpdateProcess extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 로그인된 사용자 정보 가져오기
        HttpSession session = req.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");

        if (user != null) {
            // 폼에서 수정된 데이터 받기
            String id = user.getId(); // 아이디는 수정하지 않음
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");

            // 수정된 정보를 MemberDTO 객체에 반영
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);

            // MemberDAO를 사용해 데이터베이스에 업데이트
            MemberDAO dao = new MemberDAO();
            boolean success = dao.updateMember(user);

            // 수정 성공/실패에 따른 처리
            if (success) {
                // 수정 성공: 세션에 반영된 데이터 다시 저장
                session.setAttribute("user", user);
                resp.sendRedirect("index.jsp");
            } else {
                // 수정 실패: 오류 메시지와 함께 다시 폼으로 돌아가기
                req.setAttribute("errorMsg", "회원 정보 수정에 실패했습니다.");
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            }

            dao.close();
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

}
