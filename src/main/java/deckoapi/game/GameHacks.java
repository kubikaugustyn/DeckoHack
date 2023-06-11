package deckoapi.game;

import deckoapi.AmfConnectorResult;
import deckoapi.amf.message.Func;
import deckoapi.game.ff1af0a6_7386_49be_9b85_6d06a1c72788.KutejDarkySpunte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class GameHacks extends JFrame {
    public GameHacks() {
        this.initComponents();
        GameHacks.initSize(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                windowEvent.getWindow().setVisible(false);
            }
        });
    }

    protected abstract void initComponents();

    protected abstract void loadData();

    protected abstract void saveData();

    public static void initSize(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(screenSize.width / 4, screenSize.height / 4, screenSize.width / 2, screenSize.height / 2);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public static boolean errorResult (AmfConnectorResult result) {
        if (result.status.equals("ERROR")) {
            AmfConnectorResult.ErrorResult errorResult = (AmfConnectorResult.ErrorResult) result;
            System.out.println(errorResult.status + " - " + errorResult.error);
            return true;
        }
        return false;
    }
}
