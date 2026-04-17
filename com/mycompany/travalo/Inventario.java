package com.mycompany.travalo;

import java.util.*;

public class Inventario {
    LinkedList< Producto > listaProductos = new LinkedList<>();
    Stack<String> productosVendidos = new Stack<>();
    Queue<String> pedidosReposicion = new LinkedList<>();
    String[][] matrizAlmacen = new String[3][3]; // ejemplo: 3x3 por categorías y ubicaciones

    public void agregarProducto(Producto p) {
        listaProductos.add(p);
        colocarEnMatriz(p);
        System.out.println("Producto agregado: " + p);
    }

    public void venderProducto(String nombre) {
        for (Producto p : listaProductos) {
            if (p.nombre.equalsIgnoreCase(nombre) && p.stock > 0) {
                p.stock--;
                productosVendidos.push(p.nombre);
                System.out.println("Producto vendido: " + p.nombre);
                return;
            }
        }
        System.out.println("Producto no encontrado o sin stock.");
    }

    public void mostrarInventario() {
        System.out.println("Inventario actual:");
        for (Producto p : listaProductos) {
            System.out.println(p);
        }
    }

    public void mostrarProductosVendidos() {
        System.out.println("Productos más vendidos recientemente:");
        Stack<String> temp = new Stack<>();
        while (!productosVendidos.isEmpty()) {
            String prod = productosVendidos.pop();
            System.out.println(prod);
            temp.push(prod);
        }
        while (!temp.isEmpty()) {
            productosVendidos.push(temp.pop());
        }
    }

    public void agregarPedidoReposicion(String nombreProducto) {
        pedidosReposicion.offer(nombreProducto);
        System.out.println("Pedido de reposición agregado: " + nombreProducto);
    }

    public void procesarPedidosReposicion() {
        System.out.println("Procesando pedidos de reposición...");
        while (!pedidosReposicion.isEmpty()) {
            String prod = pedidosReposicion.poll();
            System.out.println("Reposicionando: " + prod);
        }
    }

    private void colocarEnMatriz(Producto p) {
        for (int i = 0; i < matrizAlmacen.length; i++) {
            for (int j = 0; j < matrizAlmacen[i].length; j++) {
                if (matrizAlmacen[i][j] == null) {
                    matrizAlmacen[i][j] = p.nombre;
                    return;
                }
            }
        }
        System.out.println("No hay espacio en la matriz de almacenamiento.");
    }
}
