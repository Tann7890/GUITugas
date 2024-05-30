package guiii;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/film";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456"; // Ganti dengan password MySQLmu

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void saveFavoriteFilm(FavoriteFilm film) throws SQLException {
        String query = "INSERT INTO favorite_films (name, age, favorite_movie, favorite_character) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, film.getName());
            preparedStatement.setInt(2, film.getAge());
            preparedStatement.setString(3, film.getFavoriteMovie());
            preparedStatement.setString(4, film.getFavoriteCharacter());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAllFavoriteFilms() throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);  // Mulai transaksi
            try (Statement statement = connection.createStatement()) {
                // Hapus semua data dari tabel
                statement.executeUpdate("DELETE FROM favorite_films");

                // Reset auto-increment value
                statement.executeUpdate("ALTER TABLE favorite_films AUTO_INCREMENT = 1");

                connection.commit();  // Komit transaksi jika semua berhasil
            } catch (SQLException ex) {
                connection.rollback();  // Rollback jika terjadi kesalahan
                throw ex;
            } finally {
                connection.setAutoCommit(true);  // Kembali ke mode auto-commit
            }
        }
    }

    public void deleteFavoriteFilmById(int id) throws SQLException {
        String query = "DELETE FROM favorite_films WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<FavoriteFilm> getAllFavoriteFilms() throws SQLException {
        String query = "SELECT * FROM favorite_films";
        List<FavoriteFilm> favoriteFilms = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String favoriteMovie = resultSet.getString("favorite_movie");
                String favoriteCharacter = resultSet.getString("favorite_character");
                favoriteFilms.add(new FavoriteFilm(id, name, age, favoriteMovie, favoriteCharacter));
            }
        }
        return favoriteFilms;
    }
}
