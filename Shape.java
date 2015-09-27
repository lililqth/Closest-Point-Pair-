package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Shape {
    int x,y;//点的坐标
    int endX, endY;//终点坐标
    Color color;//点的颜色
    Stroke st;//画笔的粗细
    int kind; // 0:点 1：线
    public Shape(int kind, int x, int y, Color color, Stroke st)
    {
    	this.kind = 0;
    	this.x = x;
    	this.y = y;
    	this.color = color;
    	this.st = st;
    }
    public Shape(int kind, int startX, int startY, int endX, int endY, Color color, Stroke st)
    {
    	this.kind = 1;
    	this.x = startX;
    	this.y = startY;
    	this.endX = endX;
    	this.endY = endY;
    	this.color = color;
    	this.st = st;
    }
    public void drawShape(Graphics2D g)
    {
    	g.setStroke(this.st);	
		g.setColor(this.color);
		if(kind == 0)
		{
			g.drawOval(this.x, this.y, 10, 10);
		}
		if (kind == 1)
		{
			g.drawLine(x, y, endX, endY);
		}
    }
}


