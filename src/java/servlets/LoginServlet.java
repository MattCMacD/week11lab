/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String what = request.getParameter("logout");
        if (request.getParameter("logout") != null && request.getParameter("logout").equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("errormessage", "logged out");
        } else if (request.getParameter("logout") != null && request.getParameter("logout").equals("forgotIt")) {
            getServletContext().getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);
        } else if (request.getParameter("logout") != null && request.getParameter("logout").equals("forgot")) {

            AccountService as = new AccountService();

            String path = getServletContext().getRealPath("/WEB-INF");

            //FIX METHOD IN ACCOUNT SERVICE AND USERDB, LOOK AT EXAMPLE AARON GAVE IN THE LAB INSTRUCTIONS (NO. 7)
            if (as.forgotPassword(request.getParameter("emailaddr"), path) == true) {
                HttpSession session = request.getSession();
                session.setAttribute("msg", "Your password has been sent to your email");
                response.sendRedirect("login");

            }
        } else if (request.getParameter("logout") == null) {
            HttpSession session = request.getSession();
            request.setAttribute("errormessage", session.getAttribute("msg"));
            session.removeAttribute("msg");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("errormessager", "fill in both fields, please");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp/").forward(request, response);
        }

        AccountService as = new AccountService();

        String path = getServletContext().getRealPath("/WEB-INF");

        if (as.checkLogin(username, password, path) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("home");
        } else {
            request.setAttribute("errormessager", "invalid login");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp/").forward(request, response);
        }
    }
}
