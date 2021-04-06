package GUI;

import Helpers.Bug;
import Helpers.Project;
import Helpers.User;
import main.MainWindow;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProjectsPanel extends JFrame {
	public static DefaultTableModel model;
	private JPanel FunctionPanel;
	public static JTable table;
	/**
	 * Create the panel.
	 */
	public ProjectsPanel() throws SQLException {
		buildCPane();
		buildPanels();
		buildLabels();
		buildSPane();
		buildButtons();
	}

	private void buildCPane(){
		setBounds(100,100,648,624);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
	}

	private void buildPanels(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 0, 153));
		panel.setBounds(0, 0, 649, 10);
		getContentPane().add(panel);

		FunctionPanel = new JPanel();
		FunctionPanel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(153, 0, 153)));
		FunctionPanel.setBackground(new Color(255, 255, 255));
		FunctionPanel.setBounds(451, 58, 176, 470);
		getContentPane().add(FunctionPanel);
		FunctionPanel.setLayout(null);
	}

	private void buildLabels(){

		JLabel FormLabel = new JLabel("Manage Projects");
		FormLabel.setForeground(new Color(153, 0, 153));
		FormLabel.setBackground(Color.WHITE);
		FormLabel.setFont(new Font("Dialog", Font.BOLD, 21));
		FormLabel.setBounds(26, 18, 262, 29);
		getContentPane().add(FormLabel);

		JLabel FunctionLabel = new JLabel("Project Management");
		FunctionLabel.setBounds(19, 6, 154, 22);
		FunctionLabel.setForeground(new Color(153, 0, 153));
		FunctionLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		FunctionLabel.setBackground(new Color(255, 255, 255));
		FunctionPanel.add(FunctionLabel);
	}

	private void buildSPane() throws SQLException {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 61, 413, 464);
		getContentPane().add(scrollPane);
		try {
			buildTable();
		}catch (SQLException sqle){
			System.out.println(sqle);
		}catch (NullPointerException npe){
			System.out.println("This user doesn\'t have any projects");
		}

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
	}

	private void buildButtons(){

		JButton EditButton = new JButton("Edit");
		EditButton.setForeground(Color.WHITE);
		EditButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		EditButton.setBackground(new Color(153, 0, 153));
		EditButton.setBounds(40, 46, 95, 29);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditProjectForm epf = null;
				epf = new EditProjectForm((String) table.getValueAt(table.getSelectedRow(),0));
				epf.setVisible(true);
			}
		});
		FunctionPanel.add(EditButton);

		JButton DeleteButton = new JButton("Delete");
		DeleteButton.setForeground(Color.WHITE);
		DeleteButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		DeleteButton.setBackground(new Color(153, 0, 153));
		DeleteButton.setBounds(40, 101, 95, 29);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int projectid = Project.getProjectID((String) table.getValueAt(table.getSelectedRow(),0));
					Bug.removeBugsFrom(projectid);
					Project.removeProject(projectid);
					Project.getProjects(User.getCurrentUser());
					model.removeRow(table.getSelectedRow());
					MainWindow.buildTabs(User.getCurrentUser());
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} catch (NullPointerException npe){
					System.out.println(User.getCurrentUser()+" currently has no projects.");
				}
				model.fireTableDataChanged();
				table.setModel(ProjectsPanel.model);
			}
		});
		FunctionPanel.add(DeleteButton);
	}

	public static void buildTable() throws SQLException {
		model = new DefaultTableModel(new String[]{"Project Name", "Created By"}, 0){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		for (String x : Project.projects.get(User.getCurrentUser())) {
			model.addRow(new String[] {Project.getProjectName(x), User.getCurrentUser()});
		}
	}



}
