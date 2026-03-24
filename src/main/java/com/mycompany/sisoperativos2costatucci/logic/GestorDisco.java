package com.mycompany.sisoperativos2costatucci.logic;

import com.mycompany.sisoperativos2costatucci.gui.PanelSD;
import java.awt.Color;

public class GestorDisco {
    
    private PanelSD vistaDisco;
    private Queue colaLibres;
    private int totalBloques;
    
    // OPCIÓN A: El Gestor es dueño del Planificador
    private PlanificadorDisco planificador; 

    // Constructor actualizado
    public GestorDisco(int totalBloques, int cabezalInicial) {
        this.totalBloques = totalBloques;
        this.vistaDisco = new PanelSD(totalBloques);
        this.colaLibres = new Queue("BitMap_Libres");
        
        // Inicializamos el "brazo mecánico"
        this.planificador = new PlanificadorDisco(cabezalInicial);

        // Llenamos la memoria libre
        for (int i = 0; i < totalBloques; i++) {
            this.colaLibres.addBlock(new Block("Vacio", i)); 
        }
    }

    public PanelSD getVistaDisco() {
        return vistaDisco;
    }

    public int getBloquesLibres() {
        return colaLibres.getQueuesize();
    }

    public Queue getColaLibres() {
        return colaLibres;
    }
    
    public PlanificadorDisco getPlanificador() {
        return planificador;
    }

    // =====================================
    // OPERACIONES CRUD DEL DISCO
    // =====================================

public File crearArchivo(int size, String owner, Color color,Queue log) throws Exception {
    File nuevoArchivo = new File(size, colaLibres, owner, log);
    Block bloqueActual = nuevoArchivo.getFirstBlock();
    while (bloqueActual != null) {
        vistaDisco.asignarBloqueVisual(bloqueActual.getId(), color);
        bloqueActual = bloqueActual.getNext();
    }
    return nuevoArchivo; 
}

    public void leerArchivo(File archivo, String politicaPlanificacion) {
        if (archivo == null || archivo.getFirstBlock() == null) return;
        
        Queue peticionesLectura = new Queue("Req_Lectura");
        Block bloqueActual = archivo.getFirstBlock();
        
        while (bloqueActual != null) {
            peticionesLectura.addBlock(new Block("Req", bloqueActual.getId()));
            bloqueActual = bloqueActual.getNext();
        }
        
        System.out.println("\n[GestorDisco] Solicitando LECTURA de archivo de " + archivo.getOwner());
        
        // El usuario de la interfaz puede elegir qué política usar al leer
        if (politicaPlanificacion.equalsIgnoreCase("SSTF")) {
            planificador.ejecutarSSTF(peticionesLectura);
        } else {
            planificador.ejecutarFIFO(peticionesLectura);
        }
    }

    public void eliminarArchivo(File archivo) {
        if (archivo == null || archivo.getFirstBlock() == null) return;

        Queue peticionesBorrado = new Queue("Req_Borrado");
        Block bloqueActual = archivo.getFirstBlock();
        
        while (bloqueActual != null) {
            // Guardamos petición para el planificador
            peticionesBorrado.addBlock(new Block("Req", bloqueActual.getId()));
            
            Block siguiente = bloqueActual.getNext(); 
            vistaDisco.liberarBloqueVisual(bloqueActual.getId()); 
            
            bloqueActual.setInfo("Vacio");
            bloqueActual.setNext(null); 
            colaLibres.addBlock(bloqueActual); 
            
            bloqueActual = siguiente;
        }
        
        System.out.println("\n[GestorDisco] Solicitando BORRADO de archivo de " + archivo.getOwner());
        planificador.ejecutarFIFO(peticionesBorrado); // Simula que el brazo va a borrar los sectores
        
        archivo.setFirstBlock(null);
        archivo.setSizeFile(0);
    }
    
