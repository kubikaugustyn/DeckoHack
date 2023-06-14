package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

public class Teleport {
    public static Teleport mainTeleport;
    public int id, row, col, jumpRow, jumpCol;
    public boolean isMain = false, visible;

    public Teleport() {
    }

    public Teleport(int id) {
        this.id = id;
    }

    static {
        mainTeleport = new Teleport() {{
            id = -1;
            isMain = true;
        }};
    }
}
