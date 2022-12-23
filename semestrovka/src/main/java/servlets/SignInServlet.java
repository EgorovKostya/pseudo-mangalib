package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import models.Account;
import models.Manga;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import utils.HashUtil;
import validators.InputValidator;
import validators.UserValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class SignInServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    private UserValidator userValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        userValidator = (UserValidator) getServletContext().getAttribute("userValidator");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String usernameValid = InputValidator.validate(req.getParameter("usernameL"));

        Account user = Account.builder()
                .password(HashUtil.hash(req.getParameter("passwordL")))
                .username(usernameValid)
                .mangaId(new ArrayList<>())
                .build();
        if (userValidator.userInBase(user,req)) {
            user.setMangaId(mangaRepository.getUserManga(user.getId()));
            user.setRole(accountRepository.getRole(user.getId()));
            List<Manga> mangas = new ArrayList<>();
            for (Long mangaId : user.getMangaId()) {
                mangas.add(mangaRepository.findById(mangaId).get());
            }
            req.getSession().setAttribute("mangas", mangas);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/");
        } else {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
