package Text_Adventure;

public class GameStatusSingleton {

    private static GameStatusSingleton instance;
    private Player player;
    private Game game;
    private Room activeRoom;

    private GameStatusSingleton() {

    }

    public static GameStatusSingleton getInstance() {
        if (instance == null) {
            instance = new GameStatusSingleton();
        }
        return instance;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Game getGame() {
        return this.game;
    }

    public Room getActiveRoom() {
        return this.activeRoom;
    }

    public void setPlayer(Player playerIn) {
        this.player = playerIn;
    }

    public void  setGame(Game gameIn) {
        this.game = gameIn;
    }

    public void setActiveRoom(Room activeRoomIn) {
        this.activeRoom = activeRoomIn;
    }
}
