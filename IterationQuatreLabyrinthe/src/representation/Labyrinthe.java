package representation;

import java.lang.Math;

import org.hamcrest.Condition.Step;
import outils.OutilsTableau;
import representation.PileContigue;

/**
 * 
 * Classe représentant des Graphes étant 
 * une liste de Sommets et 
 * une liste de liste de deux sommets représentant les arcs
 * @author Quentin Costes
 * @author François de Saint Palais
 *
 */
public class Labyrinthe {

     /** Affichage du haut d'une case */
     private static final String HAUT_CASE = "+-----+";
     
     /** Mur du haut vide */
     private static final String HAUT_CASE_VIDE = "+     +";     
     
     /** Affichage des bord d'une case*/
     private static final String BORD_CASE = "|";
     
     /** Chaine vide pour l'espace a l'interieur des cases */ 
     private static final String CHAINE_VIDE = "     ";
     
     /** Affichage d'un bord vide'*/
     private static final String BORD_VIDE = " ";
     
     /** indice pour la liste des voisins du sommet corrsepondant a haut */
     private static final int HAUT = 0;
 
     /** indice pour la liste des voisins du sommet corrsepondant a droite */    
     private static final int DROITE = 1;
 
     /** indice pour la liste des voisins du sommet corrsepondant a bas */    
     private static final int BAS = 2;
 
     /** indice pour la liste des voisins du sommet corrsepondant a gauche */    
     private static final int GAUCHE = 3;
     
     /** Hauteur en ligne dans la console texte */
     private static final int HAUTEUR_CASE = 3;
     
     
     private Sommet entre;
     
     private Sommet sortie;
     

    /** liste de tous le sommets */
    private int largeur;
    
    /** liste de tous le sommets */
    private int hauteur;
 
     // TODO remettre private   
    /** */
    public Sommet[][] listeSommet;  
    
    /** lsite des arcs du graphe */
    public Sommet[][] listeArcs;
    
    /**
     * Créer un graphe composé 
     * @param largeur
     * @param hauteur
     * @throws IllegalArgumentException
     */
    public Labyrinthe(int hauteur, int largeur) {
        super();
        if (!(largeur > 0 && hauteur > 0)) {
            throw new IllegalArgumentException("largeur ou hauteur invalide");
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
        listeSommet = creerGrille(largeur, hauteur);
        entre = getListeSommet()[0][0];
        sortie = getListeSommet()[getListeSommet().length-1][getListeSommet()[0].length-1];
        listeArcs = new Sommet[0][0];
    }
    
    public Labyrinthe(int hauteur, int largeur, 
                      Sommet[][] listeSommet,
                      Sommet[][] listeArcs,
                      Sommet entree, Sommet sortie) {
        super();
        if (!(largeur > 0 && hauteur > 0)) {
            throw new IllegalArgumentException("largeur ou hauteur invalide");
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.listeSommet = listeSommet;
        this.listeArcs = listeArcs;
        this.entre = entree;
        this.sortie = sortie;
    }
    

    /**
     * permet de créer une grille carre de 0 (salle), et de -1 (murs)
     * @param largeur La largeur voulue pour la grille/ le labyrinthe
     * @param hauteur La hauteur voulue pour la grille/ le labyrinthe
     * @return grilleRetour liste de liste de sommets
     */
    private Sommet[][] creerGrille(int largeur, int hauteur) {
        
        Sommet[][] grilleRetour = new Sommet[hauteur][largeur];
        
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                grilleRetour[i][j] = new Sommet(j, i);
            }
        }

        return grilleRetour; // stub
    }
    
    /**
     * Mes des marques unique sur les sommets du labyrinthe
     */
    public void setMarqueSommet() {
        int marque;
        
        marque = 0;
        for (int i = 0; i < listeSommet.length; i++) {
            for (int j = 0; j < listeSommet[0].length; j++) {
                marque++;
                Sommet s = listeSommet[i][j];
                s.setMarque(marque);
            }
        }  
    }
    

