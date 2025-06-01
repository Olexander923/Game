<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Текстовый квест</title>
    <style>
        body {
            background-image: url('/images/ufo.jpg');
            background-size: cover;
            background-attachment: fixed;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            color: white;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
        }

        .content {
            padding: 20px;
            border-radius: 0 0 10px 10px;
            text-align: center;
            width: 100%;
            max-width: 600px;
            margin-top: 0;
        }

        .input-group {
            display: flex;
            gap: 10px;
            margin: 20px auto;
            width: 90%;
            max-width: 400px;
        }

        input {
            padding: 10px;
            flex: 1;
            border: none;
            border-radius: 4px;
            font-size: 16px;
        }

        button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        h1 {
            margin-top: 0;
        }
    </style>
</head>
<body>
<div class="content">

    <h3>Ты увидел нечто похожее на железный большой круг в небе, затем яркую вспышку, после чего все как в тумане...
    Очнувшись ты оказался внутри незнакомого пространства, ничего не различая от яркого света.
    Холодный неизвестный голос произнес...</h3>
    <h1>Добро пожаловать !</h1>

    <p>Пожалуйста, введите ваше имя:</p>

    <form class="input-group" method="post" action="/welcome">
        <input type="text" name="player_name" required placeholder="Ваше имя">
        <button type="submit">Начать игру</button>
    </form>
</div>
</body>
</html>
