<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.tennisscoreboard.model.MatchScore" %>
<%@ page import="com.example.tennisscoreboard.service.MatchScoreCalculationService" %>

<!DOCTYPE html>
<html>
<head>
    <title>Match Score</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1 {
            margin-bottom: 20px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #888;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f0f0f0;
        }

        .button {
            padding: 8px 14px;
            margin: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        .finished {
            color: darkgreen;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h1>🎾 Current Match Score</h1>

<%
    MatchScore match = (MatchScore) request.getAttribute("match");
    MatchScoreCalculationService calc = new MatchScoreCalculationService();
    String matchUuid = (String) request.getAttribute("matchUuid");
%>

<table>
    <tr>
        <th>Player</th>
        <th>Points</th>
        <th>Games</th>
        <th>Sets</th>
    </tr>
    <tr>
        <td><%= match.getFirstPlayerName() %></td>
        <td><%= calc.getPointsDisplay(match.getFirstPlayerPoints(), match.getSecondPlayerPoints()) %></td>
        <td><%= match.getFirstPlayerGames() %></td>
        <td><%= match.getFirstPlayerSets() %></td>
    </tr>
    <tr>
        <td><%= match.getSecondPlayerName() %></td>
        <td><%= calc.getPointsDisplay(match.getSecondPlayerPoints(), match.getFirstPlayerPoints()) %></td>
        <td><%= match.getSecondPlayerGames() %></td>
        <td><%= match.getSecondPlayerSets() %></td>
    </tr>
</table>

<% if (match.isFinished()) { %>
<p class="finished"> The match is finished.</p>
<form action="${pageContext.request.contextPath}/end-match" method="post">
    <input type="hidden" name="uuid" value="<%= matchUuid %>">
    <button type="submit" class="button">Save Match</button>
</form>
<% } else { %>
<form action="${pageContext.request.contextPath}/match-point" method="post">
    <input type="hidden" name="uuid" value="<%= matchUuid %>">
    <button type="submit" name="player" value="1" class="button"><%= match.getFirstPlayerName() %> wins point</button>
    <button type="submit" name="player" value="2" class="button"><%= match.getSecondPlayerName() %> wins point</button>
</form>
<% } %>

</body>
</html>
