package view_client;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.beans.binding.Bindings;
import util.IntStringConverter;
import viewmodel_client.LoginViewModel;
import viewmodel_client.ViewModel;


public class LoginViewController extends ViewController {

	@FXML private TextField hostField;

	@FXML private TextField portField;

	@FXML private TextField nameField;
	@FXML private Label errorLabel;

	private LoginViewModel viewModel;


	@Override
	public void init(ViewHandler viewHandler, ViewModel viewModel, Region root) {
		this.viewHandler=viewHandler;
		this.viewModel = (LoginViewModel) viewModel;
		this.root = root;

		hostField.textProperty().bindBidirectional( this.viewModel.getHostProperty());
		Bindings.bindBidirectional(
				portField.textProperty(),
				this.viewModel.getPortProperty(),
				new IntStringConverter()
		);
		errorLabel.textProperty().bind(this.viewModel.getErrorProperty());
		nameField.textProperty().bindBidirectional(this.viewModel.getNameProperty());
	}
	@FXML private void connect() {

		if(viewModel.connect())
			viewHandler.openView("menu");
	}

	/**
	 * @see view-client.ViewController#reset()
	 *  
	 */
	public void reset() {
		viewModel.clear();
	}



}
