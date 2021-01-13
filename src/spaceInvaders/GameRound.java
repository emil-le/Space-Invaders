package spaceInvaders;

import comp127graphics.CanvasWindow;
import comp127graphics.GraphicsText;
import comp127graphics.Image;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Emil Le & Izzy Valdivia. May 10th 2020
 * This class runs a game round of space invaders!
 */
public class GameRound {
    private final int CANVAS_WIDTH = 795;
    private Spaceship spaceship;
    private int spaceshipSpeed;
    private int alienName;
    private CanvasWindow canvas;
    private AlienManager alien;
    private int numLives;
    private final Font BIG_FONT = new Font("Courier", Font.BOLD, 50);
    private final Font MEDIUM_FONT = new Font("Courier", Font.BOLD, 30);
    private GraphicsText level = new GraphicsText("level",40,40);
    private int levelNumber;
    private List<Image> lives = new ArrayList();
    private boolean first = true;
    private List<String> LevelName = new ArrayList<>(){{
        add("Easy");
        add("Medium");
        add("Hard");
        add("Custom");
    }};

    /**
     *Creates a new Canvas window titled "Space Invaders!" with a black background and calls intro method.
     */
    public GameRound(CanvasWindow canvas, int numLives, int spaceshipSpeed, int alienSpeed, int levelNumber) {
        this.canvas = canvas;
        this.levelNumber = levelNumber;
        this.numLives = numLives;
        this.spaceshipSpeed = spaceshipSpeed;
        this.alienName = alienSpeed;
        runGame();
    }

    /**
     * Creates Alien Manager, creates spaceship, adds lives, and allows user to set the difficulty.
     */
    public void generateGraphics() {
        trackHeart();
        setLevel();
        alien = new AlienManager(canvas);
        alien.build();
        spaceship = new Spaceship(CANVAS_WIDTH / 2.0, 775.0,canvas, alien);
        spaceship.build();
    }

    /**
     * Displays red text while user is playing the game telling them what difficulty level they are playing.
     */
    public void setLevel() {
        level.setText("Level: " + LevelName.get(levelNumber -1));
        level.setFont(MEDIUM_FONT);
        level.setFillColor(Color.red);
        canvas.add(level);
    }

    /**
     * Calls generateRound() and animates the canvas. Updates all of the bullet positions and allows the
     * aliens manager to move!
     */
    public void runGame() {
        canvas.removeAll();
        first = true;
        generateGraphics();
        alien.setBegin();
        canvas.animate(() -> {
            if (numLives > 0 && alien.getNumAliens() > 0 && alien.getLowest() <= 740 - spaceship.getHeight()) {
                alien.moveAlien();
                alien.shoot();
                alien.getBulletList().moveBullet(alienName);
                if (alien.getBulletList().killSpaceship()) {
                    resetGame();
                }
                spaceship.getBulletList().moveBullet(spaceshipSpeed);
            } else if (first){
                first = false;
                endGame();
            }
        });
    }

    /**
     * Displays text telling the user how many lives they have left. If there are 0 lives it displays "Oh no!"
     * Resets alien manager.
     */
    public void resetGame() {
        loseHeart();
        String text = (numLives > 1)? "You have " + (numLives - 1) + " chance(s) left!" : "Oh no!";
        makeAnnouncement(text,BIG_FONT,"#5dbf5c");
        numLives--;
        alien.reset();
    }

    /**
     * If the user wins, the game prints that they win or else they print that they lose. Removes everything from the
     * canvas and closing the canvas.
     */
    public void endGame() {
            String text = (alien.getNumAliens() == 0) ? "Win " + "\u005E\u005F\u005E" : "You Lost! " + "T\u005FT";
            makeAnnouncement(text, BIG_FONT, "#5dbf5c");
            canvas.removeAll();
            canvas.closeWindow();
    }

    /**
     * Prepares text to be put on the canvas. Sets the text, font and color of the graphics text.
     * @param text
     * @param font
     * @param color
     */
    public void makeAnnouncement(String text, Font font, String color) {
        GraphicsText announcement = new GraphicsText(text, 0, 0);
        announcement.setFont(font);
        announcement.setCenter(CANVAS_WIDTH/2, ((alien.getLowest() > 400) ?  100: 600));
        announcement.setFillColor(Color.decode(color));
        canvas.add(announcement);
        canvas.draw();
        canvas.pause(5000);
        canvas.remove(announcement);
    }

    /**
     * Adds the number of lives the user gets to the upper right corner of the screen, and adds the same number of lives
     * to the lives list.
     */
    public void trackHeart() {
        for (int i = 1;i <= numLives;i++) {
            Image image = new Image(CANVAS_WIDTH-35*i-10,5, "heart.png");
            lives.add(image);
            canvas.add(image);
        }
    }

    /**
     * Removes one heart png from the upper right, and removes a heart from the lives list.
     */
    public void loseHeart() {
        canvas.remove(lives.get(lives.size()-1));
        lives.remove(lives.get(lives.size()-1));
    }
}
