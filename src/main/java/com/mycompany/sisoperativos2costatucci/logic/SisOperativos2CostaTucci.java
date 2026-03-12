package com.mycompany.sisoperativos2costatucci;

import com.mycompany.sisoperativos2costatucci.logic.GestorDisco;
import com.mycompany.sisoperativos2costatucci.logic.File;
import javax.swing.*;
import java.awt.*;

public class SisOperativos2CostaTucci {

    public static void main(String[] args) {
        // Ejecutamos la interfaz gráfica de forma segura
        SwingUtilities.invokeLater(() -> {
            
            // 1. Creamos una ventana básica manualmente
            JFrame ventana = new JFrame("Prueba Limpia del Gestor de Disco");
            ventana.setSize(500, 500);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            ventana.setLayout(new BorderLayout());

            // 2. Instanciamos nuestro "Cerebro" (el GestorDisco con 100 bloques)
            GestorDisco miDisco = new GestorDisco(100);

            // 3. Le pedimos al gestor su panel visual y lo pegamos en la ventana
            ventana.add(miDisco.getVistaDisco(), BorderLayout.CENTER);
            
            // Mostramos la ventana
            ventana.setVisible(true);

            // --- PRUEBA LÓGICA Y VISUAL ---
            
            System.out.println("Bloques libres iniciales: " + miDisco.getBloquesLibres()); // 100

            // 4. Creamos archivos usando el Gestor (ya no tocamos la cola manualmente)
            File archivoAzul = miDisco.crearArchivo(5, "Admin", new Color(70, 130, 180));
            File archivoVerde = miDisco.crearArchivo(8, "User", new Color(60, 179, 113));
            
            System.out.println("Bloques libres tras crear: " + miDisco.getBloquesLibres()); // 87

            // 5. Usamos un Timer para eliminar el azul a los 3 segundos y ver si se limpia
            Timer timer = new Timer(3000, e -> {
                System.out.println("\nBorrando el archivo Azul...");
                miDisco.eliminarArchivo(archivoAzul);
                System.out.println("Bloques libres tras borrar: " + miDisco.getBloquesLibres()); // 92
            });
            timer.setRepeats(false);
            timer.start();
            
        });
    }
}