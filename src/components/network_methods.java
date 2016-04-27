package components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class network_methods implements Commons {
	protected boolean isHost;
	protected BufferedWriter output;
	protected BufferedReader input;
	protected String hostName;
	protected String clientName;
	Socket socket;
	protected String ip;
	private JFrame frame;
	DatagramSocket toSocket;
	
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
	}
	
	protected void start(){
		new MainGame(frame,this);	//start multi-player I/O loop.
	}
	
	public final void lookForPlayers() throws IOException{
		isHost = true;
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
		ss.close();
		s.close();
		this.toSocket = new DatagramSocket();
		start();
	}
	
	public final void joinGame(String clientName, Socket socket) throws IOException{
		isHost = false;
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
		socket.close();
		this.toSocket = new DatagramSocket(GAMEPORT);
		start();
	}
	
	public void exitGame() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
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
	
	//----------------**UDP Methods**------------------\\
	
	public String getUdpMessage() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(new byte[11],11);
		toSocket.receive(receivePacket);
		return new String(receivePacket.getData(),0,receivePacket.getLength());
	}
		
	public void sendUdpMessage(String message) throws IOException{
		try {
			byte[] sendBytes = message.getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(sendBytes,sendBytes.length,InetAddress.getByName(ip),GAMEPORT);
			toSocket.send(sendPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}


