import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client
{

	public static void main(String [] args)
	{
		Scanner sc=new Scanner(System.in);
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		try
		{
			System.out.println("Connecting to " + serverName + " on port" + port);
			Socket client = new Socket(serverName, port);

			System.out.println("Connected to "+client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();                                
			DataInputStream in = new DataInputStream(inFromServer);     
			String str="";
			while(!str.equals("Over"))
			{
				str=sc.next();
				out.writeUTF(str);
				if(str.equals("Over"))
				{
					break;
				}
				System.out.println("Server says " + in.readUTF());
			}
			client.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
