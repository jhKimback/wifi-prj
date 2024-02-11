<%@ page import="Service.BookMarkService" %>
<%@ page import="dto.BookMark" %>
<%@ page import="java.sql.SQLException" %>
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
            text-align: center;
        }

    </style>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");
    Integer idInteger = Integer.parseInt(id);
    BookMarkService bookMarkService = new BookMarkService();
    BookMark bookmark = new BookMark();
    bookmark = bookMarkService.showOneBookMark(idInteger);
%>
    <h1>위치 히스토리 목록</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>
    <h2>북마크를 삭제하시겠습니까?</h2>
    <form action="bookmark-delete-submit.jsp">
    <input type="hidden" name="id" value="<%=id%>">
        <table>
            <tr>
                <th>북마크 그룹명</th>
                <td><%= bookmark.getGroup_name() != null ? bookmark.getGroup_name() : "" %></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%= bookmark.getX_swifi_main_nm() != null ? bookmark.getX_swifi_main_nm() : "" %></td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><%= bookmark.getRegister_dttm() != null ? bookmark.getRegister_dttm() : "" %></td>
            </tr>
                <td colspan="2" class="formsub">
                    <div style="text-align:center;">
                        <a href="bookmark-list.jsp">돌아가기</a> |
                        <input type="submit" value="삭제">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</html>
