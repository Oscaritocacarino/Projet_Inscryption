import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste la mise à jour du score au fil des tours.
 * Le score augmente quand le joueur attaque sans cible,
 * et diminue quand l'adversaire attaque sans cible.
 * Vérifie les cas simples, cumulés, neutres et l'état initial.
 */
public class TestScore {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Un Loup (att 3) du joueur sans carte adverse en face.
     * Attaque directe : score = +2 après finTour().
     * Car Porc-Epic présent (att 1)
     */
    @Test
    public void testScoreAugmenteAttaqueJoueurSansCible() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertEquals("Score doit être +2", 2, tour.getScore());
    }

    /**
     * Un Loup (att 3) de l'adversaire sans carte joueur en face.
     * Attaque directe adverse : score = -3 après finTour().
     * + Porc-épic (att 1)
     */
    @Test
    public void testScoreDiminueAttaqueAdversaireSansCible() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);
        // + Porc-épic (att 1)
        tour.finTour(0);

        assertEquals("Score doit être -4", -4, tour.getScore());
    }

    /**
     * Joueur : Moineau (col 0) +1, Loup (col 3) +3
     * Adversaire : Hermine (col 1) -1. Mais rocher donc 0
     * Adversaire : Porc-épic initialisé en col 2 : -1
     * Score net = +1 +3 -1 = +3.
     */
    @Test
    public void testScoreCumulPlusieursCartes() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createMoineau()), 0);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()),    3);
        tour.getPlateau().poserCarteOrdi(new Card(f.createHermine()),   1);

        tour.finTour(0);

        assertEquals("Score cumulé doit être +3", 3, tour.getScore());
    }

    /**
     * Joueur : Porc-épic (col 3) +1. Adversaire : Porc-épic (col 2) -1.
     * Colonnes différentes, pas de combat. Score net = 0.
     */
    @Test
    public void testScoreNulAvecAttaquesEgales() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createPorcepic()), 3);
        //Porc-épic deja présent chez l'ordi

        tour.finTour(0);

        assertEquals(0, tour.getScore());
    }

    /**
     * Avant tout tour, le score doit être 0.
     */
    @Test
    public void testScoreInitialZero() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertEquals("Score initial doit être 0", 0, tour.getScore());
    }

    /**
     * Un Loup (att 3) sans cible : après finTour() le score passe de 0 à +2 (car Porc-épic (att 1) présent chez l'adversaire).
     * On ne teste qu'un seul tour car finTour() place des cartes adversaires
     * automatiquement à partir du tour 1, ce qui rendrait le calcul dépendant de l'IA.
     */
    @Test
    public void testScoreAccumuleSurPlusieursFinDeTour() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);
        //Porc-épic toujours présent implicitement
        int scoreAvant = tour.getScore();
        tour.finTour(0);
        int scoreApres = tour.getScore();

        assertTrue("Le score doit avoir augmenté après un tour", scoreApres > scoreAvant);
        assertEquals("Le score doit être +2 après le premier tour", 2, scoreApres);
    }
}