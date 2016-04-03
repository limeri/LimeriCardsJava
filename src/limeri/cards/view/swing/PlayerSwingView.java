package limeri.cards.view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.*;

import limeri.cards.model.PlayerModel;
import limeri.cards.view.PlayerView;

public class PlayerSwingView extends PlayerView {

    public JPanel getPlayerPanel(PlayerModel player) {
        JPanel playerPanel = new JPanel();
        LayoutManager layout = TableSwingView.getLayout(player.getTablePosition());
        playerPanel.setLayout(layout);
//        String direction = player.getTablePosition();
//        if (direction.equals(BorderLayout.NORTH) || direction.equals(BorderLayout.SOUTH)) {
//            playerPanel.setPreferredSize(new Dimension(50, 100));
//        }
//        else if (direction.equals(BorderLayout.EAST) || direction.equals(BorderLayout.WEST)) {
//            playerPanel.setPreferredSize(new Dimension(150, 50));
//        }
//        else {
//            playerPanel.setPreferredSize(new Dimension(60, 60));
//        }
        return playerPanel;
    }

    public void addPlayerName(JFrame gameFrame, PlayerModel player) {
        String playerName = player.getName();
        JLabel playerLabel = new JLabel(playerName);
        playerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerLabel.setVerticalAlignment(SwingConstants.TOP);
        gameFrame.add(playerLabel, player.getTablePosition());
    }

    //TODO: delete
    public JPanel del_getPlayerPanel(PlayerModel player) {
        String playerName  = player.getName();
        JPanel playerPanel = new JPanel();
        LayoutManager layout = TableSwingView.getLayout(player.getTablePosition());
        playerPanel.setLayout(layout);
        playerPanel.setName("PlayerPanel_" + playerName);
        JLabel playerLabel = new JLabel(playerName);
        String direction = player.getTablePosition();
        if (direction.equals(BorderLayout.NORTH) || direction.equals(BorderLayout.SOUTH)) {
            playerPanel.setPreferredSize(new Dimension(50, 100));
        }
        else if (direction.equals(BorderLayout.EAST) || direction.equals(BorderLayout.WEST)) {
            playerPanel.setPreferredSize(new Dimension(150, 50));
        }
        else {
            playerPanel.setPreferredSize(new Dimension(60, 60));
        }
        playerPanel.add(playerLabel);
        return playerPanel;
    }

    public void del_addPlayerName(JPanel playerPanel, PlayerModel player) {
        String playerName = player.getName();
        playerPanel.setName("PlayerPanel_" + playerName);
        JLabel playerLabel = new JLabel(playerName);
        playerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerLabel.setVerticalAlignment(SwingConstants.TOP);
//        int playerHeight = playerLabel.getHeight();
//        int playerWidth  = playerLabel.getWidth();
//        JOptionPane.showMessageDialog(null, "height: " + playerHeight + ", width: " + playerWidth);
        playerPanel.add(playerLabel);
    }
}
