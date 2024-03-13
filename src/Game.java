import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Game implements Interactive, Serializable, Configuration {
    private Player player;
    private Room[] rooms;
    private int currentRoomIndex;

    public Game(Player player, Room[] rooms) {
        this.player = player;
        this.rooms = rooms;
        this.currentRoomIndex = 0; // Start in the first room
    }

    public void animateText(String text) {
        Thread animationThread = new Thread(() -> {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        });
        animationThread.start();
        try {
            animationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);

        File gameStateFile = new File("game_state.txt");

        if (gameStateFile.exists()) {
            System.out.println("Do you want to continue the previous game? (yes/no)");
            String userInput = scanner.nextLine();

            if (!userInput.equalsIgnoreCase("yes") && !userInput.equalsIgnoreCase("no")) {
                throw new InvalidInputException("Invalid input! Please enter 'yes' or 'no'.");
            }

            if (userInput.equalsIgnoreCase("yes")) {
                loadState("game_state.txt");
            } else {
                gameStateFile.delete(); // Start a new game, delete the previous state
            }
        }

        animateText("Please enter your name: ");
        String playerName = scanner.nextLine();
        player.setName(playerName);

        if (!playerName.matches("[a-zA-Z]+")) {
            throw new InvalidInputException("Invalid input! The name should contain only letters.");
        }

        while (currentRoomIndex < rooms.length) {
            Room currentRoom = rooms[currentRoomIndex];
            animateText(currentRoom.getDescription());
            animateText("You have the following items in your inventory:");
            List<String> sortedInventory = player.getSortedInventory();
            System.out.println(sortedInventory);

            animateText("Choose an item to pass the level: ");
            String chosenItem = scanner.nextLine();

            try {
                handlePlayerChoice(chosenItem, currentRoom);
            } catch (HealthLossException e) {
                player.reduceHealth();
                DatabaseHandler.addOrUpdatePlayer(player, currentRoomIndex);
                if (player.getHealth() <= 0) {
                    animateText("Game over! You have run out of health.");
                    break;
                }
                animateText(e.getMessage() + " The remaining health is: " + player.getHealth() + "\nTry again.");
            } catch (InvalidChoiceException e) {
                animateText(e.getMessage() + " Try again.");
            }

            saveState("game_state.txt");
        }

        animateText("Game over! You completed all levels.");
    }

    private void handlePlayerChoice(String chosenItem, Room currentRoom) throws HealthLossException, InvalidChoiceException {
        if (Arrays.asList(player.getInventory()).contains(chosenItem) && !player.hasUsedItem(chosenItem)) {
            player.useItem(chosenItem, currentRoom.getItemToPass());
            if (!chosenItem.equals(currentRoom.getItemToPass())) {
                throw new HealthLossException("Wrong item! You lost 15 health.");
            } else {
                animateText("Congratulations, " + player.getName() + "! You passed to the next level.");
                currentRoomIndex++;
            }
        } else {
            throw new InvalidChoiceException("Invalid choice or item already used.");
        }
        DatabaseHandler.addOrUpdatePlayer(player, currentRoomIndex);
    }


    @Override
    public void saveState(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(player.getName());
            writer.newLine();
            writer.write(String.valueOf(player.getHealth()));
            writer.newLine();
            writer.write(String.valueOf(currentRoomIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadState(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String name = reader.readLine();
            int health = Integer.parseInt(reader.readLine());
            currentRoomIndex = Integer.parseInt(reader.readLine());

            player = new Player(name);
            player.reduceHealth();
            player.getInventory(); // Load inventory
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Player player = new Player("DefaultPlayer");
        Room[] rooms = Room.createRooms();
        Game game = new Game(player, rooms);

        game.play();
    }

    public Player getPlayer() {
        return player;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }
}
