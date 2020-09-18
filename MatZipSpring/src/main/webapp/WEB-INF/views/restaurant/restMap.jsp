<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.label {margin-bottom: 96px;}
	.label * {display: inline-block;vertical-align: top;}
	.label .left {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
	.label .center {background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 12px;line-height: 24px;}
	.label .right {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>
<div id="sectionContainerCenter">
	<div id="mapContainer" style="width: 100%; height: 100%;"></div>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a4e58e65f7bc7583810d3845840f24f3"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	
	<script>
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.865585, 128.592569), //지도의 중심좌표.
			level: 3 //지도의 레벨(확대, 축소 정도)
		};
	
		var map = new kakao.maps.Map(mapContainer, options); //지도 생성 및 객체 리턴
		
		console.log(map.getCenter())
		
		function getRestaurantList() {
			axios.get('/restaurant/ajaxGetList').then(function(res) {
				console.log(res.data)
				
				res.data.forEach(function(item) {
					createMarker(item)
				})
			})
		}
		
		getRestaurantList()
		
		// 마커 생성
		function createMarker(item) {
			var content = document.createElement('div')
			content.className = 'label'
			
			var leftSpan = document.createElement('span')
			leftSpan.className = 'left'
			
			var rightSpan = document.createElement('span')
			rightSpan.className = 'right'
			
			var centerSpan = document.createElement('span')
			centerSpan.className = 'center'
			centerSpan.innerText = item.nm
			
			content.appendChild(leftSpan)
			content.appendChild(centerSpan)
			content.appendChild(rightSpan)
			
			// var ctnt = `<div class='label'<span class='left'></span><span class='center'>\${item.nm}</span><span class='right'></span></div>`
			var mPos = new kakao.maps.LatLng(item.lat, item.lng)
			var marker = new kakao.maps.CustomOverlay({
				position: mPos,
				content: content
			});
			
			addEvent(content, 'click', function() {
				console.log('마커 클릭: ' + item.i_rest)
				moveToDetail(item.i_rest)
			})
			/*
			kakao.maps.event.addListener(marker, 'click', function() {
				console.log('마커 클릭: ' + item.i_rest) // 콜백함수의 클로저
			});
			*/
			marker.setMap(map) 
		}
		
		function moveToDetail(i_rest) {
			location.href = '/restaurant/restDetail?i_rest=' + i_rest
		}
		
		function addEvent(target, type, callback) {
			if(target.addEventListener) {
				target.addEventListener(type, callback);
			} else {
				target.attachEvent('on' + type, callback);
			}
		}
		
		// check for geolocation support
		if(navigator.geolocation) {
			console.log('Geolocation is supported!')
			
			var startPos;
			var geoSuccess = function(position) {
				startPos = position;
				console.log('lat: ' + startPos.coords.latitude)
				console.log('lng: ' + startPos.coords.longitude)
				
				if(map) {
					var moveLatLon = new kakao.maps.LatLng(startPos.coords.latitude, startPos.coords.longitude)
					
					map.panTo(moveLatLon)
				}
				
			}
			navigator.geolocation.getCurrentPosition(geoSuccess);
			
		} else {
			console.log('Geolocation is not supported for this Browser/OS.')
		}
	</script>
</div> 