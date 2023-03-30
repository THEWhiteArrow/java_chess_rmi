package viewmodel_client;


import model_client.ModelClient;
import view_client.ChessViewController;

public class ViewModelFactory {

	private MenuViewModel menuViewModel;
	private LoginViewModel loginViewModel;
	private ChessViewModel chessViewModel;
	private ViewState viewState;



	public ViewModelFactory(ModelClient model) {
		this.viewState = new ViewState();
		this.chessViewModel = new ChessViewModel(model, viewState);
		this.loginViewModel = new LoginViewModel(model, viewState);
		this.menuViewModel = new MenuViewModel(model,viewState);
	}

	public LoginViewModel getLoginViewModel() {
		return loginViewModel;
	}

	public MenuViewModel getMenuViewModel() {
		return menuViewModel;
	}

	public ChessViewModel getChessViewModel() {
		return chessViewModel;
	}

}
