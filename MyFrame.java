package thuanoop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class MyFrame extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	private final String author = "Nhóm Bài tập lớn OOP";
	private final int maxTime=100 ;
	public int time=maxTime;
        public  int score=0;
	int row=6 ;
        int col=6;
	private final int width = 1000;
	private final int height = 850;
	private  JTextField lbScore;
	JProgressBar progressTime;
	private JButton btnNewGame,button;
	private MyGraphics graphicsPanel;
	private final JPanel mainPanel;
        InputStream in,on;
        

	public MyFrame() throws IOException  {
		add(mainPanel = createMainPanel());
		setTitle(author+":  Pokemon -----Màn: "+1);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);        
        }

	private JPanel createMainPanel() throws IOException {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.EAST);
		panel.add(createStatusPanel(), BorderLayout.PAGE_END);
                
		return panel;
	}

	private JPanel createGraphicsPanel() {
		graphicsPanel = new MyGraphics(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.gray);
		panel.add(graphicsPanel);
                
		return panel;
	}
	private JPanel createControlPanel() {
		lbScore =  new JTextField("0");
                lbScore.setHorizontalAlignment(JTextField.CENTER);
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

		// create panel container score and time

		JPanel panelLeft = new JPanel(new GridLayout(3, 10, 8, 5));
		panelLeft.add(new JLabel("Score:"));
		panelLeft.add(new JLabel("Time:"));
              

		JPanel panelCenter = new JPanel(new GridLayout(3, 10, 8, 5));
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(20, 0));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

		// create panel container panelScoreAndTime and button new game
		JPanel panelControl = new JPanel(new BorderLayout(20, 10));
		panelControl.setBorder(new EmptyBorder(0, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.NORTH);
                panelControl.add(button=createButton("Gợi ý"),BorderLayout.CENTER);
		panelControl.add(btnNewGame = createButton("New Game"),BorderLayout.SOUTH);
                

		Icon icon = new ImageIcon(getClass().getResource(
				"/thuanoop/icon/pokemon.png"));

		// use panel set Layout BorderLayout to panel control in top
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Status"));
		panel.add(panelControl, BorderLayout.PAGE_START);
		panel.add(new JLabel(icon), BorderLayout.CENTER);
		return panel;
	}

	// create status panel container author
	private JPanel createStatusPanel() throws IOException  {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton a = new JButton("Tắt âm");
                JButton b = new JButton("Mở âm");
                b.setForeground(Color.blue);
		a.setForeground(Color.blue);
                panel.add(b,FlowLayout.LEFT);
                panel.add(a,FlowLayout.LEFT);
                b.addActionListener(new ActionListener(){
                     @Override
                     public void actionPerformed(ActionEvent e) {
                        try {
                            in=new FileInputStream(new File("D:\\java\\oop\\src\\thuanoop\\PokemonSong-Pikachu-4630769.wav"));
                            AudioStream audios;
                            audios = new AudioStream(in);
                            AudioPlayer.player.start(audios);
                        } catch (IOException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                a.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                           
                            in.close();
                            on.close();
                          
                        } catch (IOException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                in=new FileInputStream(new File("D:\\java\\oop\\src\\thuanoop\\PokemonSong-Pikachu-4630769.wav"));
                AudioStream audios;
                audios = new AudioStream(in);
                AudioPlayer.player.start(audios);
		return panel;
               
	}

	// create a button
	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	public void newGame(int a,int b)  {
                row=a;
                col=b;
		//time = maxTime;
		graphicsPanel.removeAll();
                setTitle(author + ": Pokemon ---- Màn: "+(a/2-2));
		mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.setVisible(true);
                lbScore.setText(score+"");
                
	}
        

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewGame) {
                    try {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        on=new FileInputStream(new File("D:\\java\\oop\\src\\thuanoop\\SieuNhanCuongPhong-Takeshi_spnu.wav"));
                        AudioStream audios;
                        audios = new AudioStream(on);
                        AudioPlayer.player.start(audios);
                        time=maxTime;
                        score=0;
                        lbScore.setText(score+"");
                        newGame(6, 6);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}  
                if(e.getSource()==button){
                   
                   graphicsPanel.check();
                }
	}

	@Override
	public void run() {
		while (true) {
			try {
                            Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			progressTime.setValue((int) ((double) time / maxTime * 100));
		}
	}

	public JTextField getLbScore() {
		return lbScore;
	}

	public void setLbScore(JTextField lbScore) {
		this.lbScore = lbScore;
	}

	public JProgressBar getProgressTime() {
		return progressTime;
	}

	public void setProgressTime(JProgressBar progressTime) {
		this.progressTime = progressTime;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void showDialogNewGame() throws IOException  {
		int select = JOptionPane.showConfirmDialog(null, "Bạn muốn tiếp tục lên màn tiếp hay chơi lại?","Chơi tiếp?",JOptionPane.YES_NO_CANCEL_OPTION);
		if (select == JOptionPane.YES_OPTION) {
                    row=row+2;
                    col=col+2;
                    time=time+row*10-20;
                   
  
		    newGame(row,col);
		} 
                else if (select==JOptionPane.NO_OPTION){
			newGame(row,col);
                }
                else {
			System.exit(0);
		}
	}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

 

   
        
	

  
   
}