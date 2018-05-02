package io.github.vkb24312.snake;

import java.util.ArrayList;
import java.util.Random;

public class Snake implements Comparable<Snake>{
    long timeAlive;
    long score;

    int[] lengthToWall = new int[4];
    int[] lengthToApple = new int[2];
    ArrayList<int[]> snake = new ArrayList<>();

    int wall[] = new int[]{50, 350, 350, 50};

    int[][] lengthToWallMod = new int[4][4];
    int[][] lengthToAppleMod = new int[4][2];
    int[][] snakePlacementMod = new int[4][2];

    int wallMod;
    int appleMod;
    int snakeMod;

    int chooseDirection(SnakeGame f){
        int dir = f.direction;
        snake = f.snake;

        lengthToWall[0] = f.snake.get(0)[1]-wall[0];
        lengthToWall[1] = f.snake.get(0)[0]-wall[1];
        lengthToWall[2] = f.snake.get(0)[1]-wall[2];
        lengthToWall[3] = f.snake.get(0)[0]-wall[3];

        lengthToApple[0] = f.snake.get(0)[0]-f.apple[0];
        lengthToApple[1] = f.snake.get(0)[1]-f.apple[1];

        long[] directions = new long[4];

        long tempSnakeDir[] = new long[]{0, 0, 0, 0};
        for (int j = snake.size()-1; j > 0; j++) {
            for (int k = 0; k < 4; k++) {
                tempSnakeDir[k] = tempSnakeDir[k] + snake.get(j)[0]* snakePlacementMod[dir][0];
                tempSnakeDir[k] = tempSnakeDir[k] + snake.get(j)[1]* snakePlacementMod[dir][1];
            }
        }
        for (int j = 0; j < 4; j++) {
            directions[j] = directions[j] + (tempSnakeDir[j]/(snake.size()*4*2))*snakeMod;
        }

        long tempWallLength[] = new long[]{0, 0, 0, 0};
        for (int j = 0; j < lengthToWall.length; j++) {
            for (int k = 0; k < tempWallLength.length; k++) {
                tempWallLength[k] = tempWallLength[k] + lengthToWall[j]*lengthToWallMod[dir][j];
            }
        }
        for (int j = 0; j < 4; j++) {
            directions[j] = directions[j] + (tempWallLength[j]/(lengthToWall.length*tempWallLength.length))*wallMod;
        }

        long tempAppleLength[] = new long[]{0, 0, 0, 0};
        for (int j = 0; j < lengthToApple.length; j++) {
            for (int k = 0; k < tempAppleLength.length; k++) {
                tempAppleLength[k] = tempAppleLength[k] + lengthToApple[j]*lengthToAppleMod[dir][j];
            }
        }
        for (int j = 0; j < 4; j++) {
            directions[j] = directions[j] + (tempAppleLength[j]/(lengthToApple.length*tempAppleLength.length))*appleMod;
        }

        return getMax(directions);
    }

    private static int getMax(long[] inputArray){
        long maxValue = inputArray[0];
        int foo = 0;
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
                foo = i;
            }
        }

        return foo;
    }

    public Snake(int[][] lengthToWallMod, int[][] lengthToAppleMod, int[][] snakePlacementMod, int wallMod, int appleMod, int snakeMod){
        this.lengthToWallMod = lengthToWallMod;
        this.lengthToAppleMod = lengthToAppleMod;
        this.snakePlacementMod = snakePlacementMod;

        this.wallMod = wallMod;
        this.appleMod = appleMod;
        this.snakeMod = snakeMod;
    }

    public Snake(){
        for (int i = 0; i < lengthToWallMod.length; i++) {
            for (int j = 0; j < lengthToWallMod[i].length; j++) {
                lengthToWallMod[i][j] = new Random().nextInt();
            }
        }

        for (int i = 0; i < lengthToAppleMod.length; i++) {
            for (int j = 0; j < lengthToAppleMod[i].length; j++) {
                lengthToAppleMod[i][j] = new Random().nextInt();
            }
        }

        for (int i = 0; i < snakePlacementMod.length; i++) {
            for (int j = 0; j < snakePlacementMod[i].length; j++) {
                snakePlacementMod[i][j] = new Random().nextInt();
            }
        }

        wallMod = new Random().nextInt();
        appleMod = new Random().nextInt();
        snakeMod = new Random().nextInt();
    }

    @Override
    public int compareTo(Snake o) {
        return Long.compare(this.timeAlive, o.timeAlive);
    }
}
