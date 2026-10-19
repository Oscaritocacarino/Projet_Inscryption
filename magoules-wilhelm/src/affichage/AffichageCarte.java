package affichage;

import jeu.Card;

public class AffichageCarte {

    public static String[] lignes(Card carte, String label) {
        if (carte == null)    return lignesVide(label);
        if (carte.isAnimal()) return lignesAnimal(carte);
        return lignesObstacle(carte);
    }

    private static String[] lignesAnimal(Card carte) {
        String[] pouvoirs = construirePouvoirs(carte);

        return new String[]{
                "*-----------*",
                String.format("| %-9s |", tronquer(carte.getNom(), 9)),
                "|-----------|",
                String.format("|PV:%-2d At:%-2d|", carte.getPvActuels(), carte.getAttaque()),
                String.format("|Sa:%-2d Os:%-2d|", carte.getSang(), carte.getOs()),
                String.format("| %-9s |", pouvoirs[0]),
                String.format("| %-9s |", pouvoirs[1]),
                String.format("| %-9s |", pouvoirs[2]),
                "*-----------*"
        };
    }

    private static String[] construirePouvoirs(Card carte) {
        java.util.List<String> liste = new java.util.ArrayList<>();

        if (carte.getVolant())    liste.add("Volant");
        if (carte.getPiquant())   liste.add("Piquant");
        if (carte.getVenimeux())  liste.add("Venimeux");
        if (carte.getCoureur())   liste.add("Coureur");
        if (carte.getCroissance()) liste.add("Croissanc");  // 9 chars max sinon ca rentre pas
        if (carte.getInvincible()) liste.add("Invincib.");
        if (carte.getPuant())     liste.add("Puant");

        while (liste.size() < 3) liste.add("");

        return new String[]{ liste.get(0), liste.get(1), liste.get(2) };
    }

    private static String[] lignesObstacle(Card carte) {
        return new String[]{
                "*-----------*",
                String.format("| %-9s |", tronquer(carte.getNom(), 9)),
                "|-----------|",
                String.format("| PV: %-5d |", carte.getPvActuels()),
                "| Obstacle  |",
                "|           |",
                "|           |",
                "|           |",
                "*-----------*"
        };
    }

    private static String[] lignesVide(String label) {
        return new String[]{
                "*************",
                "*           *",
                "*           *",
                "*           *",
                String.format("*%s*", centrer(label, 11)),
                "*           *",
                "*           *",
                "*           *",
                "*************"
        };
    }

    private static String tronquer(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max) : s;
    }

    private static String centrer(String s, int largeur) {
        if (s == null) s = "";
        int pad    = largeur - s.length();
        int gauche = pad / 2;
        int droite = pad - gauche;
        return " ".repeat(Math.max(gauche, 0)) + s + " ".repeat(Math.max(droite, 0));
    }
}