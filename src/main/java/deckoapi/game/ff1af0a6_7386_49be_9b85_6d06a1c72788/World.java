package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import org.json.JSONObject;

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

    public World(JSONObject w) {
        artifacts = w.getInt("artifacts");
        bg = new Background(w.getJSONObject("bg"));
        cols = w.getInt("cols");
        layers = new HashMap<>();
        JSONObject layersObj = w.getJSONObject("layers");
        for (String name : layersObj.keySet()) {
            JSONObject layerObj = layersObj.getJSONObject(name);
            Layer layer;
            if ("surface".equals(name)) layer = new Layer.SurfaceLayer(layerObj);
            else if (name.startsWith("layer_")) layer = new Layer.DeepLayer(layerObj);
            else layer = new Layer(layerObj);
            layers.put(name, layer);
        }
        shop = new Shop(w.getJSONObject("shop"));
        teleport = new Teleport(w.getJSONObject("teleport"));
        tile = new Tile(w.getJSONObject("tile"));
        view = new View(w.getJSONObject("view"));
    }

    public static class Background {
        public float paralaxRatio;

        public Background(JSONObject src) {
            paralaxRatio = src.getFloat("paralaxRatio");
        }
    }

    public static class Layer {
        public int rows;

        public Layer(JSONObject src) {
            rows = src.getInt("rows");
        }

        public static class SurfaceLayer extends Layer {
            public int mainTeleportCol, mainTeleportJumpCol;

            public SurfaceLayer(JSONObject src) {
                super(src);
                mainTeleportCol = src.getInt("mainTeleportCol");
                mainTeleportJumpCol = src.getInt("mainTeleportJumpCol");
            }
        }

        public static class DeepLayer extends Layer {
            public int teleports, bonuses, artifacts;
            public HashMap<String, ContentSpawnChance> content;

            public DeepLayer(JSONObject src) {
                super(src);
                teleports = src.getInt("teleports");
                bonuses = src.getInt("bonuses");
                artifacts = src.getInt("artifacts");
                content = new HashMap<>();
                JSONObject contentObj = src.getJSONObject("content");
                for (String name : contentObj.keySet()) {
                    JSONObject contObj = contentObj.getJSONObject(name);
                    content.put(name, new ContentSpawnChance(contObj));
                }
            }

            public static class ContentSpawnChance {
                public float possibility;
                public int lastRowQuantityRatio;

                public ContentSpawnChance(JSONObject src) {
                    possibility = src.getFloat("possibility");
                    lastRowQuantityRatio = src.getInt("lastRowQuantityRatio");
                }
            }
        }
    }

    public static class Shop {
        public int col;

        public Shop(JSONObject src) {
            col = src.getInt("col");
        }
    }

    public static class Teleport {
        public int time;

        public Teleport(JSONObject src) {
            time = src.getInt("time");
        }
    }

    public static class Tile {
        public int size;

        public Tile(JSONObject src) {
            size = src.getInt("size");
        }
    }

    public static class View {
        public int cols, rows;

        public View(JSONObject src) {
            cols = src.getInt("cols");
            rows = src.getInt("rows");
        }

        public View(int cols, int rows) {
            this.cols = cols;
            this.rows = rows;
        }
    }
}
