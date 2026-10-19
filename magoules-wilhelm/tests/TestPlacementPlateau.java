import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste le placement et le retrait des cartes sur le plateau.
 * Couvre le côté joueur, le côté adversaire, le futurBoard,
 * les obstacles initiaux et les cas invalides (case occupée, index hors bornes).
 */
public class TestPlacementPlateau {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Pose une Hermine en case 2 (vide). Elle doit y apparaître correctement.
     */
    @Test
    public void testPoserCarteJoueurDansCaseVide() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());

        plateau.poserCarteJoueur(hermine, 2);

        assertTrue(plateau.getBoardPlayer()[2].isPresent());
        assertEquals("Hermine", plateau.getBoardPlayer()[2].get().getNom());
    }

    /**
     * Pose une Hermine en case 0, puis tente d'y poser un Ecureuil.
     * La deuxième pose doit être ignorée : l'Hermine reste en place.
     */
    @Test
    public void testPoserCarteJoueurRefuseSiOccupe() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine  = new Card(f.createHermine());
        Card ecureuil = new Card(f.createEcureuil());

        plateau.poserCarteJoueur(hermine, 0);
        plateau.poserCarteJoueur(ecureuil, 0);

        assertEquals("Hermine doit toujours occuper la case", "Hermine",
                plateau.getBoardPlayer()[0].get().getNom());
    }

    /**
     * Tente de poser une carte aux index -1 et 4 (hors bornes 0-3).
     * Toutes les cases doivent rester vides.
     */
    @Test
    public void testPoserCarteJoueurIndexInvalideNeChangePasLePlateau() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());

        plateau.poserCarteJoueur(hermine, -1);
        plateau.poserCarteJoueur(hermine,  4);

        for (int i = 0; i < 4; i++) {
            assertFalse("Toutes les cases doivent rester vides", plateau.getBoardPlayer()[i].isPresent());
        }
    }

    /**
     * Pose une Hermine en case 1, puis la retire.
     * La case doit être vide après retrait.
     */
    @Test
    public void testRetirerCarteJoueur() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());

        plateau.poserCarteJoueur(hermine, 1);
        plateau.retirerCarteJoueur(1);

        assertFalse("La case doit être vide après retrait", plateau.getBoardPlayer()[1].isPresent());
    }

    /**
     * Pose un Loup côté adversaire en case 3.
     * Il doit apparaître dans le boardOrdi à la bonne position.
     */
    @Test
    public void testPoserCarteOrdi() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card loup = new Card(f.createLoup());

        plateau.poserCarteOrdi(loup, 3);

        assertTrue(plateau.getBoardOrdi()[3].isPresent());
        assertEquals("Loup", plateau.getBoardOrdi()[3].get().getNom());
    }

    /**
     * Pose un Porc-épic dans le futurBoard (ligne X) en case 2.
     * Il doit y apparaître pour annoncer la prochaine carte adversaire.
     */
    @Test
    public void testPoserCarteFuturOrdi() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card porcepic = new Card(f.createPorcepic());

        plateau.poserFuturCarteOrdi(porcepic, 2);

        assertTrue(plateau.getFuturBoardOrdi()[2].isPresent());
        assertEquals("Porc-épic", plateau.getFuturBoardOrdi()[2].get().getNom());
    }

    /**
     * Pose puis retire une carte du futurBoard.
     * La case doit être vide après retrait (glissement vers boardOrdi effectué).
     */
    @Test
    public void testRetirerCarteFuturOrdi() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card porcepic = new Card(f.createPorcepic());

        plateau.poserFuturCarteOrdi(porcepic, 2);
        plateau.retirerFuturCarteOrdi(2);

        assertFalse(plateau.getFuturBoardOrdi()[2].isPresent());
    }

    /**
     * Crée un plateau avec un Rocher préplacé en B2 (constructeur avec obstacles).
     * B2 doit contenir le Rocher, B1 et B3 doivent être vides.
     */
    @Test
    public void testPlateauAvecObstacleInitial() {
        Card[] obstacles = new Card[4];
        obstacles[1] = new Card(f.createRocher());

        PlateauDeJeu plateau = new PlateauDeJeu(obstacles);

        assertTrue("B2 doit contenir un Rocher", plateau.getBoardPlayer()[1].isPresent());
        assertEquals("Rocher", plateau.getBoardPlayer()[1].get().getNom());
        assertFalse("B1 doit être vide", plateau.getBoardPlayer()[0].isPresent());
        assertFalse("B3 doit être vide", plateau.getBoardPlayer()[2].isPresent());
    }

    /**
     * Vérifie getCardIndex() : retourne la carte présente si l'index est valide,
     * Optional.empty() si la case est vide ou si l'index est hors bornes.
     */
    @Test
    public void testGetCardIndexRetourneCorrectement() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());

        plateau.poserCarteJoueur(hermine, 2);

        assertTrue(plateau.getCardIndex(2).isPresent());
        assertFalse(plateau.getCardIndex(0).isPresent());
        assertFalse(plateau.getCardIndex(99).isPresent());
    }
}