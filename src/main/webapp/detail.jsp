<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Service.WifiService" %>
<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.Wifi" %>
<%@ page import="dto.BookMarkGroup" %>
<!DOCTYPE html>
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
        .formsub {
            text-align: center;
        }

    </style>
</head>
<body>
    <%
        String mgrNo = request.getParameter("mgr_no");
        WifiService wifiService = new WifiService();
        Wifi wifi = new Wifi();
        wifi.setX_swifi_mgr_no(mgrNo);
        wifi = wifiService.showInfoWifi(wifi);
            %>
    <h1>위치 히스토리 목록</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>
    <form action="bookmark-add-submit.jsp">
        <input type="hidden" name="mgr_no" value="<%=wifi.getX_swifi_mgr_no()%>">
        <select name="group_name" style="width: 150px;">

    <%
        BookMarkGroupService bookmarkGroupService = new BookMarkGroupService();
        List<BookMarkGroup> list = bookmarkGroupService.showBookMarkGroup();
        if (list != null) {
            for (BookMarkGroup bookmarkGroup : list) {
    %>
        <option value="<%=bookmarkGroup.getGroup_name()%>"><%=bookmarkGroup.getGroup_name()%></option>

    <% } %>
    <% } %>
        </select>
        <input type="submit" value="북마크 등록하기"></input>
    </form>
    <table>
            <tr>
                <th>거리(km)</th>
                <td><%=wifi.getDistance()%></td>
            </tr><tr>
                <th>관리번호</th>
                <td><%= wifi.getX_swifi_mgr_no() != null ? wifi.getX_swifi_mgr_no() : "" %></td>
            </tr><tr>
                <th>자치구</th>
                <td><%= wifi.getX_swifi_wrdofc() != null ? wifi.getX_swifi_wrdofc() : "" %></td>
            </tr><tr>
                <th>와이파이명</th>
                <td><%= wifi.getX_swifi_main_nm() != null ? wifi.getX_swifi_main_nm() : "" %></td>
            </tr><tr>
                <th>도로명 주소</th>
                <td><%= wifi.getX_swifi_adres1() != null ? wifi.getX_swifi_adres1() : "" %></td>
            </tr><tr>
                <th>상세 주소</th>
                <td><%= wifi.getX_swifi_adres2() != null ? wifi.getX_swifi_adres2() : "" %></td>
            </tr><tr>
                <th>설치 위치(층)</th>
                <td><%= wifi.getX_swifi_instl_floor() != null ? wifi.getX_swifi_instl_floor() : "" %></td>
            </tr><tr>
                <th>설치 기관</th>
                <td><%= wifi.getX_swifi_instl_ty() != null ? wifi.getX_swifi_instl_ty() : "" %></td>
            </tr><tr>
                <th>설치 유형</th>
                <td><%= wifi.getX_swifi_instl_mby() != null ? wifi.getX_swifi_instl_mby() : "" %></td>
            </tr><tr>
                <th>서비스 구분</th>
                <td><%= wifi.getX_swifi_svc_se() != null ? wifi.getX_swifi_svc_se() : "" %></td>
            </tr><tr>
                <th>망 종류</th>
                <td><%= wifi.getX_swifi_cmcwr() != null ? wifi.getX_swifi_cmcwr() : "" %></td>
            </tr><tr>
                <th>설치 년도</th>
                <td><%= wifi.getX_swifi_cnstc_year() != null ? wifi.getX_swifi_cnstc_year() : "" %></td>
            </tr><tr>
                <th>실내 외 구분</th>
                <td><%= wifi.getX_swifi_inout_door() != null ? wifi.getX_swifi_inout_door() : "" %></td>
            </tr><tr>
                <th>WIFI 접속 환경</th>
                <td><%= wifi.getX_swifi_remars3() != null ? wifi.getX_swifi_remars3() : "" %></td>
            </tr><tr>
                <th>x좌표</th>
                <td><%= wifi.getLat() != null ? wifi.getLat() : "" %></td>
            </tr><tr>
                <th>y좌표</th>
                <td><%= wifi.getLnt() != null ? wifi.getLnt() : "" %></td>
            </tr><tr>
                <th>작업일자</th>
                <td><%= wifi.getWork_dttm() != null ? wifi.getWork_dttm() : "" %></td>
            </tr>
    </table>

</body>
</html>
