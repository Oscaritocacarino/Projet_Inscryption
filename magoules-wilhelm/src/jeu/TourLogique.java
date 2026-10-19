package jeu;

import affichage.AffichageJeu;

import java.util.Optional;

public class TourLogique {

    private int m_compteurTour = 0;
    private Joueur m_joueur;
    private Joueur m_adversaire;
    private PlateauDeJeu m_plateau;
    private int m_score = 0;
    private Boolean m_joueurGagne = false;
    private Boolean m_ordiGagne   = false;
    private AffichageJeu m_affichage;

    public TourLogique(Joueur joueur, Joueur adversaire) {
        this.m_adversaire = adversaire;
        this.m_joueur     = joueur;
        this.m_plateau    = creerPlateauPartie(1);
        CartesDeJeuFactory f = new CartesDeJeuFactory();
        this.m_plateau.poserFuturCarteOrdi(new Card(f.createPorcepic()), 2);
        this.m_affichage = new AffichageJeu();
    }


    public void newTour(int nbTour){
        this.m_plateau = creerPlateauPartie(nbTour);
        this.m_score = 0;
        this.m_compteurTour = 0;
        this.m_joueurGagne = false;
        this.m_ordiGagne = false;
    }
    public PlateauDeJeu getPlateau()  { return this.m_plateau; }
    public Boolean getOrdiGagne()     { return this.m_ordiGagne; }
    public Boolean getJoueurGagne()   { return this.m_joueurGagne; }
    public int     getScore()         { return this.m_score; }
    public int     getCompteurTour()  { return this.m_compteurTour; }
    public Joueur  getJoueur()        { return this.m_joueur; }


    public void piocherJoueur() {
        Optional<Card> optCarte = this.m_joueur.piocher();
        optCarte.ifPresent(c -> AffichageJeu.afficher(AffichageJeu.cartePiochee(c.getNom())));
    }

    public Boolean poserCarteJoueur(int index, int emplacement) {
        Optional<Card> optCarte = this.m_joueur.getMain().getCarte(index);
        if (optCarte.isEmpty()) {
            AffichageJeu.afficher(AffichageJeu.carteIntrouvable(index));
            return false;
        }
        Card carte = optCarte.get();

        if (!this.m_plateau.getBoardPlayer()[emplacement].isEmpty()) {
            AffichageJeu.afficher(AffichageJeu.emplacementOccupe(
                    this.m_plateau.getBoardPlayer()[emplacement].get().getNom()));

            return false;
        }
        if (carte.getOs() <= this.m_joueur.getOs()) {
            this.m_plateau.poserCarteJoueur(carte, emplacement);
            this.m_joueur.getMain().enleverCarte(index);
            this.m_joueur.depenserOs(carte.getOs());
            AffichageJeu.afficher(AffichageJeu.carteJouee(carte.getNom()));
            return true;
        } else {
            AffichageJeu.afficher(AffichageJeu.pasAssezOs(carte.getNom()));
            return false;
        }
    }
    public void afficherAttaqueCarte(String nom, int dmg){

    }


    private java.util.function.IntSupplier m_demanderSacrifice = () -> 0;

    public void setDemanderSacrifice(java.util.function.IntSupplier fn) {
        this.m_demanderSacrifice = fn;
    }

    public int demanderSacrifice() {
        return m_demanderSacrifice.getAsInt();
    }


