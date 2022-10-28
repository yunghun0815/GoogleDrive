<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/css/main.css">
<link rel="stylesheet" type="text/css" href="/css/main-myDrive.css">
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<title>Google Drive</title>
</head>
<script type="text/javascript">
	$(function(){
		var id = $("#id").val();
		
		/* 우측마우스 이벤트 */
		$(".fileList").contextmenu(function(e){
			 //Get window size:
		    var winWidth = $(document).width();
		    var winHeight = $(document).height();
		    //Get pointer position:
		    var posX = e.pageX;
		    var posY = e.pageY;
		    //Get contextmenu size:
		    var menuWidth = $("#right-click-form").width();
		    var menuHeight = $("#right-click-form").height();
		    //Security margin:
		    var secMargin = 10;
		    //Prevent page overflow:
		    if(posX + menuWidth + secMargin >= winWidth
		    && posY + menuHeight + secMargin >= winHeight){
		      //Case 1: right-bottom overflow:
		      posLeft = posX - menuWidth - secMargin + "px";
		      posTop = posY - menuHeight - secMargin + "px";
		    }
		    else if(posX + menuWidth + secMargin >= winWidth){
		      //Case 2: right overflow:
		      posLeft = posX - menuWidth - secMargin + "px";
		      posTop = posY + secMargin + "px";
		    }
		    else if(posY + menuHeight + secMargin >= winHeight){
		      //Case 3: bottom overflow:
		      posLeft = posX + secMargin + "px";
		      posTop = posY - menuHeight - secMargin + "px";
		    }
		    else {
		      //Case 4: default values:
		      posLeft = posX + secMargin + "px";
		      posTop = posY + secMargin + "px";
		    };
		    //Display contextmenu:
		    $("#right-click-form").css({
		      "left": posLeft,
		      "top": posTop
		    }).show();
		    
		    /* id, type 찾아서 컨트롤러로 전송(이름바꾸기, 공유하기, 삭제) */
		    let type = $(this).find("#importantType").val();
			let key = $(this).find("#importantKey").val(); 
			let title = $(this).find("#importantTitle").val();
			$("#event-changeName").click(function(){
				var inputString = prompt('이름 바꾸기', title);
				$.ajax({
					url: "/change/name" ,
					type: "POST",
					data: {
						id: id,
						type: type,
						key: key,
						title: inputString
					},
					success: function(result){
						console.log(result);
						location.reload();
					}
				});
			});
		    	
		    	
		    	
		    //Prevent browser default contextmenu.
		    return false;
		  });
		  //Hide contextmenu:
		  $(document).click(function(){
		    $("#right-click-form").hide();
		  });
		
		
		
		var myDrive = $("#myDrive");
		
		let menu = $(".main nav ul li");
		//좌측 메뉴 클릭
		var click = '<c:out value='${click}' />';
		$(".content>div").hide();
		if(click != ''){
			menu.eq(click).addClass("menu-click-li");
			menu.eq(click).find("span").addClass("menu-click-span");
			
			
			myDrive.show();
		}
		/* 좌측 메뉴 클릭 이벤트 */
	 	menu.click(function(){
			let index = $(this).index();
			location.href= "/drive/"+index+"/"+id;
			if(index == 0){
				myDrive.show();
			}
		}); 
		//별(중요파일) 클릭
		var star = $(".star");
		star.click(function(){
			if(click == 3){
				$(this).parent().parent().hide();
			}else{
				$(this).hide();
				$(this).prev().show();
				$(this).next().show();
			}
			let type = $(this).parent().find("#importantType").val();
			let key = $(this).parent().find("#importantKey").val();
			 $.ajax({
				url: "/important/check?id="+id+"&type="+type+"&key="+key,
				type: "GET",
				success: function(result){
					console.log(result);
				}
			});
		});
	 	
	 	
		var selectFolder = $(".selectPath");
		
		/* 현재경로 */
	 	 $.ajax({
			url: "/directory/find?id="+id,
			type: "GET",
			success: function(result){
				if($("#upperNo").val() == null || $("#upperNo").val() == ''){
					$("#upperNo").val(result);	
					$("#directoryNo").val(result);
				}
				
				/* console.log(result);
				var option = `<option value=` + result[0]['NO'] + `>/</option>`;
				for(var i=1; i<result.length; i++){
					option += `<option value=` + result[i]['NO'] + `>` + result[i]['PATH'] + `</option>`;
				}
				
				selectFolder.html(option); */
			}
		}); 
	});
