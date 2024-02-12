<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dto.BookMarkGroup" %>
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
    int affected = 0;

    try {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String orderNo = request.getParameter("order_no");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int idInteger = Integer.parseInt(id);
        int orderNumber = Integer.parseInt(orderNo);

        dto.BookMarkGroup bookmarkGroup = new dto.BookMarkGroup();
        bookmarkGroup.setId(idInteger);
        bookmarkGroup.setGroup_name(name);
        bookmarkGroup.setOrder_no(orderNumber);
        bookmarkGroup.setModify_dttm(timestamp.toString());

        BookMarkGroupService bookmarkGroupService = new BookMarkGroupService();
        affected = bookmarkGroupService.editBookMarkGroup(bookmarkGroup); // 수정 필요

    } catch (NumberFormatException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<script>
    <%
        String text = affected > 0 ? "북마크 그룹 데이터를 수정하였습니다." : "데이터 수정에 실패하였습니다.";
    %>
    alert("<%= text %>");
    window.location.href = "bookmark-group.jsp";
</script>
</body>
</html>
