package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

public class Teleport {
    public static Teleport mainTeleport;
    public int id;
    public boolean isMain = false;

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
