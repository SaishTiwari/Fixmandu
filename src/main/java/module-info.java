module build.fixmandu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens build.fixmandu to javafx.fxml;
    exports build.fixmandu;
}