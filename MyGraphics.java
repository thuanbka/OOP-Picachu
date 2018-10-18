package thuanoop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
public final class MyGraphics extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	 int row,col;
	private final int bound = 2;
	private final int size = 70;
	private JButton[][] btn;
	private Point p1 = null;
	private Point p2 = null;
	private Algorithm algorithm;
	private MyLine line;
	private final MyFrame frame;
	private final Color backGroundColor = Color.lightGray;
	private int item;
	public MyGraphics(MyFrame frame, int row, int col) {
		this.frame = frame;
		this.row = row + 2;
		this.col = col + 2;
		item = row * col / 2;
                algorithm = new Algorithm(this.row, this.col);
		setLayout(new GridLayout(row, col, bound, bound));
		setBackground(backGroundColor);
		setPreferredSize(new Dimension((size + bound) * col, (size + bound)* row));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setAlignmentY(JPanel.CENTER_ALIGNMENT);
		newGame();

	}

	public void newGame() {
		
		addArrayButton();

	}

	private void addArrayButton() {
		btn = new JButton[row][col];
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				btn[i][j] = createButton(i + "," + j);
				Icon icon = getIcon(algorithm.getMatrix()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}

	private Icon getIcon(int index) {
		int width = 65, height = 65;
		Image image = new ImageIcon(getClass().getResource("/thuanoop/icon/icon" + index + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height,Image.SCALE_SMOOTH));
		return icon;
	}

	private JButton createButton(String action) {
		JButton a = new JButton();
		a.setActionCommand(action);
		a.setBorder(null);
		a.addActionListener(this);
		return a;
	}

	public void execute(Point p1, Point p2) {
		System.out.println("delete");
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}

	private void setDisable(JButton btn) {
		btn.setIcon(null);
		btn.setBackground(backGroundColor);
		btn.setEnabled(false);
	}
        public void check(){
            algorithm.check();
            btn[algorithm.pb.x][algorithm.pb.y].setBorder(new LineBorder(Color.GREEN,5));
            btn[algorithm.pa.x][algorithm.pa.y].setBorder(new LineBorder(Color.GREEN,5));
        }

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnIndex = e.getActionCommand();
		int indexDot = btnIndex.lastIndexOf(",");
		int x = Integer.parseInt(btnIndex.substring(0, indexDot));
		int y = Integer.parseInt(btnIndex.substring(indexDot + 1,btnIndex.length()));
                    if (p1 == null) {
			p1 = new Point(x, y);
			btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
		    }   else {
                                p2 = new Point(x, y);
                                btn[p2.x][p2.y].setBorder(new LineBorder(Color.red));
                                System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "("
                                                + p2.x + "," + p2.y + ")");
                                line = algorithm.checkTwoPoint(p1, p2);
                                if (line != null) {
                                        System.out.println("line != null");
                                        algorithm.getMatrix()[p1.x][p1.y] = 0;
                                        algorithm.getMatrix()[p2.x][p2.y] = 0;
                                        algorithm.showMatrix();
                                        execute(p1, p2);
                                        line = null;
                                        frame.score +=100;
                                        item--;
                                        frame.getLbScore().setText(frame.score + "");
                                }
                                btn[p2.x][p2.y].setBorder(null);
                                btn[p1.x][p1.y].setBorder(null);
                                p1 = null;
                                p2 = null;      
                                System.out.println("done");         
                        }   
                
			if (item == 0) {
                            if(row==12){
                                int click=JOptionPane.showConfirmDialog(null,"Bạn có muốn chơi lại?","You Win",JOptionPane.YES_NO_OPTION);
                                 if(click==JOptionPane.YES_OPTION)   {    
                                        frame.score=0;
                                        frame.time=200;
                                         frame.newGame(6, 6);
                                         
                                 }
                                       else System.exit(0);
                            }
                            else{
                                try {
                                     frame.score=frame.score+frame.time*10+500;
                                     frame.showDialogNewGame();
                                } catch (IOException ex) {
                                    Logger.getLogger(MyGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }   
                            }
			}
                        else if(!algorithm.check()) {
                            //algorithm.swapMatrix();
                            //algorithm.showMatrix();
                            //newGame();
                             // newGame();
                           // addArrayButton();
                             int click=JOptionPane.showConfirmDialog(null,"Bạn có muốn chơi lại?","Game over",JOptionPane.YES_NO_OPTION);
                                 if(click==JOptionPane.YES_OPTION){
                                     frame.time=100;
                                     frame.score=0;
                                     frame.newGame(6, 6);
                                 } 
                                 else System.exit(0);            
                        }
        }
}

