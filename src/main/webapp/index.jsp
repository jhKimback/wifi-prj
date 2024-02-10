<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Service.WifiService" %>
<%@ page import="Service.HistoryService" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.Wifi" %>
<%@ page import="dto.History" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
           body {
                font-family: 'Arial', sans-serif;
           }
            table {
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                border:solid 1px #000;
                padding: 8px;
            }
            th {
                background-color: #3cb371;
            }
    </style>
</head>
<body>
    <%
        String lat = request.getParameter("lat") == null ? "0.0" : request.getParameter("lat");
        String lnt = request.getParameter("lnt") == null ? "0.0" : request.getParameter("lnt");
    %>
    <h1>와이파이 정보 구하기</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>

    <label for="latitude">LAT:</label>
    <input type="text" id="lat" name="latitude" value="<%=lat%>">
    <label for="longitude">LNT:</label>
    <input type="text" id="lnt" name="longitude" value="<%=lnt%>">

    <button id="btn_cur_position"><span>내 위치 가져오기</span></button>
    <button id="btn_near_wifi"><span>근처 Wifi 정보 보기</span></button>


    <div>
        <table>
            <thead>
            <tr>
                <th>거리(km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>

                <th>도로명 주소</th>
                <th>상세 주소</th>

                <th>설치 위치(층)</th>
                <th>설치 기관</th>
                <th>설치 유형</th>

                <th>서비스 구분</th>
                <th>망 종류</th>
                <th>설치 년도</th>
                <th>실내 외 구분</th>
                <th>WIFI 접속 환경</th>

                <th>x좌표</th>
                <th>y좌표</th>
                <th>작업일자</th>
            </tr>
            </thead>
            <tbody>
                <%
                    if (!("0.0").equals(lat) && !("0.0").equals(lnt)) {
                        WifiService wifiService = new WifiService();
                        List<Wifi> list = wifiService.showNearWifi(lat, lnt);

                        if (list != null) {
                            for (Wifi wifi : list) {
                %>
                    <tr>
                        <td><%=wifi.getDistance()%></td>
                        <td><%= wifi.getX_swifi_mgr_no() != null ? wifi.getX_swifi_mgr_no() : "" %></td>
                        <td><%= wifi.getX_swifi_wrdofc() != null ? wifi.getX_swifi_wrdofc() : "" %></td>
                        <td><a href="detail-wifi.jsp?mgrNo=<%= wifi.getX_swifi_mgr_no() %>&"><%= wifi.getX_swifi_main_nm() %></a></td>
                        <td><%= wifi.getX_swifi_adres1() != null ? wifi.getX_swifi_adres1() : "" %></td>
                        <td><%= wifi.getX_swifi_adres2() != null ? wifi.getX_swifi_adres2() : "" %></td>
                        <td><%= wifi.getX_swifi_instl_floor() != null ? wifi.getX_swifi_instl_floor() : "" %></td>
                        <td><%= wifi.getX_swifi_instl_ty() != null ? wifi.getX_swifi_instl_ty() : "" %></td>
                        <td><%= wifi.getX_swifi_instl_mby() != null ? wifi.getX_swifi_instl_mby() : "" %></td>
                        <td><%= wifi.getX_swifi_svc_se() != null ? wifi.getX_swifi_svc_se() : "" %></td>
                        <td><%= wifi.getX_swifi_cmcwr() != null ? wifi.getX_swifi_cmcwr() : "" %></td>
                        <td><%= wifi.getX_swifi_cnstc_year() != null ? wifi.getX_swifi_cnstc_year() : "" %></td>
                        <td><%= wifi.getX_swifi_inout_door() != null ? wifi.getX_swifi_inout_door() : "" %></td>
                        <td><%= wifi.getX_swifi_remars3() != null ? wifi.getX_swifi_remars3() : "" %></td>
                        <td><%= wifi.getLat() != null ? wifi.getLat() : "" %></td>
                        <td><%= wifi.getLnt() != null ? wifi.getLnt() : "" %></td>
                        <td><%= wifi.getWork_dttm() != null ? wifi.getWork_dttm() : "" %></td>
                    </tr>
                <% } %>
                <% } %>
                <% } else { %>
                    <td colspan='17'> 위치 정보를 입력한 후에 조회해 주세요. </td>
                <% } %>
            </tbody>
        </table>
    </div>

    <script>
        let getCurPosition = document.getElementById("btn_cur_position");
        let getNearestWifi = document.getElementById("btn_near_wifi");

        let lat = null;
        let lnt = null;

        window.onload = () => {
            lat = document.getElementById("lat").value;
            lnt = document.getElementById("lnt").value;
        }

        getCurPosition.addEventListener("click", function () {
            if ('geolocation' in navigator) {
                navigator.geolocation.getCurrentPosition(function (position){
                    let latitude = position.coords.latitude;
                    let longitude = position.coords.longitude;
                    document.getElementById("lat").value = latitude;
                    document.getElementById("lnt").value = longitude; // 여기서 latitude가 아닌 longitude로 수정
                })
            }
        });

        getNearestWifi.addEventListener("click", function (){
            let latitude = document.getElementById("lat").value;
            let longitude = document.getElementById("lnt").value;

            if (latitude !== "" && longitude !== "") {
                window.location.assign("http://localhost:8080/wifi/index.jsp?lat=" + latitude + "&lnt=" + longitude);
            } else {
                alert("위치 정보를 입력한 후에 조회해주세요.")
            }
        });
    </script>

</body>
</html>