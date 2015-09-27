package test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;

public class N2Find {
	static void findPair(boolean display) {
		double min = Double.MAX_VALUE;
		Point minA = null;
		Point minB = null;
		int length = FindClosestPair.pointList.size();
		Collections.sort(FindClosestPair.pointList, new SortByX());
		for (int i = 0; i < length; i++) {
			for (int j = i+1; j < length; j++) {
				Point a = FindClosestPair.pointList.get(i);
				Point b = FindClosestPair.pointList.get(j);
				double pointLength = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
				if (pointLength < min) {
					min = pointLength;
					minA = a;
					minB = b;
				}
			}
		}
		if (display)
		{
			FindClosestPair.closestPairA = minA;
			FindClosestPair.closestPairB = minB;
		}
		else
		{
			FindClosestPair.ans = new PointPair(min, minA, minB);
		}
	}
}

