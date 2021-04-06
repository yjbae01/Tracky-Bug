package GUI;

import Helpers.Project;
import Helpers.ProjectUsers;
import Helpers.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddNewUserForm extends JFrame {

	private JPanel contentPane;
	private JTextField UsernameTField;
	private JPasswordField passwordField;
	private JRadioButton AdminRButton;
	private JRadioButton UserRButton;
	private JComboBox ProjectCBox;
	/**
	 * Create the frame.
	 */
	public AddNewUserForm() {
		buildCPane();
		buildPanels();
		buildLabels();
		buildComponents();
	}

	private void buildCPane(){
		setBackground(SystemColor.window);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 532);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void buildPanels(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 0, 153));
		panel.setBounds(0, 0, 394, 10);
		getContentPane().add(panel);
	}

	private void buildLabels(){
		JLabel TitleLabel = new JLabel("Create a New User");
		TitleLabel.setForeground(new Color(153, 0, 153));
		TitleLabel.setBackground(new Color(255, 255, 255));
		TitleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		TitleLabel.setBounds(20, 22, 249, 25);
		contentPane.add(TitleLabel);

		JLabel UsernameLabel = new JLabel("Username:");
		UsernameLabel.setForeground(new Color(153, 0, 153));
		UsernameLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		UsernameLabel.setBounds(20, 102, 103, 16);
		contentPane.add(UsernameLabel);

		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setForeground(new Color(153, 0, 153));
		PasswordLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		PasswordLabel.setBounds(20, 171, 103, 16);
		contentPane.add(PasswordLabel);

		JLabel ProjectLabel = new JLabel("Project:");
		ProjectLabel.setForeground(new Color(153, 0, 153));
		ProjectLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		ProjectLabel.setBounds(20, 240, 103, 16);
		contentPane.add(ProjectLabel);

		JLabel AccessLabel = new JLabel("Access Level:");
		AccessLabel.setForeground(new Color(153, 0, 153));
		AccessLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		AccessLabel.setBounds(20, 304, 175, 16);
		contentPane.add(AccessLabel);
	}

	private void buildComponents(){

		UsernameTField = new JTextField();
		UsernameTField.setBounds(135, 98, 191, 26);
		contentPane.add(UsernameTField);
		UsernameTField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(135, 166, 191, 26);
		contentPane.add(passwordField);

		ProjectCBox = new JComboBox();
		buildCBox(ProjectCBox);
		ProjectCBox.setBounds(137, 240, 105, 22);
		getContentPane().add(ProjectCBox);

		UserRButton = new JRadioButton("User");
		UserRButton.setBounds(20, 345, 103, 23);
		UserRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (UserRButton.isSelected()){
					AdminRButton.setSelected(false);
				}
			}
		});

		contentPane.add(UserRButton);

		AdminRButton = new JRadioButton("Admin");
		AdminRButton.setBounds(135, 345, 141, 23);
		AdminRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (AdminRButton.isSelected()){
					UserRButton.setSelected(false);
				}
			}
		});
		contentPane.add(AdminRButton);

		JButton CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		CreateButton.setForeground(Color.WHITE);
		CreateButton.setBackground(new Color(153, 0, 153));
		CreateButton.setBounds(237, 401, 89, 23);
		CreateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String perm = null;
				if (AdminRButton.isSelected() || UserRButton.isSelected()){
					if (AdminRButton.isSelected()){
						perm = AdminRButton.getLabel();
					}else {
						perm = UserRButton.getLabel();
					}
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose the user's permission level.");
				}
				try {
					ProjectUsers.createUser(UsernameTField.getText(), passwordField.getText(), (String) ProjectCBox.getSelectedItem(),perm);
					User.getAllUsers();
					AdminPanel.buildTable();
					JOptionPane.showMessageDialog(new JFrame(), "User has been successfully created!");
				} catch (SQLException throwables) {
					JOptionPane.showMessageDialog(new JFrame(), "Error creating user, please try again.");
					System.out.println(throwables);
				}
				AdminPanel.table.setModel(AdminPanel.model);
				AdminPanel.model.fireTableDataChanged();
				dispose();
			}
		});
		contentPane.add(CreateButton);
	}

	private void buildCBox(JComboBox CB){
			CB.addItem("");
		for (String i : Project.projects.get(User.getCurrentUser())){
			CB.addItem(i);
		}

	}
}
