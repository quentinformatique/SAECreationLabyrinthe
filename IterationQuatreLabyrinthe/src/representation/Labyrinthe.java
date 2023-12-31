package representation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import outils.OutilsTableau;

/**
 * 
 * Classe représentant des Graphes étant 
 * une liste de Sommets et 
 * une liste de liste de deux sommets représentant les arcs
 * @author Quentin Costes
 * @author François de Saint Palais
 * @author Denamiel Clement
 * @author Descriaud Lucas
 *
 */
public class Labyrinthe {

	/** Affichage du haut d'une case */
	protected static final String HAUT_CASE = "+-----";

	/** Mur du haut vide */
	protected static final String HAUT_CASE_VIDE = "+     ";     

	/** Affichage des bord d'une case*/
	protected static final String BORD_CASE = "|";

	/** Affichage d'un bord vide'*/
	protected static final String BORD_VIDE = " ";

	/** Chaine vide pour l'espace a l'interieur des cases */ 
	protected static final String CHAINE_VIDE = "     ";

	/** Chaine vide pour l'espace a l'interieur 
	 * des cases avec sa marque si c'est une unitée
	 */ 
	protected static final String CHAINE_VIDE_MARQUE_UNITE = "  %d  ";

	/** Chaine vide pour l'espace a l'interieur 
	 * des cases avec sa marque si c'est une dizaine 
	 */ 
	protected static final String CHAINE_VIDE_MARQUE_DIZAINE = " %d  ";

	/** Chaine vide pour l'espace a l'interieur des 
	 * cases avec sa marque si c'est une centaine 
	 */ 
	protected static final String CHAINE_VIDE_MARQUE_CENTAINE = " %d ";


	/** indice pour la liste des voisins du sommet correspondant a haut */
	protected static final int HAUT = 0;

	/** indice pour la liste des voisins du sommet correspondant a droite */    
	protected static final int DROITE = 1;

	/** indice pour la liste des voisins du sommet correspondant a bas */    
	protected static final int BAS = 2;

	/** indice pour la liste des voisins du sommet correspondant a gauche */    
	protected static final int GAUCHE = 3;

	/** Hauteur en ligne dans la console texte */
	protected static final int HAUTEUR_CASE = 3;

	/** entree du labyrinthe */
	protected Sommet entree;

	/** sortie du labyrinthe */
	protected Sommet sortie;


	/** largeur du labyrinthe */
	private int largeur;

	/** hauteur du labyrinthe */
	private int hauteur;

	/** liste des sommets du graphe */
	private Sommet[][] listeSommet;  

	/** liste des arcs du graphe */
	private Sommet[][] listeArcs;

	/**
	 * Constructeur de  d'un objet de type Labyrinthe 
	 * a partir d'une hauteur et d'une largeur
	 * @param largeur largeur du labyrinthe a creer
	 * @param hauteur hauteur du labyrinthe a creer 
	 * @throws IllegalArgumentException hauteur ou largeur negative 
	 */
	public Labyrinthe(int hauteur, int largeur) {
		super();
		if (!(largeur > 1 && hauteur > 1)) {
			throw new IllegalArgumentException("largeur ou hauteur invalide");
		}
		this.largeur = largeur;
		this.hauteur = hauteur;
		listeArcs = new Sommet[0][0];

		genererLabirynthe();

		entree = listeSommet[0][0];
		sortie = listeSommet[hauteur - 1][largeur - 1];
	}

