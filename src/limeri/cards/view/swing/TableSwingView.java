package limeri.cards.view.swing;

/**
 * This is the generic swing view of the table.  It will seat the players around the table.
 * @author limeri
 */

import java.awt.*;

import javax.swing.*;

import limeri.cards.view.*;

public class TableSwingView extends TableView {
    private JFrame gameFrame;
//    private JPanel contentPane;

    public JFrame getGameFrame() {return gameFrame;}
    public void setGameFrame(JFrame gameFrame) {this.gameFrame = gameFrame;}


    public void drawInitialView() {
        JFrame gameFrame = getGameFrame();
        // If we don't have a JFrame, something's really broken.
        if (gameFrame == null) {
            throw new RuntimeException("No JFrame has been defined for this table.");
        }

        Container tableContainer = new Container();
        gameFrame.add(tableContainer);

        // Set up the base table looks.
//        this.contentPane.setBackground(new Color(0, 128, 0));
//        this.contentPane.setLayout(new BorderLayout(0, 0));

        // Add a panel to each direction of the border layout so that you can add objects to each area.
//        addControlPanel(BorderLayout.WEST);
//        addControlPanel(BorderLayout.EAST);
//        addControlPanel(BorderLayout.NORTH);
//        addControlPanel(BorderLayout.SOUTH);
//        addControlPanel(BorderLayout.CENTER);

//        gameFrame.setContentPane(contentPane);
    }

    /**
     * Gets the layout for a direction in the game (north, south, east, west, center).  This is a static
     * method because this never changes.
     * @param direction
     * @return the {@link LayoutManager} for the direction
     */
    public static LayoutManager getLayout(String direction) {
        LayoutManager layout = null;
        if (direction.equals(BorderLayout.NORTH) || direction.equals(BorderLayout.SOUTH)) {
            GridLayout myLayout = new GridLayout(1, 13, 0, 0);
            myLayout.setHgap(0);
            layout = myLayout;
        }
        else if (direction.equals(BorderLayout.WEST) || direction.equals(BorderLayout.EAST)) {
            GridLayout myLayout = new GridLayout(13, 1, 0, 0);
            myLayout.setVgap(0);
            layout = myLayout;
        }
        else if (direction.equals(BorderLayout.CENTER)) {
            layout = new BorderLayout();
        }
        return layout;
    }

    // Add a panel to the specified location of the border layout.
    private void addControlPanel(String direction) {
        JPanel panel = new JPanel();
        LayoutManager layout = getLayout(direction);
        getGameFrame().add(panel, direction);
        panel.setLayout(layout);
    }


    //TODO: DELETE

//    public void del_drawInitialView() {
//        JFrame gameFrame = getGameFrame();
//        // If we don't have a JFrame, something's really broken.
//        if (gameFrame == null) {
//            throw new RuntimeException("No JFrame has been defined for this table.");
//        }
//    
//        // Set up the base table looks.
//        this.contentPane = new JPanel();
//        this.contentPane.setBackground(new Color(0, 128, 0));
//        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        this.contentPane.setLayout(new BorderLayout(0, 0));
//    
//        // Add a panel to each direction of the border layout so that you can add objects to each area.
//        addControlPanel(BorderLayout.WEST);
//        addControlPanel(BorderLayout.EAST);
//        addControlPanel(BorderLayout.NORTH);
//        addControlPanel(BorderLayout.SOUTH);
//        addControlPanel(BorderLayout.CENTER);
//    
//        gameFrame.setContentPane(contentPane);
//    }

}