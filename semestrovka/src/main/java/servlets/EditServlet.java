package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import utils.HashUtil;
import validators.InputValidator;
import validators.UserValidator;

import java.io.IOException;

@WebServlet(urlPatterns = "/edit")
public class EditServlet extends HttpServlet {

    private UserValidator userValidator;

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
        userValidator = (UserValidator) getServletContext().getAttribute("userValidator");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account user = (Account) req.getSession().getAttribute("user");
        String valid = InputValidator.validate(req.getParameter("newUsername"));
        if (req.getParameter("newUsername") != null) {
            if (!(userValidator.userNameInBase(valid))) {
                user.setUsername(valid);
                accountRepository.updateUsername(valid, user.getId());
            } else {
                req.setAttribute("present", "error");
            }
        }
        if (req.getParameter("newPassword") != null) {
            user.setPassword(HashUtil.hash(req.getParameter("newPassword")));
            accountRepository.updatePassword(HashUtil.hash(req.getParameter("newPassword")), user.getId());
        }
        if (req.getParameter("del") != null) {
            accountRepository.delete(user.getId());
            req.getSession().invalidate();
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }
}
