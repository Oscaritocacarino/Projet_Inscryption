import jeu.*;
import affichage.AffichageJeu;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Teste chaque pouvoir de carte individuellement.
 * Chaque pouvoir est isolé pour vérifier qu'il produit exactement
 * l'effet attendu sans interférence avec d'autres mécaniques.
 */
public class TestPouvoirs {

    private CartesDeJeuFactory f;

    @Before
    public void setUp() {
        f = new CartesDeJeuFactory();
    }

    // Volant

    /**
     * Un Corbeau (volant, att 2) attaque un Ecureuil placé en face.
     * La carte volante doit ignorer la cible et attaquer directement le score (+2).
     * L'Ecureuil ne doit subir aucun dégât.
     */
    @Test
    public void testVolantIgnoreCibleEtAttaqueScore() {
        AffichageJeu affichage = new AffichageJeu();
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card corbeau  = new Card(f.createCorbeau());
        Card ecureuil = new Card(f.createEcureuil());

        plateau.poserCarteOrdi(ecureuil, 0);
        int score = corbeau.attaque(Optional.of(ecureuil), affichage);

        assertEquals("Le volant doit attaquer le score même avec une cible", 2, score);
        assertFalse("L'écureuil ne doit subir aucun dégât", ecureuil.estMorte());
        assertEquals(1, ecureuil.getPvActuels());
    }

    /**
     * Un Moineau (volant, att 1) attaque sans aucune cible.
     * Doit rapporter +1 au score comme une attaque directe normale.
     */
    @Test
    public void testVolantSansCibleAttaqueScore() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card moineau = new Card(f.createMoineau());

