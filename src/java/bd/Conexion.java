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
            //connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "ASERRANO", "bucleanidado");
             connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "ASERRANO","bucleanidado");
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

    public boolean insertarPosicion(Posicion pos) throws SQLException {
        String sql = "INSERT INTO posiciones (matricula, posX, posY, fecha) VALUES (?, ?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'))";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, pos.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setDouble(2, pos.getPosX());
        stmt.setDouble(3, pos.getPosY());
        stmt.setString(4, pos.getFecha());
        int res = stmt.executeUpdate();
        finalizarConexion();
        return (res == 1);
    }

//    public boolean insertarPosicion(ArrayList<Posicion> posiciones) throws SQLException {
//        String sql = "INSERT INTO posiciones (matricula, posX, posY, fecha) VALUES (?, ?, ?, ?)";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        int res = 0;
//        for (int i = 0; i < posiciones.size(); i++) {
//            stmt.setString(1, posiciones.get(i).getMatricula()); //stmt.setString(1, cli.getNombre);
//            stmt.setInt(2, posiciones.get(i).getPosX());
//            stmt.setInt(3, posiciones.get(i).getPosY());
//            stmt.setString(4, posiciones.get(i).getFecha());
//            res = stmt.executeUpdate();
//        }
//        finalizarConexion();
//        return (res == 1);
//    }
    public List<Posicion> obtenerPosiciones() throws SQLException {
        ResultSet rset;
        List<Posicion> posiciones = new ArrayList();
        String sql = "SELECT matricula, posX, posY, TO_CHAR(fecha, 'dd-mm-yyyy hh24:mi:ss') fecha  FROM posiciones";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            posiciones.add(new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha")));
        }
        finalizarConexion();
        return posiciones;
    }

    public List<Posicion> obtenerPosiciones(String matricula, String fecha_inicio, String fecha_fin) throws SQLException {
        ResultSet rset;
        List<Posicion> posiciones = new ArrayList();
        String sql = "SELECT matricula, posX, posY, TO_CHAR(fecha, 'dd-mm-yyyy hh24:mi:ss') fecha FROM posiciones WHERE matricula = ? AND fecha BETWEEN TO_DATE(?, 'dd/mm/yyyy hh24:mi:ss')"
                + " AND TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss')";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, matricula);
        stmt.setString(2, fecha_inicio);
        stmt.setString(3, fecha_fin);
        rset = stmt.executeQuery();
        while (rset.next()) {
            posiciones.add(new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha")));
        }
        finalizarConexion();
        return posiciones;
    }

    public Posicion obtenerUltimaPosicion(String matricula) throws SQLException {
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

    public List<Posicion> obtenerUltimaPosicionTodos() throws SQLException {
        List<Posicion> posiciones = new ArrayList();
        ResultSet rset;
        String sql = "SELECT * FROM posiciones WHERE (matricula, fecha) IN (SELECT matricula, MAX(fecha) FROM posiciones "
                + "GROUP BY matricula)";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            posiciones.add(new Posicion(rset.getString("matricula"), rset.getInt("posX"), rset.getInt("posY"), rset.getString("fecha")));
        }
        finalizarConexion();
        return posiciones;

    }

    public boolean actualizarPosicion(Posicion pos) throws SQLException {
        boolean result;
        String sql = "UPDATE posiciones SET posX = ?, posY = ? WHERE matricula = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, pos.getPosX()); //stmt.setString(1, cli.getNombre);
        stmt.setDouble(2, pos.getPosY());
        stmt.setString(3, pos.getMatricula());

        int res = stmt.executeUpdate();
        if (res == 0) {
            result = insertarPosicion(pos);
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