</script>
<body>
	<jsp:include page="common/header.jsp"></jsp:include>
	<section class="main flex">
		 <div style="display: none">
		 <sec:authorize access="isAuthenticated()">
		  	<sec:authentication property="principal" var="user" />
	       	<c:catch var="e">
	       		${user.username}
				<input type="hidden" id="id" value="${user.username}">
			</c:catch>
			<c:if test="${ e != null }">
				<c:if test="${empty user.attributes.email}">
					<input type="hidden" id="id" value="${user.attributes.response.email}">
				</c:if>
				<c:if test="${empty user.attributes.response.email}">
					<input type="hidden" id="id" value="${user.attributes.email}">
				</c:if>
			</c:if>
		</sec:authorize>
		</div>
		<nav>
			<div class="new-button">	<!-- 새로만들기 버튼 -->
				<img src="/images/plus.png">
				<button>새로 만들기</button>
				<div class="upload-box">
					<span class="upload-btn" data-bs-toggle="modal" data-bs-target="#modal-folder">폴더 만들기</span><br>
					<span class="upload-btn" data-bs-toggle="modal" data-bs-target="#modal-file">파일 업로드</span>
				</div>
				</div><!-- 클릭 시 폴더 선택 후 업로드(모달)  -->
			<ul> <!-- 좌측 메뉴바 -->
				<li><img src="/images/drive.png"><span>내 드라이브</span></li>
				<li><img src="/images/share.png"><span>공유 문서함</span></li>
				<li><img src="/images/lately.png"><span>최근 문서함</span></li>
				<li><img src="/images/important.png"><span>중요 문서함</span></li>
				<li><img src="/images/trash.png"><span>휴지통</span></li>
			</ul>
			<div class="capacity">
				<p><img src="/images/capacity.png"><span>저장용량</span></p>
				<div class="capacity-bar">
					<div class="bar-gray">
						<!-- 전체 -->
					</div>
					<div class="bar-blue">
						<!-- 사용량 -->
					</div>
					<span>15GB 중 494MB 사용</span><br>
				</div>
				<button>저장용량 구매</button>						
			</div>
		</nav>
		<div class="content">	<!-- 메인 화면 -->
			<!-- 내 드라이브 -->
			<div id="myDrive">
				<c:if test="${click == 0}">
					<div class="nav-path">
						<c:forEach items="${pathList}" var="result">
							<a class="before-a" href="/drive/0/${id}?no=${result.no}">
								<!-- <img class="dir-img" src="/images/return.png"> -->
								<c:if test="${result.title == '/'}">
									<span style="font-weight: bold">내 드라이브</span>
								</c:if>
								<c:if test="${result.title !=  '/'}">
									<span style="color: black">${result.title}</span>
								</c:if>
							</a>
							<c:if test="${param.no != result.no}">
									<span class="path-next">></span>
								</c:if>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${click == 3}">
					<div class="nav-path">
						<span class="nav-title">중요 문서함</span>
					</div>
				</c:if>
				<c:if test="${not empty directoryList || not empty fileList}">
					<table id="myDrive-table">
						<tr>
							<th>이름</th>
							<th>소유자</th>
							<th>등록일</th>
							<th>파일크기</th>
						</tr>
						<c:if test="${not empty directoryList}">
								<c:forEach items="${directoryList}" var="result">
									<tr class="directoryList fileList">
										<td>
											<c:if test="${result.important == 0}">
												<img src="/images/star_blank.png" class="star blank_star" value="0">
												<img src="/images/star_color.png"  class="star color_star"  style="display: none;">
											</c:if>
											<c:if test="${result.important == 1}">
												<img src="/images/star_color.png"  class="star color_star">
												<img src="/images/star_blank.png" class="star blank_star" style="display: none;">
											</c:if>
											<a href="/drive/0/${result.id}?no=${result.no}">
												<img class="dir-img" src="/images/folder.png">
												<span style="font-weight: bold;">${result.title}</span>
											</a>
											<input type="hidden" id="importantType" value="directory">
											<input type="hidden" id="importantKey" value="${result.no}">
											<input type="hidden" id="importantTitle" value="${result.title}">
										</td>
										<td>
											<span>${name}</span>
										</td>
										<td>
											<span>${result.uploadDate}</span>
										</td>
										<td>
											<span>-</span>
										</td>
									</tr>
								</c:forEach>
						</c:if>
						<c:if test="${not empty fileList}">
							<c:forEach items="${fileList}" var="result">
								<tr class="fileList">
									<td>
										<c:if test="${result.important == 0}">
											<img src="/images/star_blank.png" class="star blank_star">
											<img src="/images/star_color.png"  class="star color_star" style="display: none;">
										</c:if>
										<c:if test="${result.important == 1}">
											<img src="/images/star_color.png"  class="star color_star">
											<img src="/images/star_blank.png" class="star blank_star" style="display: none;">
										</c:if>
										<a href="/file/${result.fileId}">
											<img src="/images/files.png" style="width: 20px">
											<span style="font-weight: bold;">${result.fileName}</span>
										</a>
										<input type="hidden" id="importantType" value="file">
										<input type="hidden" id="importantKey" value="${result.fileId}">
										<input type="hidden" id="importantTitle" value="${result.fileName}">
									</td>
									<td>
										<span>${name}</span>
									</td>
									<td>
										<span>${result.fileUploadDate}</span>
									</td>
									<td>
										<span>
										<c:choose>
											<c:when test="${result.fileSize gt 1024*1024}">
												<fmt:formatNumber value="${result.fileSize/1024/1024}" pattern=".0" />MB
											</c:when>
											<c:when test="${result.fileSize gt 1024}">
												<fmt:formatNumber value="${result.fileSize/1024}" pattern=".0"/>KB
											</c:when>
											<c:otherwise>
												${result.fileSize}
											</c:otherwise>
										</c:choose>
										</span>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</c:if>
				<c:if test="${empty directoryList && empty fileList && not empty click}">
					<div class="emptyContent">
						<img src="/images/lion.gif"><br>
						<span>폴더나 파일을 업로드해주세요</span>
					</div>
				</c:if>
			</div>
		</div>
	</section>
