import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste la mise en place initiale d'une partie : pioche, main du joueur,
 * état du plateau, score, os et compteur de tours au démarrage.
 * Vérifie que toutes les valeurs initiales sont correctes avant le premier tour.
 */
public class TestMiseEnPlace {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * La pioche de départ doit contenir exactement 15 cartes
     */
    @Test
    public void testPiocheContient15Cartes() {
        Pioche pioche = new Pioche();
        assertEquals("La pioche doit contenir 15 cartes", 15, pioche.taille());
    }

    /**
     * Parmi les 15 cartes, 8 doivent être des Ecureuils
     */
    @Test
    public void testPiocheContient8Ecureuils() {
        Pioche pioche = new Pioche();
        long nbEcureuils = pioche.getPioche().stream()
                .filter(c -> c.getNom().equals("Ecureuil"))
                .count();
        assertEquals("La pioche doit contenir 8 écureuils", 8, nbEcureuils);
    }

    /**
     * À la création du Joueur, initialiserMain() est appelé automatiquement.
     * La main doit contenir exactement 4 cartes.
     */
    @Test
    public void testJoueurMain4CartesAuDebut() {
        Joueur joueur = new Joueur();
        assertEquals("La main doit contenir 4 cartes au début", 4, joueur.getMain().taille());
    }

    /**
     * Après que le joueur ait tiré ses 4 cartes initiales,
     * il doit rester 11 cartes dans la pioche (15 - 4 = 11).
     */
    @Test
    public void testJoueurPioche11CartesApresInitMain() {
        Joueur joueur = new Joueur();
        assertEquals("Il doit rester 11 cartes après tirage initial", 11, joueur.getPioche().taille());
    }

    /**
     * À la création, toutes les cases du plateau (joueur, ordi, futurOrdi)
     * doivent être vides (Optional.empty()).
     */
    @Test
    public void testPlateauToutesLesCasesVidesAuDebut() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        for (int i = 0; i < 4; i++) {
            assertFalse("Case joueur " + i + " doit être vide",     plateau.getBoardPlayer()[i].isPresent());
            assertFalse("Case ordi " + i + " doit être vide",       plateau.getBoardOrdi()[i].isPresent());
            assertFalse("Case futur ordi " + i + " doit être vide", plateau.getFuturBoardOrdi()[i].isPresent());
        }
    }

    /**
     * Le joueur commence avec 0 os (aucune carte morte au départ).
     */
    @Test
    public void testJoueurOsInitialZero() {
        Joueur joueur = new Joueur();
        assertEquals("Le joueur commence avec 0 os", 0, joueur.getOs());
    }

    /**
     * Le score de TourLogique doit être 0 avant tout finTour().
     */
    @Test
    public void testScoreInitialZero() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertEquals("Le score initial doit être 0", 0, tour.getScore());
    }

    /**
     * Le compteur de tour doit démarrer à 0 avant le premier finTour().
     */
    @Test
    public void testCompteurTourInitialZero() {
        Joueur joueur     = new Joueur();
        Joueur adversaire = new Joueur();
        TourLogique tour = new TourLogique(joueur, adversaire);

        assertEquals("Le compteur de tour doit démarrer à 0", 0, tour.getCompteurTour());
    }

    /**
     * Le constructeur PlateauDeJeu(Card[]) permet de placer des obstacles initiaux.
     * Vérifie que Sapin en B1 et Rocher en B3 sont bien placés, B2 et B4 vides.
     */
    @Test
    public void testPlateauObstaclesBien() {
        Card[] obstacles = new Card[4];
        obstacles[0] = new Card(f.createSapin());
        obstacles[2] = new Card(f.createRocher());

        PlateauDeJeu plateau = new PlateauDeJeu(obstacles);

        assertEquals("Sapin",  plateau.getBoardPlayer()[0].get().getNom());
        assertFalse("B2 doit être vide", plateau.getBoardPlayer()[1].isPresent());
        assertEquals("Rocher", plateau.getBoardPlayer()[2].get().getNom());
        assertFalse("B4 doit être vide", plateau.getBoardPlayer()[3].isPresent());
    }
}