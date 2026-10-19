package jeu;

public class CartesDeJeu {

    public enum Type { ANIMAL, OBSTACLE }

    private final String m_nom;
    private final int    m_pv;
    private final Type   m_type;

    private final int     m_damage;
    private final int     m_sang;
    private final int     m_os;
    private  boolean m_volant;
    private  boolean m_piquant;
    private  boolean m_venimeux;
    private  boolean m_coureur;
    private  boolean m_invincible;
    private  boolean m_croissance;
    private  boolean m_puant;

    public CartesDeJeu(String nom, int damage, int pv, int sang, int os,
                 boolean volant, boolean piquant, boolean venimeux, boolean coureur,
                 boolean invincible, boolean croissance, boolean puant) {
        this.m_type       = Type.ANIMAL;
        this.m_nom        = nom;
        this.m_pv         = pv;
        this.m_damage     = damage;
        this.m_sang       = sang;
        this.m_os         = os;
        this.m_volant     = volant;
        this.m_piquant    = piquant;
        this.m_venimeux   = venimeux;
        this.m_coureur    = coureur;
        this.m_invincible = invincible;
        this.m_croissance = croissance;
        this.m_puant      = puant;
    }

    public CartesDeJeu(String nom, int pv) {
        this.m_type       = Type.OBSTACLE;
        this.m_nom        = nom;
        this.m_pv         = pv;
        this.m_damage     = 0;
        this.m_sang       = 0;
        this.m_os         = 0;
        this.m_volant     = false;
        this.m_piquant    = false;
        this.m_venimeux   = false;
        this.m_coureur    = false;
        this.m_invincible = false;
        this.m_croissance = false;
        this.m_puant      = false;
    }

    public String  getNom()       { return m_nom; }
    public int     getPv()        { return m_pv; }
    public boolean isAnimal()     { return m_type == Type.ANIMAL; }
    public boolean isObstacle()   { return m_type == Type.OBSTACLE; }

    public int     getDamage()    { return m_damage; }
    public int     getSang()      { return m_sang; }
    public int     getOs()        { return m_os; }
    public boolean getVolant()    { return m_volant; }
    public boolean getPiquant()   { return m_piquant; }
    public boolean getVenimeux()  { return m_venimeux; }
    public boolean getCoureur()   { return m_coureur; }
    public boolean getInvincible(){ return m_invincible; }
    public boolean getCroissance(){ return m_croissance; }
    public boolean getPuant()     { return m_puant; }
    public void setPouvoirs(Boolean volant, Boolean piquant, Boolean venimeux, Boolean coureur , Boolean invincible, Boolean croissance, Boolean puant){
        this.m_volant = this.m_volant | volant;
        this.m_piquant = this.m_piquant | piquant;
        this.m_venimeux = this.m_venimeux | venimeux;
        this.m_coureur = this.m_coureur | coureur;
        this.m_invincible = this.m_invincible | invincible;
        this.m_croissance = this.m_croissance | croissance;
        this.m_puant = this.m_puant | puant;
    }
}
