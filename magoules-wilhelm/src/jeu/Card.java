package jeu;

import affichage.AffichageJeu;

import java.util.ArrayList;
import java.util.Optional;

public class Card {

    private final CartesDeJeu m_carte;
    private int m_pvActuels;

    public Card(CartesDeJeu carte) {
        this.m_carte     = carte;
        this.m_pvActuels = carte.getPv();
    }

    public boolean isAnimal()         { return m_carte.isAnimal(); }
//    public boolean isObstacle()       { return m_carte.isObstacle(); }

    public CartesDeJeu getCarte()     { return m_carte; }

    public String  getNom()           { return m_carte.getNom(); }
    public int     getPvActuels()     { return m_pvActuels; }

    public void    subirDegats(int d) { m_pvActuels -= d; }
    public boolean estMorte()         { return m_pvActuels <= 0; }

    public int     getAttaque()       { return m_carte.getDamage(); }
    public int     getSang()          { return m_carte.getSang(); }
    public int     getOs()            { return m_carte.getOs(); }
    public boolean getVolant()        { return m_carte.getVolant(); }
    public boolean getPiquant()       { return m_carte.getPiquant(); }
    public boolean getVenimeux()      { return m_carte.getVenimeux(); }
    public boolean getCoureur()       { return m_carte.getCoureur(); }
    public boolean getInvincible()    { return m_carte.getInvincible(); }
    public boolean getCroissance()    { return m_carte.getCroissance(); }
    public boolean getPuant()         { return m_carte.getPuant(); }
    public boolean getAPouvoirs(){ return this.getCoureur() || this.getInvincible() || this.getPuant() || this.getPiquant() || this.getVenimeux() || this.getCroissance() || this.getVolant();}
    public ArrayList<String> getListePouvoirs(){
        ArrayList<String> liste = new ArrayList<String>();
        if(this.getVolant()){
            liste.add("[Volant]");
        }
        if(this.getVenimeux()){
            liste.add("[Venimeux]");
        }
        if(this.getCroissance()){
            liste.add("[Croissance]");
        }
        if(this.getPiquant()){
            liste.add("[Piquant]");
        }
        if(this.getPuant()){
            liste.add("[Puant]");
        }
        if(this.getInvincible()){
            liste.add("[Invincible]");
        }
        if(this.getCoureur()){
            liste.add("[Coureur]");
        }
        return liste;
    }
    public boolean isSacrifiable()    { return isAnimal(); }
    public Boolean[] getPouvoirsBool(){
        Boolean[] pouvoirs = {false, false, false, false, false, false, false};

        if(this.getVolant()){
            pouvoirs[0] = true;
        }
        if(this.getVenimeux()){
            pouvoirs[2] = true;
        }
        if(this.getCroissance()){
            pouvoirs[5] = true;
        }
        if(this.getPiquant()){
            pouvoirs[1] = true;
        }
        if(this.getPuant()){
            pouvoirs[6] = true;
        }
        if(this.getInvincible()){
            pouvoirs[4] = true;
        }
        if(this.getCoureur()){
            pouvoirs[3] = true;
        }
    return pouvoirs;
    }
    public void setPouvoirs(Boolean volant, Boolean piquant, Boolean venimeux, Boolean coureur , Boolean invincible, Boolean croissance, Boolean puant){
        this.m_carte.setPouvoirs(volant, piquant, venimeux, coureur, invincible, croissance, puant);
    }

    public int attaque(Optional<Card> cible, AffichageJeu affichage) {
        int dmg = this.getAttaque();

        if (cible.isPresent()) {
            Card c = cible.get();

            if (c.getPuant()) dmg -= 1;
            if (dmg < 0) dmg = 0;
            if (this.getVolant()) {
                if(dmg>0){
                    affichage.degatsScore(this.getNom(), dmg);
                }
                return dmg; //attaque le score directement
            } else {
                c.subirDegats(dmg);
                if(dmg > 0){
                    affichage.degatsCarte(this.getNom(), dmg, c.getNom());
                    if (c.getPiquant())   {
                        this.subirDegats(1);
                        affichage.degatsCarte(c.getNom(), 1, this.getNom());
                    }
                    if (this.getVenimeux()) c.subirDegats(c.getPvActuels());
                }
            }
            return 0;
        }
        if (dmg > 0){
            affichage.degatsScore(this.getNom(), dmg);
        }
        return dmg; //si pas de cible

    }

    public void activerPouvoirs(PlateauDeJeu plateau, int i) {
        if (this.getCoureur() && i < 3 && plateau.getBoardPlayer()[i + 1].isEmpty()) {
            plateau.poserCarteJoueur(this, i + 1);
            plateau.retirerCarteJoueur(i);
        }
        if (this.getCroissance()) {
            plateau.retirerCarteJoueur(i);
            plateau.poserCarteJoueur(new Card(new CartesDeJeuFactory().createLoup()), i);
        }
    }

    @Override
    public boolean equals(Object object){
        if(this != null && this.getClass() == object.getClass()){
            return true;
        }
        else return false;
    }
}