package gui;

import deckoapi.game.GameHacks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NoSuchGameDataDialog extends JDialog {
    public NoSuchGameDataDialog(final Frame owner, final boolean modal, String game, String gameURL) {
        super(owner, modal);
        initComponents(game, gameURL);
        pack();
    }

    private void initComponents(String game, String gameURL) {
        this.setBackground(Color.white);
        this.setResizable(false);
        this.setTitle(GameHacks.fixUTF("Nemáme Data"));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                NoSuchGameDataDialog.this.closeDialog(windowEvent);
            }
        });
        JTextArea text = new JTextArea();
        JLabel url = new JLabel();
        text.setEditable(false);
        text.setText(GameHacks.fixUTF("Na Décku nejsou ulozena žádná nebo jen nějaká data hry " + game + ". Otevři hru ve webovém prohlížeci, spusť jí a ulož její data. Pak se vrať."));
        text.setBorder(null);
        text.setRequestFocusEnabled(false);
        /*final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 0;
        constraints.gridheight = 0;
        constraints.fill = 1;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(10, 45, 10, 10);*/
        text.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.setPreferredSize(new Dimension(250, 100));
        text.setLineWrap(true);
        add(text);
        url.setText(GameHacks.fixUTF("<html>Hra: <a href=\"\">" + game + "</a></html>"));
        url.setCursor(new Cursor(Cursor.HAND_CURSOR));
        url.setAlignmentX(Component.LEFT_ALIGNMENT);
        url.setPreferredSize(new Dimension(250, 50));
        add(url);
        GameHacks.initSize(this, 250, 150);

        url.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeckoHack.openWebsite(gameURL);
            }
        });
    }

    public void closeDialog(final WindowEvent windowEvent) {
        this.setVisible(false);
        this.dispose();
    }
}
