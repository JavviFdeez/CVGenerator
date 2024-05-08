package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.interfaces.iMainDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainDAO implements iMainDAO {

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public MainDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * @author: JavviFdeez
     * Método para cerrar la base de datos.
     *
     * @throws IOException si ocurre un error al cerrar la base de datos
     */

    @Override
    public void close() throws IOException {
        try {
            // ===================
            // Mensaje de exito
            // ===================
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            // ===================
            // Mensaje de error
            // ===================
            throw new IOException("❌ Error al cerrar la conexión." + e.getMessage());
        }
    }
}
