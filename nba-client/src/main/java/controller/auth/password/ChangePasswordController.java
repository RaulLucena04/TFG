package controller.auth.password;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.User;
import service.UsuarioService;
import session.Session;

public class ChangePasswordController {

    @FXML
    private PasswordField txtOldPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label lblError;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
private void handleSave() {

    User user = Session.getCurrentUser();

    String oldPass = txtOldPassword.getText();
    String newPass = txtNewPassword.getText();
    String confirm = txtConfirmPassword.getText();

    if (oldPass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
        showError("Rellena todos los campos");
        return;
    }

    // ✔️ Validación local sin llamar al backend
    if (!user.getPassword().equals(oldPass)) {
        showError("La contraseña actual no es correcta");
        return;
    }

    if (!newPass.equals(confirm)) {
        showError("Las contraseñas no coinciden");
        return;
    }

    if (newPass.length() < 6) {
        showError("La nueva contraseña debe tener al menos 6 caracteres");
        return;
    }

    // ✔️ Llamada al backend para actualizar
    usuarioService.updatePassword(user.getId(), newPass);

    // ✔️ Actualizar la contraseña en sesión
    user.setPassword(newPass);

    closeWindow();
}


    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showError(String msg) {
        lblError.setText(msg);
        lblError.setVisible(true);
    }

    private void closeWindow() {
        Stage stage = (Stage) txtOldPassword.getScene().getWindow();
        stage.close();
    }
}
