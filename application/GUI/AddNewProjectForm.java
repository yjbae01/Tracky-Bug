package GUI;

import Helpers.Changelog;
import Helpers.Project;
import Helpers.ProjectUsers;
import Helpers.User;
import com.sun.tools.javac.Main;
import main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AddNewProjectForm extends JFrame{
	public static String pname;
	private JTextField ProjectNameTextField;

	public AddNewProjectForm() {
		buildContentPane();
		buildPanel();
		buildLabels();
		buildField();
		buildButton();
	}

	private void buildContentPane(){
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 458, 200);
	}

	private void buildLabels(){
		JLabel FormLabel = new JLabel("Add a New Project");
		FormLabel.setForeground(new Color(153, 0, 153));
		FormLabel.setBackground(Color.WHITE);
		FormLabel.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		FormLabel.setBounds(10, 11, 307, 34);
		getContentPane().add(FormLabel);

		JLabel ProjectNameLabel = new JLabel("Project Name:");
		ProjectNameLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		ProjectNameLabel.setBounds(30, 56, 111, 21);
		getContentPane().add(ProjectNameLabel);
	}

	private void buildPanel(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 0, 153));
		panel.setBounds(0, 0, 455, 10);
		getContentPane().add(panel);
	}

	private void buildField(){
		ProjectNameTextField = new JTextField();
		ProjectNameTextField.setBounds(142, 58, 134, 20);
		getContentPane().add(ProjectNameTextField);
		ProjectNameTextField.setColumns(10);
	}

	private void buildButton(){
		JButton CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		CreateButton.setForeground(Color.WHITE);
		CreateButton.setBackground(new Color(153, 0, 153));
		CreateButton.setBounds(317, 107, 89, 23);
		CreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pname = ProjectNameTextField.getText();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					Project.addProject(pname,dtf.format(now));
					MainWindow.buildTPPanel(projectName());
					String description = Changelog.generateLogDescription("add",pname,"Project");
					Changelog.addProjectLog(description,Project.getProjectID(pname),dtf.format(now),User.getCurrentUser());
					ProjectUsers.updateUserPermissions(User.getCurrentUser(), Project.getProjectID(Project.getCurrentProject()),"Admin");

				} catch (SQLException sqle) {
					System.out.println(sqle);
					JOptionPane.showMessageDialog(new JFrame(), "Failed to create new project!");
				}
				dispose();
			}
		});
		getContentPane().add(CreateButton);
	}

	public static String projectName(){
		return pname;
	}
}
