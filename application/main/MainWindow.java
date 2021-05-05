package main;

import GUI.AddNewProjectForm;
import GUI.AdminPanel;
import GUI.ProjectsPanel;
import GUI.TabViewPanel;
import Helpers.Project;
import Helpers.ProjectUsers;
import Helpers.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class MainWindow extends JFrame {


	private JPanel contentPane;
	private JPanel MenuPanel = new JPanel();
	public static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private static boolean cleared;
	/**
	 * Create the frame.
	 */
	public MainWindow() throws SQLException {
		buildContentPane();
		buildPanels();
		buildLabels();
		buildTabbedPane();
		buildButton();
	}

	public static void main(String[] args) throws IOException {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginWindow loginWindow = null;
				try {
					loginWindow = new LoginWindow();
				} catch (IOException e) {
					e.printStackTrace();
				}

				assert loginWindow != null;
				loginWindow.setVisible(true);
			}
		});
	}

	private void buildContentPane(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1089, 750);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void buildPanels(){
		MenuPanel.setBounds(0, 0, 1070, 30);
		MenuPanel.setBackground(new Color(153, 0, 153));
		contentPane.add(MenuPanel);
		MenuPanel.setLayout(null);
	}

	private void buildLabels(){
		JLabel TitleLabel = new JLabel("TrackyBug");
		TitleLabel.setBounds(48, 1, 200, 25);
		TitleLabel.setForeground(new Color(255, 255, 255));
		TitleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		MenuPanel.add(TitleLabel);

		JLabel PictureLabel = new JLabel("");
		PictureLabel.setIcon(new ImageIcon("C:\\Users\\Young\\Documents\\PROJECTS\\RESUME\\TrackyBug\\images\\WHITElogo.jpg"));
		PictureLabel.setBounds(17, -3, 38, 39);
		MenuPanel.add(PictureLabel);
	}

	private void buildButton() throws SQLException {
		JButton AddTabButton = new JButton("Add New Project");
		AddTabButton.setBackground(new Color(153, 0, 153));
		AddTabButton.setForeground(new Color(255, 255, 255));
		AddTabButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		AddTabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewProjectForm np = new AddNewProjectForm();
				np.setVisible(true);
			}
		});
		AddTabButton.setBounds(859, 30, 191, 25);
		contentPane.add(AddTabButton);

		if (ProjectUsers.isUserAdmin(User.getCurrentUser())){
			JButton AdminButton = new JButton("Admin Menu");
			AdminButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminPanel ad = null;
					try {
						User.getAllUsers();
						ad = new AdminPanel();
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					assert ad != null;
					ad.setVisible(true);
				}
			});
			AdminButton.setForeground(Color.WHITE);
			AdminButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			AdminButton.setBackground(new Color(153, 0, 153));
			AdminButton.setBounds(682, 30, 142, 25);
			contentPane.add(AdminButton);

			JButton MPButton = new JButton("Manage Projects");
			MPButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectsPanel pp = null;
					try {
						Project.getProjects(User.getCurrentUser());
						pp = new ProjectsPanel();
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					assert pp != null;
					pp.setVisible(true);
				}
			});
			MPButton.setForeground(Color.WHITE);
			MPButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			MPButton.setBackground(new Color(153, 0, 153));
			MPButton.setBounds(490, 30, 155, 25);
			contentPane.add(MPButton);

			JButton LogoutButton = new JButton("Logout");
			LogoutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginWindow lw = null;
					try {
						lw = new LoginWindow();
					} catch (IOException throwables) {
						throwables.printStackTrace();
					}
					User.realuser = false;
					Project.hasprojects = false;
					assert lw != null;
					lw.setVisible(true);
					dispose();
				}
			});
			LogoutButton.setForeground(new Color(153, 0, 153));
			LogoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			LogoutButton.setBackground(Color.WHITE);
			LogoutButton.setBounds(920, 2, 119, 25);
			MenuPanel.add(LogoutButton);

		}else{
			JButton MPButton = new JButton("Manage Projects");
			MPButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectsPanel pp = null;
					try {
						Project.getProjects(User.getCurrentUser());
						pp = new ProjectsPanel();
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					assert pp != null;
					pp.setVisible(true);

				}
			});
			MPButton.setForeground(Color.WHITE);
			MPButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			MPButton.setBackground(new Color(153, 0, 153));
			MPButton.setBounds(682, 30, 155, 25);
			contentPane.add(MPButton);

			JButton LogoutButton = new JButton("Logout");
			LogoutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginWindow lw = null;
					try {
						lw = new LoginWindow();
					} catch (IOException throwables) {
						throwables.printStackTrace();
					}
					User.realuser = false;
					Project.hasprojects = false;
					assert lw != null;
					lw.setVisible(true);
					dispose();
				}
			});
			LogoutButton.setForeground(new Color(153, 0, 153));
			LogoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			LogoutButton.setBackground(Color.WHITE);
			LogoutButton.setBounds(920, 2, 119, 25);
			MenuPanel.add(LogoutButton);
		}



	}

	private void buildTabbedPane(){
		tabbedPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 153)));
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(0, 55, 1070, 656);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!cleared){
					Project.setCurrentProject(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				}
			}
		});
		contentPane.add(tabbedPane);
	}

	public static void buildTPPanel(String name) throws SQLException {
		TabViewPanel tvp = new TabViewPanel(Project.getProjectID(name));
		tabbedPane.addTab(name, null, tvp, null);
	}

	public static void buildTabs(String username) throws SQLException {
		cleared = true;
		clearTabs();
		cleared = false;
		Project.getProjects(username);
		for (String x : Project.projects.get(username)) {
			tabbedPane.add(Project.getProjectName(x), new TabViewPanel(Integer.parseInt(x)));
		}
	}

	private static void clearTabs(){
		tabbedPane.removeAll();
	}

}
