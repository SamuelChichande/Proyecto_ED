package com.example.proyecto_ed.Td.TDA_ABB;

import com.example.proyecto_ed.Models.Vuelo;

public class NodeBinaryTree {
    private Vuelo content;
    private BinaryTree left;
    private BinaryTree right;

    public NodeBinaryTree(Vuelo content) {
        this.content = content;
    }

    public Vuelo getContent() {
        return content;
    }

    public void setContent(Vuelo content) {
        this.content = content;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree child1) {
        this.left = child1;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree child2) {
        this.right = child2;
    }
}
