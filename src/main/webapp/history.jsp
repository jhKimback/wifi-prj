<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Service.HistoryService" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.History" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>위치 히스토리 목록</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <%
                HistoryService historyService = new HistoryService();
                List<History> list = historyService.showHistory();
                if (!list.isEmpty()) {
                    for (History history : list) {
            %>
            <tr>
            <form action="history-delete-submit.jsp" style="margin-top: 20px;">
                <td> <%=history.getId() %> </td>
                <td> <%=history.getLat() %> </td>
                <td> <%=history.getLnt() %> </td>
                <td> <%=history.getSearch_dttm() %> </td>
                    <td class="formsub" colspan="2">
                        <input type="hidden" name="id" value="<%= history.getId() %>">
                        <input type="submit" value="삭제">
                    </td>
            </form>
            <% } %>
            <% } else { %>
                <td colspan= "5">이전 히스토리가 없습니다.</td>
            <% } %>
            </tr>
        </tbody>
    </table>

</body>
</html>
