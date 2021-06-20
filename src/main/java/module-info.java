//module com.base.Appiculture {
    module Appiculture {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    //requires javafx.swt;
    requires sqlite.jdbc;
    requires java.sql;
    requires jasperreports;

    opens com.base.controllers.views;
    opens com.base.models.structure;

    exports com.base.controllers.views;
    exports com.base.models;
    exports com.base;





}