package org.JavviFdeez.model.dao;

import java.io.Closeable;
import java.io.IOException;

public interface iMainDAO extends Closeable {

    // ======================================
    // Método para cerrar la base de datos.
    // ======================================
    void close() throws IOException;
}
