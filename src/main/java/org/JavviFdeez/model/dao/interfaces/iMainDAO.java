package org.JavviFdeez.model.dao.interfaces;

import java.io.Closeable;
import java.io.IOException;

public interface iMainDAO extends Closeable {

    // ======================================
    // Método para cerrar la base de datos.
    // ======================================
    void close() throws IOException;
}
