package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import models.Account;
import repositories.AccountRepositoryImpl;
import utils.HashUtil;
import validators.InputValidator;
import validators.UserValidator;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {


    private AccountRepositoryImpl accountRepository;

    private UserValidator userValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        userValidator = (UserValidator) getServletContext().getAttribute("userValidator");
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String usernameValid = InputValidator.validate(req.getParameter("usernameR"));

        Account account = Account.builder()
                .username(usernameValid)
                .password(HashUtil.hash(req.getParameter("passwordR")))
                .mangaId(new ArrayList<>())
                .build();
        if (!(userValidator.userNameInBase(account.getUsername()))) {
            accountRepository.save(account);
            resp.sendRedirect("/login");
        } else {
            req.setAttribute("nameIsPresent", "занято");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
}