    public Boolean sacrifierJoueur( int ca) {
        Card carte = this.m_joueur.getMain().getCarte(ca).get();
        PlateauDeJeu plateau = this.m_plateau;
        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (this.m_plateau.getCardIndex(i).isPresent() && this.m_plateau.getCardIndex(i).get().isSacrifiable()) n++;
        }
        if (carte.getSang() <= n) {
            int sang = 0;
            while (sang < carte.getSang()) {
                int carteSac = demanderSacrifice();
                if(this.m_plateau.getBoardPlayer()[carteSac].isPresent() && this.m_plateau.getBoardPlayer()[carteSac].get().isSacrifiable()){

                    Card c = this.m_plateau.getBoardPlayer()[carteSac].get();
                    AffichageJeu.afficher(AffichageJeu.carteSacrifiee(c.getNom(), carteSac));
                    if(!c.getInvincible()){
                        plateau.retirerCarteJoueur(carteSac);
                        this.m_joueur.carteEstMorte();
                    }

                    sang++;
                }else{
                    AffichageJeu.afficher(AffichageJeu.aucuneCarteSacrifiable(carteSac));
                }
            }
            return true;
        } else {
            AffichageJeu.afficher(AffichageJeu.pasAssezSang());
            return false;
        }
    }
    private PlateauDeJeu creerPlateauPartie(int numPartie) {
        CartesDeJeuFactory f = new CartesDeJeuFactory();
        Card[] obstaclesJoueur = new Card[4];
        Card[] obstaclesOrdi   = new Card[4];

        switch (numPartie) {
            case 1:
                obstaclesJoueur[1] = new Card(f.createRocher()); // B2
                obstaclesOrdi[0]   = new Card(f.createSapin());  // A1
                break;
            case 3:
                obstaclesJoueur[0] = new Card(f.createSapin());  // B1
                obstaclesJoueur[2] = new Card(f.createRocher()); // B3
                obstaclesOrdi[1]   = new Card(f.createSapin()); // A2
                obstaclesOrdi[3]   = new Card(f.createRocher()); // A4
                break;
            default:
                break;
        }

        PlateauDeJeu plateau = new PlateauDeJeu(obstaclesJoueur);

        // Poser les obstacles adversaires directement sur le boardOrdi
        for (int i = 0; i < 4; i++) {
            if (obstaclesOrdi[i] != null) {
                plateau.poserCarteOrdi(obstaclesOrdi[i], i);
            }
        }

        return plateau;
    }

    public void finTour(int nb_games) {
        this.m_compteurTour++;
        this.m_joueur.debutDeTour();

        for (int i = 0; i < 4; i++) {
            if (this.m_plateau.getBoardPlayer()[i].isPresent() && this.m_plateau.getBoardPlayer()[i].get().isAnimal()) {
                this.m_score += this.m_plateau.getBoardPlayer()[i].get().attaque(this.m_plateau.getBoardOrdi()[i], this.m_affichage);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (this.m_plateau.getBoardPlayer()[i].isPresent()) {
                Card carte = this.m_plateau.getBoardPlayer()[i].get();
                if (carte.estMorte()) {
                    this.m_affichage.vaincu(carte.getNom());
                    this.m_plateau.retirerCarteJoueur(i);
                    this.m_joueur.carteEstMorte();
                }
            }
            if (this.m_plateau.getBoardOrdi()[i].isPresent()
                    && this.m_plateau.getBoardOrdi()[i].get().estMorte()) {
                this.m_affichage.vaincu(this.m_plateau.getBoardOrdi()[i].get().getNom());
                this.m_plateau.retirerCarteOrdi(i);
                this.m_adversaire.carteEstMorte();
            }

        }

        if (this.m_score >= 5) { this.m_joueurGagne = true; return; }

        for (int i = 3; i >=0; i--) {
            if (this.m_plateau.getBoardPlayer()[i].isPresent()) {
                this.m_plateau.getBoardPlayer()[i].get().activerPouvoirs(this.m_plateau, i);
            }
        }

        CartesDeJeuFactory f = new CartesDeJeuFactory();


        for (int i = 0; i < 4; i++) {
            Optional<Card> futur = this.m_plateau.getFuturBoardOrdi()[i];
            if (futur.isPresent() && this.m_plateau.getBoardOrdi()[i].isEmpty()) {
                this.m_plateau.poserCarteOrdi(futur.get(), i);
                this.m_plateau.retirerFuturCarteOrdi(i);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (this.m_plateau.getBoardOrdi()[i].isPresent() && this.m_plateau.getBoardOrdi()[i].get().isAnimal()) {
                this.m_score -= this.m_plateau.getBoardOrdi()[i].get().attaque(this.m_plateau.getBoardPlayer()[i], this.m_affichage);
            }
        }
        for(int i = 0 ; i<4 ; i++){
            if (this.m_plateau.getBoardPlayer()[i].isPresent()
                    && this.m_plateau.getBoardPlayer()[i].get().estMorte()) {
                this.m_affichage.vaincu(this.m_plateau.getBoardPlayer()[i].get().getNom());
                this.m_plateau.retirerCarteJoueur(i);
                this.m_joueur.carteEstMorte();
            }
        }


        for (int i = 0; i < 4; i++) {
            if (this.m_plateau.getBoardPlayer()[i].isPresent()
                    && this.m_plateau.getBoardPlayer()[i].get().estMorte()) {
                this.m_plateau.retirerCarteJoueur(i);
                this.m_joueur.carteEstMorte();
            }
            if (this.m_plateau.getBoardOrdi()[i].isPresent()
                    && this.m_plateau.getBoardOrdi()[i].get().estMorte()) {
                this.m_plateau.retirerCarteOrdi(i);
                this.m_adversaire.carteEstMorte();
            }
        }

        if (this.m_score <= -5) { this.m_ordiGagne = true; return; }

        for (int i = 3; i >=0; i--) {
            if (this.m_plateau.getBoardOrdi()[i].isPresent()) {
                Card carteOrdi = this.m_plateau.getBoardOrdi()[i].get();
                if (carteOrdi.getCoureur() && i < 3 && this.m_plateau.getBoardOrdi()[i + 1].isEmpty()) {
                    this.m_plateau.poserCarteOrdi(carteOrdi, i + 1);
                    this.m_plateau.retirerCarteOrdi(i);
                }
                if (carteOrdi.getCroissance()) {
                    this.m_plateau.retirerCarteOrdi(i);
                    this.m_plateau.poserCarteOrdi(new Card(f.createLoup()), i);
                }
            }
        }


        switch (nb_games){
            case 0 :
                switch (this.m_compteurTour) {
                    case 1: this.m_plateau.poserFuturCarteOrdi(new Card(f.createMoineau()),   3); break;
                    case 2: this.m_plateau.poserFuturCarteOrdi(new Card(f.createLouveteau()), 1); break;
                    case 3: this.m_plateau.poserFuturCarteOrdi(new Card(f.createLoup()),      3); break;
                    default: break;
                }
                break;
            case 1 :
                switch (this.m_compteurTour) {
                    case 1: this.m_plateau.poserFuturCarteOrdi(new Card(f.createHermine()),   1); break;
                    case 2: this.m_plateau.poserFuturCarteOrdi(new Card(f.createPunaise()), 0); break;
                    case 3: this.m_plateau.poserFuturCarteOrdi(new Card(f.createCoyote()),      3); break;
                    default: break;
                }
                break;
            case 2 :
                switch (this.m_compteurTour) {
                    case 1: this.m_plateau.poserFuturCarteOrdi(new Card(f.createElan()),   0); break;
                    case 2: this.m_plateau.poserFuturCarteOrdi(new Card(f.createCorbeau()), 3); break;
                    case 3: this.m_plateau.poserFuturCarteOrdi(new Card(f.createVipere()),      0); break;
                    case 4: this.m_plateau.poserFuturCarteOrdi(new Card(f.createVipere()),      2); break;
                    default: break;
                }
                break;
        }

    }
}