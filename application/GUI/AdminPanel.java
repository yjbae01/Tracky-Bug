package GUI;

import Helpers.User;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

public class AdminPanel extends JFrame {
	public static DefaultTableModel model;
	private JPanel FunctionPanel;
	public static JTable table;
	/**
	 * Create the panel.
	 */
	public AdminPanel() throws SQLException {
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

		JLabel FormLabel = new JLabel("Admin Panel");
		FormLabel.setForeground(new Color(153, 0, 153));
		FormLabel.setBackground(Color.WHITE);
		FormLabel.setFont(new Font("Dialog", Font.BOLD, 21));
		FormLabel.setBounds(26, 18, 262, 29);
		getContentPane().add(FormLabel);

		JLabel FunctionLabel = new JLabel("User Management");
		FunctionLabel.setBounds(24, 6, 154, 22);
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
			System.out.println(npe);
		}

		table = new JTable(model);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
	}

	private void buildButtons(){

		JButton CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Nirmala UI", Font.PLAIN, 16));
		CreateButton.setForeground(Color.WHITE);
		CreateButton.setBackground(new Color(153, 0, 153));
		CreateButton.setBounds(40, 46, 95, 29);
		CreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateNewUserForm cnuf = new CreateNewUserForm();
				cnuf.setVisible(true);
			}
		});
		FunctionPanel.add(CreateButton);

		JButton EditButton = new JButton("Edit");
		EditButton.setForeground(Color.WHITE);
		EditButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		EditButton.setBackground(new Color(153, 0, 153));
		EditButton.setBounds(40, 101, 95, 29);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditUserForm euf = null;
				try {
					euf = new EditUserForm((String) table.getValueAt(table.getSelectedRow(),0));
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				euf.setVisible(true);
			}
		});
		FunctionPanel.add(EditButton);

		JButton DeleteButton = new JButton("Delete");
		DeleteButton.setForeground(Color.WHITE);
		DeleteButton.setFont(new Font("Dialog", Font.PLAIN, 16));
		DeleteButton.setBackground(new Color(153, 0, 153));
		DeleteButton.setBounds(40, 161, 95, 29);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					User.removeUser((String) table.getValueAt(table.getSelectedRow(),0));
					User.getAllUsers();
					model.removeRow(table.getSelectedRow());
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				model.fireTableDataChanged();
				table.setModel(AdminPanel.model);
			}
		});
		FunctionPanel.add(DeleteButton);
	}

	public static void buildTable() throws SQLException {
		User.getAllUsers();
		model = new DefaultTableModel(new String[]{"Username", "Permissions"}, 0){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		for (String i : User.allusers.keySet()){
			model.addRow(new String[] {i, User.allusers.get(i)});
		}
	}



}
