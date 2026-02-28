package entities;

public class Producto {
    private long id;
    private boolean eliminado;
    private String nombre;
    private String marca;
    private String categoria;
    private double precio;
    private Double peso; // puede ser null

    public Producto() {}

    public Producto(long id, boolean eliminado, String nombre, String marca, String categoria, double precio, Double peso) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.peso = peso;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
}