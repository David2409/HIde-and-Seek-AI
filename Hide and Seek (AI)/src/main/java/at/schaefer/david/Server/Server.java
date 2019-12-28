package at.schaefer.david.Server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import at.schaefer.david.Logic.Trainer;
import at.schaefer.david.Overworker.OverworkData;
import at.schaefer.david.general.XML;

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
		input = new String[]{"1012"};
		Server server = null;
		try{
			server = new Server(Integer.parseInt(input[0]));
			server.setName("Server");
			server.Log("Started");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int networks = 60;
		OverworkData[] data = new OverworkData[networks];
		data[0] = new OverworkData(0f,(byte) 0,(byte) 0);
		for(int i = 1; i < networks/6;i++){
			data[i] = new OverworkData(0.05f,(byte) 10,(byte) 5);
		}
		for(int i = networks/6; i < 2*networks/6;i++){
			data[i] = new OverworkData(0.1f,(byte) 25,(byte) 10);
		}
		for(int i = 2*networks/6; i < 3*networks/6;i++){
			data[i] = new OverworkData(0.15f,(byte) 50,(byte) 25);
		}
		for(int i = 3*networks/6; i < 4*networks/6;i++){
			data[i] = new OverworkData(0.15f,(byte) 50,(byte) 30);
		}
		for(int i = 4*networks/6; i < 5*networks/6;i++){
			data[i] = new OverworkData(0.2f,(byte) 25,(byte) 25);
		}
		for(int i = 5*networks/6; i < networks;i++){
			data[i] = new OverworkData(0.35f,(byte) 25,(byte) 10);
		}
		server.StartT(new float[][]{{0,0,1},
									{1,0,0},
									{1,1,0}}, 
					  new float[][]{{1,0,0},
									{0,0,0},
									{0,0,0}}, 
					  	new int[][]{{4,3,0},
									{0,2,1},
									{0,0,0}}, 2, 2, 4, networks, 1, 5, data);
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
			SendToAll(Message.SendField.name() + Connection.splitSymble + XML.Object2ToString(field));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void StartT(float[][] field, float[][] gool, int[][] cockies, int iPlayerPosX, int iPlayerPosY, int iMaxMoves, int iNetworkAmount, int iRessistance, int iCalculatIn, OverworkData[] iOverworkData){
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