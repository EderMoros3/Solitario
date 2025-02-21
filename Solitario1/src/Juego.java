public class Juego {
    private Tablero tablero;
    private Baraja baraja;

    public Juego() {
        tablero = new Tablero();
        baraja = new Baraja();
        baraja.repartir(tablero);
    }

    public void iniciar() {
        tablero = new Tablero();
        baraja = new Baraja();
        baraja.repartir(tablero);
    }

    public void reiniciar() {
        tablero = new Tablero();
        baraja = new Baraja();
        baraja.repartir(tablero);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Baraja getBaraja() {
        return baraja;
    }

    public boolean moverCarta(String origen, String destino) {
        return tablero.moverCarta(origen, destino);
    }

    public boolean verificarVictoria() {
        return tablero.verificarVictoria();
    }
}