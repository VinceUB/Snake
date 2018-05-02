package io.github.vkb24312.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SnakeGame extends Frame {
    ArrayList<int[]> snake = new ArrayList<>();
    int[] apple = new int[2];
    boolean isDead = false;
    int direction = 1;
    long timeAlive = 0;

    void main(Snake mover) throws InterruptedException {
        //if(mover==null) {
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
                    switch (e.getKeyCode()) {
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
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        //}

        snake.add(new int[]{50, 50});

        apple[0] = 10 * (new Random().nextInt(30) + 5);

        apple[1] = 10 * (new Random().nextInt(30) + 5);

        long startTime = System.nanoTime();

        while (!isDead) {
            if(mover==null) Thread.sleep(100);
            else Thread.sleep(1);
            if (mover==null) repaint();

            //<editor-fold desc="AI stuff">
            if(mover!=null) {
                int move = mover.chooseDirection(this);

                switch (move) {
                    case 0:
                        up();
                        break;
                    case 1:
                        right();
                        break;
                    case 2:
                        down();
                        break;
                    case 3:
                        left();
                }
            }

            //</editor-fold>

            move();

            testEat();

            isDead = testDead();

            if (isDead) System.out.println("dead");
        }

        System.out.println("Final Score: " + snake.size());

        timeAlive = System.nanoTime()-startTime;

        if(mover!=null){
            mover.timeAlive = timeAlive;
            mover.score = snake.size();
            dispose();
        } else {
            Thread.sleep(3000);
            dispose();
            Main.main(new String[]{"nullius"});
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

        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        g2d.drawString("Score: " + Integer.toString(snake.size()), 360, 250);
    }

    //<editor-fold desc="Up, down, left & right">
    void up() {
        if (direction != 2) {
            System.out.println("up");
            direction = 0;
        } else System.out.println("not up");
    }

    void down() {
        if (direction != 0) {
            System.out.println("down");
            direction = 2;
        } else System.out.println("not down");
    }

    void left() {
        if (direction != 1) {
            System.out.println("left");
            direction = 3;
        } else System.out.println("not left");
    }

    void right() {
        if (direction != 3) {
            System.out.println("right");
            direction = 1;
        } else System.out.println("not right");
    }
    //</editor-fold>

    void move() {
        for (int i = snake.size(); i > 1; i--) {
            snake.set(i - 1, snake.get(i - 2));
        }

        switch (direction) {
            case 0:
                snake.set(0, new int[]{snake.get(0)[0], snake.get(0)[1] - 10});
                break;
            case 1:
                snake.set(0, new int[]{snake.get(0)[0] + 10, snake.get(0)[1]});
                break;
            case 2:
                snake.set(0, new int[]{snake.get(0)[0], snake.get(0)[1] + 10});
                break;
            case 3:
                snake.set(0, new int[]{snake.get(0)[0] - 10, snake.get(0)[1]});
        }
    }

    boolean testDead() {
        if (isDead) {
            return true;
        } else if (snake.get(0)[0] < 50) {
            return true;
        } else if (snake.get(0)[0] > 349) {
            return true;
        } else if (snake.get(0)[1] < 50) {
            return true;
        } else if (snake.get(0)[1] > 349) {
            return true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (Arrays.equals(snake.get(0), snake.get(i))) {
                return true;
            }
        }

        return false;
    }

    boolean testEat() {
        if ((snake.get(0)[0] == apple[0]) && (snake.get(0)[1] == apple[1])) {
            grow();
            apple[0] = (new Random().nextInt(30) + 5) * 10;

            apple[1] = (new Random().nextInt(30) + 5) * 10;

            return true;
        } else return false;
    }

    void grow() {
        switch (direction) {
            case (0):
                snake.add(new int[]{snake.get(snake.size() - 1)[0], snake.get(snake.size() - 1)[1] + 10});
                break;
            case (2):
                snake.add(new int[]{snake.get(snake.size() - 1)[0], snake.get(snake.size() - 1)[1] - 10});
                break;
            case (1):
                snake.add(new int[]{snake.get(snake.size() - 1)[0] + 10, snake.get(snake.size() - 1)[1]});
                break;
            case (3):
                snake.add(new int[]{snake.get(snake.size() - 1)[0] - 10, snake.get(snake.size() - 1)[1]});
        }

        move();
    }

    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
}