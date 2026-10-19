import jeu.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Teste la mécanique de pioche d'une carte.
 * Vérifie qu'on peut piocher une seule fois par tour, que la carte
 * s'ajoute bien à la main, que la pioche vide est gérée correctement,
 * et que debutDeTour() réinitialise bien le droit de piocher.
 */
public class TestPiocher {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Après debutDeTour(), piocher() doit retourner une carte présente.
     */
    @Test
    public void testPiocherRetourneUneCarte() {
        Joueur joueur = new Joueur();
        joueur.debutDeTour();

        Optional<Card> carte = joueur.piocher();

        assertTrue("La pioche doit retourner une carte", carte.isPresent());
    }

    /**
     * Après pioche, la main du joueur doit contenir une carte de plus.
     */
    @Test
    public void testPiocherAjouteCarteALaMain() {
        Joueur joueur = new Joueur();
        int tailleAvant = joueur.getMain().taille();
        joueur.debutDeTour();

        joueur.piocher();

        assertEquals("La main doit avoir une carte de plus", tailleAvant + 1, joueur.getMain().taille());
    }

    /**
     * Tenter de piocher deux fois dans le même tour doit échouer.
     * Le deuxième appel retourne Optional.empty() et la main ne grandit pas.
     */
    @Test
    public void testPiocherDeuxFoisMemeTourImpossible() {
        Joueur joueur = new Joueur();
        joueur.debutDeTour();
        joueur.piocher();

        Optional<Card> second = joueur.piocher();

        assertFalse("On ne peut pas piocher deux fois dans le même tour", second.isPresent());
        assertEquals("La main ne doit pas avoir augmenté", 5, joueur.getMain().taille());
    }

    /**
     * Après debutDeTour(), le verrou de pioche est réinitialisé :
     * on peut piocher à nouveau au tour suivant.
     */
    @Test
    public void testDebutDeTourPermetDePiocherANouveau() {
        Joueur joueur = new Joueur();
        joueur.debutDeTour();
        joueur.piocher();

        joueur.debutDeTour();
        Optional<Card> carte = joueur.piocher();

        assertTrue("Après debutDeTour on peut piocher à nouveau", carte.isPresent());
    }

    /**
     * Une pioche vidée entièrement doit retourner Optional.empty()
     * et ne pas planter (pas de NullPointerException).
     */
    @Test
    public void testPiocheVideRetourneEmpty() {
        Pioche pioche = new Pioche();
        while (!pioche.estVide()) pioche.piocher();

        Optional<Card> carte = pioche.piocher();

        assertFalse("Une pioche vide doit retourner Optional.empty()", carte.isPresent());
    }

    /**
     * estVide() doit retourner true après avoir pioché toutes les cartes.
     */
    @Test
    public void testPiocheEstVideApresVidage() {
        Pioche pioche = new Pioche();
        while (!pioche.estVide()) pioche.piocher();

        assertTrue(pioche.estVide());
    }

    /**
     * Après une pioche, taille() doit avoir diminué de 1.
     */
    @Test
    public void testPiocherReduitTailleDeLaPioche() {
        Pioche pioche = new Pioche();
        int taille = pioche.taille();

        pioche.piocher();

        assertEquals("La pioche doit avoir une carte de moins", taille - 1, pioche.taille());
    }

    /**
     * La carte piochée doit être non nulle et avoir un nom non nul.
     * Vérifie qu'aucune carte invalide ne peut sortir de la pioche.
     */
    @Test
    public void testPiocheRetourneCartesNonNulles() {
        Pioche pioche = new Pioche();
        Optional<Card> carte = pioche.piocher();

        assertTrue(carte.isPresent());
        assertNotNull(carte.get());
        assertNotNull(carte.get().getNom());
    }
}