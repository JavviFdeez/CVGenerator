package org.JavviFdeez.model.entity;

public class Session {
    // Instancia única de la sesión
    private static volatile Session instance;
    private int contactId;

    // Constructor privado para evitar instanciación externa
    private Session() {}

    // Método para obtener la instancia única de la sesión, con doble verificación para la seguridad de subprocesos
    public static Session getInstance() {
        if (instance == null) {
            synchronized (Session.class) {
                if (instance == null) {
                    instance = new Session();
                }
            }
        }
        return instance;
    }

    // Métodos para obtener y establecer el ID de contacto
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    // Evitar que el objeto Session sea clonado
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning not allowed for Singleton class");
    }

    // Implementar readResolve para evitar que el Singleton se rompa al ser serializado
    protected Object readResolve() {
        return getInstance();
    }
}