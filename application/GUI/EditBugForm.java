package GUI;

import Helpers.Bug;
import Helpers.Project;
import Helpers.User;
import com.toedter.calendar.JDateChooser;
import main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditBugForm extends JFrame{
	private JTextField BugTitleTField;
	private JTextField DescriptionTField;

	public EditBugForm(String bugid, String name, String description) {
		buildContentPane();
		buildPanels();
		buildLabels();
		buildFieldComponents(bugid, name, description);
	}

	private void buildContentPane(){
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 458, 500);
	}

	private void buildPanels(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 0, 153));
		panel.setBounds(0, 0, 441, 10);
		getContentPane().add(panel);
	}

	private void buildLabels(){

		JLabel TitleLabel = new JLabel("Edit Bug");
		TitleLabel.setForeground(new Color(153, 0, 153));
		TitleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		TitleLabel.setBounds(10, 21, 196, 25);
		getContentPane().add(TitleLabel);

		JLabel BugLabel = new JLabel("Bug Title:");
		BugLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		BugLabel.setBounds(37, 83, 90, 25);
		getContentPane().add(BugLabel);

		JLabel StatusLabel = new JLabel("Status:");
		StatusLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		StatusLabel.setBounds(37, 144, 90, 25);
		getContentPane().add(StatusLabel);

		JLabel DescriptionLabel = new JLabel("Description:");
		DescriptionLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		DescriptionLabel.setBounds(37, 212, 90, 25);
		getContentPane().add(DescriptionLabel);
	}

	private void buildFieldComponents(String bugid, String name, String description){
		BugTitleTField = new JTextField();
		BugTitleTField.setBounds(137, 87, 186, 20);
		BugTitleTField.setText(name);
		getContentPane().add(BugTitleTField);
		BugTitleTField.setColumns(10);

		JComboBox StatusCBox = new JComboBox();
		buildCBox(StatusCBox);
		StatusCBox.setBounds(137, 147, 105, 22);

		getContentPane().add(StatusCBox);

		DescriptionTField = new JTextField();
		DescriptionTField.setBounds(137, 216, 176, 20);
		DescriptionTField.setText(description);
		getContentPane().add(DescriptionTField);
		DescriptionTField.setColumns(10);

		JButton CreateButton = new JButton("Submit");
		CreateButton.setBackground(new Color(153, 0, 153));
		CreateButton.setForeground(Color.WHITE);
		CreateButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		CreateButton.setBounds(302, 373, 99, 31);
		CreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Bug.updateBug(bugid,BugTitleTField.getText(),DescriptionTField.getText(),(String) StatusCBox.getSelectedItem());
					MainWindow.buildTabs(User.getCurrentUser());
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				dispose();
			}
		});
		getContentPane().add(CreateButton);
	}

	private void buildCBox(JComboBox CB){
		String[] val = new String[] {"Open", "In Progress..", "Solved"};
		for (String i : val){
			CB.addItem(i);
		}

	}

}
