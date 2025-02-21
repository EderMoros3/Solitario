import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego();
        juego.iniciar(); // Inicia el juego (baraja, reparte cartas, etc.)

        boolean jugando = true;
        while (jugando) {
            System.out.println("\n=== SOLITARIO ===");
            juego.getTablero().mostrarTablero(); // Muestra el estado actual del tablero
            System.out.println("\nOpciones:");
            System.out.println("1. Mover carta entre columnas");
            System.out.println("2. Mover carta a pila final");
            System.out.println("3. Mover carta robada");
            System.out.println("4. Robar carta de la baraja");
            System.out.println("5. Mover carta robada a pila final");
            System.out.println("6. Mover pila de cartas");
            System.out.println("7. Reiniciar juego");
            System.out.println("8. Salir");

            int opcion = -1;
            while (opcion == -1) {
                System.out.print("Elige una opción: ");
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                } catch (InputMismatchException e) {
                    System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 8.");
                    scanner.nextLine(); // Consumir la entrada no válida
                }
            }

            try {
                switch (opcion) {
                    case 1: // Mover carta entre columnas/pilas
                        System.out.print("Ingrese la columna/: ");
                        String origen = "columna" + scanner.nextLine();
                        System.out.print("Ingrese la columna/: ");
                        String destino = "columna" + scanner.nextLine();

                        // Intentar mover la carta
                        if (juego.moverCarta(origen, destino)) {
                            System.out.println("Movimiento realizado con éxito.");
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                        break;

                    case 2: // Mover carta a pila final
                        System.out.print("Ingrese la columna de origen: ");
                        int numOrigen = scanner.nextInt();
                        String pilaOrigen = "columna" + numOrigen; // Formateamos el nombre de la columna

                        System.out.print("Ingrese la pila final de destino (1: Corazones, 2: Diamantes, 3: Treboles, 4: Picas): ");
                        int numDestino = scanner.nextInt();

                        String pilaDestino;
                        switch (numDestino) {
                            case 1:
                                pilaDestino = "corazones";
                                break;
                            case 2:
                                pilaDestino = "diamantes";
                                break;
                            case 3:
                                pilaDestino = "treboles";
                                break;
                            case 4:
                                pilaDestino = "picas";
                                break;
                            default:
                                System.out.println("Pila inválida.");
                                continue;
                        }

                        // Intentar mover la carta directamente
                        Stack<Carta> origenStack = juego.getTablero().obtenerPila(pilaOrigen);
                        Stack<Carta> destinoStack = juego.getTablero().obtenerPilaFinal(pilaDestino);

                        if (origenStack == null || destinoStack == null || origenStack.isEmpty()) {
                            System.out.println("Movimiento inválido.");
                        } else {
                            // Mover la carta
                            destinoStack.push(origenStack.pop());
                            
                            // Voltear la nueva carta en la pila de origen si existe
                            if (!origenStack.isEmpty()) {
                                if (!origenStack.peek().esVisible()) {
                                    origenStack.peek().voltear();
                                }
                            }
                            System.out.println("Movimiento realizado.");
                        }
                        break;

                    case 3: // Mover carta robada
                        System.out.print("Ingrese la columna/pila de destino: ");
                        destino = "columna" + scanner.nextLine().trim();

                        // Intentar mover la carta desde cartas robadas
                        if (juego.moverCarta("cartasRobadas", destino)) {
                            System.out.println("Movimiento realizado con éxito.");
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                        break;

                    case 4: // Robar carta de la baraja
                        try {
                            juego.getTablero().robarCarta(juego.getBaraja());
                        } catch (IllegalStateException e) {
                            System.out.println("No hay más cartas en la baraja.");
                        }
                        break;

                    case 5: // Mover carta robada a pila final
                        System.out.print("Ingrese la pila final de destino (1: Corazones, 2: Diamantes, 3: Treboles, 4: Picas): ");
                        numDestino = scanner.nextInt();

                        switch (numDestino) {
                            case 1:
                                destino = "corazones";
                                break;
                            case 2:
                                destino = "diamantes";
                                break;
                            case 3:
                                destino = "treboles";
                                break;
                            case 4:
                                destino = "picas";
                                break;
                            default:
                                System.out.println("Pila inválida.");
                                continue;
                        }

                        // Intentar mover la carta desde cartas robadas a la pila final
                        if (juego.getTablero().moverCartaRobadaAPilaFinal(destino)) {
                            System.out.println("Movimiento realizado con éxito.");
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                        break;

                    case 6: // Mover pila de cartas
                        System.out.print("Ingrese la columna de origen: ");
                        origen = "columna" + scanner.nextLine();
                        System.out.print("Ingrese la columna de destino: ");
                        destino = "columna" + scanner.nextLine();
                        System.out.print("Ingrese la cantidad de cartas a mover: ");
                        int cantidad = scanner.nextInt();

                        // Intentar mover la pila de cartas
                        if (juego.getTablero().moverPilaDeCartas(origen, destino, cantidad)) {
                            System.out.println("Movimiento realizado con éxito.");
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                        break;

                    case 7: // Reiniciar juego
                        juego.reiniciar();
                        System.out.println("El juego ha sido reiniciado.");
                        break;

                    case 8: // Salir
                        System.out.println("Gracias por jugar. ¡Hasta luego!");
                        jugando = false;
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

                // Verificar si el jugador ha ganado
                if (juego.verificarVictoria()) {
                    System.out.println("\n🎉 ¡Felicidades! Has ganado el Solitario. 🎉");
                    jugando = false;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}