package limeri.cards.view;

/**
 * Swing-based implementation of hand display.
 * Displays player hands in a GUI using Swing components.
 * 
 * @author limeri.ai
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import limeri.cards.controller.HandDisplayInterface;
import limeri.cards.model.CardModel;
import limeri.cards.model.PlayerModel;

public class SwingHandDisplay implements HandDisplayInterface {
    
    private static final Logger logger = LogManager.getLogger(SwingHandDisplay.class);
    
    private JFrame mainFrame;
    private JPanel mainPanel;
    private int currentPlayerCount = 0;
    
    public SwingHandDisplay(String title) {
        initializeFrame(title);
    }
    
    private void initializeFrame(String title) {
        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        
        // Create main panel with BorderLayout for directional positioning
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add a center area to separate the hands nicely
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Add control panel
        JPanel controlPanel = createControlPanel();
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        
        mainFrame.setSize(1200, 800);
        mainFrame.setLocationRelativeTo(null);
        
        logger.info("Initialized Swing hand display window with directional layout: {}", title);
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JButton exitButton = new JButton("Exit Game");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("User requested exit");
                System.exit(0);
            }
        });
        
        JButton refreshButton = new JButton("Refresh Display");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("User requested display refresh");
                mainFrame.repaint();
            }
        });
        
        controlPanel.add(refreshButton);
        controlPanel.add(exitButton);
        
        return controlPanel;
    }
    
    @Override
    public void displayHand(PlayerModel player, boolean showCards, String cardBackImage) {
        logger.info("Displaying hand for player: {} ({})", player.getName(), player.getTablePosition());
        
        JPanel playerPanel = createPlayerPanel(player, showCards, cardBackImage);
        
        // Position player based on table position
        String tablePosition = player.getTablePosition();
        String borderPosition = getBorderLayoutPosition(tablePosition);
        
        if (borderPosition != null) {
            mainPanel.add(playerPanel, borderPosition);
            currentPlayerCount++;
            logger.debug("Added player {} to {} position", player.getName(), borderPosition);
        } else {
            logger.warn("Unknown table position: {}, placing in center", tablePosition);
            mainPanel.add(playerPanel, BorderLayout.CENTER);
        }
        
        // Show the frame after adding the first player
        if (currentPlayerCount == 1) {
            mainFrame.setVisible(true);
        }
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    private JPanel createPlayerPanel(PlayerModel player, boolean showCards, String cardBackImage) {
        JPanel playerPanel = new JPanel(new BorderLayout());
        
        // Adjust panel styling based on position
        String tablePosition = player.getTablePosition().toLowerCase();
        Color borderColor = getBorderColorForPosition(tablePosition);
        
        playerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            player.getTablePosition() + " (" + player.getName() + ")",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            borderColor
        ));
        
        // Create header with hand size
        JLabel headerLabel = new JLabel("Hand Size: " + player.getHand().size(), JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        playerPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Create cards panel
        JPanel cardsPanel = createCardsPanel(player, showCards, cardBackImage);
        playerPanel.add(cardsPanel, BorderLayout.CENTER);
        
        // Create summary panel if showing cards
        if (showCards) {
            JPanel summaryPanel = createSummaryPanel(player);
            playerPanel.add(summaryPanel, BorderLayout.SOUTH);
        } else {
            JLabel cardBackLabel = new JLabel("Card Back: " + extractCardBackName(cardBackImage), JLabel.CENTER);
            cardBackLabel.setFont(new Font("Arial", Font.ITALIC, 9));
            playerPanel.add(cardBackLabel, BorderLayout.SOUTH);
        }
        
        return playerPanel;
    }
    
    private JPanel createCardsPanel(PlayerModel player, boolean showCards, String cardBackImage) {
        JPanel cardsPanel = new JPanel();
        
        // Adjust layout based on table position
        String tablePosition = player.getTablePosition().toLowerCase();
        GridLayout layout;
        
        switch (tablePosition) {
            case "north":
            case "south":
                // Horizontal layout for north and south players
                layout = new GridLayout(2, 6, 2, 2); // 2 rows, 6 columns
                break;
            case "east":
            case "west":
                // Vertical layout for east and west players
                layout = new GridLayout(6, 2, 2, 2); // 6 rows, 2 columns
                break;
            default:
                layout = new GridLayout(0, 6, 2, 2);
                break;
        }
        
        cardsPanel.setLayout(layout);
        
        if (showCards) {
            // Display actual cards with images
            Iterator<CardModel> cardIterator = player.getHandIterator();
            while (cardIterator.hasNext()) {
                CardModel card = cardIterator.next();
                JLabel cardLabel = createCardImageLabel(card, tablePosition);
                cardsPanel.add(cardLabel);
            }
            
        } else {
            // Display card backs with images
            for (int i = 0; i < player.getHand().size(); i++) {
                JLabel cardBackLabel = createCardBackImageLabel(cardBackImage, tablePosition);
                cardsPanel.add(cardBackLabel);
            }
        }
        
        return cardsPanel;
    }
    
    private JLabel createCardLabel(String text, boolean isActualCard, String tablePosition) {
        JLabel cardLabel = new JLabel(text, JLabel.CENTER);
        cardLabel.setOpaque(true);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        // Adjust card size based on position
        Dimension cardSize;
        switch (tablePosition) {
            case "north":
            case "south":
                cardSize = new Dimension(45, 30); // Slightly smaller for horizontal layout
                break;
            case "east":
            case "west":
                cardSize = new Dimension(55, 40); // Larger for vertical layout
                break;
            default:
                cardSize = new Dimension(50, 35);
                break;
        }
        
        cardLabel.setPreferredSize(cardSize);
        cardLabel.setFont(new Font("Monospaced", Font.BOLD, 10));
        
        if (isActualCard) {
            cardLabel.setBackground(Color.WHITE);
            cardLabel.setForeground(getCardColor(text));
        } else {
            cardLabel.setBackground(Color.BLUE);
            cardLabel.setForeground(Color.WHITE);
        }
        
        return cardLabel;
    }
    
    private Color getCardColor(String cardText) {
        // Determine card color based on suit
        if (cardText.contains("H") || cardText.contains("D")) {
            return Color.RED; // Hearts and Diamonds are red
        } else {
            return Color.BLACK; // Spades and Clubs are black
        }
    }
    
    private JPanel createSummaryPanel(PlayerModel player) {
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Suit Summary"));
        
        // Count cards by suit
        int spades = 0, hearts = 0, clubs = 0, diamonds = 0;
        
        Iterator<CardModel> cardIterator = player.getHandIterator();
        while (cardIterator.hasNext()) {
            CardModel card = cardIterator.next();
            switch (card.getSuit()) {
                case "S": spades++; break;
                case "H": hearts++; break;
                case "C": clubs++; break;
                case "D": diamonds++; break;
            }
        }
        
        // Create suit count labels
        summaryPanel.add(createSuitLabel("♠: " + spades, Color.BLACK));
        summaryPanel.add(createSuitLabel("♥: " + hearts, Color.RED));
        summaryPanel.add(createSuitLabel("♣: " + clubs, Color.BLACK));
        summaryPanel.add(createSuitLabel("♦: " + diamonds, Color.RED));
        
        return summaryPanel;
    }
    
    private JLabel createSuitLabel(String text, Color color) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    /**
     * Create a label with the actual card image.
     */
    private JLabel createCardImageLabel(CardModel card, String tablePosition) {
        JLabel cardLabel = new JLabel();
        
        // Get card size based on position
        Dimension cardSize = getCardSize(tablePosition);
        cardLabel.setPreferredSize(cardSize);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        try {
            // Load the card image using the card's image path
            File imageFile = new File(card.getImagePath());
            if (imageFile.exists()) {
                BufferedImage cardImage = ImageIO.read(imageFile);
                
                // Scale the image to fit the card size
                Image scaledImage = cardImage.getScaledInstance(
                    cardSize.width - 2, cardSize.height - 2, Image.SCALE_SMOOTH);
                ImageIcon cardIcon = new ImageIcon(scaledImage);
                cardLabel.setIcon(cardIcon);
                
                logger.trace("Loaded card image: {}", card.getImagePath());
            } else {
                // Fallback to text if image not found
                cardLabel.setText(card.getName() + card.getSuit());
                cardLabel.setHorizontalAlignment(JLabel.CENTER);
                cardLabel.setBackground(Color.WHITE);
                cardLabel.setOpaque(true);
                cardLabel.setFont(new Font("Monospaced", Font.BOLD, 8));
                
                logger.warn("Card image not found: {}, using text fallback", card.getImagePath());
            }
        } catch (IOException e) {
            // Fallback to text if image loading fails
            cardLabel.setText(card.getName() + card.getSuit());
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setBackground(Color.WHITE);
            cardLabel.setOpaque(true);
            cardLabel.setFont(new Font("Monospaced", Font.BOLD, 8));
            
            logger.error("Error loading card image: {}", e.getMessage());
        }
        
        return cardLabel;
    }
    
    /**
     * Create a label with the card back image.
     */
    private JLabel createCardBackImageLabel(String cardBackImagePath, String tablePosition) {
        JLabel cardLabel = new JLabel();
        
        // Get card size based on position
        Dimension cardSize = getCardSize(tablePosition);
        cardLabel.setPreferredSize(cardSize);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        try {
            // Load the card back image
            File imageFile = new File(cardBackImagePath);
            if (imageFile.exists()) {
                BufferedImage cardBackImage = ImageIO.read(imageFile);
                
                // Scale the image to fit the card size
                Image scaledImage = cardBackImage.getScaledInstance(
                    cardSize.width - 2, cardSize.height - 2, Image.SCALE_SMOOTH);
                ImageIcon cardBackIcon = new ImageIcon(scaledImage);
                cardLabel.setIcon(cardBackIcon);
                
                logger.trace("Loaded card back image: {}", cardBackImagePath);
            } else {
                // Fallback to text if image not found
                cardLabel.setText("[###]");
                cardLabel.setHorizontalAlignment(JLabel.CENTER);
                cardLabel.setBackground(Color.BLUE);
                cardLabel.setForeground(Color.WHITE);
                cardLabel.setOpaque(true);
                cardLabel.setFont(new Font("Monospaced", Font.BOLD, 8));
                
                logger.warn("Card back image not found: {}, using text fallback", cardBackImagePath);
            }
        } catch (IOException e) {
            // Fallback to text if image loading fails
            cardLabel.setText("[###]");
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setBackground(Color.BLUE);
            cardLabel.setForeground(Color.WHITE);
            cardLabel.setOpaque(true);
            cardLabel.setFont(new Font("Monospaced", Font.BOLD, 8));
            
            logger.error("Error loading card back image: {}", e.getMessage());
        }
        
        return cardLabel;
    }
    
    /**
     * Get card size based on table position.
     */
    private Dimension getCardSize(String tablePosition) {
        switch (tablePosition.toLowerCase()) {
            case "north":
            case "south":
                return new Dimension(60, 85); // Standard card ratio for horizontal layout
            case "east":
            case "west":
                return new Dimension(55, 80); // Slightly smaller for vertical layout
            default:
                return new Dimension(60, 85);
        }
    }
    
    /**
     * Extract a readable name from the card back image path.
     */
    private String extractCardBackName(String imagePath) {
        if (imagePath.contains("b1fh")) return "Blue Full Horizontal";
        if (imagePath.contains("b1fv")) return "Blue Full Vertical";
        if (imagePath.contains("b1pr")) return "Blue Partial Right";
        if (imagePath.contains("b1pl")) return "Blue Partial Left";
        if (imagePath.contains("b1pb")) return "Blue Partial Bottom";
        if (imagePath.contains("b1pt")) return "Blue Partial Top";
        if (imagePath.contains("b2fh")) return "Red Full Horizontal";
        if (imagePath.contains("b2fv")) return "Red Full Vertical";
        if (imagePath.contains("b2pr")) return "Red Partial Right";
        if (imagePath.contains("b2pl")) return "Red Partial Left";
        if (imagePath.contains("b2pb")) return "Red Partial Bottom";
        if (imagePath.contains("b2pt")) return "Red Partial Top";
        return "Card Back";
    }
    
    /**
     * Create a center panel for the table area.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0, 100, 0)); // Green table color
        centerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            "Pinochle Table",
            TitledBorder.CENTER,
            TitledBorder.CENTER,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE
        ));
        
        JLabel tableLabel = new JLabel("Game in Progress", JLabel.CENTER);
        tableLabel.setForeground(Color.WHITE);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(tableLabel);
        
        return centerPanel;
    }
    
    /**
     * Map table position to BorderLayout position.
     */
    private String getBorderLayoutPosition(String tablePosition) {
        if (tablePosition == null) {
            return null;
        }
        
        switch (tablePosition.toLowerCase()) {
            case "north":
                return BorderLayout.NORTH;
            case "south":
                return BorderLayout.SOUTH;
            case "east":
                return BorderLayout.EAST;
            case "west":
                return BorderLayout.WEST;
            default:
                logger.warn("Unknown table position: {}", tablePosition);
                return null;
        }
    }
    
    /**
     * Get border color based on table position for visual distinction.
     */
    private Color getBorderColorForPosition(String tablePosition) {
        switch (tablePosition) {
            case "north":
                return Color.BLUE;
            case "south":
                return Color.RED;
            case "east":
                return Color.GREEN;
            case "west":
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }
    
    /**
     * Clear all players from the display.
     */
    public void clearDisplay() {
        logger.info("Clearing hand display");
        
        // Remove all player panels but keep the center panel
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            Object constraints = ((BorderLayout) mainPanel.getLayout()).getConstraints(comp);
            if (!BorderLayout.CENTER.equals(constraints)) {
                mainPanel.remove(comp);
            }
        }
        
        currentPlayerCount = 0;
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    /**
     * Update the window title.
     */
    public void setTitle(String title) {
        mainFrame.setTitle(title);
        logger.debug("Updated window title to: {}", title);
    }
    
    /**
     * Get the main frame for additional customization.
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}