        int score = moineau.attaque(Optional.empty(), affichage);
        assertEquals(1, score);
    }

    // Piquant

    /**
     * Un Loup (att 3, pv 2) attaque un Porc-épic (piquant, pv 2).
     * Le Porc-épic meurt (3 dégâts > 2 PV) et riposte 1 dégât au Loup.
     * Le Loup doit avoir 1 PV restant.
     */
    @Test
    public void testPiquantRiposteUnDegat() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card loup     = new Card(f.createLoup());
        Card porcepic = new Card(f.createPorcepic());

        plateau.poserCarteOrdi(porcepic, 0);
        loup.attaque(Optional.of(porcepic), affichage);

        assertEquals("Le loup doit avoir subi 1 dégât de riposte", 1, loup.getPvActuels());
        assertTrue("Le porc-épic doit être mort (3 dégâts)", porcepic.estMorte());
    }

    /**
     * Un Ecureuil (att 0, pv 1) attaque un Porc-épic (piquant).
     * La riposte piquante se déclenche dès qu'on attaque, quelle que soit l'attaque.
     * L'Ecureuil subit 1 dégât et meurt.
     */
    @Test
    public void testPiquantRiposteMemeAvecAttaqueNulle() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card louveteau = new Card(f.createLouveteau());
        Card porcepic = new Card(f.createPorcepic());

        plateau.poserCarteOrdi(porcepic, 2);
        plateau.poserCarteJoueur(louveteau, 2);
        louveteau.attaque(Optional.of(porcepic), affichage);

        assertEquals("L'écureuil doit subir 1 dégât de riposte", 0, louveteau.getPvActuels());
        assertTrue("L'écureuil doit être mort", louveteau.estMorte());
    }

    // Puant

    /**
     * Un Loup (att 3) attaque une Punaise (puant, pv 2).
     * L'attaque est réduite de 1 : 3-1 = 2 dégâts. La Punaise meurt exactement.
     * Le Loup ne subit pas de riposte (la Punaise n'est pas piquante).
     */
    @Test
    public void testPuantReduitAttaqueDe1() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card loup    = new Card(f.createLoup());
        Card punaise = new Card(f.createPunaise());

        plateau.poserCarteOrdi(punaise, 0);
        loup.attaque(Optional.of(punaise), affichage);

        assertTrue("La punaise doit mourir après 2 dégâts", punaise.estMorte());
        assertEquals("Le loup ne doit pas subir de riposte", 2, loup.getPvActuels());
    }

    /**
     * Un Ecureuil (att 0) attaque une Punaise (puant).
     * 0 - 1 = -1, plafonné à 0 : aucun dégât infligé, la Punaise survit.
     */
    @Test
    public void testPuantAZero() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card ecureuil = new Card(f.createEcureuil());
        Card punaise  = new Card(f.createPunaise());

        plateau.poserCarteOrdi(punaise, 0);
        ecureuil.attaque(Optional.of(punaise), affichage);

        assertEquals("La punaise ne doit subir aucun dégât", 2, punaise.getPvActuels());
    }

    // Venimeux (Contact mortel)

    /**
     * Une Vipère (venimeuse, att 1) attaque un Grizzly (pv 6).
     * Le venimeux tue instantanément la cible peu importe ses PV restants.
     */
    @Test
    public void testVeneimuxTueCibleInstantanement() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card vipere  = new Card(f.createVipere());
        Card grizzly = new Card(f.createGrizzly());

        plateau.poserCarteOrdi(grizzly, 0);
        vipere.attaque(Optional.of(grizzly), affichage);

        assertTrue("Le venimeux doit tuer instantanément même un Grizzly", grizzly.estMorte());
    }

    /**
     * Une Vipère (att 1) sans cible.
     * Le pouvoir venimeux ne s'applique pas : rapporte juste son attaque en score.
     */
    @Test
    public void testVeneimuxSansCibleRapporteScore() {
        AffichageJeu affichage = new AffichageJeu();

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card vipere = new Card(f.createVipere());

        int score = vipere.attaque(Optional.empty(), affichage);
        assertEquals("Sans cible, le venimeux rapporte son attaque au score", 1, score);
    }

    // Croissance

    /**
     * Un Louveteau (croissance) posé en case 0.
     * Après activerPouvoirs(), il doit se transformer en Loup (att 3)
     * sur la même case.
     */
    @Test
    public void testCroissanceTransformeLouveteauEnLoup() {

        PlateauDeJeu plateau = new PlateauDeJeu();
        Card louveteau = new Card(f.createLouveteau());
        plateau.poserCarteJoueur(louveteau, 0);

        louveteau.activerPouvoirs(plateau, 0);

        assertTrue("La case 0 doit contenir un Loup", plateau.getBoardPlayer()[0].isPresent());
        assertEquals("Loup", plateau.getBoardPlayer()[0].get().getNom());
        assertEquals("Le loup doit avoir 3 d'attaque", 3,
                plateau.getBoardPlayer()[0].get().getAttaque());
    }

    /**
     * Un Louveteau posé en case 2 se transforme en Loup en case 2.
     * La case 1 ne doit pas être affectée.
     */
    @Test
    public void testCroissanceLoupRemplaceLouveteauMemeCase() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card louveteau = new Card(f.createLouveteau());
        plateau.poserCarteJoueur(louveteau, 2);

        louveteau.activerPouvoirs(plateau, 2);

        assertFalse("La case 1 ne doit pas contenir de carte", plateau.getBoardPlayer()[1].isPresent());
        assertTrue("Le Loup doit être en case 2", plateau.getBoardPlayer()[2].isPresent());
    }

    // Coureur

    /**
     * Un Elan (coureur) en case 0 avec la case 1 libre.
     * Après activerPouvoirs(), l'Elan se déplace vers la droite (case 1).
     * La case 0 doit être vide.
     */
    @Test
    public void testCoureurSeDeplaceDroite() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card elan = new Card(f.createElan());
        plateau.poserCarteJoueur(elan, 0);

        elan.activerPouvoirs(plateau, 0);

        assertFalse("La case 0 doit être vide après déplacement",
                plateau.getBoardPlayer()[0].isPresent());
        assertTrue("L'élan doit être en case 1",
                plateau.getBoardPlayer()[1].isPresent());
        assertEquals("Elan", plateau.getBoardPlayer()[1].get().getNom());
    }

    /**
     * Un Elan en case 0 avec la case 1 occupée par un Ecureuil.
     * La droite est bloquée : l'Elan ne se déplace pas, reste en case 0.
     */
    @Test
    public void testCoureurBloqueNeBougePas() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card elan     = new Card(f.createElan());
        Card ecureuil = new Card(f.createEcureuil());

        plateau.poserCarteJoueur(elan,     0);
        plateau.poserCarteJoueur(ecureuil, 1);

        elan.activerPouvoirs(plateau, 0);

        assertTrue("L'élan doit rester en 0 si la droite est bloquée",
                plateau.getBoardPlayer()[0].isPresent());
        assertEquals("Elan", plateau.getBoardPlayer()[0].get().getNom());
    }

    /**
     * Un Elan en dernière colonne (case 3) : pas de case à droite.
     * Il ne doit pas se déplacer.
     */
    @Test
    public void testCoureurEnDerniereColonneNeBougePas() {
        PlateauDeJeu plateau = new PlateauDeJeu();
        Card elan = new Card(f.createElan());
        plateau.poserCarteJoueur(elan, 3);

        elan.activerPouvoirs(plateau, 3);

        assertTrue("L'élan en dernière colonne ne doit pas bouger",
                plateau.getBoardPlayer()[3].isPresent());
    }

    // Invincible (Chat : nombreuses vies)

    /**
     * Vérifie que le Chat a bien le flag invincible à true.
     * Ce flag empêche sa mort lors d'un sacrifice.
     */
    @Test
    public void testChatEstInvincible() {
        Card chat = new Card(f.createChat());
        assertTrue("Le chat doit avoir le flag invincible", chat.getInvincible());
    }

    /**
     * Le Chat est un animal donc isSacrifiable() retourne true.
     * (La logique d'invincibilité est vérifiée séparément lors du sacrifice.)
     */
    @Test
    public void testChatEstSacrifiable() {
        Card chat = new Card(f.createChat());
        assertTrue("Le chat est sacrifiable car isAnimal() = true", chat.isSacrifiable());
    }
}