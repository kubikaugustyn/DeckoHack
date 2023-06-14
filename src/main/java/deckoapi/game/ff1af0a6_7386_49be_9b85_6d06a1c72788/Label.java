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
     var b = <SOURCE>.split("\n").map(a=>a.trim())
     b = b.map(a=>{
        var b = a.split(" = ")
        return `put("${b[0]}", ${b[1]});`
     }).join("\n").replaceAll(",);", ");")
     console.log(b)
     */
    HashMap<String, String> tileTypeMap = new HashMap<>() {
        {
            put("NONE", tileType.NONE);
            put("SHOP", tileType.SHOP);
            put("INVISIBLE", tileType.INVISIBLE);
            put("HORIZON", tileType.HORIZON);
            put("UNBREAKABLE", tileType.UNBREAKABLE);
            put("TELEPORT", tileType.TELEPORT);
            put("COIN_0", tileType.COIN_0);
            put("COIN_1", tileType.COIN_1);
            put("COIN_2", tileType.COIN_2);
            put("COIN_3", tileType.COIN_3);
            put("COIN_4", tileType.COIN_4);
            put("BONUS", tileType.BONUS);
            put("ARTIFACT_0", tileType.ARTIFACT_0);
            put("ARTIFACT_1", tileType.ARTIFACT_1);
            put("ARTIFACT_2", tileType.ARTIFACT_2);
            put("ARTIFACT_3", tileType.ARTIFACT_3);
            put("ARTIFACT_4", tileType.ARTIFACT_4);
            put("ARTIFACT_5", tileType.ARTIFACT_5);
            put("ARTIFACT_6", tileType.ARTIFACT_6);
            put("ARTIFACT_7", tileType.ARTIFACT_7);
            put("ARTIFACT_8", tileType.ARTIFACT_8);
            put("ARTIFACT_9", tileType.ARTIFACT_9);
            put("ARTIFACT_10", tileType.ARTIFACT_10);
            put("ARTIFACT_11", tileType.ARTIFACT_11);
            put("ARTIFACT_12", tileType.ARTIFACT_12);
            put("ARTIFACT_13", tileType.ARTIFACT_13);
            put("ARTIFACT_14", tileType.ARTIFACT_14);
            put("ARTIFACT_15", tileType.ARTIFACT_15);
            put("ARTIFACT_16", tileType.ARTIFACT_16);
            put("ARTIFACT_17", tileType.ARTIFACT_17);
            put("ARTIFACT_18", tileType.ARTIFACT_18);
            put("ARTIFACT_19", tileType.ARTIFACT_19);
            put("ARTIFACT_20", tileType.ARTIFACT_20);
            put("ARTIFACT_21", tileType.ARTIFACT_21);
            put("ARTIFACT_22", tileType.ARTIFACT_22);
            put("ARTIFACT_23", tileType.ARTIFACT_23);
            put("ARTIFACT_24", tileType.ARTIFACT_24);
            put("ARTIFACT_25", tileType.ARTIFACT_25);
            put("ARTIFACT_26", tileType.ARTIFACT_26);
            put("ARTIFACT_27", tileType.ARTIFACT_27);
            put("ARTIFACT_28", tileType.ARTIFACT_28);
            put("ARTIFACT_29", tileType.ARTIFACT_29);
            put("ARTIFACT_30", tileType.ARTIFACT_30);
            put("ARTIFACT_31", tileType.ARTIFACT_31);
            put("ARTIFACT_32", tileType.ARTIFACT_32);
            put("ARTIFACT_33", tileType.ARTIFACT_33);
            put("ARTIFACT_34", tileType.ARTIFACT_34);
            put("OBSTACLE", tileType.OBSTACLE);
            put("SOIL", tileType.SOIL);
            put("CAVE_UP", tileType.CAVE_UP);
            put("CAVE_DOWN", tileType.CAVE_DOWN);
        }
    };

    interface key {
        String UP = "up",
                RIGHT = "right",
                DOWN = "down",
                LEFT = "left";
    }
}