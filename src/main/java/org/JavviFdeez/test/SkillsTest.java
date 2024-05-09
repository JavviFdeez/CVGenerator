package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.SkillsDAO;
import org.JavviFdeez.model.entity.Skills;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SkillsTest {
    public static void main(String[] args) {
        // testSaveSkills();
        // testUpdateSkills();
        // testDeleteSkills();
        // testFindSkillsById();
        // testFindAllSkills();
    }

    private static void testSaveSkills() {
        // Crear una nueva instancia de contacto con los datos necesarios
        Skills skills = new Skills("Ejemplo");

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

    private static void testUpdateSkills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de SkillsDAO con la aplicación establecida
            SkillsDAO sDAO = new SkillsDAO(connection);

            // Obtener una skill existente para actualizar
            int skillIdToUpdate = 3;
            Skills skillToUpdate = sDAO.findById(skillIdToUpdate);

            // Verificar si se encontró la skill
            if (skillToUpdate != null) {
                // Actualizar los datos de la skill
                skillToUpdate.setName("Nuevo nombre");

                // Llamar al método update y pasarle el ID y la skill actualizada
                sDAO.update(skillIdToUpdate, skillToUpdate);

                // Llamar al método findById para obtener la skill actualizada
                Skills updatedSkill = sDAO.findById(skillIdToUpdate);

                // Verificar si la skill se actualizo correctamente
                if (updatedSkill != null) {
                    System.out.println("✅ Skill actualizada exitosamente: " + updatedSkill);
                } else {
                    System.out.println("❌ Error al actualizar la skill.");
                }
            } else {
                System.out.println("❌ La skill no existe.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteSkills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de SkillsDAO con la aplicación establecida
            SkillsDAO sDAO = new SkillsDAO(connection);

            // Obtener una skill existente para eliminar
            int skillIdToDelete = 3;

            // Llamar al método delete y pasarle el ID de la skill a eliminar
            Skills skillToDelete = sDAO.findById(skillIdToDelete);

            // Verificar si se encontró la skill
            if (skillToDelete != null) {
                try {
                    // Llamar al método delete y pasarle el ID de la skill a eliminar
                    sDAO.delete(skillIdToDelete);
                    System.out.println("✅ Skill eliminada exitosamente con ID: " + skillIdToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar la skill: " + e.getMessage());
                }
            } else {
                System.out.println("❌ La skill no existe.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindSkillsById() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de SkillsDAO con la aplicación establecida
            SkillsDAO sDAO = new SkillsDAO(connection);

            // ID de la skill que deseas buscar
            int skillId = 1;

            // Llamar al método findById y pasarle el ID de la skill
            Skills skill = sDAO.findById(skillId);

            // Verificar si se encontró la skill
            if (skill != null) {
                System.out.println("✅ Skill encontrada: " + skill);
            } else {
                System.out.println("❌ La skill no existe.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAllSkills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de SkillsDAO con la aplicación establecida
            SkillsDAO sDAO = new SkillsDAO(connection);

            // Llamar al método findAll
            try {
                List<Skills> skills = sDAO.findAll();

                // Verificar si se encontró al menos una skill
                if (!skills.isEmpty()) {
                    System.out.println("✅ Skills encontradas:");
                    for (Skills skill : skills) {
                        System.out.println(skill);
                    }
                } else {
                    System.out.println("❌ No se encontró ninguna skill.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error al obtener las skills: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}