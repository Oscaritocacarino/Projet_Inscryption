package jeu;

import affichage.AffichageAction;
import affichage.AffichageJeu;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {

    private int m_nbgame = 1;
    private TourLogique       m_tourlogique;
    private InteractionJoueur m_currentTour;
    private Joueur            m_joueur;
    private Joueur            m_adversaire;
    private final Scanner     m_scanner;
    private AffichageAction   m_affichageAction;
    private ArrayList<Integer> m_tabSacr = new ArrayList<Integer>();

    public Run(Scanner scanner) {
        this.m_scanner         = scanner;
        this.m_affichageAction = new AffichageAction();
        this.m_joueur          = new Joueur();
        this.m_adversaire      = new Joueur();

        // Partie 1 : Rocher en B2
        this.m_tourlogique   = new TourLogique(this.m_joueur, this.m_adversaire);
        this.m_currentTour   = new InteractionJoueur(this.m_tourlogique, this.m_affichageAction, this.m_nbgame, this.m_joueur);
    }

    public Card[] recompense() {
        CartesDeJeuFactory f = new CartesDeJeuFactory();
        return new Card[]{ new Card(f.createCorbeau()), new Card(f.createLouveteau()) };
    }

    public Joueur getJoueur()     { return this.m_joueur; }
    public Joueur getAdversaire() { return this.m_adversaire; }

    private void nouvellePartie() {
        this.m_nbgame++;
        this.m_tourlogique.newTour(this.m_nbgame);
        this.m_joueur.resetPioche();
        this.m_joueur.initialiserMain();
        this.m_joueur.depenserOs(this.m_joueur.getOs());
        this.m_currentTour   = new InteractionJoueur(this.m_tourlogique, this.m_affichageAction, this.m_nbgame, this.m_joueur);
    }

    public ArrayList<Card> sacrifices(){
        ArrayList<Card> sac = new ArrayList<Card>();
        for(int i = 0 ; i< this.m_joueur.getPiocheDef().taille() ; i++){
            Card carte = this.m_joueur.getPiocheDef().getPioche().get(i);
            if(carte.getAPouvoirs()){
                sac.add(carte);
                this.m_tabSacr.add(i);
            }
        }
        return sac;
    }
    public void playRun() {
        for (int i = 0; i < 3; i++) {
            while (!this.m_tourlogique.getOrdiGagne() && !this.m_tourlogique.getJoueurGagne()) {
                this.m_currentTour.jouer(this.m_affichageAction.choixAction(), i);
            }
            if (this.m_tourlogique.getJoueurGagne()) {
                AffichageJeu.afficher(AffichageJeu.remportePartie(this.m_nbgame));
            } else {
                // Le joueur perd une partie → il perd le jeu entier
                AffichageJeu.afficher(AffichageJeu.perdPartie(this.m_nbgame));
                AffichageJeu.afficher(AffichageJeu.perdJeu());
                return;
            }

            // Récompense + Pierre de sacrifice après la 2e partie
            if (i == 0) {
                Card[] recompenses = recompense();
                int choix = this.m_affichageAction.choixRecompense(recompenses);
                this.m_joueur.getPiocheDef().ajouterCarte(recompenses[choix]);
                AffichageJeu.afficher(AffichageJeu.carteAjoutee(recompenses[choix].getNom()));
            }
            if(i == 1){
                ArrayList<Card> sacr = this.sacrifices();
                int choix = this.m_affichageAction.choixSacrificePierre(sacr);
                Boolean[] pouvoirs = sacr.get(choix).getPouvoirsBool();
                this.m_joueur.getPiocheDef().getPioche().remove((int)this.m_tabSacr.get(choix));
                int choixBuff = this.m_affichageAction.choixBufferCarte(this.m_joueur.getPiocheDef());
                this.m_joueur.getPiocheDefContenu(choixBuff).setPouvoirs(pouvoirs[0], pouvoirs[1],pouvoirs[2],pouvoirs[3],pouvoirs[4],pouvoirs[5],pouvoirs[6]);
            }
            if (i < 2) nouvellePartie();
        }

        // Les 3 parties remportées → victoire
        AffichageJeu.afficher(AffichageJeu.gagne3Parties());
    }
}