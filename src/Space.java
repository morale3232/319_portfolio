import javax.swing.JLabel;

public class Space {

	Boolean taken = false;
	int player = -1;
	int coor_x = -1;
	int coor_y = -1;
	JLabel label;


	public Space(int row, int col) {
		this.coor_x = row;
		this.coor_y = col;
	}
	
	public JLabel getLabel() {
		return label;
	}

	public void setLabel( JLabel label ) {
		this.label = label;
	}

	public Boolean getTaken() {
		return taken;
	}

	public void setTaken( Boolean taken ) {
		this.taken = taken;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer( int player ) {
		this.player = player;
	}

	public int getCoor_x() {
		return coor_x;
	}

	public void setCoor_x( int coor_x ) {
		this.coor_x = coor_x;
	}

	public int getCoor_y() {
		return coor_y;
	}

	public void setCoor_y( int coor_y ) {
		this.coor_y = coor_y;
	}
}
