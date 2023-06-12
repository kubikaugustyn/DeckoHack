package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import java.util.HashMap;

public class World {
    public int artifacts;
    public Background bg;
    public int cols;
    public HashMap<String, Layer> layers;
    public Shop shop;
    public Teleport teleport;
    public Tile tile;
    public View view;

    public static class Background {
        public int paralaxRatio;
    }

    public static class Layer {
        public int rows, teleports, bonuses, artifacts;
        public HashMap<String, ContentSpawnChance> content;

        public static class ContentSpawnChance {
            public float possibility;
            public int lastRowQuantityRatio;
        }
    }

    public static class Shop {
        public int col;
    }

    public static class Teleport {
        public int time;
    }

    public static class Tile {
        public int size;
    }

    public static class View {
        public int cols, rows;
    }
}
