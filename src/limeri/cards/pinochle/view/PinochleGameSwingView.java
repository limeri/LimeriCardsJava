package limeri.cards.pinochle.view;

/**
 * This class manages all of the rendering of swing objects.  PinochleSwingGame does the controller work of setting
 * up the models and this class draws them.
 */

import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

import limeri.cards.Constants;
//import limeri.cards.exceptions.NoPlayersFoundException;
import limeri.cards.model.*;
import limeri.cards.view.swing.*;
import limeri.cards.pinochle.*;
import limeri.cards.pinochle.game.*;

public class PinochleGameSwingView extends GameSwingView {
    private Pinochle game;

    public PinochleGameSwingView() {
        super();
        setTitle("LimeriPinochle");
    }

    public Pinochle getGame() {return game;}
    public void setGame(Pinochle game) {this.game = game;}

    @Override
    public void initializeGame() {
//        initializeTable();
        initializePlayers(getGameFrame());
    }

    @Override
    public void initializeMenu() {
        super.initializeMenu();
        initializeFileMenu();
    }

    /**
     * Show the hands on the table.
     */
    public void showHands() {
        JFrame gameFrame = getGameFrame();
        gameFrame.setVisible(false);
        for (Iterator<PlayerModel> iter = getGame().getPlayers().iterator(); iter.hasNext();) {
            PlayerModel player = (PlayerModel)iter.next();
            drawHand(player, Constants.OPEN_HAND);
        }
        gameFrame.setVisible(true);
    }

    /**
     * Initialize the file menu items.
     */
    private void initializeFileMenu() {
        JMenu fileMenu = findMenu("fileMenu");

        JMenuItem miNewGame = new JMenuItem("New Game");
        miNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        miNewGame.setName("miNewGame");
        miNewGame.setToolTipText("Start a new game.");
        miNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                PinochleSwingGame game = (PinochleSwingGame)getGame();
                game.playGame();
            }
        });

        fileMenu.insert(miNewGame, 0);
    }

    /**
     * Initialize the table.
     */
//    private void initializeTable() {
//        // Get the model.
//        TableModel tableModel = new TableModel();
//        tableModel.setPlayers(getGame().getPlayers());
//        TableSwingView tableView = new TableSwingView();
//        tableView.setModel(tableModel);
//        tableView.setGameFrame(getGameFrame());
//        tableView.drawInitialView();
//    }


    /**
     * Draw the players.
     */
    private void initializePlayers(JFrame gameFrame) {
        PinochleSwingGame game = (PinochleSwingGame)getGame();
        for (Iterator<PlayerModel> iter = game.getPlayers().iterator(); iter.hasNext();) {
            PlayerModel player = (PlayerModel)iter.next();
            PlayerSwingView playerView = new PlayerSwingView();
            playerView.addPlayerName(getGameFrame(), player);
        }
    }

    /**
     * Draw the hand of an individual player.
     * @param player a PlayerModel to draw
     * @param access SHOW_CARDS or HIDE_CARDS (to show or hide the cards)
     */
    private void drawHand(PlayerModel player, int access) {
        PlayerSwingView playerView = new PlayerSwingView();
        String name = player.getName();
        player.setName(name + " playing");
        JPanel playerPanel = playerView.getPlayerPanel(player);

        for (Iterator<CardModel> iter = player.getHandIterator(); iter.hasNext();) {
            CardModel card = (CardModel)iter.next();
            String imagePath = card.getImagePath();
            JLabel cardLabel = new JLabel("");
            cardLabel.setIcon(new ImageIcon(imagePath));
            cardLabel.setVerticalAlignment(SwingConstants.TOP);
            cardLabel.setHorizontalAlignment(SwingConstants.LEFT);
            playerPanel.add(cardLabel);
        }
        JFrame gameFrame = getGameFrame();
        gameFrame.add(playerPanel, player.getTablePosition());
    }

    //todo: delete

//    private void del_initializePlayers(JFrame gameFrame) {
//        PinochleSwingGame game = (PinochleSwingGame)getGame();
//        int numberOfPlayers;
//        try {
//            numberOfPlayers = getGame().getNumberOfPlayers();
//        }
//        catch (NoPlayersFoundException e) {
//            JOptionPane.showMessageDialog(null, "No players have come to the table.");
//            System.exit(0);
//        }
//
//        for (Iterator<PlayerModel> iter = game.getPlayers().iterator(); iter.hasNext();) {
//            PlayerModel player = (PlayerModel)iter.next();
//            PlayerSwingView playerView = new PlayerSwingView();
//            JPanel playerPanel = playerView.getPlayerPanel(player);
//            playerView.addPlayerName(playerPanel, player);
//            String playerPos   = player.getTablePosition();
//            gameFrame.getContentPane().add(playerPanel, playerPos);
//        }
//    }
}
