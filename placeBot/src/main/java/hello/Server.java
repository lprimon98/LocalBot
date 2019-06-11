package hello;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void serverConnection(ServerSocket s, Socket clientSocket, String json) {
		//public static void main(String args[]) throws IOException {
			OutputStreamWriter outWriter = null;
			try {
				outWriter = new OutputStreamWriter(clientSocket.getOutputStream());
				outWriter.write("HTTP/1.1 200 OK\r\n");
				outWriter.write("Date: Tue, 11 Jan 2011 13:09:20 GMT\r\n");
				outWriter.write("Expires: -1\r\n");
				outWriter.write("Cache-Control: private, max-age=0\r\n");
				outWriter.write("Content-type: text/html\r\n");
				outWriter.write("Server: vinit\r\n");
				outWriter.write(json);
				//System.out.println(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					outWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
