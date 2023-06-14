package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import java.util.HashMap;
import java.util.Vector;

public class Engine {
    public HashMap<String, Vector<String>> artifacts;
    public HashMap<Integer, HashMap<Integer, String>> tileLayerNames; // Sadly can't be Vector2, because of negative indexes
    public double seed, startSeed;
    public Hero hero;
    public String shadowMap = "", tileTypeCodeMap = "", generatedShadowMap = "", generatedTileTypeCodeMap = "";
    public WorldGenerator worldGenerator;
    public Vector<Teleport> teleports;
    public Vector<LayerDepth> layersDepth;

    public Engine() {
        seed = startSeed = Math.random();
        worldGenerator = new WorldGenerator(this);
    }

    public double randomSeed() {
        double a = 1e4 * Math.sin(seed++);
        return a - Math.floor(a);
    }

    public static class LayerDepth {
        public int depth, start, end;
    }
}
