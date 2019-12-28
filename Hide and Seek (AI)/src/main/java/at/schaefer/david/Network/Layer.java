package at.schaefer.david.Network;

public class Layer {
	public Synapse[] synapsen;
	
	public Layer(int iSynapsen){
		synapsen = new Synapse[iSynapsen];
		for(int i = 0; i < iSynapsen; i++){
			synapsen[i] = new Synapse();
		}
	}
	
	public Layer(int iSynapsen, int layerBeforeLenght, float ressistensTo){
		synapsen = new Synapse[iSynapsen];
		for(int i = 0; i < iSynapsen; i++){
			synapsen[i] = new Synapse(layerBeforeLenght, ressistensTo);
		}
	}
	
	public void Set(float[] values){
		for(int i = 0; i < values.length; i++){
			synapsen[i].value = values[i];
		}
	}
	
	public float[] Get(){
		float[] values = new float[synapsen.length];
		for(int i = 0; i < synapsen.length; i++){
			values[i] = synapsen[i].value;
		}
		return values;
	}
}