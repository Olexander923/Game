package org.example;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Отображаем стартовую страницу с предысторией игры, формой для ввода имени игрока,
 * кнопкой "начать игры"
 */
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override //показать страницу приветствия
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/welcome.jsp").forward(req,resp);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //получаем имя игрока
        String name = req.getParameter("name");

        //сохраняем имя в сессии
        HttpSession session = req.getSession(true);
        session.setAttribute("player", new Player(name));

        //переправляем в игру
        resp.sendRedirect(req.getContextPath() + "/game");
    }
}
