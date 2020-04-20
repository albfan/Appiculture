module Appiculture {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.swt;
    requires sqlite.jdbc;
    requires java.sql;

    exports com.base.controllers.views;
    exports com.base.models;
    exports com.base;
    //exports com.base.views;

    opens com.base.controllers.views;
    opens com.base.models.structure;
//    opens com.stephane to javafx.fxml;
//    opens com.stephane.views to javafx.fxml;
// opens javafx.scene to org.controlsfx.controls; //añadido esto para testFX


}