package repositories;

import models.Manga;

public interface MangaRepository extends Crud<Manga, Long> {
    Long getMaxId();
}
