package at.schaefer.david.Logic;

public class ArrrayTemplate {
	private float[] template;
	private float[] templateBig;
	private int[][] cockies;
	
	public ArrrayTemplate(float[] iTemplate, float[] iTemplateBig, int[][] iCockies) {
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
	public int GetChockie(int x, int y){
		return cockies[y][x];
	}
}