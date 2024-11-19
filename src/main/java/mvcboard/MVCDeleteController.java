package mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import utils.JSFunction;

@WebServlet("/mvcdelete.do")
public class MVCDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//로그인 확인
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			//session영역에 인증에 관련된 속성이 있는지 확인 후 경고창 띄움
			JSFunction.alertLocation(resp, "로그인 후 이용해주세요.", "./login.jsp");
			return;
		}
			//게시물 얻어오기 : 열람에서 사용한 메서드를 그대로 사용한다.
			String idx = req.getParameter("idx");
			MVCBoardDAO dao = new MVCBoardDAO();
			MVCBoardDTO dto = dao.selectView(idx);
			
			MemberDTO user = (MemberDTO) session.getAttribute("user");
			String userId = user.getId();
			
			if(!dto.getId().equals(userId)) {
				JSFunction.alertBack(resp, "작성자 본인만 삭제할 수 있습니다.");
				return;
			}
			
			//게시물 삭제
			int result = dao.deletePost(idx);
			dao.close();
			if(result == 1) {
				//게시물 삭제 성공 시 첨부파일도 삭제
				String saveFileName = dto.getSfile();
				//서버에 저장된 파일명으로 파일을 삭제한다.
				FileUtil.deleteFile(req, "/Uploads", saveFileName);
			}
			
			//삭제가 완료되면 목록으로 이동한다.
			JSFunction.alertLocation(resp, "삭제되었습니다.", "./mvclist.do");
	}
}