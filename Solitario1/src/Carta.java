public class Carta {
    private int valor;
    private String palo;
    private boolean visible;

    // Constructor
    public Carta(int valor, String palo) {
        this.valor = valor;
        this.palo = palo;
        this.visible = false;
    }

    // Metodo para voltear la carta
    public void voltear() {
        this.visible = !this.visible;
    }

    // Getters y Setters
    public int getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public boolean esVisible() {
        return visible;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Metodo toString
    @Override
    public String toString() {
        if (visible) {
            String valorStr;
            switch (valor) {
                case 11:
                    valorStr = "J";
                    break;
                case 12:
                    valorStr = "Q";
                    break;
                case 13:
                    valorStr = "K";
                    break;
                default:
                    valorStr = String.valueOf(valor);
                    break;
            }
            return valorStr + " de " + palo;
        } else {
            return "[X]";
        }
    }
}