<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dto.BookMarkGroup" %>

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
 <form action="bookmark-group-delete-submit.jsp" style="margin-top: 20px;">
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
                <td colspan="2">
                    <div class="formsub">
                        <a href="bookmark-group.jsp">돌아가기</a> |
                        <input type="submit" value="삭제">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
