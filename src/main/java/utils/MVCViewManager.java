package utils;

import freeboard.FreeboardDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;
import mvcboard.MVCBoardDAO;

public class MVCViewManager {

	// 게시물 조회수 증가 처리
    public static boolean handlePostView(HttpServletRequest req, HttpServletResponse resp, String postId) {
        
        HttpSession session = req.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        
        // 로그인 x
        if (user == null) {
            return false;
        }
        
        String userId = user.getId(); // 세션에서 사용자 ID를 가져옵니다.
        String cookieName = "mvcviewedPost_" + postId + "_" + userId;  // 쿠키 이름에 사용자 ID 추가
        String viewedToday = CookieManager.readCookie(req, cookieName);
        
        // 만약 해당 쿠키가 없다면, 사용자가 오늘 해당 게시물을 본 적이 없다는 의미
        if (viewedToday.isEmpty()) {
            // 오늘 조회하지 않은 경우, 조회수 증가 로직 (DB에 조회수 1 증가)
        	MVCBoardDAO dao = new MVCBoardDAO();
            dao.updateVisitCount(postId);  // 조회수 증가 메서드 호출
            
            // 쿠키를 생성하여 기록 => 유효기간 1일
            CookieManager.makeCookie(resp, cookieName, "true", 24 * 60 * 60); 
            return true;
        } else {
            // 이미 조회한 경우
            return false;
        }
    }
}
