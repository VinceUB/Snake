package io.github.vkb24312.snake;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        //SnakeGame snakeGame = new SnakeGame();

        try {
            if (args[0].equals("AI")) {
                Snake[] snake = new Snake[10];

                for (int i = 0; i < snake.length; i++) {
                    snake[i] = new Snake();
                }

                for (Snake aSnake : snake) {
                    new SnakeGame().main(aSnake);
                }

                Arrays.sort(snake);

                System.out.println("\n\n\n\n");
            } else {
                new SnakeGame().main(null);
            }
        } catch (ArrayIndexOutOfBoundsException ignore){
            new SnakeGame().main(null);
        }
    }
}
