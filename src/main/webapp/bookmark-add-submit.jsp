<%@ page import="Service.BookMarkService" %>
<%@ page import="dto.BookMarkGroup" %>
<%@ page import="dto.BookMark" %>
<%@ page import="java.sql.Timestamp" %>
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
    request.setCharacterEncoding("UTF-8");

    String groupName = request.getParameter("group_name");
    String mgrNo = request.getParameter("mgr_no");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    int affected = 0;

    BookMarkGroup bookmarkGroup = new BookMarkGroup();
    BookMark bookmark = new BookMark();
    bookmark.setX_swifi_mgr_no(mgrNo);
    bookmark.setRegister_dttm(timestamp.toString());
    bookmarkGroup.setGroup_name(groupName);

    BookMarkService bookmarkService = new BookMarkService();
    affected = bookmarkService.addBookMark(bookmark, bookmarkGroup);


%>
<script>
    <%
        String text = affected > 0 ? "북마크 정보를 추가하였습니다." : "북마크 정보를 추가하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-list.jsp";
</script>
</body>
</html>
