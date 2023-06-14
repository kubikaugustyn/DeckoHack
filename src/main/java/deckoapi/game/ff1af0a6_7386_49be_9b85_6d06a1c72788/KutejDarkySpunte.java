package deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788;

import deckoapi.AmfConnectorCallback;
import deckoapi.AmfConnectorResult;
import deckoapi.IntegrativeContainer;
import deckoapi.game.GameHacks;
import deckoapi.jshelper.XMLHttpRequest;
import gui.DeckoHack;
import org.json.JSONObject;
import rufus.lzstring4java.LZString;

import javax.swing.*;
import java.awt.*;

public class KutejDarkySpunte extends GameHacks {
    private Engine engine;
    private String resourcesURL = "https://decko.ceskatelevize.cz/rest/FileStore/FLASH_APP_DATA_PACKAGES/FILE-3294430!";
    public Game game;
    private MapEditorPane MapEditorPane;

    public KutejDarkySpunte() {
        super("Kutej dárky, špunte!", "https://decko.ceskatelevize.cz/flashAppIframe/ff1af0a6-7386-49be-9b85-6d06a1c72788?closeButtonEnabled=false");
        engine = new Engine();
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
        System.out.println(mediaConfig);
        System.out.println(gameConfig);
        System.out.println("Parsing game");
        game = new Game(gameConfig, mediaConfig);
        System.out.println("Game parsed");
        engine.worldGenerator.game = game;
        System.out.println(game);
    }

    @Override
    protected void initComponents() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);
        this.setResizable(true);
        this.setTitle(fixUTF("Kutej Dárky, Špunte!"));
        this.setName("KutejDarkySpunteFrame");
        this.setIconImage(DeckoHack.getImage("KutejDarkySpunte/favicon.png"));
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        this.MapEditorPane = new MapEditorPane();
        jTabbedPane1.addTab("Editor mapy", new ImageIcon(DeckoHack.getImage("KutejDarkySpunte/MapEditor.png")), this.MapEditorPane);
        jTabbedPane1.setMinimumSize(new Dimension(400, 400));
        add(jTabbedPane1);
    }

    @Override
    protected void loadData() {
        /*for (AmfConnectorResult.AppStatesResult.Slot slot : slots) {
            System.out.println(slot);
            AmfConnectorCallback callback = res -> {
                if (errorResult(res)) return;
                System.out.println("AppStateCallback: " + res.status);
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

        IC.amfConnector.getTokenStatus(res -> System.out.println("Token: " + res.status + " (" + IC.amfConnector.getToken() + ")"));*/
        load();
    }

    @Override
    protected void saveData() {

    }

    void load() {
        IC.amfConnector.loadAppState(this::onStartSeedLoad, 0, "startSeed", "text");
    }

    void onStartSeedLoad(AmfConnectorResult result) {
        if (errorResult(result)) loadFailed(result);
        else {
            AmfConnectorResult.LoadAppStateResult appState = (AmfConnectorResult.LoadAppStateResult) result;
            engine.startSeed = Float.parseFloat(appState.state);
            engine.seed = engine.startSeed;
            IC.amfConnector.loadAppState(this::onHeroLoad, 0, "hero", "text");
        }
    }

    void onHeroLoad(AmfConnectorResult result) {
        if (errorResult(result)) loadFailed(result);
        else {
            AmfConnectorResult.LoadAppStateResult appState = (AmfConnectorResult.LoadAppStateResult) result;
            JSONObject heroJson = new JSONObject(LZString.decompressFromUTF16(appState.state));
            engine.hero = new Hero(heroJson);
            engine.worldGenerator.generateWorldConfig();
            IC.amfConnector.loadAppState(this::onShadowMapLoad, 0, "shadowMap", "text");
        }
    }

    void onShadowMapLoad(AmfConnectorResult result) {
        if (errorResult(result)) loadFailed(result);
        else {
            AmfConnectorResult.LoadAppStateResult appState = (AmfConnectorResult.LoadAppStateResult) result;
            engine.shadowMap = mapPreprocessor(engine.shadowMap, LZString.decompressFromUTF16(appState.state));
            IC.amfConnector.loadAppState(this::onTileTypeCodeMapLoad, 0, "tileTypeCodeMap", "text");
        }
    }

    void onTileTypeCodeMapLoad(AmfConnectorResult result) {
        if (errorResult(result)) loadFailed(result);
        else {
            AmfConnectorResult.LoadAppStateResult appState = (AmfConnectorResult.LoadAppStateResult) result;
            engine.tileTypeCodeMap = mapPreprocessor(engine.tileTypeCodeMap, LZString.decompressFromUTF16(appState.state));
            startLevel();
        }
    }

    void loadFailed(AmfConnectorResult result) {
        System.out.println("Load failed");
    }

    void startLevel() {
        System.out.println("Start level");
    }

    String mapPreprocessor(String a, String b) {
        StringBuilder result = new StringBuilder(a);
        for (int i = 0; i < b.length(); i++) if (b.charAt(i) != '#') result.setCharAt(i, b.charAt(i));
        return result.toString();
    }
}
