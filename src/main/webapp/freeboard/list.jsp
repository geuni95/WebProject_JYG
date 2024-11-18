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

        /* 헤더 스타일 */
        header {
            text-align: center;
            padding: 10px;
        }

        /* 메인 콘텐츠 영역 스타일 */
        main {
            flex-grow: 1; /* 남은 공간을 모두 채우도록 설정 */
            padding: 20px;
        }

        /* 푸터 스타일 */
        footer {
            text-align: center;
            padding: 10px;
            position: relative;
            bottom: 0;
            width: 100%;
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
					        <li class="menu-item"><a href="about.html">Q&A 게시판</a></li>
					        <li class="menu-item"><a href="project.html">자료실 게시판</a></li>
					
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
					
						<h2>자유 게시판</h2>
						
						<!-- 검색 폼 -->
						<form method="get">  
						<table border="1" width="90%">
						<tr>
						    <td align="center">
						        <select name="searchField">
						            <option value="title">제목</option>
						            <option value="content">내용</option>
						        </select>
						        <input type="text" name="searchWord" />
						        <input type="submit" value="검색하기" />
						    </td>
						</tr>
						</table>
						</form>
						
						<table border="1" width="90%">
						    <tr>
						        <th width="10%">번호</th>
						        <th width="*">제목</th>
						        <th width="15%">작성자</th>
						        <th width="10%">조회수</th>
						        <th width="15%">작성일</th>
						    </tr>
						<c:choose>
							<c:when test="${ empty boardLists }">
						        <tr>
						            <td colspan="6" align="center">
						                등록된 게시물이 없습니다^^*
						            </td>
						        </tr>
						    </c:when>
						    <c:otherwise>
						    	<c:forEach items="${ boardLists }" var="row" varStatus="loop">
						    	<tr align="center">
		    		<td>
		    			${ map.totalCount - (((map.pageNum-1) * map.pageSize)
		    				 + loop.index) }
		    		</td>
						    		<td align="left">
						    			<a href="./view.do?idx=${ row.idx }">
						    				${ row.title }</a>
						    		</td>
						    		<td>${ row.id }</td>
						    		<td>${ row.visitcount }</td>
						    		<td>${ row.postdate }</td>
						    	</tr>
						       	</c:forEach>
						   	</c:otherwise>
						</c:choose>
						</table>
						
						<table border="1" width="90%">
						    <tr align="center">
					 	        <td>
						            ${ map.pagingImg }
						        </td>
						        <td width="100"><button type="button"
						            onclick="location.href='./write.do';">글쓰기</button></td>
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