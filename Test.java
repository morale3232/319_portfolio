package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings( "serial" )
public class Test extends JFrame {
	static final String gapList[] = {"0", "10", "15", "20"};
    final static int maxGap = 20;
    JButton applyButton = new JButton("Apply gaps");
    GridLayout experimentLayout = new GridLayout(6,6);
    
    static final int numRows = 6;
    static final int numCols = 6;
    
    
    public Test(String name) {
        super(name);
        setResizable(true);
    }
     
    public void addComponentsToPane(final Container pane) {
        experimentLayout.setHgap(10);
        JPanel insertButtonsPane = new JPanel();
        insertButtonsPane.setLayout(new GridLayout(1, 6));
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
         
        //Set up components preferred size
//        JButton b = new JButton("Just fake button");
//        Dimension buttonSize = b.getPreferredSize();
//        compsToExperiment.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+maxGap,
//                (int)(buttonSize.getHeight() * 3.5)+maxGap * 2));
        
        for(int r = 0; r < numRows; r++) {
        		for (int c = 0; c < numCols; c++) {
        			JLabel temp = new JLabel(Integer.toString(r)+ ", " + Integer.toString(c));
        			temp.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.black));
        			compsToExperiment.add(temp);
        			
        		}
        		JButton button = new JButton(Integer.toString(r));
        		String temp_r = Integer.toString(r);
        		button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed( ActionEvent e ) {
						// TODO call insertPiece function
						System.out.println(temp_r);
					}
				});
        		insertButtonsPane.add(button);
        }
         
        //Process the Apply gaps button press
        applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Get the horizontal gap value
                String horGap = (String)horGapComboBox.getSelectedItem();
                //Get the vertical gap value
                String verGap = (String)verGapComboBox.getSelectedItem();
                //Set up the horizontal gap value
                experimentLayout.setHgap(Integer.parseInt(horGap));
                //Set up the vertical gap value
                experimentLayout.setVgap(Integer.parseInt(verGap));
                //Set up the layout of the buttons
                experimentLayout.layoutContainer(compsToExperiment);
            }
        });
        
        pane.add(insertButtonsPane, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(compsToExperiment, BorderLayout.SOUTH);
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        Test frame = new Test("GridLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
