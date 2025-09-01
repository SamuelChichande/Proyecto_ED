package com.example.proyecto_ed.Td.TDA_ABB;

import com.example.proyecto_ed.Models.Vuelo;

import org.w3c.dom.Node;

import java.util.List;
import java.util.Stack;

public class BinaryTree {
    private NodeBinaryTree root;

    public BinaryTree(){
        this.root = null;
    }

    public BinaryTree(NodeBinaryTree root) {
        this.root = root;
    }

    public BinaryTree(Vuelo content) {
        this.root = new NodeBinaryTree(content);
    }

    public NodeBinaryTree getRoot() {
        return root;
    }

    public void setRoot(NodeBinaryTree root) {
        this.root = root;
    }

    public boolean isEmpty(){
        if (this.getRoot() == null) {
            return true;
        }
        return false;
    }

    public boolean isLedf(){
        if (this.isEmpty()) {
            return this.getRoot().getLeft() == null && this.getRoot().getRight() == null;
        }
        return false;
    }

    /*
    public void construirABB(List<Vuelo> lista) {
        if (lista == null || lista.isEmpty()) {
            this.root = null;
            return;
        }

        // Construimos el ABB insertando los elementos en orden
        for (Vuelo vuelo : lista) {
            setRoot(insertar(root, vuelo));
        }

    }
    */

    public boolean insertar(BinaryTree bt, Vuelo vuelo) {
        if (bt == null || bt.isEmpty()) {
            return false;
        }
        
        Vuelo vueloaActual = bt.getRoot().getContent();

        if (vuelo.getId() < vueloaActual.getId()) {
            if (bt.getRoot().getLeft() != null) {
                BinaryTree nuevoArbol = new BinaryTree();
                nuevoArbol.getRoot().setContent(vuelo);
                bt.getRoot().setLeft(nuevoArbol);
            } else {
                return insertar(bt.getRoot())
            }
        } else if (vuelo.getId() > vueloaActual.getId()) {

        } else {
            return false;
        }

        if (bt.getRoot().getLeft() != null){
            insertar(bt.getRoot().getLeft(), vuelo);
        } else if (bt.getRoot().getRight() != null) {
            insertar(bt.getRoot().getRight(), vuelo);
        }

        if (bt.getRoot().getLeft() == null && bt.getRoot().getContent().getId() > vuelo.getId()) {
            bt.getRoot().getLeft().getRoot().setContent(vuelo);
        } else if (bt.getRoot().getRight() == null && bt.getRoot().getContent().getId() < vuelo.getId()) {
            bt.getRoot().getRight().getRoot().setContent(vuelo);
        }
        return true;
    }

    public int countLevelsRecursive(){
        if (this.isEmpty()) {
            return 0;
        }

        int levelsLeft = 0;
        int levelsRight = 0;

        if (this.root.getLeft() != null) {
            levelsLeft += this.root.getLeft().countLevelsRecursive();
        }

        if (this.root.getRight() != null) {
            levelsRight = this.root.getRight().countLevelsRecursive();
        }

        if (levelsLeft >= levelsRight) {
            return levelsLeft+1;
        } else{
            return levelsRight+1;
        }
    }


