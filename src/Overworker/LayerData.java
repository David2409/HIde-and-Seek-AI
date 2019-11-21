package Overworker;

public class LayerData {
	public SynapsenData[] synapsenDatas;
	
	public LayerData(int lenght, int layerBeforeLenght){
		synapsenDatas = new SynapsenData[lenght];
		for (int i = 0; i < synapsenDatas.length; i++) {
			synapsenDatas[i] = new SynapsenData(layerBeforeLenght);
		}
	}
}