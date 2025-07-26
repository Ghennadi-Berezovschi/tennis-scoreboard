<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🎾 Finished Matches</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1 {
            margin-bottom: 20px;
        }

        a.button {
            display: inline-block;
            margin-bottom: 20px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            padding: 8px 14px;
            border-radius: 4px;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="text"] {
            padding: 6px;
            font-size: 14px;
        }

        button {
            padding: 6px 12px;
            font-size: 14px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1976D2;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #888;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f0f0f0;
        }

        .pagination {
            margin-top: 20px;
            text-align: center;
        }

        .pagination a,
        .pagination span {
            margin: 0 5px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
        }

        .pagination a:hover {
            background-color: #388E3C;
        }

        .pagination .disabled {
            background-color: #ccc;
            color: #666;
            pointer-events: none;
            cursor: default;
        }

        p {
            color: #666;
        }
    </style>
</head>
<body>

<h1>🎾 Finished Matches</h1>

<a class="button" href="${pageContext.request.contextPath}/new-match">➤ Start New Match</a>

<form method="get" action="${pageContext.request.contextPath}/matches-page">
    <label>
        Filter by player name:
        <input type="text" name="filter_by_player_name" value="${filter_by_player_name}">
    </label>
    <button type="submit">Search</button>
</form>

<c:if test="${empty matches}">
    <p>No matches found.</p>
</c:if>

<c:if test="${not empty matches}">
    <table>
        <tr>
            <th>#</th>
            <th>Player 1</th>
            <th>Player 2</th>
            <th>Winner</th>
        </tr>

        <c:forEach var="match" items="${matches}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${match.playerFirstName}</td>
                <td>${match.playerSecondName}</td>
                <td>${match.playerWinner}</td>
            </tr>
        </c:forEach>
    </table>

    <div class="pagination">
        <c:set var="filterQuery" value="" />
        <c:if test="${not empty filter_by_player_name}">
            <c:set var="filterQuery" value="&amp;filter_by_player_name=${fn:escapeXml(filter_by_player_name)}" />
        </c:if>

        <c:choose>
            <c:when test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/matches-page?page=${currentPage - 1}${filterQuery}">← Prev</a>
            </c:when>
            <c:otherwise>
                <span class="disabled">← Prev</span>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="disabled">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/matches-page?page=${i}${filterQuery}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/matches-page?page=${currentPage + 1}${filterQuery}">Next →</a>
            </c:when>
            <c:otherwise>
                <span class="disabled">Next →</span>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>

</body>
</html>