    //Reparacion de AVL
    //En este metodo el arbol AVL que no cumple con el factor de equilibrio, identifica
    //si necesita una rotacion simple o doble para reparalo y llama a su respectivo metodo
    public static BinaryTree repararAVL(BinaryTree tree){
        if (tree.isEmpty() || tree.isLedf()) {
            return null;
        }

        //Validand que el arbol binario cumpla con las propidades de un AVL
        if (!priorityOrder(tree)) {
            return null;
        }

        //Condicion para comenzar a reparar el arbol
        if (factorEquilibrio(tree.getRoot()) > 1 || factorEquilibrio(tree.getRoot()) < -1) {
            //Arbol desde donde empezara la reparacion
            NodeBinaryTree nodeN = findProblemNode(tree);
            //Buscamos el nodo padre para poder reemplazarlo despues
            NodeBinaryTree nodeParent = findParent(tree, nodeN);
            boolean isChildLeft = false;
            if (nodeParent.getLeft() != null) {
                if (nodeParent.getLeft().getRoot().getContent().getId() == nodeN.getContent().getId()) {
                    isChildLeft = true;
                }
            }
            //Identificamos el tipo de reparacion que necesita
            int balance = factorEquilibrio(nodeN);
            // Rotación simple derecha
            if (balance > 1 && factorEquilibrio(nodeN.getLeft().getRoot()) >= 0) {
                nodeN = rotacionSimple(nodeN, true);
            }

            // Rotación simple izquierda
            if (balance < -1 && factorEquilibrio(nodeN.getRight().getRoot()) <= 0) {
                nodeN = rotacionSimple(nodeN, false);
            }

            // Rotación doble izquierda-derecha
            if (balance > 1 && factorEquilibrio(nodeN.getLeft().getRoot()) < 0) {
                nodeN = rotacionDoble(nodeN, false);
            }

            // Rotación doble derecha-izquierda
            if (balance < -1 && factorEquilibrio(nodeN.getRight().getRoot()) > 0) {
                nodeN = rotacionSimple(nodeN, true);
            }

            //Recorremos el arbol hasta encontrar el lugar donde se desea reemplazar
            Stack<BinaryTree> s = new Stack<>();
            s.push(tree);
            while (!s.isEmpty()) {
                BinaryTree ab = s.pop();
                if (ab.getRoot().getLeft() != null) {
                    s.push(ab.getRoot().getLeft());
                }

                if (ab.getRoot().getRight()!= null) {
                    s.push(ab.getRoot().getRight());
                }

                if (ab.getRoot().getContent().getId() == nodeParent.getContent().getId()) {
                    if (isChildLeft) {
                        ab.getRoot().getLeft().setRoot(nodeN);
                    } else {
                        ab.getRoot().getRight().setRoot(nodeN);
                    }
                }
            }

            return tree;
        }
        return tree;
    }

    public static NodeBinaryTree rotacionSimple(NodeBinaryTree n, boolean rotationRight){
        BinaryTree n1 = null;
        if (rotationRight) {
            n1 = n.getLeft();
            n.setLeft(n1.getRoot().getRight());
            n1.getRoot().getRight().setRoot(n);
            n = n1.getRoot();
        } else {
            n1 = n.getRight();
            n.setRight(n1.getRoot().getLeft());
            n1.getRoot().getLeft().setRoot(n);
            n = n1.getRoot();
        }
        return n;
    }

    public static NodeBinaryTree rotacionDoble(NodeBinaryTree n, boolean firstRight){
        NodeBinaryTree n1 = null;
        if (firstRight) {
            //Hacemos una rotacion simple del hijo izquierdo con el sentido contrario
            n1 = rotacionSimple(n.getLeft().getRoot(), !firstRight);
            n.setLeft(n1.getRight());
            n1.getRight().setRoot(n);
            n = n1;
        } else {
            //Hacemos una rotacion simple del hijo derehco con el sentido contrario
            n1 = rotacionSimple(n.getRight().getRoot(), !firstRight);
            n.setRight(n1.getLeft());
            n1.getLeft().setRoot(n);
            n = n1;
        }
        return n;
    }

