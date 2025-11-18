import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private GestionClients gestion = new GestionClients();
    private TableView<Client> table = new TableView<>();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        TableColumn<Client, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        TableColumn<Client, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        TableColumn<Client, String> colTel = new TableColumn<>("Téléphone");
        colTel.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));

        table.getColumns().addAll(colNom, colPrenom, colEmail, colTel);
        table.setItems(gestion.getClients());

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField telField = new TextField();
        telField.setPromptText("Téléphone");

        Button ajouterBtn = new Button("Ajouter");
        ajouterBtn.setOnAction(e -> {
            gestion.ajouterClient(nomField.getText(), prenomField.getText(), emailField.getText(), telField.getText());
            nomField.clear(); prenomField.clear(); emailField.clear(); telField.clear();
        });

        Button modifierBtn = new Button("Modifier");
        modifierBtn.setOnAction(e -> {
            Client c = table.getSelectionModel().getSelectedItem();
            if (c != null) gestion.modifierClient(c, nomField.getText(), prenomField.getText(), emailField.getText(), telField.getText());
        });

        Button supprimerBtn = new Button("Supprimer");
        supprimerBtn.setOnAction(e -> {
            Client c = table.getSelectionModel().getSelectedItem();
            if (c != null) gestion.supprimerClient(c);
        });

        HBox form = new HBox(5, nomField, prenomField, emailField, telField, ajouterBtn, modifierBtn, supprimerBtn);
        VBox root = new VBox(10, table, form);
        stage.setScene(new Scene(root, 800, 400));
        stage.setTitle("Gestion des clients");
        stage.show();
    }
}
