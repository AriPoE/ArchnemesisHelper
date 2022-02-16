package archnemesis;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JToggleButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class PopUpWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static PopUpWindow dialog;
	//private static ArrayList<String> inventory;

	/**
	 * Launch the application.
	 */
	public static void go(ArrayList<String> temp) {
		try {
			dialog = new PopUpWindow(temp);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PopUpWindow(ArrayList<String> temp) {
		setBounds(100, 100, 1200, 800);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		
		JLabel lblInventory = new JLabel("");
		
		lblInventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblInventory);

		ArrayList<String> inventory = Calc.go();
		lblInventory.setText(addInventory(inventory));
		addButtons(temp, inventory);
	}
	/**
	 * Inventory-Label at the left hand side of the program.
	 * @param inventory
	 * @return String with HTML code and all Inventory with Row-Seperation
	 */
	private String addInventory(ArrayList<String> inventory) {
		ArrayList<String> inventoryNumber = new ArrayList<String>();
		String inventoryString = "<html><body>Inventory<br>";
		for(String tmp : inventory) {
			if(!inventoryNumber.contains(tmp)) {
				inventoryNumber.add(tmp);
				inventoryNumber.add(""+Collections.frequency(inventory, tmp));
			}
		}
		for (int i = 0; i<inventoryNumber.size(); i = i + 2) {
			inventoryString = inventoryString + inventoryNumber.get(i) + ": ";
			inventoryString = inventoryString + inventoryNumber.get(i+1) + "<br>";
		}
		inventoryString = inventoryString + "</body></html>";
		return inventoryString;
	}
	/**
	 * Kind of the "main"-function to get the style of the program and the recipe hierarchy
	 * It will use the number in front of the String to determine where it's going to get located.
	 * Furthermore it will check if previous craft was in the inventory -> if yes, the materials will be checked aswell.
	 * example: necromancer is checked, so overcharged and bombardier will be checked too, aber not used out of the inventory.
	 * @param temp
	 * @param inventory
	 */
	private void addButtons(ArrayList<String> temp, ArrayList<String> inventory) {
		int last = 500;
		int leverNumber = 500;
		boolean lever = false;
		
		for(String s : temp) {
			int current = 0;
			if(!Character.isAlphabetic(s.charAt(0))) {
				current=1;
				s = s.substring(1);
				if(!Character.isAlphabetic(s.charAt(0))) {
					current=2;
					s = s.substring(1);
					if(!Character.isAlphabetic(s.charAt(0))) {
						current=3;
						s = s.substring(1);
						if(!Character.isAlphabetic(s.charAt(0))) {
							current=4;
							s = s.substring(1);
							if(!Character.isAlphabetic(s.charAt(0))) {
								current=5;
								s = s.substring(1);
							}
						}
					}
				}
			}
			addSpaces(current, last);
			JToggleButton tmpBt = new JToggleButton(s);
			for(String tmp : inventory) {
				if(leverNumber >= current) lever = false;
				if(lever && leverNumber < current) {
					tmpBt.setSelected(true);
					break;
				}
				if(tmp.equalsIgnoreCase(s)) {
					tmpBt.setSelected(true);
					inventory.remove(tmp);
					leverNumber = current;
					lever = true;
					break;
				}
			}
			last = current;
			contentPanel.add(tmpBt);
		}
	}
	/**
	 * GridLayout, the empty spaces are just not labeled JPanels
	 * I determine the position out of the current tier and last tier
	 * @param current current "tier" of the craft
	 * @param last last "tier" of the craft
	 * @return Wont need a return, but I needed a way to "stop" the function from adding more.
	 */
	private boolean addSpaces(int current, int last) {
		if(current - 1 == last) return true;
		if(last == 500) return true;
		if(current == 0) {
			for(int i = 0 + last; i<4; i++) {
				JPanel temp = new JPanel();
				contentPanel.add(temp);
			}
			return true;
		} 
		for(int i = 0 + Math.abs(current - last); i<4; i++) {
			JPanel temp = new JPanel();
			contentPanel.add(temp);
		}
		return true;
	}
}
