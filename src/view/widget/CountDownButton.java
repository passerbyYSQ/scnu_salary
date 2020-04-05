package view.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author passerbyYSQ
 * @create 2020年3月31日 下午6:58:39
 */
public class CountDownButton extends JButton 
		implements Runnable, ActionListener {

	private int lastSeconds = 60;
	private Action action;

	public CountDownButton(int lastSeconds, String text, Action action) {
		super(text);
		this.lastSeconds = lastSeconds;
		this.action = action;
		addActionListener(this);
	}

	public interface Action {
		void beforeCountDown();
	}
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		action.beforeCountDown();
		new Thread(this).start();
	}

	@Override
	public void run() {
		setEnabled(false);
		setText(lastSeconds + "s");

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			lastSeconds--;
			setText(lastSeconds + "s");

			if (lastSeconds <= 0) {
				setEnabled(true);
				setText("发送验证码");
				break;
			}
		}
	}

}
