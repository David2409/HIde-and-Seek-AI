package general;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FloatFloatArray {
	public float[][] array;
	
	public FloatFloatArray(){
		
	}
	
	public FloatFloatArray(float[][] iArray){
		array = iArray;
	}
}