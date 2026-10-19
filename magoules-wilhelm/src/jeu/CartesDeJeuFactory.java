package jeu;

public class CartesDeJeuFactory {

    public CartesDeJeu createChat() {
        return new CartesDeJeu("Chat", 0, 1, 1, 0, false, false, false, false, true, false, false);
    }

    public CartesDeJeu createCorbeau() {
        return new CartesDeJeu("Corbeau", 2, 3, 2, 0, true, false, false, false, false, false, false);
    }

    public CartesDeJeu createCoyote() {
        return new CartesDeJeu("Coyote", 2, 1, 0, 4, false, false, false, false, false, false, false);
    }

    public CartesDeJeu createEcureuil() {
        return new CartesDeJeu("Ecureuil", 0, 1, 0, 0, false, false, false, false, false, false, false);
    }

    public CartesDeJeu createElan() {
        return new CartesDeJeu("Elan", 2, 4, 2, 0, false, false, false, true, false, false, false);
    }

    public CartesDeJeu createGrizzly() {
        return new CartesDeJeu("Grizzly", 4, 6, 3, 0, false, false, false, false, false, false, false);
    }

    public CartesDeJeu createHermine() {
        return new CartesDeJeu("Hermine", 1, 3, 1, 0, false, false, false, false, false, false, false);
    }

    public CartesDeJeu createLoup() {
        return new CartesDeJeu("Loup", 3, 2, 2, 0, false, false, false, false, false, false, false);
    }

    public CartesDeJeu createLouveteau() {
        return new CartesDeJeu("Louveteau", 1, 1, 1, 0, false, false, false, false, false, true, false);
    }

    public CartesDeJeu createMoineau() {
        return new CartesDeJeu("Moineau", 1, 2, 1, 0, true, false, false, false, false, false, false);
    }

    public CartesDeJeu createPorcepic() {
        return new CartesDeJeu("Porc-épic", 1, 2, 1, 0, false, true, false, false, false, false, false);
    }

    public CartesDeJeu createPunaise() {
        return new CartesDeJeu("Punaise", 1, 2, 0, 2, false, false, false, false, false, false, true);
    }

    public CartesDeJeu createVipere() {
        return new CartesDeJeu("Vipère", 1, 1, 2, 0, false, false, true, false, false, false, false);
    }
    public CartesDeJeu createInstantWin(){
        return  new CartesDeJeu("InstantWin",  5, 5, 0, 0, false, false, false, false, false, false, false);
    }


    public CartesDeJeu createRocher() {
        return new CartesDeJeu("Rocher", 5);
    }

    public CartesDeJeu createSapin() {
        return new CartesDeJeu("Sapin", 3);
    }
}