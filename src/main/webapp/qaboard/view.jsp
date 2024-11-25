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

	<script>
	function delete_confirm(idx){
		if(confirm("삭제하시겠습니까?")){
			location.href='./qadelete.do?idx=${ param.idx }';
		}
		else{
			alert("삭제 실패");
		}
	}
	</script>
	
	<body>
	<!-- 에러 메시지가 있을 경우 JavaScript로 팝업 메시지 띄우기 -->
	<c:if test="${not empty error}">
	   <script type="text/javascript">
	       alert("${error}");  // 서블릿에서 설정한 에러 메시지를 alert()로 표시
	   </script>
	</c:if>
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
					        <li class="menu-item current-menu-item"><a href="./qalist.do">Q&A 게시판</a></li>
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
			            <h2>Q/A 게시글 (${dto.title}) 내용</h2>
			
			            <!-- 게시글 테이블 -->
			            <table border="1" width="90%">
			                <colgroup>
			                    <col width="15%"/> <col width="35%"/>
			                    <col width="15%"/> <col width="*"/>
			                </colgroup>
			                <tr>
			                    <td>번호</td> <td>${dto.idx}</td>
			                    <td>작성자</td> <td>${dto.name}</td>
			                </tr>
			                <tr>
			                    <td>작성일</td> <td>${dto.postdate}</td>
			                    <td>조회수</td> <td>${dto.visitcount}</td>
			                </tr>
			                <tr>
			                    <td>제목</td>
			                    <td colspan="3">${dto.title}</td>
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
						    </tr>						    <tr>
						        <td colspan="4" align="center">
						        	<button type="button"
						        		onclick="location.href='./qaedit.do?idx=${ param.idx }';">수정하기</button>
						        	<!--[퀴즈1]삭제할건지 확인하기  -->
						            <button type="button"
						            	onclick="delete_confirm(${ param.idx });">삭제하기</button>
						            <button type="button" onclick="location.href='./qalist.do';">
						                목록 바로가기
						            </button>
						        </td>
						    </tr>
						</table><br/>
				
						<!-- 댓글 작성 폼 -->
						<form action="qaview.do?idx=${dto.idx}&action=write" method="post">
						    <textarea name="content" required placeholder="댓글을 작성하세요..." rows="5"></textarea><br>
						    <input type="submit" value="댓글 작성">
						</form>
						
						<!-- 댓글 목록 표시 -->
						<h3>댓글</h3>
						<div class="comment-list">
						    <c:forEach var="comment" items="${commentList}">
						        <div class="comment-item">
						            <div class="comment-header">
						                <div class="comment-info">
						                    <br/><strong>${comment.id}</strong> <span>${comment.postdate}</span>
						                </div>
						            </div>
						            <div class="comment-body">
						                <p>${comment.content}</p>
						            </div>
									<!-- 댓글 좋아요 버튼 -->
									<div class="comment-actions">
									    <c:choose>
									        <c:when test="${hasLiked}">
									            <a href="qaview.do?idx=${dto.idx}&action=unlike&commentIdx=${comment.idx}" class="like-button">좋아요 취소</a>
									        </c:when>
									        <c:otherwise>
									            <a href="qaview.do?idx=${dto.idx}&action=like&commentIdx=${comment.idx}" class="like-button">좋아요</a>
									        </c:otherwise>
									    </c:choose>
									    
									    <!-- 좋아요 수 표시 -->
									    <span>${comment.likes}명이 좋아요👍🏻</span>
									</div>
						            
						            <!-- 댓글 수정 및 삭제 버튼 -->
						            <c:if test="${comment.isDeleted != 'Y' && sessionScope.user != null && sessionScope.user.id == comment.id}">
						                <div class="comment-actions">
						                    <a href="qaview.do?idx=${dto.idx}&action=editForm&commentIdx=${comment.idx}">수정</a> |
						                    <a href="qaview.do?idx=${dto.idx}&action=delete&commentIdx=${comment.idx}">삭제</a>
						                </div>
						            </c:if>
						        </div>
						
						        <!-- 댓글 수정 폼 (수정 버튼 클릭 시 표시) -->
								<c:if test="${param.action == 'editForm' && param.commentIdx == comment.idx}">
								    <form action="qaview.do" method="post">								    	
								    	<input type="hidden" name="idx" value="${dto.idx}" />
								    	<input type="hidden" name="action" value="edit" />								    
								        <input type="hidden" name="commentIdx" value="${comment.idx}">
								        <textarea name="content" required>${comment.content}</textarea><br>
								        <input type="submit" value="수정 완료">
								    </form>
								</c:if>
						    </c:forEach>
						</div>
			
						<!-- 페이징 -->
						<div class="pagination">
						    <c:forEach begin="1" end="${totalPages}" var="page">
						        <c:choose>
						            <c:when test="${page == currentPage}">
						                <span class="current-page">${page}</span>
						            </c:when>
						            <c:otherwise>
						                <a href="qaview.do?idx=${dto.idx}&page=${page}">${page}</a>
						            </c:otherwise>
						        </c:choose>
						    </c:forEach>
						</div>
						
			            <script>
			                function delete_confirm(idx) {
			                    if (confirm("정말 삭제하시겠습니까?")) {
			                        location.href = "./qadelete.do?idx=" + idx;
			                    }
			            </script>
			
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