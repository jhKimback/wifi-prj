<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
    <h1>북마크 그룹</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br></br>
    <form action="bookmark-group-add.jsp">
        <input type="submit" value="북마크 그룹 이름 추가">
    </form>

    <%
        BookMarkGroupService bookmarkGroupService = new BookMarkGroupService();
        List<BookMarkGroup> bookmarkGroupList = bookmarkGroupService.showBookMarkGroup();
    %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 그룹명</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <% if (bookmarkGroupList.isEmpty()) { %>
                <tr>
                    <td colspan="6"> 정보가 존재하지 않습니다.</td>
                </tr>
            <% } else {
                for (BookMarkGroup bookmarkGroup: bookmarkGroupList) { %>
                    <tr>
                        <td> <%=bookmarkGroup.getId() %> </td>
                        <td> <%=bookmarkGroup.getGroup_name() != null ? bookmarkGroup.getGroup_name() : "" %> </td>
                        <td> <%=bookmarkGroup.getOrder_no() %> </td>
                        <td><%=bookmarkGroup.getRegister_dttm() != null ? bookmarkGroup.getRegister_dttm() : "" %></td>
                        <td> <%=bookmarkGroup.getModify_dttm() != null ? bookmarkGroup.getModify_dttm() : "" %> </td>
                        <td>
                            <div class="formsub">
                                <a href="bookmark-group-edit.jsp?id=<%=bookmarkGroup.getId()%>">수정</a> |
                                <a href="bookmark-group-delete.jsp?id=<%=bookmarkGroup.getId()%>">삭제</a>
                            </div>
                        </td>
                    </tr>
                <% }
            } %>
        </tbody>
    </table>

</body>
</html>
