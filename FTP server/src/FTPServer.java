import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
	public final static int SERVER_PORT = 8081;  
	public static String SERVER_DIRECTORY = System.getProperty("user.dir"); 
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		int port=Integer.parseInt(args[0]);
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server starting...");
		System.out.println("The server started at port " + port);
		while(true) {
			Socket socket = serverSocket.accept();
			System.out.println("Client connected!");
			
			try{
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

			while(true) {
				
			String inputCmd = (String) inputStream.readObject();
			System.out.println("Input command is : " + inputCmd);
			
			String[] userInput = inputCmd.split(" ", 2);
		
			if("quit".equalsIgnoreCase(userInput[0])){
				outputStream.writeObject("quit");
				 inputStream.close();
				 outputStream.close();
				 break;
			}
			switch(userInput[0]) {
			case "get":// get file
				File file= new File(SERVER_DIRECTORY+"/"+userInput[1]);
				byte bGet[] = new byte[1000];
				if(file.exists()) {
					FileInputStream fileInStream = new FileInputStream(file);
					fileInStream.read(bGet, 0, bGet.length);
					outputStream.write(bGet, 0, bGet.length);
					System.out.println("File has been sent!");
				}
				else{
					
					outputStream.write(bGet,0,bGet.length);
					System.out.println("File do not exist!");
				}
				outputStream.flush();
				break;

			case "put":// put file
				FileOutputStream fileStreamPut = new FileOutputStream(SERVER_DIRECTORY+"/"+userInput[1]);
				byte bPut[] = new byte[1000];
				inputStream.read(bPut, 0, bPut.length);
				fileStreamPut.write(bPut, 0, bPut.length);
				fileStreamPut.flush();
				break;
				
			case "delete": //Delete File
				File f2 = new File(SERVER_DIRECTORY+"/"+userInput[1]);
				System.out.println("file to be delted:"+userInput[1]);
				if (f2.delete())
				{
					System.out.println("File deleted successful");
				}
				else
				{
					System.out.println("File did not get deleted successful");
				}
				break;
				
			case "ls": //List files

				File f1 = new File(SERVER_DIRECTORY);
				String[] s=f1.list();
				//ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(s);
				outputStream.flush();
				
				break;

			case "cd":// change directory
				if(userInput[1].equals("..")) {
					SERVER_DIRECTORY=SERVER_DIRECTORY.substring(0, SERVER_DIRECTORY.lastIndexOf("/"));
					System.out.println("Current directory chnaged to: "+SERVER_DIRECTORY);
				}
				else{
					SERVER_DIRECTORY=SERVER_DIRECTORY.concat("/"+userInput[1]);
					System.out.println("Current directory chnaged to: "+SERVER_DIRECTORY);
				}

				break;
			case "mkdir":// make directory
				File f = new File(SERVER_DIRECTORY.concat("/"+userInput[1]));
				if (f.mkdir()) {
					System.out.println("Directory" +userInput[1]+ "created");
				}
				else {
					System.out.println("Directory cannot be created");
				}
			break;
			case "pwd":// PWD
				//ObjectOutputStream outputStream1 = new  ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(SERVER_DIRECTORY);
				outputStream.flush();
			break;	
			
				

			}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		}

	}

}
