module ro.mpp.teledon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens ro.mpp.teledon to javafx.fxml;
    exports ro.mpp.teledon;
}