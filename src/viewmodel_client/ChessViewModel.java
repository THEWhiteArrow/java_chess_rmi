package viewmodel_client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model_client.ModelClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class ChessViewModel extends ViewModel implements PropertyChangeListener  {

	private ObservableList<String> chatList;
	private boolean isWhite = true;
	private final String FEN="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private StringProperty notationProperty, chatProperty;

	private ViewState viewState;


	private ModelClient model;

	public ChessViewModel(ModelClient model, ViewState viewState) {
		this.model=model;
		this.viewState=viewState;
		this.notationProperty = new SimpleStringProperty();
		this.chatProperty = new SimpleStringProperty();
		this.model.addListener(this);

		chatList = FXCollections.observableArrayList();
	}

	public ObservableList<String> getChatList(){
		return chatList;
	}


public synchronized boolean setSpectator()
{
	return model.setSpectator();
}
	public synchronized void clear() {

		this.notationProperty.set(FEN);
		loadChat();
	}

	private synchronized  void loadChat(){
		Platform.runLater( () -> {
			chatList.clear();
			for(String chat : getChat())
				chatList.add(chat);
		});
	}
	public String getRoomId()
	{
		return viewState.getRoomId();
	}

	public void sendNotation(String notation) {

		if( isWhite ) model.sendNotation(viewState.getRoomId(), notation);
		else {
			StringBuilder builder = new StringBuilder(notation.split(" ")[0]);
			model.sendNotation(viewState.getRoomId(), String.valueOf(builder.reverse()));
		}

	}

	public void sendChatMessage(){
		
		model.sendChatMessage(viewState.getRoomId(),viewState.getName(),chatProperty.get());
		chatProperty.set("");;
	}

	public StringProperty getNotationProperty(){return notationProperty;}
	public StringProperty getChatProperty(){return chatProperty;}


	public void changeView(){
		isWhite = !isWhite;
		StringBuilder builder;
		if(notationProperty.get().contains(" ")){
			builder = new StringBuilder(notationProperty.get().split(" ")[0]);
		}else{
			builder = new StringBuilder(notationProperty.get());
		}
		notationProperty.set(String.valueOf(builder.reverse()));
	}

	public synchronized ArrayList<String> getChat()
	{
		return model.getAllChat(viewState.getRoomId());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String type = evt.getPropertyName();

		switch(type){
			case "ERROR":
//				no error property yet
				break;
			case "NOTATION":
				String notation = (String)evt.getNewValue();
				if( isWhite) notationProperty.set(notation);
				else {
					StringBuilder builder = new StringBuilder(notation.split(" ")[0]);
					notationProperty.set(String.valueOf(builder.reverse()));
				}
				break;
			case "CHAT":
				Platform.runLater( ()->{
					chatList.add(0,(String)evt.getNewValue());
				});
				break;
		}
	}

}
