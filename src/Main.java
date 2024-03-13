public class Main {
    public static void main(String[] args) {
        Player player = new Player("Default_User");
        Room[] rooms = Room.createRooms();
        Game game = new Game(player, rooms);
        game.play();
    }
}
