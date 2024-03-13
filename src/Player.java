import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends Serializable.SerializableAdapter implements Comparable<Player> {
    private String name;
    private int health;
    private String[] inventory;
    private List<String> usedItems;
    private int amount;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.inventory = new String[]{"Map", "Torch", "Hat", "Jacket", "Water", "Sword", "Food", "Rope", "Compass", "Umbrella"};
        this.usedItems = new ArrayList<>();
        this.amount=15;
    }

    public Player(String name, int health, int level) {
        super();
    }

    public List<String> getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(List<String> usedItems) {
        this.usedItems = usedItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth() {
        this.health -= amount;
    }

    public int getAmount()
    {
        return amount;
    }

    public String[] getInventory() {
        return inventory;
    }

    public void useItem(String item, String correctItem) {
        if (Arrays.asList(inventory).contains(item)) {
            inventory = Arrays.stream(inventory)
                    .filter(i -> !i.equals(item))
                    .toArray(String[]::new);

            if (item.equals(correctItem)) {
                usedItems.add(item);
            } else {
                System.out.println("Incorrect item! You didn't lose the item.");
            }
        } else {
            System.out.println("You don't have that item in your inventory.");
        }
    }


    public boolean hasUsedItem(String item) {
        return usedItems.contains(item);
    }

    public List<String> getSortedInventory() {
        return Arrays.stream(inventory)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(Player other) {
        return Integer.compare(this.health, other.getHealth());
    }

    public void setHealth(int health) {
    }

    public int getCurrentLevel() {
        return 0;
    }
}
