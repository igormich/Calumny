package utils;
import java.awt.TextArea;


public class Logger {

	private static TextArea textArea;

	public static void log(String message) {
		System.out.println(message);
		if(textArea!=null)
			textArea.setText(textArea.getText()+"\n"+message);
	}
	public static String cardById(int card) {
		if(card==8) return "принцесса";
		if(card==7) return "герцогиня";
		if(card==6) return "король";
		if(card==5) return "принц";
		if(card==4) return "служанка";
		if(card==3) return "барон";
		if(card==2) return "священник";
		if(card==1) return "стражница";
		return "";
	}
	public static void serverLog(String message) {
		log("Server: " + message);	
	}
}
