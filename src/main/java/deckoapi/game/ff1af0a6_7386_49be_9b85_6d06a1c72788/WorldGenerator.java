package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import java.util.HashMap;
import java.util.Vector;

public class WorldGenerator {
    Game game;
    Engine engine;
    World o;
    World.View p; // View, but edited
    int q; // Cols (width)
    int r; // Rows of 2*SurfaceLayer + sum of all DeepLayer

    public WorldGenerator() {
    }

    // Shuffle artifacts
    void a() {
        int artifactsNum = game.levels.get(game.levelId).world.artifacts;
        Vector<Integer> b = new Vector<>(artifactsNum);
        for (int i = 0; i < artifactsNum; i++) b.add(i, i);
        engine.artifacts.put("surface", new Vector<>());
        HashMap<String, World.Layer> c = game.levels.get(game.levelId).world.layers;
        for (String d : c.keySet())
            if (c.get(d) instanceof World.Layer.DeepLayer layer) {
                Vector<String> artifacts = new Vector<>();
                engine.artifacts.put(d, artifacts);
                for (int a = 0; a < layer.artifacts; a++) {
                    int e = (int) Math.floor(engine.randomSeed() * b.size());
                    String f = "artifact_" + b.get(e);
                    artifacts.add(f);
                    b.remove(e);
                }
            }
    }

    void b() {
        short b = 65;
        Vector<String> A = new Vector<>(Label.tileTypeMap.size()), B = new Vector<>(Label.tileTypeMap.size());
        for (String a : Label.tileTypeMap.keySet()) {
            A.add(Label.tileTypeMap.get(a));
            B.add(String.valueOf((char) b));
            if (++b == 91) b = 97;
        }
        engine.tileTypeCodeMap = "";
        r = 2 * p.rows;
        for (String a : o.layers.keySet()) r += o.layers.get(a).rows;
        engine.tileTypeCodeMap = "#".repeat(r * q);
    }

    int c(int a, int b) {
        return (b + p.rows) * q + a + p.cols;
    }

    void d() { // Reset shadow map
        StringBuilder map = new StringBuilder();
        for (String a : engine.tileLayerNames.keySet()) {
            for (String b : engine.tileLayerNames.get(a).keySet()) {
                map.append(map.length() / q > 8 ? "9" : "0"); // If layer is > 8 then 9 (dark) else 0 (light, at the top only)
            }
        }
        engine.shadowMap = map.toString();
    }

    void e(String a,String b){

    }

    void generateWorldConfig() {
        if (game == null) return;
        if (engine == null) return;
    }
}
