package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.AccountRepositoryImpl;
import repositories.MangaRepositoryImpl;
import validators.MangaValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/garbage/*")
public class GarbageServlet extends HttpServlet {

    private static String PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PATH = (String) getServletContext().getAttribute("path");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStream = new FileInputStream(PATH + req.getPathInfo().substring(1));

        byte[] image = inputStream.readAllBytes();
        resp.getOutputStream().write(image);
    }
}
