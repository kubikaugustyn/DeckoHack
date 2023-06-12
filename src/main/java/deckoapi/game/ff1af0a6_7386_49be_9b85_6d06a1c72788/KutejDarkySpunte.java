package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import deckoapi.AmfConnectorCallback;
import deckoapi.AmfConnectorResult;
import deckoapi.IntegrativeContainer;
import deckoapi.game.GameHacks;
import deckoapi.jshelper.XMLHttpRequest;
import gui.DeckoHack;
import org.json.JSONObject;
import rufus.lzstring4java.LZString;

import java.awt.*;

public class KutejDarkySpunte extends GameHacks {
    private String resourcesURL = "https://decko.ceskatelevize.cz/rest/FileStore/FLASH_APP_DATA_PACKAGES/FILE-3294430!";
    public Game game;

    public KutejDarkySpunte() {
        super("Kutej dárky, špunte!", "https://decko.ceskatelevize.cz/flashAppIframe/ff1af0a6-7386-49be-9b85-6d06a1c72788?closeButtonEnabled=false");
        this.initComponents();
    }

    @Override
    protected void initIC() {
        // For: https://decko.ceskatelevize.cz/flashAppIframe/ff1af0a6-7386-49be-9b85-6d06a1c72788?closeButtonEnabled=false
        IC.sendAppRequest("ff1af0a6-7386-49be-9b85-6d06a1c72788", "/rest/Token/flashVars", null, "closeButtonEnabled=false", null);
    }

    @Override
    protected void dpContReady() {
        game = null;
        JSONObject ic = IC.appRequestJSON.getJSONObject("integrativeContainer");
        String poster = ic.getString("posterUrnPath");
        String url = resourcesURL = ic.getString("urnServiceUrl") + poster.substring(0, poster.indexOf("/"));
        // /dat/MediaConfig.json
        // /dat/GameConfig.json
        //System.out.println(url);

        XMLHttpRequest.getJSON(url + "/dat/MediaConfig.json", 10_000, ex -> {
        }, this::loadGameConfig, con -> {
            // Injected code...
            XMLHttpRequest<IntegrativeContainer> request = new XMLHttpRequest<>();
            request.open("", "", false);
            DeckoHack.instance.setRequestHeaders(request, true);
            for (String key : request.getRequestHeaders().keySet())
                con.setRequestProperty(key, request.getRequestHeaders().get(key));
        });
    }

    private void loadGameConfig(JSONObject mediaConfig) {
        XMLHttpRequest.getJSON(resourcesURL + "/dat/GameConfig.json", 10_000, ex -> {
        }, response -> parseGameConfigs(mediaConfig, response), con -> {
            // Injected code...
            XMLHttpRequest<IntegrativeContainer> request = new XMLHttpRequest<>();
            request.open("", "", false);
            DeckoHack.instance.setRequestHeaders(request, true);
            for (String key : request.getRequestHeaders().keySet())
                con.setRequestProperty(key, request.getRequestHeaders().get(key));
        });
    }

    private void parseGameConfigs(JSONObject mediaConfig, JSONObject gameConfig) {
        game = new Game(gameConfig, mediaConfig);
    }

    @Override
    protected void initComponents() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);
        this.setResizable(true);
        this.setTitle(fixUTF("Kutej Dárky, Špunte!"));
        this.setName("KutejDarkySpunteFrame");
        this.setIconImage(DeckoHack.getImage("KutejDarkySpunte/favicon.png"));
    }

    @Override
    protected void loadData() {
        for (AmfConnectorResult.AppStatesResult.Slot slot : slots) {
            System.out.println(slot);
            AmfConnectorCallback callback = res -> {
                if (errorResult(res)) return;
                System.out.println(res.status);
                AmfConnectorResult.LoadAppStateResult appStateResult = (AmfConnectorResult.LoadAppStateResult) res;
                System.out.println(appStateResult.resourceType);
                System.out.println(appStateResult.state);
                System.out.println("Decompressed: " + LZString.decompressFromUTF16(appStateResult.state));
                System.out.println(appStateResult.image);
            };
            IC.amfConnector.loadAppState(callback, slot.slotNumber, "startSeed", "text");
            IC.amfConnector.loadAppState(callback, slot.slotNumber, "hero", "text");
            IC.amfConnector.loadAppState(callback, slot.slotNumber, "shadowMap", "text");
            IC.amfConnector.loadAppState(callback, slot.slotNumber, "tileTypeCodeMap", "text");
        }

        IC.amfConnector.getTokenStatus(res -> System.out.println("Token: " + res.status + " (" + IC.amfConnector.getToken() + ")"));
    }

    @Override
    protected void saveData() {

    }
}
