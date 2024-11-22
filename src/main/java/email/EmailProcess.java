package email;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smtp.NaverSMTP;

@WebServlet("/email.do")
public class EmailProcess extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청에서 전달된 값을 받아옵니다.
		String from = (String) req.getAttribute("from");
		String to = (String) req.getAttribute("to");
		String subject = (String) req.getAttribute("subject");
		System.out.println(from);
		System.out.println(to);
		System.out.println(subject);
		// 이메일 전송을 위한 파라미터 준비
		Map<String, String> emailInfo = new HashMap<>();
		emailInfo.put("from", from); // 보내는 사람
		emailInfo.put("to", to); // 받는 사람
		emailInfo.put("subject", subject); // 제목

		// 내용은 메일 포맷에 따라 다르게 처리
		String content = (String) req.getAttribute("content"); // 내용
		System.out.println(content);
		String format = (String) req.getAttribute("format"); // 메일 포맷(text)
		System.out.println(format);
		emailInfo.put("content", content);
		emailInfo.put("format", "text/plain;charset=UTF-8");

		try {
			NaverSMTP smtpServer = new NaverSMTP(); // 메일 전송 클래스 생성
			smtpServer.emailSending(emailInfo);// 전송
			System.out.print("이메일 전송 성공");
			req.setAttribute("successMessage", "이메일 전송이 성공적으로 완료되었습니다.");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		} catch (Exception e) {
			System.out.print("이메일 전송 실패");
			req.setAttribute("error", "이메일 전송에 실패했습니다. 다시 시도해 주세요.");
			e.printStackTrace();
		    req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}
}