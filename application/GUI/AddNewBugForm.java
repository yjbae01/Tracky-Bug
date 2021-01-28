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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddNewBugForm extends JFrame{
	private JTextField BugTitleTField;
	private JTextField DescriptionTField;

	public AddNewBugForm(String projectname) {
		buildContentPane();
		buildPanels();
		buildLabels();
		buildFieldComponents(projectname);
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

		JLabel TitleLabel = new JLabel("Submit a New Bug");
		TitleLabel.setForeground(new Color(153, 0, 153));
		TitleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		TitleLabel.setBounds(10, 21, 196, 25);
		getContentPane().add(TitleLabel);

		JLabel BugLabel = new JLabel("Bug Title:");
		BugLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		BugLabel.setBounds(37, 83, 90, 25);
		getContentPane().add(BugLabel);

		JLabel DateLabel = new JLabel("Due Date:");
		DateLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		DateLabel.setBounds(37, 278, 90, 25);
		getContentPane().add(DateLabel);


		JLabel SeverityLabel = new JLabel("Severity:");
		SeverityLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		SeverityLabel.setBounds(37, 144, 90, 25);
		getContentPane().add(SeverityLabel);

		JLabel DescriptionLabel = new JLabel("Description:");
		DescriptionLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		DescriptionLabel.setBounds(37, 212, 90, 25);
		getContentPane().add(DescriptionLabel);
	}

	private void buildFieldComponents(String projectname){
		BugTitleTField = new JTextField();
		BugTitleTField.setBounds(137, 87, 186, 20);
		getContentPane().add(BugTitleTField);
		BugTitleTField.setColumns(10);

		JComboBox SeverityCBox = new JComboBox();
		buildCBox(SeverityCBox);
		SeverityCBox.setBounds(137, 147, 105, 22);

		getContentPane().add(SeverityCBox);

		JDateChooser DueDate = new JDateChooser();
		DueDate.setBounds(137, 278, 105, 20);
		getContentPane().add(DueDate);

		DescriptionTField = new JTextField();
		DescriptionTField.setBounds(137, 216, 176, 20);
		getContentPane().add(DescriptionTField);
		DescriptionTField.setColumns(10);

		JButton CreateButton = new JButton("Submit");
		CreateButton.setBackground(new Color(153, 0, 153));
		CreateButton.setForeground(Color.WHITE);
		CreateButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		CreateButton.setBounds(302, 373, 99, 31);
		CreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.util.Date duu = DueDate.getDate();
				Date duedate = new Date(duu.getTime());
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				try {
					int id = Project.getProjectID(projectname);
					Bug.addBug(id,BugTitleTField.getText(),DescriptionTField.getText(),"'"+dtf.format(now)+"'","'"+duedate+"'",
							(String) SeverityCBox.getSelectedItem(),"Open", User.getCurrentUser());
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
		String[] val = new String[] {"","Minor", "Major", "Critical"};
		for (String i : val){
			CB.addItem(i);
		}

	}

}
