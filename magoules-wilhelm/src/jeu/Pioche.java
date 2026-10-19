package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Pioche {

    private ArrayList<Card> m_pioche;
    // Stocke les récompenses ajoutées entre les parties
    private ArrayList<CartesDeJeu> m_recompenses = new ArrayList<>();

    public Pioche() {
        this.m_pioche = new ArrayList<>();
        remplir();
    }

    //Remplit la pioche avec 15 cartes de nouveau + les récompenses.
    private void remplir() {
        CartesDeJeuFactory f = new CartesDeJeuFactory();
        this.m_pioche.clear();

        for (int i = 0; i < 8; i++) {
            this.m_pioche.add(new Card(f.createEcureuil()));
        }
        this.m_pioche.add(new Card(f.createChat()));
        this.m_pioche.add(new Card(f.createPorcepic()));
        this.m_pioche.add(new Card(f.createPunaise()));
        this.m_pioche.add(new Card(f.createMoineau()));
        this.m_pioche.add(new Card(f.createVipere()));
        this.m_pioche.add(new Card(f.createElan()));
        this.m_pioche.add(new Card(f.createGrizzly()));

        // Réajouter les récompenses avec des PV frais
        for (CartesDeJeu recompense : m_recompenses) {
            this.m_pioche.add(new Card(recompense));
        }
    }

    /**
     * Réinitialise la pioche pour une nouvelle partie.
     * Recrée toutes les cartes avec leurs PV d'origine.
     * Les récompenses sont conservées et recréées aussi.
     */
    public void reset() {
        remplir();
        Collections.shuffle(this.m_pioche);
    }

    public ArrayList<Card> getPioche() { return this.m_pioche; }

    public void setPioche(ArrayList<Card> pioche) {
        this.m_pioche = new ArrayList<>(pioche);
    }

    public Optional<Card> piocher() {
        if (estVide()) return Optional.empty();
        return Optional.of(this.m_pioche.remove(0));
    }

    public boolean estVide() { return this.m_pioche.isEmpty(); }

    public int taille() { return this.m_pioche.size(); }


    public void ajouterCarte(Card carte) {
        this.m_pioche.add(carte);
        this.m_recompenses.add(carte.getCarte()); // mémoriser pour reset()
    }
}