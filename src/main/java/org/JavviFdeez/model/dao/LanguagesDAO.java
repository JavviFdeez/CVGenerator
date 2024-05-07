package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.entity.Languages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguagesDAO implements iLanguagesDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_languages (lang_id, contact_id, spanish, english, french) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_languages SET contact_id=?, spanish=?, english=?, french=? WHERE lang_id=?";
    private static final String DELETE = "DELETE FROM cvv_languages WHERE lang_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_languages WHERE lang_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_languages";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public LanguagesDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param lang
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR un lenguaje en la base de datos.
     */
    @Override
    public Languages save(Languages lang) throws SQLException {
        // =========================================
        // Insertar el lenguaje en la base de datos
        // =========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, lang.getContact_id());
            pst.setInt(2, lang.getContact_id());
            pst.setInt(3, lang.getSpanish());
            pst.setInt(4, lang.getEnglish());
            pst.setInt(5, lang.getFrench());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ningun lenguaje, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningun lenguaje.");
            }

            // ==============================================================
            // Obtener el ID generado por la base de datos
            // ==============================================================
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // ================================================
                    // Obtener el ID generado por la base de datos
                    // ================================================
                    lang.setLang_id(generatedKeys.getInt(1));
                } else {
                    // ================================================
                    // Si no se insertó ningun lenguaje, mostrar mensaje de error
                    // ================================================
                    throw new SQLException("❌ Error al insertar, no se guardó ningun lenguaje.");
                }
            }

            return lang;
        }
    }

    /**
     * @param lang
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un lenguaje en la base de datos.
     */
    @Override
    public Languages update(Languages lang) throws SQLException {
        // ==========================================
        // Actualizar el lenguaje en la base de datos
        // ==========================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, lang.getContact_id());
            pst.setInt(2, lang.getSpanish());
            pst.setInt(3, lang.getEnglish());
            pst.setInt(4, lang.getFrench());
            pst.setInt(5, lang.getLang_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se actualizo ningun lenguaje, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningun lenguaje.");
            }
        }
        return lang;

    }

    /**
     * @param lang
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR un lenguaje de la base de datos.
     */
    @Override
    public Languages delete(Languages lang) throws SQLException {
        // ==========================================
        // Eliminar el lenguaje de la base de datos
        // ==========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, lang.getLang_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se elimino ningun lenguaje, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningun lenguaje.");
            }
        }

        return lang;
    }

    @Override
    public Languages findById(int id) throws SQLException {
        // =================================================
        // Lenguaje encontrado, o null si no lo encuentra
        // =================================================
        Languages foundlang = null;

        // =================================================
        // Consultar para obtener un lenguaje por su ID
        // =================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // ==============================================
            // Ejecutar la consulta y obtener el resultado
            // ==============================================
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // =================================================
                    // Crear un nuevo lenguaje con los datos obtenidos
                    // =================================================
                    foundlang = new Languages(
                            rs.getInt("lang_id"),
                            rs.getInt("contact_id"),
                            rs.getInt("spanish"),
                            rs.getInt("english"),
                            rs.getInt("french")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar el lenguaje");
        }
        return foundlang;
    }

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todos los lenguajes en la base de datos.
     */
    @Override
    public List<Languages> findAll() throws SQLException {
        // ================================================
        // Lenguajes encontradas, o null si no se encuentran
        // =================================================
        List<Languages> languagesList = new ArrayList<>();

        // ==============================================
        // Ejecutar la consulta y obtener el resultado
        // ==============================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // =================================================
                    // Crear un nuevo lenguaje con los datos obtenidos
                    // =================================================
                    Languages lang = new Languages(
                            rs.getInt("lang_id"),
                            rs.getInt("contact_id"),
                            rs.getInt("spanish"),
                            rs.getInt("english"),
                            rs.getInt("french")
                    );
                    // =================================================
                    // Agregar el lenguaje a la lista de lenguajes
                    // =================================================
                    languagesList.add(lang);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar los lenguajes");
        }

    return languagesList;
    }
}