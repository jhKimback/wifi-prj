<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
       body {
            font-family: 'Arial', sans-serif;
       }
       h1 {
        text-align: center;
       }
    </style>
</head>
<body>
    <%
        int count = 2;
    %>
    <div>
        <% if (count > 0) {%>
            <div style="text-align: center;">
                <h1><%=count%>건의 데이터를 성공적으로 저장했습니다.</h1>
                <a href="index.jsp">홈으로 돌아가기</a>
            </div>
        <% } else { %>
            <h1>데이터 저장 실패</h1>
        <% } %>
    </div>

</body>
</html>
