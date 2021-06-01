package database;

import logic.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection conn = DatabaseConnection.makeConnection();

    public UserDAO() throws SQLException { }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?);";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());

        statement.execute();
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?;";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, username);
        ResultSet result = statement.executeQuery();

        return result.next();
    }

    public boolean accountExists(User user) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        ResultSet result = statement.executeQuery();

        return result.next();
    }
}
