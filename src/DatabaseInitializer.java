import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public static String getURL() {
        return URL;
    }

    public static void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String createPlayerTable = "CREATE TABLE IF NOT EXISTS Player (" +
                    "name TEXT NOT NULL," +
                    "health INTEGER NOT NULL," +
                    "room INTEGER NOT NULL" +
                    ")";
            statement.execute(createPlayerTable);

            statement.close();
            connection.close();

            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
