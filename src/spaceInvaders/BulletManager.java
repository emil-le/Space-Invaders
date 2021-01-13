package spaceInvaders;

import comp127graphics.CanvasWindow;
import comp127graphics.GraphicsObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BulletManager {
    private List<Bullet> bulletList = new ArrayList();
    private  int maxBullet;
    private Random ran = new Random();
    private CanvasWindow canvas;
    private Color color;
    private GraphicsObject lowRgt, upLft, lowLft, upRgt;
    private boolean hitSpaceship;
    private AlienManager alien;

    public BulletManager(CanvasWindow canvas, int maxBullet, Color color, AlienManager alien) {
        this.canvas = canvas;
        this.maxBullet = maxBullet;
        this.color = color;
        this.alien = alien;
    }

    public BulletManager(CanvasWindow canvas, int maxBullet, Color color) {
        this.canvas = canvas;
        this.maxBullet = maxBullet;
        this.color = color;
    }
    private boolean percentChance(double chance) {
        return ran.nextDouble() * 100 < chance;
    }

    /**
     * Randomly generates a bullet at position (XPos, YPos) at a certain percent chance random.
     * @param XPos
     * @param YPos
     * @param random
     */
    public void generateBullet(double XPos,double YPos, double random) {
        if (bulletList.size() <= maxBullet -1 && percentChance(random)) {
            Bullet bullet = new Bullet(XPos,YPos,color);
            bullet.setStrokeWidth(5);
            bulletList.add(bullet);
            canvas.add(bullet);
        }
    }

    /**
     * Checks to see if a bullet hit the spaceship.
     * @param bullet
     */
    public void bulletInteraction(Bullet bullet) {
        upLft = canvas.getElementAt(bullet.getX(), bullet.getY());
        upRgt = canvas.getElementAt(bullet.getX()+2, bullet.getY());
        lowLft = canvas.getElementAt(bullet.getX(), bullet.getY()+5);
        lowRgt = canvas.getElementAt(bullet.getX()+2, bullet.getY()+5);
        hitSpaceship = (lowLft instanceof Spaceship) ||
                       (upRgt instanceof Spaceship)  ||
                       (upLft instanceof Spaceship)  ||
                       (lowRgt instanceof Spaceship);
    }

    /**
     * if the parameter bullet hits an alien, it removes the alien from the canvas, removes the alien from the alien list
     * and removes the bullet from the canvas.
     * @param b
     */
    public void killAlien(Bullet b) {
        if (lowLft instanceof Alien){
            canvas.remove(lowLft);
            alien.getAlienList().remove(lowLft);
            b.setPosition(-10,-10);
        } else if (upRgt instanceof Alien) {
            canvas.remove(upRgt);
            alien.getAlienList().remove(upRgt);
            b.setPosition(-10,-10);
        } else if (upLft instanceof Alien) {
            canvas.remove(upLft);
            alien.getAlienList().remove(upLft);
            b.setPosition(-10,-10);
        } else if (lowRgt instanceof Alien) {
            canvas.remove(lowRgt);
            alien.getAlienList().remove(lowRgt);
            b.setPosition(-10,-10);
        }
    }

    /**
     * This method moves bullets at a certain rate -> range. It also checks to see if all bullets are on the screen. If
     * they are not on the screen, they are removed from the canvas and bulletlist
     * @param range
     */
    public void moveBullet(int range) {
        int i = 0;
        while (i <= bulletList.size()-1) {
            Bullet b = bulletList.get(i);
            b.updatePosition(range);
            bulletInteraction(b);
            if (b.getY() >= 800 || b.getY() <= 0) {
                canvas.remove(b);
                bulletList.remove(b);
                i--;
            } else if (range < 0){
                killAlien(b);
            } else if (range > 0 && hitSpaceship){
                break;
            }
            i++;
        }
    }

    /**
     * Returns the boolean hitSpaceship.
     * @return
     */
    public boolean killSpaceship() {
        return hitSpaceship;
    }

    /**
     * Removes all bullets from the screen and clears the bullet list.
     */
    public void resetBullet() {
        Iterator bullet = bulletList.iterator();
        while(bullet.hasNext()) {
            Bullet b = (Bullet) bullet.next();
            canvas.remove(b);
        }
        bulletList.clear();
    }
}
