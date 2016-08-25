/**
 * @author Daniel Rodríguez García
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Flow {
	ArrayList<ArrayList<Character>> matrix;
	ArrayList<ArrayList<Point>> trail;
	HashMap<Character, ArrayList<Point>> finalMap;
	
	public Flow(ArrayList<ArrayList<Character>> matrix){
		this.matrix = matrix;
		finalMap = new HashMap<>();
	}
	
	/**
	 * Calls the search method to find the path between points start and end corresponding to Character find.
	 * If two paths are found for the same Character it takes the shorter one.
	 * @param find Character to be found
	 * @param start Starting point
	 * @param end Ending point
	 */
	public void findPath(Character find, Point start, Point end){
		trail = new ArrayList<>();
		int smallest = 0;
		search(find, start, new ArrayList<Point>(), end);
		for(int i=0; i<trail.size();i++){
			if(trail.get(i).size()<trail.get(smallest).size()){
				smallest = i;
			}
		}
		finalMap.put(find, trail.get(smallest));
		trail = null;		
	}
	
	/**
	 * Verifies that all Characters have different paths, and that these paths
	 * do not cross each other at any time, making the puzzle unsolvable.
	 * @return True if the puzzle is solvable, False otherwise
	 */
	public boolean check(){
		Iterator<Character> iterator = finalMap.keySet().iterator();
		ArrayList<Point> check = new ArrayList<>();
		
		Character temp = iterator.next();
		while(temp!=null){
			for(int i=0;i<finalMap.get(temp).size();i++){
				if(check.contains(finalMap.get(temp).get(i))){
					return false;
				}
				else{
					check.add(finalMap.get(temp).get(i));
				}
			}
			try{
			temp = iterator.next();
			}
			catch(NoSuchElementException e){
				temp = null;
			}
		}		
		return true;
	}
	
	/**
	 * Principal method of the class, used to solve the complete puzzle,
	 * giving the search method the parameters to solve the path for a Character.
	 * @param map HashMap containing all the Characters to be solved and their
	 * start and ending points in the Cartesian plane
	 * @return "unsolvable" if two paths cross each other, nothing otherwise
	 */
	public String find(HashMap<Character, ArrayList<Point>> map){
		Iterator<Character> iterator = map.keySet().iterator();
		Character temp = iterator.next();
		while(temp!=null){
			findPath(temp, map.get(temp).get(0), map.get(temp).get(1));
			try{
			temp = iterator.next();
			}
			catch(NoSuchElementException e){
				temp = null;
			}
		}
		if(!check()){
			return "unsolvable";
		}
		print();
		return "";
	}
	
	/**
	 * If the puzzle passed all the other tests, meaning it had a solution, it prints
	 * the coordinates for the paths in the way of (Y, X) or (row, column) reversing the
	 * values of the way the class uses the Points as (X,Y) 
	 */
	private void print() {
		Iterator<Character> iterator = finalMap.keySet().iterator();
		Character temp = iterator.next();
		while(temp!=null){
			System.out.print(temp+" - ");
			for(int i=0;i<finalMap.get(temp).size();i++){
				System.out.print("("+(int)finalMap.get(temp).get(i).getY()+", "+(int)finalMap.get(temp).get(i).getX()+") ");
			}
			System.out.println();
			try{
			temp = iterator.next();
			}
			catch(NoSuchElementException e){
				temp = null;
			}
		}		
	}

	/**
	 * Recursive method that crawls the whole "matrix" to find if there is a path
	 * that has a distance 1 to his previous and next points and its not obstructed
	 * by any other Character.
	 * @param cara Character to find path for
	 * @param pivot Current point from where pivots right, down, left or up. First iteration uses the start point
	 * @param list List where all the points that create a successful path are stored
	 * @param toFind Ending point to find if possible
	 */
	public void search(Character cara, Point pivot, ArrayList<Point> list, Point toFind){
		ArrayList<Point> pass = new ArrayList<>(list);
		Character here;
		try{
		 here = matrix.get(pivot.y).get(pivot.x);
		}
		catch(NullPointerException e){
			 here = null;
		}
		catch(IndexOutOfBoundsException y){
			here =null;
		}
		if(here!=null&&(here.equals('_')||here.equals(cara))&&!pivot.equals(toFind)&&!pass.contains(pivot)){
			if(!pass.isEmpty()&&distance(pivot, pass.get(pass.size()-1))){
				pass.add(pivot);
				search(cara, new Point(pivot.x+1, pivot.y), pass, toFind);
				search(cara, new Point(pivot.x, pivot.y+1), pass, toFind);
				search(cara, new Point(pivot.x-1, pivot.y), pass, toFind);
				search(cara, new Point(pivot.x, pivot.y-1), pass, toFind);
			}
			else if(pass.isEmpty()){
				pass.add(pivot);
				search(cara, new Point(pivot.x+1, pivot.y), pass, toFind);
				search(cara, new Point(pivot.x, pivot.y+1), pass, toFind);
				search(cara, new Point(pivot.x-1, pivot.y), pass, toFind);
				search(cara, new Point(pivot.x, pivot.y-1), pass, toFind);
			}
			
		}
		else if(pass.size()>1&&distance(pivot, pass.get(pass.size()-1))&&here!=null&&pivot.equals(toFind)&&matrix.get(pass.get(pass.size()-1).y).get(pass.get(pass.size()-1).x).equals('_')){
			pass.add(pivot);
			trail.add(new ArrayList<>(pass));
		}
	}
	
	/**
	 * Calculates the distance between two points.
	 * @param a Previous point
	 * @param b Current point
	 * @return True if the distance is 1, False otherwise
	 */
	public boolean distance(Point a, Point b){
		return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y, 2))==1.0;
	}
	
	
}