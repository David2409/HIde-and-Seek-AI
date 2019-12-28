package at.schaefer.david.general;

import at.schaefer.david.Overworker.OverworkData;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OverworkDataArray {
	public OverworkData[] array;
	
	public OverworkDataArray() {
		
	}
	
	public OverworkDataArray(OverworkData[] iArray){
		array = iArray;
	}
}