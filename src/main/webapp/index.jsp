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
					        <li class="menu-item current-menu-item"><a href="index.jsp">홈</a></li>
					        <li class="menu-item"><a href="./list.do">자유게시판</a></li>
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

			<div class="hero hero-slider">
				<ul class="slides">
					<li data-bg-image="images/board1.jpg">
						<a href="./list.do"/>
						<div class="container">
							<div class="slide-title">
								<span>Free board</span> <br>
								<span>click to move</span> <br>
							</div>
						</div>
					</li>
					<li data-bg-image="images/QAboard.jpg">
						<a href="index.jsp"/>
						<div class="container">
							<div class="slide-title">
								<span>Q&A board</span> <br>
								<span>click to move</span> <br>
							</div>
						</div>
					</li>
					<li data-bg-image="images/downloadboard.jpg">
						<a href="index.jsp"/>
						<div class="container">
							<div class="slide-title">
								<span>download</span> <br>
								<span>click to move</span> <br>
							</div>
						</div>
					</li>
				</ul> <!-- .slides -->
			</div> <!-- .hero-slider -->

			<footer class="site-footer">
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