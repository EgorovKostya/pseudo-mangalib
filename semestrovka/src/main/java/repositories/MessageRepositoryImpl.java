package repositories;

import mappers.RowMapper;
import models.Faq;
import models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryImpl implements MassageRepository {

    //language=SQL
    private static final String SQL_INSERT_MESSAGE = "insert into chat(user_id, username, message) values (?, ?, ?)";
    //language=SQL
    private static final String SQL_UPDATE_MESSAGE = "update chat set user_id = ?, username = ?, message = ? where id = ?";
    //language=SQL
    private static final String SQL_DELETE_MESSAGE = "delete from chat where id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL_MESSAGE = "select * from chat order by id desc";
    //language=SQL
    private static final String SQL_FIND_BY_ID_MESSAGE = "select * from chat where id = ?";

    private static final RowMapper<Message> MESSAGE_ROW_MAPPER = resultSet -> {
        try {
            return Message.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .username(resultSet.getString("username"))
                    .message(resultSet.getString("message"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private final Connection connection;

    public MessageRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Message entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MESSAGE)) {
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getMessage());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Message entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MESSAGE)){
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getMessage());
            statement.setLong(4, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MESSAGE)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_MESSAGE)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(MESSAGE_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return messages;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID_MESSAGE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(MESSAGE_ROW_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something goes wrong", e);
        }

        return Optional.empty() ;
    }
}
