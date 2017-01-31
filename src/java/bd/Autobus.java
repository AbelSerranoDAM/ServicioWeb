package bd;

public class Autobus {

    private String matricula;
    private String password;

    public Autobus(String matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

    public Autobus() {
    }

    public String getMatricula() {
        return matricula;
    }

    public String getPassword() {
        return password;
    }

}
