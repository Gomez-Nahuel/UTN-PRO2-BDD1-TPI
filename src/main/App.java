package main;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import config.DbConfig;
import dao.ProductoDao;
import entities.Producto;
import entities.CodigoBarras;
import service.ProductoService;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProductoDao productoDao = new ProductoDao();
    private static final ProductoService service = new ProductoService();

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n=== MENU PRODUCTOS ===");
            System.out.println("1. Crear producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto (logico)");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opcion = Integer.parseInt(sc.nextLine());

            try {
                switch (opcion) {
                    case 1 -> crearProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println("Error:");
                e.printStackTrace();
            }

        } while (opcion != 0);
    }

    private static void crearProducto() throws Exception {

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Marca: ");
        String marca = sc.nextLine();

        System.out.print("Categoria: ");
        String categoria = sc.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());

        System.out.print("Peso (opcional, Enter para null): ");
        String pesoStr = sc.nextLine();
        Double peso = pesoStr.isBlank() ? null : Double.parseDouble(pesoStr);

        System.out.print("Codigo tipo (EAN13, EAN8, UPC): ");
        String tipo = sc.nextLine();

        System.out.print("Codigo valor: ");
        String valor = sc.nextLine();

        Producto p = new Producto();
        p.setEliminado(false);
        p.setNombre(nombre);
        p.setMarca(marca);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setPeso(peso);

        CodigoBarras c = new CodigoBarras();
        c.setEliminado(false);
        c.setTipo(tipo);
        c.setValor(valor);
        c.setFechaAsignacion("2026-03-01");
        c.setObservaciones("Alta manual");

        long id = service.crearProductoConCodigo(p, c);

        System.out.println("Producto creado con ID: " + id);
    }

    private static void listarProductos() throws Exception {
        try (Connection conn = DbConfig.getConnection()) {
            List<Producto> lista = productoDao.getAll(conn, false);
            for (Producto p : lista) {
                System.out.println(p.getId() + " | " + p.getNombre() + " | " + p.getPrecio());
            }
        }
    }

    private static void buscarProducto() throws Exception {
        System.out.print("ID: ");
        long id = Long.parseLong(sc.nextLine());

        try (Connection conn = DbConfig.getConnection()) {
            Producto p = productoDao.getById(conn, id);
            if (p == null) {
                System.out.println("No encontrado.");
            } else {
                System.out.println("Nombre: " + p.getNombre());
                System.out.println("Marca: " + p.getMarca());
                System.out.println("Precio: " + p.getPrecio());
            }
        }
    }

    private static void actualizarProducto() throws Exception {
        System.out.print("ID a actualizar: ");
        long id = Long.parseLong(sc.nextLine());

        try (Connection conn = DbConfig.getConnection()) {
            Producto p = productoDao.getById(conn, id);
            if (p == null) {
                System.out.println("No existe.");
                return;
            }

            System.out.print("Nuevo nombre: ");
            p.setNombre(sc.nextLine());

            System.out.print("Nuevo precio: ");
            p.setPrecio(Double.parseDouble(sc.nextLine()));

            productoDao.update(conn, p);
            System.out.println("Actualizado.");
        }
    }

    private static void eliminarProducto() throws Exception {
        System.out.print("ID a eliminar: ");
        long id = Long.parseLong(sc.nextLine());

        try (Connection conn = DbConfig.getConnection()) {
            productoDao.deleteLogico(conn, id);
            System.out.println("Eliminado logico realizado.");
        }
    }
}
