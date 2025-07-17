<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🎾 New Match</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 40px;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #444;
            margin-bottom: 30px;
        }

        form {
            max-width: 400px;
            margin: 0 auto;
            background-color: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-bottom: 15px;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 20px;
        }

        button:hover {
            background-color: #388E3C;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #2196F3;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h1>🎾 Create a New Match</h1>

<form action="${pageContext.request.contextPath}/new-match" method="post">
    <label>
        Player 1 Name:
        <input type="text" name="player1" required>
    </label>

    <label>
        Player 2 Name:
        <input type="text" name="player2" required>
    </label>

    <button type="submit">Start Match</button>
</form>

<a href="${pageContext.request.contextPath}/matches" class="back-link">← Back to match list</a>

</body>
</html>
