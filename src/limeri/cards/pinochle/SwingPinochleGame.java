package limeri.cards.pinochle;

/**
 * Swing-based Pinochle game application.
 * Uses PropertyReader for deck configuration and log4j for logging.
 * Provides a complete GUI interface for the card game.
 * 
 * @author limeri.ai
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import limeri.cards.controller.SimpleGameController;
import limeri.cards.controller.HandDisplayController;
import limeri.cards.Constants;
import limeri.cards.deck.PinochleDeck;
import limeri.cards.model.PlayerModel;
import limeri.cards.view.SwingHandDisplay;

public class SwingPinochleGame extends SimpleGameController {
    
    private static final Logger logger = LogManager.getLogger(SwingPinochleGame.class);
    
    private int numberOfPlayers = Constants.PINOCHLE_INITIAL_PLAYERS;
    private int numberOfCardsInHand;
    private PinochleDeck deck;
    private ArrayList<PlayerModel> players;
    private SwingHandDisplay swingDisplay;
    
    // GUI Components
    private JFrame controlFrame;
    private JButton dealButton;
    private JButton showOpenHandsButton;
    private JButton showClosedHandsButton;
    private JButton newGameButton;
    private JLabel statusLabel;
    
    public SwingPinochleGame() {
        initializeControlWindow();
        initializeGame();
    }
    
    private void initializeControlWindow() {
        controlFrame = new JFrame("Pinochle Game Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setLayout(new BorderLayout());
        
        // Create control panel
        JPanel controlPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Status label
        statusLabel = new JLabel("Game initialized - Ready to deal cards", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Control buttons
        dealButton = new JButton("Deal Cards");
        dealButton.addActionListener(new DealCardsListener());
        
        showOpenHandsButton = new JButton("Show Open Hands");
        showOpenHandsButton.addActionListener(new ShowOpenHandsListener());
        showOpenHandsButton.setEnabled(false);
        
        showClosedHandsButton = new JButton("Show Closed Hands");
        showClosedHandsButton.addActionListener(new ShowClosedHandsListener());
        showClosedHandsButton.setEnabled(false);
        
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new NewGameListener());
        
        controlPanel.add(dealButton);
        controlPanel.add(showOpenHandsButton);
        controlPanel.add(showClosedHandsButton);
        controlPanel.add(newGameButton);
        
        // Add components to frame
        controlFrame.add(statusLabel, BorderLayout.NORTH);
        controlFrame.add(controlPanel, BorderLayout.CENTER);
        
        // Create info panel
        JPanel infoPanel = createInfoPanel();
        controlFrame.add(infoPanel, BorderLayout.SOUTH);
        
        controlFrame.setSize(300, 250);
        controlFrame.setLocationRelativeTo(null);
        controlFrame.setVisible(true);
        
        logger.info("Initialized control window");
    }
    
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Game Information"));
        
        infoPanel.add(new JLabel("Players:"));
        infoPanel.add(new JLabel(String.valueOf(numberOfPlayers)));
        
        infoPanel.add(new JLabel("Deck Type:"));
        infoPanel.add(new JLabel("Pinochle (48 cards)"));
        
        infoPanel.add(new JLabel("Cards per Hand:"));
        JLabel cardsPerHandLabel = new JLabel("12");
        infoPanel.add(cardsPerHandLabel);
        
        return infoPanel;
    }
    
    /**
     * Set up the deck and the players using PropertyReader-based DeckController.
     */
    public void initializeGame() {
        logger.info("Initializing Pinochle game with PropertyReader");
        
        try {
            // Create deck using PropertyReader-based PinochleDeck
            deck = new PinochleDeck();
            
            setNumberOfCardsInHand(deck.getNumberOfCardsInDeck() / getNumberOfPlayers());
            setPlayers(initializePlayers(getNumberOfPlayers(), "Player"));
            
            logger.info("Game initialized successfully - {} players, {} cards per hand", 
                       getNumberOfPlayers(), getNumberOfCardsInHand());
            
            updateStatus("Game ready - Click 'Deal Cards' to start");
            
        } catch (Exception e) {
            logger.error("Error initializing game: {}", e.getMessage(), e);
            updateStatus("Error initializing game: " + e.getMessage());
            JOptionPane.showMessageDialog(controlFrame, 
                "Error initializing game: " + e.getMessage(), 
                "Initialization Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Deal all cards to the players.
     */
    public void dealCards() {
        if (deck != null && players != null && !players.isEmpty()) {
            logger.info("Dealing {} cards to each of {} players", getNumberOfCardsInHand(), getNumberOfPlayers());
            
            try {
                deck.deal(players, getNumberOfCardsInHand());
                logger.info("Successfully dealt all {} cards", deck.getNumberOfCardsInDeck());
                updateStatus("Cards dealt - " + deck.getNumberOfCardsInDeck() + " cards distributed");
                
                // Enable hand display buttons
                showOpenHandsButton.setEnabled(true);
                showClosedHandsButton.setEnabled(true);
                dealButton.setEnabled(false);
                
                // Automatically show closed hands after dealing
                displayHands(Constants.CLOSED_HAND);
                
            } catch (Exception e) {
                logger.error("Error dealing cards: {}", e.getMessage(), e);
                updateStatus("Error dealing cards: " + e.getMessage());
                JOptionPane.showMessageDialog(controlFrame,
                    "Error dealing cards: " + e.getMessage(),
                    "Dealing Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            logger.error("Cannot deal cards - deck or players not properly initialized");
            updateStatus("Error: Deck or players not initialized");
        }
    }
    
    /**
     * Display all player hands according to the specified mode.
     */
    public void displayHands(int gameMode) {
        if (players == null || players.isEmpty()) {
            logger.error("No players found to display hands");
            return;
        }
        
        logger.info("Displaying hands in {} mode", gameMode == Constants.OPEN_HAND ? "OPEN" : "CLOSED");
        
        try {
            // Create new Swing display if needed
            if (swingDisplay == null) {
                String title = "Pinochle Game - " + (gameMode == Constants.OPEN_HAND ? "Open Hands" : "Closed Hands");
                swingDisplay = new SwingHandDisplay(title);
            } else {
                swingDisplay.clearDisplay();
                swingDisplay.setTitle("Pinochle Game - " + (gameMode == Constants.OPEN_HAND ? "Open Hands" : "Closed Hands"));
            }
            
            // Create hand display controller
            HandDisplayController displayController = new HandDisplayController();
            displayController.setGameHandAccess(gameMode);
            
            // Display all hands using the Swing interface
            displayController.displayAllHands(players, swingDisplay);
            
            updateStatus("Displaying " + (gameMode == Constants.OPEN_HAND ? "open" : "closed") + " hands");
            
        } catch (Exception e) {
            logger.error("Error displaying hands: {}", e.getMessage(), e);
            updateStatus("Error displaying hands: " + e.getMessage());
            JOptionPane.showMessageDialog(controlFrame,
                "Error displaying hands: " + e.getMessage(),
                "Display Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
        logger.debug("Status updated: {}", message);
    }
    
    // Action Listeners
    private class DealCardsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dealCards();
        }
    }
    
    private class ShowOpenHandsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayHands(Constants.OPEN_HAND);
        }
    }
    
    private class ShowClosedHandsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayHands(Constants.CLOSED_HAND);
        }
    }
    
    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logger.info("Starting new game");
            
            // Close existing display
            if (swingDisplay != null) {
                swingDisplay.getMainFrame().dispose();
                swingDisplay = null;
            }
            
            // Reset game state
            initializeGame();
            dealButton.setEnabled(true);
            showOpenHandsButton.setEnabled(false);
            showClosedHandsButton.setEnabled(false);
            
            updateStatus("New game started - Ready to deal cards");
        }
    }
    
    // Getters and Setters
    public int getNumberOfPlayers() { return numberOfPlayers; }
    public void setNumberOfPlayers(int numberOfPlayers) { this.numberOfPlayers = numberOfPlayers; }
    
    public int getNumberOfCardsInHand() { return numberOfCardsInHand; }
    public void setNumberOfCardsInHand(int numberOfCardsInHand) { this.numberOfCardsInHand = numberOfCardsInHand; }
    
    public PinochleDeck getDeck() { return deck; }
    public void setDeck(PinochleDeck deck) { this.deck = deck; }
    
    public ArrayList<PlayerModel> getPlayers() { return players; }
    public void setPlayers(ArrayList<PlayerModel> players) { this.players = players; }
    
    /**
     * Main entry point for the Swing Pinochle game.
     */
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Could not set system look and feel: {}", e.getMessage());
        }
        
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logger.info("=== STARTING SWING PINOCHLE GAME ===");
                new SwingPinochleGame();
            }
        });
    }
}
