package deckoapi.game;

import deckoapi.AmfConnectorCallback;
import deckoapi.AmfConnectorResult;
import deckoapi.DpCont;
import deckoapi.IntegrativeContainer;
import deckoapi.amf.message.Func;
import deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788.KutejDarkySpunte;
import gui.NoSuchGameDataDialog;
import rufus.lzstring4java.LZString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public abstract class GameHacks extends JFrame {
    protected final IntegrativeContainer IC;
    protected final Vector<AmfConnectorResult.AppStatesResult.Slot> slots;
    protected String gameName;
    protected String gameURL;
    protected final NoSuchGameDataDialog noSuchGameDataDialog;
    protected boolean initialised = false;

    public GameHacks(String gameName, String gameURL) {
        this.gameName = gameName;
        this.gameURL = gameURL;
        noSuchGameDataDialog = new NoSuchGameDataDialog(this, false, gameName, gameURL);
        IC = new IntegrativeContainer();
        slots = new Vector<>();
        this.initComponents();
        GameHacks.initSize(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                GameHacks.this.close();
            }
        });
    }

    public void start() {
        if (!initialised) {
            IC.dpCont.addReadyListener(() -> this.setInitialised(true));
            IC.dpCont.addReadyListener(this::dpContReady);
            IC.dpCont.addReadyListener(this::checkSlots);
            initIC();
            return;
        }
        this.checkSlots();
    }

    private void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    public void checkSlots() {
        IC.amfConnector.getAppStates(result -> {
            if (errorResult(result)) {
                noSuchData();
                return;
            }
            System.out.println(result.status);
            AmfConnectorResult.AppStatesResult appStatesResult = (AmfConnectorResult.AppStatesResult) result;
            slots.clear();
            for (AmfConnectorResult.AppStatesResult.Slot slot : appStatesResult.slots) {
                System.out.println(slot);
                slots.add(slot);
            }
            if (slots.size() == 0) noSuchData();
            else {
                this.loadData();
                setVisible(true);
            }
        });
    }

    protected abstract void initIC();

    protected abstract void dpContReady();

    protected abstract void initComponents();

    protected abstract void loadData();

    protected abstract void saveData();

    public static void initSize(Window win) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        initSize(win, screenSize.width / 2, screenSize.height / 2);
    }

    public static void initSize(Window win, int w, int h) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        win.setBounds(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2, w, h);
        if (!(win instanceof JFrame frame)) return;
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public boolean errorResult(AmfConnectorResult result) {
        if (result.status.equals("ERROR")) {
            AmfConnectorResult.ErrorResult errorResult = (AmfConnectorResult.ErrorResult) result;
            System.out.println(errorResult.status + " - " + errorResult.error);
            return true;
        }
        return false;
    }

    public void noSuchData() {
        noSuchGameDataDialog.setVisible(true);
    }

    public void close() {
        noSuchGameDataDialog.dispatchEvent(new WindowEvent(noSuchGameDataDialog, WindowEvent.WINDOW_CLOSING));
        this.setVisible(false);
    }

    public static String fixUTF(String in) {
        return new String(in.getBytes(), StandardCharsets.UTF_8);
    }
}
