package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.LanguagesDAO;
import org.JavviFdeez.model.entity.Languages;

import java.sql.Connection;
import java.sql.SQLException;

public class LanguagesTest {
    public static void main(String[] args) {
        // testSaveLanguages();
        // testUpdateLanguages();
         testDeleteLanguages();
        // testFindLanguagesById();
        // testFindAllLanguages();
    }

    private static void testSaveLanguages() {
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

    private static void testUpdateLanguages() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de LanguagesDAO con la aplicación establecida
            LanguagesDAO langDAO = new LanguagesDAO(connection);

            // Obtener un lenguaje existente para actualizar
            int langIdToUpdate = 1;
            Languages langToUpdate = langDAO.findById(langIdToUpdate);

            // Verificar si se encontró el lenguaje
            if (langToUpdate != null) {
                // Actualizar los datos del lenguaje
                langToUpdate.setSpanish(5);
                langToUpdate.setEnglish(1);
                langToUpdate.setFrench(1);

                // Llamar al método update y pasarle el ID y el lenguaje actualizado
                Languages updatedLang = langDAO.update(langIdToUpdate, langToUpdate);

                // Verificar si el lenguaje se actualizó correctamente
                if (updatedLang != null) {
                    System.out.println("✅ Lenguaje actualizada exitosamente: " + updatedLang);
                } else {
                    System.out.println("❌ Error al actualizar el lenguaje.");
                }
            } else {
                System.out.println("❌ No se encontró el lenguaje con el ID: " + langIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteLanguages() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de LanguagesDAO con la aplicación establecida
            LanguagesDAO langDAO = new LanguagesDAO(connection);

            // Obtener un lenguaje existente para eliminar
            int langIdToDelete = 1;

            // Lenguaje que deseas eliminar
            Languages langToDelete = langDAO.findById(langIdToDelete);

            // Verificar si se encontró el lenguaje
            if (langToDelete != null) {
                try {
                    // Llamar al método delete y pasarle el ID del lenguaje a eliminar
                    langDAO.delete(langIdToDelete);
                    System.out.println("✅ Lenguaje eliminado exitosamente." + langIdToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar el lenguaje: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró el lenguaje con el ID: " + langIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}
