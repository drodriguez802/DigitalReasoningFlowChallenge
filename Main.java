/**
 * @author Daniel Rodríguez García
 * Main class. Takes and stores input to then be processed
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {

	/*
	 * Takes the input and stores the two characters directions, beginning and end as if it were
	 * draw in a Cartesian plane. Stores both values in a HashMap using its Character as Key and 
	 * a an ArrayList of Point to store the two points. Then sends it to the Flow class using
	 * the find method.
	 */
	public static void main(String[] args) {
		String end = "end";
		ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
		HashMap<Character, ArrayList<Point>> map = new HashMap<>();
		boolean exit = true;
		int index = 0;
		while(exit){
			Scanner in = new Scanner(System.in);
			String line = in.nextLine();
			if(line.equals(end)){
				break;
			}
			for(int i=0; i<line.length();i++){
				char now = line.charAt(i);
				if(now!='_'){
					ArrayList<Point> list;
					if(!map.containsKey(now)){
						list = new ArrayList<>();						
						Point toAdd = new Point(i, index);
						list.add(toAdd);
						map.put(now, new ArrayList<>(list));
						list = null;
					}
					else if(map.get(now).size()==1){
						Point toAdd = new Point(i, index);
						list = new ArrayList<>(map.get(now));
						list.add(toAdd);
						map.put(now, new ArrayList<>(list));
						list=null;
					}
				}
			}
			ArrayList<Character> characters = new ArrayList<>();
			for(int i=0; i<line.length(); i++){
				characters.add(line.charAt(i));
			}
			matrix.add(characters);
			index++;
		}
		Flow send = new Flow(matrix);
		System.out.println(send.find(map));
	}
}
