package js4martineztorre.gob.biblioteca.model;

public class Utilidades {

    /**
     * Rutas para configurar con el backend php
     * Es importante que el backed se encuentre en: xampp/htdocs/biblio
     * En caso de encontrarse en esa ruta, cambiar el nombre en la variable NOMBRE_CARPETA
     *
     * Verificar la IP en caso de ser en local
     * Si es un hosting, cambiar el URL_DOMINIO al nombre del dominio
     *
     * */
    public static String NOMBRE_CARPETA = "biblio/";

    /** Raiz del proyecto 10.0.2.2 */
    public static String URL_DOMINIO = "http://192.168.1.70:80/" + NOMBRE_CARPETA;

    /** Archivos del Crud general */
    public static String INDEX = "index.php";
    public static String VER = "ver.php";
    public static String CREAR = "crear.php";
    public static String ACTUALIZAR = "actualizar.php";
    public static String BUSCAR = "buscar.php";

    /******************************************************************************************************************/

    /** Categorias */
    public static String LISTAR_CATEGORIAS = URL_DOMINIO + "categorias/" + INDEX;
    public static String VER_CATEGORIA = URL_DOMINIO + "categorias/" + VER;
    public static String CREAR_CATEGORIA = URL_DOMINIO + "categorias/" + CREAR;
    public static String ACTUALIZAR_CATEGORIA = URL_DOMINIO + "categorias/" + ACTUALIZAR;

    /** Editoriales */
    public static String LISTAR_EDITORIALES = URL_DOMINIO + "editoriales/" + INDEX;
    public static String VER_EDITORIAL = URL_DOMINIO + "editoriales/" + VER;
    public static String CREAR_EDITORIAL = URL_DOMINIO + "editoriales/" + CREAR;
    public static String ACTUALIZAR_EDITORIAL = URL_DOMINIO + "editoriales/" + ACTUALIZAR;

    /** Autores */
    public static String LISTAR_AUTORES = URL_DOMINIO + "autores/" + INDEX;
    public static String VER_AUTOR = URL_DOMINIO + "autores/" + VER;
    public static String CREAR_AUTOR = URL_DOMINIO + "autores/" + CREAR;
    public static String ACTUALIZAR_AUTOR = URL_DOMINIO + "autores/" + ACTUALIZAR;

    /** Libros */
    public static String LISTAR_LIBROS = URL_DOMINIO + "libros/" + INDEX;
    public static String VER_LIBRO = URL_DOMINIO + "libros/" + VER;
    public static String CREAR_LIBRO = URL_DOMINIO + "libros/" + CREAR;
    public static String ACTUALIZAR_LIBRO = URL_DOMINIO + "libros/" + ACTUALIZAR;
    public static String BUSCAR_LIBRO = URL_DOMINIO + "libros/" + BUSCAR;

    /** Usuarios */
    public static String LISTAR_USUARIOS = URL_DOMINIO + "usuarios/" + INDEX;
    public static String VER_USUARIO = URL_DOMINIO + "usuarios/" + VER;
    public static String CREAR_USUARIO = URL_DOMINIO + "usuarios/" + CREAR;
    public static String ACTUALIZAR_USUARIO = URL_DOMINIO + "usuarios/" + ACTUALIZAR;
    public static String ACTIVAR_USUARIO = URL_DOMINIO + "usuarios/activar.php";
    public static String DESACTIVAR_USUARIO = URL_DOMINIO + "usuarios/desactivar.php";

    /** Lectores */
    public static String LISTAR_LECTORES = URL_DOMINIO + "lectores/" + INDEX;
    public static String VER_LECTOR = URL_DOMINIO + "lectores/" + VER;
    public static String CREAR_LECTOR = URL_DOMINIO + "lectores/" + CREAR;
    public static String ACTUALIZAR_LECTOR = URL_DOMINIO + "lectores/" + ACTUALIZAR;
    public static String ACTIVAR_LECTOR = URL_DOMINIO + "lectores/activar.php";
    public static String DESACTIVAR_LECTOR = URL_DOMINIO + "lectores/desactivar.php";

    /** Prestamo */
    public static String LISTAR_PRESTAMOS = URL_DOMINIO + "prestamos/" + INDEX;
    public static String VER_PRESTAMO = URL_DOMINIO + "prestamos/" + VER;
    public static String CREAR_PRESTAMO = URL_DOMINIO + "prestamos/" + CREAR;
    public static String ACTUALIZAR_PRESTAMO = URL_DOMINIO + "prestamos/" + ACTUALIZAR;
    public static String BUSCAR_PRESTAMO = URL_DOMINIO + "prestamos/buscar.php";
    public static String CANCELAR_PRESTAMO = URL_DOMINIO + "prestamos/cancelar.php";
    public static String DEVOLVER_PRESTAMO = URL_DOMINIO + "prestamos/devolver.php";
    public static String VERIFICAR_RETRASOS = URL_DOMINIO + "prestamos/verificar_retrasos.php";
    public static String NOTIFICAR_RETRASOS = URL_DOMINIO + "prestamos/notificar.php";
    public static String PERDER = URL_DOMINIO + "prestamos/perder.php";
    public static String HISTORIAL = URL_DOMINIO + "prestamos/historial.php";

    /** Login */
    public static String LOGIN = URL_DOMINIO + "auth/login.php";

}
