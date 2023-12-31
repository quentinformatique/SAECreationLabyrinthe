/* 
 * Sommet.java				18 avril 2023
 * Iut de Rodez , pas de copyright
 */

package representation;

/**
 * Représentation d'un sommet d'un graphe
 * Un sommet a une position en x et y et des marque
 * @author Costes Quentin
 * @author de Saint Palais Francois
 * @author Denamiel Clément 
 * @author Descriaud Lucas
 */
public class Sommet {
    
    /** coordonnée X de la salle dans le labyrinthe */
    private int posX;

    /** coordonnée Y de la salle dans le labyrinthe */
    private int posY;

    /** Contient les possibles marque du Sommet.  */
    private int marque;

    private boolean estParcourus;
    
    /** 
     * Indique la présence de voisins du sommet. 
     * Commence par le voisin du haut puis continue dans le sens horaire
     */
    private boolean[] voisins;


    /**
     * Constructeur de la classe sommet
     * Un sommet est valide si ses coordonnées sont dans les entier naturels
     * les paramètres x et y ne peuvent donc pas être négatifs.
     * La marque est initialisé à -1.
     * @param x position y du sommet
     * @param y position y du sommet
     * @throws IllegalArgumentException si les arguments sont invalides
     */
    public Sommet (int x, int y) {
        super();
        if (!coordonneesValides(x, y)) {
            throw new IllegalArgumentException();
        }
        posX = x;
        posY = y;
        marque = -1;
        voisins = new boolean[4]; // par defaut a false
    }
    

    /**
     * Vérifie que les coordonnées sont valides (pas de coordonnées dans les négatifs)
     * @param x position coordonnées sont positives x du sommet
     * @param y position y du sommet
     * @return true si les paramètres sont valides
     */
    private boolean coordonneesValides(int x, int y) {
        return 0 <= x && 0 <= y;
    }

    /**
     * @return la position x du sommet
     */
    public int getPosX() {
        return posX;
    }
    
    /**  
     * @return la position y du sommet
     */    
    public int getPosY() {
        return posY;
    }

    @Override
    public String toString() {
        
        return "(" + posX + "; " + posY + ") marque : "+marque;
    }

    @Override
    public int hashCode() {
        return posX * 100 + posY; 
    }

	 
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }
	
	/**
	 * @return la marque du sommet
	 */
    public int getMarque() {
        return marque;
    }
	
	/**
	 * Permet de modifier la marque d'un sommet
	 * @param argMarque nouvelle marque a appliquer a this
	 */
    public void setMarque(int argMarque) {
        this.marque = argMarque;
    }

	/**
	 * getter de l'atribut voisins
	 * @return liste des voisins
	 */
	public boolean[] getVoisins() {
        return this.voisins;
    }
    
    
    /**
     * @param voisins la nouvelle liste a attribuer
     */
    public void setVoisin(boolean[] voisins) {
        this.voisins = voisins ; 
    }

    /**
     * Met a jour le tableau des voisins
     * @param indice entier de 0 a 3 
     *        0 : haut
     *        1 : droite 
     *        2 : bas 
     *        3 : gauche
     * @param voisin true si on souhaite un voisin , false sinon 
     */
    public void setVoisin(boolean voisin, int indice) {
        if (indice < 0 || voisins.length <= indice) {
            throw new IllegalArgumentException("Erreur : Indice invalide");
        }
        this.voisins[indice] = voisin ; 
    }

    /**
     * @return la valeur de estParcourus
     */
    public boolean estParcourus() {
        return estParcourus;
    }

    /**
     * @param estParcourus modifie la valeur de estParcourus
     */
    public void setEstParcourus(boolean estParcourus) {
        this.estParcourus = estParcourus;
    }
    
    
}
