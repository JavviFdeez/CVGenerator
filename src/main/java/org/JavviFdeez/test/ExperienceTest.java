package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ExperiencesDAO;
import org.JavviFdeez.model.entity.Experiences;


import java.sql.Connection;
import java.sql.SQLException;

public class ExperienceTest {
    public static void main(String[] args) {
        // Crear una nueva instancia de experiencia con los datos necesarios
        Experiences exp = new Experiences(4, "Developer", "12", "Google", "Malaga", 2022, 1);

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ExperienceDAO con la conexión establecida
            ExperiencesDAO expDAO = new ExperiencesDAO(connection);

            // Guardar una exp en la base de datos
            try {
                // Llama al método save del SkillsDAO para guardar el contacto
                Experiences savedExp = expDAO.save(exp);
                System.out.println("✅ Experiencia insertada exitosamente: " + savedExp);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar la experiencia: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }
}
