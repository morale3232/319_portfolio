
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePlayer extends JFrame {

	private static final long serialVersionUID = 1L;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	BufferedReader clientIn;
	ServerListener sl;
	private static String serverIP = "localhost";
	final static int serverPort = 4444;
	GridLayout experimentLayout = new GridLayout(numRows, numCols);
	final JPanel compsToExperiment = new JPanel();
	static final int numRows = 6;
	static final int numCols = 7;
	Board board = new Board();
	boolean myTurn = true;
	static boolean connected = false;

	public static void main( String[] args ) throws IOException {
		GamePlayer player = new GamePlayer();
		player.sl = new ServerListener(player);
		new Thread(player.sl).start();
	}

	public GamePlayer() {
		try {
			clientIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter IP Address to connect to server:");
			serverIP = clientIn.readLine();
			System.out.println("You entered " + serverIP);
			connect();
			if ( connected ) {
				System.out.println("Connected");
				createAndShowGUI();
			} else {
				System.out.println("Could not connect. Exiting program.");
				System.exit(-1);
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void connect() {
		try {
			socket = new Socket(serverIP, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch ( IOException e ) {
			System.out.println("Could not connect. Exiting program.");
			System.exit(-1);
			e.printStackTrace();
		}
	}

	private void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(getContentPane());
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	private void addComponentsToPane( final Container pane ) {
		experimentLayout.setHgap(20);
		JPanel insertButtonsPane = new JPanel();
		insertButtonsPane.setLayout(new GridLayout(1, numCols));
		compsToExperiment.setLayout(experimentLayout);

		for ( int r = 0; r < numRows; r++ ) {
			for ( int col = 0; col < numCols - 1; col++ ) {
				JLabel temp = new JLabel(" ", JLabel.CENTER);
				temp.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.black));
				temp.setOpaque(true);
				temp.setBackground(Color.white);
				compsToExperiment.add(temp);
				board.getSpaceAtLocation(r, col).setLabel(temp);
			}
			JButton button = new JButton(Integer.toString(r + 1));
			int temp_c = r;
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed( ActionEvent e ) {
					handleButtonPress(temp_c);
				}
			});
			insertButtonsPane.add(button);
		}

		pane.add(insertButtonsPane, BorderLayout.NORTH);
		pane.add(compsToExperiment, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				System.out.println("Window Closed");
				try {
					out.close();
					in.close();
					socket.close();
				} catch ( Exception ioe ) {
					ioe.printStackTrace();
				}
			}
		});

	}

	private void handleButtonPress( int column ) {
		if ( myTurn ) {
			int spaceToInsert = board.getLowestAvailableSpace(column);
			if ( spaceToInsert == -1 ) {
				myTurn = true;
				System.out.println("This column is full");
				boolean gameOver = board.isBoardFull();
				if ( !gameOver )
					return;
				else {
					out.println("tie");
					this.gameEnd("You tied!");
					return;
				}
			}
			Space toSet = board.getSpaceAtLocation(spaceToInsert, column);
			setSpace(this, toSet);

			if ( Gameplay.checkForWin(this, spaceToInsert, column) ) {
				out.println("move" + spaceToInsert + "," + column);
				out.println("lose");
				this.gameEnd("You win!");
			} else if ( board.isBoardFull() ) {
				out.println("tie");
				this.gameEnd("You tied!");
				return;
			}
			out.println("move" + spaceToInsert + "," + column);
			myTurn = false;
		}
	}

	public void setSpace( GamePlayer gamePlayer, Space toSet ) {
		toSet.setTaken(true);
		toSet.setOwner(gamePlayer);
		if ( gamePlayer == this ) {
			toSet.getLabel().setText("O");
			toSet.getLabel().setBackground(Color.red);
		} else {
			toSet.getLabel().setText("X");
			toSet.getLabel().setBackground(Color.blue);
		}
	}

	// public void lose() {
	// JLabel lose = new JLabel("YOU LOST");
	// this.add(lose, BorderLayout.WEST);
	// this.pack();
	// this.setVisible(true);
	// this.setEnabled(false);
	// }
	//
	// public void win() {
	// JLabel win = new JLabel("YOU WON");
	// this.add(win, BorderLayout.WEST);
	// this.pack();
	// this.setVisible(true);
	// this.setEnabled(false);
	// }
	//
	// public void tie() {
	// JLabel win = new JLabel("YOU TIED. GAME OVER.");
	// this.add(win, BorderLayout.WEST);
	// this.pack();
	// this.setVisible(true);
	// this.setEnabled(false);
	// }

	public void gameEnd( String message ) {
		JLabel messageLabel = new JLabel(message);
		this.add(messageLabel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		this.setEnabled(false);
	}

}

class ServerListener extends Thread {
	private BufferedReader in;
	private GamePlayer player;

	ServerListener( GamePlayer player ) {
		this.player = player;
		in = player.in;
	}

	public void run() {
		while ( true ) {
			try {
				String receiveMsg = in.readLine();
				handleMessage(receiveMsg);
			} catch ( IOException e ) {

				System.out.println("Stream has closed.");
				try {
					player.out.close();
					player.in.close();
					player.socket.close();
				} catch ( IOException e1 ) {
					e1.printStackTrace();
				}
				break;
			} catch ( NullPointerException e ) {
				continue;
			}
		}
	}

	public void handleMessage( String msg ) {
		if ( msg == null ) {
			System.exit(-1);
		}
		if ( msg.contains("msg") ) { // for debugging
			String newMsg = msg.replace("msg", "");
			System.out.println(newMsg);
		} else if ( msg.contains("move") ) { // set move from other player
			String newMsg = msg.replace("move", "");
			String[] move = newMsg.split(",");
			Space toSet = player.board.getSpaceAtLocation(Integer.parseInt(move[0]), Integer.parseInt(move[1]));
			player.setSpace(null, toSet);
			player.myTurn = true;
		} else if ( msg.contains("lose") ) { // you lost the game :(
			System.out.println("YOU LOST");
			player.gameEnd("You lose!");
		} else if ( msg.contains("tie") ) { // you tied
			System.out.println("YOU TIED");
			player.gameEnd("You tied");
		}
	}
}