    // Dentro de la clase GestorDisco
public void reiniciarEstructura() {
    // 1. Limpiamos visualmente los cuadritos
    if (this.vistaDisco != null) {
        this.vistaDisco.limpiarTodosLosBloques();
    }

    // 2. Reiniciamos la cola de bloques libres (suponiendo que son 181 bloques)
    this.colaLibres = new Queue("Libres");
    for (int i = 0; i < 181; i++) {
        this.colaLibres.addBlock(new Block(i));
    }
}
public Recovery ejecutarOperacionSegura(File archivo, String operacion) {
    Recovery status = new Recovery();
    try {
        if (operacion.equalsIgnoreCase("CREAR")) {
            // Intento ciego de creación
            Block aux = archivo.getFirstBlock();
            while (aux != null) {
                // Si esto falla (ej. bloque ya ocupado o fuera de rango), salta al catch
                this.vistaDisco.asignarBloqueVisual(aux.getId(), Color.BLUE);
                aux = aux.getNext();
                status.processedCount++;
            }
        } else if (operacion.equalsIgnoreCase("ELIMINAR")) {
            // Intento ciego de eliminación
            this.eliminarArchivo(archivo); 
        }
        status.success = true;
    } catch (Exception e) {
        System.out.println("Falla detectada. Iniciando recuperación semiautomática...");
        // DISPARADOR: Al fallar, ejecutamos la recuperación del log hasta el último punto estable
        status = iniciarRecuperacionSemiautomatica("Fallo en " + operacion + ": " + e.getMessage());
    }
    return status;
}

private Recovery iniciarRecuperacionSemiautomatica(String causa) {
    // Aquí usamos la lógica que definimos antes para recorrer el 'log' 
    // y restaurar la consistencia hasta donde sea posible.
    Recovery rs = recuperarDesdeLog(this.colaLibres, Color.ORANGE); 
    rs.errorMessage = causa; // Preservamos la causa del fallo original
    rs.success = false;      // Marcamos que hubo una falla previa
    return rs;
}
public Recovery recuperarDesdeLog(Queue logQueue, Color colorRecup) {
    Recovery status = new Recovery();
    Row actual = logQueue.getFirstRow();
    
    try {
        while (actual != null) {
            // Operación "Ciega": Intentamos recuperar el bloque del log
            if (actual.getAfterChange() != null) {
                Block b = actual.getAfterChange();
                
                // IMPORTANTE: Aquí no preguntamos 'if (hayEspacio)'.
                // Simplemente intentamos pintar y registrar.
                vistaDisco.asignarBloqueVisual(b.getId(), colorRecup);
                
                // Si tu lógica requiere sacar el bloque de la cola de libres:
                colaLibres.popBlockById(b.getId());
                
                status.processedCount++;
            }
            actual = actual.getNextRow();
        }
        status.success = true;
    } catch (Exception e) {
        // Si el disco visual o la memoria fallan, retornamos lo que llevamos
        status.success = false;
        status.errorMessage = e.getMessage();
    }
    return status;
}

public File cargarArchivoEnPosicionEspecifica(int inicio, int cantidad, String owner, Color color, Queue log) throws Exception {
    // 1. IMPORTANTE: Pasamos 0 como tamaño al constructor.
    // Esto evita que el constructor de File asigne bloques automáticamente desde el inicio.
    File nuevoArchivo = new File(0, colaLibres, owner, log);
    
    // Asignamos el color para que el objeto lo guarde
    nuevoArchivo.color = color; 

    Block anterior = null;

    // 2. Aquí es donde REALMENTE asignamos las posiciones del JSON
    for (int i = 0; i < cantidad; i++) {
        int idBuscado = inicio + i;
        
        // Extraemos el bloque específico (ej: el 11, luego el 12...)
        Block bloqueActual = this.colaLibres.extraerBloquePorId(idBuscado);
        
        if (bloqueActual != null) {
            // Pintamos en la posición exacta que dice el JSON
            vistaDisco.asignarBloqueVisual(bloqueActual.getId(), color);
            
            if (i == 0) {
                nuevoArchivo.setFirstBlock(bloqueActual);
            } else {
                if (anterior != null) {
                    anterior.setNext(bloqueActual);
                }
            }
            anterior = bloqueActual;
        } else {
            System.out.println("Error: El bloque " + idBuscado + " ya no está en la cola de libres.");
        }
    }
    
    return nuevoArchivo; 
}}