package affichage;

import jeu.Card;
import jeu.Pioche;

import java.util.ArrayList;
import java.util.Scanner;

public class AffichageAction {

    private final Scanner m_scanner;

    public AffichageAction() {
        this.m_scanner = new Scanner(System.in);
    }



    public String choixTour() {
        return "1. Piocher une carte\n 2. Poser une carte\n 3. Terminer le tour";
    }

    public int choixAction() {

        int choix = -1;
        while (choix != 1 && choix != 2 && choix != 3) {
            System.out.println("Que voulez-vous faire ?");
            System.out.println();
            System.out.println("  1. Piocher une carte");
            System.out.println("  2. Poser une carte");
            System.out.println("  3. Terminer le tour");
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix != 1 && choix != 2 && choix != 3) {
                    System.out.println("Saisie invalide : entrez 1, 2 ou 3.");
                }
            } else {
                System.out.println("Saisie invalide : entrez un nombre (1, 2 ou 3).");
                m_scanner.next();
            }
        }
        return choix;
    }

    public int choixRecompense(Card[] recompenses) {
        int choix = -1;
        System.out.println("Choisissez une carte à ajouter à votre pioche :");
        System.out.println("  1. " + recompenses[0].getNom());
        System.out.println("  2. " + recompenses[1].getNom());
        while (choix != 1 && choix != 2) {
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix != 1 && choix != 2) {
                    System.out.println("Saisie invalide : entrez 1 ou 2.");
                }
            } else {
                System.out.println("Saisie invalide : entrez 1 ou 2.");
                m_scanner.next();
            }
        }
        return choix - 1;
    }

    public int choixSacrifice() {
        int choix = -1;
        while (choix < 1 || choix > 4) {
            System.out.println("Choisissez l'emplacement de la carte à sacrifier (1-4) :");
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix < 1 || choix > 4) {
                    System.out.println("Saisie invalide : entrez un nombre entre 1 et 4.");
                }
            } else {
                System.out.println("Saisie invalide : entrez un nombre entre 1 et 4.");
                m_scanner.next();
            }
        }
        return choix - 1;
    }

    public int choixJeuMain(int taille) {
        int choix = -1;
        while (choix < 0 || choix > taille) {
            System.out.println("Choisissez le numéro de la carte à jouer (0 pour annuler):");
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix < 0 || choix > taille) {
                    System.out.println("Saisie invalide : entrez un numéro valide (0 pour annuler) .");
                }
            } else {
                System.out.println("Saisie invalide : entrez un nombre (0 pour annuler).");
                m_scanner.next();
            }
        }
        return choix - 1;
    }

    public int choixJeuPlateau() {
        int choix = -1;
        while (choix < 0 || choix > 4) {
            System.out.println("Choisissez l'emplacement sur le plateau (1-4, 0 pour annuler) :");
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix < 0 || choix > 4) {
                    System.out.println("Saisie invalide : entrez un nombre entre 1 et 4, ou 0 pour annuler.");
                }
            } else {
                System.out.println("Saisie invalide : entrez un nombre entre 1 et 4, ou 0 pour annuler .");
                m_scanner.next();
            }
        }
        return choix - 1;
    }
    public int choixSacrificePierre(ArrayList<Card> cartes){
        System.out.println("Veuillez choisir une carte à sacrifier parmis les suivantes : " );
        int choix = -1;
        for(int i = 0 ; i < cartes.size() ; i++){
            System.out.print(i +1  + " : " + cartes.get(i).getNom());
            for(int j = 0 ; j<cartes.get(i).getListePouvoirs().size() ; j++){
                System.out.print(cartes.get(i).getListePouvoirs().get(j) + " ");
            }
            System.out.println("\n");
        }
        System.out.print("> ");
        if (m_scanner.hasNextInt()) {
            choix = m_scanner.nextInt();
            if(choix <= 0 || choix > cartes.size() ){
                System.out.println("Saisie invalide, veuillez réessayer");
            }
        }else{
            System.out.println("Saisie invalide, veuillez réessayer");
            m_scanner.next();
        }
        return choix - 1;
    }
    public int choixBufferCarte(Pioche pioche){
        System.out.println("Veuillez quelle carte va récupérer ses pouvoirs parmis les suivantes : \n" );
        int choix = -1;
        for(int i = 0 ; i < pioche.getPioche().size() ; i++){
            System.out.print(i +1  + " : " + pioche.getPioche().get(i).getNom());
            for(int j = 0 ; j<pioche.getPioche().get(i).getListePouvoirs().size() ; j++){
                System.out.print(pioche.getPioche().get(i).getListePouvoirs().get(j) + " ");
            }
            System.out.println("\n");
        }
        if (m_scanner.hasNextInt()) {
            choix = m_scanner.nextInt();
            if(choix <= 0 || choix > pioche.getPioche().size() ){
                System.out.println("Saisie invalide, veuillez réessayer");
            }
        }else{
            System.out.println("Saisie invalide, veuillez réessayer");
            m_scanner.next();
        }
        return choix - 1;
    }

    public int choixJeuPlateau2() {
        int choix = -1;
        while (choix < 0 || choix > 4) {
            System.out.println("Veuillez choisir un emplacement valide (1-4, 0 pour annuler) :");
            System.out.print("> ");
            if (m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                if (choix < 0 || choix > 4) {
                    System.out.println("Saisie invalide : entrez un nombre entre 0 et 4.");
                }
            } else {
                System.out.println("Saisie invalide : entrez un nombre entre 0 et 4.");
                m_scanner.next();
            }
        }
        return choix - 1;
    }
}