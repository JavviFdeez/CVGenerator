package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.SkillsDAO;
import org.JavviFdeez.model.entity.Skills;
import java.sql.Connection;
import java.sql.SQLException;

public class SkillsTest {
    public static void main(String[] args) {
        // Crear una nueva instancia de contacto con los datos necesarios
        Skills skills = new Skills("Java");

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de SkillsDAO con la conexión establecida
            SkillsDAO sDAO = new SkillsDAO(connection);

            // Guardar una skill en la base de datos
            try {
                // Llama al método save del SkillsDAO para guardar el contacto
                Skills savedSkills = sDAO.save(skills);
                System.out.println("✅ Skill insertado exitosamente: " + savedSkills);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar la skill: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }
}
