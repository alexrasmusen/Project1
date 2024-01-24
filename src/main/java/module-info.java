module edu.okcu.teamalpha.project1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens edu.okcu.teamalpha.project1 to javafx.fxml;
    exports edu.okcu.teamalpha.project1;
}