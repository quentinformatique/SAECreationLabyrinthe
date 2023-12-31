package representation;

import outils.OutilsListe;
import outils.OutilsMatrice;

/**
 * 
 * Classe représentant des Graphes étant 
 * une liste de Sommets et 
 * une liste de liste de deux sommets représentant les arcs
 * @author Quentin Costes
 * @author François de Saint Palais
 *
 */
public class Graphe {

    /** liste de tous le sommets */
    private Sommet[] listeSommet;
    
    /** liste de tous le sommets */
    private Sommet[][] listeArcs;
        
    /**
     * Créer un graphe composé 
     * @param listeSommets
     * @param listeArcs
     */
	public Graphe(Sommet[] listeSommets, Sommet[][] listeArcs) {
        super();
        
        try {
            if (estValide(listeSommets,listeArcs)) {
                this.listeSommet = listeSommets;
                this.listeArcs = listeArcs;            
            }     
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Erreur - Paramètres du constructeur invalide.\n\tRaison : "
                    + e.getMessage());
        }
    }
    
	/**
	 * Fonction qui vérifie la validité du couple sommet - arcs, 
	 * pour créer un graphe valide.
	 * @param listeSommets
	 * @param listeArcs
	 * @return
	 */
	protected static boolean estValide(Sommet[] listeSommets, Sommet[][] listeArcs) 
	throws IllegalArgumentException{
        
        /* Dans notre situation, un graphe doit forcement avoir un arc et un sommet*/
        if (   listeSommets == null  || listeArcs == null 
            || listeArcs.length == 0 || listeArcs[0].length == 0 
            || listeSommets.length == 0) {
            throw new IllegalArgumentException("Une des liste est vide");
        }
        
        /* Vérifie que tous les sommets des arcs existent */
        for (int j = 0; j < listeArcs.length; j++) {
            if (   !OutilsListe.contient(listeSommets, listeArcs[j][0]) 
                || !OutilsListe.contient(listeSommets, listeArcs[j][1])) {
                throw new IllegalArgumentException("Un arcs pointe un sommet inexistant");
            }
        }
        
        /* Le graphe n'a pas de sommet en double */
        for (int i = 0; i < listeSommets.length; i++) {
            for (int j = 0; j < listeSommets.length; j++) {
                if (i != j && listeSommets[i].equals(listeSommets[j])) {
                    throw new IllegalArgumentException("Le graphe contient de fois même sommet");
                }
            }
        }
        return true;
    }
	
   /**
	 * renvoie le nombre d'arcs du graphe
	 * @return int nombre d'arcs
	 */
    public int getNbArretes() {
        return listeArcs.length;
    }
    
    /**
     * Renvoie le nombre de sommets du graphe
     * @return int nombre de sommet
     */
    public int getNbSommets() {
        return listeSommet.length;
    }
    
    /**
     * Permet de verifier la présence d'un arc entre deux sommets
     * dans les deux sens.
     * @param sommet1
     * @param sommet2
     * @return true si un arc existe entre les deux sommets, false sinon
     */
    public boolean sontRelies (Sommet sommet1, Sommet sommet2) {
        return    existeArcEntre(sommet1, sommet2) 
               || existeArcEntre(sommet2, sommet1);
    }
    
    public boolean existeArcEntre(Sommet sommet1, Sommet sommet2) {
        for (int i = 0; i < listeArcs.length; i++) {
            if (listeArcs[i][0].equals(sommet1) && listeArcs[i][1].equals(sommet2)) {
                return true;
            }  
        }
        return false;
    }
    
    
    /**
     *@return une matrice booléenne 
     */
    public boolean[][] toMatriceAdjacence() {
        boolean[][] matrice = new boolean[this.listeSommet.length][this.listeSommet.length];
        for (int i = 0; i < listeSommet.length; i++) {
            for (int j = 0; j < listeSommet.length; j++) {
                matrice[i][j] = existeArcEntre(listeSommet[i],listeSommet[j]);
            }
        }
        
        return matrice;
    }
    
    /**
     * @return true si tous les sommet ce pointe eux même false sinon
     */
    public boolean estReflexif() {
        for (Sommet sommet : listeSommet) {
            if (!sontRelies(sommet, sommet)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * @return true s'il n'existe aucune boucle sur chacun des sommets false sinon
     */
    public boolean estIrreflexif() {
        for (int i = 0; i < listeArcs.length; i++) {
            if (listeArcs[i][0] == (listeArcs[i][1])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @return true si le graphe contient au moins une boucle et false sinon
     */
    public boolean contienCircuit() {
        boolean[][] matrice = this.toMatriceAdjacence();
        boolean continuer;
        
        continuer = false;
        do {
            for (int i = 0; i < matrice.length; i++) {
             	if (   OutilsMatrice.ligneVide(matrice,i) 
             	    || OutilsMatrice.colonneVide(matrice,i)) {
             	    
                     OutilsMatrice.supLigneColonne(matrice, i);
                 }  
        	}
            //TODO Finir
            continuer = !OutilsMatrice.estNul(matrice);
        } while (continuer);
        
        return continuer;
//        return false;
    }
    
    /** 
     * permet d'ajouter une arrete au graphe
     * @param extreemitée 1 de l'arrete
     * @param extremitée 2 de l'arrete
     * @throws IllegalArgumentException si les sommet ne sont pas dans le graphe
     * 									ou si l'arrete existe deja
     */
    public void ajouterArrete(Sommet sommet1, Sommet sommet2) {
        boolean sommet1Valide,
        		sommet2Valide;
        
        sommet1Valide = false;
        sommet2Valide = false;
        for (int i = 0; i < listeSommet.length; i++) {
            if (sommet1.equals(listeSommet[i])) {
                sommet1Valide = true;
            } else if (sommet2.equals(listeSommet[i])) {
                sommet2Valide = true;
            }        
        }
        
        if (!sommet1Valide || !sommet2Valide) {
            throw new IllegalArgumentException("les sommets données ne sont pas dans le graphe");
        }; 
          
        // TODO verifier que l'arrete n'existe pas deja
   		Sommet listeArcs2[][] = new Sommet[this.listeArcs.length + 1][1];
   		
   		for (int i = 0; i < this.listeArcs.length; i++) {
            listeArcs2[i] = this.listeArcs[i];
        }
       	
       	Sommet[] listTemp = {sommet1, sommet2};
        listeArcs2[listeArcs2.length - 1] = listTemp; 			
		this.listeArcs = listeArcs2;
		      
    }
    
    @Override
    public String toString() {
       	 
       	String res = "";
       	Sommet courant, aTester;
       	
       	for (int i = 0; i < listeSommet.length; i++) {
            courant = listeSommet[i];
            for (int j = 0; j < listeSommet.length; j++) {
                aTester = listeSommet[j];
                
                if (sontRelies(courant, aTester)) {
                    res += courant.toString() + "-" + aTester.toString() + "\n";
                }
            }
        }
       	 
        return res;
        /*String toString;
        toString = "Sommets : (" ;
        for (int i = 0 ;  i < getNbSommets() ; i++){
            toString += listeSommet[i].toString();
        }
        toString += ")\nArcs : (";
        for (int i = 0 ; i < getNbArretes() - 1 ; i++){
            toString += "(" + listeArcs[i][0] + " -> " + listeArcs[i][1]+ "), ";
        }
        toString += "(" + listeArcs[getNbArretes()-1][0] + " -> " + listeArcs[getNbArretes()-1][1]+ "))";
        return toString ;*/ 
    }
    
}