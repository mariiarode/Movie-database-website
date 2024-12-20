package database;

import model.MVmovie;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MVdb {
    private static final Logger LOGGER = Logger.getLogger(MVdb.class.getName());
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:USERdb.sqlite";

    private static Connection conn;
    private static Statement stat;
    
    private static final String queryUpdateTable = "ALTER TABLE movies ADD COLUMN releaseYear INTEGER, ADD COLUMN genre TEXT";

    private static final String queryDeleteMovie = "DELETE FROM movies WHERE id=?";
    private static final String querySelectMovieByTitle = "SELECT * FROM movies WHERE title = ?";
    private static final String queryInsertMovie = "INSERT INTO movies (title, producer, actors, summary, poster, releaseYear, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String queryUpdateMovie = "UPDATE movies SET title=?, producer=?, actors=?, summary=?, poster=?, releaseYear=?, genre=? WHERE id=?";
    private static final String querySelectMovieById = "SELECT * FROM movies WHERE id=?";
    private static final String querySelectMovieTitlesStartingWith = "SELECT title FROM movies WHERE title LIKE ?";
    private static final String querySelectAllMovies = "SELECT * FROM movies";
    private static final String queryDeleteAllMovies = "DELETE FROM movies";

    public MVdb() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();

            if (!conn.isClosed()&& !isTableExists()) { // Check if connection is open
                createTables();
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
    }


    private boolean isTableExists() {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='movies';";
        try (ResultSet results = stat.executeQuery(query)) {
            return results.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking table existence", e);
            return false;
        }
    }
    public boolean updateTable() {
        try {
            stat.execute(queryUpdateTable);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating movies table", e);
            return false;
        }
    }
    private boolean createTables() {
        String createMovies = "CREATE TABLE IF NOT EXISTS movies (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, producer TEXT, actors TEXT, summary TEXT, poster BLOB, releaseYear INTEGER, genre TEXT)";
        try {
            stat.execute(createMovies);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating movies table", e);
            return false;
        }
    }
    public static boolean dropTables() {
        String dropMovies = "DROP TABLE IF EXISTS movies";
        try {
            stat.execute(dropMovies);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error dropping movies table", e);
            return false;

        }
    }


    public static boolean deleteAllMovies() {
        String query = "DELETE FROM movies";
        try (PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting all movies", e);
            return false;
        }
    }
    public boolean updateMovie(MVmovie movie) {
        String query = "UPDATE movies SET title=?, producer=?, actors=?, summary=?, releaseYear=?, genre=? WHERE id=?";
        try (PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setString(1, movie.getTitle());
            prepStmt.setString(2, movie.getProducer());
            prepStmt.setString(3, movie.getActors());
            prepStmt.setString(4, movie.getSummary());
            prepStmt.setInt(5, movie.getReleaseYear());
            prepStmt.setString(6, movie.getGenre());
            prepStmt.setInt(7, movie.getId());

            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating movie", e);
            return false;
        }
    }

    public List<String> searchMovieTitlesStartingWith(String query) {
        List<String> movieTitles = new LinkedList<>();
        try (PreparedStatement prepStmt = conn.prepareStatement(querySelectMovieTitlesStartingWith)) {
            prepStmt.setString(1, query + "%"); // Add wildcard to search for titles starting with the query
            try (ResultSet resultSet = prepStmt.executeQuery()) {
                while (resultSet.next()) {
                    movieTitles.add(resultSet.getString("title"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching movie titles", e);
        }
        return movieTitles;
    }

    public int insertMovie(MVmovie movie) {
        int id = -1;
        try (PreparedStatement prepStmt = conn.prepareStatement(queryInsertMovie, Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, movie.getTitle());
            prepStmt.setString(2, movie.getProducer());
            prepStmt.setString(3, movie.getActors());
            prepStmt.setString(4, movie.getSummary());
            prepStmt.setBytes(5, movie.getPoster());
            prepStmt.setInt(6, movie.getReleaseYear());
            prepStmt.setString(7, movie.getGenre());

            int rowsAffected = prepStmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                        movie.setId(id);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting movie", e);
        }
        return id;
    }

    public MVmovie selectMovieByTitle(String title) {
        MVmovie movie = null;
        try (PreparedStatement prepStmt = conn.prepareStatement(querySelectMovieByTitle)) {
            prepStmt.setString(1, title);
            try (ResultSet resultSet = prepStmt.executeQuery()) {
                if (resultSet.next()) {
                    movie = new MVmovie(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("producer"),
                            resultSet.getString("actors"),
                            resultSet.getString("summary"),
                            resultSet.getBytes("poster"),
                            resultSet.getInt("releaseYear"),
                            resultSet.getString("genre")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting movie by title", e);
        }
        return movie;
    }

    public MVmovie selectMovieById(int id) {
        MVmovie movie = null;
        try (PreparedStatement prepStmt = conn.prepareStatement(querySelectMovieById)) {
            prepStmt.setInt(1, id);
            try (ResultSet resultSet = prepStmt.executeQuery()) {
                if (resultSet.next()) {
                    movie = new MVmovie(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("producer"),
                            resultSet.getString("actors"),
                            resultSet.getString("summary"),
                            resultSet.getBytes("poster"),
                            resultSet.getInt("releaseYear"),
                            resultSet.getString("genre")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting movie by ID", e);
        }
        return movie;
    }

    public List<MVmovie> selectAllMovies() {
        List<MVmovie> movies = new LinkedList<>();
        try (PreparedStatement prepStmt = conn.prepareStatement(querySelectAllMovies);
             ResultSet resultSet = prepStmt.executeQuery()) {
            while (resultSet.next()) {
                MVmovie movie = new MVmovie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("producer"),
                        resultSet.getString("actors"),
                        resultSet.getString("summary"),
                        resultSet.getBytes("poster"),
                        resultSet.getInt("releaseYear"),
                        resultSet.getString("genre")
                );
                movies.add(movie);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting all movies", e);
        }
        return movies;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing connection", e);
        }
    }

    // Other methods like updateMovie, deleteMovie, etc.
}
