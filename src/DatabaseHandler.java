import java.sql.*;

public class DatabaseHandler {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public static void addOrUpdatePlayer(Player player, int roomNumber) {
        if (getPlayer(player.getName()) == null) {
            addPlayer(player);
        } else {
            updatePlayer(player, roomNumber);
        }
    }

    public static void addPlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Player (name, health, room) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, player.getName());
            preparedStatement.setInt(2, player.getHealth());
            int roomNumber = 0;
            preparedStatement.setInt(3, roomNumber);  // Assuming room numbers start from 0, increment by 1 for level

            preparedStatement.executeUpdate();

            System.out.println("Player added to the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updatePlayer(Player player, int roomNumber) {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Player SET health = ?, room = ? WHERE name = ?")) {

            preparedStatement.setInt(1, player.getHealth());
            preparedStatement.setInt(2, roomNumber);  // Assuming room numbers start from 0, increment by 1 for level
            preparedStatement.setString(3, player.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Player updated in the database.");
            } else {
                System.out.println("Player not found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }



    public static Player getPlayer(String playerName) {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Player WHERE name = ?")) {

            preparedStatement.setString(1, playerName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int health = resultSet.getInt("health");
                    int room = resultSet.getInt("room");

                    return new Player(name, health, room);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Player not found
    }
}
