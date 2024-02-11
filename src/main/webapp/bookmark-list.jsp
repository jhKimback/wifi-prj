<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.BookMarkService" %>
<%@ page import="dto.BookMark" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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

    </style>
</head>
<body>
    <h1>북마크 목록</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 그룹명</th>
                <th>와이파이명</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%
            BookMarkService bookmarkService = new BookMarkService();
            List<BookMark> list = bookmarkService.showBookMark();
            if (!list.isEmpty()){
                for (BookMark bookmark: list) {
        %>
            <td><%=bookmark.getId()%></td>
            <td><%=bookmark.getGroup_name()%></td>
            <td ><a href="detail.jsp?mgr_no=<%= bookmark.getX_swifi_mgr_no() %>&"><%= bookmark.getX_swifi_main_nm() %></a></td>
            <td><%=bookmark.getRegister_dttm()%></td>
            <td>
                <div style="text-align:center;">
                    <a href="bookmark-delete.jsp?id=<%=bookmark.getId()%>">삭제</a>
                </div>
            </td>
        <tr>
        <% } %>
        <% } else { %>
             <td colspan= "5">이전 히스토리가 없습니다.</td>
        <% } %>
        </tr>
        </tbody>
    </table>
</body>
</html>
