public class Room implements Comparable<Room> {
    private String name;
    private String description;
    private String itemToPass;

    public Room(String name, String description, String itemToPass) {
        this.name = name;
        this.description = description;
        this.itemToPass = itemToPass;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getItemToPass() {
        return itemToPass;
    }
//  LAB 4 USAGE
    @Override
    public int compareTo(Room other) {
        return this.name.compareTo(other.getName());
    }

    public void updateName(String newName) {
        this.name = newName;
    }
        public static Room[] createRooms() {
        Room[] rooms = new Room[10];
        rooms[0] = new Room("Room 1", "The room is a somber expanse, its walls hewn from ancient stone.\nFlickering torchlight reveals faded murals,\nrecounting tales of forgotten heroes.\nAt the heart of the chamber, an archway beckons,\nits carvings hinting at the labyrinth beyond.\n" + "\n" + "Stepping into the maze, you're enveloped in a web of twisting passages.\nMoss-clad walls rise, concealing both promise and peril.\nEchoes of distant footsteps and murmurs remind you that you're not alone.\nSteel your resolve, for only the sharpest minds will conquer this Maze of Shadows.*\n-I wonder how can i can find my way to the other entrance..." , "Map");
        rooms[1] = new Room("Room 2", "*You find yourself standing on the threshold of a suffocating darkness, the air thick and foreboding.\nThe only way forward lies in the trembling void, urging you to navigate this eerie abyss and unveil the secrets concealed within.*\nHuh?... So this is the second room?\n-Its not what I expected, I can't see anything...\n-I should find something that can guide my way to the other level...", "Torch");
        rooms[2] = new Room("Room 3", "*Proceeds to open the new portal\nYou step into a disorienting realm, where twisted landscapes defy logic and every path seems to lead in circles.\nJagged spires loom ominously, casting long, deceptive shadows that dance across the uneven ground.\nFaint echoes of distant whispers taunt your senses, as you struggle to discern any semblance of direction.\nAmidst the confusion, a subtle anomaly beckons, silently urging you to trust its unseen guidance, a hidden beacon in this bewildering expanse.", "Compass");
        rooms[3] = new Room("Room 4", "You step into a small chamber with a locked door.", "Key");
        rooms[4] = new Room("Room 5", "You hear faint sounds of clashing weapons.", "Helmet");
        rooms[5] = new Room("Room 6", "An archer's kit lies abandoned in this room.", "Bow");
        rooms[6] = new Room("Room 7", "Arrows are strewn across the floor.", "Arrow");
        rooms[7] = new Room("Room 8", "A lantern illuminates the room with a soft glow.", "Lantern");
        rooms[8] = new Room("Room 9", "You find an old map on a dusty table.", "Map");
        rooms[9] = new Room("Room 10", "A deep chasm blocks your way forward.", "Rope");

        return rooms;
    }
}
