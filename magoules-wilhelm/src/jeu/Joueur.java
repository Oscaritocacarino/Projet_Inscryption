package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import affichage.AffichageJeu;

public class Joueur {

    private Pioche m_pioche;
    private Pioche m_piocheProv;
    private MainJoueur m_main = new MainJoueur(new Card(new CartesDeJeuFactory().createInstantWin()), new Card(new CartesDeJeuFactory().createInstantWin()), new Card(new CartesDeJeuFactory().createInstantWin()), new Card(new CartesDeJeuFactory().createInstantWin()));
    private int m_os;
    private boolean m_aPioche;

    public Joueur() {
        this.m_pioche  = new Pioche();
        this.m_piocheProv = new Pioche();
        this.resetPioche();
        this.m_os      = 0;
        this.m_aPioche = false;
        initialiserMain();
    }

    public MainJoueur getMain() {
        return this.m_main;
    }

    public Pioche getPioche() {
        return this.m_piocheProv;
    }
    public Pioche getPiocheDef(){
        return this.m_pioche;
    }
    public Card getPiocheDefContenu(int i){
        return this.getPiocheDef().getPioche().get(i);
    }

    public int getOs() {
        return this.m_os;
    }

    public void initialiserMain() {
        Card c1 = this.m_piocheProv.piocher().orElse(null);
        Card c2 = this.m_piocheProv.piocher().orElse(null);
        Card c3 = this.m_piocheProv.piocher().orElse(null);
        Card c4 = new Card(new CartesDeJeuFactory().createEcureuil());
        this.m_main.viderMain();
        this.m_main.ajouterCarte(c1);
        this.m_main.ajouterCarte(c2);
        this.m_main.ajouterCarte(c3);
        this.m_main.ajouterCarte(c4);
    }

    public Optional<Card> piocher() {
        if (m_aPioche) {
            AffichageJeu.afficher(AffichageJeu.dejaPioche());
            return Optional.empty();
        }
        if (m_piocheProv.estVide()) {
            AffichageJeu.afficher(AffichageJeu.piocheVide());
            return Optional.empty();
        }
        Optional<Card> optCarte = m_piocheProv.piocher();
        optCarte.ifPresent(carte -> {
            m_main.ajouterCarte(carte);
            m_aPioche = true;
        });
        return optCarte;
    }

    public void carteEstMorte() {
        this.m_os++;
    }

    public void debutDeTour() {
        this.m_aPioche = false;
    }
    public void depenserOs(int nb){
        this.m_os -= nb;
    }
    public void resetPioche() {
        ArrayList<Card> fraiches = new ArrayList<>();
        for (Card c : this.m_pioche.getPioche()) {
            fraiches.add(new Card(c.getCarte())); // nouvelle instance = PV reinitialisé
        }
        this.m_piocheProv.setPioche(fraiches);
        Collections.shuffle(this.m_piocheProv.getPioche());
    }

}