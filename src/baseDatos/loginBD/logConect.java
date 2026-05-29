/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos.loginBD;

import java.io.File;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author decab
 */
//Clase para la base de datos de usuarios, hecha con SQLlite
public class logConect {
    private static final String CARPETA = "data";
    private static final String URL = "jdbc:sqlite:"+CARPETA+ "/usuarios.db";
    private static Connection connection = null;
    
    // Conectar a la BD
    public static Connection connect() {
        try {
            File folder = new File(CARPETA);
            if (!folder.exists()) {
                folder.mkdir();
            }
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
    
    // Crear tablas al iniciar
    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "nombre TEXT NOT NULL, "
                   + "password TEXT NOT NULL)";
        
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(sql);
            
            // Insertar usuario por defecto si no hay
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuarios");
            if (rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO usuarios (nombre, password) VALUES ('admin', '1234')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Validar login
    public static boolean validarLogin(String nombre, String password) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cerrar conexión
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Crear usuario
    public static boolean crearUsuario(String nombre,String password){
        String sql = "INSERT INTO usuarios (nombre, password) VALUES (?,?)" ;
        if (buscarUsuario(nombre)){
            JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error de usuario", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try (PreparedStatement pstmt = connect().prepareStatement(sql)){
            pstmt.setString(1, nombre);
            pstmt.setString(2, password); 
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0){
                JOptionPane.showMessageDialog(null, "Usuario creado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Funcion que sirve para saber si un usuario existe
    private static boolean buscarUsuario(String nombre){
        String sql = "Select * from usuarios where nombre = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)){
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //funcion para lectura de usuarios (para tablas)
    public static void lecturaUsers(DefaultTableModel modelo){
        modelo.setRowCount(0);
        
        String sql = "SELECT * FROM usuarios";
        
        try (Connection con = connect();
            PreparedStatement pstmt = connect().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
            Object[] fila = {
               rs.getInt("id"),
               rs.getString("nombre"),
               rs.getString("password")
           };
           modelo.addRow(fila);
       }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

