package Cloest_Point_Pair;

import java.util.Comparator;


class SortByX implements Comparator {
	public int compare(Object o1, Object o2) {
		Point s1 = (Point) o1;
		Point s2 = (Point) o2;
		if (s1.x > s2.x) {
			return 1;
		}
		return -1;
	}
}
class SortByY implements Comparator {
	public int compare(Object o1, Object o2) {
		Point s1 = (Point) o1;
		Point s2 = (Point) o2;
		if (s1.y > s2.y) {
			return 1;
		}
		return -1;
	}
}