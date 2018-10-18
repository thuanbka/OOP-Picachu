package thuanoop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
public final class Algorithm {
	private int row;
	private int col;
	private final int notBarrier = 0;
	private int[][] matrix;
          boolean t=true;
          Point pa=null,pb=null;
	public Algorithm(int row, int col) {
		this.row = row;
		this.col = col;
                
		System.out.println(row + "," + col);
		createMatrix();
                swapMatrix();
		showMatrix();
               
	}
        //createMatrix
        private void createMatrix() {
		matrix = new int[row][col];
		for (int i = 0; i < col; i++) {
			matrix[0][i] = matrix[row - 1][i] = 0;
		}
		for (int i = 0; i < row; i++) {
			matrix[i][0] = matrix[i][col - 1] = 0;
		}

		Random rand = new Random();
		// int imgNumber = row * col / 4;
		int imgNumber = 42;
		int maxDouble = imgNumber / 4;
		int imgArr[] = new int[imgNumber + 1];
		ArrayList<Point> listPoint = new ArrayList<>();
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				listPoint.add(new Point(i, j));
			}
		}
		int i = 0;
		do {
			int imgIndex = rand.nextInt(imgNumber) + 1;
			if (imgArr[imgIndex] < maxDouble) {
				imgArr[imgIndex] += 2;
				for (int j = 0; j < 2; j++) {
					try {
						int size = listPoint.size();
						int pointIndex = rand.nextInt(size);
						matrix[listPoint.get(pointIndex).x][listPoint
								.get(pointIndex).y] = imgIndex;
						listPoint.remove(pointIndex);
					} catch (Exception e) {
					}
				}
				i++;
			}
		} while (i < row * col / 2);
	}
        //thay doi matrix
          public void swapMatrix(){
            Random rand = new Random();
            Vector<Point> m = new Vector<>();
            for (int i = 1; i < row - 1; i++) {
                    for (int j = 1; j < col - 1; j++) {
                            m.add(new Point(i, j));
                    }
            }
            Vector<Integer> a = new Vector<>();

            for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				if(matrix[i][j]!=0)
                                    a.add(matrix[i][j]);          
			}
            }
            
            while(a.size()>0){
                 int size = m.size();
                int t=rand.nextInt(a.size());
                int pointIndex = rand.nextInt(size);
                int k=a.get(t);
                matrix[m.get(pointIndex).x][m.get(pointIndex).y] =k;
                a.remove(t);
                m.remove(pointIndex);    
            }
            if(m.size()>0){
                for(int i=0;i<m.size();i++)
                   matrix[m.get(i).x][m.get(i).y] =0;
            }
        }

	// show matrix
	public void showMatrix() {
            
            if(t)
            {
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				System.out.printf("%3d", matrix[i][j]);
			} 
                       
			System.out.println();
		}
                
                 t=check(); 
                 
               
            }
            else System.out.println("Game Over");
	}

	// check with line x, from column y1 to y2
	private boolean checkLineX(int y1, int y2, int x) {
		// find point have column max and min
		int min = Math.min(y1, y2);
		int max = Math.max(y1, y2);
		// run column
		for (int y = min + 1; y < max; y++) {
			if (matrix[x][y] > notBarrier) { // if see barrier then die
				return false;
			}
		}
		return true;
	}

	private boolean checkLineY(int x1, int x2, int y) {
		int min = Math.min(x1, x2);
		int max = Math.max(x1, x2);
		for (int x = min + 1; x < max; x++) {
			if (matrix[x][y] > notBarrier) {
				return false;
			}
		}
		return true;
	}

	// check in rectangle
	private int checkRectX(Point p1, Point p2) {
		System.out.println("check rect x");
		// find point have y min and max
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for (int y = pMinY.y; y <= pMaxY.y; y++) {
			if (y > pMinY.y && matrix[pMinY.x][y] > notBarrier) {
				return -1;
			}
			// check two line
			if ((matrix[pMaxY.x][y] == notBarrier || y == pMaxY.y)
					&& checkLineY(pMinY.x, pMaxY.x, y)
					&& checkLineX(y, pMaxY.y, pMaxY.x)) {

				System.out.println("Rect x");
				System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
						+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
						+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
				// if three line is true return column y
				return y;
			}
		}
		// have a line in three line not true then return -1
		return -1;
	}

	private int checkRectY(Point p1, Point p2) {
		System.out.println("check rect y");
		// find point have y min
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		// find line and y begin
		for (int x = pMinX.x; x <= pMaxX.x; x++) {
			if (x > pMinX.x && matrix[x][pMinX.y] > notBarrier) {
				return -1;
			}
			if ((matrix[x][pMaxX.y] == notBarrier || x == pMaxX.x)
					&& checkLineX(pMinX.y, pMaxX.y, x)
					&& checkLineY(x, pMaxX.x, pMaxX.y)) {

				System.out.println("Rect y");
				System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
						+ "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
						+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
				return x;
			}
		}
		return -1;
	}

	/**
	 * p1 and p2 are Points want check
	 * 
	 * @param type
	 *            : true is check with increase, false is decrease return column
	 *            can connect p1 and p2
	 */
	private int checkMoreLineX(Point p1, Point p2, int type) {
		System.out.println("check chec more x");
		// find point have y min
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		// find line and y begin
		int y = pMaxY.y + type;
		int hang = pMinY.x;
		int colFinish = pMaxY.y;
		if (type == -1) {
			colFinish = pMinY.y;
			y = pMinY.y + type;
			hang = pMaxY.x;
			System.out.println("colFinish = " + colFinish);
		}

		// find column finish of line

		// check more
		if ((matrix[hang][colFinish] == notBarrier || pMinY.y == pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, hang)) {
			while (matrix[pMinY.x][y] == notBarrier
					&& matrix[pMaxY.x][y] == notBarrier) {
				if (checkLineY(pMinY.x, pMaxY.x, y)) {

					System.out.println("TH X " + type);
					System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
							+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
							+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
					return y;
				}
				y += type;
			}
		}
		return -1;
	}

	private int checkMoreLineY(Point p1, Point p2, int type) {
		System.out.println("check more y");
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		int x = pMaxX.x + type;
		int cot = pMinX.y;
		int rowFinish = pMaxX.x;
		if (type == -1) {
			rowFinish = pMinX.x;
			x = pMinX.x + type;
			cot = pMaxX.y;
		}
		if ((matrix[rowFinish][cot] == notBarrier || pMinX.x == pMaxX.x)
				&& checkLineY(pMinX.x, pMaxX.x, cot)) {
			while (matrix[x][pMinX.y] == notBarrier
					&& matrix[x][pMaxX.y] == notBarrier) {
				if (checkLineX(pMinX.y, pMaxX.y, x)) {
					System.out.println("TH Y " + type);
					System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
							+ x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
							+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
					return x;
				}
				x += type;
			}
		}
		return -1;
	}
        public  boolean check(){
            for(int i=1;i<row-1;i++)
            { 
                for(int j=1;j<col-1;j++)
                {  pa = new Point(i, j);
                    
                    for(int k=1;k<row-1;k++)
                    {
                        for(int l=1;l<col-1;l++)
                        { 
                                pb = new Point(k, l);
                                if(checkTwoPoint(pa, pb)!=null){
                                   // System.out.println("("+pa.x+","+pa.y+")"+"->"+"("+pb.x+","+pb.y+")");
                                    return true;
                                }                           
                                else continue;
                        }
                    }  
                } 
            }
            return false;
        }

	public MyLine checkTwoPoint(Point p1, Point p2) {
		if (!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]&& matrix[p1.x][p1.y]!=0) {
			// check line with x
			if (p1.x == p2.x) {
				if (checkLineX(p1.y, p2.y, p1.x)) {
                                    System.out.println("("+p1.x+","+p1.y+")"+"->"+"("+p2.x+","+p2.y+")");
					return new MyLine(p1, p2);
				}
			}
			// check line with y
			if (p1.y == p2.y) {
				if (checkLineY(p1.x, p2.x, p1.y)) {
                                     System.out.println("("+p1.x+","+p1.y+")"+"->"+"("+p2.x+","+p2.y+")");
					return new MyLine(p1, p2);
				}
			}

			int t = -1; // t is column find
			// check in rectangle with x
			if ((t = checkRectX(p1, p2)) != -1) {
				return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
			}

			// check in rectangle with y
			if ((t = checkRectY(p1, p2)) != -1) {
				return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
			}
			// check more right
			if ((t = checkMoreLineX(p1, p2, 1)) != -1) {
				return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
			}
			// check more left
			if ((t = checkMoreLineX(p1, p2, -1)) != -1) {
				return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
			}
			// check more down
			if ((t = checkMoreLineY(p1, p2, 1)) != -1) {
				return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
			}
			// check more up
			if ((t = checkMoreLineY(p1, p2, -1)) != -1) {
				return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
			}
		}
		return null;
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
}
