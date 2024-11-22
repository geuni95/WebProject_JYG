package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDTO;

public class AttendanceManager {

    // 사용자가 오늘 출석했는지 여부를 확인하는 메서드
    public static boolean hasAttendedToday(HttpServletRequest req, String userId) {
        // 쿠키 이름은 "attendance_" + userId로 설정
        String cookieName = "attendance_" + userId;
        String attendedToday = CookieManager.readCookie(req, cookieName);
        
        // 쿠키 값이 비어 있으면 출석하지 않은 것
        return !attendedToday.isEmpty();
    }

    // 출석 처리 및 댓글 작성
    public static boolean markAttendanceAndComment(HttpServletRequest req, HttpServletResponse resp, String userId, String comment) {
        // 이미 출석한 경우
        if (hasAttendedToday(req, userId)) {
            return false;  // 이미 출석했으면 댓글을 작성할 수 없음
        }

        // 댓글을 작성할 수 있도록 처리 (예: 세션에 저장)
        // 실제 DB에 저장하지 않으므로 세션이나 화면에만 표시할 수 있음
        HttpSession session = req.getSession();
        session.setAttribute("comment", comment);  // 세션에 댓글 저장 (예시로 화면에만 표시)

        // 오늘 출석을 기록한 것으로 간주하여 쿠키 생성
        String cookieName = "attendance_" + userId;
        CookieManager.makeCookie(resp, cookieName, "attended", 24 * 60 * 60);  // 쿠키 유효 기간 1일

        return true;  // 댓글 작성 성공
    }
}
