package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import java.util.HashMap;

public interface Label {
    interface user {
        String MEMBER = "member",
                ANONYMOUS = "anonymous",
                EVERYONE = "everyone";
    }

    interface action {
    }

    interface screen {
        String QUIT = "quit",
                HOME = "home",
                FINAL = "final",
                INSTRUCTIONS = "instructions",
                TYPE_IMAGE = "type_image",
                TYPE_BUTTON = "type_button";
    }

    interface buttonType {
        String QUIT = "quit",
                DISABLED = "disabled",
                MENU = "menu",
                WINSCREEN = "winscreen",
                SHOP = "shop",
                SHOP_UPGRADE = "shop upgrade",
                SHOP_INVENTORY = "shop inventory",
                UPGRADE_POCKET = "upgrade backpack",
                UPGRADE_ENERGY = "upgrade energy",
                UPGRADE_FLASHLIGHT = "upgrade flashlight",
                UPGRADE_DRILL = "upgrade drill",
                USE_INVENTORY_ITEM = "use inventory item";
    }

    interface menuBtns {
        String QUIT = "quit",
                NEW = "new",
                CONTINUE = "continue";
    }

    interface shopBtns {
        String QUIT = "quit",
                MENU = "menu",
                NEXT = "next",
                PREV = "prev";
    }

    interface follows {
        String HERO = "hero",
                SHOP = "shop";
    }

    interface inventory {
        String TNT = "tnt",
                SONAR = "sonar",
                ENERGY = "energy";
    }

    interface upgrades {
        String DRILL = "drill",
                BACKPACK = "backpack",
                ENERGY_STORAGE = "energystorage",
                FLASHLIGHT = "flashlight";
    }

    interface phase {
        String INACTIVE = "inactive",
                WAITING = "waiting",
                MOVE = "move",
                TELEPORT = "teleport",
                SHOP_SUMMARY = "shop summary",
                SHOP_UPGRADES = "shop upgrades",
                SHOP_INVENTORY = "shop inventory",
                INVENTORY = "inventory",
                MENU = "menu";
    }

    interface layerName {
        String SURFACE = "surface",
                LAYER_0 = "layer_0",
                LAYER_1 = "layer_1",
                LAYER_2 = "layer_2",
                LAYER_3 = "layer_3",
                LAYER_4 = "layer_4";
    }

    interface tileType {
        String NONE = "none",
                SHOP = "shop",
                INVISIBLE = "invisible",
                HORIZON = "horizon",
                UNBREAKABLE = "unbreakable",
                TELEPORT = "teleport",
                COIN_0 = "coin_0",
                COIN_1 = "coin_1",
                COIN_2 = "coin_2",
                COIN_3 = "coin_3",
                COIN_4 = "coin_4",
                BONUS = "bonus",
                ARTIFACT_0 = "artifact_0",
                ARTIFACT_1 = "artifact_1",
                ARTIFACT_2 = "artifact_2",
                ARTIFACT_3 = "artifact_3",
                ARTIFACT_4 = "artifact_4",
                ARTIFACT_5 = "artifact_5",
                ARTIFACT_6 = "artifact_6",
                ARTIFACT_7 = "artifact_7",
                ARTIFACT_8 = "artifact_8",
                ARTIFACT_9 = "artifact_9",
                ARTIFACT_10 = "artifact_10",
                ARTIFACT_11 = "artifact_11",
                ARTIFACT_12 = "artifact_12",
                ARTIFACT_13 = "artifact_13",
                ARTIFACT_14 = "artifact_14",
                ARTIFACT_15 = "artifact_15",
                ARTIFACT_16 = "artifact_16",
                ARTIFACT_17 = "artifact_17",
                ARTIFACT_18 = "artifact_18",
                ARTIFACT_19 = "artifact_19",
                ARTIFACT_20 = "artifact_20",
                ARTIFACT_21 = "artifact_21",
                ARTIFACT_22 = "artifact_22",
                ARTIFACT_23 = "artifact_23",
                ARTIFACT_24 = "artifact_24",
                ARTIFACT_25 = "artifact_25",
                ARTIFACT_26 = "artifact_26",
                ARTIFACT_27 = "artifact_27",
                ARTIFACT_28 = "artifact_28",
                ARTIFACT_29 = "artifact_29",
                ARTIFACT_30 = "artifact_30",
                ARTIFACT_31 = "artifact_31",
                ARTIFACT_32 = "artifact_32",
                ARTIFACT_33 = "artifact_33",
                ARTIFACT_34 = "artifact_34",
                OBSTACLE = "obstacle",
                SOIL = "soil",
                CAVE_UP = "cave_up",
                CAVE_DOWN = "cave_down";
    }

    /*
     var b = a.split("\n").map(a=>a.trim())
     b = b.map(a=>{
        var b = a.split(" = ")
        return `put("${b[0]}", ${b[1]});`
     }).join("\n").replaceAll(",);", ");")
     console.log(b)
     */
    HashMap<String, String> tileTypeMap = new HashMap<>() {
        {
            put("NONE", "none");
            put("SHOP", "shop");
            put("INVISIBLE", "invisible");
            put("HORIZON", "horizon");
            put("UNBREAKABLE", "unbreakable");
            put("TELEPORT", "teleport");
            put("COIN_0", "coin_0");
            put("COIN_1", "coin_1");
            put("COIN_2", "coin_2");
            put("COIN_3", "coin_3");
            put("COIN_4", "coin_4");
            put("BONUS", "bonus");
            put("ARTIFACT_0", "artifact_0");
            put("ARTIFACT_1", "artifact_1");
            put("ARTIFACT_2", "artifact_2");
            put("ARTIFACT_3", "artifact_3");
            put("ARTIFACT_4", "artifact_4");
            put("ARTIFACT_5", "artifact_5");
            put("ARTIFACT_6", "artifact_6");
            put("ARTIFACT_7", "artifact_7");
            put("ARTIFACT_8", "artifact_8");
            put("ARTIFACT_9", "artifact_9");
            put("ARTIFACT_10", "artifact_10");
            put("ARTIFACT_11", "artifact_11");
            put("ARTIFACT_12", "artifact_12");
            put("ARTIFACT_13", "artifact_13");
            put("ARTIFACT_14", "artifact_14");
            put("ARTIFACT_15", "artifact_15");
            put("ARTIFACT_16", "artifact_16");
            put("ARTIFACT_17", "artifact_17");
            put("ARTIFACT_18", "artifact_18");
            put("ARTIFACT_19", "artifact_19");
            put("ARTIFACT_20", "artifact_20");
            put("ARTIFACT_21", "artifact_21");
            put("ARTIFACT_22", "artifact_22");
            put("ARTIFACT_23", "artifact_23");
            put("ARTIFACT_24", "artifact_24");
            put("ARTIFACT_25", "artifact_25");
            put("ARTIFACT_26", "artifact_26");
            put("ARTIFACT_27", "artifact_27");
            put("ARTIFACT_28", "artifact_28");
            put("ARTIFACT_29", "artifact_29");
            put("ARTIFACT_30", "artifact_30");
            put("ARTIFACT_31", "artifact_31");
            put("ARTIFACT_32", "artifact_32");
            put("ARTIFACT_33", "artifact_33");
            put("ARTIFACT_34", "artifact_34");
            put("OBSTACLE", "obstacle");
            put("SOIL", "soil");
            put("CAVE_UP", "cave_up");
            put("CAVE_DOWN", "cave_down");
        }
    };

    interface key {
        String UP = "up",
                RIGHT = "right",
                DOWN = "down",
                LEFT = "left";
    }
}