package model.utils;

import java.io.Serializable;

public class Resultat implements Comparable<Resultat>, Serializable {

    private int score;
    private String name;

    public Resultat(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }


    @Override
    public int compareTo(Resultat o)
    {
        return o.getScore() - score;
    }

    @Override
    public String toString()
    {
        return name + " : " + score;
    }
}
