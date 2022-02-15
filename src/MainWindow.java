package archnemesis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import org.opencv.core.Core;

public class MainWindow {

	private JFrame frmArchnemesisHelper;
	private static String[][] matList;
	private static String[] tierOne;
	private static ArrayList<String> inventoryList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmArchnemesisHelper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. I didn't understand the Warnings and it worked, so I suppressed them.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		fill();
		String configFiles[][] = getConfig();
		inventoryList = new ArrayList<String>();
		/*All mods for the Dropdown*/
		String[] temp = {"Arcane Buffer", "Berserker", "Bloodletter", "Bombardier", "Bonebreaker", "Chaosweaver", "Consecrator", "Deadeye", "Dynamo", "Echoist", "Flameweaver", "Frenzied", "Frostweaver", "Gargantuan", "Hasted", "Incendiary", "Juggernaut", "Malediction", "Opulent", "Overcharged", "Permafrost", "Sentinel", "Soul Conduit", "Steel-Infused", "Stormweaver", "Toxic", "Vampiric", 
					"Assassin", "Corpse Detonator", "Corrupter", "Drought Bringer", "Entangler", "Executioner", "Flame Strider", "Frost Strider", "Heralding Minions", "Hexer", "Ice Prison", "Magma Barrier", "Mana Siphoner", "Mirror Image", "Necromancer", "Rejuvenating", "Storm Strider", "Abberath-Touched", "Arakaali-Touched", "Brine King-Touched", "Crystal-Skinned", "Effigy", "Empowered Elements",
					"Empowering Minions", "Evocationist", "Invulnerable", "Lunaris-Touched", "Shakari-Touched", "Solaris-Touched", "Soul Eater", "Temporal Bubble", "Treant Horde", "Trickster", "Tukohama-Touched", "Innocence-Touched", "Kitava-Touched"
		};
		ArrayList<String> SortDem = new ArrayList<String>();
		for(String tmp : temp) {
			SortDem.add(tmp);
		}
		Collections.sort(SortDem);
		int counter = 0;
		for(String tmp : SortDem) {
			inventoryList.add(tmp);
			temp[counter] = tmp;
			counter++;
		}
		
