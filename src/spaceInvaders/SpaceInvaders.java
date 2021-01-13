package spaceInvaders;

import comp127graphics.*;
import comp127graphics.Image;
import comp127graphics.Rectangle;
import comp127graphics.ui.Button;
import comp127graphics.ui.TextField;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static java.awt.Color.BLACK;

/**
 * Authors: Emil Le & Izzy Valdivia. May 10th 2020
 * This class creates the introduction for the game space invaders.
 * This allows the user to choose their preferred difficulty for a game round, or to customize their game round's difficulty.
 */
public class SpaceInvaders {
    private static final int CANVAS_WIDTH = 795;
    private static final int CANVAS_HEIGHT = 800;
    private int spaceshipSpeed;
    private int alienSpeed;
    private CanvasWindow canvas;
    private int numLives;
    private final Font BIG_FONT = new Font("Courier", Font.BOLD, 50);
    private final Font MEDIUM_FONT = new Font("Courier", Font.BOLD, 30);
    private final Font SMALL_FONT = new Font("Courier", Font.BOLD, 20);
    private final Font MICRO_FONT = new Font("Courier", Font.BOLD, 15);
    private int levelName;
    boolean slideshow = true;
    private List<TextField> textBox = new ArrayList<>();
    private GraphicsGroup frame = new GraphicsGroup();
    private List<Button> button = new ArrayList<>() {{
        add(new Button("Easy"));
        add(new Button("Medium"));
        add(new Button("Hard"));
        add(new Button("Custom"));
        add(new Button("Play"));
    }};

