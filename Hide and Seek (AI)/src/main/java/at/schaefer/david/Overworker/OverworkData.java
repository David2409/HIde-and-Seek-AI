package at.schaefer.david.Overworker;

import at.schaefer.david.Network.Network;

public class OverworkData {
	public float differenz;
	public byte randomStrenghtChanges;
	public byte randomRessistansChanges;
	
	public OverworkData(float differenz, byte randomStrenghtChanges, byte randomRessistansChanges) {
		this.differenz = differenz;
		this.randomStrenghtChanges = randomRessistansChanges;
		this.randomRessistansChanges = randomRessistansChanges;
	}
}