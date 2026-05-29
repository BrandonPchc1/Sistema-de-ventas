
package Clases;

/**
 *
 * @author decab
 */
//clase para mantener sesiones, de nombre Singleton Session Pattern
public class sesionUsuario {
    private static sesionUsuario instancia;
    private String nombre;
    private String password;
    private boolean esAdmin;
    private boolean sesionActiva;
    
    private sesionUsuario() {
        
    }
    
    public static sesionUsuario getInstance() {
        if (instancia == null) { //Acceso global
            instancia = new sesionUsuario();
        }
        return instancia;
    }
    
    public void iniciarSesion(String nombre, String password, boolean esAdmin) {
        this.nombre = nombre;
        this.password = password;
        this.esAdmin = esAdmin;
        this.sesionActiva = true;//activara la sesion
    }
    
    public void cerrarSesion() {
        this.nombre = null;
        this.password = null;
        this.esAdmin = false;
        this.sesionActiva = false; //cerrara la sesion
    }
    
    public String getNombre() { return nombre; }
    public String getPassword() { return password; }
    public boolean isEsAdmin() { return esAdmin; }
    public boolean isSesionActiva() { return sesionActiva; }
    
    public boolean esAdmin() {
        return esAdmin;
    }
}
