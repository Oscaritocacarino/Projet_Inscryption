import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste l'attaque de toutes les cartes à la fin d'un tour via finTour().
 * Vérifie que chaque carte attaque bien sa cible (ou le score si pas de cible),
 * que les cartes mortes sont retirées, et que le score est cumulé correctement.
 */
public class TestFinTour {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Joueur : Loup (col 0) face à un Ecureuil, Hermine (col 1) sans cible.
     * Le Loup tue l'Ecureuil (pas de score), l'Hermine rapporte +1.
     * TourLogique place un Porc-épic en futurBoard,
     * qui glisse en boardOrdi au finTour() et attaque (-1).
     * Score final = +1 - 1 (Porc-épic) = 0.
     */
    @Test
    public void testFinTourCarteJoueurTueCibleEtSansCibleMarque() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()),    0);
        tour.getPlateau().poserCarteJoueur(new Card(f.createHermine()), 3);
        tour.getPlateau().poserCarteOrdi(new Card(f.createEcureuil()),  0);
        tour.finTour(0);

        assertFalse("L'emplacement A0 doit être vide (écureuil mort)",
                tour.getPlateau().getBoardOrdi()[0].isPresent());
        assertEquals("Score doit être 0 (Hermine +1, Porc-épic -1)", 0, tour.getScore());
    }

    /**
     * Deux Moineaux (att 1) sans aucune cible adverse : +1 chacune = +2.
     * Le Porc-épic de l'adversaire glisse et attaque (-1).
     * Score final = +2 - 1 = +1.
     */
    @Test
    public void testFinTourPlusieursCartesSansCible() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createMoineau()), 0);
        tour.getPlateau().poserCarteJoueur(new Card(f.createMoineau()), 3);
        tour.finTour(0);

        assertEquals("Score doit être +1 (2 Moineaux +2, Porc-épic -1)", 1, tour.getScore());
    }

    /**
     * Adversaire : Loup (att 3) sans carte joueur en face → -3.
     * Le constructeur TourLogique place aussi un Porc-épic en futurBoard
     * qui glisse en boardOrdi au finTour() et attaque sans cible (-1).
     * Score final = -3 - 1 = -4.
     */
    @Test
    public void testFinTourAdversaireAttaqueJoueur() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);
        tour.finTour(0);

        assertEquals("Score doit être -4 (Loup -3, Porc-épic -1)", -4, tour.getScore());
    }

    /**
     * Joueur : Loup (att 3) face à un Ecureuil (pv 1).
     * Après finTour(), l'emplacement A0 doit être vide (carte ordi retirée automatiquement).
     */
    @Test
    public void testFinTourCarteMorteRetireeduPlateau() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()),     0);
        tour.getPlateau().poserCarteOrdi(new Card(f.createEcureuil()), 0);
        tour.finTour(0);

        assertFalse("L'emplacement A0 doit être vide après la mort de l'écureuil",
                tour.getPlateau().getBoardOrdi()[0].isPresent());
    }

    /**
     * Plateau entièrement vide des deux côtés.
     * Le Porc-épic placé automatiquement dans le futurBoard de l'adversaire,
     * glisse en boardOrdi au finTour() et attaque sans cible → score = -1.
     */
    @Test
    public void testFinTourAucuneCarteScoreInchange() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.finTour(0);

        assertEquals("Score doit être -1 (Porc-épic adversaire)", -1, tour.getScore());
    }
}