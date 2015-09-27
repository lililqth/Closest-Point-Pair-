package test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

class PointPair{
	double length;
	Point A;
	Point B;
	public PointPair(double min ,Point A, Point B)
	{
		this.length =min;
		this.A = A;
		this.B = B;
	}
}
public class NlognFind {
	
	//static ArrayList<Point> listYSort;
	//static ArrayList<Point> listXSort;
	public static void findPair(boolean display)
	{
		ArrayList<Point> list = FindClosestPair.pointList;
		Collections.sort(list, new SortByY());
		PointPair pair = find(list);
		if (display)
		{
			FindClosestPair.closestPairA = pair.A;
			FindClosestPair.closestPairB = pair.B;
		}
		else
		{
			FindClosestPair.ans = pair;
		}
	}
	
	static PointPair find(ArrayList<Point> list){	
		double min = Double.MAX_VALUE;
		Point minA = null;
		Point minB = null; 
		int n = list.size();
		if (n == 1)
		{
			return new PointPair(min,
					new Point(0, 0),new Point(0, 0));
		}
		/*�����������ģС��һ��ֵ��ʱ��ֱ�Ӽ���*/
		if (n < 4)
		{
			for (int i = 0; i < n; i++) {
				for (int j = i+1; j < n; j++) {
					Point a = list.get(i);
					Point b = list.get(j);
					double pointLength = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
					if (pointLength < min) {
						min = pointLength;
						minA = a;
						minB = b;
					}
				}
			}
			return new PointPair(min, minA, minB);
		}
		
		/*Ѱ����λ��*/
		
		double maxX = Double.MIN_VALUE;
		double minX = Double.MAX_VALUE;
		for (int i=0; i<n;i++)
		{
			if (maxX < list.get(i).x)
			{
				maxX = list.get(i).x;
			}
			if (minX > list.get(i).x)
			{
				minX = list.get(i).x;
			}
		}
		double middleLine = (maxX + minX) / 2;
		
		/*�ֱ��м����������߷�������*/
		ArrayList<Point> arrayL = new ArrayList<Point>();
		ArrayList<Point> arrayR = new ArrayList<Point>();
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i).x <= middleLine)
			{
				arrayL.add(list.get(i));
			}
			else
			{
				arrayR.add(list.get(i));	
			}
		}
		PointPair pairL= find(arrayL);
		PointPair pairR= find(arrayR);
		
		/*�ϲ�*/
		if (pairL.length < pairR.length)
		{
			min = pairL.length;
			minA = pairL.A;
			minB = pairL.B;
		}
		else
		{
			min = pairR.length;
			minA = pairR.A;
			minB = pairR.B;
		}
		double mind = Math.sqrt(min);
		ArrayList<Point> middleL = new ArrayList<Point>();
		ArrayList<Point> middleR = new ArrayList<Point>();
		
		/*Ѱ�����������ڵ�Ԫ��*/
		for (int i=0; i<arrayL.size(); i++)
		{
			if (arrayL.get(i).x > middleLine-mind )
			{
				middleL.add(arrayL.get(i));
			}
		}
		for (int i=0; i<arrayR.size(); i++)
		{
			if (arrayR.get(i).x < middleLine+mind )
			{
				middleR.add(arrayR.get(i));
			}
		}
		middleL.add(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE));
		middleR.add(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE));
		ArrayList<Point> middle = new ArrayList<Point>();
		// merge���ҵ�middle����
		for(int k=0,i=0,j=0; k < middleL.size()+middleR.size(); k++)
		{
			if (middleL.get(i).y < middleR.get(j).y)
			{
				middle.add(middleL.get(i++));
			}
			else if (middleR.get(j).y < middleL.get(i).y)
			{
				middle.add(middleR.get(j++));
			}
		}
		
		/*���Ϸ����7���ڵ�Ƚ�*/
		min = mind * mind;// min = mind ^ 2;
		for (int i=0; i<middle.size(); i++)
		{
			for (int j=i+1;j<middle.size() && j<i+8; j++)
			{
				Point a = middle.get(i);
				Point b = middle.get(j);
				if (Math.abs(middle.get(i).y - middle.get(j).y) <= mind)
				{
					double crossLength = (a.x - b.x)*(a.x - b.x) 
							+ (a.y - b.y)*(a.y - b.y);
					if (min > crossLength)
					{
						min = crossLength;
						minA = a;
						minB = b;
					}
				}
				else
				{
					continue;
				}
			}
		}
		return new PointPair(min, minA, minB);
	}
}
