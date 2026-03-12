package com.mycompany.sisoperativos2costatucci.logic;

import com.mycompany.sisoperativos2costatucci.logic.*;
import javax.swing.*;
import java.awt.*;

public class MainTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            JFrame ventana = new JFrame("Prueba Oficial de Planificación (Caso PDF)");
            ventana.setSize(800, 800);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            
            // Creamos un disco de 200 bloques porque las peticiones llegan hasta el 180
            // El cabezal inicia en la posición 50
            GestorDisco miDisco = new GestorDisco(200, 50);
            ventana.add(miDisco.getVistaDisco(), BorderLayout.CENTER);
            ventana.setVisible(true);

            new Thread(() -> {
                try {
                    System.out.println("=== INICIO DE PRUEBA OFICIAL ===");
                    System.out.println("Cabezal inicialmente en la posición: 50");
                    System.out.println("Peticiones en cola: 95, 180, 34, 119, 11, 123, 62, 64\n");
                    
                    // Pintamos de rojo los bloques que vamos a solicitar para verlos en la UI
                    marcarBloquesEnUI(miDisco);

                    Thread.sleep(3000); // Pausa para que veas la ventana antes de empezar

                    // ==========================================
                    // PRUEBA 1: FIFO
                    // ==========================================
                    System.out.println("***********************************");
                    miDisco.getPlanificador().setPosicionCabezal(50);
                    miDisco.getPlanificador().ejecutarFIFO(crearColaPDF());
                    Thread.sleep(4000); 

                    // ==========================================
                    // PRUEBA 2: SSTF
                    // ==========================================
                    System.out.println("***********************************");
                    miDisco.getPlanificador().setPosicionCabezal(50);
                    miDisco.getPlanificador().ejecutarSSTF(crearColaPDF());
                    Thread.sleep(4000); 

                    // ==========================================
                    // PRUEBA 3: SCAN (Ascensor hacia arriba)
                    // (Este debería dar: 62 -> 64 -> 95 -> 119 -> 123 -> 180 -> 34 -> 11)
                    // ==========================================
                    System.out.println("***********************************");
                    miDisco.getPlanificador().setPosicionCabezal(50);
                    miDisco.getPlanificador().ejecutarSCAN(crearColaPDF(), true);
                    Thread.sleep(4000); 

                    // ==========================================
                    // PRUEBA 4: C-SCAN (Circular)
                    // (Este debería dar: 62 -> 64 -> 95 -> 119 -> 123 -> 180 -> 11 -> 34)
                    // ==========================================
                    System.out.println("***********************************");
                    miDisco.getPlanificador().setPosicionCabezal(50);
                    miDisco.getPlanificador().ejecutarCSCAN(crearColaPDF());
                    
                    System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
                    
                } catch (InterruptedException e) {
                    System.out.println("Error en hilo.");
                }
            }).start();
        });
    }

    // Método que crea la cola exacta del caso de prueba de tu documento,
    // usando únicamente tu clase Queue y Block, cero arreglos.
    private static Queue crearColaPDF() {
        Queue cola = new Queue("Cola_PDF");
        cola.addBlock(new Block("Req", 95));
        cola.addBlock(new Block("Req", 180));
        cola.addBlock(new Block("Req", 34));
        cola.addBlock(new Block("Req", 119));
        cola.addBlock(new Block("Req", 11));
        cola.addBlock(new Block("Req", 123));
        cola.addBlock(new Block("Req", 62));
        cola.addBlock(new Block("Req", 64));
        return cola;
    }

    // Método solo visual para que sepas dónde están los bloques solicitados
    private static void marcarBloquesEnUI(GestorDisco disco) {
        Queue cola = crearColaPDF();
        Block actual = cola.getFirstBlock(); // o getHead(), según como lo llamaste
        while(actual != null) {
            disco.getVistaDisco().asignarBloqueVisual(actual.getId(), Color.RED);
            actual = actual.getNext();
        }
    }
}