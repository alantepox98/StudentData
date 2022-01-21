package application;
import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;

public class MyRectangle extends MyShape{
	MyPoint tlc;
	int width; 
	int height;
	MyColor color;
	
	MyRectangle(MyPoint p , int w, int h, MyColor color){ 
		super(new MyPoint(),null);
		this.tlc = p; this.width = w; this.height = h;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public MyPoint getTLC() {
		return tlc;
	}
	public int getW() {return width;}
	public int getH() {return height;}
	public MyColor getColor() {return color;}
	
	@Override
	public double area() {
		return width *height;
	}
	
	@Override
	public double perimeter() {
		return 2 * (width + height);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color.getFxColor());
		gc.fillRect(tlc.getX(), tlc.getY(), width, height);
	}
	public boolean pointInMyShape(MyPoint a) {
		return ((tlc.getX()<= a.getX() && a.getX()<=tlc.getX()+width) && (tlc.getY()<= a.getY() && a.getY()<= tlc.getY()+height));
	}
	public MyRectangle getMyBoundingRectangle() {
		return this;
	}
	@Override
	public String toString() {
		return "Rectangle Top Left Corner " + tlc +" Width: "+ width + " Height: " + height + " Perimeter: "
				+ perimeter() + " Area:  "+ area();
	}
	
}
