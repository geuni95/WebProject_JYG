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

	<script>
	function delete_confirm(idx){
		if(confirm("삭제하시겠습니까?")){
			location.href='./mvcdelete.do?idx=${ param.idx }';
		}
		else{
			alert("삭제 실패");
		}
	}
	</script>
	
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
					        <li class="menu-item"><a href="./list.do">자유게시판</a></li>
					        <li class="menu-item"><a href="project.html">Q&A 게시판</a></li>
					        <li class="menu-item current-menu-item"><a href="./mvclist.do">자료실 게시판</a></li>
					
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
					
						<h2>자유 게시판 - view 상태</h2>
					
						<table border="1" width="90%">
						    <colgroup>
						        <col width="15%"/> <col width="35%"/>
						        <col width="15%"/> <col width="*"/>
						    </colgroup> 
						    <tr>
						        <td>번호</td> <td>${ dto.idx }</td>
						        <td>작성자</td> <td>${ dto.name }</td>
						    </tr>
						    <tr>
						        <td>작성일</td> <td>${ dto.postdate }</td>
						        <td>조회수</td> <td>${ dto.visitcount }</td>
						    </tr>
						    <tr>
						        <td>제목</td>
						        <td colspan="3">${ dto.title }</td>
						    </tr>
						    <tr>
						        <td>내용</td>
						        <td colspan="3" height="100">
						        	${ dto.content }
						        	<c:if test="${ not empty dto.ofile }">
						        	<br />
						        	<c:choose>
						        		<c:when test="${ mimeType eq 'img' }">
						        			<img src="./Uploads/${ dto.sfile }" style="max-width:600px;" />
						        		</c:when>
						        		<c:when test="${ mimeType eq 'audio' }">
						        			<audio controls = "controls">
						        			<source src="./Uploads/${ dto.sfile }"type="audio/mp3"/></audio>
						        		</c:when>
						        		<c:when test="${ mimeType eq 'video' }">
						        			<video src="./Uploads/${ dto.sfile }"type="video/mp3" controls>
						        			your browser does not support the video tag.</video>
						        		</c:when>
						        	</c:choose>
						        	</c:if>
						        </td>
						    </tr> 
						    <tr>
						        <td>첨부파일</td>

						        <td>
						            <!-- 첨부한 파일이 있다면 다운로드 링크를 출력한다. -->
						            <c:if test="${ not empty dto.ofile }">
						                <!-- 로그인 여부를 확인 -->
						                <c:if test="${ not empty sessionScope.user }">
						                    <!-- 로그인한 사용자만 다운로드 링크를 보여줍니다 -->
						                    <a href="./mvcdownload.do?ofile=${ dto.ofile }&sfile=${ dto.sfile }&idx=${ dto.idx }">
						                       ${ dto.ofile } [다운로드]
						                    </a>
						                </c:if>
						                <c:if test="${ empty sessionScope.user }">
						                    <!-- 로그인하지 않은 경우 알림을 표시 -->
						                    로그인 후 다운로드 가능합니다.
						                </c:if>
						            </c:if>
						        </td>
						         <td>다운로드수</td>
						        <td>${ dto.downcount }</td>
						    </tr>  
						    <tr>
						        <td colspan="4" align="center">
						        	<button type="button"
						        		onclick="location.href='./mvcedit.do?idx=${ param.idx }';">수정하기</button>
						        	<!--[퀴즈1]삭제할건지 확인하기  -->
						            <button type="button"
						            	onclick="delete_confirm(${ param.idx });">삭제하기</button>
						            <button type="button" onclick="location.href='./mvclist.do';">
						                목록 바로가기
						            </button>
						        </td>
						    </tr>
						</table>

					</div>
				</div> <!-- .page -->

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