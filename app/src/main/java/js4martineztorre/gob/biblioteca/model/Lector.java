package js4martineztorre.gob.biblioteca.model;

public class Lector {

    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;
    private String creacion;
    private int estatus;

    public Lector() {

    }

    public Lector(String apellido, String correo, String creacion, String direccion, int estatus, int id, String nombre, String telefono) {
        this.apellido = apellido;
        this.correo = correo;
        this.creacion = creacion;
        this.direccion = direccion;
        this.estatus = estatus;
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Lector(int id, String nombre, String apellido, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
