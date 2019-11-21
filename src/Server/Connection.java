package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import general.XML;

public class Connection extends Thread{
	public final static String splitSymble = "<SPLIT>";
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Server server;
	private static List<Integer> usedIds = new ArrayList<Integer>();
	
	public Connection(Socket iSocket, Server iServer) throws Exception{
		server = iServer;
		socket = iSocket;
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
		Integer id;
		do{
			id = new Random().nextInt();
		} while (!usedIds.contains(id));
		setName(id.toString());
		start();
	}
	
	@Override
	public void run() {
		try {
			String input = "";
			String[] command = null;
			do {
				input = inputStream.readUTF();
				command = input.split(splitSymble);
				SendMessage(Boolean.toString(ExecuteCommand(command)));
			} while(!command[0].equals(Message.CLOSECONNECTION));
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean SendMessage(String message){
		try {
			outputStream.writeUTF(message);
			outputStream.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean ExecuteCommand(String[] input){
		try {
			switch(input[0]){
			case "START":
				//			  field 1 							| gool 2 							| cockies 3 					| 					pl ayerPos 4,5 							|  maxMoves 6 				| net amount 7 				| ressistance 8
				server.StartT(XML.StirngToFloatArray2(input[1]).array, XML.StirngToFloatArray2(input[2]).array, XML.StirngToFloatArray2(input[3]).array, Integer.parseInt(input[4]), Integer.parseInt(input[5]), Integer.parseInt(input[6]), Integer.parseInt(input[7]), Integer.parseInt(input[8]));
				break;
			case "STOP":
				server.StopT();
				break;
			case "PAUSE":
				server.PauseT();
				break;
			case "RESUME":
				server.ResumeT();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void Log(String log){
		System.out.println(server.getName() + "-Connection-" + getName() + log);
	}
}