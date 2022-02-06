import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FTPClient {
	//public final static int CLIENT_PORT = 8081;  
	//public final static String CLIENT_DIRECTORY = System.getProperty("user.dir"); 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String serverName=args[0];
		int port=Integer.parseInt(args[1]);
		
		Socket socket = new Socket(serverName,port);
		System.out.println("Connecting to Server..");
		//ObjectOutputStream outputStream = new  ObjectOutputStream(socket.getOutputStream());
		//System.out.println(CLIENT_DIRECTORY);
		
		System.out.println("Server Connected!");
		try  {
			Scanner sc = new Scanner(System.in);
			while(true) {
				//USER COMMANDS BEGIN FROM HERE----->
				System.out.println("\n *** What service would you want to leverage? ***");
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

					InputStream inputStream =socket.getInputStream();
					FileOutputStream fileStreamGet = new FileOutputStream("copy"+fileName);
					byte bGet[] = new byte[1000];
					inputStream.read(bGet, 0, bGet.length);
					String s = new String(bGet, StandardCharsets.UTF_8);
					fileStreamGet.write(bGet, 0, bGet.length);
					System.out.println("File has been copied to local directory!");
						
					break;

				case "2": // Put file
					
					ObjectOutputStream outputStreamPut = new  ObjectOutputStream(socket.getOutputStream());
					
					File file= new File(fileName);
					if(file.exists()) {
					FileInputStream fileStreamPut = new FileInputStream(file);
					outputStreamPut.writeObject("2 "+fileName);
					byte bPut[] = new byte[1000];
					fileStreamPut.read(bPut, 0, bPut.length);
					OutputStream os = socket.getOutputStream();
					os.write(bPut, 0, bPut.length);
					os.flush();
					System.out.println("File has been sent!");
					}else {
						System.out.println("File at the location do not exists!");
					}
					break;

				case "3": // Delete file
					
					ObjectOutputStream outputStreamDel = new ObjectOutputStream(socket.getOutputStream());
					outputStreamDel.writeObject("3 "+fileName);
					break;

				case "4": // List files
					//String list1 =sc.nextLine();
					ObjectOutputStream outputStreamList = new ObjectOutputStream(socket.getOutputStream());
					outputStreamList.writeObject("4");
					
					ObjectInputStream InputStreamList = new ObjectInputStream(socket.getInputStream());
					String[] list = (String[])InputStreamList.readObject();
					for(String s1:list)
					{
						System.out.print(s1+" ");
					}
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
					ObjectOutputStream outputStreampwd = new ObjectOutputStream(socket.getOutputStream());
					outputStreampwd.writeObject("7 ");

					ObjectInputStream outputStreampwd2 = new ObjectInputStream(socket.getInputStream());
					String path = (String)outputStreampwd2.readObject();
					System.out.println(path);
					break;

				case "8": 
					ObjectOutputStream outputStreamExit = new ObjectOutputStream(socket.getOutputStream());
					outputStreamExit.writeObject("8 ");
					socket.close();
					System.exit(0);//Exit
					break;

				default:
					System.out.println("Invalid Command");
					break;
				}		
			}
	
		}catch(Exception e) {}
	}
}