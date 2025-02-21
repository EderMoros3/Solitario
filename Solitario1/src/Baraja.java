import java.util.*;

public class Baraja {
    private Stack<Carta> cartas;
    private Stack<Carta> cartasRobadas;
    private String[] palos = {"Corazones", "Diamantes", "Treboles", "Picas"};

    public Baraja() {
        this.cartas = new Stack<>();
        this.cartasRobadas = new Stack<>();
        for (int i = 1; i <= 13; i++) {
            for (String palo : palos) {
                this.cartas.push(new Carta(i, palo));
            }
        }
        barajar();
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta robarCarta() {
        try {
            if (cartas.isEmpty() && !cartasRobadas.isEmpty()) {
                // Mover todas las cartas robadas de vuelta a la baraja
                while (!cartasRobadas.isEmpty()) {
                    Carta carta = cartasRobadas.pop();
                    if (carta.esVisible()) {
                        carta.voltear();
                    }
                    cartas.push(carta);
                }
            }

            if (!cartas.isEmpty()) {
                Carta cartaRobada = cartas.pop();
                cartaRobada.voltear();
                cartasRobadas.push(cartaRobada);
                return cartaRobada;
            }
            throw new IllegalStateException("No hay más cartas en la baraja.");
        } catch (Exception e) {
            System.out.println("Error al robar carta: " + e.getMessage());
            return null;
        }
    }

    public void repartir(Tablero tablero) {
        try {
            for (int i = 0; i < 7; i++) {
                for (int j = i; j < 7; j++) {
                    Carta carta = cartas.pop();
                    if (j == i) carta.voltear();
                    tablero.getColumnas().get(j).push(carta);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al repartir cartas: " + e.getMessage());
        }
    }

    public Stack<Carta> getCartasRobadas() {
        return cartasRobadas;
    }
}