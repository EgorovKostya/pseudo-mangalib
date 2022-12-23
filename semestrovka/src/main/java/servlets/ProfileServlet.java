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

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

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
        Account user = (Account) req.getSession().getAttribute("user");
        user.removeManga(Long.valueOf(req.getParameter("delete")));
        mangaRepository.deleteMangasById(user.getId(), Long.valueOf(req.getParameter("delete")));
        List<Manga> mangas = new ArrayList<>();
        for (Long mangaId : user.getMangaId()) {
            mangas.add(mangaRepository.findById(mangaId).get());
        }
        req.getSession().setAttribute("mangas", mangas);
        resp.sendRedirect("/profile");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
