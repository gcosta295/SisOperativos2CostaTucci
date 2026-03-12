package com.mycompany.sisoperativos2costatucci.logic;

public class PlanificadorDisco {

    private int posicionCabezal;

    public PlanificadorDisco(int posicionInicial) {
        this.posicionCabezal = posicionInicial;
    }

    public int getPosicionCabezal() {
        return posicionCabezal;
    }

    public void setPosicionCabezal(int posicion) {
        this.posicionCabezal = posicion;
    }

    // ==========================================
    // 1. ALGORITMO FIFO
    // ==========================================
    public int ejecutarFIFO(Queue peticiones) {
        System.out.println("--- Ejecutando Planificación FIFO ---");
        int operacionesTotales = 0;
        Block actual = peticiones.getFirstBlock();

        while (actual != null) {
            int destino = actual.getId();
            int distancia = Math.abs(destino - posicionCabezal);
            operacionesTotales += distancia;

            System.out.println("-> Cabezal movido de " + posicionCabezal + " a " + destino);
            posicionCabezal = destino;
            actual = actual.getNext();
        }
        return operacionesTotales;
    }

    // ==========================================
    // 2. ALGORITMO SSTF (El más cercano primero)
    // ==========================================
    public int ejecutarSSTF(Queue peticiones) {
        System.out.println("--- Ejecutando Planificación SSTF ---");
        int operacionesTotales = 0;
        int totalPeticiones = peticiones.getQueuesize();
        int peticionesAtendidas = 0;

        marcarTodosComoPendientes(peticiones);

        while (peticionesAtendidas < totalPeticiones) {
            int distanciaMinima = Integer.MAX_VALUE;
            Block bloqueMasCercano = null;

            Block actual = peticiones.getFirstBlock();
            while (actual != null) {
                if (actual.getInfo().equals("Pendiente")) {
                    int distancia = Math.abs(actual.getId() - posicionCabezal);
                    if (distancia < distanciaMinima) {
                        distanciaMinima = distancia;
                        bloqueMasCercano = actual;
                    }
                }
                actual = actual.getNext();
            }

            if (bloqueMasCercano != null) {
                operacionesTotales += procesarBloque(bloqueMasCercano);
                peticionesAtendidas++;
            }
        }
        return operacionesTotales;
    }

    // ==========================================
    // 3. ALGORITMO SCAN (Ascensor)
    // ==========================================
    public int ejecutarSCAN(Queue peticiones, boolean direccionArriba) {
        System.out.println("--- Ejecutando Planificación SCAN (Ascensor) ---");
        int operacionesTotales = 0;
        int totalPeticiones = peticiones.getQueuesize();
        int peticionesAtendidas = 0;
        boolean moviendoArriba = direccionArriba;

        marcarTodosComoPendientes(peticiones);

        while (peticionesAtendidas < totalPeticiones) {
            Block mejorBloque = null;

            if (moviendoArriba) {
                // Buscamos el más cercano que sea MAYOR o IGUAL a la posición actual
                int minCercano = Integer.MAX_VALUE;
                Block actual = peticiones.getFirstBlock();
                while (actual != null) {
                    if (actual.getInfo().equals("Pendiente") && actual.getId() >= posicionCabezal) {
                        if (actual.getId() < minCercano) {
                            minCercano = actual.getId();
                            mejorBloque = actual;
                        }
                    }
                    actual = actual.getNext();
                }

                if (mejorBloque != null) {
                    operacionesTotales += procesarBloque(mejorBloque);
                    peticionesAtendidas++;
                } else {
                    // Si ya no hay mayores, cambiamos de dirección hacia abajo
                    moviendoArriba = false;
                }
            } else {
                // Buscamos el más cercano que sea MENOR o IGUAL a la posición actual
                int maxCercano = -1;
                Block actual = peticiones.getFirstBlock();
                while (actual != null) {
                    if (actual.getInfo().equals("Pendiente") && actual.getId() <= posicionCabezal) {
                        if (actual.getId() > maxCercano) {
                            maxCercano = actual.getId();
                            mejorBloque = actual;
                        }
                    }
                    actual = actual.getNext();
                }

                if (mejorBloque != null) {
                    operacionesTotales += procesarBloque(mejorBloque);
                    peticionesAtendidas++;
                } else {
                    moviendoArriba = true;
                }
            }
        }
        return operacionesTotales;
    }

    // ==========================================
    // 4. ALGORITMO C-SCAN (Ascensor Circular)
    // ==========================================
    public int ejecutarCSCAN(Queue peticiones) {
        System.out.println("--- Ejecutando Planificación C-SCAN (Circular) ---");
        int operacionesTotales = 0;
        int totalPeticiones = peticiones.getQueuesize();
        int peticionesAtendidas = 0;

        marcarTodosComoPendientes(peticiones);

        while (peticionesAtendidas < totalPeticiones) {
            Block mejorBloque = null;

            // C-SCAN siempre sube, buscamos el más cercano MAYOR o IGUAL
            int minCercano = Integer.MAX_VALUE;
            Block actual = peticiones.getFirstBlock();
            while (actual != null) {
                if (actual.getInfo().equals("Pendiente") && actual.getId() >= posicionCabezal) {
                    if (actual.getId() < minCercano) {
                        minCercano = actual.getId();
                        mejorBloque = actual;
                    }
                }
                actual = actual.getNext();
            }

            if (mejorBloque != null) {
                operacionesTotales += procesarBloque(mejorBloque);
                peticionesAtendidas++;
            } else {
                // Si ya no hay mayores, damos un salto al MÁS PEQUEÑO de toda la cola
                int minimoAbsoluto = Integer.MAX_VALUE;
                Block bloqueReinicio = null;
                actual = peticiones.getFirstBlock();
                while (actual != null) {
                    if (actual.getInfo().equals("Pendiente") && actual.getId() < minimoAbsoluto) {
                        minimoAbsoluto = actual.getId();
                        bloqueReinicio = actual;
                    }
                    actual = actual.getNext();
                }

                if (bloqueReinicio != null) {
                    System.out.println("-> [Salto C-SCAN al inicio del disco]");
                    operacionesTotales += procesarBloque(bloqueReinicio);
                    peticionesAtendidas++;
                }
            }
        }
        return operacionesTotales;
    }

    // --- MÉTODOS AUXILIARES ---
    private void marcarTodosComoPendientes(Queue peticiones) {
        Block temp = peticiones.getFirstBlock();
        while (temp != null) {
            temp.setInfo("Pendiente");
            temp = temp.getNext();
        }
    }

    private int procesarBloque(Block bloque) {
        int destino = bloque.getId();
        bloque.setInfo("Visitado"); // Lo marcamos para no volverlo a procesar
        int distancia = Math.abs(destino - posicionCabezal);
        System.out.println("-> Cabezal movido de " + posicionCabezal + " a " + destino + " (Distancia: " + distancia + ")");
        posicionCabezal = destino;
        return distancia;
    }
}
