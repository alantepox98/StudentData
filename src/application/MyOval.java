package application;
import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;

public class MyOval extends MyShape{
	MyPoint center;
	int width;
	int height;
	MyColor color;
	
	MyOval(MyPoint p, int w, int h, MyColor color){
		super(new MyPoint(), null);
		this.center =p; this.height = h; this.width = w;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public int getX() {return center.getX();}
	public int getY() {return center.getY();}
	public Integer getA() {return width/2 -getX();}
	public Integer getB() {return (width/2) + getX();}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public MyPoint getCenter() {return center;}
	@Override
	public double area() {
		return (width/2) *(height/2)* Math.PI;
	}
	
	@Override
	public double perimeter() {
		return (2 * Math.PI)* Math.sqrt((Math.pow(width/2, 2)+ Math.pow(height/2, 2))/2);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		int x = center.getX()- (width/2);
		int y = center.getY() -(height/2);
		
		gc.setFill(color.getFxColor());
		gc.fillOval(x,y, width, height);
	}
	public MyRectangle getMyBoundingRectangle() {
		MyPoint TLC = new MyPoint(center.getX()- (width/2),center.getY() -(height/2),MyColor.PURPLE);
		return new MyRectangle(TLC, width, height, MyColor.PURPLE);
	}
	public boolean pointInMyShape(MyPoint p) {
		int x = center.getX()- (width/2);
		int y = center.getY() -(height/2);
		MyPoint c = new MyPoint(x,y,null);
		if (width== height) {
			return center.distanceFrom(p) <=width/2;
		}
		else {
			int dx = center.getX() - p.getX();
			int dy = center.getY() - p.getY();
			return Math.pow((height/2) * dx , 2)+ Math.pow((width/2) * dy, 2) <= Math.pow((width/2)*(height/2), 2);
		}
	}
	@Override
	public String toString() {
		return "Oval Center ("+ center.getX()+", "+center.getY()+") Abscissa from x: -"+getA()+" to x: "+getB()+
				" Perimeter: "+ perimeter() + " Area: "+ area();
	}
}

