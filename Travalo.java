import javax.swing.JOptionPane;

public class Travalo {

    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        // Agregar productos a través de JOptionPane
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String categoria = JOptionPane.showInputDialog("Ingrese la categoría del producto:");
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock del producto:"));

        Producto producto = new Producto(nombre, categoria, stock);
        inventario.agregarProducto(producto);

        // Mostrar inventario
        inventario.mostrarInventario();

        // Simulamos venta
        String productoVenta = JOptionPane.showInputDialog("Ingrese el nombre del producto a vender:");
        inventario.venderProducto(productoVenta);

        // Ver top productos vendidos
        inventario.mostrarProductosVendidos();

        // Simulamos reposición
        String productoReposicion = JOptionPane.showInputDialog("Ingrese el nombre del producto para reposición:");
        inventario.agregarPedidoReposicion(productoReposicion);
        inventario.procesarPedidosReposicion();
    }
}
