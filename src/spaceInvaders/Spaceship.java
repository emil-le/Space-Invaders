package spaceInvaders;

import comp127graphics.CanvasWindow;
import comp127graphics.Image;
import java.awt.*;

public class Spaceship extends Image {
    private BulletManager bulletList;
    private CanvasWindow canvas;
    private AlienManager alien;

    public Spaceship(Double XPos, Double YPos, CanvasWindow canvas, AlienManager alien){
        super(XPos,YPos, "spaceship.png");
        this.setCenter(XPos,YPos);
        this.canvas = canvas;
        this.alien = alien;
    }

    /**
     * Adds a Spaceship object to the screen, creates a new BulletManager.
     */
    public void build(){
        this.canvas.add(this);
        bulletList = new BulletManager(this.canvas,5, Color.decode("#34ebc6"),alien);
        this.canvas.onMouseMove(event -> this.setCenter(event.getPosition().getX(), 775));
        this.canvas.onClick(event -> this.bulletList.generateBullet(event.getPosition().getX(),750,100));
    }

    /**
     * returns the current list of bullets of the spaceship.
     * @return
     */
    public BulletManager getBulletList() { return bulletList; }
}
