package Logic;

public class ArrrayTemplate {
	private float[] template;
	private float[] templateBig;
	private float[][] cockies;
	
	public ArrrayTemplate(float[] iTemplate, float[] iTemplateBig, float[][] iCockies) {
		template = iTemplate;
		templateBig = iTemplateBig;
		cockies = iCockies;
	}
	
	public float[] Clone(){
		return template.clone();
	}
	
	public float[] CloneBig(){
		return templateBig.clone();
	}
	public float GetChockie(int x, int y){
		return cockies[y][x];
	}
}