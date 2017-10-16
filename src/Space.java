import javax.swing.JLabel;

public class Space {

	Boolean taken = false;
	GamePlayer owner;
	int coor_x = -1;
	int coor_y = -1;
	JLabel label;

	public Space( int row, int col ) {
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

	public GamePlayer getOwner() {
		return owner;
	}

	public void setOwner( GamePlayer p1 ) {
		this.owner = p1;
	}
}
