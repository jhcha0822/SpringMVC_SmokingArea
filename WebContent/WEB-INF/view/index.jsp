<%@page import="com.sds.smoking.common.Pager"%>
<%@page import="com.sds.smoking.domain.SmokingArea"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
	// 선언 영역
	// Pager pager = new Pager();
%>
<%
	// 메소드 영역
	List<SmokingArea> smokingAreaList = (List<SmokingArea>)request.getAttribute("smokingAreaList");
	// pager.init(smokingAreaList, request); // 페이징 처리
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>광진구 흡연구역</title>
<%@ include file='../inc/header.jsp' %>
<style type="text/css">
	
</style>
</head>
<script type="text/javascript">

	let map;

	function deg2rad(deg) {
		return deg * (Math.PI/180);
	}
	
	function Haversine_formula(lat1, lon1, lat2, lon2) {
		// 두 지점 간의 거리 계산
		let dLat = deg2rad(lat2 - lat1);
		let dLon = deg2rad(lon2 - lon1);

		let a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        		Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        		Math.sin(dLon/2) * Math.sin(dLon/2);
		let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		let distance = 6371 * c * 1000;    // Distance in m
		return distance;
	}
	
	function sortTable(n) { // https://insurang.tistory.com
		const table = document.getElementById("areaList");
		const tbody = table.querySelector("tbody");
		const rows = Array.from(tbody.querySelectorAll("tr"));

		rows.sort((rowA, rowB) => {
			const cellA = rowA.querySelectorAll("td")[n].textContent;
		    const cellB = rowB.querySelectorAll("td")[n].textContent;
		  	return cellA.localeCompare(cellB, undefined, {numeric: true, sensitivity: 'base'});
		});

		table.setAttribute("data-sort-dir", "asc");

		table.removeChild(tbody);
		rows.forEach(row => tbody.appendChild(row));
		table.appendChild(tbody);
	}
	
	function hideTable(n) { // 가장 가까운 테이블 n개만 보여주기
		let table = document.getElementById("table");
		for(let i=table.rows.length; i>n; i--) {
			table.deleteRow(-1); // 하단 행 삭제
		}
	}

	// map 초기화
	function initMap() {
		let mapProp= {
		  center:new google.maps.LatLng(37.54766, 127.0850), // 광진구 좌표
		  zoom:18,
		};
		map = new google.maps.Map(document.getElementById("content"), mapProp);
	}
	
	// 마커와 정보 추가하기: 매개변수 위도, 경도
	function createMarker(lati, longi, name){
		let marker = new google.maps.Marker({
		  position:new google.maps.LatLng(lati, longi),
		  animation:google.maps.Animation.BOUNCE
		});
		marker.setMap(map);
		map.panTo(marker.position);
	}
	
	function parse() {
		location.href="/smoking/getApi";
	}
	
	// 버튼 누를때 동작
	function search() {
		// 검색한 위치로 지도 중앙값 설정, 마커 띄우기
		createMarker($("#lati").val(), $("#longi").val(), "현 위치");
		// 흡연구역 목록 DB에서 가져오기
		$.ajax({
			url: "/smoking/list",
			type: "GET",
			success: function(result, status, xhr) {
				console.log("서버의 응답 정보는 ", result);
				let table = "";
				for(let i=0; i<result.length; i++) {
					let json = result[i];
					table += "<tr>";
					table += "<td>"+json.area_nm+"</td>";
					table += "<td>"+json.area_desc+"</td>";
					table += "<td>"+Haversine_formula($("#lati").val(), $("#longi").val(), json.latitude, json.longitude)+"</td>";
					// 안보이게 위도와 경도도 넣기
					table += "<td style='visibility:hidden; position:absolute;'>"+json.latitude+"</td>";
					table += "<td style='visibility:hidden; position:absolute;'>"+json.longitude+"</td>";
					table += "</tr>";
				}
				$("#table").html(table); 
				sortTable(2);  // 거리순 정렬
				hideTable(10); // 가장 가까운 10건만 보여주기
				$("#spin").toggleClass("spinner-border spinner-border-sm");
			}
		});
	}
	
	/*
	// 한 건의 상세 데이터를 받아오기 위한 함수
	function getPosition() {
		let table = document.getElementById("areaList");
		let rowList = table.rows;
		
		for(i=1; i<rowLst.length; i++) { // thead 제외
			let row = rowList[i];
			row.onclick = function(){
				return function(){
					let name = this.cells[0];
					let detail = this.cells[1];
					let dist = this.cells[2];
					let lati = this.cells[3];
					let longi = this.cells[4];
					// 마커 띄우기
					createMarker(lati, longi, name);
				}; // return end
			}(row); // onclick end
		} // for end
	} // function end
	*/
	
	let latitest;
	
	$(function(){ // $( document ).ready( function(){} );
		// (위도,경도)를 붙여넣는 텍스트필드에 키보드에서 손을 떼면
		// 문자열을 분리하여 위도와 경도 텍스트필드에 출력
		$("#mock").on("keyup", function(){
			if($(this).val().length>15) {
				let arr = $(this).val().split(",");
				$("#lati").val(arr[0]);
				$("#longi").val(arr[1].trim()); // trim: 공백 제거				
			}
		});
		
		$("#parse").click(function(){
			parse(); // API 파싱
		});
		
		$("#search").click(function(){
			search(); // DB 값 읽어오기
			$("#spin").toggleClass("spinner-border spinner-border-sm");
		});
		
		$("#table").click(function(){
			let rowList =  this.rows;
			// console.log("rowList", rowList);
			
			for (i=0; i<rowList.length; i++) {
				var row = rowList[i];
				var tdsNum = row.childElementCount;
				
				row.onclick = function() {
			          return function() {
			        	  let name = this.cells[0].innerHTML;
			        	  let lati = this.cells[3].innerHTML;
			        	  let longi = this.cells[4].innerHTML;
			        	  createMarker(lati, longi, name);
			          };
			   	}(row);
			}
		});
		
	});
	
</script>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3" id="aside">
				<p>
					<button id="parse" type="button" class="btn btn-primary btn-block">
						API 호출 → DB입력
					</button>
				</p>
				<div class="form-group">
					<label for="mock" class="form-label">좌표:</label>
					<input type="text" class="form-control form-control-sm" id="mock" placeholder="위도와 경도 입력">
				</div>
				<div class="form-group">
					<label for="lati" class="form-label">위도:</label>
					<input type="text" class="form-control form-control-sm" id="lati" placeholder="위도">
				</div>
				<div class="form-group">
					<label for="longi" class="form-label">경도:</label>
					<input type="text" class="form-control form-control-sm" id="longi" placeholder="경도">
				</div>
				<p><button id="search" type="button" class="btn btn-primary btn-block">
					<span id="spin"></span>
						가까운 흡연구역 검색
				</button></p>
				<table class="table table-bordered table-hover table-striped text-center" id="areaList">
					<thead>
						<tr>
							<th>장소명</th>
							<th>상세위치</th>
							<th>거리</th>
						<tr>
					</thead>
					<tbody id="table">
						<!-- Ajax로 채워질 부분 -->
					</tbody>
				</table>
			</div>
			<div class="col-sm-9" id="content">
				지도
			</div>
		</div>
	</div>
</body>
</html>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAGl4v22xE7sU94Z5rC4WzkUsxZZCCaEnM&callback=initMap"></script>