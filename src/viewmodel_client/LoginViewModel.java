package viewmodel_client;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import model_client.ModelClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class LoginViewModel extends ViewModel implements PropertyChangeListener {
	private final String[] names = {
			"Liam",
			"Olivia",
			"Noah",
			"Emma",
			"Oliver",
			"Ava",
			"William",
			"Sophia",
			"Elijah",
			"Isabella",
			"James",
			"Charlotte",
			"Benjamin",
			"Amelia",
			"Lucas",
			"Mia",
			"Mason",
			"Harper",
			"Ethan",
			"Evelyn"
	};
	private StringProperty hostProperty;
	private IntegerProperty portProperty;

	private StringProperty nameProperty;
	private StringProperty errorProperty;

	private ViewState viewState;

	private ModelClient model;



	public LoginViewModel(ModelClient model, ViewState viewState) {
		this.model=model;
		this.viewState=viewState;
		hostProperty = new SimpleStringProperty();
		portProperty = new SimpleIntegerProperty();
		errorProperty = new SimpleStringProperty();
		nameProperty= new SimpleStringProperty();
	}

	public boolean connect()  {
		String host = hostProperty.get();
		int port = portProperty.get();
		String name = nameProperty.get();


		if( model.connectToServer(host,port) ){
			this.viewState.setName(name);
			return true;
		}else
			return false;

	}

	public void clear() {
		hostProperty.set("localhost");
		portProperty.set(6789);
		errorProperty.set("");
		nameProperty.set( names[(int)Math.floor(Math.random()*names.length)]);
	}


	public IntegerProperty getPortProperty() {
		return portProperty;
	}



	public StringProperty getHostProperty() {
		return hostProperty;
	}

	public StringProperty getNameProperty(){return nameProperty;}

	public StringProperty getErrorProperty() {
		return errorProperty;
	}

	/**
	 * @see java::beans::PropertyChangeListener#propertyChange(PropertyChangeEvent)
	 *  
	 */
	public void propertyChange(PropertyChangeEvent evt) {

	}

}
