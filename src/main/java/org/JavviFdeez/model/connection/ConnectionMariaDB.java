package org.JavviFdeez.model.connection;

import org.JavviFdeez.utils.XMLManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE="connection.xml";
    private static ConnectionMariaDB _instance;
    private Connection conn;

    private ConnectionMariaDB(){
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);

        // =================================================
        // Establecer la configuración de la base de datos
        // =================================================
        try {
            conn = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            // ======================================================================
            // Manejo de excepciones: registra o lanza una excepción personalizada
            // ======================================================================
            e.printStackTrace();
            conn = null;
        }
    }

    // ====================================
    // Devuelve la instancia de la clase
    // ====================================
    public static synchronized Connection getConnection(){
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        return _instance.conn;
    }

    // =======================================================
    // Cierra la conexión automáticamente después de su uso
    // =======================================================
    public static synchronized void closeConnection(Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
