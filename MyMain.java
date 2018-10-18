package thuanoop;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JOptionPane;
public class MyMain {
	MyFrame frame;
	public MyMain() throws IOException {
		frame = new MyFrame();
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();
	}

	public static void main(String[] args) throws IOException {
            MyMain myMain = new MyMain();
	}
	class MyTimeCount extends Thread {
                @Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				frame.setTime(frame.getTime() - 1);
                                frame.progressTime.setStringPainted(true);
                                String s = String.valueOf(frame.getTime());
                                frame.progressTime.setForeground(Color.BLUE);
                                frame.progressTime.setString(s);
				if (frame.getTime() == 0) {
				 int click=JOptionPane.showConfirmDialog(null,"Bạn có muốn chơi lại?","Full time",JOptionPane.YES_NO_OPTION);
                                 if(click==JOptionPane.YES_OPTION){
                                     frame.time=100;
                                     frame.score=0;
                                    frame.newGame(6, 6);}
                                 else System.exit(0);
				}
			}
		}
	}
}
