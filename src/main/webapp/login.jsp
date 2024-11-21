<%@page import="membership.MemberDAO"%>
<%@page import="membership.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Modern Architecture | Contact</title>

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

		<script>
	function validateForm(form){
		if (form.id.value ==""){
			alert("아이디를 입력하세요.");
			form.id.focus();
			return false;
		}
		if (form.pass.value == ""){
			alert("패스워드를 입력하세요.");
			form.pass.focus();
			return false;
		}
</script>

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
							<li class="menu-item"><a href="./list.do">자유게시판</a></li>
					        <li class="menu-item"><a href="./qalist.do">Q&A 게시판</a></li>
					        <li class="menu-item"><a href="./mvclist.do">자료실 게시판</a></li>
							<li class="menu-item current-menu-item"><a href="login.jsp">로그인</a></li>
							<li class="menu-item"><a href="join.jsp">회원가입</a></li>
						</ul> <!-- .menu -->
					</div> <!-- .main-navigation -->

					<div class="mobile-navigation"></div>
				</div>
			</div> <!-- .site-header -->

			<main class="main-content">
				
				<div class="page">
					<div class="container">
						<a href="index.jsp" class="button-back"><img src="images/arrow-back.png" alt="" class="icon">로그인 취소</a>

						<div class="row">
							<div class="col-md-8">
								<div>
								<img src="images/login.png" width = "800"/>
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
									<h2 class="section-title">LOGIN</h2>
									<p>if you want see board, login here!!</p>
									
								    <!-- 로그인 오류 메시지가 있으면 출력 -->
								    <c:if test="${not empty LoginErrMsg}">
								        <p style="color: red; font-weight: bold;">${LoginErrMsg}</p>
								    </c:if>
    
									<form name = "loginForm" method = "post" 
										action="LoginProcess.do" onsubmit="return validateForm(this);">
										<input type="text" name="id" placeholder="ID..">
										<input type="text" name="pass" placeholder="Password..">
										<p class="text-right">
											<button type="submit">로그인</button>
											<a href="findlogin.jsp" style="text-decoration: none;">
							                <button type="button" class="btn btn-link">ID/PASS 찾기</button>
							            	</a>
										</p>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div> <!-- .page -->

			</main> <!-- .main-content -->

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
		<script src="http://maps.google.com/maps/api/js?sensor=false&amp;language=en"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>