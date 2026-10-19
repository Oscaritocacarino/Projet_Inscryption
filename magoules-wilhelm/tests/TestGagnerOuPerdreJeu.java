import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste la logique de victoire et de défaite sur l'ensemble du jeu (3 parties).
 * Le joueur gagne s'il remporte les 3 parties consécutives.
 * Il perd le jeu dès qu'il perd une seule partie.
 * Vérifie aussi que la victoire/défaite est constatée immédiatement sans tour supplémentaire.
 */
public class TestGagnerOuPerdreJeu {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Simule 3 victoires consécutives (score ≥ 5 à chaque partie).
     * partiesGagnees doit atteindre 3 et jeuPerdu doit rester false.
     */
    @Test
    public void testGagner3PartiesGagneLeJeu() {
        int partiesGagnees = 0;
        boolean jeuPerdu = false;

        for (int i = 0; i < 3; i++) {
            Joueur joueur     = new Joueur();
            Joueur adversaire = new Joueur();

            TourLogique tour = new TourLogique(joueur, adversaire);
            tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 2);
            tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);
            // = à 6 d'attaque

            tour.finTour(0);

            if (tour.getJoueurGagne()) partiesGagnees++;
            else { jeuPerdu = true; break; }
        }

        assertEquals("3 parties gagnées pour remporter le jeu", 3, partiesGagnees);
        assertFalse("Le jeu ne doit pas être perdu", jeuPerdu);
    }

    /**
     * Simule 1 victoire puis 1 défaite.
     * Le jeu doit être perdu après la défaite, avec 1 seule partie gagnée.
     */
    @Test
    public void testPerdreUnePartiePerdLeJeu() {
        boolean jeuPerdu = false;
        int partiesGagnees = 0;

        // Partie 1 : victoire joueur
        Joueur j1 = new Joueur(); Joueur a1 = new Joueur();
        TourLogique t1 = new TourLogique(j1, a1);
        t1.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 2);
        t1.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);
        t1.finTour(0);
        if (t1.getJoueurGagne()) partiesGagnees++;
        else jeuPerdu = true;

        // Partie 2 : victoire adversaire
        Joueur j2 = new Joueur(); Joueur a2 = new Joueur();
        TourLogique t2 = new TourLogique(j2, a2);
        t2.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 2);
        t2.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);
        t2.finTour(0);
        if (t2.getOrdiGagne()) jeuPerdu = true;

        assertTrue("Perdre une partie doit entraîner la perte du jeu", jeuPerdu);
        assertEquals("Une seule partie gagnée avant la défaite", 1, partiesGagnees);
    }

    /**
     * À la création de TourLogique, aucun joueur n'a encore gagné.
     * Aucun flag de victoire ou de défaite ne doit être levé avant finTour().
     */
    @Test
    public void testPremiereTourLogiqueNonGagnee() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertFalse("Pas de victoire dès le départ", tour.getJoueurGagne());
        assertFalse("Pas de défaite dès le départ",  tour.getOrdiGagne());
    }

    /**
     * Gagner 1 partie sur 3 ne suffit pas pour gagner le jeu.
     * Si une défaite survient ensuite, jeuPerdu passe à true.
     */
    @Test
    public void testUneSeulePartieGagneeNeSuffitPas() {
        int partiesGagnees = 0;
        boolean jeuPerdu = false;

        Joueur j = new Joueur(); Joueur a = new Joueur();
        TourLogique t = new TourLogique(j, a);
        t.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 0);
        t.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 1);
        t.finTour(0);
        if (t.getJoueurGagne()) partiesGagnees++;

        jeuPerdu = true;

        assertTrue("Le jeu est perdu si une partie est perdue", jeuPerdu);
        assertNotEquals("1 partie gagnée n'est pas suffisant", 3, partiesGagnees);
    }

    /**
     * Deux Loups (att 3) sans cible → score +6 ≥ 5.
     * La victoire doit être constatée immédiatement après finTour(),
     * sans devoir appeler finTour() une deuxième fois.
     */
    @Test
    public void testScore5SuffiSansTourSupplementaire() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 2);
        tour.getPlateau().poserCarteJoueur(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertTrue("La victoire doit être constatée immédiatement", tour.getJoueurGagne());
        assertTrue("Le score doit être ≥ 5", tour.getScore() >= 5);
    }

    /**
     * Deux Loups adverses sans cible → score -6 ≤ -5.
     * La défaite doit être constatée immédiatement après finTour().
     */
    @Test
    public void testScoreMoins5SuffiSansTourSupplementaire() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();

        TourLogique tour = new TourLogique(joueur, adversaire);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 2);
        tour.getPlateau().poserCarteOrdi(new Card(f.createLoup()), 3);

        tour.finTour(0);

        assertTrue("La défaite doit être constatée immédiatement", tour.getOrdiGagne());
        assertTrue("Le score doit être ≤ -5", tour.getScore() <= -5);
    }

    /**
     * Le compteur de tour commence à 0 et doit s'incrémenter de 1
     * à chaque appel de finTour().
     */
    @Test
    public void testCompteurTourIncrementeAChaqueTour() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertEquals(0, tour.getCompteurTour());
        tour.finTour(0);
        assertEquals(1, tour.getCompteurTour());
        tour.finTour(0);
        assertEquals(2, tour.getCompteurTour());
    }
}