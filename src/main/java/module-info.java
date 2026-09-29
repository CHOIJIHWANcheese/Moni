module com.moni.app {
    requires javafx.controls;
    requires javafx.graphics;
    requires com.sun.jna;
    requires com.sun.jna.platform;

    exports com.moni.app to javafx.graphics;
}
