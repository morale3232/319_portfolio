package testing;

public class Board {
	private int numRows = 6;
	private int numCols = 7;
	
	private Space[][] array = new Space[numRows][numCols];
	
	public Board() {
		for(int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				array[r][c] = new Space(r, c);
			}
		}
	}
	
	public int getLowestAvailableSpace(int column) {
		for(int r = numRows-1; r >=0; r--) {
			if(!(array[r][column].taken)) {
				return r;
			}
		}
		
		return -1; // row is full
	}
	
	public Space getSpaceAtLocation(int r, int c) {
		return array[r][c];
	}

}

