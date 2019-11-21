package general;

import java.io.StringBufferInputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;

public class XML {
	/*public static void main(String[] args) throws Exception{
		FloatFloatArray test = new FloatFloatArray(new float[][]{{1f,0,1f,0,1f},{1f,0,1f,0,1f},{1f,0,1f,0,1f},{1f,0,1f,0,1f},{1f,0,1f,0,1f}});
		System.out.println(XML.FloatArray2ToString(test));
		test = XML.StirngToFloatArray2(XML.FloatArray2ToString(test));
		for (float[] array : test.array) {
			for (float f : array) {
				System.out.print((int) f + " ");
			}
			System.out.println();
		}
	}*/
	
	public static String FloatArray2ToString(Object o) throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(o, sw);
		return sw.toString();
	}
	
	public static FloatFloatArray StirngToFloatArray2(String string) throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(FloatFloatArray.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (FloatFloatArray) jaxbUnmarshaller.unmarshal(new InputSource(new StringBufferInputStream(string)));
	}
}