import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int serverPort = 8080;
		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Server starting...");
		System.out.println("The server started with at port " + serverPort);
		Socket socket = serverSocket.accept();
		
		FileInputStream fr = new FileInputStream("/Users/mrunayamac/Desktop/abc.txt");
		byte b[] = new byte[1000];
		fr.read(b, 0, b.length);
		OutputStream os = socket.getOutputStream();
		os.write(b, 0, b.length);
		
		

	}

}
