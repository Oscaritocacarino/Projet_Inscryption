package jeu;

import affichage.AffichageAction;
import affichage.AffichageJeu;
import affichage.AffichagePlateau;

public class InteractionJoueur {

    private TourLogique    m_tourLogique;
    private AffichageAction m_affichageAction;
    private boolean        m_pioche = false;
    private int            m_numPartie;
    private Joueur m_joueur;

    public InteractionJoueur(TourLogique tourLogique, AffichageAction af, int numPartie, Joueur joueur) {
        this.m_tourLogique      = tourLogique;
        this.m_affichageAction  = af;
        this.m_numPartie        = numPartie;
        this.m_joueur = joueur;
        this.m_tourLogique.setDemanderSacrifice(this.m_affichageAction::choixSacrifice);
        afficherEtat();
    }

    public void jouer(int choix, int nb_games) {
        switch (choix) {
            case 1:
                if (!this.m_pioche) {
                    this.m_tourLogique.piocherJoueur();
                    this.m_pioche = true;
                    afficherEtat();
                } else {
                    AffichageJeu.afficher(AffichageJeu.dejaPioche());
                }
                break;

            case 2:
                int indexMain    = this.m_affichageAction.choixJeuMain(this.m_joueur.getMain().getMain().size());
                if (indexMain == -1 ) return;
                if(this.m_tourLogique.sacrifierJoueur(indexMain)){
                    int indexPlateau = this.m_affichageAction.choixJeuPlateau();
                    if (indexPlateau == -1) return;
                    while(indexPlateau != -1 && !this.m_tourLogique.poserCarteJoueur(indexMain, indexPlateau)){
                        indexPlateau = this.m_affichageAction.choixJeuPlateau2();
                    }
                }
                afficherEtat();
                break;

            case 3:
                this.m_pioche = false;
                this.m_tourLogique.finTour(nb_games);
                if (this.m_tourLogique.getJoueurGagne()) {
                    afficherEtat();
                } else if (this.m_tourLogique.getOrdiGagne()) {
                    afficherEtat();
                } else {
                    AffichageJeu.afficher(AffichageJeu.debutTourSuivant());
                    afficherEtat();
                }
                break;

            default:
                AffichageJeu.afficher(AffichageJeu.choixInvalide());
                break;
        }
    }

    private void afficherEtat() {
        Joueur joueur = this.m_tourLogique.getJoueur();
        AffichagePlateau.afficher(
                this.m_tourLogique.getPlateau(),
                this.m_tourLogique.getScore(),
                this.m_numPartie,
                this.m_tourLogique.getCompteurTour(),
                joueur.getMain(),
                joueur.getOs(),
                joueur.getPioche().taille()
        );
    }
}