package com.mycompany.sisoperativos2costatucci.logic;

import com.mycompany.sisoperativos2costatucci.gui.PanelSD;
import java.awt.Color;

public class GestorDisco {

    private PanelSD vistaDisco;
    private Queue colaLibres;
    private int totalBloques;

    // El constructor prepara el disco vacío cuando lo instancias desde tu JFrame
    public GestorDisco(int totalBloques) {
        this.totalBloques = totalBloques;
        this.vistaDisco = new PanelSD(totalBloques);
        this.colaLibres = new Queue("BitMap_Libres");

        // Llenamos la cola con los bloques vacíos
        for (int i = 0; i < totalBloques; i++) {
            this.colaLibres.addBlock(new Block("Vacio", i));
        }
    }

    // Método para que tu JFrame pueda obtener el panel visual y mostrarlo
    public PanelSD getVistaDisco() {
        return vistaDisco;
    }

    // Método para que tu JFrame pueda saber cuántos bloques quedan libres
    public int getBloquesLibres() {
        return colaLibres.getQueuesize();
    }

    // Método a llamar cuando el usuario presione el botón "Crear Archivo"
    public File crearArchivo(int size, String owner, Color color) {
        // 1. Validar si hay espacio suficiente
        if (colaLibres.getQueuesize() < size) {
            System.out.println("Error: No hay espacio suficiente en el disco.");
            return null;
        }

        // 2. Crear el archivo lógicamente (esto extrae los bloques de la colaLibres)
        File nuevoArchivo = new File(size, colaLibres, owner);

        // 3. Pintarlo en la interfaz gráfica
        Block bloqueActual = nuevoArchivo.getFirstBlock();
        while (bloqueActual != null) {
            vistaDisco.asignarBloqueVisual(bloqueActual.getId(), color);
            bloqueActual = bloqueActual.getNext();
        }

        return nuevoArchivo; // Retornamos el archivo por si el JTree o alguien más lo necesita
    }

    // Método a llamar cuando el usuario presione el botón "Eliminar Archivo"
    public void eliminarArchivo(File archivo) {
        if (archivo == null || archivo.getFirstBlock() == null) {
            return; // No hay nada que borrar
        }

        Block bloqueActual = archivo.getFirstBlock();

        while (bloqueActual != null) {
            Block siguiente = bloqueActual.getNext();

            vistaDisco.liberarBloqueVisual(bloqueActual.getId()); // Pintar de blanco

            bloqueActual.setInfo("Vacio");
            bloqueActual.setNext(null);
            colaLibres.addBlock(bloqueActual); // Devolver a la cola de libres

            bloqueActual = siguiente;
        }

        archivo.setFirstBlock(null);
        archivo.setSizeFile(0);
    }
}
