package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Manga;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import validators.MangaValidator;

import java.io.IOException;

@WebServlet(urlPatterns = "/titles")
public class MangaServlet extends HttpServlet {

    private MangaValidator mangaValidator;

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
        mangaValidator = (MangaValidator) getServletContext().getAttribute("mangaValidator");

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("title") != null) {
            if (mangaValidator.mangaNameInBase(req.getParameter("title"))) {
                Manga manga = mangaRepository.getMangaByName(req.getParameter("title"));
                req.setAttribute("manga", manga);
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/manga.jsp").forward(req, resp);
    }
}
