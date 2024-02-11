<%@ page import="Service.HistoryService" %>
<%@ page import="dto.History" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>위치 히스토리 목록</title>
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
    int affected = 0;

        try {
            int idInteger = Integer.parseInt(id);
            HistoryService historyService = new HistoryService();

            History history = new History();
            history.setId(idInteger);

            affected = historyService.deleteHistory(history);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
%>
<script>
    <%
        String text = affected > 0 ? "기록 데이터를 삭제하였습니다." : "삭제할 기록 데이터가 없습니다.";
    %>
    alert("<%= text %>");
    location.href = "history.jsp";
</script>
</body>
</html>
