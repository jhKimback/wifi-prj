<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dto.BookMarkGroup" %>
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

    String name = request.getParameter("name");
    String orderNo = request.getParameter("order_no");
    int affected = 0;

        try {
            int orderNumber = Integer.parseInt(orderNo);
            BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();

            BookMarkGroup bookMarkGroup = new BookMarkGroup();
            bookMarkGroup.setGroup_name(name);
            bookMarkGroup.setOrder_no(orderNumber);

            affected = bookMarkGroupService.deleteBookMarkGroup(bookMarkGroup);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
%>
<script>
    <%
        String text = affected > 0 ? "북마크 그룹 데이터를 삭제하였습니다." : "삭제할 북마크 그룹 데이터가 없습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-group.jsp";
</script>
</body>
</html>
