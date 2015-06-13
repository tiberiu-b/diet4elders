package d4elders.mvc.view;

import javax.swing.JApplet;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class MainView extends JApplet {

	private static final long serialVersionUID = 1L;

	public MainView() {

		int algorithmToRun = 0;

		if (algorithmToRun == 0) {
			CuckooAdminPanel cuckooPanel = new CuckooAdminPanel();
			this.add(cuckooPanel);
			cuckooPanel.setVisible(true);
			this.setSize(1100, 640);
		}
		if (algorithmToRun == 1) {
			HBMOAdminPanel hbmoPanel = new HBMOAdminPanel();
			this.add(hbmoPanel);
			hbmoPanel.setVisible(true);
			this.setSize(1100, 640);
		}
	}

	public static void main(String[] args) {
		new MainView();
	}
}