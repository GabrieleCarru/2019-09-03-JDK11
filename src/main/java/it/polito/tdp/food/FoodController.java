/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Correlato;
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
    	
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Errore: selezionare un tipo di porzione. ");
    		return;
    	}
    	
    	Integer passi;
    	try {
    		passi = Integer.parseInt(txtPassi.getText());
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Inserire un numero intero positivo per il numero di passi. \n");
    		return;
    	}
    	txtResult.appendText("Cerco cammino di " + passi + " passi con peso massimo... \n");
    	
    	this.model.trovaCammino(porzione, passi);
    	
    	if(this.model.getCamminoMax() == null) {
    		txtResult.appendText("Non ho trovato un cammino. \n");
    		return;
    	} else {
    		txtResult.appendText("Cammino trovato con peso massimo = " + this.model.getPesoMax() + ". \n");
    		for(String s : this.model.getCamminoMax()) {
    			txtResult.appendText(s + "\n");
    		}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	
    	txtResult.appendText("\n");
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Errore: selezionare un tipo di porzione. ");
    		return;
    	}
    	txtResult.appendText("I tipi di porzione correlati a " + porzione + " sono: \n");
    	
    	List<Correlato> vicini = this.model.getVicini(porzione);
    	for(Correlato c : vicini) {
    		txtResult.appendText(String.format("%s [%f] \n", c.getCorrelato(), c.getPeso()));
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Double calories;
    	try {
    		calories = Double.parseDouble(txtCalorie.getText());

    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Inserire un valore numerico valido.");
    		return;
    	}
    	
    	if(calories < 0) {
			txtResult.setText("Inserire un valore positivo per le calorie.");
			return;
		}
		
		if(calories.isNaN()) {
			txtResult.setText("Inserire un valore numerico positivo per le calorie.");
			return;
		}
		
		txtResult.appendText("Creo il grafo...\n");
		this.model.creaGrafo(calories);
		txtResult.appendText(String.format("Grafo creato! #Vertici %d #Archi %d \n", 
										this.model.getNumberVertex(), 
										this.model.getNumberEdge()));
		
		List<String> listaPorzioni = this.model.getTipiPorzione(calories);
		Collections.sort(listaPorzioni);
		boxPorzioni.getItems().addAll(listaPorzioni);
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