		frmArchnemesisHelper = new JFrame();
		frmArchnemesisHelper.setTitle("Archnemesis Helper");
		frmArchnemesisHelper.setBounds(100, 100, 625, 375);
		frmArchnemesisHelper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmArchnemesisHelper.getContentPane().setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frmArchnemesisHelper.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0,1 + configFiles.length, 0, 0));
	    
		JComboBox cb1 = new JComboBox();
		cb1.setEditable(true);
		frmArchnemesisHelper.getContentPane().add(cb1);
		
		JComboBox cb2 = new JComboBox();
		cb2.setEditable(true);
		frmArchnemesisHelper.getContentPane().add(cb2);
		
		JComboBox cb3 = new JComboBox();
		cb3.setEditable(true);
		frmArchnemesisHelper.getContentPane().add(cb3);
		
		JComboBox cb4 = new JComboBox();
		cb4.setEditable(true);
		frmArchnemesisHelper.getContentPane().add(cb4);
		
		cb1.addItem("");
		cb2.addItem("");
		cb3.addItem("");
		cb4.addItem("");
		
		for(int i = 0; i<temp.length; i++) {
			cb1.addItem(temp[i]);
			cb2.addItem(temp[i]);
			cb3.addItem(temp[i]);
			cb4.addItem(temp[i]);
		}
		/*
		 * Config-Files can be any number of *.txt - files in config folder. For-Loop to add all the different buttons.
		 */
		JButton export = new JButton("Export");
		panel.add(export);
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> inventory = Calc.go();
				ArrayList<String> result = new ArrayList<String>();
				for(String s : inventoryList) {
					result.add(s);
					result.add(""+Collections.frequency(inventory, s));
				}
				try {
					FileWriter mW = new FileWriter("export.csv");
					for(int i=0; i<result.size(); i = i + 2) {
						mW.write(result.get(i)+";"+result.get(i+1)+"\n");
					} 
					mW.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		for(int i = 0; i<configFiles.length; i++) {
			JButton configButton = new JButton(configFiles[i][0]);
			panel.add(configButton);
			final int u = i;
			configButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cb1.setSelectedItem(configFiles[u][1]);
					cb2.setSelectedItem(configFiles[u][2]);
					cb3.setSelectedItem(configFiles[u][3]);
					cb4.setSelectedItem(configFiles[u][4]);
					ArrayList<String> temp = getMat(cb1.getSelectedItem().toString());
					ArrayList<String> finalList = new ArrayList<String>();
					for(String s : temp) {
						finalList.add(s);
					}
					temp = getMat(cb2.getSelectedItem().toString());
					for(String s : temp) {
						finalList.add(s);
					}
					temp = getMat(cb3.getSelectedItem().toString());
					for(String s : temp) {
						finalList.add(s);
					}
					temp = getMat(cb4.getSelectedItem().toString());
					for(String s : temp) {
						finalList.add(s);
					}
					PopUpWindow.go(finalList);
				}
			});
		}
		/*
		 * Normal Go
		 */
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> temp = getMat(cb1.getSelectedItem().toString());
				ArrayList<String> finalList = new ArrayList<String>();
				for(String s : temp) {
					finalList.add(s);
				}
				temp = getMat(cb2.getSelectedItem().toString());
				for(String s : temp) {
					finalList.add(s);
				}
				temp = getMat(cb3.getSelectedItem().toString());
				for(String s : temp) {
					finalList.add(s);
				}
				temp = getMat(cb4.getSelectedItem().toString());
				for(String s : temp) {
					finalList.add(s);
				}
				PopUpWindow.go(finalList);
			}
		});
		frmArchnemesisHelper.getContentPane().add(btnGo);

	}
	/**
	 * Recursive function to get all the materials needed (with tiers, so I can differentiate between Recipes)
	 * 
	 * @param combinedItem Item I want to craft
	 * @return complete List of all the materialLists
	 */
	private ArrayList<String> getMat(String combinedItem) {
		ArrayList<String> materialList = new ArrayList<String>();
		
		if(contains(tierOne, combinedItem)) {
			materialList.add(combinedItem);
		} else {
			for(int i = 0; i<matList.length; i++) {
				if(matList[i][0].equalsIgnoreCase(combinedItem)) {
					int counter = 0;
					materialList.add(matList[i][0]);
					for(int u = 1; u<matList[i].length; u++) {
						if(matList[i][u].length() > 0) {
							counter++;
							ArrayList<String> temp = getMat(matList[i][u]);
							for(String s : temp){
								materialList.add(counter+s);
							}
						}
					}
				}
			}
		}
		return materialList;
	}
	/**
	 * A 2D-Array with all the Recipes. You could do it in JSON or XML, but I was too lazy, this is the easiest way I guess. And its static anyway.
	 */
	private void fill() {
		matList = new String[36][5];
		String[] temp = {"Arcane Buffer", "Berserker", "Bloodletter", "Bombardier", "Bonebreaker", "Chaosweaver", "Consecrator", "Deadeye", "Dynamo", "Echoist", "Flameweaver", "Frenzied", "Frostweaver", "Gargantuan", "Hasted", "Incendiary", "Juggernaut", "Malediction", "Opulent", "Overcharged", "Permafrost", "Sentinel", "Soul Conduit", "Steel-Infused", "Stormweaver", "Toxic", "Vampiric"};
		tierOne = temp;
		for(int i=0; i<matList.length; i++) {
			matList[i][1] = "";
			matList[i][2] = "";
			matList[i][3] = "";
			matList[i][4] = "";
		}
		matList[0][0] = "Assassin";
		matList[0][1] = "Deadeye";
		matList[0][2] = "Vampiric";
		
		matList[1][0] = "Corpse Detonator";
		matList[1][1] = "Necromancer";
		matList[1][2] = "Incendiary";
		
		matList[2][0] = "Corrupter";
		matList[2][1] = "Bloodletter";
		matList[2][2] = "Chaosweaver";
		
		matList[3][0] = "Drought Bringer";
		matList[3][1] = "Malediction";
		matList[3][2] = "Deadeye";
		
		matList[4][0] = "Entangler";
		matList[4][1] = "Toxic";
		matList[4][2] = "Bloodletter";
		
		matList[5][0] = "Executioner";
		matList[5][1] = "Frenzied";
		matList[5][2] = "Berserker";
		
		matList[6][0] = "Flame Strider";
		matList[6][1] = "Flameweaver";
		matList[6][2] = "Hasted";
		
		matList[7][0] = "Frost Strider";
		matList[7][1] = "Frostweaver";
		matList[7][2] = "Hasted";
		
		matList[8][0] = "Heralding Minions";
		matList[8][1] = "Dynamo";
		matList[8][2] = "Arcane Buffer";
		
		matList[9][0] = "Hexer";
		matList[9][1] = "Chaosweaver";
		matList[9][2] = "Echoist";
		
		matList[10][0] = "Ice Prison";
		matList[10][1] = "Permafrost";
		matList[10][2] = "Sentinel";
		
		matList[11][0] = "Magma Barrier";
		matList[11][1] = "Incendiary";
		matList[11][2] = "Bonebreaker";
		
		matList[12][0] = "Mana Siphoner";
		matList[12][1] = "Consecrator";
		matList[12][2] = "Dynamo";
		
		matList[13][0] = "Mirror Image";
		matList[13][1] = "Echoist";
		matList[13][2] = "Soul Conduit";
		
		matList[14][0] = "Necromancer";
		matList[14][1] = "Bombardier";
		matList[14][2] = "Overcharged";
		
		matList[15][0] = "Rejuvenating";
		matList[15][1] = "Gargantuan";
		matList[15][2] = "Vampiric";
		
		matList[16][0] = "Storm Strider";
		matList[16][1] = "Stormweaver";
		matList[16][2] = "Hasted";
		
		matList[17][0] = "Abberath-Touched";
		matList[17][1] = "Flame Strider";
		matList[17][2] = "Frenzied";
		matList[17][3] = "Rejuvenating";
		
		matList[18][0] = "Arakaali-Touched";
		matList[18][1] = "Corpse Detonator";
		matList[18][2] = "Entangler";
		matList[18][3] = "Assassin";
		
		matList[19][0] = "Brine King-Touched";
		matList[19][1] = "Ice Prison";
		matList[19][2] = "Storm Strider";
		matList[19][3] = "Heralding Minions";
		
		matList[20][0] = "Crystal-Skinned";
		matList[20][1] = "Permafrost";
		matList[20][2] = "Rejuvenating";
		matList[20][3] = "Berserker";
		
		matList[21][0] = "Effigy";
		matList[21][1] = "Hexer";
		matList[21][2] = "Malediction";
		matList[21][3] = "Corrupter";
		
		matList[22][0] = "Empowered Elements";
		matList[22][1] = "Evocationist";
		matList[22][2] = "Steel-Infused";
		matList[22][3] = "Chaosweaver";
		
		matList[23][0] = "Empowering Minions";
		matList[23][1] = "Necromancer";
		matList[23][2] = "Executioner";
		matList[23][3] = "Gargantuan";
		
		matList[24][0] = "Evocationist";
		matList[24][1] = "Flameweaver";
		matList[24][2] = "Frostweaver";
		matList[24][3] = "Stormweaver";
		
		matList[25][0] = "Invulnerable";
		matList[25][1] = "Sentinel";
		matList[25][2] = "Juggernaut";
		matList[25][3] = "Consecrator";
		
		matList[26][0] = "Lunaris-Touched";
		matList[26][1] = "Invulnerable";
		matList[26][2] = "Frost Strider";
		matList[26][3] = "Empowering Minions";
		
		matList[27][0] = "Shakari-Touched";
		matList[27][1] = "Entangler";
		matList[27][2] = "Soul Eater";
		matList[27][3] = "Drought Bringer";
		
		matList[28][0] = "Solaris-Touched";
		matList[28][1] = "Invulnerable";
		matList[28][2] = "Magma Barrier";
		matList[28][3] = "Empowering Minions";
		
		matList[29][0] = "Soul Eater";
		matList[29][1] = "Soul Conduit";
		matList[29][2] = "Necromancer";
		matList[29][3] = "Gargantuan";
		
		matList[30][0] = "Temporal Bubble";
		matList[30][1] = "Juggernaut";
		matList[30][2] = "Hexer";
		matList[30][3] = "Arcane Buffer";
		
		matList[31][0] = "Treant Horde";
		matList[31][1] = "Toxic";
		matList[31][2] = "Sentinel";
		matList[31][3] = "Steel-Infused";
		
		matList[32][0] = "Trickster";
		matList[32][1] = "Overcharged";
		matList[32][2] = "Assassin";
		matList[32][3] = "Echoist";
		
		matList[33][0] = "Tukohama-Touched";
		matList[33][1] = "Bonebreaker";
		matList[33][2] = "Executioner";
		matList[33][3] = "Magma Barrier";
		
		matList[34][0] = "Innocence-Touched";
		matList[34][1] = "Lunaris-Touched";
		matList[34][2] = "Solaris-Touched";
		matList[34][3] = "Mirror Image";
		matList[34][4] = "Mana Siphoner";
		
		matList[35][0] = "Kitava-Touched";
		matList[35][1] = "Tukohama-Touched";
		matList[35][2] = "Abberath-Touched";
		matList[35][3] = "Corrupter";
		matList[35][4] = "Corpse Detonator";
	}
	/**
	 * checks if array contains string
	 * @param array
	 * @param string
	 * @return true/false, if contains
	 */
	private static boolean contains(String[] array, String string) {
		for(String temp : array) {
			if (temp.equals(string)) return true;
		}
		return false;
	}
	/**
	 * matListGetter for other *.java
	 * @return String[][] matList
	 */
	public static String[][] getMatList() {
		return matList;
	}
	/**
	 * Reads the Config Folder and it's content. Any files thats in there is going to get used for "favorite" recipe
	 * @return
	 */
	private static String[][] getConfig() {
		File folder = new File("config");
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> temp = new ArrayList<File>();
		for(int i = 0; i < listOfFiles.length; i++) {
			temp.add(listOfFiles[i].getAbsoluteFile());
		}
		String[][] configFiles = new String[temp.size()][5];
		for(int i = 0; i<configFiles.length; i++) {
			configFiles[i][0] = "";
			configFiles[i][1] = "";
			configFiles[i][2] = "";
			configFiles[i][3] = "";
			configFiles[i][4] = "";
			try(BufferedReader br = new BufferedReader(new FileReader(temp.get(i)))) {
				String line = "";
				configFiles[i][0] = temp.get(i).getName().substring(0, temp.get(i).getName().length()-4);
				int counter = 1 ;
				while ((line = br.readLine()) != null && counter < 5) {
					configFiles[i][counter] = line;
					counter++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configFiles;
	}
}
