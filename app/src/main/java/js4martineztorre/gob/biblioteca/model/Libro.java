package js4martineztorre.gob.biblioteca.model;

public class Libro {

    private int id;
    private String isbn;
    private String titulo;
    private String autor_nombre;
    private int anio_publicacion;
    private String editorial_nombre;
    private String categoria_nombre;
    private int pasillo;
    private int stoke;
    private int role_id;

    public Libro() {
    }

    public Libro(int anio_publicacion, String autor_nombre, String categoria_nombre, String editorial_nombre, int id, String isbn, int pasillo, int stoke, String titulo) {
        this.anio_publicacion = anio_publicacion;
        this.autor_nombre = autor_nombre;
        this.categoria_nombre = categoria_nombre;
        this.editorial_nombre = editorial_nombre;
        this.id = id;
        this.isbn = isbn;
        this.pasillo = pasillo;
        this.stoke = stoke;
        this.titulo = titulo;
    }

    public Libro(int anio_publicacion, String autor_nombre, String categoria_nombre, String editorial_nombre, int id, String isbn, int stoke, String titulo) {
        this.anio_publicacion = anio_publicacion;
        this.autor_nombre = autor_nombre;
        this.categoria_nombre = categoria_nombre;
        this.editorial_nombre = editorial_nombre;
        this.id = id;
        this.isbn = isbn;
        this.stoke = stoke;
        this.titulo = titulo;
    }

    public Libro(int id, String titulo, String autor_nombre, int role_id) {
        this.titulo = titulo;
        this.id = id;
        this.autor_nombre = autor_nombre;
        this.role_id = role_id;
    }

    public Libro(String titulo, String autor_nombre, int anio_publicacion, String editorial_nombre) {
        this.titulo = titulo;
        this.autor_nombre = autor_nombre;
        this.anio_publicacion = anio_publicacion;
        this.editorial_nombre = editorial_nombre;
    }

    public int getAnio_publicacion() {
        return anio_publicacion;
    }

    public void setAnio_publicacion(int anio_publicacion) {
        this.anio_publicacion = anio_publicacion;
    }

    public String getAutor_nombre() {
        return autor_nombre;
    }

    public void setAutor_nombre(String autor_nombre) {
        this.autor_nombre = autor_nombre;
    }

    public String getCategoria_nombre() {
        return categoria_nombre;
    }

    public void setCategoria_nombre(String categoria_nombre) {
        this.categoria_nombre = categoria_nombre;
    }

    public String getEditorial_nombre() {
        return editorial_nombre;
    }

    public void setEditorial_nombre(String editorial_nombre) {
        this.editorial_nombre = editorial_nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPasillo() {
        return pasillo;
    }

    public void setPasillo(int pasillo) {
        this.pasillo = pasillo;
    }

    public int getStoke() {
        return stoke;
    }

    public void setStoke(int stoke) {
        this.stoke = stoke;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
