package task_list_generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.text.DateFormatter;

import org.apache.commons.lang3.StringUtils;

public class TaskListGenerator {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		initializeConfig();
		File taskListFolder = new File(AppConfig.OUTPUT_PATH.value());
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = currentDate.format(formatter);
		File taskList = new File(taskListFolder.getAbsolutePath() + "/" +date.replaceAll("/","-") + ".txt");
		String star = "*";
		String equalSign = "=";
		String header = String.format(StringUtils.repeat(equalSign,144) + "\n" + StringUtils.repeat(star, 4) + "DATE" + StringUtils.repeat(star, 5)
				+ "TASK DESCRIPTION" + StringUtils.repeat(star, 115) + "\n" + StringUtils.repeat(equalSign,144));
		BufferedWriter writer = null;
		Scanner sc = null;
		
		System.out.flush();
		if(!taskList.exists()){
			writeNew(date,header,taskList,writer,sc);
		}else{
			appendList(date,taskList,writer, sc);
		}

	}
	public static void appendList(String date, File taskList, BufferedWriter writer, Scanner sc) throws IOException{
		writer = new BufferedWriter(new FileWriter(taskList,true));
		sc = new Scanner(System.in);
		writer.newLine();
		writer.append(date);
		writer.append("   ");
		System.out.println("Please Enter Task Description: \n");
		while(true){
			System.out.println("Writing line...");
			String line = sc.nextLine();
			if(line.equalsIgnoreCase("done"))break;
			System.out.println("Enter next line: (enter \"done\" to finish writing)");
			writer.append(line);
			writer.newLine();
			writer.append(StringUtils.repeat(" ", 12));
			writer.flush();	
		}
		sc.close();
		writer.close();
	}
	
	public static void writeNew(String date, String header, File taskList, BufferedWriter writer, Scanner sc) throws IOException{
		writer = new BufferedWriter(new FileWriter(taskList,true));
		sc = new Scanner(System.in);
		writer.write(header);
		writer.newLine();
		writer.write(date);
		writer.append("   ");
		System.out.println("Please Enter Task Description: \n");
		while(true){
			System.out.println("Writing line...");
			String line = sc.nextLine();
			if(line.equalsIgnoreCase("done"))break;
			System.out.println("Enter next line: (enter \"done\" to finish writing)");
			writer.append(line);
			writer.newLine();
			writer.append(StringUtils.repeat(" ", 12));
			writer.flush();	
		}
		sc.close();
		writer.close();
	}
	

	private static void initializeConfig() {
		try {
			AppConfig.setContext(new FileInputStream(new File("config/config.properties")));
		} catch (FileNotFoundException e) {
			System.out.println("ConfigFile Not Found");
			e.printStackTrace();
			System.exit(0);
		}

	}
}
