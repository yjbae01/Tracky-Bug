package GUI;

import Helpers.Changelog;
import Helpers.Project;
import Helpers.User;
import main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EditProjectForm extends JFrame{
	public static String pname;
	private JTextField ProjectNameTextField;

	public EditProjectForm(String projectname) {
		buildContentPane();
		buildPanel();
		buildLabels();
		buildField();
		buildButton(projectname);
	}

	private void buildContentPane(){
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 458, 200);
	}

	private void buildLabels(){
		JLabel FormLabel = new JLabel("Edit Project Name");
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

	private void buildButton(String projectname){
		JButton changeButton = new JButton("Change");
		changeButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		changeButton.setForeground(Color.WHITE);
		changeButton.setBackground(new Color(153, 0, 153));
		changeButton.setBounds(317, 107, 89, 23);
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pname = ProjectNameTextField.getText();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				try {
					Project.updateProject(pname,Project.getProjectID(projectname));
					String description = Changelog.generateLogDescription("edit",pname,"Project");
					Changelog.addProjectLog(description,Project.getProjectID(pname),dtf.format(now),User.getCurrentUser());
					Project.getProjects(User.getCurrentUser());
					ProjectsPanel.buildTable();
					MainWindow.buildTabs(User.getCurrentUser());
				} catch (SQLException sqle){
					System.out.println(sqle);
					JOptionPane.showMessageDialog(new JFrame(), "Failed to edit project name!");
				}
				JOptionPane.showMessageDialog(new JFrame(), "Project's name has been updated!");
				ProjectsPanel.table.setModel(ProjectsPanel.model);
				ProjectsPanel.model.fireTableDataChanged();
				dispose();
			}
		});
		getContentPane().add(changeButton);
	}

	public static String projectName(){
		return pname;
	}
}
