package components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class network_methods implements Commons {
	protected boolean isHost;
	protected BufferedWriter output;
	protected BufferedReader input;
	protected String hostName;
	protected String clientName;
	private Socket socket;
	private String ip;
	private JFrame frame;
	
	public network_methods(Socket socket, String hostName, String clientName, BufferedReader input, BufferedWriter output) {
		this.socket = socket;
		this.hostName = hostName;
		this.clientName = clientName;
		this.input = input;
		this.output = output;
		isHost = false;
	}

	public network_methods(String username,JFrame frame){
		this.frame = frame;
		this.hostName = username;
		isHost = true;
	}
	
	protected void start(){
		new MainGame(frame,this);	//start multi-player I/O loop.
	}
	
	public final void lookForPlayers() throws IOException{
		System.out.println("Waiting for a player to join");
		//open server socket
		ServerSocket ss = new ServerSocket(GAMEPORT);
		//wait for player to join
		Socket s = ss.accept();
		this.socket = s;
		//get and store client ip
		ip = s.getInetAddress().getHostAddress();
		System.out.println("client ip is stored as: "+ip);
		//set up input/output streams
		input = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
		output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		//send name of game and player's name
		output.write("hostname: " + hostName + "\n");
		output.flush();
		//user sends their name back
		clientName = getMessage(30000);
		System.out.println("Player "+clientName+" has been found! Starting game.");
		start();
	}
	
	public final void joinGame(String clientName, Socket socket) throws IOException{
		ip = socket.getInetAddress().getHostAddress();
		this.socket = socket;
		System.out.println("host ip is stored as: "+ip);
		//establish streams
		input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		//figure out what game we're playing
		String in = getMessage(input, 30000);
		String hostName = in.substring(in.indexOf(' ')+1).trim();
		System.out.println("Host: " + hostName);
		output.write(clientName + "\n");
		output.flush();
		start();
	}
	
	public void exitGame() {
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) throws IOException {
		output.write(message + "\n");
		//output.flush();
	}
	
	private static String getMessage(BufferedReader input, long timeout) throws IOException {
		int count = 0;
		long maxCount = timeout / 100;
		while(!input.ready()) {
			System.out.println("input not ready");
			try{Thread.sleep(100);}catch(Exception e){}
			count++;
			//after 30 seconds just give up!
			if(count > maxCount){
				throw new IOException("Timed out after " + timeout + " milliseconds");
			}
		}
		String in = input.readLine();
		System.out.println(in);
		return in;
	}
	
	public String getMessage(long timeout) throws IOException {
		return network_methods.getMessage(input, timeout);
	}
	
	public void newConnection(){
		try {
	        socket = new Socket( ip, GAMEPORT);
	        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
	    } catch (UnknownHostException e) {	        
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}


