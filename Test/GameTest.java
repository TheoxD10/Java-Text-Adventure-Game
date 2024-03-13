import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void testGameInitialization() {
        Player player = new Player("TestPlayer");
        Room[] rooms = Room.createRooms();

        Game game = new Game(player, rooms);

        assertEquals(player, game.getPlayer());
        assertEquals(rooms, game.getRooms());
        assertEquals(0, game.getCurrentRoomIndex());
    }
}