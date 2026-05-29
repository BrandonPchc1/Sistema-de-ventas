/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author decab
 */
public class generarReporte {
    private static final int ANCHO_COLUMNA = 20; //ancho del formato
    private static final String CARPETA_REPORTES = "reportes/"; //carpeta donde se guardaran
    
    public static void crearReporte(TableModel modelo, String rootName, String usuario) {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos para generar el reporte");
            return;
        }
        
        BufferedWriter writer = null;
        
        try {
            // Crear carpeta si no existe
            File carpeta = new File(CARPETA_REPORTES);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String nombreArchivo = CARPETA_REPORTES + rootName + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            
            writer = new BufferedWriter(new FileWriter(nombreArchivo));
            
            // Calcular ancho total de la tabla
            int anchoTotal = (ANCHO_COLUMNA * modelo.getColumnCount());
            String separador = crearSeparador(anchoTotal);
            
            // Título
            writer.write(separador);
            writer.newLine();
            writer.write(centrarTexto("MI REPORTE", anchoTotal));
            writer.newLine();
            writer.write(separador);
            writer.newLine();
            writer.write("Usuario: " + usuario);
            writer.newLine();
            writer.write("Fecha:   " + fecha);
            writer.newLine();
            writer.write(separador);
            writer.newLine();
            writer.newLine();
            
            // Encabezados
            String[] encabezados = new String[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                encabezados[i] = modelo.getColumnName(i);
            }
            
            for (int i = 0; i < encabezados.length; i++) {
                writer.write(String.format("%-" + ANCHO_COLUMNA + "s", encabezados[i]));
            }
            writer.newLine();
            
            // Línea separadora de encabezados
            writer.write(crearLineaSeparadora(anchoTotal, '-'));
            writer.newLine();
            
            // Datos y calcular total acumulado
            double totalAcumulado = 0;
            int columnaTotal = -1;
            
            // Encontrar la columna de total (asumiendo que es la última)
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                String columna = modelo.getColumnName(i).toUpperCase();
                if (columna.equals("TOTAL") || columna.equals("TOTAL GENERAL")) {
                    columnaTotal = i;
                    break;
                }
            }
            // Si no encuentra, usa la última columna
            if (columnaTotal == -1) {
                columnaTotal = modelo.getColumnCount() - 1;
            }
            
            for (int row = 0; row < modelo.getRowCount(); row++) {
                List<String[]> filasMultilinea = new ArrayList<>();
                int maxLineas = 1;
                
                // Sumar al total acumulado
                try {
                    Object valorTotal = modelo.getValueAt(row, columnaTotal);
                    double totalFila = Double.parseDouble(valorTotal.toString());
                    totalAcumulado += totalFila;
                } catch (Exception e) {
                    // Si no es número, ignorar
                }
                
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(row, col);
                    String texto = (valor != null ? valor.toString() : "");
                    List<String> lineas = dividirTexto(texto, ANCHO_COLUMNA);
                    filasMultilinea.add(lineas.toArray(new String[0]));
                    if (lineas.size() > maxLineas) maxLineas = lineas.size();
                }
                
                for (int linea = 0; linea < maxLineas; linea++) {
                    for (int col = 0; col < modelo.getColumnCount(); col++) {
                        String[] lineasColumna = filasMultilinea.get(col);
                        String texto = (linea < lineasColumna.length) ? lineasColumna[linea] : "";
                        writer.write(String.format("%-" + ANCHO_COLUMNA + "s", texto));
                    }
                    writer.newLine();
                }
            }
            
            writer.newLine();
            writer.write(separador);
            writer.newLine();
            writer.write("Total de registros: " + modelo.getRowCount());
            writer.newLine();
            writer.write(String.format("Total acumulado:    $%.2f", totalAcumulado));
            writer.newLine();
            writer.write(separador);
            
            writer.close();
            
            Runtime.getRuntime().exec("notepad.exe " + nombreArchivo);
            JOptionPane.showMessageDialog(null, "Reporte guardado en: " + nombreArchivo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //funciones de formato
    private static String crearSeparador(int ancho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ancho; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
    
    private static String crearLineaSeparadora(int ancho, char caracter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ancho; i++) {
            sb.append(caracter);
        }
        return sb.toString();
    }
    
    private static String centrarTexto(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < espacios; i++) {
            sb.append(" ");
        }
        sb.append(texto);
        for (int i = sb.length(); i < ancho; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private static List<String> dividirTexto(String texto, int ancho) {
        List<String> lineas = new ArrayList<>();
        
        if (texto.length() <= ancho) {
            lineas.add(texto);
            return lineas;
        }
        
        String resto = texto;
        while (resto.length() > ancho) {
            int corte = ancho;
            while (corte > 0 && corte < resto.length() && resto.charAt(corte) != ' ') {
                corte--;
            }
            if (corte == 0) corte = ancho;
            
            lineas.add(resto.substring(0, corte));
            resto = resto.substring(corte).trim();
        }
        
        if (!resto.isEmpty()) {
            lineas.add(resto);
        }
        
        return lineas;
    }
}


