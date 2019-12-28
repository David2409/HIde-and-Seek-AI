package at.schaefer.david.Overworker;

public class SynapsenData {
	public ConnectionData[] connectionData;
	public float ressistans;
	
	public SynapsenData(int lenght){
		connectionData = new ConnectionData[lenght];
		for(int i = 0; i < connectionData.length; i++){
			connectionData[i] = new ConnectionData();
		}
	}
}
