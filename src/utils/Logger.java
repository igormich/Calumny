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
		if(card==8) return "���������";
		if(card==7) return "���������";
		if(card==6) return "������";
		if(card==5) return "�����";
		if(card==4) return "��������";
		if(card==3) return "�����";
		if(card==2) return "���������";
		if(card==1) return "���������";
		return "";
	}
	public static void serverLog(String message) {
		log("Server: " + message);	
	}
}
