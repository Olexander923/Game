package org.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Отвечает только за рестарт
 */
@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            GameState gameState = (GameState) session.getAttribute("gameState");
            if (gameState != null) {
                gameState.reset();
            }
        }
        resp.sendRedirect("/welcome");
    }
}