	// TODO remettre private
    /** 
     * permet d'ajouter une arrete au graphe
     * @param sommet1 1 de l'arrete
     * @param sommet2 2 de l'arrete
     * @throws IllegalArgumentException si les sommet ne sont pas dans le graphe
     *                                     ou si l'arrete existe deja
     */
    public void ajouterArrete(Sommet sommet1, Sommet sommet2) {
        boolean sommet1Valide,
                sommet2Valide;
        
        sommet1Valide = estPresent(sommet1);
        sommet2Valide = estPresent(sommet2);
        
        if (!sommet1Valide || !sommet2Valide) {
            throw new IllegalArgumentException("les sommets données ne sont pas dans le graphe");
        }; 
        
        Sommet[] listTemp1 = {sommet1, sommet2};
        Sommet[] listTemp2 = {sommet2, sommet1};
        if (this.listeArcs != null) {
            for (int i = 0; i < listeArcs.length; i++) {
                if (listeArcs[i] == listTemp1 || listeArcs[i] == listTemp2) {
                    throw new IllegalArgumentException("l'arc existe deja");
                }
            }    
        }
        
        
        if (sommet1.getPosY() > sommet2.getPosY()) {
            sommet1.setVoisin(true, 0);
            sommet2.setVoisin(true, 2);
        } 
        if (sommet1.getPosY() < sommet2.getPosY()) {
            sommet1.setVoisin(true, 2);
            sommet2.setVoisin(true, 0);
        }
        if (sommet1.getPosX() > sommet2.getPosX()) {
            sommet1.setVoisin(true, 3);
            sommet2.setVoisin(true, 1);
        }
        if (sommet1.getPosX() < sommet2.getPosX()) {
            sommet1.setVoisin(true, 1);
            sommet2.setVoisin(true, 3);
        }
        
        if (this.listeArcs != null) {
            Sommet listeArcs2[][] = new Sommet[this.listeArcs.length + 1][1];
            for (int i = 0; i < this.listeArcs.length; i++) {
                listeArcs2[i] = this.listeArcs[i];
            }    
            
            Sommet[] listTemp = {sommet1, sommet2};
            listeArcs2[listeArcs2.length - 1] = listTemp;
            
            this.listeArcs = listeArcs2;
        } else {
            Sommet listeArcs2[][] = new Sommet[1][1];
            Sommet[] listTemp = {sommet1, sommet2};
            listeArcs2[0] = listTemp;
            
            this.listeArcs = listeArcs2;
        }     
    }
    
