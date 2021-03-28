/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import entities.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.UserService;
import utils.Connexion;

/**
 *
 * @author asus
 */
public class Projet extends Application {
    private Stage primaryStage;
    private Parent parentPage;
    public static User connectedUser;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pa login ");
        
       //parentPage = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
     //   parentPage = FXMLLoader.load(getClass().getResource("/view/afficher_mesévenements.fxml"));
      parentPage = FXMLLoader.load(getClass().getResource("/view/AccueilUtilisateur.fxml"));
         //parentPage = FXMLLoader.load(getClass().getResource("/view/GestionLieuAdmin.fxml"));
   //   parentPage = FXMLLoader.load(getClass().getResource("/view/ValidationEvenementAdmin.fxml"));
        Scene scene = new Scene(parentPage);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
        
        
    }
    
}


















