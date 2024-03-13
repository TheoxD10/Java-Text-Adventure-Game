import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends JFrame implements ActionListener {
    private Player player;
    private Room[] rooms;
    private int currentRoomIndex;
    private JTextArea textArea;
    private JTextField inputField;
    private JButton submitButton;
    private JLabel nameLabel;
    private JLabel healthLabel;

    private String currentMessage;
    private int currentIndex;

    public GameGUI(Player player, Room[] rooms) {
        this.player = player;
        this.rooms = rooms;
        this.currentRoomIndex = 0;

        setTitle("Adventure Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.GREEN);
        textArea.setBackground(Color.BLACK);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.BLACK);

        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setCaretColor(Color.GREEN);
        inputPanel.add(inputField, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.GREEN);
        inputPanel.add(submitButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        nameLabel = new JLabel("Player: " + player.getName());
        nameLabel.setForeground(Color.GREEN);
        add(nameLabel, BorderLayout.NORTH);

        healthLabel = new JLabel("Health: " + player.getHealth());
        healthLabel.setForeground(Color.RED);
        add(healthLabel, BorderLayout.WEST);

        currentMessage = "";
        currentIndex = 0;
    }

    public void actionPerformed(ActionEvent e) {
        String chosenItem = inputField.getText();
        Room currentRoom = rooms[currentRoomIndex];
        if (chosenItem.equals(currentRoom.getItemToPass())) {
            animateText("Congratulations! You passed to the next level.\n");
            currentRoomIndex++;
        } else {
            player.reduceHealth();
            if (player.getHealth() <= 0) {
                animateText("Game over! You have run out of health.\n");
                inputField.setEditable(false);
                submitButton.setEnabled(false);
            } else {
                animateText("Wrong item! You lost 15 health. Try again.\n");
            }
        }
        inputField.setText("");
        healthLabel.setText("Health: " + player.getHealth());

        if (player.getHealth() > 0) {
            inputField.setEditable(true);
            submitButton.setEnabled(true);
        }
    }

    private void animateText(String message) {
        currentMessage = message;
        currentIndex = 0;
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (currentIndex < currentMessage.length()) {
                    textArea.append(String.valueOf(currentMessage.charAt(currentIndex)));
                    currentIndex++;
                } else {
                    ((Timer) evt.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        Player player = new Player("PlayerName"); // Replace with actual player name
        Room[] rooms = Room.createRooms();

        GameGUI gui = new GameGUI(player, rooms);
        gui.setVisible(true);
        gui.displayCurrentRoom();
    }

    public void displayCurrentRoom() {
        Room currentRoom = rooms[currentRoomIndex];
        animateText(currentRoom.getDescription() + "\n");
        animateText("You have the following items in your inventory:\n");
        for (String item : player.getInventory()) {
            animateText(item + "\n");
        }
        animateText("Choose an item to pass the level: ");
    }
}
