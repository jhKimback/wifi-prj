<%@ page import="Service.BookMarkService" %>
<%@ page import="dto.BookMark" %>
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

    String id = request.getParameter("id");
    Integer idInteger = Integer.parseInt(id);
    int affected = 0;

        try {
            BookMarkService bookMarkService = new BookMarkService();

            BookMark bookmark = new BookMark();
            bookmark.setId(idInteger);
            affected = bookMarkService.deleteBookMark(bookmark);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
%>
<script>
    <%
        String text = affected > 0 ? "북마크 정보를 삭제하였습니다." : "북마크 정보를 삭제하지 못했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-list.jsp";
</script>
</body>
</html>
