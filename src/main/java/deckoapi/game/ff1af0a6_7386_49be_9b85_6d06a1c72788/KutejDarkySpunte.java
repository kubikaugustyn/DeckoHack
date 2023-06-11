package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import deckoapi.AmfConnector;
import deckoapi.AmfConnectorCallback;
import deckoapi.AmfConnectorResult;
import deckoapi.game.GameHacks;
import gui.DeckoHack;
import rufus.lzstring4java.LZString;

import java.awt.*;
import java.util.Objects;

public class KutejDarkySpunte extends GameHacks {
    public KutejDarkySpunte() {
        this.initComponents();
    }

    @Override
    protected void initComponents() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);
        this.setResizable(true);
        this.setTitle("Kutej Darky, Spunte!");
        this.setName("KutejDarkySpunteFrame");
        this.setIconImage(DeckoHack.getImage("KutejDarkySpunte/favicon.png"));
    }

    @Override
    protected void loadData() {
        AmfConnector.getAppStates(result -> {
            if (!GameHacks.errorResult(result)) {
                System.out.println(result.status);
                AmfConnectorResult.AppStatesResult appStatesResult = (AmfConnectorResult.AppStatesResult) result;
                for (AmfConnectorResult.AppStatesResult.Slot slot : appStatesResult.slots) {
                    System.out.println(slot);
                    AmfConnectorCallback callback = res -> {
                        if (!GameHacks.errorResult(res)) {
                            System.out.println(res.status);
                            AmfConnectorResult.LoadAppStateResult appStateResult = (AmfConnectorResult.LoadAppStateResult) res;
                            System.out.println(appStateResult.resourceType);
                            System.out.println(appStateResult.state);
                            System.out.println("Decompressed: " + LZString.decompressFromUTF16(appStateResult.state));
                            System.out.println(appStateResult.image);
                        }
                    };
                    AmfConnector.loadAppState(callback, slot.slotNumber, "startSeed", "text");
                    AmfConnector.loadAppState(callback, slot.slotNumber, "hero", "text");
                    AmfConnector.loadAppState(callback, slot.slotNumber, "shadowMap", "text");
                    AmfConnector.loadAppState(callback, slot.slotNumber, "tileTypeCodeMap", "text");
                }

                AmfConnector.getTokenStatus(res -> System.out.println("Token: " + result.status + " (" + AmfConnector.getToken() + ")"));
            }
        });
    }

    @Override
    protected void saveData() {

    }
}
