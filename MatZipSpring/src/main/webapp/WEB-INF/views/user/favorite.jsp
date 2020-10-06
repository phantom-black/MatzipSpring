<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container">
	<c:forEach items="${data}" var="item">
		<div class="f-item">
		<div class="img">
			<img src="/res/img/rest/${item.i_rest}/rec_menu/${item.menuList[0].menu_pic}" onError="this.style.display='none';">
		</div>
		<div class="ctnt">
			<div class="top">
				<div class="f-title">
					<div class="nm font-NotoSans-500">
						<a href="/rest/detail?i_rest=?${item.i_rest}">${item.rest_nm}</a>
					</div>
					<span class="addr">${item.rest_addr}</span>
				</div>
				<div class="f-fav">
					<span class="material-icons">favorite</span>
				</div>
			</div>
			<div class="bottom">
					<c:forEach items="${item.menuList}" var="recMenu" varStatus="status">
						<c:if test="${status.index > 0}">
							<div class="recMenuItem">
								<div class="pic">
									<img src="/res/img/rest/${item.i_rest}/rec_menu/${recMenu.menu_pic}">
								</div>
								<div class="info">
									<div class="nm">${recMenu.menu_nm}</div>
									<div class="price"><fmt:formatNumber type="number" value="${recMenu.menu_price}"/>원</div>
								</div>
							</div>	
						</c:if>
					</c:forEach>
				</div>
		</div>
		</div>
	</c:forEach>
</div>