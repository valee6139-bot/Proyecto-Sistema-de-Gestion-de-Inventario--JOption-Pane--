package com.mycompany.travalo;

public class Listasenlasadas {
    private nodos cab;

    public Listasenlasadas() {
        this.cab = null;
    }

    // Inserta un nodo al final de la lista
    public void insertarFinal(Producto dato) {
        nodos nuevo = new nodos(dato);
        if (cab == null) {
            cab = nuevo;
        } else {
            nodos actual = cab;
            while (actual.sig != null) {
                actual = actual.sig;
            }
            actual.sig = nuevo;
        }
    }

    // Retorna la cabeza de la lista
    public nodos getCabeza() {
        return cab;
    }

    // Muestra la lista por consola (para depuración)
    public void mostrar() {
        nodos actual = cab;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.sig;
        }
    }

    // Actualiza el stock de un producto en la lista enlazada
    public boolean actualizarStockNodo(String nombre, int nuevoStock) {
        nodos actual = cab;
        while (actual != null) {
            if (actual.dato.nombre.equalsIgnoreCase(nombre)) {
                actual.dato.stock = nuevoStock;
                return true;
            }
            actual = actual.sig;
        }
        return false;
    }

    // Elimina un producto por nombre de la lista enlazada
    public boolean eliminar(String nombre) {
        if (cab == null) {
            return false;
        }
        // Si el primer nodo es el que hay que eliminar
        if (cab.dato.nombre.equalsIgnoreCase(nombre)) {
            cab = cab.sig;
            return true;
        }
        // Buscar en los nodos siguientes
        nodos actual = cab;
        while (actual.sig != null) {
            if (actual.sig.dato.nombre.equalsIgnoreCase(nombre)) {
                actual.sig = actual.sig.sig;
                return true;
            }
            actual = actual.sig;
        }
        return false;
    }
}
