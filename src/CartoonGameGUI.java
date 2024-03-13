import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CartoonGameGUI extends JFrame {
    private Player player;
    private Room[] rooms;
    private int currentRoomIndex;
    private JLabel roomLabel;
    private JLabel healthLabel;
    private JTextArea textArea;

    public CartoonGameGUI(Player player, Room[] rooms) {
        this.player = player;
        this.rooms = rooms;
        this.currentRoomIndex = 0;

        // Set up the frame
        setTitle("Cartoon Adventure Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        // Set layout to BorderLayout
        setLayout(new BorderLayout());

        // Create labels for room and health
        roomLabel = new JLabel("" + rooms[currentRoomIndex].getName());
        roomLabel.setForeground(Color.WHITE);
        roomLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));


        healthLabel = new JLabel("Health: " + player.getHealth());
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        healthLabel.setText("Health: " + player.getHealth());

        // Create a text area for output
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        // Create a button for interaction (you can replace this with your own interaction mechanism)
        JButton interactionButton = new JButton("Interact");
        interactionButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        interactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInteraction();
            }
        });

        // Add components to the frame
        add(roomLabel, BorderLayout.NORTH);
        add(healthLabel, BorderLayout.SOUTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(interactionButton, BorderLayout.EAST);

        // Display the frame
        setVisible(true);
    }

    public void animateText(String text) {
        SwingWorker<Void, Character> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (char c : text.toCharArray()) {
                    publish(c);
                    Thread.sleep(50); // Adjust the sleep duration as needed
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Character> chunks) {
                for (Character c : chunks) {
                    textArea.append(c.toString());
                }
                textArea.setCaretPosition(textArea.getDocument().getLength() - 1);
            }

            @Override
            protected void done() {
                textArea.append("\n");
                textArea.setCaretPosition(textArea.getDocument().getLength() - 1);
            }
        };
        worker.execute();
    }

    public void showRoomDescription() {
        Room currentRoom = rooms[currentRoomIndex];
        animateText(currentRoom.getDescription());
    }

    public void showWelcomeMessages() {
        animateText("Welcome traveller! I see you've came a long way for this adventure...\nMake sure to have your stuff prepared in order to find the might path!");
    }

    public void handleInteraction() {
        Room currentRoom = rooms[currentRoomIndex];
        animateText(currentRoom.getDescription() + "\nYou can choose between these items in order to pass the level:\nMap, Torch, Hat, Jacket, Water, Sword, Food, Rope, Compass, Umbrella ");

        String playerAnswer = inputAnswer();

        // Compare player's answer with the correct answer to progress to the next level
        if (playerAnswer.equals(currentRoom.getItemToPass())) {
            currentRoomIndex++;
//            showRoomDescription(); // Automatically show the description of the new room
            animateText("You've used the item accordingly, press interact in order to move on...");
            if (currentRoomIndex < rooms.length) {
                Room nextRoom = rooms[currentRoomIndex];
                roomLabel.setText(nextRoom.getName()); // Update room label dynamically
            } else {
                animateText("Congratulations! You have completed all levels.");
            }
        } else {
            player.reduceHealth();
            healthLabel.setText("Health: " + player.getHealth()); // Update health label
            if (player.getHealth() <= 0) {
                animateText("Game over! You have run out of health.");
            } else {
                animateText("Wrong answer! You lost 15 health. The remaining health is: " + player.getHealth() + "\nTry again.");
            }
        }
    }


    public String inputAnswer() {
        String answer = JOptionPane.showInputDialog(this, "Enter your answer:");
        return answer;
    }

    public static void main(String[] args) {
        Player player = new Player("Theo");
        Room[] rooms = Room.createRooms();
        CartoonGameGUI gameGUI = new CartoonGameGUI(player, rooms);

        // Automatically show welcome messages
        gameGUI.showWelcomeMessages();

        // Automatically show room description before interaction
        gameGUI.showRoomDescription();
    }
}
