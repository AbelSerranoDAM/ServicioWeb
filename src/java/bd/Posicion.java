package bd;

public class Posicion {

    private String matricula;
    private int posX;
    private int posY;
    private String fecha;

    public Posicion(String matricula, int posX, int posY, String fecha) {
        this.matricula = matricula;
        this.posX = posX;
        this.posY = posY;
        this.fecha = fecha;
    }

    public Posicion() {
    }

    public String getMatricula() {
        return matricula;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getFecha() {
        return fecha;
    }

}
