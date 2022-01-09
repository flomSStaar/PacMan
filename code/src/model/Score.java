package model;

public class Score {
    private int score;

    public void increase() {
        score++;
    }

    public void reset() {
        score = 0;
    }

    public int getScore() {
        return score;
    }
}
