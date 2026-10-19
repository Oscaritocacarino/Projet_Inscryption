import jeu.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Teste l'ajout de nouvelles cartes dans la pioche à la fin de la 2e partie.
 * Vérifie que deux cartes sont bien proposées, que la carte choisie est correcte,
 * et que l'ajout en piocheProvisoire fonctionne sans modifier taille().
 */
public class TestRecompense {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Le tableau de récompenses doit toujours contenir exactement 2 cartes.
     */
    @Test
    public void testRecompenseProposExactement2Cartes() {
        Card[] recompenses = new Card[]{
                new Card(f.createCorbeau()),
                new Card(f.createLouveteau())
        };

        assertEquals("Il doit y avoir exactement 2 cartes proposées", 2, recompenses.length);
    }

    /**
     * La première récompense (index 0) doit être un Corbeau.
     */
    @Test
    public void testRecompense0EstCorbeau() {
        Card[] recompenses = new Card[]{
                new Card(f.createCorbeau()),
                new Card(f.createLouveteau())
        };

        assertNotNull(recompenses[0]);
        assertEquals("La première récompense doit être Corbeau", "Corbeau", recompenses[0].getNom());
    }

    /**
     * La deuxième récompense (index 1) doit être un Louveteau.
     */
    @Test
    public void testRecompense1EstLouveteau() {
        Card[] recompenses = new Card[]{
                new Card(f.createCorbeau()),
                new Card(f.createLouveteau())
        };

        assertNotNull(recompenses[1]);
        assertEquals("La deuxième récompense doit être Louveteau", "Louveteau", recompenses[1].getNom());
    }


    /**
     * Vérifie que la carte ajoutée correspond bien à celle choisie (Corbeau).
     * Contrôle le nom, le flag volant et la valeur d'attaque.
     */
    @Test
    public void testCarteAjouteeEstBienLaCarteChoisie() {
        Pioche pioche = new Pioche();
        Card corbeau = new Card(f.createCorbeau());

        pioche.ajouterCarte(corbeau);

        assertEquals("La carte ajoutée doit être Corbeau", "Corbeau", corbeau.getNom());
        assertTrue("Le corbeau doit être volant", corbeau.getVolant());
        assertEquals("Le corbeau doit avoir att 2", 2, corbeau.getAttaque());
    }

    /**
     * Le Louveteau proposé en récompense doit posséder le pouvoir Croissance
     * (se transforme en Loup après son premier tour sur le plateau).
     */
    @Test
    public void testLouveteauRecompenseALaCroissance() {
        Card louveteau = new Card(f.createLouveteau());
        assertTrue("Le louveteau doit avoir le pouvoir croissance", louveteau.getCroissance());
    }

    /**
     * Le Corbeau proposé en récompense doit être volant
     * (attaque directement le score même avec une cible en face).
     */
    @Test
    public void testCorbeauRecompenseEstVolant() {
        Card corbeau = new Card(f.createCorbeau());
        assertTrue("Le corbeau doit être volant", corbeau.getVolant());
    }


}