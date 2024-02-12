<%@ page import="Service.WifiService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <%
        WifiService wifiService = new WifiService();
        int count = wifiService.getWifiJson();
    %>

    <%--와이파이 개수 디스플레이 --%>
    <div>
        <% if (count > 0) {%>
            <div class="formsub">
                <h1><%=count%>건의 데이터를 성공적으로 저장했습니다.</h1>
            </div>
        <% } else { %>
            <div class="formsub">
                <h1>데이터 저장 실패</h1>
            </div>
        <% } %>
        <div class="formsub">
            <a href="index.jsp">홈으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
