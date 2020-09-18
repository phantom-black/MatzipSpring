<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sectionContainerCenter">
	<div>
		<form id="frm" action="/restaurant/restRegProc" method="post" onsubmit="return chkFrm()">
			<div><input type="text" name="nm" placeholder="가게명"></div>
			<div>
				<input type="text" name="addr" placeholder="주소" onkeyup="changeAddr()" style="width:200px;">
				<button type="button" onclick="getLatLng()">좌표 가져오기</button><span id="resultGetLatLng"></span>
			</div>
			<input type="hidden" name="lat" value="0">
			<input type="hidden" name="lng" value="0">
			<div>
				카테고리: 
				<select name="cd_category">
					<option value="0">--선택--</option>
					<c:forEach items="${categoryList}" var="item">
						<option value="${item.cd}">${item.val}</option>
					</c:forEach>
				</select>
			</div>
			<div><input type="submit" value="등록"></div>
		</form>
	</div>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a4e58e65f7bc7583810d3845840f24f3&libraries=services"></script>
	<script>
		function chkFrm() {
			if(frm.nm.value.length == 0) {
				alert('가게명을 입력해주세요')
				frm.nm.focus()
				return false
			} else if(frm.addr.value.length < 9) {
				alert('주소를 확인해 주세요')
				frm.addr.focus()
				return false
			} else if(frm.lat.value == '0' || frm.lng.value == '0') {
				alert('좌표값을 가져와 주세요')
				return false
			} else if(frm.cd_category.value == '0') {
				frm.cd_category.focus()
				return false
			}
		}	
	
		function changeAddr() {
			resultGetLatLng.innerText = ''
			frm.lat.value = '0'
			frm.lng.value = '0'
		}
	
		const geocoder = new kakao.maps.services.Geocoder();
	
		function getLatLng() {
			const addrStr = frm.addr.value
			
			if(addrStr.length < 9) {
				alert('주소를 확인해 주세요')
				frm.addr.focus()
				return
			}
			
			geocoder.addressSearch(addrStr, function(result, status) {
				if (status === kakao.maps.services.Status.OK) {
			        console.log(result[0]);
			        
			        if(result.length > 0) {
			        	resultGetLatLng.innerText = 'v'
			        	frm.lat.value = result[0].y
				        frm.lng.value = result[0].x
			        }
			    }	
			});		
		}
	</script>
</div> 