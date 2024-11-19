<%@page import="membership.MemberDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>HTML 게시판</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		
		<!--[if lt IE 9]>
		<script src="js/ie-support/html5.js"></script>
		<script src="js/ie-support/respond.js"></script>
		<![endif]-->
		
		 <style>
	        /* 전체 페이지를 화면 크기에 맞게 설정 */
	        html, body {
	            height: 100%;
	            margin: 0;
	            padding: 0;
	            display: flex;
	            flex-direction: column;
	        }
	
	        #site-content {
	            flex: 1;
	        }
	
	        footer.site-footer {
	            margin-top: auto; /* 푸터를 화면 하단에 고정 */
	        }
    	</style>
    
	</head>

	<body>
		
		<div id="site-content">
			<div class="site-header">
				<div class="container">
					<a href="index.jsp" id="branding">
						<img src="images/logo.png" alt="" class="logo">
						<div class="logo-text">
							<h1 class="site-title">HTML 게시판</h1>
							<small class="site-description">첨부기능까지</small>
						</div>
					</a> <!-- #branding -->

					<!-- Default snippet for navigation -->
					<div class="main-navigation">
					    <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
					    <ul class="menu">
					        <li class="menu-item"><a href="index.jsp">홈</a></li>
					        <li class="menu-item current-menu-item"><a href="./list.do">자유게시판</a></li>
					        <li class="menu-item"><a href="project.html">Q&A 게시판</a></li>
					        <li class="menu-item"><a href="./mvclist.do">자료실 게시판</a></li>
					
					        <!-- 로그인 여부에 따라 메뉴 항목을 변경 -->
					        <c:choose>
					            <c:when test="${not empty sessionScope.user}">
					                <!-- 로그인된 경우 -->
					                <li class="menu-item current-menu-item"><a href="logout.jsp">로그아웃</a></li>
                    				<li class="menu-item"><a href="profile.jsp">안녕하세요, ${user.name}님!</a></li>
					            </c:when>
					            <c:otherwise>
					                <!-- 로그인되지 않은 경우 -->
					                <li class="menu-item"><a href="login.jsp">로그인</a></li>
					            </c:otherwise>
					        </c:choose>
					
					        <li class="menu-item"><a href="join.jsp">회원가입</a></li>
					    </ul> <!-- .menu -->
					</div> <!-- .main-navigation -->

					<div class="mobile-navigation"></div>
				</div>
			</div> <!-- .site-header -->

			<main class="main-content">
				
				<div class="page">
					<div class="container">
					
						<h2>자유 게시판 글쓰기</h2>
						<form name="writeFrm" method="post"  
						      action="./write.do" onsubmit="return validateForm(this);">
						<table border="1" width="90%">
						    <tr>
						        <td>제목</td>
						        <td>
						            <input type="text" name="title" style="width:90%;" />
						        </td>
						    </tr>
						    <tr>
						        <td>내용</td>
						        <td>
						            <textarea name="content" style="width:90%;height:100px;"></textarea>
						        </td>
						    </tr>
						    <tr>
						        <td colspan="2" align="center">
						            <button type="submit">작성 완료</button>
						            <button type="reset">RESET</button>
						            <button type="button" onclick="location.href='./list.do';">
						                목록 바로가기
						            </button>
						        </td>
						    </tr>
						</table>    
						</form>
					</div>
				</div> <!-- .page -->
				
    <script type="text/javascript">
    function validateForm(form) {  // 필수 항목 입력 확인
        if (form.title.value == "") {
            alert("제목을 입력하세요.");
            form.title.focus();
            return false;
        }
        if (form.content.value == "") {
            alert("내용을 입력하세요.");
            form.content.focus();
            return false;
        }
    }
    </script>
    
			</main> <!-- .main-content -->

			<footer class="site-footer" style="margin-top: auto;">
				<div class="container">
					<div class="pull-left">
						<address>
							<strong>Project Name</strong>
							<p>HTML JSP board</p>
						</address>

						<a href="#" class="phone">+ 1 800 931 033</a>
					</div> <!-- .pull-left -->
					<div class="pull-right">

						<div class="social-links">
							<a href="#"><i class="fa fa-facebook"></i></a>
							<a href="#"><i class="fa fa-google-plus"></i></a>
							<a href="#"><i class="fa fa-twitter"></i></a>
							<a href="#"><i class="fa fa-pinterest"></i></a>

						</div>

					</div> <!-- .pull-right -->

					<div class="colophon">Copyright 2014 Company name. Designed by <a href="http://www.vandelaydesign.com/" title="Designed by VandelayDesign.com" target="_blank">VandelayDesign.com</a>. All rights reserved.</div>

				</div> <!-- .container -->
			</footer> <!-- .site-footer -->
		</div>

		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>