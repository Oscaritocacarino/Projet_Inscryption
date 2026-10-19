package jeu;

import java.util.ArrayList;
import java.util.Optional;

public class MainJoueur {

    private ArrayList<Card> m_main;

    public MainJoueur(Card carte1, Card carte2, Card carte3, Card carte4) {

        this.m_main = new ArrayList<>();

        this.m_main.add(carte1);
        this.m_main.add(carte2);
        this.m_main.add(carte3);
        this.m_main.add(carte4);
    }

    public ArrayList<Card> getMain() {
        return this.m_main;
    }

    public Optional<Card> getCarte(int index) {
        if (index >= 0 && index < m_main.size()) {
            return Optional.of(m_main.get(index));
        }
        return Optional.empty();
    }

    public void viderMain(){
        this.m_main.clear();
    }
    public boolean enleverCarte(int index) {

        if (index >= 0 && index < m_main.size()) {
            m_main.remove(index);
            return true;
        }

        return false;
    }

    public boolean ajouterCarte(Card carte) {

        m_main.add(carte);
        return true;
    }

    public int taille() {
        return m_main.size();
    }
}
