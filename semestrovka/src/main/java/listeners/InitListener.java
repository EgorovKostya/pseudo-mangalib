package listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repositories.AccountRepositoryImpl;
import repositories.FaqRepositoryImpl;
import repositories.MangaRepositoryImpl;
import repositories.MessageRepositoryImpl;
import utils.PostgresConnectionProvider;
import validators.MangaValidator;
import validators.UserValidator;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userRepository",
                new AccountRepositoryImpl(PostgresConnectionProvider.getConnection()));
        sce.getServletContext().setAttribute("mangaRepository",
                new MangaRepositoryImpl(PostgresConnectionProvider.getConnection()));
        sce.getServletContext().setAttribute("userValidator", new UserValidator());
        sce.getServletContext().setAttribute("faqRepository",
                new FaqRepositoryImpl(PostgresConnectionProvider.getConnection()));
        sce.getServletContext().setAttribute("messageRepository",
                new MessageRepositoryImpl(PostgresConnectionProvider.getConnection()));

        sce.getServletContext().setAttribute("mangaValidator", new MangaValidator());

        sce.getServletContext().setAttribute("path", "C:\\Users\\egoro\\OneDrive\\Рабочий стол\\ThirdSem\\images\\  ");
    }
}
