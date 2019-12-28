package at.schaefer.david.general;

import java.io.StringBufferInputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;

public class XML {

	public static String Object2ToString(Object o) throws Exception{
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
	
	public static IntIntArray StringToIntArray2(String string) throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(IntIntArray.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (IntIntArray) jaxbUnmarshaller.unmarshal(new InputSource(new StringBufferInputStream(string)));
	}
	
	public static OverworkDataArray StringToOverworkDataArray(String string) throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(OverworkDataArray.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (OverworkDataArray) jaxbUnmarshaller.unmarshal(new InputSource(new StringBufferInputStream(string)));
	}
}