	/**
	 * Constructeur utilisé pour la construction d'un labyrinthe 
	 * issu d'un fichier .json
	 * @param hauteur hauteur du labyrinthe 
	 * @param largeur largeur du labyrinthe
	 * @param listeSommet liste des pieces du labyrinthe
	 * @param listeArcs liste des portes du labyrinthe
	 * @param entree entree du labyrinthe
	 * @param sortie sortie du labyrinthe
	 */
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
		this.entree = entree;
		this.sortie = sortie;
		if (entree.equals(sortie)) {
			throw new IllegalArgumentException("entrée et sortie confondue");
		}  
	}



	/** 
	 * Créer une grille de taille largeur x hauteur
	 * Et marque les sommets avec une marque unique
	 * @param largeur La largeur voulue pour la grille/ le labyrinthe
	 * @param hauteur La hauteur voulue pour la grille/ le labyrinthe
	 */
	private void genererLabirynthe() {
		listeSommet = creerGrille(largeur, hauteur);    
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

		return grilleRetour; 
	}

	/** 
	 * permet d'ajouter une arrete au graphe
	 * @param sommet1 1 de l'arrete
	 * @param sommet2 2 de l'arrete
	 * @throws IllegalArgumentException si les sommet ne sont pas dans le graphe
	 *                                     ou si l'arrete existe deja
	 */
	private void ajouterArrete(Sommet sommet1, Sommet sommet2) {
		boolean sommet1Valide,
		sommet2Valide;

		sommet1Valide = estPresent(sommet1);
		sommet2Valide = estPresent(sommet2);

		if (!sommet1Valide || !sommet2Valide) {
			throw new IllegalArgumentException(
					"les sommets données ne sont pas dans le graphe");
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
	 * @param sommet dont on souhaite verifier la presence
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
	 * Algorithme de construction du labyrinthe par chaine ascendante
	 */
	public void chaineAscendante() {
		int nbArcCreer;
		nbArcCreer = 0;  

		do {
			int indiceXSommetRandom = (int) (Math.random() 
					* listeSommet.length) ;
			int indiceYSommetRandom = (int) (Math.random() 
					* listeSommet[0].length);
			Sommet sommetChoisie = listeSommet[indiceXSommetRandom]
					[indiceYSommetRandom];
			Sommet[] voisinsChoisi = getSommetsVoisins(sommetChoisie);

			Sommet sommetAAteindre = voisinsChoisi[new Random()
			                                       .nextInt(voisinsChoisi.length)];


			if (sommetChoisie.getMarque() == -1 && 
					sommetAAteindre.getMarque() == -1) {
				nbArcCreer++;     
				sommetChoisie.setMarque(nbArcCreer);
				sommetAAteindre.setMarque(nbArcCreer);
				ajouterArrete(sommetChoisie, sommetAAteindre);
			} else if (sommetAAteindre.getMarque()!= sommetChoisie.getMarque()){
				if (sommetChoisie.getMarque() == -1) {
					nbArcCreer++; 
					sommetChoisie.setMarque(sommetAAteindre.getMarque());
					ajouterArrete(sommetChoisie, sommetAAteindre);
				} else if (sommetAAteindre.getMarque() == -1) {
					nbArcCreer++; 
					sommetAAteindre.setMarque(sommetChoisie.getMarque());
					ajouterArrete(sommetChoisie, sommetAAteindre);
				} else {
					nbArcCreer++;   
					fusionnerMarques(sommetChoisie, sommetAAteindre);
					ajouterArrete(sommetChoisie, sommetAAteindre);               
				}
			}       			            
		} while (nbArcCreer < this.hauteur * this.largeur - 1) ;
	}

	/** 
	 * permet de récuperer les sommet connexes a un graphe passé en paramtres
	 * @param s sommet a récuperer sa connexitée
	 * @return liste de sommet connexe a s
	 */
	private Sommet[] getConnexitee(Sommet s) {

		ArrayList<Sommet> sommetsConnexes = new ArrayList<>();

		for (int i = 0; i < listeSommet.length; i++) {
			for (int j = 0; j < listeSommet[i].length; j++) {
				if (listeSommet[i][j].getMarque() == s.getMarque()) {
					sommetsConnexes.add(listeSommet[i][j]);
				}
			}
		}

		Sommet[] retour = new Sommet[sommetsConnexes.size()];

		for (int i = 0; i < sommetsConnexes.size(); i++) {
			retour[i] = sommetsConnexes.get(i);
		}

		return retour;    	
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
	 * Verifie si deux sommets sont adjacent 
	 * @param sChoisie sommet choisis
	 * @param sTeste sommet dont on souhaite verifier l'adjacence a sChoisie
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
	 * Fusionne les marques entre deux sommets
	 * @param sommetEcrasant sommet dont on garde la marque a applique 
	 * @param sommetEcrase sommet qui va etre marque avec la marque ecrasante
	 */
	public void fusionnerMarques(Sommet sommetEcrasant, Sommet sommetEcrase) {
		Sommet[] connexitée = getConnexitee(sommetEcrase);

		for (int i = 0; i < connexitée.length; i++) {
			connexitée[i].setMarque(sommetEcrasant.getMarque());
		}
	} 

	/**
	 * Verifie si tous les sommets du graphe on ete parcourus 
	 * lors de la creation de labyrinthe
	 * @param tab la liste des sommets du graphe
	 * @return true si tous les elements sont visités
	 *         false sinon
	 */
	private boolean sontTousVisites(Sommet[] tab) {

		for (Sommet s : tab) {
			if (!s.estParcourus()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Construction par backtracking avec une pile
	 * en modifiant la liste des arcs via l'appel de la 
	 * méthode ajouterArc
	 */
	public void constructionBacktracking(){
		setMarqueSommet();

		PileContigue pileSommets = new PileContigue();

		int indiceXSommetRandom = (int)(Math.random() 
				* (listeSommet.length-1));
		int indiceYSommetRandom = (int)(Math.random() 
				* (listeSommet[0].length-1)); 
		Sommet sommetCourant = listeSommet[indiceXSommetRandom]
				[indiceYSommetRandom];
		sommetCourant.setEstParcourus(true);
		pileSommets.empiler(sommetCourant);

		while (!pileSommets.estVide()) {
			Sommet[] listeVoisins = getSommetsVoisins(sommetCourant);

			for (int i = 0; i < listeVoisins.length; i++ ) {
				if (listeVoisins[i].estParcourus()) {
					listeVoisins[i] = null;
				}
			}
			listeVoisins = OutilsTableau.copieSaufNull(listeVoisins);
			if (listeVoisins.length == 0) {
				pileSommets.depiler();
				if (!pileSommets.estVide()) {
					sommetCourant = (Sommet) pileSommets.sommet();
				}
			} else {
				boolean sommetRandomTrouve = false;
				for (int i = 0; i < listeVoisins.length 
						        && !sommetRandomTrouve; i++) {
					int indiceVoisinRandom = (int)(Math.random() 
							                 * (listeVoisins.length));
					Sommet voisinRandom = listeVoisins[indiceVoisinRandom];

					if (!voisinRandom.estParcourus()) {
						ajouterArrete(sommetCourant, voisinRandom);
						pileSommets.empiler(voisinRandom);              
						voisinRandom.setEstParcourus(true);
						sommetCourant = (Sommet) pileSommets.sommet();
						sommetRandomTrouve = true;
					}
				} 
			}
		}
	}

	/**
	 * Met des marques uniques sur les sommets du labyrinthe
	 * Les marques commencent à 1 et se finissent au nombre de sommet -1
	 */
	public void setMarqueSommet() {
		int marque;

		marque = 1;
		for (int i = 0; i < listeSommet.length; i++) {
			for (int j = 0; j < listeSommet[0].length; j++) {
				Sommet s = listeSommet[i][j];
				s.setMarque(marque);
				s.setEstParcourus(false);
				marque++;
			}
		}  
	}

	/**
	 * Obtention des voisins d'un sommet. 
	 * @param sommet le sommet dont on veut avoir les voisins
	 * @return la liste des voisins d'un sommet
	 */
	private Sommet[] getSommetsVoisins(Sommet sommet) {
		int indice;
		Sommet[] retour = new Sommet[4];

		indice = 0;
		for (Sommet[] liste : listeSommet) {
			for (Sommet s : liste) {
				if (sommetAdjacent(sommet, s)) {
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
	 * permet de verifier la liaison entre 2 sommets
	 * @param sommet1 le premier sommet
	 * @param sommet2 le deuxieme sommet
	 * @return true si les 2 sommets sont relies
	 */
	private boolean sontRelies (Sommet sommet1, Sommet sommet2) {
		return    existeArcEntre(sommet1, sommet2) 
				|| existeArcEntre(sommet2, sommet1);
	}

	/**
	 * Permet de verifier la présence d'une arrete entre deux sommets
	 * @param sommet1 sommet1
	 * @param sommet2 sommet2
	 * @return true si une arrete existe entre les deux sommets, false sinon
	 */
	private boolean existeArcEntre(Sommet sommet1, Sommet sommet2) {
		for (int i = 0; i < listeArcs.length; i++) {
			if (   listeArcs[i][0].equals(sommet1) 
			    && listeArcs[i][1].equals(sommet2)) {
				return true;
			}  
		}
		return false;
	}

	/**
	 * Permet d'obtenir les sommets voisins d'un sommet
	 * @param sommet sommet dont on souhaite obtenir les voisins
	 * @return les voisins de sommet
	 */
	private Sommet[] getVoisinsAvecArc(Sommet sommet) {
		int indice;
		Sommet[] retour = new Sommet[4];

		indice = 0;
		for (Sommet[] liste : listeSommet) {
			for (Sommet s : liste) {
				if (sontRelies(sommet, s)) {
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
	public PileContigue parcoursProfondeur() {

		setMarqueSommet();

		PileContigue pileSommets = new PileContigue();
		pileSommets.empiler(entree);
		entree.setEstParcourus(true);

		Sommet sommetCourant = (Sommet) pileSommets.sommet();

		while (!sommetCourant.equals(sortie)) {

			Sommet[] listeVoisins = getVoisinsAvecArc(sommetCourant);

			for (int i = 0; i < listeVoisins.length; i++ ) {
				if (listeVoisins[i].estParcourus()) {
					listeVoisins[i] = null;
				}
			}
			listeVoisins = OutilsTableau.copieSaufNull(listeVoisins);

			if (sontTousVisites(listeVoisins) && !pileSommets.estVide()) {
				pileSommets.depiler();
				if (!pileSommets.estVide()) {
					sommetCourant = (Sommet) pileSommets.sommet();
				} 
			} else {
				sommetCourant = listeVoisins[0];
				sommetCourant.setEstParcourus(true);
				pileSommets.empiler(sommetCourant);
			}

		}

		return pileSommets;

	}


	@Override
	public String toString() {
		String affichage;
		affichage = "";
		this.setMarqueSommet();
		for (int hauteur = 0 ; hauteur < this.hauteur ; hauteur++){ 
			for (int j = 0 ; j < this.largeur ; j++ ) {
				if (this.listeSommet[hauteur][j].getVoisins()[HAUT]) {
					affichage += HAUT_CASE_VIDE; 
				}
				else {
					affichage += HAUT_CASE;
				}  
				if (j == this.largeur -1 ) {
					affichage += "+";
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

					/* Affichage de l'entrée, de la sortie 
					 * ou de la marque du sommet */
					if (this.listeSommet[hauteur][j].equals(entree) && i == 1) {
						affichage += "  E  ";
					} else if (this.listeSommet[hauteur][j].equals(sortie) && i == 1) {
						affichage += "  S  ";
					} else {
						if (i == 1) {
							if (listeSommet[hauteur][j].getMarque() < 10) {
								affichage += String.format(
										     CHAINE_VIDE_MARQUE_UNITE
										     ,listeSommet[hauteur][j].getMarque());
							} else if (listeSommet[hauteur][j].getMarque() < 100) {
								affichage += String.format(
										     CHAINE_VIDE_MARQUE_DIZAINE
										     ,listeSommet[hauteur][j].getMarque());
							} else if (listeSommet[hauteur][j].getMarque() < 1000){
								affichage += String.format(
										     CHAINE_VIDE_MARQUE_CENTAINE
										     , listeSommet[hauteur][j].getMarque());
							} else {
								affichage += CHAINE_VIDE;
							}
						} else {
							affichage += CHAINE_VIDE;
						}

					}

					if (j == this.largeur -1 ) {
						affichage.substring(0, affichage.length() - 1);
						affichage += BORD_CASE;
					}
				}
			}
			affichage += "\n";
			if (hauteur == this.hauteur -1 ) {
				for (int j = 0 ; j < this.largeur ; j++ ) {
					affichage += HAUT_CASE;
					if (j == this.largeur -1 ) {
						affichage += "+";
					}
				}
			}



		}
		return affichage + "\n";
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
	 * @return la valeur de entree
	 */
	public Sommet getEntre() {
		return entree;
	}

	/**
	 * @param entree modifie la valeur de entree
	 */
	public void setEntre(Sommet entre) {
		this.entree = entre;
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

	/**
	 * @return la hauteur du labyrinthe
	 */
	public int getHauteur() {
		return hauteur;
	}

	/** 
	 * @return la largeur du labyrinthe
	 */
	public int getLargeur() {
		return largeur;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Labyrinthe other = (Labyrinthe) obj;
		return Objects.equals(entree, other.entree) 
				              && hauteur == other.hauteur 
				              && largeur == other.largeur
					  && Arrays.deepEquals(listeArcs, other.listeArcs) 
					  && Arrays.deepEquals(listeSommet, other.listeSommet)
					  && Objects.equals(sortie, other.sortie);
	}

    /**
     * Donne les coordonnée d'un sommet à partir de sa marque
     * @param marque
     * @return
     */
    public int[] getCoordoneeFromMarque(int marque) {
        if (marque <= 0 || largeur*hauteur < marque) {
            throw new IllegalArgumentException(
                    "Erreur : Marque iniexistante dans le labyrinthe"
                    );
        }
        for (int i = 0; i < listeSommet.length; i++) {
            for (int j = 0; j < listeSommet[i].length; j++) {
                if (listeSommet[i][j].getMarque() == marque) {
                    return new int[] {i,j};
                }
            }
        }
        return new int[] {listeSommet.length,listeSommet[0].length};        
    }

}