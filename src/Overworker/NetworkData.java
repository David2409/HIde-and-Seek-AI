package Overworker;

import java.util.Random;

import Network.Connection;
import Network.Layer;
import Network.Network;

public class NetworkData {
	public LayerData[] layerDatas;
	
	public NetworkData(Network[] networks, float[] importance){
		layerDatas = new LayerData[networks[0].layers.length];
		layerDatas[0] = new LayerData(networks[0].layers[0].synapsen.length, 0);
		for(int i = 1; i < layerDatas.length; i++){
			layerDatas[i] = new LayerData(networks[0].layers[i].synapsen.length,networks[0].layers[i-1].synapsen.length);
		}
		for(int network = 0; network < importance.length; network++){
			for(int layer = 0; layer < networks[0].layers.length; layer++){
				for(int synapse = 0; synapse < networks[0].layers[layer].synapsen.length; synapse++){
					for(int connection = 0; connection < networks[0].layers[layer].synapsen[synapse].connections.length; connection++){
						layerDatas[layer].synapsenDatas[synapse].connectionData[connection].strenght += networks[network].layers[layer].synapsen[synapse].connections[connection].strength * importance[network];
					}
					layerDatas[layer].synapsenDatas[synapse].ressistans += networks[network].layers[layer].synapsen[synapse].ressistens * importance[network];
				}
			}
		}
	}
	
	public Network[] GenerateNetworks(OverworkData[] differenz, float ressistens){
		Random rand = new Random();
		Network[] networks = new Network[differenz.length];
		for(int network = 0; network < networks.length; network++){
			networks[network] = new Network(layerDatas.length);
			for(int layer = 0; layer < layerDatas.length; layer++){
				networks[network].layers[layer] = new Layer(layerDatas[layer].synapsenDatas.length, 0, 0);
				for(int synapse = 0; synapse < layerDatas[layer].synapsenDatas.length; synapse++){
					networks[network].layers[layer].synapsen[synapse].connections =  new Connection[layerDatas[layer].synapsenDatas[synapse].connectionData.length];
					for(int connection = 0; connection < layerDatas[layer].synapsenDatas[synapse].connectionData.length; connection++){
						networks[network].layers[layer].synapsen[synapse].connections[connection] = new Connection();
						networks[network].layers[layer].synapsen[synapse].connections[connection].strength = layerDatas[layer].synapsenDatas[synapse].connectionData[connection].strenght * ((1 - differenz[network].differenz/2) + rand.nextFloat() * differenz[network].differenz);
					}
					networks[network].layers[layer].synapsen[synapse].ressistens = layerDatas[layer].synapsenDatas[synapse].ressistans * ((1 - differenz[network].differenz/2) + rand.nextFloat() * differenz[network].differenz);
				}
			}
		}
		for(int network = 0; network < differenz.length; network++){
			for(int i = 1; i < differenz[network].randomRessistansChanges; i++);{
				int layer = rand.nextInt(networks[network].layers.length);
				if(layer == 0){
					layer = 1;
				}
				networks[network].layers[layer].synapsen[rand.nextInt(networks[network].layers[layer].synapsen.length)].ressistens = rand.nextFloat() * ressistens;
			}
			for(int i = 1; i < differenz[network].randomStrenghtChanges; i++);{
				int layer = rand.nextInt(networks[network].layers.length);
				if(layer == 0){
					layer = 1;
				}
				int synapse = rand.nextInt(networks[network].layers[layer].synapsen.length);
				networks[network].layers[layer].synapsen[synapse].connections[rand.nextInt(networks[network].layers[layer].synapsen[synapse].connections.length)].strength = rand.nextFloat();
			}
		}
		return networks;
	}
	
	public static float[] GetImportance(Network[] networks, int many){
		float[] importance = new float[many];
		int fitnessSum = 0;
		for (int i = 0; i < many; i++){
			fitnessSum += networks[i].fitness;
		}
		if(fitnessSum != 0){
			for (int i = 0; i < many; i++){
				importance[i] = networks[i].fitness / fitnessSum;
			}
		}
		return importance;
	}
}