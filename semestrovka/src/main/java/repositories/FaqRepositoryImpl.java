package repositories;

import mappers.RowMapper;
import models.Account;
import models.Faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FaqRepositoryImpl implements FaqRepository {
    //language=SQL
    private static final String SQL_INSERT_FAQ = "insert into faq(label, answer) values (?, ?)";
    //language=SQL
    private static final String SQL_UPDATE_FAQ = "update faq set label = ?, answer = ? where id = ?";
    //language=SQL
    private static final String SQL_DELETE_FAQ = "delete from faq where id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL_FAQ = "select * from faq ";
    //language=SQL
    private static final String SQL_FIND_BY_ID_FAQ = "select * from faq where id = ?";

    private static final RowMapper<Faq> FAQ_ROW_MAPPER = resultSet -> {
        try {
            return Faq.builder()
                    .id(resultSet.getLong("id"))
                    .label(resultSet.getString("label"))
                    .answer(resultSet.getString("answer"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private final Connection connection;

    public FaqRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Faq entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FAQ)) {
            statement.setString(1, entity.getLabel());
            statement.setString(2, entity.getAnswer());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Faq entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FAQ)){
            statement.setString(1, entity.getLabel());
            statement.setString(2, entity.getAnswer());
            statement.setLong(3, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FAQ)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Faq> findAll() {
        List<Faq> faqs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_FAQ)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                faqs.add(FAQ_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return faqs;
    }

    @Override
    public Optional<Faq> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID_FAQ)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(FAQ_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something goes wrong", e);
        }

        return Optional.empty() ;
    }
}

