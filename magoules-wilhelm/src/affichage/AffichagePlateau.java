package affichage;

import jeu.Card;
import jeu.MainJoueur;
import jeu.PlateauDeJeu;

import java.util.List;
import java.util.Optional;

public class AffichagePlateau {

    private static final int NB_COL   = 4;
    private static final String SEP   = "   "; // séparateur entre colonnes
    // Largeur d'une carte = 13 caractères + 3 de séparateur
    private static final int LARGEUR_CARTE_ET_SEP = 13 + 3; // 16

    public static void afficher(
            PlateauDeJeu plateau,
            int score,
            int numPartie,
            int numTour,
            MainJoueur mainJoueur,
            int nbOs,
            int nbPioche
    ) {
        System.out.println();
        afficherEntete(numPartie, numTour, score);
        afficherFuturBoardOrdi(plateau);
        afficherFleches();
        afficherBoardOrdi(plateau, score);
        afficherBoardJoueur(plateau);
        afficherMain(mainJoueur.getMain(), nbOs, nbPioche);

    }

    private static void afficherEntete(int numPartie, int numTour, int score) {
        System.out.print("          ");
        System.out.println("=============================================================");
        System.out.print("               ");
        System.out.printf ("    Partie %d  —  Tour %-3d          Score : %+d%n",
                numPartie, numTour, score);
        System.out.print("          ");
        System.out.println("=============================================================");
        System.out.println();
    }


    private static void afficherFuturBoardOrdi(PlateauDeJeu plateau) {
        Optional<Card>[] futur = plateau.getFuturBoardOrdi();
        String[][] lignes = new String[NB_COL][];
        for (int i = 0; i < NB_COL; i++) {
            Card c = futur[i].orElse(null);
            lignes[i] = AffichageCarte.lignes(c, "X" + (i + 1));
        }
        imprimerRangee("          ", lignes, null);
    }

    private static void afficherFleches() {
        StringBuilder haut = new StringBuilder("          ");
        StringBuilder bas  = new StringBuilder("          ");
        for (int col = 0; col < NB_COL; col++) {
            haut.append("      ||      ");
            bas .append("      \\/      ");
            if (col < NB_COL - 1) { haut.append("  "); bas.append("  "); }
        }
        System.out.println(haut);
        System.out.println(bas);
    }

    private static void afficherBoardOrdi(PlateauDeJeu plateau, int score) {
        Optional<Card>[] board = plateau.getBoardOrdi();
        String[][] lignes = new String[NB_COL][];
        for (int i = 0; i < NB_COL; i++) {
            Card c = board[i].orElse(null);
            lignes[i] = AffichageCarte.lignes(c, "A" + (i + 1));
        }
        imprimerRangee("          ", lignes, null);
    }

    private static void afficherBoardJoueur(PlateauDeJeu plateau) {
        Optional<Card>[] board = plateau.getBoardPlayer();
        String[][] lignes = new String[NB_COL][];
        for (int i = 0; i < NB_COL; i++) {
            Card c = board[i].orElse(null);
            lignes[i] = AffichageCarte.lignes(c, "B" + (i + 1));
        }
        imprimerRangee("          ", lignes, null);
    }

    private static void imprimerRangee(String prefixeFixe, String[][] lignes, Integer ligneScoreIndex) {
        int nbLignes = lignes[0].length; // 7
        for (int l = 0; l < nbLignes; l++) {
            if (ligneScoreIndex != null && l == ligneScoreIndex) {
                System.out.print(padDroite(prefixeFixe, 10));
            } else {
                System.out.print("          ");
            }
            for (int col = 0; col < NB_COL; col++) {
                System.out.print(lignes[col][l]);
                if (col < NB_COL - 1) System.out.print(SEP);
            }
            System.out.println();
        }
    }

    private static void afficherMain(List<Card> cartes, int nbOs, int nbPioche) {
        System.out.println();
        System.out.printf("Votre main (Os disponibles : %d) :%n", nbOs);
        System.out.println();
        if (cartes.isEmpty()) {
            System.out.println("  (main vide)");
        } else {
            for (int i = 0; i < cartes.size(); i++) {
                Card c = cartes.get(i);
                if (c.isAnimal()) {
                    System.out.printf(
                            "  %d. %-12s PV:%-2d  Att:%-2d  Sang:%-2d  Os:%-2d%s%n",
                            i + 1,
                            capitalise(c.getNom()),
                            c.getPvActuels(),
                            c.getAttaque(),
                            c.getSang(),
                            c.getOs(),
                            cartes.get(i).getListePouvoirs().stream()
                                    .map(String::trim)
                                    .collect(java.util.stream.Collectors.joining(" "))
                    );
                } else {
                    System.out.printf(
                            "  %d. %-12s PV:%-2d  [Obstacle]%n",
                            i + 1,
                            capitalise(c.getNom()),
                            c.getPvActuels()
                    );
                }
            }
        }
        System.out.println();
        System.out.printf("Pioche : %d carte(s) restante(s)%n", nbPioche);

    }





    private static String padDroite(String s, int largeur) {
        if (s.length() >= largeur) return s.substring(0, largeur);
        return s + " ".repeat(largeur - s.length());
    }

    private static String capitalise(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}