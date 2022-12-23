package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Manga;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import utils.AllowedFileName;
import validators.MangaValidator;
import validators.UserValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@MultipartConfig
@WebServlet(urlPatterns = "/redactor")
public class RedactorServlet extends HttpServlet {

    private MangaValidator mangaValidator;

    private AccountRepositoryImpl accountRepository;

    private MangaRepositoryImpl mangaRepository;

    private static String PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PATH = (String) getServletContext().getAttribute("path");
        accountRepository = (AccountRepositoryImpl) getServletContext().getAttribute("userRepository");
        mangaRepository = (MangaRepositoryImpl) getServletContext().getAttribute("mangaRepository");
        mangaValidator = (MangaValidator) getServletContext().getAttribute("mangaValidator");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!(mangaValidator.mangaNameInBase(req.getParameter("titleName")))) {
            Part file = req.getParts().stream().filter(Objects::nonNull).toList().get(0);
            System.out.println(file.getSubmittedFileName());
            System.out.println(AllowedFileName.rename(file.getSubmittedFileName(), req));
            String[] split = file.getSubmittedFileName().split("\\.");
            String extension = split[split.length - 1];
            String[] allowedExt = {"jpg", "jpeg", "png", "gif"};
            for (String s : allowedExt) {
                if (extension.equals(s)) {
                    String path = uploadFile(file, req);
                    try {
                        mangaRepository.save(
                                Manga.builder()
                                        .pageCount(Integer.valueOf(req.getParameter("pageCount")))
                                        .description(req.getParameter("titleDescription"))
                                        .name(req.getParameter("titleName"))
                                        .extension("." + s)
                                        .url(path)
                                        .status(req.getParameter("status"))
                                        .startDate(req.getParameter("date"))
                                        .fullDescription(req.getParameter("fullDescription"))
                                        .author(req.getParameter("author"))
                                        .alternativeName(req.getParameter("alName"))
                                        .type(req.getParameter("type"))
                                        .link(req.getParameter("link"))
                                        .build());
                    } catch (NumberFormatException e) {
                        req.setAttribute("errorPageCount", "nope");

                    }
                    break;
                }
            }
        } else {
            req.setAttribute("errorName", "nope");
        }
        req.getRequestDispatcher("/WEB-INF/views/redactor.jsp").forward(req, resp);
    }

    private String uploadFile(Part file,HttpServletRequest req) {
        try {
            Files.copy(file.getInputStream(), Path.of(PATH + AllowedFileName.rename(file.getSubmittedFileName(), req)), StandardCopyOption.REPLACE_EXISTING);
            return (PATH + AllowedFileName.rename(file.getSubmittedFileName(), req));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/redactor.jsp").forward(req, resp);
    }
}
