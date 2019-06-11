package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static String serverExpress(){
		Socket clientSocket = null;
		try {
			clientSocket = new Socket("192.168.15.43", 3001);
			BufferedReader din = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "ASCII"));
			String line = null;
			String s = "";
			while ((line = din.readLine()) != null) {
				s = s + "\r\n" + line;
				if (line.length() == 0)
					break;
			}
			System.out.println(s);
			return s;
		} catch (IOException e) {
			
		}finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				
			}
		}
	//public static void main(String []) throws IOException {
		return null;
		
	}

}