    /**
     * Vérifie qu'un sommet ce trouve dans un graphe
     * @return true si sommet est dans graphe false sinon
     */
    public boolean estPresent(Sommet sommet) {
        for (Sommet[] sommets : listeSommet) {
            for (Sommet sTester : sommets) {
                if (sommet.equals(sTester)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Construction du labyrinthe p création ascendante de chaines
     */
    public void chaineAscendante() {
        boolean arreteCreer;
        Sommet sommetTest;
        int[][] coordonDejaGenerees = new int[hauteur * largeur][2];
        
        setMarqueSommet();
        
        do {
        	int indiceXSommetRandom = (int) Math.random() * listeSommet.length;
        	int indiceYSommetRandom = (int) Math.random() * listeSommet[0].length;
						
			arreteCreer = false;
            Sommet sommetChoisie = listeSommet[indiceXSommetRandom][indiceYSommetRandom];
            for (int i = 0; i < listeSommet.length && !arreteCreer; i++) {
                for (int j = 0; j < listeSommet[0].length && !arreteCreer; j++) {
                    sommetTest = listeSommet[i][j];
                    if (   sommetAdjacent(sommetChoisie, sommetTest) 
                        && sommetChoisie.getMarque()!= sommetTest.getMarque()) {
                        ajouterArrete(sommetTest, sommetChoisie);
                        ajouterArrete(sommetChoisie, sommetTest);
                        fusionnerMarques(sommetChoisie, sommetTest);
                        arreteCreer = true;
                    }
                }
            }
        } while (!ontTousLaMemeMarque());
    }
    
    
    /**
     * Vérifie si tous les sommets de la grille on la même marque.
     * @return true s'ils ont tous la même et false sinon 
     */
    public boolean ontTousLaMemeMarque() {
        int marque = listeSommet[0][0].getMarque();
        for (int i = 0; i < listeSommet.length; i++) {
            for (int j = 0; j < listeSommet[0].length; j++) {
                    if (listeSommet[i][j].getMarque() != marque) {
                        return false;
                    }
                }
            }
        return true;
    }
    
    /**
     * @return true si les deux sommet sont adjacent dans la grille false sinon.
     */
    private static boolean sommetAdjacent(Sommet sChoisie, Sommet sTeste) {
        int sChoisiX = sChoisie.getPosX();
        int sChoisiY = sChoisie.getPosY();
        int sTesteX = sTeste.getPosX();
        int sTesteY = sTeste.getPosY() ;
        return   (sChoisiX == sTesteX && (   sChoisiY - 1 == sTesteY
                                          || sChoisiY + 1 == sTesteY)
                  ) 
              || (sChoisiY == sTesteY && (   sChoisiX - 1 == sTesteX
                                          || sChoisiX + 1 == sTesteX)
                  );
    }
    
    /** 
     * Fusionne les marques de tous les sommets connexes aux sommet s1 et s2
     * @param s1 
     * @param s2
     */
    public void fusionnerMarques(Sommet sommetEcrasant, Sommet sommetEcrase) {
        int marqueEcrasante = sommetEcrasant.getMarque();
        int marqueEcrasee = sommetEcrase.getMarque();
        
        
        for (Sommet[] ligneSommet : listeSommet) {
            for (Sommet sommet : ligneSommet) {
                if (sommet.getMarque() == marqueEcrasee) {
                    sommet.setMarque(marqueEcrasante);
                }
            }
        }
    } 
    
    
    /**
     * Construction par backtracking avec une pile
     * en modifiant la liste des arcs via l'appel de la 
     * méthode ajouterArc
     */
    public void constructionBacktracking() {
        boolean[] sommetsVisites = new boolean[largeur * hauteur];
        PileContigue pileSommets = new PileContigue();    
        
        int indiceXSommetRandom = (int)(Math.random() * (listeSommet.length));
        int indiceYSommetRandom = (int)(Math.random() * (listeSommet[0].length));   
        setMarqueSommet();
    
        //On empile un sommet au hasard
        pileSommets.empiler(listeSommet[indiceXSommetRandom][indiceYSommetRandom]);
        
        Sommet sommetPile = (Sommet) pileSommets.sommet();
        //On marque le sommet comme parcourue
        sommetsVisites[sommetPile.getMarque()-1] = true;
        
        
        while (!pileSommets.estVide()) {
            Sommet sommetCourant = (Sommet) pileSommets.sommet();
            Sommet[] listeVoisins = getSommetsVoisins(sommetCourant);
            
            
            boolean tousParcourus = true;
            for (int i = 0; i < listeVoisins.length - 1 && tousParcourus; i++ ) {
                if (!sommetsVisites[listeVoisins[i].getMarque()]) {
                    tousParcourus = false;
                }
            }
            
            
            if (tousParcourus) {
                pileSommets.depiler();
                System.out.println(pileSommets);
                sommetCourant =(Sommet) pileSommets.sommet();
            } else {
                int indiceVoisinRandom = (int)(Math.random() * (listeVoisins.length));
                Sommet voisinRandom = listeVoisins[indiceVoisinRandom];
                ajouterArrete(sommetCourant, voisinRandom);
                pileSommets.empiler(voisinRandom);
                sommetsVisites[voisinRandom.getMarque()-1] = true;
            }
            sommetsVisites[sommetCourant.getMarque()-1] = true;
            System.out.println(pileSommets.sommet());
            for (int i = 0; i < sommetsVisites.length; i++) {
                System.out.print(sommetsVisites[i] + " | ");
            }
            System.out.println("\n");
        }
        
    }
    
    /** 
     * permet de récupérer la liste des voisins d'un sommet
     * @param current le sommet dont on veut avoir les voisins
     */
    private Sommet[] getSommetsVoisins(Sommet current) {
        int indice;
        Sommet[] retour = new Sommet[4];
        
        indice = 0;
        for (Sommet[] liste : listeSommet) {
            for (Sommet s : liste) {
                if (sommetAdjacent(current, s)) {
                    retour[indice] = s;
                    indice++;
                }
            }
        }
        
        if (indice != 4) {
            Sommet[] nouveauTableau = new Sommet[indice];
            for (int i = 0; i < indice; i++) {
                nouveauTableau[i] = retour[i];
            }
            retour = nouveauTableau;
        }
        return retour;
    }    
    
    
    /** 
     * effectue le parcours en main droite (profondeur)
     * a partir du sommet defini comme entree
     * @return le parcours en profondeur a partir de l'entree
     */
    public Sommet[] parcoursMainDroite() {
        
        Sommet[] sommetsParcourus = new Sommet[largeur * hauteur]; 
        int indice;
        
        PileContigue pileSommets = new PileContigue();
        pileSommets.empiler(entre);
        
        indice = 0;
        while (!pileSommets.estVide()) {
            Sommet sommetCourant = (Sommet) pileSommets.sommet();
            pileSommets.depiler();
            
            if (!OutilsTableau.contient(sommetsParcourus, sommetCourant)) {
                sommetsParcourus[indice] = sommetCourant;
                indice++;
                
                if (sommetCourant.equals(sortie)) {
                    return sommetsParcourus;
                } else {
                    for (Sommet voisin : getSommetsVoisins(sommetCourant)) {
                        if (!OutilsTableau.contient(sommetsParcourus, voisin)) {
							pileSommets.empiler(voisin);
						}
                    }
                }
            }
        }
        
        return sommetsParcourus;
    }
    
    
    @Override
    public String toString() {
        String affichage;
        affichage = "";
        
        for (int hauteur = 0 ; hauteur < this.hauteur ; hauteur++){ 
            for (int j = 0 ; j < this.largeur ; j++ ) {
                if (this.listeSommet[hauteur][j].getVoisins()[HAUT]) {
                    affichage += HAUT_CASE_VIDE; 
                }
                else {
                    affichage += HAUT_CASE;
                }  
            }    
            for (int i = 0; i < HAUTEUR_CASE ; i++) {
                affichage += "\n";        
                   for (int j = 0; j < this.largeur; j++) {
                    if (this.listeSommet[hauteur][j].getVoisins()[GAUCHE]) {
                        affichage += BORD_VIDE; 
                    } else {
                        affichage += BORD_CASE;
                    }
                
                       affichage += CHAINE_VIDE;
                       
                       if (this.listeSommet[hauteur][j].getVoisins()[DROITE]){
                        affichage += BORD_VIDE;   
                    } else {
                        affichage += BORD_CASE; 
                    }
                       
                }
            }
            affichage += "\n";
            for (int j = 0 ; j < this.largeur ; j++ ) {
                if (this.listeSommet[hauteur][j].getVoisins()[BAS]){
                        affichage += HAUT_CASE_VIDE;   
                } else {
                    affichage += HAUT_CASE; 
                }     
            }
            affichage += "\n";
        }
        return affichage;
    }

    /**
     * @return la valeur de listeSommet
     */
    public Sommet[][] getListeSommet() {
        return listeSommet;
    }

    /**
     * @return la valeur de listeArcs
     */
    public Sommet[][] getListeArcs() {
        return listeArcs;
    } 
    
        /**
     * @return la valeur de entre
     */
    public Sommet getEntre() {
        return entre;
    }

    /**
     * @param entre modifie la valeur de entre
     */
    public void setEntre(Sommet entre) {
        this.entre = entre;
    }

    /**
     * @return la valeur de sortie
     */
    public Sommet getSortie() {
        return sortie;
    }

    /**
     * @param sortie modifie la valeur de sortie
     */
    public void setSortie(Sommet sortie) {
        this.sortie = sortie;
    }
    
    /** */
    public int getHauteur() {
        return hauteur;
    }
    
    /** */
    public int getLargeur() {
        return largeur;
    }

}