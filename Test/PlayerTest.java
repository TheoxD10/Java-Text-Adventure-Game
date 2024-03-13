import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testReduceHealth() {
        Player player = new Player("TestPlayer");
        int initialHealth = player.getHealth();

        player.reduceHealth();

        int expectedHealth = initialHealth - player.getAmount();
        int actualHealth = player.getHealth();

        assertEquals(expectedHealth, actualHealth);
    }
}
