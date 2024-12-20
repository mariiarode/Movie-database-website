package database;

import model.MVuser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class USERdb {
    private static final Logger LOGGER = Logger.getLogger(USERdb.class.getName());
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:USERdb.sqlite";

    private Connection conn;
    private Statement stat;

    private static final String queryInsertUser = "INSERT INTO users (username, password, privileges) VALUES (?, ?, ?);";
    private static final String queryUpdateUser = "UPDATE users SET username=?, password=?, privileges=? WHERE id=?";
    private static final String queryUpdateUserPrivileges = "UPDATE users SET privileges=? WHERE id=?";
    private static final String queryDeleteUser = "DELETE FROM users WHERE id=?";

    private static final String queryInsertFavoriteMovie = "INSERT INTO user_movies (user_id, movie_id) VALUES (?, ?);";
    private static final String queryDeleteFavoriteMoviesByUserId = "DELETE FROM user_movies WHERE user_id=?";
    private static final String querySelectFavoriteMoviesByUserId = "SELECT m.title FROM movies m INNER JOIN user_movies um ON m.id = um.movie_id WHERE um.user_id=?";

    public USERdb() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
            if (!stat.isClosed() && !isTableExists())
                createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTableExists() {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='users';";
        try (ResultSet results = stat.executeQuery(query)) {
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTables() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, privileges INTEGER)";
        String createMovies = "CREATE TABLE IF NOT EXISTS movies (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, year INTEGER)";
        String createUserMovies = "CREATE TABLE IF NOT EXISTS user_movies (user_id INTEGER, movie_id INTEGER, FOREIGN KEY (user_id) REFERENCES users(id), FOREIGN KEY (movie_id) REFERENCES movies(id), PRIMARY KEY (user_id, movie_id))";
        try {
            stat.execute(createUsers);
            stat.execute(createMovies);
            stat.execute(createUserMovies);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> selectFavoriteMovieIdsByUserId(int userId) {
        List<Integer> favoriteMovieIds = new LinkedList<>();
        String query = "SELECT movie_id FROM user_movies WHERE user_id=?";
        try (PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setInt(1, userId);
            try (ResultSet resultSet = prepStmt.executeQuery()) {
                while (resultSet.next()) {
                    favoriteMovieIds.add(resultSet.getInt("movie_id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting favorite movie IDs", e);
        }
        return favoriteMovieIds;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean insertUser(MVuser user) {
        String sql = "INSERT INTO users (username, password, privileges) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getPrivileges());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(MVuser user) {
        try (PreparedStatement prepStmt = conn.prepareStatement(queryUpdateUser)) {
            prepStmt.setString(1, user.getLogin());
            prepStmt.setString(2, user.getPassword());
            prepStmt.setInt(3, user.getPrivileges());
            prepStmt.setInt(4, user.getId());

            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MVuser> getAllUsers() {
        List<MVuser> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                MVuser user = new MVuser();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("username"));
                user.setPrivileges(resultSet.getInt("privileges"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public boolean updateUserPrivileges(int userId, int newPrivileges) {
        try {
            String query = "UPDATE users SET privileges = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, newPrivileges);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteUser(int id) {
        try (PreparedStatement prepStmt = conn.prepareStatement(queryDeleteUser)) {
            prepStmt.setInt(1, id);
            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean insertFavoriteMovie(int userId, int movieId) {
        try (PreparedStatement prepStmt = conn.prepareStatement(queryInsertFavoriteMovie)) {
            prepStmt.setInt(1, userId);
            prepStmt.setInt(2, movieId);
            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFavoriteMoviesByUserId(int userId) {
        try (PreparedStatement prepStmt = conn.prepareStatement(queryDeleteFavoriteMoviesByUserId)) {
            prepStmt.setInt(1, userId);
            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleUsers() {
        for (int i = 0; i < 20; i++) {
            MVuser user = new MVuser();
            int randomNumber = (int) (Math.random() * 1000);
            user.setLogin("user" + randomNumber);
            user.setPassword("password" + randomNumber);
            user.setPrivileges(1);
            insertUser(user);
        }
    }
    public MVuser selectUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                MVuser user = new MVuser();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPrivileges(rs.getInt("privileges"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        USERdb db = new USERdb();
        db.insertSampleUsers();
        System.out.println(":..............");
        db.closeConnection();
}
}