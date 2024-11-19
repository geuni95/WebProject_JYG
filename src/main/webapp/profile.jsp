<%@page import="membership.MemberDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		<title>프로필 수정</title>
			<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400|" rel="stylesheet" type="text/css">
			<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
			<link rel="stylesheet" href="style.css">
		
	</head>
<body>
	<div id="site-content">
		<div class="site-header">
			<div class="container">
				<a href="index.jsp" id="branding"><img src="images/logo.png" alt="" class="logo">
					<div class="logo-text"><h1 class="site-title">HTML 게시판</h1><small class="site-description">첨부기능까지</small></div></a>
					<div class="main-navigation">
					<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
						<ul class="menu">
							<li class="menu-item"><a href="index.jsp">홈</a></li>
							<li class="menu-item"><a href="./list.do">자유게시판</a></li>
					        <li class="menu-item"><a href="project.html">Q&A 게시판</a></li>
					        <li class="menu-item"><a href="./mvclist.do">자료실 게시판</a></li>
							<c:choose>
							<c:when test="${not empty sessionScope.user}">
							<li class="menu-item"><a href="logout.jsp">로그아웃</a></li>
							<li class="menu-item current-menu-item"><a href="profile.jsp">안녕하세요, ${user.name}님!</a></li>
							</c:when>
							<c:otherwise>
							<li class="menu-item"><a href="login.jsp">로그인</a></li>
							</c:otherwise>
							</c:choose>
							<li class="menu-item"><a href="join.jsp">회원가입</a></li>
						</ul>
					</div>
			</div>
		</div>
	</div>
		
			<main class="main-content">
				<div class="page">
					<div class="container">
						<a href="index.jsp" class="button-back"><img src="images/arrow-back.png" alt="" class="icon">프로필 수정 취소</a>

						<div class="row">
							<div class="col-md-8">
								<div>
								<img src="images/profile.png" width = "800" height="500"/>
								</div>

								<div class="contact-detail">
									<address>
										<div class="contact-icon">
											<img src="images/icon-marker.png" class="icon">
										</div>
										<p><strong>Company name INC.</strong> <br>가산디지털단지 kosmo</p>
									</address>
									<a href="#" class="phone"><span class="contact-icon"><img src="images/icon-phone.png" class="icon"></span> 010 5583 5688</a>
									<a href="#" class="email"><span class="contact-icon"><img src="images/icon-envelope.png" class="icon"></span> wkddprms@gmail.com</a>
								</div>
							</div>
							<div class="col-md-3 col-md-offset-1">
								<div class="contact-form">
									<h2 class="profile-container">프로필 수정</h2>
									<c:if test="${not empty sessionScope.user}">
										<form action="ProfileUpdateProcess.do" method="post">
											<div><label for="id">아이디:</label><input type="text" id="id" name="id" value="${sessionScope.user.id}" readonly /></div>
											<div><label for="name">이름:</label><input type="text" id="name" name="name" value="${sessionScope.user.name}" /></div>
											<div><label for="email">이메일:</label><input type="email" id="email" name="email" value="${sessionScope.user.email}" /></div>
											<div><label for="phone">전화번호:</label><input type="text" id="phone" name="phone" value="${sessionScope.user.phone}" /></div>
											<div><button type="submit">수정하기</button></div>
										</form>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div> <!-- .page -->
			</main> <!-- .main-content -->

			<footer class="site-footer">
			<div class="container">
				<div class="pull-left">
					<address><strong>Company Name</strong><p>532 Avenue Street, Omaha</p></address>
					<a href="#" class="phone">+ 1 800 931 033</a>
				</div>
				<div class="pull-right">
					<div class="social-links">
						<a href="#"><i class="fa fa-facebook"></i></a>
						<a href="#"><i class="fa fa-google-plus"></i></a>
						<a href="#"><i class="fa fa-twitter"></i></a>
						<a href="#"><i class="fa fa-pinterest"></i></a>
					</div>
				</div>
			<div class="colophon">Copyright 2014 Company name. Designed by <a href="http://www.vandelaydesign.com/" target="_blank">VandelayDesign.com</a>. All rights reserved.</div>
			</div>
			</footer>
		</div>
		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
	</body>
</html>