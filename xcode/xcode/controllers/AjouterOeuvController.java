/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xcode.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xcode.DBaseC.ConnectionDB;
import xcode.IService.Listener;
import xcode.entities.Commande;
import xcode.entities.ElementPanier;
import xcode.entities.Oeuvrage;
import xcode.entities.PanierHolder;
import xcode.service.cmdservices;

/**
 * FXML Controller class
 *
 * @author Mega-PC
 */

public class AjouterOeuvController implements Initializable {
    
    Connection connection=ConnectionDB.getInstance().getCnx();
    
    @FXML
    private TextField txtrechercher;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label nomO;
    @FXML
    private Label prixo;
    @FXML
    private ImageView imgO;
    @FXML
    private Label descO;
    @FXML
    private ScrollPane scrollO;
    @FXML
    private GridPane grid;
    @FXML
    private Button Voirpanier;
    @FXML 
    private Button Rechercher;
    
    private Oeuvrage oi;
    
    private Image image;
    
    private Listener Listener;
    private List<Oeuvrage> listcmd =new ArrayList<>();
    cmdservices os = new cmdservices();
    @FXML
    private Button btnAdd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listcmd.addAll(os.afficherLO());
           if (listcmd.size() > 0) {
                setChosenO(listcmd.get(0));
                Listener = new Listener() {
                    
                    public void onClickListener(Oeuvrage oeuvrage) {

                        setChosenO(oeuvrage);
                    }
                };
            }
           int column = 0;
            int row = 1;
           try {
                for (int i = 0; i < listcmd.size(); i++) {

                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("/xcode/views/Oeuvre.fxml"));
                   AnchorPane anchorPane = fxmlLoader.load();

                    OeuvreDispController itemController = fxmlLoader.getController();
                itemController.setData(listcmd.get(i),Listener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
                }
                
                
                
                } catch (IOException ex) {
                   Logger.getLogger(AjouterOeuvController.class.getName()).log(Level.SEVERE, null, ex);
               }
    }    
    
    private void setChosenO(Oeuvrage o) {
        nomO.setText(o.getNom());
        prixo.setText((o.getPrix())+"DT");
        descO.setText(o.getDescription());
                File newFile2 = new File("C:\\Users\\Mega-PC\\Desktop\\XCode\\src\\xcode\\img\\"+o.getImage());

        //image = new Image(getClass().getResourceAsStream(o.getImg()));
        imgO.setImage(new Image(newFile2.toURI().toString()));
        oi=o;
        System.out.println(oi.getOeuvrage_id());
    }
    

 
    
    
    
    
    @FXML
    private void VoirPanier(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/xcode/views/Panier.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void addPanier(ActionEvent event) {
        
    
        
        List<ElementPanier> listEOp=PanierHolder.getInstance().getEP();
                ElementPanier ep=new ElementPanier();

     
        boolean existsElem=false;
        int i;
        for(i=0;i<listEOp.size();i++)
        {
            if(listEOp.get(i).getOeuv().equals(oi))
            {
                existsElem=true;
                ep=listEOp.get(i);
                
                 break;
            }

        }
        
         
        if(!existsElem)
        {   
            ep.setOeuv(oi);
            ep.setQuantite(1);
            listEOp.add(ep);
            os.createPanierTemp(1,ep.getOeuv().getOeuvrage_id(),ep.getQuantite());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Element ajouté ! ");
            alert.setContentText("Element ajouté avec sucèes ! ");
            alert.showAndWait(); 
        }
        else
        {   
            if(oi.getQuantite()>ep.getQuantite())
            {
            os.updatePanierTemp(1,ep.getOeuv().getOeuvrage_id(),1);
            System.out.println(oi.getOeuvrage_id()+"    1");
            ep.setQuantite(ep.getQuantite()+1);
            
            listEOp.set(i, ep);
            
            
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setHeaderText("Element existe ! ");
            alert.setContentText("Element existe deja, quantité incrementé ! ");
            alert.showAndWait(); 
            }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Stock insuffisant ! ");
            alert.setContentText("Stock insuffisant! ");
            alert.showAndWait(); 
            }
        }
             
            PanierHolder.getInstance().setEP(listEOp);

                
        }
    
    
    
}
    

