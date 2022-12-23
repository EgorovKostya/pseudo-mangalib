package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.AccountRepositoryImpl;
import repositories.FaqRepositoryImpl;
import repositories.MangaRepositoryImpl;

import java.io.IOException;

@WebServlet(urlPatterns = "/faq")
public class FaqServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    private FaqRepositoryImpl faqRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
        faqRepository = (FaqRepositoryImpl)  getServletContext().getAttribute("faqRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("questions", faqRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/views/faq.jsp").forward(req, resp);
    }
}
