package gui;

import deckoapi.IntegrativeContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class DeckoHack extends JFrame {
    private Properties cfg;
    private String username = "username", password = "password";
    private File appdataDir = null, credentials = null;

    public static void main(final String[] array) {
        IntegrativeContainer.sendAppRequest("ff1af0a6-7386-49be-9b85-6d06a1c72788", "/rest/Token/flashVars", null, "closeButtonEnabled=false", null);

        final DeckoHack deckoHack = new DeckoHack();
        deckoHack.show();
    }

    public DeckoHack() {
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
            if (!credentials.exists()) {
                this.saveCredentials();
            } else {
                final BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(credentials));
                this.cfg.load(inStream);
                inStream.close();
            }
        } catch (Exception ex2) {
            System.err.println("Error reading credentials file - " + credentials.getAbsolutePath());
            System.exit(0);
        }
        this.setSize(800, 600);
    }

    private void initComponents() {

        this.setTitle("Decko Hacky");
        this.setName("MainFrame");
        this.setIconImage(this.getImage("favicon.png"));
        this.setForeground(Color.white);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                DeckoHack.this.exitForm(windowEvent);
            }
        });

        this.pack();
    }

    private void exitForm(final WindowEvent windowEvent) {
        System.exit(0);
    }

    public Image getImage(String path) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/res/" + path))).getImage();
    }

    private void saveCredentials() {
        try {
            if (!credentials.exists()) {
                if (!credentials.createNewFile()) throw new Exception("Failed to create credentials file");
            }
            cfg.put("Username", username);
            cfg.put("Password", password);
            final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(credentials));
            cfg.store(out, "Default credentials for Decko Hack");
            out.close();
        } catch (Exception ex) {
            System.err.println("Failed to save credentials");
            ex.printStackTrace();
        }
    }
}
