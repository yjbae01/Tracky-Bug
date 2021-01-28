package GUI;

import Connections.InitiateDB;
import Helpers.Bug;
import Helpers.Project;
import Helpers.User;
import main.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TabViewPanel extends JPanel {
	private JTextField SearchTextField;
	public static JScrollPane scrollPane;
	private static int selectedrow;
	/**
	 * Create the panel.
	 */
	public TabViewPanel(String projectname) throws SQLException {
		buildCPane();
		buildLabels();
		buildSPane(projectname);
		buidComponents(Project.getProjectID(projectname), projectname);
	}


	private void buildCPane(){
		setBackground(new Color(153, 0, 153));
		setLayout(null);
	}

	private void buildLabels(){

		JLabel SearchLabel = new JLabel("Search");
		SearchLabel.setForeground(new Color(255, 255, 255));
		SearchLabel.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		SearchLabel.setBounds(972, 19, 57, 16);
		add(SearchLabel);

		JLabel FunctionsLabel = new JLabel("Functions");
		FunctionsLabel.setForeground(new Color(255, 255, 255));
		FunctionsLabel.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		FunctionsLabel.setBounds(963, 451, 72, 14);
		add(FunctionsLabel);

	}

	private void buildSPane(String projectname){
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 909, 633);
		add(scrollPane);
			try {
				buildTable(projectname);
			}catch (SQLException sqle){
				System.out.println(sqle);
			}catch (NullPointerException npe){
				System.out.println("");
			}
	}

	private void buidComponents(int projectid, String projectname){
		SearchTextField = new JTextField();
		SearchTextField.setBounds(951, 42, 86, 20);
		add(SearchTextField);
		SearchTextField.setColumns(10);


		Button SearchButton = new Button("Search");
		SearchButton.setBounds(961, 68, 70, 22);
		add(SearchButton);

		JButton EditButton = new JButton("Edit");
		EditButton.setBounds(954, 520, 83, 23);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditBugForm ebf = null;
				try {
					ebf = new EditBugForm((String) createTable(getModel(projectid)).getValueAt(selectedrow,0),
							(String) createTable(getModel(projectid)).getValueAt(selectedrow,1),(String) createTable(getModel(projectid)).getValueAt(selectedrow,2));
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				ebf.setVisible(true);
			}
		});
		add(EditButton);


		JButton DeleteButton = new JButton("Delete");
		DeleteButton.setBounds(954, 554, 83, 23);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Bug.removeBug((String) createTable(getModel(projectid)).getValueAt(selectedrow,0),projectid);
					MainWindow.buildTabs(User.getCurrentUser());
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		add(DeleteButton);

		JButton AddButton = new JButton("Add Bug");
		AddButton.setBounds(954, 486, 83, 23);
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewBugForm bugform = new AddNewBugForm(Project.getCurrentProject());
				System.out.println(Project.getCurrentProject());
				bugform.setVisible(true);
			}
		});
		add(AddButton);
	}


	public static void buildTable(String projectname) throws SQLException {
		Bug.projectBugs.clear();
		int projectid = Project.getProjectID(projectname);
		scrollPane.setViewportView(createTable(getModel(projectid)));
	}

	public static JTable createTable(DefaultTableModel model){
		JTable table = new JTable(model);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedrow = table.getSelectedRow();
			}
		});

		return table;
	}

	public static DefaultTableModel getModel(int projectid) throws SQLException {
		DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Bug", "Description", "Created", "Reporter", "Severity", "Status", "Due Date"}, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};;
		Bug.retrieveBugsFromProjectID(projectid);
		List<String> ar = Bug.projectBugs.get(projectid);
		int numberOfBugs = ar.size() / 8;
		int index = 0;
		int sizeOfList = 8;
		if (numberOfBugs == 0 || ar == null){

		}else {
			for (int i = 0; i < numberOfBugs; i++) {
				model.addRow(new String[]{Bug.projectBugs.get(projectid).get(index), Bug.projectBugs.get(projectid).get(index + 1), Bug.projectBugs.get(projectid).get(index + 2),
						Bug.projectBugs.get(projectid).get(index + 3), Bug.projectBugs.get(projectid).get(index + 4), Bug.projectBugs.get(projectid).get(index + 5),
						Bug.projectBugs.get(projectid).get(index + 6), Bug.projectBugs.get(projectid).get(index + 7)});
				index += sizeOfList;
			}
			model.fireTableDataChanged();
		}
		return model;
	}

}