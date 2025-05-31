# Libro Total - Android App

Esta aplicación móvil ha sido desarrollada en **Android Studio con Java** y está conectada a un **backend en PHP** para gestionar una biblioteca digital. Permite a los usuarios administrar categorías, editoriales, autores, libros, lectores y préstamos, además de alertar cuando un libro no ha sido devuelto. También implementa un sistema de roles para el control de acceso.

![Vistas]()

## Características principales
- **Gestión completa** de categorías, editoriales, autores, libros, lectores y usuarios.
- **Control de préstamos y devoluciones**, con alertas cuando un libro no ha sido regresado a tiempo.
- **Sistema de roles** con niveles de acceso:
    - **Administrador**: acceso total a todas las funciones.
    - **Empleado**: acceso restringido a ciertas funcionalidades.
- **Escaneo de códigos QR e ISBN** para la identificación rápida de libros y lectores.
- **Animaciones** en la pantalla de carga para mejorar la experiencia de usuario.

## Tecnologías implementadas
- **Java** para el desarrollo en Android.
- **PHP** y **MySQL** en el backend para la gestión de la base de datos.
- **Lottie** para animaciones en la pantalla de carga.
- **ZXing-Android-Embedded** para el escaneo de códigos QR e ISBN.
- **Volley** para realizar solicitudes HTTP GET al backend.
- **Formato JSON** para la comunicación entre la app y el servidor.

## Instalación
Sigue estos pasos para instalar la aplicación:

1. **Descargar el código**
    - Clonar el repositorio con:
      ```sh
      git clone https://github.com/usuario/proyecto-android.git
      ```
    - O descargar el ZIP y extraerlo en la ubicación deseada.

2. **Configuración**
    - Localizar la clase `Utilidades.java` y modificar la constante `NOMBRE_CARPETA`:
        - Si el backend está en **local**, definir el nombre de la carpeta donde está ubicado.
        - Si el backend está en un **hosting**, actualizar `URL_DOMINIO` con el nombre del dominio.

3. **Instalar dependencias**
    - Agregar las siguientes librerías al `build.gradle`:
      ```gradle
      dependencies {
         implementation 'com.airbnb.android:lottie:6.6.6'
         implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
         implementation 'com.android.volley:volley:1.2.0'
         implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
      }
      ```

4. **Ejecutar la aplicación**
    - Compilar el proyecto en Android Studio.
    - Conectar el dispositivo o usar un emulador para probar la aplicación.

## Acceso
Credenciales por defecto para el administrador:
- **Usuario:** admin
- **Contraseña:** admin123@

## Repositorio del Backend PHP
[Backed-biblio-app-moviles](https://github.com/HLBrandon/backed-biblio-app-moviles "Backed-biblio-app-moviles")