</body>
	<!-- modal 폴더 업로드 -->
		<div class="modal fade" id="modal-folder" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" style="top: 30%;">
		  	<form action="/upload/folder" method="post">
			    <div class="modal-content">
			      <div class="modal-body">
			      <p>폴더 만들기</p>
			      	 <!-- <span>경로 : </span>
 				     <select class="selectPath" name="upperNo">
			      	</select> -->
			      	<input type="hidden" id="upperNo" name="upperNo"  value="${param.no}">
			        <input type="text" name="title" required="required" placeholder="새로운 폴더명">
			        <sec:authorize access="isAuthenticated()">
				        <sec:authentication property="principal" var="user" />
				        <input type="hidden" name="id" value="
				        	<c:catch var="e">
								${user.username}
							</c:catch>
							<c:if test="${ e != null }">
								<c:if test="${empty user.attributes.email}">
									${user.attributes.response.email}
								</c:if>
								<c:if test="${empty user.attributes.response.email}">
									${user.attributes.email}
								</c:if>
							</c:if>
				         ">
				     </sec:authorize>
			         <div class="submit-box">
				         <span class="reset-btn" data-bs-dismiss="modal">취소</span>
				         <input type="submit" value="만들기">
			         </div> 
			      </div>
			    </div>
		    </form>
		  </div>
		</div>
	<!-- modal 파일 업로드 -->
		<div class="modal fade" id="modal-file" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" style="top: 30%;">
		  	<form action="/upload/file" method="post" enctype="multipart/form-data">
			    <div class="modal-content">
			      <div class="modal-body">
			      <p>파일 업로드</p>
			      	<!-- <span>경로 : </span>
			      	<select class="selectPath" name="path">
			      	</select> -->
			      	<input type="hidden" id="directoryNo" name="no" value="${param.no}">
			        <input type="file" name="file" required="required">
			        <sec:authorize access="isAuthenticated()">
				        <sec:authentication property="principal" var="user" />
				        <input type="hidden" name="id" value="
				        	<c:catch var="e">
								${user.username}
							</c:catch>
							<c:if test="${ e != null }">
								<c:if test="${empty user.attributes.email}">
									${user.attributes.response.email}
								</c:if>
								<c:if test="${empty user.attributes.response.email}">
									${user.attributes.email}
								</c:if>
							</c:if>
				         ">
			         </sec:authorize>
			         <div class="submit-box">
				         <span class="reset-btn" data-bs-dismiss="modal">취소</span>
				         <input type="submit" value="업로드">
			         </div> 
			      </div>
			    </div>
		    </form>
		  </div>
		</div>
		<!-- 마우스 우측클릭 이벤트 -->
		<div id="right-click-form" style="display: none">
			<ul>
				<li id="event-changeName">
					<span>이름 바꾸기</span>
				</li>
				<li id="event-delete">
					<span>삭제하기</span>
				</li>
				<li id="event-share">
					<span>공유하기</span>
				</li>
			</ul>
		</div>
</html>