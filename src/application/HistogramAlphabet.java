package application;

import java.util.Optional;
import java.util.Random;
import java.lang.Math;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;




// COMPUTES PROBABILITIES AND FREQUENCY
public class HistogramAlphabet {
	Map<Character,Integer> frequency = new HashMap<Character,Integer>();
	Map<Character,Double> probability = new HashMap<Character,Double>();
	
	//Empty Constructor
	HistogramAlphabet(){
	}
	//Main Constructor
	HistogramAlphabet(String text){
		String w = text.replaceAll("[^a-zA-Z]", "");
		for(int i = 0; i< w.length();i++) {
			Character key = w.charAt(i);
			incrementFrequency(frequency, key);
		}
	}
	HistogramAlphabet(Map<Character,Integer> m){
		frequency.putAll(m);
	}
	
	public Map<Character,Integer> getFrequency(){return frequency;}
	public Integer getCumulativeFrequency() {return frequency.values().stream().reduce(0, Integer::sum);}
	
	// SORTING DECREASING OR INCREASING
	public Map<Character, Integer> sortUpFrequency(){
		return frequency.entrySet().stream().sorted(Map.Entry.comparingByValue())
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
	}
	
	public Map<Character, Integer> sortDownFrequency(){
		return frequency.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
	}
	
	//PROBABILITIES
	public Map<Character,Double> getProbability(){
		double inverseCumulativeFrequency = 1.0/getCumulativeFrequency();
		for(Character Key: frequency.keySet()) {
			probability.put(Key, (double) frequency.get(Key)* inverseCumulativeFrequency);
			
		}
		return probability.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
	}
	
	public Double getSumOfProbability() {return probability.values().stream().reduce(0.0, Double::sum);}
	
	public String toString() {
		String output= "Frequency of Characters:\n";
		for(Character K : frequency.keySet()) {
			output += K + ": " + frequency.get(K)+ "\n";
		}
		return output;
	}
	
	public static<K> void incrementFrequency(Map<K, Integer>m, K key) {
		m.putIfAbsent(key, 0);
		m.put(key, m.get(key)+1);
	}
		
	//**** INNER CLASS **** 
	public class MyPieChart{                        
		Map<Character,Slice> slices = new HashMap<Character,Slice>();
		
		int n;
		MyPoint center;
		int radius;
		double rotangle;
		double p =0;
		
		//Constructor
		MyPieChart(int n , MyPoint p, int r, double rotangle){
			this.n = n; this.radius = r; this.rotangle = Optional.ofNullable(rotangle).orElse(0.0); 
			this.center =p;
			probability = getProbability();
			slices = getMyPieChart();
		}
		
		//Create slice objects, put into map
		public Map<Character,Slice> getMyPieChart(){
			MyColor[] colors = MyColor.getMyColors();
			Random rand = new Random();
			int colorSize = colors.length;
			double startangle = rotangle;
			for(Character Key: frequency.keySet()) {  //Loop, ALPHABETICALY
				double angle = 360*probability.get(Key);
				slices.put(Key, new Slice(center,radius, startangle, angle,colors[rand.nextInt(colorSize)]));
				startangle+= angle;
			}
			
			return slices;
		}
		
		//draw slices
		public void draw(GraphicsContext GC) {
			Map<Character,Integer> sortedFrequency = getFrequency();
			
			int i =0 ; 	
			for(Character Key: sortedFrequency.keySet()) {
					if(i==n) { break;}
					else{
						slices.get(Key).draw(GC);
						label(GC, Key);
						p = p + getProbability().get(Key);
						i++;
					}			
			}
			
			// Slice and label for all other letters
			if(p<1) {	
				Slice s = new Slice(center,radius, (p*360) + rotangle,(360-(p*360)), MyColor.ALICEBLUE);
				s.draw(GC);
				GC.setFill(MyColor.BLACK.getFxColor());
				double a =((360-(p*360))/2+ p*360)+93;
				double x1 = (((radius*1.2)*Math.sin(((a+rotangle)*Math.PI)/180))+center.getX())-20;
				double y1 = (((radius*1.2)* Math.cos(((a+ rotangle)*Math.PI)/180))+center.getY())+10;			
				GC.fillText("All other letters:\n    "+ String.format("%.4f",1-p), x1, y1);
			}
		}
		
		//draw labels
		public void label(GraphicsContext GC, char k) {
			double r = radius*1.2;
	
			double a = (getMyPieChart().get(k).getAngle()/2+getMyPieChart().get(k).getStartAngle())+93;
			double x1 = ((r*Math.sin((a*Math.PI)/180))+center.getX())-20;
			double y1 = ((r* Math.cos((a*Math.PI)/180))+center.getY())+10;
			GC.setFill(MyColor.BLACK.getFxColor());
			GC.fillText(k+ " : "+ String.format("%.0f",(double)getFrequency().get(k)),x1 , y1); //DISPLAY FREQUENCY MAP DATA
		}
	}
}
