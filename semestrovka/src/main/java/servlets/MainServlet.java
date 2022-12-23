package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;
import models.Manga;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "")
public class MainServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null) {
            req.getSession().invalidate();
            resp.sendRedirect("/");
        } else {
            if (req.getParameter("deleteManga") != null) {
                mangaRepository.delete(Long.valueOf(req.getParameter("deleteManga")));
            } else {
                Account user = (Account) req.getSession().getAttribute("user");
                user.add(Long.valueOf(req.getParameter("favourites")));
                accountRepository.saveFavouritesManga(user, Long.valueOf(req.getParameter("favourites")));
                List<Manga> mangas = new ArrayList<>();
                for (Long mangaId : user.getMangaId()) {
                    mangas.add(mangaRepository.findById(mangaId).get());
                }
                req.getSession().setAttribute("mangas", mangas);
            }

            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mangaList", mangaRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);
    }
}
