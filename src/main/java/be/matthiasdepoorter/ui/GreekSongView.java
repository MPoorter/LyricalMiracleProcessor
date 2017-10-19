package be.matthiasdepoorter.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;

public class GreekSongView extends JFrame {

	private GreekSongController controller;
	private JList<String> list;
	private DefaultListModel<String> defaultListModel;
	private JButton createPDF;

	public GreekSongView(GreekSongController controller) {
		super("Miracle Lyrical cheat sheet");
		this.controller = controller;
		initComponents();
		layoutComponents();
		initListeners();
		setVisible(true);
	}

	private void initComponents() {
		defaultListModel = new DefaultListModel<>();
		controller.getSongTitles().forEach(title -> defaultListModel.addElement(title));
		list = new JList<>(defaultListModel);
		createPDF = new JButton("Create PDF");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(300, 200);
		setLocation(100, 100);
	}

	private void layoutComponents() {
		add(list, BorderLayout.CENTER);
		add(createPDF, BorderLayout.SOUTH);
	}

	private void initListeners() {
		createPDF.addActionListener(e -> {
			JFileChooser saveFile = new JFileChooser();
			saveFile.setSelectedFile(new File(list.getSelectedValue() + ".pdf"));
			saveFile.showSaveDialog(null);
			if (!controller.createPdf(list.getSelectedIndex(), saveFile.getSelectedFile().getPath())) {
				createPopupWarning();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.shutDownExecutorService();
			}
		});
	}

	private void createPopupWarning() {
		JDialog errorWarning = new JDialog(this, "Error while creating PDF", true);
		errorWarning.setAlwaysOnTop(true);
		JLabel warningLabel = new JLabel("An error occurred while trying to create the PDF.");
		JButton okButton = new JButton("OK");
		errorWarning.add(warningLabel);
		errorWarning.add(okButton);
		okButton.addActionListener(e -> errorWarning.setVisible(false));
	}

	void setSongList() {
		defaultListModel.removeAllElements();
		controller.getSongTitles().forEach(title -> defaultListModel.addElement(title));
	}
}
