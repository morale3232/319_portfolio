import java.net.Socket;

public class Player {

	private String name;
	
	private Socket socket;
	
	public Player() {
		this.name = "";
		this.socket = null;
	}
	
	public Player(String name, Socket socket) {
		setName(name);
		setSocket(socket);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket( Socket socket ) {
		this.socket = socket;
	}

	
}
