package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "ASERRANO", "bucleanidado");
            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "PROFEA1","1234");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    public boolean insertarPosiciones(Posicion pos) throws SQLException {
        String sql = "INSERT INTO posiciones (matricula, posX, posY, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, pos.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setInt(2, pos.getPosX());
        stmt.setInt(3, pos.getPosY());
        stmt.setString(4, pos.getFecha());
        int res = stmt.executeUpdate();
        finalizarConexion();
        return (res == 1);
    }

    public List<Posicion> obtenerPosiciones() throws SQLException {
        ResultSet rset;
        List<Posicion> posiciones = new ArrayList();
        String sql = "SELECT matricula, posX, posY, fecha FROM posiciones";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            posiciones.add(new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha")));
        }
        finalizarConexion();
        return posiciones;
    }

    public List<Posicion> obtenerPosiciones(String matricula) throws SQLException {
        ResultSet rset;
        List<Posicion> posiciones = new ArrayList();
        String sql = "SELECT matricula, posX, posY, fecha FROM posiciones WHERE matricula = ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, matricula);
        rset = stmt.executeQuery();
        while (rset.next()) {
            posiciones.add(new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha")));
        }
        finalizarConexion();
        return posiciones;
    }

    public Posicion obtenerPosicion(String matricula) throws SQLException {
        Posicion pos = null;

        ResultSet rset;

        String sql = "SELECT matricula, posX, posY, fecha FROM posiciones WHERE matricula = ? AND fecha =("
                + "SELECT MAX(fecha) FROM posiciones WHERE matricula = ?)";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, matricula);
        stmt.setString(2, matricula);
        rset = stmt.executeQuery();
        while (rset.next()) {
            pos = new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha"));
        }
        finalizarConexion();
        return pos;

    }

    public boolean actualizarPosicion(Posicion pos) throws SQLException {
        boolean result;
        String sql = "UPDATE posiciones SET posX = ?, posY = ? WHERE matricula = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, pos.getPosX()); //stmt.setString(1, cli.getNombre);
        stmt.setInt(2, pos.getPosY());
        stmt.setString(3, pos.getMatricula());

        int res = stmt.executeUpdate();
        if (res == 0) {
            result = insertarPosiciones(pos);
        } else {
            result = true;
        }
        return (result);
    }
//    public boolean eliminarCliente(int id) throws SQLException {
//
//        String sql = "DELETE FROM cliente WHERE idcliente = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setInt(1, id);
//
//        int res = stmt.executeUpdate();
//
//        return (res == 1);
//    }

    public List<Autobus> obtenerAutobuses() throws SQLException {
        ResultSet rset;
        List<Autobus> autobuses = new ArrayList();
        String sql = "SELECT matricula, password FROM autobuses";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            autobuses.add(new Autobus(rset.getString("matricula"), rset.getString("password")));
        }
        finalizarConexion();
        return autobuses;
    }

}
