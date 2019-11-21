package Logic;

import Network.*;
import Overworker.*;
import Server.Server;

public class Trainer extends Thread {
	Server server;
	private ArrrayTemplate arrayTemplate;
	private float[][] field;
	private float[][] goolPos;
	private float[][] cockies;
	private int playerPosX;
	private int playerPosY;
	private int maxMoves;
	private float ressistance;
	private Network[] networks;
	private OverworkData[] overworkData;
	private int calculateIn;
	
	public Trainer(Server iServer, float[][] iField, float[][] iGoolPos, float[][] iCockies, int iPlayerPosX, int iPlayerPosY, int iMaxMoves, int networkAmount, float IRessistance, int iCalculateIn, OverworkData[] iOverworkData){
		server = iServer;
		field = iField;
		goolPos = iGoolPos;
		playerPosX = iPlayerPosX;
		playerPosY = iPlayerPosY;
		maxMoves = iMaxMoves;
		ressistance = IRessistance;
		calculateIn = iCalculateIn;
		overworkData = iOverworkData;
		cockies = iCockies;
		networks = new Network[networkAmount];
		float[] template = new float[4 * field.length * field.length];
		for (int i = 0; i < template.length; i++) {
			template[i] = 1f;
		}
		float[] templateBig = new float[template.length * 2];
		for (int i = 0; i < template.length; i++) {
			templateBig[i] = 1f;
		}
		arrayTemplate = new ArrrayTemplate(template, templateBig, iCockies);
	}
	
	@Override
	public void run(){
		int[] layers = new int[]{8 * field.length * field.length, 4};
		for (int i = 0; i < networks.length; i++) {
			networks[i] = new Network(layers, 1);
		}
		GameThread[] gameThreads = new GameThread[networks.length];
		Network best = networks[0];
		int maxScore = GetMaxScore(cockies, playerPosX, playerPosY, maxMoves);
		while (best.fitness != maxScore) {
			for (int i = 0; i < gameThreads.length; i++) {
				GameThread gameThread = new GameThread(new Game(field, goolPos, playerPosX, playerPosY, arrayTemplate), networks[i], maxMoves);
				gameThreads[i] = gameThread;
				gameThread.start();
			}
			Wait(gameThreads);
			Network.Sort(networks);
			best = networks[0];
			NetworkData networkData = new NetworkData(networks, NetworkData.GetImportance(networks, calculateIn));
			networks = networkData.GenerateNetworks(overworkData, ressistance);
			networks[0] = best;
			Log("Best Network Finished with " + ((int) best.fitness) + "/" + maxScore);
		}
		Log("Finished Training");
	}
	
	private void Wait(Thread[] threads){
		for (Thread thread : threads) {
			if(thread.getState() != State.TERMINATED){
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private int GetMaxScore(float[][] cockies, int startx, int starty, int maxMoves){
		int[] pos = new int[]{starty, startx};
		int maxScore = 0;
		for(int i = maxMoves; i > 0; i--){
			int[] newPos = GetNewPos(cockies, pos[1], pos[0]);
			if(Compare(pos, newPos)){
				return maxScore;
			} else {
				maxScore += i * cockies[(int) newPos[0]][(int) newPos[1]];
			}
			pos = newPos;
		}
		return maxScore;
	}
	
	private int[] GetNewPos(float[][] field, int posX, int posY){
		if(isHigher(field[posY][posX-1], new float[]{field[posY][posX], field[posY][posX+1], field[posY-1][posX], field[posY+1][posX]})){
			return new int[]{posY, posX-1};
		} else if(isHigher(field[posY][posX+1], new float[]{field[posY][posX], field[posY][posX-1], field[posY-1][posX], field[posY+1][posX]})){
			return new int[]{posY, posX+1};
		} else if(isHigher(field[posY-1][posX], new float[]{field[posY][posX], field[posY][posX-1], field[posY][posX+1], field[posY+1][posX]})){
			return new int[]{posY-1, posX};
		} else if(isHigher(field[posY+1][posX], new float[]{field[posY][posX], field[posY][posX-1], field[posY][posX+1], field[posY-1][posX]})){
			return new int[]{posY+1, posX};
		} else {
			return new int[]{posY, posX};
		}
	}
	
	private boolean isHigher(float number, float[] others){
		for (int i = 0; i < others.length; i++) {
			if(number < others[i]){
				return false;
			}
		}
		return true;
	}
	
	private boolean Compare(int[] a1, int[] a2){
		for (int i = 0; i < a1.length; i++) {
			if(a1[i] != a2[i]){
				return false;
			}
		}
		return true;
	}
	
	private void Log(String log){
		server.Log(this, log);
	}
}