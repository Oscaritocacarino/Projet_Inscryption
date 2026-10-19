import jeu.*;
import affichage.AffichageJeu;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Teste la mécanique d'attaque d'une carte individuelle.
 * Vérifie que les dégâts sont bien infligés, que la mort est détectée,
 * et que le score est correctement retourné selon la présence ou non d'une cible.
 */
public class TestAttaqueCarte {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    /**
     * Un Loup (att 3) attaque un Ecureuil (pv 1).
     * La cible doit mourir et l'attaque ne doit pas rapporter de score.
     */
    @Test
    public void testAttaqueCarteContreCibleTue() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card loup     = new Card(f.createLoup());
        Card ecureuil = new Card(f.createEcureuil());

        plateau.poserCarteOrdi(ecureuil, 0);
        int scoreGain = loup.attaque(Optional.of(ecureuil), affichage);

        assertEquals("L'attaque sur cible ne rapporte pas de score", 0, scoreGain);
        assertTrue("L'écureuil doit être mort après 3 dégâts", ecureuil.estMorte());
    }

    /**
     * Un Loup (att 3) attaque sans cible en face.
     * Le score retourné doit être égal à la valeur d'attaque.
     */
    @Test
    public void testAttaqueSansCibleRetourneAttaque() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card loup = new Card(f.createLoup());

        int scoreGain = loup.attaque(Optional.empty(), affichage);

        assertEquals("L'attaque sans cible rapporte l'attaque en score", 3, scoreGain);
    }

    /**
     * Une Hermine (att 1) attaque un Grizzly (pv 6).
     * La cible doit survivre avec 5 PV restants (pas de mort).
     */
    @Test
    public void testAttaqueInfligeDegats() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());
        Card grizzly = new Card(f.createGrizzly());

        plateau.poserCarteOrdi(grizzly, 0);
        hermine.attaque(Optional.of(grizzly), affichage);

        assertFalse("Le Grizzly ne doit pas être mort", grizzly.estMorte());
        assertEquals("Le Grizzly doit avoir 5 PV restants", 5, grizzly.getPvActuels());
    }

    /**
     * Vérifie que subirDegats() réduit bien les PV et que estMorte()
     * passe à true quand les PV tombent à 0 ou moins.
     */
    @Test
    public void testSubirDegatsReducePv() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card hermine = new Card(f.createHermine());
        hermine.subirDegats(2);
        assertEquals(1, hermine.getPvActuels());
        assertFalse(hermine.estMorte());
        hermine.subirDegats(1);
        assertTrue(hermine.estMorte());
    }

    /**
     * Un Ecureuil (att 0) attaque une Hermine (pv 3).
     * Aucun dégât ne doit être infligé, la cible doit rester en vie avec tous ses pv.
     */
    @Test
    public void testAttaqueAvecAttaqueZeroNeTuePas() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card ecureuil = new Card(f.createEcureuil());
        Card hermine  = new Card(f.createHermine());

        plateau.poserCarteOrdi(hermine, 0);
        ecureuil.attaque(Optional.of(hermine), affichage);

        assertFalse("L'Hermine ne doit pas mourir d'une attaque à 0", hermine.estMorte());
        assertEquals(3, hermine.getPvActuels());
    }
}