package utils;


import jakarta.servlet.http.HttpServletRequest;
import repositories.MangaRepository;
import repositories.MangaRepositoryImpl;

public class AllowedFileName {

    public static String rename(String name, HttpServletRequest request) {
        MangaRepository mangaRepository = (MangaRepositoryImpl) request.getServletContext().getAttribute("mangaRepository");
        String[] split = name.split("\\.");
        return (mangaRepository.getMaxId() + 1) + "." + split[split.length - 1];
    }
}
