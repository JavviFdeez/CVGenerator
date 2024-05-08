package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.LanguagesDAO;
import org.JavviFdeez.model.entity.Languages;

import java.sql.Connection;
import java.sql.SQLException;

public class LanguagesTest {
    public static void main(String[] args) {
        // Crear una nueva instancia de lenguaje con los datos necesarios
        Languages lang = new Languages(4, 5, 3, 2);

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de LanguagesDAO con la conexión establecida
            LanguagesDAO langDAO = new LanguagesDAO(connection);

            // Guardar una exp en la base de datos
            try {
                // Llama al método save del SkillsDAO para guardar el contacto
                Languages savedExp = langDAO.save(lang);
                System.out.println("✅ Lenguaje insertado exitosamente: " + savedExp);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar el lenguaje: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }
}
