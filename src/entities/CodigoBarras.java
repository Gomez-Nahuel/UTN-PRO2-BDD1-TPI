package entities;

public class CodigoBarras {
    private long id;
    private boolean eliminado;
    private String tipo;
    private String valor;
    private String fechaAsignacion; // simple para ahora (YYYY-MM-DD)
    private String observaciones;
    private long productoId;

    public CodigoBarras() {}

    public CodigoBarras(long id, boolean eliminado, String tipo, String valor, String fechaAsignacion, String observaciones, long productoId) {
        this.id = id;
        this.eliminado = eliminado;
        this.tipo = tipo;
        this.valor = valor;
        this.fechaAsignacion = fechaAsignacion;
        this.observaciones = observaciones;
        this.productoId = productoId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(String fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public long getProductoId() { return productoId; }
    public void setProductoId(long productoId) { this.productoId = productoId; }
}
