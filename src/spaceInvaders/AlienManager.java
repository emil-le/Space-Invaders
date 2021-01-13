package spaceInvaders;

import comp127graphics.CanvasWindow;
import java.awt.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AlienManager {
    private List<Alien> alienList = new ArrayList();
    private int x = 20;
    private int y = 50;
    private int numBullet = 5;
    private BulletManager bulletList;
    private CanvasWindow canvas;
    private int index;
    private Clock clock = Clock.systemDefaultZone();
    private long begin, end;
    private  final int MIN_TIME = 510*2;
    private  final int MAX_TIME = 490*2;

    public AlienManager(CanvasWindow canvas){
        this.canvas = canvas;
    }

    /**
     * Builds a 5 * 13 block of aliens with a 5 pixel space above and between each alien.
     */
    public void build() {
        bulletList = new BulletManager(canvas,numBullet, Color.decode("#B2495B"));
        for (int i =0; i <= 64; i++) {
            Alien alien1 = new Alien(x,y);
            canvas.add(alien1);
            alienList.add(alien1);
            if (x >= 680) {
                x = 20;
                y += alien1.getHeight() + 5;
            }
            else
                x += alien1.getWidth() + 5;
        }
        index = 0;
    }

    /**
     * Getter method for alienList. Return the alienList.
     * @return
     */
    public int getNumAliens() {
        return alienList.size();
    }

    /**
     * Resets bullet manager, creates new bullet manager, and sets the beginning time.
     */
    public void reset() {
        bulletList.resetBullet();
        bulletList = new BulletManager(canvas,numBullet, Color.decode("#B2495B"));
        setBegin();
    }

    /**
     * Randomly selects one of the aliens without an alien in front of it, and shoots a bullet at that starting
     * position.
     */
    public void shoot(){
        List<Alien> lowestAliens = new ArrayList<>();
        Iterator alien = alienList.iterator();
        while(alien.hasNext()) {
            Alien a = (Alien) alien.next();
            if (!(canvas.getElementAt(a.getX(), a.getY()+a.getHeight()+5) instanceof Alien)) {
                lowestAliens.add(a);
            }
        }

        int maxLowestAliens = lowestAliens.size();
        Random rand = new Random();
        int r = rand.nextInt(maxLowestAliens);
        Alien randomAlien = lowestAliens.get(r);
        bulletList.generateBullet(randomAlien.getX() + (randomAlien.getImageWidth()/2) ,randomAlien.getY() + randomAlien.getImageHeight(),1);
    }

    /**
     * Moves the whole block of aliens to the right, down, and to the left. This happens at a consistent rate.
     */
    public void moveAlien() {
        setEnd();
        if (end - begin <= MIN_TIME && end - begin >= MAX_TIME) {
            begin = end;

            Iterator c = alienList.iterator();
            if (index % 4 == 0) {
                while (c.hasNext()) {
                    Alien a = (Alien) c.next();
                    a.setPosition(a.getX() + 15, a.getY());
                }
            } else if (index % 4 == 1) {
                while (c.hasNext()) {
                    Alien a = (Alien) c.next();
                    a.setPosition(a.getX(), a.getY() + 15);
                }
            } else if (index % 4 == 2) {
                while (c.hasNext()) {
                    Alien a = (Alien) c.next();
                    a.setPosition(a.getX() - 15, a.getY());
                }
            } else if (index % 4 == 3) {
                while (c.hasNext()) {
                    Alien a = (Alien) c.next();
                    a.setPosition(a.getX(), a.getY() + 15);
                }
            }
            index = (index == 3) ? 0 : index + 1;
        }
    }

    /**
     * Resetting the beginning time of a new alien movement.
     */
    public void setBegin() {
        begin = clock.millis();
    }

    /**
     * Resetting the ending time of a new alien movement.
     */
    public void setEnd() {
        end = clock.millis();
    }

    /**
     * returns the y position of the alien that is the lowest.
     * @return
     */
    public double getLowest() {
        Iterator c = alienList.iterator();
        double max = 0;
        while (c.hasNext()) {
            Alien a = (Alien) c.next();
            max = (a.getY() > max)? a.getY(): max;
        }
        return max;
    }

    /**
     * returns the Alien List (the list of all surviving aliens right now).
     * @return
     */
    public List<Alien> getAlienList() { return alienList; }

    /**
     * returns the current list of bullets of the aliens.
     * @return
     */
    public BulletManager getBulletList() { return bulletList; }
}