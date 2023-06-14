package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Hero {
    public HashMap<String, HeroAchievement> achievments;
    public HashMap<String, Vector<String>> artifacts;
    public Vector<String> backpack;
    public String color;
    public int energyUnits;
    public HashMap<String, Boolean> help;
    public HeroInventory inventory;
    public Teleport lastUsedTeleport;
    public HashMap<String, Boolean> layersReached;
    public boolean mainTeleportVisible;
    public int money;
    public String music;
    public HeroPos pos;
    public HashMap<String, String> upgrades;

    public Hero(JSONObject h) {
        JSONObject achievementsObj = h.getJSONObject("achievments");
        achievments = new HashMap<>(achievementsObj.length());
        for (String name : achievementsObj.keySet()) {
            HeroAchievement achievement;
            if (achievementsObj.get(name) instanceof JSONArray) {
                achievement = new HeroAchievement.SberatelKosti() {{
                    values = new Vector<>();
                    for (Object a : achievementsObj.getJSONArray(name).toList()) values.add((String) a);
                }};
            } else {
                JSONObject aObj = achievementsObj.getJSONObject(name);
                achievement = new HeroAchievement() {{
                    masteryLevel = aObj.optInt("masteryLevel");
                    unitsCount = aObj.optInt("masteryLevel");
                }};
            }
            achievments.put(name, achievement);
        }
        JSONObject artifactsObj = h.getJSONObject("artifacts");
        artifacts = new HashMap<>(artifactsObj.length());
        for (String name : artifactsObj.keySet()) {
            Vector<String> values = new Vector<>();
            for (int i = 0; i < artifactsObj.getJSONArray(name).length(); i++)
                values.add(artifactsObj.getJSONArray(name).getString(i));
            artifacts.put(name, values);
        }
        backpack = new Vector<>();
        for (int i = 0; i < h.getJSONArray("backpack").length(); i++)
            backpack.add(h.getJSONArray("backpack").getString(i));
        color = h.getString("color");
        energyUnits = h.getInt("energyUnits");
        JSONObject helpObj = h.getJSONObject("help");
        help = new HashMap<>(helpObj.length());
        for (String name : helpObj.keySet()) help.put(name, helpObj.getBoolean(name));
        JSONObject invObj = h.getJSONObject("inventory");
        inventory = new HeroInventory() {{
            sonar = invObj.getInt("sonar");
            tnt = invObj.getInt("tnt");
            energy = invObj.getInt("energy");
        }};
        if (h.get("lastUsedTeleport") instanceof String && "main".equals(h.getString("lastUsedTeleport")))
            lastUsedTeleport = Teleport.mainTeleport;
        else lastUsedTeleport = new Teleport(h.getInt("lastUsedTeleport"));
        JSONObject layersReachedObj = h.getJSONObject("layersReached");
        layersReached = new HashMap<>(layersReachedObj.length());
        for (String name : layersReachedObj.keySet()) layersReached.put(name, layersReachedObj.getBoolean(name));
        mainTeleportVisible = h.getBoolean("mainTeleportVisible");
        money = h.getInt("money");
        music = h.getString("music");
        JSONObject posObj = h.getJSONObject("pos");
        pos = new HeroPos() {{
            col = posObj.getInt("col");
            row = posObj.getInt("row");
            dir = posObj.getString("dir");
        }};
        JSONObject upgradesObj = h.getJSONObject("upgrades");
        upgrades = new HashMap<>(upgradesObj.length());
        for (String name : upgradesObj.keySet()) upgrades.put(name, upgradesObj.getString(name));
    }

    public static class HeroAchievement {
        public int masteryLevel, unitsCount;

        public static class SberatelKosti extends HeroAchievement {
            public Vector<String> values;
        }
    }

    public static class HeroInventory {
        public int energy, sonar, tnt;
    }

    public static class HeroPos {
        public int col, row;
        public String dir;
    }
}
