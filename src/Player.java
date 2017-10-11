import java.net.Socket;

public class Player {

	private String name;
	private String symbol;
	private Socket socket;
	
	public Player() {
		this.name = "";
		this.symbol = " ";
		this.socket = null;
	}
	
	public Player(String name, String symbol, Socket socket) {
		setName(name);
		setSymbol(symbol);
		setSocket(socket);
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return this.symbol;
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
