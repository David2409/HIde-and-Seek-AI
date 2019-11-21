package Server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.html.HTMLIsIndexElement;

import Logic.Trainer;
import Overworker.OverworkData;
import general.XML;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private List<Connection> connections;
	private Trainer trainer;
	
	public Server(int port) throws Exception{
		serverSocket = new ServerSocket(port);
		connections = new ArrayList<Connection>();
		start();
	}
	
	public static void main(String[] input){
		input = new String[]{"0"};
		Server server = null;
		try{
			server = new Server(Integer.parseInt(input[0]));
			server.setName("Server");
			server.Log("Started");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int networks = 100;
		OverworkData[] data = new OverworkData[networks];
		for (int i = 0; i < data.length; i++) {
			data[i] = new OverworkData(0.25f, (byte) 20, (byte) 20);
		}
		server.StartT(new float[][]{{1,1,1,1,1,},{1,0,0,0,1},{1,0,1,1,1},{1,0,0,0,1},{1,1,1,1,1}}, new float[][]{{0,0,0,0,0},{0,0,0,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}}, new float[][]{{0,0,0,0,0},{0,80,160,320,0},{0,40,0,0,0},{0,20,10,0,0},{0,0,0,0,0}}, 3, 3, 10, networks, 1, 4, data);
		Scanner scanner = new Scanner(System.in);
		while(scanner.nextLine() != "dis");
	}
	
	@Override
	public void run() {
		try {
			while(true){
				connections.add(new Connection(serverSocket.accept(), this));
				Log("Created new Connection");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SendToAll(String message){
		for (Connection connection : connections) {
			connection.SendMessage(message);
		}
	}
	
	public boolean SendFieldToAll(float[][] field){
		try {
			SendToAll(Message.SendField.name() + Connection.splitSymble + XML.FloatArray2ToString(field));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void StartT(float[][] field, float[][] gool, float[][] cockies, int iPlayerPosX, int iPlayerPosY, int iMaxMoves, int iNetworkAmount, int iRessistance, int iCalculatIn, OverworkData[] iOverworkData){
		trainer = new Trainer(this, field, gool, cockies, iPlayerPosX, iPlayerPosY, iMaxMoves, iNetworkAmount, iRessistance, iCalculatIn, iOverworkData);
		trainer.setName("Trainer-" + trainer.getId());
		trainer.start();
		Log("Started Training (Trainer id: " + trainer.getId() + ")");
	}
	
	public void StopT(){
		trainer.stop();
		Log("Stoped Training");
	}
	
	public void PauseT() throws Exception{
		trainer.wait();
		Log("Paused Training");
	}
	
	public void ResumeT(){
		trainer.notify();
		Log("Resumed Training");
	}
	
	public void Log(Thread from, String log){
		System.out.println(getName() +  "-" + from.getName() + ": " + log);
	}
	
	private void Log(String log){
		System.out.println(getName() + ": " + log);
	}
}