package js4martineztorre.gob.biblioteca.model;

public class Categoria {

    private int id;
    private String nombre;
    private int pasillo;

    public Categoria() {
    }

    public Categoria(int id, String nombre, int pasillo) {
        this.id = id;
        this.nombre = nombre;
        this.pasillo = pasillo;
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
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

    public int getPasillo() {
        return pasillo;
    }

    public void setPasillo(int pasillo) {
        this.pasillo = pasillo;
    }
}
