package archnemesis;


import java.io.File;
import java.util.ArrayList;

//import java.awt.GridLayout;
//import java.awt.Image;

//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
//import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Calc {
	
	static Mat img = new Mat();
	static Mat imgZwei = new Mat();
	static Mat mask = new Mat();
	static ArrayList<String> inventory = new ArrayList<String>();
	static String material = "";
	
	public static ArrayList<String> go() {
		inventory.clear();
		ScreenRecorder.go();
		
		ArrayList<File> files = getFiles();
		img = Imgcodecs.imread("Screenshot.png", Imgcodecs.IMREAD_COLOR);
		
		for(File file : files) {
			imgZwei = Imgcodecs.imread("dataSource\\"+file.getName(), Imgcodecs.IMREAD_COLOR);
			material = file.getName().substring(0,file.getName().length()-4);
			if(img.empty() || imgZwei.empty()) {
				System.out.println("Fehler.");
				System.exit(0);
			}
			matchingMethod();
		}
		String[][] matList = MainWindow.getMatList();
		for(int i = 0; i<matList.length; i ++) {
			for(int j = 0; j<matList[i].length; j++) {
				matList[i][j] = matList[i][j].replaceAll(" ", "");
			}
		}

		return inventory;
	}
	
	private static void matchingMethod() {
		Mat result = new Mat();
		Mat img_display = new Mat();
		img.copyTo(img_display);

		int result_cols = img.cols() - imgZwei.cols() + 1;
		int result_rows = img.rows() - imgZwei.cols() + 1;
		
		result.create(result_rows, result_cols, CvType.CV_32FC1);
		
		
        Imgproc.matchTemplate(img, imgZwei, result, Imgproc.TM_CCOEFF_NORMED);
        Imgproc.threshold(result, result, 0.1, 1, Imgproc.THRESH_TOZERO);
        
		double threshold = 0.95;
		double maxval;
		
		
		while(true) {
			
			
			Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
			
			Point matchLoc = mmr.maxLoc;
			
			maxval = mmr.maxVal;
			if(maxval >=threshold) {
				Imgproc.rectangle(img_display, matchLoc, new Point(matchLoc.x + imgZwei.cols(), matchLoc.y + imgZwei.rows()), new Scalar(0, 0, 255));
				Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + imgZwei.cols(), matchLoc.y + imgZwei.rows()), new Scalar(0,0,255),-1);
				inventory.add(material);
			} else {
				break;
			}
		}

	}
	/**
	 * gets all the *.pngs in the dataSource-Folder
	 * @return List with all the Files.
	 */
	public static  ArrayList<File> getFiles() {
		File folder = new File("dataSource");
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> temp = new ArrayList<File>();
		for(int i = 0; i < listOfFiles.length; i++) {
			temp.add(listOfFiles[i].getAbsoluteFile());
		}
		return temp;
	}
}