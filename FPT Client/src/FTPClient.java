import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTPClient {
	public final static int CLIENT_PORT = 8080;  
	public final static String CLIENT_DIRECTORY = "/Users/mrunayamac/Documents/"; 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("localhost",CLIENT_PORT);
		System.out.println("Connecting to Server..");
		//ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
		
		
		System.out.println("Server Connected!");
		try  {
			Scanner sc = new Scanner(System.in);
			while(true) {
				//USER COMMANDS BEGIN FROM HERE----->
				System.out.println("What service would you want to leverage?");
				System.out.println(" 1 = Get file \n 2 = Put file \n 3 = Delete file \n"
						+ " 4 = List Files \n 5 = Change directory \n 6 = Make directory \n 7 = Print current working directory \n 8 = Exit ");

				String userCmd = sc.nextLine();
				String fileName = "";
				if (userCmd.equals("1") || userCmd.equals("2") ||userCmd.equals("3")) {
					System.out.println("Please enter the file name");
					 fileName = sc.nextLine();
					
				}
				
				switch (userCmd) {
				case "1": //  Get file from server->client
					ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
					outputStream.writeObject("1 "+fileName); 

					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					FileOutputStream fileStreamGet = new FileOutputStream(CLIENT_DIRECTORY+"copy"+fileName);
					byte bGet[] = new byte[1000];
					if(!"File do not exists".equalsIgnoreCase((String) inputStream.readObject())){
						inputStream.read(bGet, 0, bGet.length);
						fileStreamGet.write(bGet, 0, bGet.length);
						System.out.println("File has been copied to local directory!");
					}else {
						System.out.println("File does not exists at the location!");
					}
					break;

				case "2": // Put file
					
					ObjectOutputStream outputStreamPut = new  ObjectOutputStream(socket.getOutputStream());
					
					File file= new File(CLIENT_DIRECTORY+fileName);
					if(file.exists()) {
					FileInputStream fileStreamPut = new FileInputStream(file);
					outputStreamPut.writeObject("2 "+fileName);
					byte bPut[] = new byte[1000];
					fileStreamPut.read(bPut, 0, bPut.length);
					OutputStream os = socket.getOutputStream();
					os.write(bPut, 0, bPut.length);
					os.flush();
					//outputStreamPut.reset();
					System.out.println("File has been sent!");
					}else {
						System.out.println("File at the location do not exists!");
					}
					break;

				case "3": // Delete file
					break;

				case "4": // List files
					break;

				case "5": // Change directory
					System.out.println("Please enter change directory command");
					String cdcmd = sc.nextLine();
					
					ObjectOutputStream outputStreamCd = new  ObjectOutputStream(socket.getOutputStream());
					outputStreamCd.writeObject("5 "+cdcmd);
					break;

				case "6": // Make directory
					System.out.println("Please enter directory name");
					String mkdircmd = sc.nextLine();
					
					ObjectOutputStream outputStreammkdir = new  ObjectOutputStream(socket.getOutputStream());
					outputStreammkdir.writeObject("6 "+mkdircmd);
					break;
	
				case "7": //PWD
					break;

				case "8": 
					socket.close();//Exit
					break;

				default:
					System.out.println("Invalid Command");
					break;
				}		
			}
	
		}catch(Exception e) {}
	}
}
