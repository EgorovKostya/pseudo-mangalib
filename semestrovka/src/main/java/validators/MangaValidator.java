package validators;

import models.Account;
import models.Manga;
import repositories.MangaRepository;
import repositories.MangaRepositoryImpl;
import utils.PostgresConnectionProvider;

import java.util.List;

public class MangaValidator {

    MangaRepository mangaRepository = new MangaRepositoryImpl(PostgresConnectionProvider.getConnection());

    public boolean mangaNameInBase (String username) {
        List<String> mangas = mangaRepository.findAll().stream().map(Manga::getName).toList();
        return mangas.contains(username);
    }
}
