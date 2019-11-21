package Network;
import java.util.Random;

public class Synapse {
	public Connection[] connections;
	public float value;
	public float ressistens;
	
	public Synapse(){
		
	}
	
	public Synapse(int layerBeforeLenght, float iRessistensTo){
		connections = new Connection[layerBeforeLenght];
		Random rand = new Random();
		for(int i = 0; i < layerBeforeLenght; i++){
			connections[i] = new Connection(rand.nextFloat());
		}
		value = 0f;
		ressistens = rand.nextFloat() * iRessistensTo * -1;	
	}
}
