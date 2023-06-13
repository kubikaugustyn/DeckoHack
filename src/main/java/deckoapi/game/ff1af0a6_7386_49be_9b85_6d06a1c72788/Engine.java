package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import java.util.HashMap;
import java.util.Vector;

public class Engine {
    public HashMap<String, Vector<String>> artifacts;
    public HashMap<String, HashMap<String, String>> tileLayerNames;
    public double seed, startSeed;
    public Hero hero;
    public String shadowMap = "", tileTypeCodeMap = "";
    public WorldGenerator worldGenerator;

    public Engine() {
        seed = startSeed = Math.random();
        worldGenerator = new WorldGenerator();
    }

    public double randomSeed() {
        double a = 1e4 * Math.sin(seed++);
        return a - Math.floor(a);
    }
}
