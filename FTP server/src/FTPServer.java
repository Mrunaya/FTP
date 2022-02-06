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
	public static String SERVER_DIRECTORY = "/Users/mrunayamac/Desktop"; 
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server starting...");
		System.out.println("The server started at port " + SERVER_PORT);
		
			Socket socket = serverSocket.accept();

			

			while(true) {
				//ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String inputCmd = (String) inputStream.readObject();

			String[] userInput = inputCmd.split(" ", 2);
			switch(userInput[0]) {
			case "1":// get file
				File file= new File(SERVER_DIRECTORY+"/"+userInput[1]);
				OutputStream os = socket.getOutputStream();
				byte bGet[] = new byte[1000];
				if(file.exists()) {
					FileInputStream fileInStream = new FileInputStream(file);
					fileInStream.read(bGet, 0, bGet.length);

					os.write(bGet, 0, bGet.length);
					System.out.println("File has been sent!");
				}
				else{
					
					os.write(bGet,0,bGet.length);
					System.out.println("File do not exist!");
				}
				break;

			case "2":// put file
				InputStream input =socket.getInputStream();
				FileOutputStream fileStreamPut = new FileOutputStream(SERVER_DIRECTORY+"/copy"+userInput[1]);
				byte bPut[] = new byte[1000];
				input.read(bPut, 0, bPut.length);
				fileStreamPut.write(bPut, 0, bPut.length);
				fileStreamPut.flush();
				break;
				
			case "3": //Delete File
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
				
			case "4": //List files

				File f1 = new File(SERVER_DIRECTORY);
				String[] s=f1.list();
				ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(s);
				
				break;

			case "5":// change directory
				if(userInput[1].equals("cd ..")) {
					SERVER_DIRECTORY=SERVER_DIRECTORY.substring(0, SERVER_DIRECTORY.lastIndexOf("/"));
					System.out.println("Current directory chnaged to: "+SERVER_DIRECTORY);
				}
				else{
					String[] directory=userInput[1].split(" ");
					SERVER_DIRECTORY=SERVER_DIRECTORY.concat("/"+directory[1]);
					System.out.println("Current directory chnaged to: "+SERVER_DIRECTORY);
				}

				break;
			case "6":// make directory
				File f = new File(SERVER_DIRECTORY.concat("/"+userInput[1]));
				if (f.mkdir()) {
					System.out.println("Directory" +userInput[1]+ "created");
				}
				else {
					System.out.println("Directory cannot be created");
				}
			break;
			case "7":// PWD
				ObjectOutputStream outputStream1 = new  ObjectOutputStream(socket.getOutputStream());
				outputStream1.writeObject(SERVER_DIRECTORY);
				System.out.println(SERVER_DIRECTORY);
			break;	

			}

		}
		

	}

}