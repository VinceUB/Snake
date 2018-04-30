package io.github.vkb24312.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Frame {

    int Dimensions = 2;
    ArrayList<int[]> snake = new ArrayList<>();
    int[] apple = new int[Dimensions];
    boolean isDead = false;
    int direction = 1;

    public static void main(String[] args) throws InterruptedException {
        new Main().main();
    }

    void main() throws InterruptedException{
        repaint();

        this.setVisible(true);
        this.setSize(500, 500);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case 37:
                        left();
                        break;
                    case 38:
                        up();
                        break;
                    case 39:
                        right();
                        break;
                    case 40:
                        down();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        snake.add(new int[]{50, 50});

        apple[0] = 10*(new Random().nextInt(30)+5);

        apple[1] = 10*(new Random().nextInt(30)+5);

        while(!isDead){
            Thread.sleep(100);
            repaint();

            move();

            testEat();

            isDead = testDead();

            if(isDead) System.out.println("dead");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.RED);
        g2d.fill(new Rectangle(0, 0, 500, 500));

        g2d.setPaint(Color.WHITE);

        g2d.fill(new Rectangle(50, 50, 300, 300));


        g2d.setPaint(Color.BLUE);

        for (int i = 0; i < snake.toArray().length; i++) {
            g2d.fill(new Rectangle(snake.get(i)[0], snake.get(i)[1], 10, 10));
        }

        g2d.setPaint(Color.BLACK);

        g2d.fill(new Rectangle(apple[0], apple[1], 10, 10));
    }

    //<editor-fold desc="Up, down, left & right">
    void up(){
        System.out.println("up");
        direction = 0;
        if(snake.size()>3) {
            move();
            testEat();
        }
    }

    void down(){
        System.out.println("down");
        direction = 2;
        if(snake.size()>3) {
            move();
            testEat();
        }
    }

    void left(){
        System.out.println("left");
        direction = 3;
        if(snake.size()>3) {
            move();
            testEat();
        }
    }

    void right(){
        System.out.println("right");
        direction = 1;
        if(snake.size()>3) {
            move();
            testEat();
        }
    }

    //TODO: Don't allow the extra movement if near edge
    //</editor-fold>

    void move(){
        for (int i = snake.size(); i > 1; i--) {
            snake.set(i-1, snake.get(i-2));
        }

        switch(direction){
            case 0:
                snake.set(0, new int[]{snake.get(0)[0], snake.get(0)[1]-10});
                break;
            case 1:
                snake.set(0, new int[]{snake.get(0)[0]+10, snake.get(0)[1]});
                break;
            case 2:
                snake.set(0, new int[]{snake.get(0)[0], snake.get(0)[1]+10});
                break;
            case 3:
                snake.set(0, new int[]{snake.get(0)[0]-10, snake.get(0)[1]});
        }
    }

    boolean testDead(){
        if(isDead){
            return true;
        } else if(snake.get(0)[0]<50){
            return true;
        } else if(snake.get(0)[0]>349){
            return true;
        } else if(snake.get(0)[1]<50){
            return true;
        } else if(snake.get(0)[1]>349){
            return true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if(Arrays.equals(snake.get(0), snake.get(i))){
                return true;
            }
        }

        return false;
    }

    boolean testEat(){
        if((snake.get(0)[0] == apple[0]) && (snake.get(0)[1] == apple[1])){
            grow();
            apple[0] = (new Random().nextInt(30)+5)*10;

            apple[1] = (new Random().nextInt(30)+5)*10;

            return true;
        } else return false;
    }

    void grow(){
        switch(direction) {
            case (0):
                snake.add(new int[]{snake.get(snake.size() - 1)[0], snake.get(snake.size() - 1)[1]+10});
                break;
            case (2):
                snake.add(new int[]{snake.get(snake.size() - 1)[0], snake.get(snake.size() - 1)[1]-10});
                break;
            case (1):
                snake.add(new int[]{snake.get(snake.size() - 1)[0]+10, snake.get(snake.size() - 1)[1]});
                break;
            case (3):
                snake.add(new int[]{snake.get(snake.size() - 1)[0]-10, snake.get(snake.size() - 1)[1]});
        }

        move();
    }
}
