package viewmodel_client;

public class ViewState {

	private String notation;

	private String roomId;

	private String name;

	public ViewState() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotation() {
		return notation;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setNotation(String notation) {
		this.notation=notation;
	}

	public void setRoomId(String roomId) {
		this.roomId=roomId;
	}

}
