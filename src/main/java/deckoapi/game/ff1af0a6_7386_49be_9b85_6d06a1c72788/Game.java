package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import deckoapi.game.GameHacks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

public class Game {
    public final Vector<GameLevel> levels;
    public int levelId = 0;
    public float version = 0;
    public JSONObject gameConfig, mediaConfig;

    public Game(JSONObject gameConfig, JSONObject mediaConfig) {
        version = gameConfig.getFloat("version");
        this.gameConfig = gameConfig;
        this.mediaConfig = mediaConfig;
        JSONArray levels = gameConfig.getJSONArray("levels");
        this.levels = new Vector<>();
        for (Object level : levels) {
            GameLevel lv = new GameLevel((JSONObject) level);
            // System.out.println("Level: " + lv);
            this.levels.add(lv);
        }
    }

    public static class GameLevel {
        public int id;
        public HashMap<String, Achievement> achievements;
        public float energyUnitPrice;
        public int energyUnitsAlert;
        public Hero hero;
        public HashMap<String, InventoryItem> inventory;
        public Materials materials;
        public int rescueMaxPrice;
        public HashMap<String, Upgradeable> upgrades;
        public World world;

        public GameLevel(JSONObject l) {
            id = l.getInt("id");
            JSONObject achievementsObj = l.getJSONObject("achievments");
            achievements = new HashMap<>(achievementsObj.length());
            for (String name : achievementsObj.keySet()) {
                JSONObject aObj = achievementsObj.getJSONObject(name);
                Achievement achievement = new Achievement() {{
                    text = aObj.optString("text");
                    if (text != null) text = GameHacks.fixUTF(text);
                    bonus = aObj.optInt("bonus");
                    units = aObj.optInt("units");
                }};
                achievements.put(name, achievement);
            }
            energyUnitPrice = l.getFloat("energyUnitPrice");
            energyUnitsAlert = l.getInt("energyUnitsAlert");
            hero = new Hero(l.getJSONObject("hero"));
            JSONObject inv = l.getJSONObject("inventory");
            JSONObject tnt = inv.getJSONObject("tnt");
            JSONObject son = inv.getJSONObject("sonar");
            JSONObject ene = inv.getJSONObject("energy");
            inventory = new HashMap<>() {{
                put("tnt", new InventoryItem.TNT() {{
                    name = GameHacks.fixUTF(tnt.getString("name"));
                    description = GameHacks.fixUTF(tnt.getString("description"));
                    radius = tnt.getInt("radius");
                    price = tnt.getInt("price");
                }});
                put("sonar", new InventoryItem.Sonar() {{
                    name = GameHacks.fixUTF(son.getString("name"));
                    description = GameHacks.fixUTF(son.getString("description"));
                    radius = son.getInt("radius");
                    price = son.getInt("price");
                }});
                put("energy", new InventoryItem.Energy() {{
                    name = GameHacks.fixUTF(ene.getString("name"));
                    description = GameHacks.fixUTF(ene.getString("description"));
                    unitsIncrement = ene.getInt("unitsIncrement");
                    price = ene.getInt("price");
                }});
            }};
            materials = new Materials();
            JSONObject mo = l.getJSONObject("materials");
            materials.defaultMoveTime = mo.getFloat("defaultMoveTime");
            for (String name : mo.keySet()) {
                if ("defaultMoveTime".equals(name)) continue;
                JSONObject materialObj = mo.getJSONObject(name);
                int bonus = materialObj.optInt("bonus", 0), resistance = materialObj.getInt("resistance");
                Materials.Material material;
                if (materialObj.has("name") && materialObj.has("price"))
                    material = new Materials.Material.Sellable(bonus, resistance, materialObj.getInt("price"), GameHacks.fixUTF(materialObj.getString("name")));
                else material = new Materials.Material(bonus, resistance);
                materials.materials.put(name, material);
            }
            rescueMaxPrice = l.getInt("rescueMaxPrice");
            upgrades = new HashMap<>();
            JSONObject uo = l.getJSONObject("upgrades");
            for (String name : uo.keySet()) {
                JSONObject upgradeableObj = uo.getJSONObject(name);
                Upgradeable upgradeable = new Upgradeable();
                for (String upgradeName : upgradeableObj.keySet()) {
                    JSONObject upgradeObj = upgradeableObj.getJSONObject(upgradeName);
                    Upgradeable.Upgrade upgrade;
                    switch (upgradeName) {
                        case "drill" -> upgrade = new Upgradeable.Upgrade.Drill(upgradeObj);
                        case "backpack" -> upgrade = new Upgradeable.Upgrade.Backpack(upgradeObj);
                        case "energystorage" -> upgrade = new Upgradeable.Upgrade.EnergyStorage(upgradeObj);
                        case "flashlight" -> upgrade = new Upgradeable.Upgrade.Flashlight(upgradeObj);
                        default -> upgrade = new Upgradeable.Upgrade(upgradeObj);
                    }
                    upgradeable.upgrades.put(name, upgrade);
                }
                upgrades.put(name, upgradeable);
            }
            world = new World(l.getJSONObject("world"));
        }

