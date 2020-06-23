/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.InfoArco;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	
    	txtResult.appendText("\n");
    	
    	String partenza = boxPorzioni.getValue();
    	
    	if(partenza == null) {
    		txtResult.appendText("Selezionare un tipo di porzione per poter cercare i correlati. \n");
    		return;
    	}
    	
    	Integer N;
    	try {
    		N = Integer.parseInt(txtPassi.getText());
    		
    		if(N < 0) {
    			txtResult.appendText("Errore: Bisogna selezionare un valore intero positivo. \n");
        		return;
    		}
    		
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Errore: Bisogna selezionare un valore intero positivo. \n");
    		return;
    	}
    	
    	this.model.trovaCammino(partenza, N);
    	
    	if(model.getCamminoMax() == null) {
    		txtResult.appendText("Nessun cammino massimo trovato con " + N + " passi. \n");
    	}
    	
    	txtResult.appendText(String.format("Trovato cammino massimo con peso = %d: \n", 
    								this.model.getPesoMax()));
    	
    	for(String s : this.model.getCamminoMax()) {
    		txtResult.appendText(String.format(" - %s \n", s));
    	}
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	
    	txtResult.appendText("\n");
    	
    	String partenza = boxPorzioni.getValue();
    	
    	if(partenza == null) {
    		txtResult.appendText("Selezionare un tipo di porzione per poter cercare i correlati. \n");
    		return;
    	}
    	
    	List<InfoArco> vicini = this.model.getVicini(partenza);
    	
    	txtResult.appendText(String.format("Trovati %d tipi di porzione correlati a %s: \n", 
    								vicini.size(), partenza));
    	
    	int i = 1;
    	for(InfoArco ia : vicini) {
    		txtResult.appendText(String.format(" %d) %s  | %d \n", 
    						i, ia.getP2(), ia.getPeso()));
    		i++;
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	Double calories = null;
    	try {
    		calories = Double.parseDouble(txtCalorie.getText());
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Errore: inserire un valore numerico per le calorie. \n");
    		return;
    	}
    	
    	txtResult.appendText("Creazione grafo...\n");
    	
    	this.model.creaGrafo(calories);
    	
    	txtResult.appendText(String.format("Grafo creato! [#Vertici %d, #Archi %d] \n", 
    								this.model.getNumberVertex(), this.model.getNumberEdges()));
    	
    	boxPorzioni.getItems().addAll(this.model.getAllTipiPorzione(calories));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
