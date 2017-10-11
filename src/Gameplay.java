import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings( "serial" )
public class Gameplay extends JFrame {
	static final String gapList[] = { "0", "10", "15", "20" };
	final static int maxGap = 20;
	static final int numRows = 6;
	static final int numCols = 7;
	static int currentTurn = 0;
	static final Color player1Color = Color.red;
	static final Color player2Color = Color.blue;
	GridLayout experimentLayout = new GridLayout(numRows, numCols);
	final JPanel compsToExperiment = new JPanel();
	Board board = new Board();

	public Gameplay( String name ) {
		super(name);
		setResizable(true);
	}

	private boolean checkForWin( int r, int c ) {

		if ( checkHorizontal(r, c) || checkVertical(r, c) || checkDiagDownRight(r, c) || checkDiagUpRight(r, c) ) {
			return true;
		}
		return false;
	}

	private boolean checkHorizontal( int r, int c ) {
		int count = 0;
		char currSymbol = board.getSpaceAtLocation(r, c).getOwner().getSymbol();
		for ( int i = Math.max(0, c - 3); i < Math.min(numCols, c + 3); i++ ) {
			if ( board.getSpaceAtLocation(r, i).getTaken() ) { // if space is occupied
				if ( board.getSpaceAtLocation(r, i).getOwner().getSymbol() == currSymbol ) {
					count++;
				} else {
					count = 1;
				}
			}
		}
		if ( count >= 4 ) {
			System.out.println("WINNER HORI");
			// return true;
		}
		return false;
	}

	private boolean checkVertical( int r, int c ) {
		int count = 0;
		char currSymbol = board.getSpaceAtLocation(r, c).getOwner().getSymbol();
		for ( int i = Math.max(0, r - 4); i < Math.min(numRows, r + 4); i++ ) {
			if ( board.getSpaceAtLocation(i, c).getTaken() ) {
				if ( board.getSpaceAtLocation(i, c).getOwner().getSymbol() == currSymbol ) {
					count++;
				} else {
					count = 1;
				}
			}
		}
		if ( count >= 4 ) {
			System.out.println("WINNER VERT");
			// return true;
		}
		return false;
	}

	private boolean checkDiagDownRight( int r, int c ) {
		int count = 0;
		char currSymbol = board.getSpaceAtLocation(r, c).getOwner().getSymbol();

		for ( int i = r - 3, j = c - 3; i < r + 4 && j < c + 4; i++, j++ ) {
			if ( 0 <= i && i < numRows && 0 <= j && j < numCols ) {
				if ( board.getSpaceAtLocation(i, j).getTaken() ) {
					if ( board.getSpaceAtLocation(i, j).getOwner().getSymbol() == currSymbol ) {
						count++;
					} else {
						count = 1;
					}
				}
			}
			if ( count == 4 ) {
				System.out.println("Winner DiagDown");
				// return true;
			}
		}
		return false;
	}

	private boolean checkDiagUpRight( int r, int c ) {
		int count = 0;
		char currSymbol = board.getSpaceAtLocation(r, c).getOwner().getSymbol();

		for ( int i = r + 3, j = c - 3; i > r - 4 && j < c + 4; i--, j++ ) {
			if(0<= i && i<numRows && 0<=j && j<numCols) {
				if(board.getSpaceAtLocation(i, j).getTaken()) {
					if(board.getSpaceAtLocation(i, j).getOwner().getSymbol() == currSymbol) {
						count++;
					} else {
						count = 1;
					}
				}
			}
			if(count==4) {
				System.out.println("WINNER DiagUp");
				return true;
			}
		}
		return false;
	}

	public void addComponentsToPane( final Container pane ) {
		experimentLayout.setHgap(20);
		JPanel insertButtonsPane = new JPanel();
		insertButtonsPane.setLayout(new GridLayout(1, numCols));
		compsToExperiment.setLayout(experimentLayout);

		for ( int r = 0; r < numRows; r++ ) {
			for ( int c = 0; c < numCols; c++ ) {
				JLabel temp = new JLabel(Integer.toString(c) + ", " + Integer.toString(r), JLabel.CENTER);
				temp.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.black));
				temp.setOpaque(true);
				temp.setBackground(Color.white);
				compsToExperiment.add(temp);
				board.getSpaceAtLocation(r, c).setLabel(temp);
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
		pane.add(new JSeparator(), BorderLayout.CENTER);
		pane.add(compsToExperiment, BorderLayout.SOUTH);
	}

	private void handleButtonPress( int column ) {
		int spaceToInsert = board.getLowestAvailableSpace(column);
		if ( spaceToInsert == -1 ) {
			// TODO error message
			System.out.println("This column is full");
			return;
		}

		Space toSet = board.getSpaceAtLocation(spaceToInsert, column);
		toSet.setTaken(true);

		if ( currentTurn % 2 == 1 ) { // player 1
			toSet.setOwner(new Player("Alex", 't', null));
			toSet.getLabel().setBackground(player1Color);
			checkForWin(spaceToInsert, column);
		} else { // Player 2
			toSet.setOwner(new Player("John", 'a', null));
			toSet.getLabel().setBackground(player2Color);
			checkForWin(spaceToInsert, column);
		}

	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		Gameplay frame = new Gameplay("GridLayoutDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set up the content pane.
		frame.addComponentsToPane(frame.getContentPane());
		// Display the window.

		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main( String[] args ) {

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