        public static class Achievement {
            public String text;
            public int bonus, units;
        }

        public static class InventoryItem {
            public String name, description;
            public int price;

            public static class Energy extends InventoryItem {
                public int unitsIncrement;
            }

            public static class TNT extends InventoryItem {
                public int radius;
            }

            public static class Sonar extends InventoryItem {
                public int radius;
            }
        }

        public static class Materials {
            public HashMap<String, Material> materials;
            public float defaultMoveTime;

            public Materials() {
                materials = new HashMap<>();
            }


            public static class Material {
                public int bonus, resistance;

                public Material(int bonus, int resistance) {
                    this.bonus = bonus;
                    this.resistance = resistance;
                }

                public static class Sellable extends Material {
                    public int price;
                    public String name;

                    public Sellable(int bonus, int resistance, int price, String name) {
                        super(bonus, resistance);
                        this.price = price;
                        this.name = name;
                    }
                }
            }
        }

        public static class Upgradeable {
            public HashMap<String, Upgrade> upgrades;

            public Upgradeable() {
                upgrades = new HashMap<>();
            }

            public static class Upgrade {
                public int price;
                public String name, description;

                public Upgrade(int price, String name, String description) {
                    this.price = price;
                    this.name = name;
                    this.description = description;
                }

                public Upgrade(JSONObject src) {
                    this.price = src.getInt("price");
                    this.name = src.getString("name");
                    this.description = src.getString("description");
                }

                public static class Backpack extends Upgrade {
                    public int units;

                    public Backpack(int price, String name, String description, int units) {
                        super(price, name, description);
                        this.units = units;
                    }

                    public Backpack(JSONObject src) {
                        super(src);
                        this.units = src.getInt("units");
                    }
                }

                public static class Drill extends Upgrade {
                    public int timeScale;
                    public HashMap<String, Boolean> layers;
                    public String lock;

                    public Drill(int price, String name, String description, int timeScale, HashMap<String, Boolean> layers, String lock) {
                        super(price, name, description);
                        this.timeScale = timeScale;
                        this.layers = layers;
                        this.lock = lock;
                    }

                    public Drill(JSONObject src) {
                        super(src);
                        this.timeScale = src.getInt("timeScale");
                        this.layers = new HashMap<>();
                        JSONObject layers = src.getJSONObject("layers");
                        for (String layer : layers.keySet()) layers.put(layer, layers.getBoolean(layer));
                        this.lock = src.getString("lock");
                    }
                }

                public static class EnergyStorage extends Upgrade {
                    public int units;

                    public EnergyStorage(int price, String name, String description, int units) {
                        super(price, name, description);
                        this.units = units;
                    }

                    public EnergyStorage(JSONObject src) {
                        super(src);
                        this.units = src.getInt("units");
                    }
                }

                public static class Flashlight extends Upgrade {
                    public int radius, treshold;

                    public Flashlight(int price, String name, String description, int radius, int treshold) {
                        super(price, name, description);
                        this.radius = radius;
                        this.treshold = treshold;
                    }

                    public Flashlight(JSONObject src) {
                        super(src);
                        this.radius = src.getInt("radius");
                        this.treshold = src.getInt("treshold");
                    }
                }
            }
        }
    }
}
