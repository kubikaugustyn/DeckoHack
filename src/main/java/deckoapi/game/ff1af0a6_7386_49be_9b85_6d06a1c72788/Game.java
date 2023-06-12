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
            JSONObject l = (JSONObject) level;
            GameLevel lv = new GameLevel() {{
                id = l.getInt("id");
                JSONObject achievementsObj = l.getJSONObject("achievments");
                achievements = new HashMap<>(achievementsObj.length());
                for (String name : achievementsObj.keySet()) {
                    JSONObject aObj = achievementsObj.getJSONObject(name);
                    Achievement achievement = new Achievement() {{
                        text = aObj.optString("name");
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
                        name = tnt.getString("name");
                        description = tnt.getString("description");
                        radius = tnt.getInt("radius");
                        price = tnt.getInt("price");
                    }});
                    put("sonar", new InventoryItem.Sonar() {{
                        name = son.getString("name");
                        description = son.getString("description");
                        radius = son.getInt("radius");
                        price = son.getInt("price");
                    }});
                    put("energy", new InventoryItem.Energy() {{
                        name = ene.getString("name");
                        description = ene.getString("description");
                        unitsIncrement = ene.getInt("unitsIncrement");
                        price = ene.getInt("price");
                    }});
                }};
                //Materials
                rescueMaxPrice = l.getInt("rescueMaxPrice");
                // TODO
                // World
            }};
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

            public static class Material {
                public int bonus, resistance;

                public static class Sellable extends Material {
                    public int price;
                    public String name;
                }
            }
        }

        public static class Upgradeable {
            public HashMap<String, Upgrade> upgrades;

            public static class Upgrade {
                public int price;
                public String name, description;

                public static class Backpack extends Upgrade {
                    public int units;
                }

                public static class Drill extends Upgrade {
                    public int timeScale;
                    public HashMap<String, Boolean> layers;
                }

                public static class EnergyStorage extends Upgrade {
                    public int units;
                }

                public static class Flashlight extends Upgrade {
                    public int radius, treshold;
                }
            }
        }
    }
}
