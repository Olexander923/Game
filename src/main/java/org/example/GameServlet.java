package org.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Сервелет обрабатывает логику игры:переход между вопросами, проверка ответов
 */
@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[DEBUG] doGet вызван для пути: " + req.getRequestURI());
        HttpSession session = req.getSession(true);
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }
        System.out.println("[DEBUG] Вопрос: " + gameState.getCurrentQuestion());
        System.out.println("[DEBUG] Опции: " + gameState.getCurrentOptions());

        req.setAttribute("question", gameState.getCurrentQuestion());
        req.setAttribute("options", gameState.getCurrentOptions());
        req.setAttribute("status", gameState.getStatus());



        req.getRequestDispatcher("/game.jsp").forward(req, resp);
        System.out.println("[DEBUG] doGet вызван");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String answer = req.getParameter("answer");
        if (answer == null || answer.trim().isEmpty()) {
            resp.sendError(400, "Выберите вариант ответа");
            return;
        }

        HttpSession session = req.getSession(true);
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }

        gameState.processAnswer(answer);

        if (gameState.isGameOver()) {
            Player player = (Player) session.getAttribute("player");
            if (player == null) {
                player = new Player("Игрок");
            }
            player.incrementGamesPlayed();
            session.setAttribute("player", player);


            resp.sendRedirect("/result");
        } else {
            resp.sendRedirect(req.getContextPath() + "/game");
        }
        System.out.println("[DEBUG] doPost: текущий вопрос = " + gameState.getCurrentQuestion());
        System.out.println("[DEBUG] doPost: полученный ответ = " + answer);

    }
}
