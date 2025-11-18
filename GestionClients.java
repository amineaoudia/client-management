import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

public class GestionClients {
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private int prochainId = 1;
    private final String fichier = "clients.txt";

    public GestionClients() { chargerClients(); }

    public ObservableList<Client> getClients() { return clients; }

    public void ajouterClient(String nom, String prenom, String email, String telephone) {
        Client c = new Client(prochainId++, nom, prenom, email, telephone);
        clients.add(c);
        sauvegarderClients();
    }

    public void modifierClient(Client c, String nom, String prenom, String email, String telephone) {
        c.setNom(nom);
        c.setPrenom(prenom);
        c.setEmail(email);
        c.setTelephone(telephone);
        sauvegarderClients();
    }

    public void supprimerClient(Client c) {
        clients.remove(c);
        sauvegarderClients();
    }

    private void sauvegarderClients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichier))) {
            for (Client c : clients) bw.write(c.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    private void chargerClients() {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = br.readLine()) != null) {
                Client c = Client.fromString(line);
                clients.add(c);
                if (c.getId() >= prochainId) prochainId = c.getId() + 1;
            }
        } catch (FileNotFoundException e) { }
        catch (IOException e) { System.out.println("Erreur chargement : " + e.getMessage()); }
    }
}
