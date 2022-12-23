package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;
import models.Manga;
import models.Message;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import repositories.MessageRepositoryImpl;
import validators.InputValidator;

import java.io.IOException;

@WebServlet(urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    private MessageRepositoryImpl messageRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
        messageRepository = (MessageRepositoryImpl) getServletContext().getAttribute("messageRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account user = (Account) req.getSession().getAttribute("user");
        Message message = Message.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .message(InputValidator.validate(req.getParameter("message")))
                .build();
        messageRepository.save(message);
        resp.sendRedirect("/chat");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("messages", messageRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
    }
}
