package com.mycompany.travalo;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Inventario {
    Stack<String> productosVendidos = new Stack<>();
    Queue<String> pedidosReposicion = new LinkedList<>();
    String[][] matrizAlmacen = new String[3][3];
    Listasenlasadas listaNodos = new Listasenlasadas();

    File archivoProductos = new File("productos.txt");
    File archivoVentas = new File("ventas.txt");

    public void agregarProducto(Producto p) {
        listaNodos.insertarFinal(p);
        colocarEnMatriz(p);
        guardarInventarioEnArchivo();
        JOptionPane.showMessageDialog(null, "Producto agregado: " + p);
    }

    public void eliminarProducto(String nombre) {
        boolean eliminado = listaNodos.eliminar(nombre);
        if (eliminado) {
            eliminarDeMatriz(nombre);
            guardarInventarioEnArchivo();
            JOptionPane.showMessageDialog(null, "Producto eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
        }
    }

    public void actualizarProducto(String nombre, int nuevoStock) {
        nodos actual = listaNodos.getCabeza();
        while (actual != null) {
            if (actual.dato.nombre.equalsIgnoreCase(nombre)) {
                actual.dato.stock = nuevoStock;
                guardarInventarioEnArchivo();
                JOptionPane.showMessageDialog(null, "Stock actualizado.");
                return;
            }
            actual = actual.sig;
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
    }

    public void venderProducto(String nombre) {
        nodos actual = listaNodos.getCabeza();
        while (actual != null) {
            if (actual.dato.nombre.equalsIgnoreCase(nombre) && actual.dato.stock > 0) {
                actual.dato.stock--;
                productosVendidos.push(actual.dato.nombre);
                guardarVentasEnArchivo();
                guardarInventarioEnArchivo();
                JOptionPane.showMessageDialog(null, "Producto vendido: " + actual.dato.nombre);
                return;
            }
            actual = actual.sig;
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado o sin stock.");
    }

    public void mostrarInventario() {
        StringBuilder sb = new StringBuilder("Inventario actual:\n");
        nodos actual = listaNodos.getCabeza();
        while (actual != null) {
            sb.append(actual.dato).append("\n");
            actual = actual.sig;
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void mostrarProductosVendidos() {
        if (productosVendidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos vendidos.");
            return;
        }
        StringBuilder sb = new StringBuilder("Productos vendidos recientemente:\n");
        Stack<String> temp = new Stack<>();
        while (!productosVendidos.isEmpty()) {
            String prod = productosVendidos.pop();
            sb.append(prod).append("\n");
            temp.push(prod);
        }
        while (!temp.isEmpty()) {
            productosVendidos.push(temp.pop());
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void agregarPedidoReposicion(String nombreProducto) {
        pedidosReposicion.offer(nombreProducto);
        JOptionPane.showMessageDialog(null, "Pedido agregado: " + nombreProducto);
    }

    public void procesarPedidosReposicion() {
        StringBuilder sb = new StringBuilder("Procesando pedidos:\n");
        while (!pedidosReposicion.isEmpty()) {
            String prod = pedidosReposicion.poll();
            sb.append("Reposicionando: ").append(prod).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
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
    }

    private void eliminarDeMatriz(String nombre) {
        for (int i = 0; i < matrizAlmacen.length; i++) {
            for (int j = 0; j < matrizAlmacen[i].length; j++) {
                if (nombre.equals(matrizAlmacen[i][j])) {
                    matrizAlmacen[i][j] = null;
                    return;
                }
            }
        }
    }

    public void mostrarInventarioPorCategoria(String categoria) {
        StringBuilder sb = new StringBuilder("Productos en categoría: " + categoria + "\n");
        nodos actual = listaNodos.getCabeza();
        while (actual != null) {
            if (actual.dato.categoria.equalsIgnoreCase(categoria)) {
                sb.append(actual.dato).append("\n");
            }
            actual = actual.sig;
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void mostrarMatrizAlmacen() {
        StringBuilder sb = new StringBuilder("Matriz de Almacenamiento:\n");
        for (int i = 0; i < matrizAlmacen.length; i++) {
            for (int j = 0; j < matrizAlmacen[i].length; j++) {
                sb.append((matrizAlmacen[i][j] != null ? matrizAlmacen[i][j] : "Vacío")).append("\t");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void actualizarProductoEnListaEnlazada(String nombre, int nuevoStock) {
        nodos actual = listaNodos.getCabeza();
        boolean encontrado = false;

        while (actual != null) {
            if (actual.dato.nombre.equalsIgnoreCase(nombre)) {
                actual.dato.stock = nuevoStock;
                encontrado = true;
                break;
            }
            actual = actual.sig;
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Producto actualizado en lista enlazada.");
            guardarInventarioEnArchivo();
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado en lista enlazada.");
        }
    }

    // ----------------- Persistencia -------------------
    public void guardarInventarioEnArchivo() {
        try (PrintWriter pw = new PrintWriter(archivoProductos)) {
            nodos actual = listaNodos.getCabeza();
            HashSet<String> productosGuardados = new HashSet<>();

            while (actual != null) {
                Producto p = actual.dato;
                if (!productosGuardados.contains(p.nombre.toLowerCase())) {
                    pw.println(p.nombre + "," + p.categoria + "," + p.stock);
                    productosGuardados.add(p.nombre.toLowerCase());
                }
                actual = actual.sig;
            }
        } catch (IOException e) {
            System.err.println("Error guardando inventario.");
        }
    }

    public void cargarInventarioDesdeArchivo() {
        if (!archivoProductos.exists()) return;

        listaNodos = new Listasenlasadas();
        matrizAlmacen = new String[3][3];

        try (Scanner sc = new Scanner(archivoProductos)) {
            while (sc.hasNextLine()) {
                String[] datos = sc.nextLine().split(",");
                if (datos.length == 3) {
                    Producto p = new Producto(datos[0], datos[1], Integer.parseInt(datos[2]));
                    listaNodos.insertarFinal(p);
                    colocarEnMatriz(p);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando inventario.");
        }
    }

    public void guardarVentasEnArchivo() {
        try (PrintWriter pw = new PrintWriter(archivoVentas)) {
            for (String venta : productosVendidos) {
                pw.println(venta);
            }
        } catch (IOException e) {
            System.err.println("Error guardando ventas.");
        }
    }

    public void cargarProductosVendidosDesdeArchivo() {
        if (!archivoVentas.exists()) return;
        try (Scanner sc = new Scanner(archivoVentas)) {
            while (sc.hasNextLine()) {
                productosVendidos.push(sc.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Error cargando ventas.");
        }
    }
}
