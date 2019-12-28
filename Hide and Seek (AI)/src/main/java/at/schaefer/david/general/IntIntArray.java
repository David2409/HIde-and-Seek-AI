package at.schaefer.david.general;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntIntArray {
	public int[][] array;
	
	public IntIntArray() {
		
	}
	
	public IntIntArray(int[][] iArray){
		array = iArray;
	}
}
