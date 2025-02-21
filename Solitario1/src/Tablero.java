import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Tablero {
    private List<Stack<Carta>> columnas;
    private List<Stack<Carta>> pilas;
    private Stack<Carta> cartasRobadas; // Columna adicional para las cartas robadas

    public Tablero() {
        columnas = new ArrayList<>();
        pilas = new ArrayList<>();
        for (int i = 0; i < 7; i++) columnas.add(new Stack<>());
        for (int i = 0; i < 4; i++) pilas.add(new Stack<>());
        cartasRobadas = new Stack<>(); // Inicializar la columna de cartas robadas
    }

    public void robarCarta(Baraja baraja) {
        try {
            Carta cartaRobada = baraja.robarCarta();
            if (cartaRobada != null) {
                cartasRobadas.push(cartaRobada);
                System.out.println("Carta robada: " + cartaRobada);
            }
        } catch (Exception e) {
            System.out.println("Error al robar carta: " + e.getMessage());
        }
    }

    public boolean moverCarta(String origen, String destino) {
        try {
            Stack<Carta> pilaOrigen = obtenerPila(origen);
            Stack<Carta> pilaDestino = obtenerPila(destino);

            if (pilaOrigen == null || pilaDestino == null || pilaOrigen.isEmpty()) {
                System.out.println("Movimiento inválido: pila de origen o destino no encontrada o vacía.");
                return false;
            }

            Carta carta = pilaOrigen.peek();
            if (validarMovimiento(carta, origen, destino)) {
                pilaDestino.push(pilaOrigen.pop());
                voltearCartaSuperior(pilaOrigen); // Voltear la nueva carta superior si es necesario
                System.out.println("Movimiento realizado con éxito.");
                return true;
            }
            System.out.println("Movimiento inválido: no se cumplen las reglas.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al mover carta: " + e.getMessage());
            return false;
        }
    }

    public boolean moverPilaDeCartas(String origen, String destino, int cantidad) {
        try {
            Stack<Carta> pilaOrigen = obtenerPila(origen);
            Stack<Carta> pilaDestino = obtenerPila(destino);

            if (pilaOrigen == null || pilaDestino == null || pilaOrigen.size() < cantidad) {
                System.out.println("Movimiento inválido: pila de origen o destino no encontrada o no hay suficientes cartas.");
                return false;
            }

            Stack<Carta> subPila = new Stack<>();
            for (int i = 0; i < cantidad; i++) {
                subPila.push(pilaOrigen.pop());
            }

            Carta cartaSuperior = subPila.peek();
            if (validarMovimiento(cartaSuperior, origen, destino)) {
                while (!subPila.isEmpty()) {
                    pilaDestino.push(subPila.pop());
                }
                voltearCartaSuperior(pilaOrigen); // Voltear la nueva carta superior si es necesario
                System.out.println("Movimiento realizado con éxito.");
                return true;
            } else {
                // Devolver las cartas a la pila de origen si el movimiento no es válido
                while (!subPila.isEmpty()) {
                    pilaOrigen.push(subPila.pop());
                }
                System.out.println("Movimiento inválido: no se cumplen las reglas.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al mover pila de cartas: " + e.getMessage());
            return false;
        }
    }
    
    public boolean moverCartaRobadaAPilaFinal(String destino) {
        try {
            Stack<Carta> pilaOrigen = cartasRobadas;
            Stack<Carta> pilaDestino = obtenerPilaFinal(destino);

            if (pilaOrigen == null || pilaDestino == null || pilaOrigen.isEmpty()) {
                System.out.println("Movimiento inválido: pila de origen o destino no encontrada o vacía.");
                return false;
            }

            Carta carta = pilaOrigen.peek();
            if (validarMovimiento(carta, "cartasRobadas", destino)) {
                pilaDestino.push(pilaOrigen.pop());
                voltearCartaSuperior(pilaOrigen); // Voltear la nueva carta superior si es necesario
                System.out.println("Movimiento realizado con éxito.");
                return true;
            }
            System.out.println("Movimiento inválido: no se cumplen las reglas.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al mover carta robada a pila final: " + e.getMessage());
            return false;
        }
    }

    private void voltearCartaSuperior(Stack<Carta> pila) {
        if (!pila.isEmpty()) {
            Carta cartaSuperior = pila.peek();
            if (!cartaSuperior.esVisible()) {
                cartaSuperior.voltear();
            }
        }
    }

    public void mostrarTablero() {
        System.out.println("\nEstado del Tablero:");
        
        // Mostrar pilas finales
        String[] palos = {"Corazones", "Diamantes", "Treboles", "Picas"};
        for (int i = 0; i < pilas.size(); i++) {
            System.out.print("Pila " + palos[i] + ": ");
            if (!pilas.get(i).isEmpty()) {
                System.out.println(pilas.get(i).peek());
            } else {
                System.out.println("[Vacía]");
            }
        }

        // Mostrar columnas
        for (int i = 0; i < columnas.size(); i++) {
            System.out.print("Columna " + (i + 1) + ": ");
            for (Carta carta : columnas.get(i)) System.out.print(carta + " ");
            System.out.println();
        }

        // Mostrar cartas robadas
        System.out.print("Cartas robadas: ");
        if (!cartasRobadas.isEmpty()) {
            System.out.println(cartasRobadas.peek());
        } else {
            System.out.println("[Vacía]");
        }
    }

    public Stack<Carta> obtenerPilaFinal(String palo) {
        switch (palo.toLowerCase()) {
            case "corazones": return pilas.get(0);
            case "diamantes": return pilas.get(1);
            case "treboles": return pilas.get(2);
            case "picas": return pilas.get(3);
        }
        System.out.println("No se encontró la pila final: " + palo);
        return null;
    }
    
    public Stack<Carta> obtenerPila(String nombre) {
        if (nombre.startsWith("columna")) {
            int indice = Integer.parseInt(nombre.replace("columna", "")) - 1;
            if (indice >= 0 && indice < columnas.size()) {
                return columnas.get(indice);
            }
        } else if (nombre.equals("cartasRobadas")) {
            return cartasRobadas;
        } else {
            switch (nombre.toLowerCase()) {
                case "corazones": return pilas.get(0);
                case "diamantes": return pilas.get(1);
                case "treboles": return pilas.get(2);
                case "picas": return pilas.get(3);
            }
        }
        System.out.println("No se encontró la pila: " + nombre);
        return null;
    }

    private boolean validarMovimiento(Carta carta, String origen, String destino) {
        Stack<Carta> pilaDestino = obtenerPila(destino);

        if (pilaDestino == null || carta == null) {
            System.out.println("Movimiento inválido: pila de destino no encontrada o carta nula.");
            return false;
        }

        if (destino.equals("corazones") || destino.equals("diamantes") || destino.equals("treboles") || destino.equals("picas")) {
            if (pilaDestino.isEmpty()) {
                return carta.getValor() == 1;
            } else {
                Carta cartaDestino = pilaDestino.peek();
                return carta.getPalo().equals(cartaDestino.getPalo()) && carta.getValor() == cartaDestino.getValor() + 1;
            }
        } else if (destino.startsWith("columna")) {
            if (pilaDestino.isEmpty()) {
                return carta.getValor() == 13;
            } else {
                Carta cartaDestino = pilaDestino.peek();
                // Verificar que las cartas sean de distinto color y la carta que se mueve sea un valor menor
                if (sonDelMismoColor(carta, cartaDestino)) return false;
                return carta.getValor() == cartaDestino.getValor() - 1;
            }
        }

        System.out.println("Movimiento inválido: destino no válido.");
        return false;
    }

    private boolean sonDelMismoColor(Carta carta1, Carta carta2) {
        return (esRojo(carta1) && esRojo(carta2)) || (!esRojo(carta1) && !esRojo(carta2));
    }

    private boolean esRojo(Carta carta) {
        return carta.getPalo().equals("Corazones") || carta.getPalo().equals("Diamantes");
    }

    public boolean verificarVictoria() {
        for (Stack<Carta> pila : pilas) {
            if (pila.size() < 13) return false;
        }
        return true;
    }

    public List<Stack<Carta>> getColumnas() {
        return columnas;
    }

    public List<Stack<Carta>> getPilas() {
        return pilas;
    }

    public Stack<Carta> getCartasRobadas() {
        return cartasRobadas;
    }
}