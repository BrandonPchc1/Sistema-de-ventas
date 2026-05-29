/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos.baseProductos;

import Clases.sesionUsuario;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author decab
 */
//Clase para las bases de datos de los productos, hecha con SQLlite
public class productCrud {
    private static final String CARPETA = "data";
    private static final String URL = "jdbc:sqlite:" + CARPETA + "/Productos.db";
    private static Connection connection = null;
    //Conectar a la BD
    public static Connection connect() {
        try {
            File folder = new File(CARPETA);
            if (!folder.exists()) {
                folder.mkdir();
            }
            connection = DriverManager.getConnection(URL);
            return connection;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
    
    //Crear la base de datos
    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" 
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "usuario TEXT NOT NULL, "           
                   + "nombre TEXT NOT NULL, "
                   + "descripcion TEXT,"
                   + "cantidad INT NOT NULL,"
                   + "precio DOUBLE NOT NULL,"
                   + "total DOUBLE NOT NULL,"
                   + "UNIQUE(usuario, nombre))";        
        
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Cerrar la conexion
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Obtener usuario actual de la sesión
    private static String getUsuarioActual() {
        return sesionUsuario.getInstance().getNombre();
    }
    /*Funcion que determina si se actualiza o se crea el producto,
    lo hice asi para evitar que se creen varios productos repetidos, en caso de que se repita
    simplemente se modificara*/
    public static void crearOActualizarProducto(String nombre, String descripcion, int cantidad, double precio, double total) {
        String usuario = getUsuarioActual();
        if (usuario == null || usuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuario logueado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (buscarProducto(nombre, usuario)) {
            // Si existe, reemplazar 
            actualizarProducto(nombre, descripcion, cantidad, precio, total, usuario);
        } else {
            // Si no existe, crear nuevo
            insertarProducto(nombre, descripcion, cantidad, precio, total, usuario);
        }
    }
    
    // Buscar producto por nombre y usuario
    private static boolean buscarProducto(String nombre, String usuario) {
        String sql = "SELECT * FROM productos WHERE usuario = ? AND nombre = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Modificar producto existente
    private static void actualizarProducto(String nombre, String descripcion, int cantidad, double precio, double total, String usuario) {
        String sql = "UPDATE productos SET descripcion = ?, cantidad = ?, precio = ?, total = ? WHERE usuario = ? AND nombre = ?";
        
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, descripcion);
            pstmt.setInt(2, cantidad);        
            pstmt.setDouble(3, precio);
            pstmt.setDouble(4, total);
            pstmt.setString(5, usuario);
            pstmt.setString(6, nombre);
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Producto actualizado con éxito", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Crear producto inexistente
    private static void insertarProducto(String nombre, String descripcion, int cantidad, double precio, double total, String usuario) {
        String sql = "INSERT INTO productos (usuario, nombre, descripcion, cantidad, precio, total) VALUES (?,?,?,?,?,?)";
        
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, cantidad);
            pstmt.setDouble(5, precio);
            pstmt.setDouble(6, total);
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Producto creado con éxito." , 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Leer solo los productos del usuario actual
    public static void leerMisProductos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        String usuario = getUsuarioActual();
        
        if (usuario == null || usuario.isEmpty()) {
            return;
        }
        
        String sql = "SELECT nombre, descripcion, cantidad, precio, total FROM productos WHERE usuario = ? ORDER BY nombre";
        
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getDouble("total")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Leer todos los productos (SOLO PARA ADMIN)
    public static void leerTodosProductos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        
        String sql = "SELECT usuario, nombre, descripcion, cantidad, precio, total FROM productos ORDER BY usuario, nombre";
        
        try (PreparedStatement pstmt = connect().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("usuario"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio"),
                    rs.getDouble("total")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Borrar productos
    public static void borrarProducto(String nombre){
        String usuario = getUsuarioActual(); //Obtiene que usuario lo esta pidiendo
        String sql = "DELETE FROM productos where usuario = ? AND nombre = ?";
        
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, nombre);
            
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Producto eliminado con éxito." , 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

