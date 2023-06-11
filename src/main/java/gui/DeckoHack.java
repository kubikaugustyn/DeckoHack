package gui;

import deckoapi.*;
import deckoapi.IntegrativeContainer;
import deckoapi.game.GameHacks;
import deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788.KutejDarkySpunte;
import deckoapi.jshelper.XMLHttpRequest;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;

import rufus.lzstring4java.LZString;

public class DeckoHack extends JFrame {
    public static DeckoHack instance;
    private Properties cfg;
    private String username = "username", password = "password";
    private HashMap<String, String> cookies = new HashMap<>();
    private File appdataDir = null, credentials = null;
    public static KutejDarkySpunte kutejDarkySpunte;
    public static HashMap<String, GameHacks> gameHacks;

    public static void main(final String[] array) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        DeckoHack.instance = new DeckoHack();
        DeckoHack.instance.init();
    }

    public DeckoHack() {

    }

    private void init() {
        this.initComponents();
        try {
            this.appdataDir = new File(System.getenv("APPDATA") + File.separatorChar + "DeckoHack");
            this.appdataDir.mkdir();
        } catch (Exception ex) {
            System.err.println("Error creating user preferences directory - " + this.appdataDir.getAbsolutePath());
            System.exit(0);
        }
        try {
            this.cfg = new Properties();
            credentials = new File(this.appdataDir.getAbsolutePath() + File.separatorChar + "credentials");
            if (!credentials.exists()) saveCredentials();
            else loadCredentials();
        } catch (Exception ex2) {
            System.err.println("Error reading credentials file - " + credentials.getAbsolutePath());
            System.exit(0);
        }
        //this.setSize(800, 600);
        GameHacks.initSize(this);
        this.loadCredentials();
        this.tryLogin();
    }

    private void onAppReady() {
        this.setVisible(true);
    }

    private void acquireBaseCookies() {
        XMLHttpRequest<DeckoHack> request = new XMLHttpRequest<>();
        request.open("GET", "https://decko.ceskatelevize.cz/", false);
        request.onreadystatechange = req -> {
            if (req.readyState == XMLHttpRequest.DONE) {
                //System.out.println(req.responseText);
                cookies.clear();
                processSetCookieHeaders(req);
            }
        };
        setRequestHeaders(request, false);
        request.send();
    }

    public void setRequestHeaders(XMLHttpRequest<?> request, boolean setCookie) {
        request.setRequestHeader("Origin", "https://decko.ceskatelevize.cz");
        request.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        request.setRequestHeader("Pragma", "no-cache");
        request.setRequestHeader("Cache-Control", "no-cache");
        request.setRequestHeader("Upgrade-Insecure-Requests", "1");
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        request.setRequestHeader("Referer", "https://decko.ceskatelevize.cz");
        request.setRequestHeader("Accept-Language", "en-GB,en;q=0.9,cs-CZ;q=0.8,cs;q=0.7,en-US;q=0.6,ru;q=0.5,be;q=0.4");
        request.setRequestHeader("Sec-Ch-Ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        request.setRequestHeader("Sec-Ch-Ua-Mobile", "?0");
        request.setRequestHeader("Sec-Ch-Ua-Platform", "\"Windows\"");
        request.setRequestHeader("Sec-Fetch-Dest", "document");
        request.setRequestHeader("Sec-Fetch-Mode", "navigate");
        request.setRequestHeader("Sec-Fetch-Site", "same-origin");
        request.setRequestHeader("Sec-Fetch-User", "?1");
        if (setCookie) request.setRequestHeader("Cookie", assembleCookies(cookies));
    }

    private void processSetCookieHeaders(XMLHttpRequest<?> request) {
        String[] setCookies = request.getRealResponseHeader("Set-Cookie");
        //System.out.println(Arrays.toString(setCookies));
        for (String setCookie : setCookies) {
            int equals = setCookie.indexOf("=");
            int end = setCookie.indexOf("; ");
            if (end == -1) end = setCookie.length();
            String name = setCookie.substring(0, equals);
            String value = setCookie.substring(equals + 1, end);
            cookies.put(name, value);
            //System.out.println("Set cookie " + name + " to " + value);
        }
        //System.out.println();
        saveCredentials();
    }

    private void tryLogin() {
        XMLHttpRequest<DeckoHack> request = new XMLHttpRequest<>();
        request.open("GET", "https://decko.ceskatelevize.cz/dp/prihlasit", false);
        request.onreadystatechange = req -> {
            if (req.readyState == XMLHttpRequest.DONE) {
                //System.out.println(request.responseText);
                processSetCookieHeaders(req);
                String csfrInput = "<input type=\"hidden\" name=\"_csrf\" value=\"";
                if (!request.responseText.contains(csfrInput)) throw new NullPointerException("Oh no. Can't login.");
                int begin = req.responseText.indexOf(csfrInput) + csfrInput.length();
                String csrf = req.responseText.substring(begin, begin + 36);
                tryLoginContinue(csrf);
            }
        };
        setRequestHeaders(request, true);
        request.send();
    }

    private void tryLoginContinue(String csrf) {
        XMLHttpRequest<DeckoHack> request = new XMLHttpRequest<>();
        request.open("POST", "https://decko.ceskatelevize.cz/dp/prihlasit", false);
        //request.open("POST", "http://localhost:8888", false);
        request.onreadystatechange = req -> {
            if (req.readyState == XMLHttpRequest.DONE) {
                processSetCookieHeaders(req);
                if (req.status != 302) throw new NullPointerException("Maybe invalid credentials?");
                // System.out.println(req.status);
                // System.out.println(req.responseText);
                if (req.getResponseHeader("Location").endsWith("/prihlaseno")) onLoggedIn();
                else System.out.println("Login redirect to: " + req.getResponseHeader("Location"));
            }
        };
        request.allowRedirects = false;
        setRequestHeaders(request, true);

        String formData = String.format("_csrf=%s&username=%s&password=%s&passwordConfirmation=placeholder", csrf, username, password);
        request.send(formData.getBytes());
    }

    private void onLoggedIn() {
        DpCont.addReadyListener(this::onAppReady);
        //deckoHack.show();
        // For: https://decko.ceskatelevize.cz/flashAppIframe/ff1af0a6-7386-49be-9b85-6d06a1c72788?closeButtonEnabled=false
        IntegrativeContainer.sendAppRequest("ff1af0a6-7386-49be-9b85-6d06a1c72788", "/rest/Token/flashVars", null, "closeButtonEnabled=false", null);
        //AmfConnector.killToken(res -> System.out.println("Killed token: " + res.status));
    }

    private void initComponents() {
        this.setTitle("Decko Hacky");
        this.setName("MainFrame");
        this.setIconImage(DeckoHack.getImage("favicon.png"));
        this.setForeground(Color.white);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                DeckoHack.this.exitForm(windowEvent);
            }
        });
        JPanel games = new JPanel();
        games.setLayout(new BoxLayout(games, BoxLayout.Y_AXIS));
        for (int i = 0; i < gameHacks.size(); i++) {
            JButton button = new JButton((String) gameHacks.keySet().toArray()[i]);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    // TODO onClick --> show
                }
            });
            games.add(button);
        }
        this.add(games);

        this.pack();
    }

    private void exitForm(final WindowEvent windowEvent) {
        System.exit(0);
    }

    public static Image getImage(String path) {
        return new ImageIcon(Objects.requireNonNull(DeckoHack.class.getResource("/res/" + path))).getImage();
    }

    private void saveCredentials() {
        try {
            if (!credentials.exists()) {
                if (!credentials.createNewFile()) throw new Exception("Failed to create credentials file");
            }
            cfg.put("Username", username);
            cfg.put("Password", password);
            cfg.put("Cookies", assembleCookies(cookies));
            final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(credentials));
            cfg.store(out, "Default credentials for Decko Hack");
            out.close();
        } catch (Exception ex) {
            System.err.println("Failed to save credentials");
            ex.printStackTrace();
        }
    }

    private void loadCredentials() {
        try {
            final BufferedInputStream in = new BufferedInputStream(new FileInputStream(credentials));
            cfg.load(in);
            in.close();
            if (!credentials.exists()) this.saveCredentials();
            username = cfg.getProperty("Username");
            password = cfg.getProperty("Password");
            cookies = parseCookies(cfg.getProperty("Cookies"));
            if (cookies.size() == 0) acquireBaseCookies();
        } catch (Exception ex) {
            System.err.println("Failed to load credentials");
            ex.printStackTrace();
        }
    }

    private HashMap<String, String> parseCookies(String src) {
        HashMap<String, String> cookies = new HashMap<>();
        if (src == null) return cookies;
        String[] parts = src.split("; ");
        for (String part : parts) {
            int equals = part.indexOf("=");
            cookies.put(part.substring(0, equals), part.substring(equals + 1));
        }
        return cookies;
    }

    private String assembleCookies(HashMap<String, String> cookies) {
        StringBuilder r = new StringBuilder();
        for (String name : cookies.keySet()) r.append(name).append("=").append(cookies.get(name)).append("; ");
        if (r.length() >= 2 && r.substring(r.length() - 2, r.length()).equals("; "))
            r.delete(r.length() - 2, r.length());
        return r.toString();
    }

    static {
        kutejDarkySpunte = new KutejDarkySpunte();
        gameHacks = new HashMap<>() {{
            put("Kutej Darky, Spunte!", kutejDarkySpunte);
        }};
    }
}
