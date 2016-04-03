package limeri.cards.view.swing;

/**
 * This is the generic view for a game.  It will create a JFrame that a specific game view can
 * customize to its needs.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import limeri.cards.view.GameView;

public abstract class GameSwingView extends GameView {

    private final static int DEFAULT_X_LOCATION = 400;
    private final static int DEFAULT_Y_LOCATION = 250;
    private final static int DEFAULT_HEIGHT     = 600;
    private final static int DEFAULT_WIDTH      = 800;

    private JFrame gameFrame;
    private int gameHeight;
    private int gameWidth;
    private int gameLocationX;
    private int gameLocationY;
    private JMenuBar menubar;
    private String title;

    protected JFrame getGameFrame() {return gameFrame;}
    protected void setGameFrame(JFrame gameFrame) {this.gameFrame = gameFrame;}

    public int getGameHeight() {return gameHeight;}
    public void setGameHeight(int gameHeight) {this.gameHeight = gameHeight;}

    public int getGameWidth() {return gameWidth;}
    public void setGameWidth(int gameWidth) {this.gameWidth = gameWidth;}

    public int getGameLocationX() {return gameLocationX;}
    public void setGameLocationX(int gameLocationX) {this.gameLocationX = gameLocationX;}

    public int getGameLocationY() {return gameLocationY;}
    public void setGameLocationY(int gameLocationY) {this.gameLocationY = gameLocationY;}

    protected JMenuBar getMenubar() {return menubar;}
    protected void setMenubar(JMenuBar menubar) {this.menubar = menubar;}

    protected String getTitle() {return title;}
    protected void setTitle(String title) {this.title = title;}

    /**
     * drawInitialGameView will draw the initial game for the player to start.
     */
    public void drawInitialView() {
        String title = getTitle();
        if (title == null) {
            title = "limeri.games inc";
        }
        gameFrame = new JFrame(title);
        gameFrame.setName("PinochleGameFrame");
        gameFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        this.setGameHeight(GameSwingView.DEFAULT_HEIGHT);
        this.setGameWidth(GameSwingView.DEFAULT_WIDTH);
        this.setGameLocationX(GameSwingView.DEFAULT_X_LOCATION);
        this.setGameLocationY(GameSwingView.DEFAULT_Y_LOCATION);

        initializeMenu();
        initializeGame();
        showGame();
    }

    /**
     * initializeGame is the hook used by subclasses to set up the initial view of the game.
     */
    public abstract void initializeGame();

    /**
     * initializeMenu will create the initial base menu for any game.
     */
    public void initializeMenu() {
        this.menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("fileMenu");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem miExit = new JMenuItem("Exit Game");
        miExit.setName("miExit");
        miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        miExit.setToolTipText("Exit the game");
        miExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        fileMenu.add(miExit);
        menubar.add(fileMenu);

        getGameFrame().setJMenuBar(menubar);
    }

    /**
     * showGame will make the game visible to the user after all of the setup is complete.
     */
    public void showGame() {
        JFrame frame = getGameFrame();
        frame.pack();
        frame.setLocation(this.getGameLocationX(), this.getGameLocationY());
        frame.setSize(this.getGameWidth(), this.getGameHeight());
        frame.setVisible(true);
    }

    /**
     * findMenu is used by children to find menus with a specific name from the menu bar.
     * @param name the name of the menu to find
     * @return the corrosponding menu or null
     */
    protected JMenu findMenu(String name) {
        JMenuBar menuBar = getMenubar();
        int menuCnt = menuBar.getMenuCount();
        if (menuCnt == 0) {
            return null;
        }
        for (int i = 0; i < menuCnt; i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu.getName().equals(name)) {
                return menu;
            }
        }
        return null;
    }
}
