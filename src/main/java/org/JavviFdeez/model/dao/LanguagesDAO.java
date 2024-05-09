package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.dao.interfaces.iLanguagesDAO;
import org.JavviFdeez.model.entity.Languages;
import org.JavviFdeez.utils.LanguageValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguagesDAO implements iLanguagesDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_languages (contact_id, spanish, english, french) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_languages SET spanish=?, english=?, french=? WHERE lang_id=?";
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
        // ===========================================================
        // Validar que los valores estén dentro del rango permitido
        // ===========================================================
        if (!LanguageValidator.isValidLanguageRange(lang.getSpanish()) ||
                !LanguageValidator.isValidLanguageRange(lang.getEnglish()) ||
                !LanguageValidator.isValidLanguageRange(lang.getFrench())) {
            throw new IllegalArgumentException("❌ Error al insertar, los valores de los idiomas deben estar en el rango de 0 a 5.");
        }

        // =============================================================
        // Preparar la sentencia SQL para insertar un nuevo lenguaje
        // =============================================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, lang.getContact_id());
            pst.setInt(2, lang.getSpanish());
            pst.setInt(3, lang.getEnglish());
            pst.setInt(4, lang.getFrench());

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
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un lenguaje en la base de datos.
     */
    @Override
    public Languages update(int id, Languages updateLang) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        // ============================================================
        // Validar que los valores estén dentro del rango permitido
        // ============================================================
        if (!LanguageValidator.isValidLanguageRange(updateLang.getSpanish()) ||
                !LanguageValidator.isValidLanguageRange(updateLang.getEnglish()) ||
                !LanguageValidator.isValidLanguageRange(updateLang.getFrench())) {
            throw new IllegalArgumentException("❌ Error al actualizar, los valores de los idiomas deben estar en el rango de 0 a 5.");
        }
        // ==========================================
        // Actualizar el lenguaje en la base de datos
        // ==========================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, updateLang.getSpanish());
            pst.setInt(2, updateLang.getEnglish());
            pst.setInt(3, updateLang.getFrench());
            pst.setInt(4, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se actualizo ningun lenguaje, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el lenguaje con ID: " + id);
            }

            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al actualizar el lenguaje: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updateLang;
    }


    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR un lenguaje de la base de datos.
     */
    @Override
    public void delete(int id) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        // ==========================================
        // Eliminar el lenguaje de la base de datos
        // ==========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se elimino ningun lenguaje, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("No se eliminó ningun lenguaje con el ID: " + id);
            }

            // Realizar commit
                conn.commit();
            } catch (SQLException e) {
                // En caso de error, hacer rollback
                conn.rollback();
                throw new SQLException("Error al eliminar el lenguaje: " + e.getMessage(), e);
            } finally {
                try {
                    // Restaurar la autoconfirmación
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    // Manejar cualquier excepción al restaurar la autoconfirmación
                    throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
                }
            }
        }

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR un lenguaje en la base de datos.
     */
    @Override
    public Languages findById(int id) throws SQLException {
        Languages foundLang = null;
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    foundLang = new Languages(
                            rs.getInt("contact_id"),
                            rs.getInt("spanish"),
                            rs.getInt("english"),
                            rs.getInt("french")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar el lenguaje", e);
        }
        return foundLang;
    }


    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todos los lenguajes en la base de datos.
     */
    @Override
    public List<Languages> findAll() throws SQLException {
        List<Languages> languagesList = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Languages lang = new Languages(
                            rs.getInt("contact_id"),
                            rs.getInt("spanish"),
                            rs.getInt("english"),
                            rs.getInt("french")
                    );
                    languagesList.add(lang);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar los lenguajes", e);
        }
        return languagesList;
    }
}