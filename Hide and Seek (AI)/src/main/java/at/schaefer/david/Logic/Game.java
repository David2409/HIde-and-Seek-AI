package at.schaefer.david.Logic;

public class Game {
	private float[][] field;
	private float[][] goolPos;
	private int playerPosX;
	private int playerPosY;
	private ArrrayTemplate template;
	
	public Game(float[][] iField, float[][] iGoolPos, int iPlayerPosX, int iPlayerPosY, int iWidth, ArrrayTemplate iTemplate) {
		field = iField;
		goolPos = iGoolPos;
		playerPosX = iPlayerPosX;
		playerPosY = iPlayerPosY;
		template = iTemplate;
	}
	
	public Game(float[][] iField, float[][] iGoolPos, int iPlayerPosX, int iPlayerPosY, ArrrayTemplate iTemplate) {
		field = iField;
		goolPos = iGoolPos;
		playerPosX = iPlayerPosX;
		playerPosY = iPlayerPosY;
		template = iTemplate;
	}
	
	private float[] GetField(float[][] inputField){
		int beginY = field.length - playerPosY;
		int beginX = field.length - playerPosX;
		float[] ouput = template.Clone();
		for (int y = 0; y < inputField.length; y++) {
			for (int x = 0; x < inputField.length; x++) {
				ouput[(beginY + y) * inputField.length + beginX + x] = inputField[y][x];
			}
		}
		return ouput;
	}
	
	public float[] GetObstacleField(){
		return GetField(field);
	}
	
	public float[] GetGoolField(){
		return GetField(goolPos);
	}
	
	public float[][] GetObstacleField2D(){
		return field;
	}
	
	public float[][] GetGoolField2D(){
		return goolPos;
	}
	
	public float[] GetInput(){
		int beginY = field.length - playerPosY;
		int beginX = field.length - playerPosX;
		float[] output = template.CloneBig();
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field.length; x++) {
				output[(beginY + y) * 2 * field.length + beginX + x] = field[y][x];
			}
		}
		
		beginY += field.length*2;
		
		for (int y = 0; y < goolPos.length; y++) {
			for (int x = 0; x < goolPos.length; x++) {
				output[(beginY + y) * 2 * goolPos.length + beginX + x] = goolPos[y][x];
			}
		}
		
		return output;
	}
	
	public boolean MoveUp(){
		if(playerPosY != 0 && field[playerPosY-1][playerPosX] != 1){
			playerPosY--;
			if(goolPos[playerPosY][playerPosX] == 1){
				return true;
			}
		}
		return false;
	}
	
	public boolean MoveDown(){
		if(playerPosY != field.length-1 && field[playerPosY+1][playerPosX] != 1){
			playerPosY++;
			if(goolPos[playerPosY][playerPosX] == 1){
				return true;
			}
		}
		return false;
	}
	
	public boolean MoveLeft(){
		if(playerPosX != 0 && field[playerPosY][playerPosX - 1] != 1){
			playerPosX--;
			if(goolPos[playerPosY][playerPosX] == 1){
				return true;
			}
		}
		return false;
	}
	
	public boolean MoveRight(){
		if(playerPosX != field.length-1 && field[playerPosY][playerPosX + 1] != 1){
			playerPosX++;
			if(goolPos[playerPosY][playerPosX] == 1){
				return true;
			}
		}
		return false;
	}
	
	public boolean Move(int move){
		switch (move) {
			case 0:
				return MoveLeft();
			case 1:
				return MoveUp();
			case 2:
				return MoveRight();
			default:
				return MoveDown();
		}
	}
	
	public int GetCockie(){
		return template.GetChockie(playerPosX, playerPosY);
	}
}