<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dto.BookMarkGroup" %>

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
        .formsub {
            text-align: center;
        }

    </style>
</head>
<body>
    <%
        Integer id = Integer.parseInt(request.getParameter("id"));
        BookMarkGroupService bookmarkGroupService = new BookMarkGroupService();

        BookMarkGroup bookmarkGroup = bookmarkGroupService.showOneBookMarkGroup(id);

        String defaultName = bookmarkGroup.getGroup_name();
        Integer defaultOrderNo = bookmarkGroup.getOrder_no();
    %>
    <h1>북마크 그룹</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>
 <form action="bookmark-group-edit-submit.jsp" style="margin-top: 20px;">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><input type="text" name="name" value="<%= defaultName %>"></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="text" name="order_no" value="<%= defaultOrderNo %>"></td>
            </tr>
            <tr>
                <td colspan="2" class="formsub">
                    <div style="text-align:center;">
                        <a href="bookmark-group.jsp">돌아가기</a> |
                        <input type="submit" value="수정">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
