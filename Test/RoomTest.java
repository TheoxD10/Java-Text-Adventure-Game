import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RoomTest {

    @Test
    public void testCreateRooms() {
        Room[] rooms = Room.createRooms();

        assertEquals(10, rooms.length);

        // Check individual rooms for specific properties
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            assertNotNull(room);
            assertEquals("Room " + (i + 1), room.getName()); // Assuming room names follow a pattern
            assertNotNull(room.getDescription());
            assertNotNull(room.getItemToPass());
        }
    }
}