    /**
     *Creates a new Canvas window titled "Space Invaders!" with a black background and calls intro method.
     */
    public  SpaceInvaders() {
        canvas = new CanvasWindow("Space Invaders!", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(BLACK);
        createIntroduction();
    }

    /**
     * Creates introduction with Space invaders Png coming down from the top of the canvas, sets names, and allows the
     * user to choose an 'Easy' 'Medium' 'Hard' or 'Custom' difficulty game!
     */
    public void createIntroduction() {
        List<Rectangle> rectangles = new ArrayList<>(){{
            add(new Rectangle(80,72,10,250));
            add(new Rectangle(CANVAS_WIDTH-80,72,10,250));
            add(new Rectangle(80,63,CANVAS_WIDTH-150,10));
            add(new Rectangle(80,321,CANVAS_WIDTH-150,10));
        }};

        for (int i = 0; i <= 3; i++) {
            rectangles.get(i).setStrokeWidth(3);
            rectangles.get(i).setFillColor(Color.YELLOW);
            rectangles.get(i).setStrokeColor(Color.BLUE);
            frame.add(rectangles.get(i));
        }

        Image emil = new Image(0,0,"emil.png");
        emil.setPosition(80, 325);
        frame.add(emil);

        Image izzy = new Image(0,0,"izzy.png");
        izzy.setPosition(CANVAS_WIDTH-200, 330);
        frame.add(izzy);

        GraphicsText text = new GraphicsText("Please choose the difficulty of the game!");
        text.setFont(MEDIUM_FONT);
        text.setFillColor(Color.decode("#b1bc3a"));
        text.setCenter(CANVAS_WIDTH/2,500);
        frame.add(text);

        GraphicsText hint = new GraphicsText("(Please do not choose custom if this is your first time!)");
        hint.setFont(MICRO_FONT);
        hint.setFillColor(Color.decode("#b1bc3a"));
        hint.setCenter(CANVAS_WIDTH/2,525);
        frame.add(hint);

        Image gameLogo = new Image(0,0,"space.png");
        gameLogo.setCenter(CANVAS_WIDTH / 2+100, 0);
        gameLogo.setMaxHeight(250);
        gameLogo.setMaxWidth(500);

        canvas.animate(()-> {
            if (gameLogo.getY() <= 67 && slideshow) {
                gameLogo.moveBy(0, 5);
                canvas.add(gameLogo);
            }
            if (gameLogo.getY() == 72 && slideshow) {
                canvas.add(frame);
                addButton(button.get(0),CANVAS_WIDTH/2-150,"Easy");
                addButton(button.get(1),CANVAS_WIDTH/2-50,"Medium");
                addButton(button.get(2),CANVAS_WIDTH/2+50,"Hard");
                addButton(button.get(3),CANVAS_WIDTH/2+150,"Custom");
                slideshow = false;
            }
        });
    }

    /**
     * Makes the user press 'play' button before the game begins. Displays instructions/ how-to-play
     * for the games. Also displays "are you ready????" to indicate that the game is about to begin.
     * @param numLives
     * @param spaceshipSpeed
     * @param alienSpeed
     * @param levelName
     */
    public void makePreset(int numLives, int spaceshipSpeed, int alienSpeed, int levelName) {
        this.levelName = levelName;
        this.numLives = numLives;
        this.spaceshipSpeed = -spaceshipSpeed;
        this.alienSpeed = alienSpeed;
        canvas.removeAll();

        GraphicsText text = new GraphicsText("Are you ready????");
        text.setFont(BIG_FONT);
        text.setFillColor(Color.YELLOW);
        text.setCenter(CANVAS_WIDTH/2,520);
        canvas.add(text);

        List<GraphicsText> instruction = new ArrayList<>() {{
            add(new GraphicsText("HOW TO PLAY:"));
            add(new GraphicsText(""));
            add(new GraphicsText("Move the spaceship by moving your cursors."));
            add(new GraphicsText("Click to shoot the Alien."));
            add(new GraphicsText("Bullets will kill the aliens upon contact."));
            add(new GraphicsText("You only have 5 bullets at a time."));
            add(new GraphicsText("Bullets will recharge when it kills Aliens or get out of bounds."));
            add(new GraphicsText("You win if all aliens are destroyed."));
            add(new GraphicsText(""));
            add(new GraphicsText("Beware aliens will also shoot you."));
            add(new GraphicsText("You will lose a life if Aliens' bullets touch you."));
            add(new GraphicsText("You will COMPLETELY lose the battle if you ran out of lives."));
            add(new GraphicsText("The hearts on the top right corner --"));
            add(new GraphicsText("-- indicates how many lives you have."));
            add(new GraphicsText(""));
            add(new GraphicsText("Aliens will also be ascending down."));
            add(new GraphicsText("You will also COMPLETELY lose the battle --"));
            add(new GraphicsText("-- if ANY of those Aliens come above you on the y-axis."));
            add(new GraphicsText("(They don't have even to touch you for you to lose.)"));
        }};

        for (int i = 0; i <= instruction.size() -1; i ++) {
            instruction.get(i).setFont(SMALL_FONT);
            instruction.get(i).setFillColor(Color.YELLOW);
            instruction.get(i).setCenter(CANVAS_WIDTH/2,60 + 23*i);
            canvas.add(instruction.get(i));
        }

        addButton(button.get(4),CANVAS_WIDTH/2,"Play");
    }

    /**
     * Building the TextField for player to input Integer Values to customize the game.
     * Adding GraphicsText to guild the player how to input values.
     */
    private void buildBox() {
        for (int i = 0; i <= 2; i++) {
            textBox.add(new TextField());
            textBox.get(i).setCenter(CANVAS_WIDTH/2,150 + (i+1)*100);
            canvas.add(textBox.get(i));
        }

        List<GraphicsText> text = new ArrayList<>(){{
            add(new GraphicsText("Please only enter integer values from 1 to 20!"));
            add(new GraphicsText("Otherwise a value will be chosen for you!"));
            add(new GraphicsText("Please enter how many lives you want:"));
            add(new GraphicsText("Please enter how fast spaceship bullet speed you want:"));
            add(new GraphicsText("Please enter how fast alien bullet speed you want:"));
        }};

        for (int i = 0; i <= 4; i++) {
            text.get(i).setFont(SMALL_FONT);
            text.get(i).setFillColor(Color.yellow);
            text.get(i).setCenter(CANVAS_WIDTH/2, (i > 1)? i*100 : 20*i + 50);
            canvas.add(text.get(i));
        }
    }


    /**
     * Takes in and interprets inputs if user selects a custom game. Asks the user how many lives they want to have,
     * how fast they want the spaceship bullets, and how fast they want the alien bullets to go. Sets up the game.
     * Reading the input from the user and assign to the variable numLives, spaceshipSpeed, alienSpeed.
     * If the user does not follow guild line, a chosen value will be assigned.
     */
    private void buildCustom() {
        canvas.removeAll();
        levelName = 4;
        buildBox();

        textBox.get(0).onChange((text)->readIntField(textBox.get(0), (newValue) -> {
            numLives = (newValue >=1 && newValue <=20) ? newValue : 5;}));

        textBox.get(1).onChange((text)->readIntField(textBox.get(1), (newValue) -> {
            spaceshipSpeed = (newValue >=1 && newValue <=20) ? -newValue : -5;}));

        textBox.get(2).onChange((text)->readIntField(textBox.get(2), (newValue) -> {
            alienSpeed = (newValue >=1 && newValue <=20) ? newValue : 1;}));

        addButton(button.get(4),CANVAS_WIDTH/2,"Play");
    }

    /**
     * Reading input from the user typing in the TextField.
     * If the input is not an integer, changing the Text Field color, and reading "21" as the predetermined value.
     * Sources: Painter Lab - COMP 127.
     * @param field
     * @param updateAction
     */
    private void readIntField(TextField field, Consumer<Integer> updateAction) {
        try {
            updateAction.accept(
                    Integer.parseInt(
                            field.getText()));
            field.setBackground(Color.WHITE);
        } catch (NumberFormatException e) {
            field.setBackground(new Color(0xFFCCCC));
            updateAction.accept(21);
        }
    }

    /**
     * Sets 5 different buttons "Easy" "Medium" "Hard" "Custom" and "Play". Easy, medium, and hard set the spaceship
     * bullet speed, alien bullet speed, and life numbers automatically. The button "Custom" calls the custom method
     * and the play button create a new GameRound object.
     * @param button
     * @param x
     * @param function
     */
    private void addButton(Button button, double x, String function) {
        button.setCenter(x,CANVAS_HEIGHT/2+50+100);
        canvas.add(button);

        button.onClick(() -> {
            if (function.equals("Easy")) makePreset(5,5,1,1);
            if (function.equals("Medium")) makePreset(3,5,2,2);
            if (function.equals("Hard")) makePreset(1,5,5,3);
            if (function.equals("Custom")) buildCustom();
            if (function.equals("Play")) new GameRound(canvas,numLives, spaceshipSpeed, alienSpeed, levelName);
        });
    }

    public static void main (String[]args){
        new SpaceInvaders();
    }
}
