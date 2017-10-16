
public class Gameplay {
	static final String gapList[] = { "0", "10", "15", "20" };
	final static int maxGap = 20;
	static final int numRows = 6;
	static final int numCols = 7;

	public Gameplay() {

	}

	public static boolean checkForWin(GamePlayer gamePlayer, int r, int c) {

		if (checkHorizontal(gamePlayer, r, c) || checkVertical(gamePlayer, r, c) || checkDiagDownRight(gamePlayer, r, c)
				|| checkDiagUpRight(gamePlayer, r, c)) {
			return true;
		}
		return false;
	}

	private static boolean checkHorizontal(GamePlayer gamePlayer, int r, int c) {
		int count = 0;
		for (int i = Math.max(0, c - 3); i < Math.min(numCols, c + 3); i++) {
			if (gamePlayer.board.getSpaceAtLocation(r, i).getTaken()) {
				GamePlayer g = gamePlayer.board.getSpaceAtLocation(r, i).getOwner();
				if (g == gamePlayer) {
					count++;
				} else {
					count = 0;
				}
			}
		}
		if (count >= 4) {
			System.out.println("WINNER HORI");
			return true;
		}
		return false;
	}

	private static boolean checkVertical(GamePlayer gamePlayer, int r, int c) {
		int count = 0;
		for (int i = Math.max(0, r - 4); i < Math.min(numRows, r + 4); i++) {
			if (gamePlayer.board.getSpaceAtLocation(i, c).getTaken()) {
				GamePlayer g = gamePlayer.board.getSpaceAtLocation(i, c).getOwner();
				if (g == gamePlayer) {
					count++;
				} else {
					count = 0;
				}
			}
		}
		if (count >= 4) {
			System.out.println("WINNER VERT");
			return true;
		}
		return false;
	}

	private static boolean checkDiagDownRight(GamePlayer gamePlayer, int r, int c) {
		int count = 0;
		for (int i = r - 3, j = c - 3; i < r + 4 && j < c + 4; i++, j++) {
			if (0 <= i && i < numRows && 0 <= j && j < numCols) {
				if (gamePlayer.board.getSpaceAtLocation(i, j).getTaken()) {
					if (gamePlayer.board.getSpaceAtLocation(i, j).getOwner() == gamePlayer) {
						count++;
					} else {
						count = 0;
					}
				}
			}
			if (count == 4) {
				System.out.println("Winner DiagDown");
				return true;
			}
		}
		return false;
	}

	private static boolean checkDiagUpRight(GamePlayer gamePlayer, int r, int c) {
		int count = 0;
		for (int i = r + 3, j = c - 3; i > r - 4 && j < c + 4; i--, j++) {
			if (0 <= i && i < numRows && 0 <= j && j < numCols) {
				if (gamePlayer.board.getSpaceAtLocation(i, j).getTaken()) {
					if (gamePlayer.board.getSpaceAtLocation(i, j).getOwner() == gamePlayer) {
						count++;
					} else {
						count = 0;
					}
				}
			}
			if (count == 4) {
				System.out.println("WINNER DiagUp");
				return true;
			}
		}
		return false;
	}
}
