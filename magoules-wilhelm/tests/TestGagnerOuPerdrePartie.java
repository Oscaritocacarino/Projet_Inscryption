import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste les conditions de victoire et de défaite d'une partie.
 * Une partie est gagnée quand le score atteint +5, perdue quand il atteint -5.
 * Vérifie les valeurs exactes, les valeurs limites et l'état initial.
 */
public class TestGagnerOuPerdrePartie {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Deux Loups (att 3) sans cible → score +6 ≥ 5.
     * Le joueur doit gagner, l'ordi ne doit pas avoir gagné.
     */
    @Test
    public void testJoueurGagneQuandScoreAtteint5() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 2);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertTrue("Le joueur doit avoir gagné", tour.getJoueurGagne());
        assertFalse("L'ordi ne doit pas avoir gagné", tour.getOrdiGagne());
    }

    /**
     * Hermine(1) + Loup(3) + Hermine(1) = 5 exactement.
     * Le score doit déclencher la victoire à la valeur limite.
     */
    @Test
    public void testJoueurGagneExactement5() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createCorbeau()), 0);
        tour.getPlateau().poserCarteJoueur(new Card(f.createPunaise()),    2);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertTrue("Score exact de 5 doit faire gagner le joueur", tour.getJoueurGagne());
    }

    /**
     * Deux Loups adverses (att 3 chacun) sans cible → score -6 ≤ -5.
     * L'ordi doit gagner, le joueur ne doit pas avoir gagné.
     */
    @Test
    public void testOrdiGagneQuandScoreAtteintMoins5() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 2);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertTrue("L'ordi doit avoir gagné", tour.getOrdiGagne());
        assertFalse("Le joueur ne doit pas avoir gagné", tour.getJoueurGagne());
    }

    /**
     * Hermine(1) + Loup(3) + Hermine(1) adverses = -5 exactement.
     * L'ordi doit gagner à la valeur limite.
     */
    @Test
    public void testOrdiGagneExactementMoins5() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createHermine()), 0);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()),    2);
        tour.getPlateau().poserCarteOrdi(new Card(f.createCoyote()), 3);

        tour.finTour(0);

        assertTrue("Score de -5 doit faire gagner l'ordi", tour.getOrdiGagne());
    }

    /**
     * Une seule Hermine (att 1) → score +1.
     * La partie ne doit pas être terminée (ni joueur ni ordi n'a gagné).
     */
    @Test
    public void testPartieNonTermineeSiScoreEntre() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createHermine()), 0);

        tour.finTour(0);

        assertFalse("Partie ne doit pas être terminée pour score +1", tour.getJoueurGagne());
        assertFalse("Ordi ne doit pas avoir gagné",                   tour.getOrdiGagne());
    }

    /**
     * À la création de TourLogique, aucun joueur n'a gagné et le score est 0.
     */
    @Test
    public void testEtatInitialNonGagne() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertFalse(tour.getJoueurGagne());
        assertFalse(tour.getOrdiGagne());
        assertEquals(0, tour.getScore());
    }

    /**
     * Un Loup adverse (att 3) sans cible → score -4 (ne pas oublier la presence du porc-epic initialisée sur le board de l'ordi).
     * La valeur -4 n'est pas encore en dessous du seuil de -5 : l'ordi ne doit pas encore gagner.
     */
    @Test
    public void testPartieNonTermineeScoreMoins4() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertFalse("Score de -4 ne doit pas faire gagner l'ordi", tour.getOrdiGagne());
        assertEquals(-4, tour.getScore());
    }
}