    //Metodos necesarios
    public static boolean priorityOrder(BinaryTree tree){
        if (tree.isEmpty()) {
            return false;
        } else if(tree.isLedf()) {
            return true;
        } else {
            boolean leftPO = true;
            boolean rightPO = true;

            if (tree.getRoot().getLeft() != null) {
                BinaryTree left = tree.getRoot().getLeft();
                //Si el contenido del hijo izquierdo es mayor, retornara falso
                if (left.getRoot().getContent().getId() >= tree.getRoot().getContent().getId()) {
                    leftPO = false;
                    return leftPO;
                }
                leftPO = priorityOrder(left);
            }

            if (tree.getRoot().getRight()!= null) {
                BinaryTree right = (BinaryTree) tree.getRoot().getRight();
                //Si el contenido del hijo derecho es menor, retornara falso
                if (right.getRoot().getContent().getId() <= tree.getRoot().getContent().getId()) {
                    rightPO = false;
                    return rightPO;
                }
                rightPO = priorityOrder(right);
            }
            return leftPO && rightPO;
        }
    }

    public static int factorEquilibrio(NodeBinaryTree node){
        if (node == null) {
            return 0;
        } else {
            int leftLevel = 0;
            int rightLevel = 0;
            if (node.getLeft() != null) {
                leftLevel = node.getLeft().countLevelsRecursive();
            }

            if (node.getRight()!= null) {
                rightLevel = node.getRight().countLevelsRecursive();
            }
            return rightLevel - leftLevel;
        }
    }



    //Estos metodos se encargan de retornar el nodo que no cumple con el factor de equilibrio
    //y se encuentre mas cercano al nodo que causa el problema
    public static NodeBinaryTree findProblemNode(BinaryTree tree) {
        if (tree == null || tree.isEmpty()) {
            return null;
        }
        //Si el nodo de la raiz del arbol no cumple el factor de equilibrio se lo guarda
        NodeBinaryTree nodoProblemaActual = null;
        if (Math.abs(factorEquilibrio(tree.getRoot())) > 1) {
            nodoProblemaActual = tree.getRoot();
        } else {
            nodoProblemaActual = null;
        }
        //Si el nodo del hijo izquierdo no cumple con el factor de equilibrio se lo guarda
        NodeBinaryTree nodoProblemaIzq = null;
        if (tree.getRoot().getLeft() != null) {
            nodoProblemaIzq = findProblemNode(tree.getRoot().getLeft());;
        }
        //Si el nodo del hijo derecho no cumple con el factor de equilibrio se lo guarda
        NodeBinaryTree nodoProblemaDer = null;
        if (tree.getRoot().getRight() != null) {
            nodoProblemaDer = findProblemNode(tree.getRoot().getRight());
        }
        //Retornara el nodo que no cumple con el factor de equilibrio mas cercano al nodo
        //que causa el problema
        return getNodeNotFE(nodoProblemaActual,
                getNodeNotFE(nodoProblemaIzq, nodoProblemaDer));
    }

    private static NodeBinaryTree getNodeNotFE(NodeBinaryTree a, NodeBinaryTree b) {
        if (a == null) return b;
        if (b == null) return a;
        if (Math.abs(factorEquilibrio(a)) > Math.abs(factorEquilibrio(b))) {
            return a;
        } else {
            return b;
        }
    }

    public static NodeBinaryTree findParent(BinaryTree AB, NodeBinaryTree node){
        if (node == null || AB.isEmpty() || AB.getRoot().equals(node)) {
            return null;
        }

        if (AB.getRoot().getLeft() != null && AB.getRoot().getLeft().getRoot().equals(node)) {
            return AB.getRoot();
        }

        if (AB.getRoot().getRight() != null && AB.getRoot().getRight().getRoot().equals(node)) {
            return AB.getRoot();
        }

        NodeBinaryTree nodeParent = null;

        if (AB.getRoot().getLeft() != null) {
            nodeParent = findParent(AB.getRoot().getLeft(), node);
            //Hacemos esta validacion para que si ya se encontro el nodo padre
            //no tenga que seguir buscando, y asi no pueda cambiar el nodo padre
            if (nodeParent != null) {
                return nodeParent;
            }
        }

        if (AB.getRoot().getRight() != null) {
            nodeParent = findParent(AB.getRoot().getRight(), node);
        }

        return nodeParent;
    }
}
