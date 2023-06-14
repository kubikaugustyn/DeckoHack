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
    int s;
    Vector<LayerPossibilityStuff> t;
    float v;
    int u, w, x, y, z;
    Vector<String> A;
    String B;
    WorldGenerator C;

    public WorldGenerator(Engine engine) {
        this.engine = engine;
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
        A = new Vector<>(Label.tileTypeMap.size());
        StringBuilder B = new StringBuilder(Label.tileTypeMap.size());
        for (String a : Label.tileTypeMap.keySet()) {
            A.add(Label.tileTypeMap.get(a));
            B.append((char) b);
            if (++b == 91) b = 97;
        }
        this.B = B.toString();
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
        for (int a : engine.tileLayerNames.keySet()) {
            for (int b : engine.tileLayerNames.get(a).keySet()) {
                map.append(map.length() / q > 8 ? "9" : "0"); // If layer is > 8 then 9 (dark) else 0 (light, at the top only)
            }
        }
        engine.shadowMap = map.toString();
    }

    void e(String a, int b) {
        LayerPossibilityStuff.PossibilityInterval pi = t.get(b).possibilityInterval.get(a);
        int c = pi.intEnd - pi.intStart;
        w += c;
        float d = c * v + u;
        u = (int) (d - Math.floor(d));
        d = (int) (Math.floor(d));
        x += d;
        for (var e = 0; e < d; e++) t.get(b).content.add(a);
    }

    void f(String a, World.Layer layer) { // Generate artifact (layer name, layer class)
        char c;
        if (!(layer instanceof World.Layer.DeepLayer b)) return;
        Vector<GResult> d = g(b);
        /* Artifact stash
        BBB - 3xCAVE_UP
        DAE - NONE, ARTIFACT_?, NONE
        CCC - 3xCAVE_DOWN
         */
        for (int e = 0; e < b.artifacts; e++) {
            if (d.size() > 0) {
                int f = (int) Math.floor(engine.randomSeed() * d.size());
                GResult h = d.elementAt(f);
                int i = h.int0, j = h.int1;
                d.remove(f);
                c = C.tileTypeToTypeCodeChar(engine.artifacts.get(a).get(e));
                C.setTileTypeCodeChar(i, j + s, c);
                engine.tileLayerNames.get(j + s).put(i, a); // Place A
                for (int k = i - 1; k <= i + 1; k++) {
                    int l = j - 1;
                    c = C.tileTypeToTypeCodeChar(Label.tileType.CAVE_UP); // Place B
                    C.setTileTypeCodeChar(k, l + s, c);
                    engine.tileLayerNames.get(l + s).put(k, a);
                    l = j + 1;
                    c = C.tileTypeToTypeCodeChar(Label.tileType.CAVE_DOWN); // Place C
                    C.setTileTypeCodeChar(k, l + s, c);
                    engine.tileLayerNames.get(l + s).put(k, a);
                }
                c = C.tileTypeToTypeCodeChar(Label.tileType.NONE);
                C.setTileTypeCodeChar(i - 1, j + s, c);
                engine.tileLayerNames.get(j + s).put(i - 1, a); // Place D
                c = C.tileTypeToTypeCodeChar(Label.tileType.NONE);
                C.setTileTypeCodeChar(i + 1, j + s, c);
                engine.tileLayerNames.get(j + s).put(i + 1, a); // Place E
            } else System.err.println("Neni misto pro artefakt");
        }
    }

    Vector<GResult> g(World.Layer.DeepLayer a) {
        int b;
        Vector<GResult> c = new Vector<>();
        int d = a.artifacts;
        int e = o.cols / d; // No need to Math.floor
        Vector<Integer> f = new Vector<>(d);
        for (int g = 0; g < d; g++) f.add(g, g);
        for (int g = 0; g < d; g++) {
            b = (int) Math.floor(engine.randomSeed() * f.size());
            int h = f.get(b);
            f.remove(b);
            int i = h * e + 2;
            int j = i + e - 4;
            int k = (int) Math.floor(engine.randomSeed() * (j - i)) + i;
            int l = (int) Math.floor(engine.randomSeed() * (a.rows - 4)) + 2;
            c.add(new GResult(k, l));
        }
        return c;
    }

    Vector<HResult> h(World.Layer.DeepLayer a) {
        Vector<HResult> b = new Vector<>();
        for (int c = 0; c < o.cols; c++) {
            //for(...)  i(c, d) || C.getTileTypeCodeChar(c, d + s) !== C.tileTypeToTypeCodeChar(Label.tileType.SOIL) || b.push([c, d]);
            for (int d = 0; d < a.rows; d++)
                if (!i(c, d) && C.getTileTypeCodeChar(c, d + s) == C.tileTypeToTypeCodeChar(Label.tileType.SOIL))
                    b.add(new HResult(c, d));
        }
        return b;
    }

    boolean i(int a, int b) {
        for (var c = a - 3; c < a + 3; c++)
            for (var d = b - 3; d < b + 3; d++)
                if (C.tileExists(c, d + s) && C.isArtifactCodeChar(C.getTileTypeCodeChar(c, d + s))) return true;
        return false;
    }

    void j(String a, World.Layer.DeepLayer b) { // Generate teleport (layer name, layer class)
        Vector<HResult> c = h(b);
        for (int d = 0; d < b.teleports; d++)
            if (c.size() > 0) {
                int e = (int) Math.floor(engine.randomSeed() * c.size());
                HResult f = c.get(e);
                c.remove(e);
                int g = f.int0, i = f.int1;
                C.setTileTypeCodeChar(g, i + s, C.tileTypeToTypeCodeChar(Label.tileType.TELEPORT));
                engine.tileLayerNames.get(i + s).put(g, a);
                engine.teleports.add(y, new Teleport() {
                    {
                        id = y;
                        row = i + s;
                        col = g;
                        visible = true;
                    }
                });
                y++;
            } else System.err.println(a + " neni kam umistit teleport");
        k(c, a, b);
    }

    void k(Vector<HResult> a, String b, World.Layer.DeepLayer c) { // Generate bonus (places left after teleports, layer name, layer class)
        int d = c.bonuses;
        for (int e = 0; e < d; e++)
            if (a.size() > 0) {
                int f = (int) Math.floor(engine.randomSeed() * a.size());
                HResult g = a.elementAt(f);
                a.remove(f);
                int h = g.int0, i = g.int1;
                C.setTileTypeCodeChar(h, i + s, C.tileTypeToTypeCodeChar(Label.tileType.BONUS));
            } else System.err.println(b + " neni kam umistit bonus");
    }

    void l() { // Generate blank world top
        for (int b = -p.rows; b < 0; b++) {
            engine.tileLayerNames.put(b, new HashMap<>());
            int c;
            for (c = -p.cols; c < 0; c++) { // Left unbreakable
                C.setTileTypeCodeChar(c, b, C.tileTypeToTypeCodeChar(Label.tileType.UNBREAKABLE));
                engine.tileLayerNames.get(b).put(c, Label.layerName.LAYER_0);
            }
            for (c = 0; c < o.cols; c++) { // Center blocks
                char a = -1 == b ? C.tileTypeToTypeCodeChar(Label.tileType.HORIZON) : C.tileTypeToTypeCodeChar(Label.tileType.INVISIBLE);
                C.setTileTypeCodeChar(c, b, a);
                engine.tileLayerNames.get(b).put(c, Label.layerName.LAYER_0);
            }
            for (c = o.cols; c <= o.cols + p.cols; c++) { // Right unbreakable
                C.setTileTypeCodeChar(c, b, C.tileTypeToTypeCodeChar(Label.tileType.UNBREAKABLE));
                engine.tileLayerNames.get(b).put(c, Label.layerName.LAYER_0);
            }
        }
    }

    void m() { // Generate blank world bottom unbreakable
        for (int a = s; a < s + p.rows; a++) {
            engine.tileLayerNames.put(a, new HashMap<>());
            for (var b = -p.cols; b <= o.cols + p.cols; b++) {
                C.setTileTypeCodeChar(b, a, C.tileTypeToTypeCodeChar(Label.tileType.UNBREAKABLE));
                engine.tileLayerNames.get(a).put(b, Label.layerName.LAYER_4);
            }
        }
    }

    void n(Vector<String> a) {
        for (int b = a.size(); b > 0; b--) {
            int c = (int) Math.floor(engine.randomSeed() * b);
            String d = a.get(b - 1);
            a.set(b - 1, a.get(c));
            a.set(c, d);
        }
    }

    static class GResult {
        int int0, int1;

        public GResult() {
        }

        public GResult(int int0, int int1) {
            this.int0 = int0;
            this.int1 = int1;
        }
    }

    static class HResult {
        int int0, int1;

        public HResult() {
        }

        public HResult(int int0, int int1) {
            this.int0 = int0;
            this.int1 = int1;
        }
    }

    static class LayerPossibilityStuff {
        HashMap<String, PossibilityInterval> possibilityInterval;
        Vector<String> content;

        public LayerPossibilityStuff() {
            possibilityInterval = new HashMap<>();
            content = new Vector<>();
        }

        static class PossibilityInterval {
            int intStart, intEnd;

            public PossibilityInterval(int intStart, int intEnd) {
                this.intStart = intStart;
                this.intEnd = intEnd;
            }
        }
    }

    void generateWorldConfig() {
        if (game == null) return;
        if (engine == null) return;
        engine.worldGenerator = C = this;
        engine.artifacts = new HashMap<>();
        engine.tileLayerNames = new HashMap<>();
        engine.layersDepth = new Vector<>();
        engine.teleports = new Vector<>();
        a();
        z = 0;
        o = game.levels.get(game.levelId).world;
        p = new World.View((o.view.cols - 1) / 2, (o.view.rows - 1) / 2);
        q = o.cols + 2 * p.cols;
        b();
        l();
        s = 0;
        y = 0;
        int c, g;
        char h;
        for (String i : o.layers.keySet()) {
            World.Layer k = o.layers.get(i);
            Engine.LayerDepth depth = new Engine.LayerDepth();
            engine.layersDepth.add(depth);
            depth.depth = k.rows;
            depth.start = z;
            z += k.rows;
            depth.end = z;
            if ("surface".equals(i)) {
                for (c = 0; c < k.rows; c++) {
                    engine.tileLayerNames.put(c + s, new HashMap<>());
                    for (g = -p.cols; g < o.cols + p.cols; g++) {
                        h = g < 0 || g > o.cols ? this.tileTypeToTypeCodeChar(Label.tileType.UNBREAKABLE) : c + s == 0 ? g == o.shop.col ? this.tileTypeToTypeCodeChar(Label.tileType.SHOP) : this.tileTypeToTypeCodeChar(Label.tileType.NONE) : this.tileTypeToTypeCodeChar(Label.tileType.SOIL);
                        this.setTileTypeCodeChar(g, c + s, h);
                        engine.tileLayerNames.get(c + s).put(g, i);
                    }
                }
            } else {
                t = new Vector<>(k.rows);
                for (c = 0; c < k.rows; c++) t.add(c, new LayerPossibilityStuff());
                HashMap<String, Integer> r = new HashMap<>();
                World.Layer.DeepLayer kDeep = (World.Layer.DeepLayer) k;
                for (String A : kDeep.content.keySet()) {
                    int B = kDeep.content.get(A).lastRowQuantityRatio;
                    int C = (B - 1) / (k.rows - 1);
                    r.put(A, 0);
                    for (int D = 0; D < t.size(); D++) {
                        int E = 1 + C * D;
                        t.get(D).possibilityInterval.put(A, new LayerPossibilityStuff.PossibilityInterval(r.get(A), r.get(A) + E));
                        r.put(A, r.get(A) + E);
                    }
                }
                HashMap<String, Integer> F = new HashMap<>();
                for (String A : kDeep.content.keySet()) {
                    F.put(A, 0);
                    for (c = 0; c < k.rows; c++) {
                        LayerPossibilityStuff.PossibilityInterval pi = t.get(c).possibilityInterval.get(A);
                        pi.intStart /= r.get(A);
                        pi.intEnd /= r.get(A);
                        F.put(A, F.get(A) + pi.intEnd - pi.intStart);
                    }
                }
                int G = o.cols * k.rows;
                for (String A : kDeep.content.keySet()) {
                    int v = (int) Math.ceil(kDeep.content.get(A).possibility * G);
                    w = 0;
                    x = 0;
                    u = 0;
                    if (kDeep.content.get(A).lastRowQuantityRatio >= 1) for (int D = 0; D < k.rows; D++) e(A, D);
                    else for (int D = k.rows - 1; D >= 0; D--) e(A, D);
                }
                for (LayerPossibilityStuff D : t) {
                    for (int H = 0; H < o.cols; H++)
                        if (D.content.size() <= H || D.content.get(H) == null) D.content.add(H, Label.tileType.SOIL);
                }
                for (c = 0; c < k.rows; c++) {
                    n(t.get(c).content);
                    engine.tileLayerNames.put(c + s, new HashMap<>());
                    for (g = -p.cols; g <= o.cols + p.cols; g++) {
                        String I = null;
                        if (k.rows - 1 == c) switch (i) {
                            case Label.layerName.LAYER_0 -> I = Label.layerName.LAYER_1;
                            case Label.layerName.LAYER_1 -> I = Label.layerName.LAYER_2;
                            case Label.layerName.LAYER_2 -> I = Label.layerName.LAYER_3;
                            case Label.layerName.LAYER_3 -> I = Label.layerName.LAYER_4;
                        }
                        String J;
                        if (I != null && engine.randomSeed() < .5) J = I;
                        else J = i;
                        h = g < 0 || g >= o.cols ? this.tileTypeToTypeCodeChar(Label.tileType.UNBREAKABLE) : t.get(c).content.get(g) != null ? this.tileTypeToTypeCodeChar(t.get(c).content.get(g)) : this.tileTypeToTypeCodeChar(Label.tileType.SOIL);
                        this.setTileTypeCodeChar(g, c + s, h);
                        engine.tileLayerNames.get(c + s).put(g, J);
                    }
                }
                f(i, kDeep);
                j(i, kDeep);
            }
            s += k.rows;
        }
        h = this.tileTypeToTypeCodeChar(Label.tileType.TELEPORT);
        this.setTileTypeCodeChar(((World.Layer.SurfaceLayer) o.layers.get("surface")).mainTeleportCol, 0, h);
        Teleport.mainTeleport.visible = engine.hero.mainTeleportVisible;
        Teleport.mainTeleport.row = 0;
        Teleport.mainTeleport.col = ((World.Layer.SurfaceLayer) o.layers.get("surface")).mainTeleportCol;
        Teleport.mainTeleport.jumpRow = 0;
        Teleport.mainTeleport.jumpCol = ((World.Layer.SurfaceLayer) o.layers.get("surface")).mainTeleportJumpCol;
        y++;
        m();
        d();
        engine.generatedShadowMap = engine.shadowMap;
        engine.generatedTileTypeCodeMap = engine.tileTypeCodeMap;
    }

    boolean isArtifactCodeChar(char a) {
        for (int b = 0; b < B.length(); b++) if (a == B.charAt(b) && A.get(b).contains("artifact")) return true;
        return false;
    }

    boolean tileExists(int a, int b) {
        return c(a, b) <= q * r;
    }

    char tileTypeToTypeCodeChar(String a) {
        for (int b = 0; b < A.size(); b++) if (A.get(b) == a) return B.charAt(b);
        return '\0';
    }

    String tileTypeToTypeCodeChar(char a) {
        for (int b = 0; b < B.length(); b++) if (B.charAt(b) == a) return A.get(b);
        return null;
    }

    void setTileTypeCodeChar(int a, int b, char d) {
        StringBuilder builder = new StringBuilder(engine.tileTypeCodeMap);
        int i = c(a, b);
        if (builder.length() <= i) builder.append("\0".repeat(i - builder.length() + 1));
        builder.setCharAt(i, d);
        engine.tileTypeCodeMap = builder.toString();
    }

    char getTileTypeCodeChar(int a, int b) {
        return engine.tileTypeCodeMap.charAt(c(a, b));
    }

    char getShadowValue(int a, int b) {
        return engine.shadowMap.charAt(c(a, b));
    }

    void setShadowValue(int a, int b, char d) {
        StringBuilder builder = new StringBuilder(engine.shadowMap);
        int i = c(a, b);
        if (builder.length() <= i) builder.append("\0".repeat(i - builder.length() + 1));
        builder.setCharAt(i, d);
        engine.shadowMap = builder.toString();
    }
}
