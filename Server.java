import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Server implements Runnable
{
	private ServerSocket serverSocket;
	Socket server;
	Thread t;
	public Server(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
		while(true)
		{
			 Scanner sc=new Scanner(System.in);                     
			 System.out.println("Waiting for client on "+serverSocket.getLocalPort() + "...");
			 server = serverSocket.accept();
			 t=new Thread(this);
			 t.start();
		}
		
	}

	public void run()
	{
			try
			{
				System.out.println("connected to " + server.getRemoteSocketAddress());
				Scanner sc=new Scanner(System.in);
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				String str="";
				while(true)
				{
					String recv="";
					recv=(String)in.readUTF();
					System.out.println("Client says"+recv);
					if(recv.equals("Over"))
							break;
					str=sc.next();
					out.writeUTF(str);

				}
				server.close();

			} 
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
	}

	public static void main(String [] args)
	{
		int port = Integer.parseInt(args[0]);
		try
		{
			Server s = new Server(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
