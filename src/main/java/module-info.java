module cat.copernic.presentacio {
    /**
     * Especifiquem els paquets que el nostre projecte ha d'utilitzar
     */
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.csv;
    
    /**
     * Obrim el paquet de dades a javafx base per a que sigui accesible
     */
    opens cat.copernic.dades to javafx.base;
    opens cat.copernic.presentacio to javafx.fxml;
    exports cat.copernic.presentacio;
     // Reemplaza "tu.modulo" por el nombre de tu módulo.
}
