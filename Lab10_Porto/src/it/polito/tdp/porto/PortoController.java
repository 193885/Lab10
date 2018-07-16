package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.java.swing.plaf.motif.resources.motif;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model m ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	txtResult.clear();    	
    	
    	try {
    	
	    	for (Author a : m.findCoautori(boxPrimo.getValue())) 
	    		
	    		txtResult.appendText(a.toString()+"\n");
	    	

	    	List<Author> noncoautori  = new ArrayList<>(m.getAllAutori()) ;
	    	
	    	noncoautori.removeAll(m.findCoautori(boxPrimo.getValue())) ;
        	noncoautori.remove(boxPrimo.getValue()) ;
        	
        	boxSecondo.getItems().clear();
        	
	    	boxSecondo.getItems().addAll(noncoautori);	    	
    	
    	}catch( RuntimeException e) {
    		
    		e.printStackTrace();
    	}

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    		txtResult.clear();
    	    		
  	List <Paper> sequenzaAuthor = m.findSequenza(boxPrimo.getValue(),boxSecondo.getValue());
    		
    		System.out.println("---------------------");
    		
    		System.out.println(sequenzaAuthor);
    		
    		
    		
   		for (Paper p: sequenzaAuthor)
    			
    			txtResult.appendText(p.toString()+"\n");

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {
	
		m= model;

		boxPrimo.getItems().addAll( m.getAllAutori());
		
	}
}
