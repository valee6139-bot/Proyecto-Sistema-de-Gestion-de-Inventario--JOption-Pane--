package com.mycompany.travalo;

import javax.swing.JOptionPane;

public class Travalo {

    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        inventario.cargarInventarioDesdeArchivo(); // Cargar al iniciar

        while (true) {
           String opcion = JOptionPane.showInputDialog(null,
                "Sistema de Gestión de Inventarios\n"
                + "1. Agregar Producto\n"
                + "2. Eliminar Producto\n"
                + "3. Mostrar Inventario\n"
                + "4. Vender Producto\n"
                + "5. Procesar Reposición\n"
                + "6. Mostrar Productos Vendidos\n"
                + "7. Mostrar Inventario por Categoría\n"
                + "8. Mostrar Matriz de Almacenamiento\n"
                + "9. Actualizar Producto (usando Nodos)\n"
                + "10. Salir\n");


            if (opcion == null || opcion.equals("10")) {
                inventario.guardarInventarioEnArchivo(); // Guardar antes de salir
                break;
            }

            switch (opcion) {
                case "1":
                    String nombre = JOptionPane.showInputDialog("Nombre del Producto:");
                    String categoria = JOptionPane.showInputDialog("Categoría del Producto:");
                    int stock = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de Stock:"));
                    inventario.agregarProducto(new Producto(nombre, categoria, stock));
                    break;

                case "2":
                    String eliminar = JOptionPane.showInputDialog("Ingrese el nombre del producto a eliminar:");
                    inventario.eliminarProducto(eliminar);
                    break;
                    
                case "3":
                    inventario.mostrarInventario();
                    break;

                case "4":
                    String vender = JOptionPane.showInputDialog("Producto a vender:");
                    inventario.venderProducto(vender);
                    break;

                case "5":
                    inventario.procesarPedidosReposicion();
                    break;

                case "6":
                    inventario.mostrarProductosVendidos();
                    break;

                case "7":
                    String cat = JOptionPane.showInputDialog("Categoría a consultar:");
                    inventario.mostrarInventarioPorCategoria(cat);
                    break;

                case "8":
                    inventario.mostrarMatrizAlmacen();
                    break;

                case "9":
                    String nombreNodo = JOptionPane.showInputDialog("Nombre del producto a actualizar (desde nodos):");
                    int nuevoStockNodo = Integer.parseInt(JOptionPane.showInputDialog("Nuevo stock:"));
                    inventario.actualizarProductoEnListaEnlazada(nombreNodo, nuevoStockNodo);
                    break;

                    
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                 

            }
        }
    }
}
