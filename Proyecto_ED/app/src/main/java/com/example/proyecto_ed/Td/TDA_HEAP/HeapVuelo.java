package com.example.proyecto_ed.Td.TDA_HEAP;

import com.example.proyecto_ed.Models.Vuelo;

import java.util.Arrays;
import java.util.Comparator;

public class HeapVuelo {
    private Comparator<Vuelo> comparator;
    private Vuelo[] arreglo;
    private int MAX = 100;
    private int efectivo;
    private boolean isMax;

    public HeapVuelo(Vuelo[] arreglo, boolean isMax) {
        this.arreglo = Arrays.copyOf(arreglo, arreglo.length);
        this.efectivo = arreglo.length;
        this.isMax = isMax;
        this.comparator = isMax
                ? Comparator.comparingDouble(Vuelo::getPrecio).reversed()
                : Comparator.comparingDouble(Vuelo::getPrecio);
        makeHeap();
    }

    public boolean isEmpty() {
        return efectivo == 0;
    }

    private void addCapacity() {
        Vuelo[] tmp = (Vuelo[]) new Object[MAX * 2];
        for (int i = 0; i < MAX; i++){
            tmp[i] = arreglo[i];
        }
        arreglo = tmp;
        MAX = MAX * 2;
    }

    public int getIzq(int raiz){
        if (raiz < 0) {
            return 0;
        } else {
            return raiz*2+1;
        }
    }

    public int getDer(int raiz){
        if (raiz < 0) {
            return 0;
        } else {
            return raiz*2+2;
        }
    }

    private boolean isValidIndex(int pos) {
        return pos >= 0 && pos < efectivo;
    }

    private void interambiar(int posRaiz, int posMayor) {
        if (isValidIndex(posRaiz)) {
            Vuelo element1 = arreglo[posRaiz];
            Vuelo element2 = arreglo[posMayor];

            arreglo[posRaiz] = element2;
            arreglo[posMayor] = element1;
        }
    }

    private int getPosMayor(int posRaiz) {
        int izq = getIzq(posRaiz);
        int der = getDer(posRaiz);
        if (izq > posRaiz) {
            return izq;
        } else if (der > posRaiz) {
            return der;
        } else {
            return posRaiz;
        }
    }

    public void ajustar(int posRaiz){
        int posIzq = getIzq(posRaiz);
        int posDer = getDer(posRaiz);
        if (isValidIndex(posIzq) && isValidIndex(posDer)) {
            int posMayor = getPosMayor(posRaiz);
            if (isValidIndex(posMayor)) {
                if (posMayor != posRaiz) {
                    interambiar(posRaiz, posMayor);
                    ajustar(posMayor);
                }
            }
        }
    }

    public void makeHeap(){
        int i;
        for (i = this.efectivo/2-1; i >= 0; i--) {
            ajustar(i);
        }
    }

    public Vuelo desencolar(){
        Vuelo maxValue;
        if (!isEmpty()) {
            maxValue = this.arreglo[0];
            interambiar(0, this.efectivo-1);
            this.efectivo--;
            ajustar(0);
            return maxValue;
        }
        return null;
    }

    public void encolar(Vuelo vuelo) {
        if (efectivo == MAX) {
            addCapacity();
        }
        arreglo[efectivo] = vuelo;
        ajustar(efectivo);
        efectivo++;
    }

    public Vuelo verRaiz() {
        if (isEmpty()) {
            return null;
        }
        return arreglo[0];
    }
}
