package jeu;

import affichage.AffichageAction;
import affichage.AffichageJeu;

import java.util.Optional;

public class PlateauDeJeu {

    private Optional<Card>[] m_boardPlayer;
    private Optional<Card>[] m_boardOrdi;
    private Optional<Card>[] m_futurBoardOrdi;

    public PlateauDeJeu() {

        this.m_boardPlayer = new Optional[4];
        this.m_boardOrdi = new Optional[4];
        this.m_futurBoardOrdi = new Optional[4];

        for (int i = 0; i < 4; i++) {
            this.m_boardPlayer[i]    = Optional.empty();
            this.m_boardOrdi[i]      = Optional.empty();
            this.m_futurBoardOrdi[i] = Optional.empty();
        }
    }

    public PlateauDeJeu(Card[] obstaclesJoueur) {
        this(); // initialise tout à vide
        if (obstaclesJoueur != null) {
            for (int i = 0; i < 4 && i < obstaclesJoueur.length; i++) {
                if (obstaclesJoueur[i] != null) {
                    m_boardPlayer[i] = Optional.of(obstaclesJoueur[i]);
                }
            }
        }
    }



    public Optional<Card>[] getBoardPlayer() {
        return m_boardPlayer;
    }

    public Optional<Card>[] getBoardOrdi() {
        return m_boardOrdi;
    }

    public Optional<Card>[] getFuturBoardOrdi() {
        return m_futurBoardOrdi;
    }

    public Optional<Card> getCardIndex(int index) {

        if (index >= 0 && index < 4) {
            return m_boardPlayer[index];
        }

        return Optional.empty();
    }

    public void poserCarteJoueur(Card c, int index) {
        if (index < 0 || index >= 4) {
            AffichageJeu.afficher(AffichageJeu.indexInvalide());
            return;
        }
        if (m_boardPlayer[index].isPresent()) {
            AffichageJeu.afficher(AffichageJeu.emplacementDejeuOccupe());
            return;
        }
        m_boardPlayer[index] = Optional.of(c);
    }

    public void retirerCarteJoueur(int index) {
        if (index >= 0 && index < 4) {
            m_boardPlayer[index] = Optional.empty();
        } else {
            AffichageJeu.afficher(AffichageJeu.indexInvalide());
        }
    }

    public void poserCarteOrdi(Card carte, int index) {

        if (index >= 0 && index < 4 && this.m_boardOrdi[index].isEmpty()) {
            m_boardOrdi[index] = Optional.of(carte);
        }
    }
    public void poserFuturCarteOrdi(Card carte, int index) {

        if (index >= 0 && index < 4 && this.m_futurBoardOrdi[index].isEmpty()) {
            this.m_futurBoardOrdi[index] = Optional.of(carte);
        }
    }

    public void retirerCarteOrdi(int index) {

        if (index >= 0 && index < 4) {
            this.m_boardOrdi[index] = Optional.empty();
        }
    }
    public void retirerFuturCarteOrdi(int index) {

        if (index >= 0 && index < 4) {
            this.m_futurBoardOrdi[index] = Optional.empty();
        }
    }


}