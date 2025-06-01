package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.ServerException;

/**
 * Отображает результат игры, перед этим сделав проверку состояния игры
 */
@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            resp.sendRedirect("welcome");
            return;
        }

        req.setAttribute("status", gameState.getStatus());
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }
}
