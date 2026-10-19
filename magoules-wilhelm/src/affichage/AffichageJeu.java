package affichage;


public class AffichageJeu {


    public static String dejaPioche()               { return "Vous avez déjà pioché ce tour-ci."; }
    public static String piocheVide()               { return "La pioche est vide !"; }
    public static String cartePiochee(String nom)   { return "Vous avez pioché : " + nom; }

    public static String carteJouee(String nom)     { return "Vous avez joué : " + nom; }
    public static String emplacementOccupe(String nom) {
        return "Il y a déjà la carte " + nom + " à cet emplacement.";
    }
    public static String pasAssezOs(String nom)     { return "Vous n'avez pas assez d'os pour jouer " + nom + "."; }
    public static String pasAssezSang()             { return "Vous ne pouvez pas sacrifier assez de cartes pour jouer cela."; }
    public static String carteIntrouvable(int i)    { return "Carte introuvable en main à l'index " + i + "."; }

    public static void degatsCarte(String nom, int dmg, String nomVic){
        System.out.println(nom + " inflige " + dmg + " de dégats à " + nomVic);
    }

    public static void degatsScore(String nom, int dmg){
        System.out.println("\033[1m" +nom + " inflige " + dmg + " de dégats au score \033[0m");
    }
    public static void vaincu(String nom){
        System.out.println(nom + " a été vaincu");
    }

    public static String aucuneCarteSacrifiable(int col) {
        return "Aucune carte sacrifiable en B" + (col + 1) + ".";
    }
    public static String carteSacrifiee(String nom, int col) {
        return "Vous sacrifiez " + nom + " en B" + (col + 1) + ".";
    }


    public static String indexInvalide()            { return "Index invalide."; }
    public static String emplacementDejeuOccupe()    { return "Il y a déjà une carte à cet emplacement."; }


    public static String debutTourSuivant()         { return "Début du tour suivant."; }
    public static String choixInvalide()            { return "Choix invalide, veuillez choisir une option valide."; }



    public static String remportePartie(int n)      {
        return "\n╔══════════════════════════════════════╗\n" +
                "║  Vous avez gagné cette partie !      ║\n" +
                "║   Place à la prochaine partie !      ║\n" +
                "╚══════════════════════════════════════╝\n";
    }
    public static String perdPartie(int n)          {
        return "\n╔══════════════════════════════════════╗\n" +
                "║    Vous avez perdue cette partie...  ║\n" +
                "║      Mettons fin à ce désastre...    ║\n" +
                "╚══════════════════════════════════════╝\n"; }


    public static String gagne3Parties() {
        return "\n╔══════════════════════════════════════╗\n" +
                "║  Félicitations ! Vous avez remporté  ║\n" +
                "║        les 3 parties. Bravo !        ║\n" +
                "╚══════════════════════════════════════╝\n";
    }
    public static String perdJeu() {
        return "\n╔══════════════════════════════════════╗\n" +
                "║  Vous avez perdu le jeu...           ║\n" +
                "║  L'adversaire l'emporte.             ║\n" +
                "╚══════════════════════════════════════╝\n";
    }

    public static String carteAjoutee(String nom)   { return "Vous ajoutez " + nom + " à votre pioche."; }


    public static void afficher(String message) {
        System.out.println(message);
    }


}