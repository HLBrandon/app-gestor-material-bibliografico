package js4martineztorre.gob.biblioteca.model;

public class Prestamo {
    private int id;
    private String lector_nombre;
    private String lector_apellido;
    private String lector_correo;
    private String lector_telefono;
    private String lector_direccion;

    private String libro_isbn;
    private String libro_titulo;
    private String autor_nombre;

    private String usuario_nombre;
    private String usuario_apellido;

    private String fecha_prestamo;
    private String fecha_devolucion;

    private Boolean retraso;

    private int estatus;
    private int perder;

    public Prestamo() {
    }

    // Todos los datos
    public Prestamo(String autor_nombre, String fecha_devolucion, String fecha_prestamo, int id, String lector_apellido, String lector_correo, String lector_direccion, String lector_nombre, String lector_telefono, String libro_isbn, String libro_titulo, String usuario_apellido, String usuario_nombre, Boolean retraso, int estatus) {
        this.autor_nombre = autor_nombre;
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_prestamo = fecha_prestamo;
        this.id = id;
        this.lector_apellido = lector_apellido;
        this.lector_correo = lector_correo;
        this.lector_direccion = lector_direccion;
        this.lector_nombre = lector_nombre;
        this.lector_telefono = lector_telefono;
        this.libro_isbn = libro_isbn;
        this.libro_titulo = libro_titulo;
        this.usuario_apellido = usuario_apellido;
        this.usuario_nombre = usuario_nombre;
        this.retraso = retraso;
        this.estatus = estatus;
    }

    // Constructor para las tarjetas
    public Prestamo(int id, String lector_nombre, String lector_apellido, String lector_correo, String libro_titulo, String libro_isbn, Boolean retraso) {
        this.id = id;
        this.lector_nombre = lector_nombre;
        this.lector_apellido = lector_apellido;
        this.lector_correo = lector_correo;
        this.libro_titulo = libro_titulo;
        this.libro_isbn = libro_isbn;
        this.retraso = retraso;
    }

    // Constructor para el historial de prestamos
    public Prestamo(int id, String lector_nombre, String lector_apellido, String lector_correo, String libro_titulo, String libro_isbn, int estatus, int perder, String fecha_prestamo, String fecha_devolucion) {
        this.id = id;
        this.lector_nombre = lector_nombre;
        this.lector_apellido = lector_apellido;
        this.lector_correo = lector_correo;
        this.libro_titulo = libro_titulo;
        this.libro_isbn = libro_isbn;
        this.estatus = estatus;
        this.perder = perder;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getAutor_nombre() {
        return autor_nombre;
    }

    public void setAutor_nombre(String autor_nombre) {
        this.autor_nombre = autor_nombre;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLector_apellido() {
        return lector_apellido;
    }

    public void setLector_apellido(String lector_apellido) {
        this.lector_apellido = lector_apellido;
    }

    public String getLector_correo() {
        return lector_correo;
    }

    public void setLector_correo(String lector_correo) {
        this.lector_correo = lector_correo;
    }

    public String getLector_direccion() {
        return lector_direccion;
    }

    public void setLector_direccion(String lector_direccion) {
        this.lector_direccion = lector_direccion;
    }

    public String getLector_nombre() {
        return lector_nombre;
    }

    public void setLector_nombre(String lector_nombre) {
        this.lector_nombre = lector_nombre;
    }

    public String getLector_telefono() {
        return lector_telefono;
    }

    public void setLector_telefono(String lector_telefono) {
        this.lector_telefono = lector_telefono;
    }

    public String getLibro_isbn() {
        return libro_isbn;
    }

    public void setLibro_isbn(String libro_isbn) {
        this.libro_isbn = libro_isbn;
    }

    public String getLibro_titulo() {
        return libro_titulo;
    }

    public void setLibro_titulo(String libro_titulo) {
        this.libro_titulo = libro_titulo;
    }

    public String getUsuario_apellido() {
        return usuario_apellido;
    }

    public void setUsuario_apellido(String usuario_apellido) {
        this.usuario_apellido = usuario_apellido;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public Boolean getRetraso() {
        return retraso;
    }

    public void setRetraso(Boolean retraso) {
        this.retraso = retraso;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getPerder() {
        return perder;
    }

    public void setPerder(int perder) {
        this.perder = perder;
    }
}
