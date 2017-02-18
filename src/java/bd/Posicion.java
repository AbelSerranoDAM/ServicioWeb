package bd;

public class Posicion {

    private String matricula;
    private double posX;
    private double posY;
    private String fecha;

    public Posicion(String matricula, double posX, double posY, String fecha) {
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

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public String getFecha() {
        return fecha;
    }

}
