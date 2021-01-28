package main;

import Connections.ServerConnection;
import GUI.TabViewPanel;
import Helpers.Bug;
import Helpers.Project;
import Helpers.User;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
	private JTextField UsernameTextField;
	private JPasswordField PasswordField;
	private JLabel PictureLabel;
	private JPanel UPPanel = new JPanel();

	
	public LoginWindow() throws IOException {
		buildContentPane();
		buildPanel();
		buildLabels();
		buildFields();
		buildButton();
	}

	private void buildContentPane(){
		setBounds(100, 100, 449, 345);

		setTitle("TrackyBug");
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
	}

	private void buildLabels (){
		JLabel UsernameLabel = new JLabel("Username:");
		UsernameLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 16));
		UsernameLabel.setBounds(0, 39, 82, 31);
		UPPanel.add(UsernameLabel);

		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 16));
		PasswordLabel.setBounds(0, 100, 82, 31);
		UPPanel.add(PasswordLabel);

		JLabel TitleLabel = new JLabel("Login");
		TitleLabel.setForeground(new Color(153, 0, 153));
		TitleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 33));
		TitleLabel.setBounds(24, 11, 148, 54);
		getContentPane().add(TitleLabel);

		PictureLabel = new JLabel("");
		PictureLabel.setIcon(new ImageIcon("C:\\Users\\Young\\Documents\\PROJECTS\\RESUME\\TrackyBug\\images\\logo_f.jpg"));
		PictureLabel.setBounds(351, 11, 72, 54);
		getContentPane().add(PictureLabel);
	}

	private void buildButton(){
		JButton LoginButton = new JButton("Login");
		LoginButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		LoginButton.setBackground(new Color(153, 0, 153));
		LoginButton.setForeground(new Color(255, 255, 255));
		LoginButton.setBounds(270, 157, 95, 25);
		LoginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String usr = UsernameTextField.getText();
					String pwd = PasswordField.getText();
					User.userSearcher(usr,pwd);
					if (User.realuser){
						User.setCurrentUser(usr);
						Project.getProjects(usr);
						if (Project.hasprojects){
							Project.setCurrentProject(Project.projects.get(User.getCurrentUser()).get(0));
							Bug.retrieveBugsFromProjectID(Project.getProjectID(Project.getCurrentProject()));
							MainWindow.buildTabs(usr);
							ServerConnection.loginSuccess(usr);
							buildMainWindow();
						}else{
							ServerConnection.loginSuccess(usr);
							buildMainWindow();
						}
					}else{
						JOptionPane.showMessageDialog(new JFrame(), "Wrong Username or Password!");
					}
				} catch (SQLException throwables) {
					System.out.println("Database Connection ERROR!");
				} catch (NullPointerException npe){
					System.out.println("ERROR: NULL! @ LoginWindow");
				}
			}
		});
		UPPanel.add(LoginButton);
	}

	private void buildFields(){
		UsernameTextField = new JTextField();
		UsernameTextField.setBounds(85, 41, 170, 28);
		UPPanel.add(UsernameTextField);
		UsernameTextField.setColumns(10);

		PasswordField = new JPasswordField();
		PasswordField.setColumns(10);
		PasswordField.setBounds(86, 102, 170, 28);
		UPPanel.add(PasswordField);
	}

	private void buildPanel(){
		UPPanel.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(153, 0, 153)));
		UPPanel.setBackground(new Color(255, 255, 255));
		UPPanel.setForeground(Color.WHITE);
		UPPanel.setBounds(24, 76, 375, 193);
		getContentPane().add(UPPanel);
		UPPanel.setLayout(null);
	}

	private void buildMainWindow() throws SQLException {
		JOptionPane.showMessageDialog(new JFrame(), "You have successfully logged in.");
		MainWindow mw = new MainWindow();
		mw.setVisible(true);
		dispose();
	}
}
