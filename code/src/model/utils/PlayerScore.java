package model.utils;

/**
 * Permet de stocker le pseudo et le score du joueur dans un objet pour pouvoir le sauvegarder
 */
public class PlayerScore {
    private final String pseudo;
    private final int score;

    /**
     * Créé une instance de PlayerScore
     *
     * @param pseudo Pseudonyme du joueur
     * @param score  Score du joueur
     */
    public PlayerScore(String pseudo, int score) {
        this.score = score;
        this.pseudo = pseudo;
    }

    /**
     * Récupère le pseudonyme du joueur
     *
     * @return Pseudonyme du joueur
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Récupère le score du joueur
     *
     * @return Score du joueur
     */
    public int getScore() {
        return score;
    }
}
