package js4martineztorre.gob.biblioteca.model;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String usuario;
    private String contrasenia;
    private int role_id;
    private int estatus;
    private String role_nombre;

    public Usuario() {
    }

    public Usuario(String apellido, String contrasenia, int estatus, int id, String nombre, int role_id, String role_nombre, String telefono, String usuario) {
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.estatus = estatus;
        this.id = id;
        this.nombre = nombre;
        this.role_id = role_id;
        this.role_nombre = role_nombre;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public Usuario(String apellido, String nombre, int id, String role_nombre) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.id = id;
        this.role_nombre = role_nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_nombre() {
        return role_nombre;
    }

    public void setRole_nombre(String role_nombre) {
        this.role_nombre = role_nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
