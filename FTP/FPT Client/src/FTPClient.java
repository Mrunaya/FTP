import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTPClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("localhost",8080);
		System.out.println("Connecting to Server..");
		InputStream input =socket.getInputStream();
		FileOutputStream fileStream = new FileOutputStream("/Users/mrunayamac/Documents/abcCopy.txt");
		byte b[] = new byte[1000];
		input.read(b, 0, b.length);
		fileStream.write(b, 0, b.length);
		
		System.out.println("Server Connected!");
		try  {
			Scanner sc = new Scanner(System.in);
			while(true) {
				//USER COMMANDS BEGIN FROM HERE----->
				System.out.println("What service would you want to leverage?");
				System.out.println(" 1 = Get file \n 2 = Put file \n 3 = Delete file \n"
						+ " 4 = List Files \n 5 = Change directory \n 6 = Make directory \n 7 = Print current working directory \n 8 = Exit ");

				String userCmd = sc.nextLine();
				
				switch (userCmd) {
				case "1": //  Get file 
					break;

				case "2": // Put file
					break;

				case "3": // Delete file
					break;

				case "4": // List files
                   break;

				case "5": // Change directory
					break;

				case "6": // Make directory
					break;

				case "7": //PWD
					break;
					
				case "8": //Exit
					break;

				default:
					System.out.println("Invalid Command");
					break;
				}		
			}
	
		}catch(Exception e) {}
	}
}
