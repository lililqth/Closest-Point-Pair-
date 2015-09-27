package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Shape {
    int x,y;//�������
    int endX, endY;//�յ�����
    Color color;//�����ɫ
    Stroke st;//���ʵĴ�ϸ
    int kind; // 0:�� 1����
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


