package test.representation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import outils.OutilsMatrice;

import org.junit.jupiter.api.BeforeEach;


import representation.Graphe;
import representation.Sommet;


/**
 * Test Unitaire de la classe Graphe
 * @author Costes Quentin
 * @author de Saint Palais François
 * @author Denamiel Clément
 * @author Descriaud Lucas
 *
 */
class TestGraphe {
    
    private ArrayList<Graphe> grapheCorrecte;
    
    @BeforeEach
    void genererGrapheValide() {
        grapheCorrecte = new ArrayList<>(10);
        {/* Un graphe normal */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            grapheCorrecte.add(new Graphe(tS, tA));            
        }
             
        { /* graphe symétrique */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[0]}, {tS[0], tS[2]}, 
            				 {tS[2], tS[0]}, {tS[1], tS[2]}, {tS[2], tS[1]}};
            grapheCorrecte.add(new Graphe(tS, tA)); 
        }
        
        { /* graphe transitif */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}, {tS[2], tS[0]}};
            grapheCorrecte.add(new Graphe(tS, tA)); 
        }  
        
        { /* graphe avec un sommet isolé */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[0]}};
            grapheCorrecte.add(new Graphe(tS, tA)); 
        } 
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1,3),
            			   new Sommet(2,1), new Sommet(2,2), new Sommet(2,3)};
            Sommet[][]tA = {{tS[0],tS[3]},{tS[2],tS[5]}};
            grapheCorrecte.add(new Graphe(tS, tA));
        }
    }

    @Test
    @DisplayName("Test du constructeur avec valeur Valide")
    void testGrapheOK() {
        {/* Un graphe normal */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            assertDoesNotThrow(()-> new Graphe(tS, tA));            
        }
             
        { /* graphe symétrique */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[0]}, {tS[0], tS[2]}, {tS[2], tS[0]}, {tS[1], tS[2]}, {tS[2], tS[1]}};
            assertDoesNotThrow(()-> new Graphe(tS, tA)); 
        }
        
        { /* graphe transitif */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}, {tS[2], tS[0]}};
            assertDoesNotThrow(()-> new Graphe(tS, tA)); 
        }  
        
        { /* graphe avec un sommet isolé */
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[0]}};
            assertDoesNotThrow(()-> new Graphe(tS, tA)); 
        }  
        
       
    }
    
    @Test
    @DisplayName("Test du constructeur avec valeur Invalide")
    void testGrapheNotOk() {
        {/* Il doit exister une liste de sommet et/ou d'arcs */
            Sommet[] tSommet = {new Sommet(1,1), new Sommet(1,2)};
            Sommet[][] tArcs = {{tSommet[0],tSommet[1]}};
            Sommet[] tSommetVide = {};
            Sommet[][] tArcsVide = {{}};
            assertThrows(IllegalArgumentException.class, ()->new Graphe(null, null));
            assertThrows(IllegalArgumentException.class, ()->new Graphe(tSommet, null));
            assertThrows(IllegalArgumentException.class, ()->new Graphe(null, tArcs));            
            assertThrows(IllegalArgumentException.class, ()->new Graphe(tSommetVide, tArcsVide));            
            assertThrows(IllegalArgumentException.class, ()->new Graphe(tSommet, tArcsVide));
            assertThrows(IllegalArgumentException.class, ()->new Graphe(tSommetVide, tArcs));
        }
        {
            
        }
        
        {/* Liste d'arcs non vide alors que liste sommet vide */
            Sommet[] tSommet = {};
            Sommet[][] tArcs = {{new Sommet(1,1),new Sommet(1,2)}};
            assertThrows(IllegalArgumentException.class, ()->new Graphe(tSommet, tArcs));
        }

        {/* Il existe une arrête entre deux sommet inexistant */
            Sommet[] tSommets = {new Sommet(1,1)};
            Sommet[][] tArcs = {{new Sommet(1,2),new Sommet(1,3)}};
            assertThrows(IllegalArgumentException.class,()-> new Graphe(tSommets, tArcs));            
        }
        
        {/* Un des sommets de l'arrête est inexistant*/
            Sommet[] tSommets = {new Sommet(1,1)};
            Sommet[][] tArcs = {{tSommets[0], new Sommet(3,3)}};
            assertThrows(IllegalArgumentException.class,()-> new Graphe(tSommets, tArcs));            
        }

        {/* Un des sommets de l'arrête est inexistant*/
            Sommet[] tSommets = {new Sommet(1,1)};
            Sommet [][] tArcs = {{new Sommet(1,3),tSommets[0]}};
            assertThrows(IllegalArgumentException.class,()-> new Graphe(tSommets, tArcs));            
        }


        
        {/* Un graphe doit  avoir au moins une arrête */
            Sommet[] tSommets = {new Sommet(1,1), new Sommet(1,2), new Sommet(1,4)};
            Sommet[][] tArcs = {{}};
            assertThrows(IllegalArgumentException.class, ()-> new Graphe(tSommets, tArcs));
        }
        
        {/* Un graphe ne contient qu'une seule fois chaque sommet*/
            Sommet[] tS = {new Sommet(1,1),new Sommet(1,1),new Sommet(1,2)};
            Sommet[][] tA = {{tS[0],tS[2]}};
            assertThrows(IllegalArgumentException.class, ()-> new Graphe(tS,tA));
        }
        
    }

    @Test
    @DisplayName("Test du geter des nombre d'arretes")
    void testGetNbArretes() {
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,3), new Sommet(1,4) };
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}, {tS[2], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(3, g.getNbArretes()); 
        }
        
        {
            Sommet [] tS = {new Sommet(2,1), new Sommet(2,2)};
            Sommet [][] tA = {{tS[0], tS[1]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(1, g.getNbArretes()); 
        }

        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1,3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[0]}, {tS[0], tS[2]}, 
            			     {tS[2], tS[0]}, {tS[2], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(6, g.getNbArretes()); 
        }
        
        
        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,3), new Sommet(1,4)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(3, g.getNbArretes()); 
        }
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1,3)};
            Sommet[][] tA = {{tS[2], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(1, g.getNbArretes()); 
        }
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,3), new Sommet(1,4), 
                           new Sommet(1,5), new Sommet(1,6)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(5, g.getNbArretes()); 
        }
        {
            Sommet[] tS = {new Sommet(1,6), new Sommet(2,6)};
            Sommet[][] tA = {{tS[0],tS[1]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(2, g.getNbArretes()); 
        }
        
    }

    @Test
    @DisplayName("Test du getter des nombre de sommets")
    void testGetNbSommets() {
        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,3), new Sommet(1,4)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}, {tS[2], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(3, g.getNbSommets()); 
        }
        
        {
            Sommet[] tS = {new Sommet(1, 2), new Sommet(1, 1)};
            Sommet[][] tA = {{tS[0], tS[1]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(2, g.getNbSommets()); 
        }
        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2)};
            Sommet[][] tA = {{tS[1], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertEquals(2, g.getNbSommets()); 
        }
        {
            Sommet[] tS = {new Sommet(1, 2), new Sommet(1, 1)};
            Sommet[][] tA = {{tS[1], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(tS.length + 1, g.getNbSommets()); 
        }
        {
            Sommet[] tS = {new Sommet(1, 1), new Sommet(1, 2)};
            Sommet[][] tA = {{tS[1], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertNotEquals(tS.length - 1, g.getNbSommets()); 
        }
    }
    
    @Test
    @DisplayName("Test de ma méthode sontRelier")
    void testSontRelier () {
        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2)};
            Sommet[][] tA = {{tS[1], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertTrue(g.sontRelies(tS[0], tS[1]));
            assertTrue(g.sontRelies(tS[1], tS[0]));  
        }
        
        {
            Sommet[] tS = {new Sommet(1, 1), new Sommet(1, 2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}, {tS[2], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertTrue(g.sontRelies(tS[1], tS[0]));
            assertTrue(g.sontRelies(tS[1], tS[2]));
            assertTrue(g.sontRelies(tS[2], tS[0])); 
        }
        
        {
            Sommet[] tS = {new Sommet(1,1), new Sommet(1,2), new Sommet(1, 3)};
            Sommet[][] tA = {{tS[1], tS[0]}};
            Graphe g = new Graphe(tS, tA);
            assertFalse(g.sontRelies(tS[0], tS[2]));
            assertFalse(g.sontRelies(tS[2], tS[0])); 
            assertTrue(g.sontRelies(tS[1], tS[0]));  
        }
    }
    
    @Test
    void testToMatriceAdjacence() {
        boolean[][] graphe1 = {{false,true,false},
                               {false,false,true},
                               {false,false,false}};
                               
        boolean[][] graphe2 = {{false , true , true},
        					   {true , false , true},
        					   {true , true , false}};
        						
        boolean[][] graphe3 = {{false,true,false},
                               {false,false,true},
                               {true,false,false}};
                
        boolean[][] graphe4 = {{false , true , false},
        					   {true , false , false},
        					   {false , false , false}};
        
        assertArrayEquals(graphe1, grapheCorrecte.get(0).toMatriceAdjacence());
        assertArrayEquals(graphe2, grapheCorrecte.get(1).toMatriceAdjacence());
        assertArrayEquals(graphe3, grapheCorrecte.get(2).toMatriceAdjacence());
        assertArrayEquals(graphe4, grapheCorrecte.get(3).toMatriceAdjacence());
        
    }
    
    @Test
    void testToString() { 
		Graphe test = grapheCorrecte.get(4);
		System.out.println(test);
		//String attendu = "Sommets : ((1; 1)(1; 2)(1; 3))"
		//        + "\nArcs : (((1; 1) -> (1; 2)), ((1; 2) -> (1; 3)))";
		//assertEquals(attendu, test.toString());
    }
    
    @Test
    void testContientCircuit() {
        Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
        Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]}};
        Graphe graphe = new Graphe(tS,tA);
        assertTrue(graphe.contienCircuit());

        assertFalse(grapheCorrecte.get(0).contienCircuit());
        assertTrue(grapheCorrecte.get(1).contienCircuit());
        assertTrue(grapheCorrecte.get(2).contienCircuit());
        assertTrue(grapheCorrecte.get(3).contienCircuit());
    }
    
    @Test
    void testEstReflexif() {
        assertFalse(grapheCorrecte.get(0).estReflexif());
        assertFalse(grapheCorrecte.get(1).estReflexif());
        assertFalse(grapheCorrecte.get(2).estReflexif());
        assertFalse(grapheCorrecte.get(3).estReflexif());
        
        {
            Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
            Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]},
                             {tS[0],tS[0]},{tS[1],tS[1]},{tS[2],tS[2]}};
            Graphe graphe = new Graphe(tS,tA);
            assertTrue(graphe.estReflexif());
        }
        {
            Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
            Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]},
                             {tS[0],tS[0]},{tS[1],tS[1]}};
            Graphe graphe = new Graphe(tS,tA);
            assertFalse(graphe.estReflexif());
        }
        
    }

    @Test
    void testEstIreflexif() {
        assertTrue(grapheCorrecte.get(0).estIrreflexif());
        assertTrue(grapheCorrecte.get(1).estIrreflexif());
        assertTrue(grapheCorrecte.get(2).estIrreflexif());
        assertTrue(grapheCorrecte.get(3).estIrreflexif());
        
        {
            Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
            Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]},
                    {tS[0],tS[0]},{tS[1],tS[1]},{tS[2],tS[2]}};
            Graphe graphe = new Graphe(tS,tA);
            assertFalse(graphe.estIrreflexif());
        }
        {
            Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
            Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]},
                    {tS[0],tS[0]},{tS[1],tS[1]}};
            Graphe graphe = new Graphe(tS,tA);
            assertFalse(graphe.estIrreflexif());
        }
        {
            Sommet[] tS = {new Sommet(0, 1),new Sommet(0, 2),new Sommet(0, 3)};
            Sommet[][] tA = {{tS[0],tS[1]},{tS[1],tS[2]},{tS[2],tS[0]}};
            Graphe graphe = new Graphe(tS,tA);
            assertTrue(graphe.estIrreflexif());
        }
        
    }
    
    @Test
    void testAjoutetArrete() {
        {
            Sommet s1 = new Sommet(1,1);
            Sommet s2 = new Sommet(1,2);
            Sommet s3 = new Sommet(1,3);
            Sommet[] tS = {s1, s2, s3};
            Sommet[][] tA = {{tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            assertFalse(g.sontRelies(tS[0], tS[1]));           
            g.ajouterArrete(s1, s2);
            assertTrue(g.sontRelies(s1, s2));                        
        }
        
        {
            Sommet s1 = new Sommet(1,1);
            Sommet s2 = new Sommet(1,2);
            Sommet s3 = new Sommet(1,3);
            Sommet[] tS = {s1, s2, s3};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
            
            assertFalse(g.sontRelies(tS[0], tS[2]));
            
            g.ajouterArrete(tS[0], tS[2]);
            
            assertTrue(g.sontRelies(tS[0], tS[2]));
            assertTrue(g.sontRelies(tS[1], tS[2]));

        }
        
        {
            Sommet s1 = new Sommet(1,1);
            Sommet s2 = new Sommet(1,2);
            Sommet s3 = new Sommet(1,3);
            Sommet[] tS = {s1, s2};
            Sommet[][] tA = {{tS[0], tS[1]}};
            Graphe g = new Graphe(tS, tA);
            
            // le sommet n'existe pas dans le graphe
            assertThrows(IllegalArgumentException.class, ()-> g.ajouterArrete(tS[1], s3));  
        }
        // TODO tester le fait que ça throw si l'arrete existe deja
        {
            Sommet s1 = new Sommet(1,1);
            Sommet s2 = new Sommet(1,2);
            Sommet s3 = new Sommet(1,3);
            Sommet[] tS = {s1, s2, s3};
            Sommet[][] tA = {{tS[0], tS[1]}, {tS[1], tS[2]}};
            Graphe g = new Graphe(tS, tA);
        }
    }
}
