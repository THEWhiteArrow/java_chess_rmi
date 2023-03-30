package view_client;


import javafx.scene.layout.Region;
import viewmodel_client.ViewModel;

public abstract class ViewController {

	protected Region root;

	protected ViewHandler viewHandler;

//	protected ViewModel viewModel;


	public abstract  void init(ViewHandler viewHandler, ViewModel viewModel, Region root) ;

//	public void init(ViewHandler viewHandler, ViewModel viewModel, Region root){
//		this.viewHandler=viewHandler;
//		this.viewModel=viewModel;
//		this.root=root;
//	}

	public Region getRoot(){
		return root;
	}

	public abstract void reset() ;

}
