package Network;

public class Network {
	public Layer[] layers;
	public long fitness;
	
	public Network(int layers) {
		this.layers = new Layer[layers];
	}
	
	public Network(int[] iLayers, float ressistensTo){
		fitness = 0;
		layers = new Layer[iLayers.length];
		layers[0] = new Layer(iLayers[0], 0, ressistensTo);
		for(int i = 1; i < iLayers.length; i++){
			layers[i] = new Layer(iLayers[i], iLayers[i-1], ressistensTo);
		}
	}
	
	public void Simulate(float[] firstLayer){
		SetFirstLayer(firstLayer);
		for(int layer = 1; layer < layers.length; layer++){
			for(int synapse = 0; synapse < layers[layer].synapsen.length; synapse++){
				layers[layer].synapsen[synapse].value = layers[layer].synapsen[synapse].ressistens;
				for(int connection = 0; connection < layers[layer].synapsen[synapse].connections.length; connection++){
					layers[layer].synapsen[synapse].value += layers[layer-1].synapsen[connection].value * layers[layer].synapsen[synapse].connections[connection].strength;
				}
			}
		}
	}
	
	private void SetFirstLayer(float[] firstLayer){
		layers[0].Set(firstLayer);
	}
	
	public float[] GetLastLayer(){
		return layers[layers.length-1].Get();
	}
	
	public static void Sort(Network[] networks){
		for(int i = 0; i < networks.length-1; i++){
			for(int i2 = 0; i2 < networks.length-i-1; i2++){
				if(networks[i2].fitness < networks[i2+1].fitness){
					Switch(networks, i2, i2+1);
				}
			}
		}
	}
	
	private static void Switch(Network[] networks, int pos1, int pos2){
		Network save = networks[pos1];
		networks[pos1] = networks[pos2];
		networks[pos2] = save;
	}
}