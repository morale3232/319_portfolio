
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class GameServer {
	static ArrayList<PlayerHandler> players = new ArrayList<PlayerHandler>();;
	int portNum = 4444;
	PlayerHandler playerWaiting = null;
	static int count;
	ServerSocket serverSocket;

	public static void main(String[] args) throws IOException {
		GameServer game = new GameServer();
		try {
			game.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() {
		try {
			serverSocket = new ServerSocket(portNum);
			System.out.println("Server is running");
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					
					PlayerHandler player = new PlayerHandler(this, clientSocket);
					players.add(player);
					count++;

					player.start();

					System.out.println("Player " + count + " created");
					if(count == 2){
						break;
					}

				} catch (IOException e) {
					System.out.println("Accept failed: " + portNum);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class PlayerHandler extends Thread {

	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	GameServer server;

	PlayerHandler(GameServer server, Socket s) {
		this.s = s;
		this.server = server;
	}

	public void run() {
		try {
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			communicate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate(){
		try {
			String receiveMsg;
			while (true) {
				if ((receiveMsg = in.readLine()) != null) {
					for (PlayerHandler p : GameServer.players) {
						if (!p.equals(this)){
							p.out.println(receiveMsg);
						}
					}
					//System.out.println(receiveMsg);
				}
			}
		} catch (IOException e) {
			try {
				server.serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}