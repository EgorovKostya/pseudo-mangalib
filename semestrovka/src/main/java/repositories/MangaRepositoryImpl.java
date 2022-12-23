package repositories;

import mappers.RowMapper;
import models.Account;
import models.Manga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MangaRepositoryImpl implements MangaRepository {
    //language=SQL
    private static final String SQL_INSERT_MANGA = "insert into manga(name, description, page_count, url, extension, type, start_date, status, author, alternative_name, full_description, link) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //language=SQL
    private static final String SQL_UPDATE_MANGA = "update manga set name = ?, description = ?, page_count = ?, url = ?, extension = ?, type = ?, start_date = ?, status = ?, author = ?, alternative_name = ?, full_description = ?, link = ? where id = ?";
    //language=SQL
    private static final String SQL_DELETE_MANGA = "update manga set deleted = true where id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL_MANGA = "select * from manga where deleted = false";
    //language=SQL
    private static final String SQL_FIND_BY_ID_MANGA = "select * from manga where id = ?";
    //language=SQL
    private static final String SQL_FIND_MANGA_BY_NAME = "select * from manga where name = ?";
    //language=SQL
    private static final String SQL_FIND_FAVOURITES_MANGA = "select * from account_manga where account_id = ?";
    //language=SQL
    private static final String SQL_DELETE_MANGA_BY_IDS = "delete from account_manga where account_id = ? and manga_id = ?";

    private static final RowMapper<Manga> MANGA_ROW_MAPPER = resultSet -> {
        try {
            return Manga.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .pageCount(resultSet.getInt("page_count"))
                    .url(resultSet.getString("url"))
                    .extension(resultSet.getString("extension"))
                    .type(resultSet.getString("type"))
                    .alternativeName(resultSet.getString("alternative_name"))
                    .author(resultSet.getString("author"))
                    .fullDescription(resultSet.getString("full_description"))
                    .startDate(resultSet.getString("start_date"))
                    .status(resultSet.getString("status"))
                    .link(resultSet.getString("link"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private final Connection connection;

    public MangaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Manga entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MANGA)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setInt(3,entity.getPageCount());
            statement.setString(4, entity.getUrl());
            statement.setString(5, entity.getExtension());
            statement.setString(6, entity.getType());
            statement.setString(7, entity.getStartDate());
            statement.setString(8, entity.getStatus());
            statement.setString(9, entity.getAuthor());
            statement.setString(10, entity.getAlternativeName());
            statement.setString(11, entity.getFullDescription());
            statement.setString(12, entity.getLink());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Manga entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MANGA)){
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setInt(3,entity.getPageCount());
            statement.setString(4, entity.getUrl());
            statement.setString(5, entity.getExtension());
            statement.setString(6, entity.getType());
            statement.setString(7, entity.getStartDate());
            statement.setString(8, entity.getStatus());
            statement.setString(9, entity.getAuthor());
            statement.setString(10, entity.getAlternativeName());
            statement.setString(11, entity.getFullDescription());
            statement.setString(12, entity.getLink());
            statement.setLong(13, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MANGA)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Manga> findAll() {
        List<Manga> mangas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_MANGA)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mangas.add(MANGA_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return mangas;
    }

    @Override
    public Optional<Manga> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID_MANGA)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(MANGA_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something goes wrong", e);
        }

        return Optional.empty() ;
    }

    public List<Long> getUserManga(Long id) {
        List<Long> ids = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FAVOURITES_MANGA)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("manga_id"));
                ids.add(resultSet.getLong("manga_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something goes wrong", e);
        }
        return ids;
    }

    public void deleteMangasById(Long ac_id, Long manga_id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MANGA_BY_IDS)){
            statement.setLong(1, ac_id);
            statement.setLong(2, manga_id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Long getMaxId() {
        try (PreparedStatement statement = connection.prepareStatement("select max(id) from manga")){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return (resultSet.getLong("max"));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return 0L;
    }

    public Manga getMangaByName(String title) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MANGA_BY_NAME)){
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return (MANGA_ROW_MAPPER.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }
}
