package test;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindClosestPair {
	private JFrame frame;
	public static ArrayList<Point> pointList = new ArrayList<Point>(); //点列表
	public static PointPair ans = null;	//求解出的最近点对
	public static boolean randomFlag = false;	//求解出的最近点对
	static Point closestPairA = null; 
	static Point closestPairB = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindClosestPair window = new FindClosestPair();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FindClosestPair() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// 界面逻辑
		frame = new JFrame("程序测试");
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(5,5));
		//选点区域
		JPanel pTop = new JPanel(new BorderLayout(5,5));
		ArcsPanel arcs = new ArcsPanel();
		JButton jbt1 = new JButton("计算最近点对");
		jbt1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				FindClosestPair.randomFlag = false;
				FindClosestPair.pointList.clear();
				// 建立point列表
				for (Shape shape : ArcsPanel.graphList)
				{
					if (shape.kind == 0)
					{
						FindClosestPair.pointList.add(new Point(shape.x, shape.y));
					}
				}
				if (FindClosestPair.pointList.size() <= 1)
				{
					JOptionPane.showMessageDialog(null, "请输入至少两个点", "错误", JOptionPane.ERROR_MESSAGE); 
					return ;
				}
				NlognFind.findPair(true);
				//N2Find.findPair();
				for (Shape shape : ArcsPanel.graphList)
				{
					if (shape.kind == 1)
					{
						ArcsPanel.graphList.remove(shape);
						break;
					}
				}
				ArcsPanel.graphList.add(new Shape(1, (int)closestPairA.x,(int)closestPairA.y,
						(int)closestPairB.x, (int)closestPairB.y, Color.RED, new BasicStroke(1)));
				arcs.repaint();
			}
		});
		pTop.add("Center", arcs);
		pTop.add("South", jbt1);
		
		// 随机撒点Panel
		JPanel pBottom = new JPanel(new GridLayout(3, 1));
		TitledBorder title;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(blackline,
				"产生一百万个点,求最近点对");
		pBottom.setBorder(title);
		JButton jbt2 = new JButton("随机撒点100万个");
		jbt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FindClosestPair.randomFlag = true;
				FindClosestPair.pointList.clear();
				Random random = new Random(1000);
				for (int i=0;i<100000; i++)
				{
					double PointX = random.nextDouble()*2000;
					double PointY = random.nextDouble()*2000;
					FindClosestPair.pointList.add(new Point(PointX, PointY));
				}
			}
		});
		JButton jbt3 = new JButton("nlogn复杂度算法");
		jbt3.setName("nlogn");
		findMouseAdapter mouseAdapter =  new findMouseAdapter();
		jbt3.addMouseListener(mouseAdapter);
		JButton jbt4 = new JButton("n^2复杂度算法");
		jbt4.setName("n2");
		jbt4.addMouseListener(mouseAdapter);
		pBottom.add(jbt2);
		pBottom.add(jbt3);
		pBottom.add(jbt4);
		
		frame.getContentPane().add("Center", pTop);
		frame.getContentPane().add("South", pBottom);
		
		// 业务逻辑
		pointList.clear();
		
	}
}

class ArcsPanel extends JPanel {
	static ArrayList<Shape> graphList= new ArrayList<Shape>();
	public ArcsPanel() {
		//addMouseListener(new Mouse());
		addMouseListener(new Mouse());
		this.setSize(500, 300);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Shape sp : ArcsPanel.graphList)
		{
			sp.drawShape((Graphics2D)g);
		}
	}
}

class findMouseAdapter extends MouseAdapter{
	public void mousePressed(MouseEvent e) {
		if (!FindClosestPair.randomFlag)
		{
			JOptionPane.showMessageDialog(null, "请先随机撒点", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		long startTime = System.currentTimeMillis();
		if (((JButton) e.getSource()).getName().equals("n2"))
		{
			N2Find.findPair(false);
		}
		else if (((JButton) e.getSource()).getName().equals("nlogn"))
		{
			NlognFind.findPair(false);
		}
		long endTime = System.currentTimeMillis();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");   
		JOptionPane.showMessageDialog(null, "最近距离为"+ 
				df.format(Math.sqrt(FindClosestPair.ans.length))+"\n"+
						"共消耗时间：" + (endTime-startTime) + "ms",
						"已找到最近点对", JOptionPane.INFORMATION_MESSAGE);
	}
}

class Mouse extends MouseAdapter {
	private int startX = 0;
	private int startY = 0;
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getButton() == MouseEvent.BUTTON1) {
		    startX = arg0.getX();
		    startY = arg0.getY();
		    ArcsPanel p = (ArcsPanel) arg0.getSource();
			Graphics2D g = (Graphics2D)p.getGraphics();
			BasicStroke bs=new BasicStroke(3);//width是线宽,float型
			g.setStroke(bs);	
			g.setColor(Color.BLACK);
			g.drawOval(startX, startY, 10, 10);
			ArcsPanel.graphList.add(new Shape(0, startX, startY, Color.BLACK, bs));
			//FindClosestPair.pointList.add(new Point(startX, startY));
		}
	}
}

