package com.zjy.Snack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snack extends JFrame {

    //食物
    private Point point = new Point();
    //蛇
    private LinkedList<Point> linkedList = new LinkedList<Point>();
    //记录键盘的值
    private int key=37;

    //初始化食物和蛇
    public void init(){
        point.setLocation(100,100);
        linkedList.add(new Point(300,300));
        linkedList.add(new Point(310,300));
        linkedList.add(new Point(320,300));
        linkedList.add(new Point(330,300));
        linkedList.add(new Point(340,300));
        linkedList.add(new Point(350,300));
        new Thread(new MoveThread()).start();
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(500,500);
        Graphics g2=image.getGraphics();//获取图片的画笔
        //设置背景颜色之类的
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,500,500);
        g2.translate(50,50);//设置要画的边框的起始位置
        //画框，边界框
        g2.setColor(Color.RED);
        g2.drawRect(0,0,400,400);

        //画食物
        g2.setColor(Color.RED);
        g2.fillRect(point.x,point.y,10,10);

        //画蛇，初始化为5个
        g2.setColor(Color.GREEN);
        for(Point p : this.linkedList){
            g2.fillRect(p.x,p.y,10,10);
        }

        g.drawImage(image,0,0,500,500,this);
    }

    //线程的实现
    class MoveThread implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //获取蛇的第一个点
                Point p = linkedList.getFirst().getLocation();
                switch (key){
                    case 37:
                        p.x-=10;
                        break;
                    case 38:
                        p.y-=10;
                        break;
                    case 39:
                        p.x+=10;
                        break;
                    case 40:
                        p.y+=10;
                        break;
                    default:
                }
                //判断蛇的位置
                if(p.x<0 || p.x>390 || p.y<0 || p.y>390 || linkedList.contains(p)){
                    JOptionPane.showMessageDialog(null,"game over");
                    return;
                }
                //向前移动一步，因为在上面的p的初始化中，p是copy而不是引用的
                linkedList.addFirst(p);

                //吃掉食物
                if(p.equals(point)){
                    int x=(int)(Math.random()*40)*10;
                    int y=(int)(Math.random()*40)*10;
                    System.out.println(x+" "+y);
                    point.setLocation(x,y);
                }else{
                    linkedList.removeLast();
                }
                Snack.this.repaint();
            }
        }
    }

    public Snack(){
        //窗口标题
        this.setTitle("贪吃蛇");
        //不能改变大小
        this.setResizable(false);
        //大小
        this.setSize(500,500);
        //点击关闭，程序停止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置居中显示
        this.setLocationRelativeTo(null);
        //设置为可见
        this.setVisible(true);

        //键盘的监听事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()>=37&&e.getKeyCode()<=40){
                    if(Math.abs(key-e.getKeyCode())!=2)
                        key=e.getKeyCode();
                }
            }
        });
        init();
    }


}
