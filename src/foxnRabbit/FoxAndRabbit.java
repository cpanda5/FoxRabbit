package foxnRabbit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import animal.Animal;
import animal.Fox;
import animal.Rabbit;
import cell.Cell;
import field.Field;
import field.Location;
import field.View;

public class FoxAndRabbit {
    private Field theField;
    private View theView;

    private class StepListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            step();
            theView.repaint();
        }
    }

    public FoxAndRabbit(int size) {
        theField = new Field(size, size);
        for ( int row = 0; row<theField.getHeight(); row++ ) {
            for ( int col = 0; col<theField.getWidth(); col++ ) {
                double probability = Math.random();
                if ( probability < 0.05 ) {
                    theField.place(row, col, new Fox());
                } else if ( probability < 0.15 ) {
                    theField.place(row, col, new Rabbit());
                }
            }
        }
        theView = new View(theField);
        JFrame frame = new JFrame(); // 图形窗口
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作，保证当点击图形窗口右上角的关闭键时能够结束程序
        frame.setResizable(false); // 设置窗口为不能改变大小
        frame.setTitle("Cells");
        frame.add(theView);
        JButton btnStep = new JButton("Step");
        frame.add(btnStep, BorderLayout.SOUTH);
        btnStep.addActionListener(new StepListener());  // 添加接口
//        btnStep.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                step();
//                theView.repaint();
//            }
//        });
        frame.pack(); // 自己决定窗口大小
        frame.setVisible(true); // 设置窗口可见

    }

    public void start(int steps) {
        for ( int i=0; i<steps; i++ ) {
            step();
            theView.repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void step() {
        for ( int row = 0; row< theField.getHeight(); row++ ) {
            for ( int col = 0; col < theField.getWidth(); col++ ) {
                Cell cell = theField.get(row, col);
                if ( cell != null ) {
                    Animal animal = (Animal)cell;

                    animal.grow();
                    if ( animal.isAlive() ) {
                        
                    	// move
                        Location loc = animal.move(theField.getFreeNeighbour(row, col));
                        if ( loc != null ) {
                            theField.move(row, col, loc);
                        }
                        
                        // eat
                        Cell[] neighbour = theField.getNeighbour(row, col);
                        ArrayList<Animal> listRabbit = new ArrayList<Animal>();
                        for ( Cell an : neighbour ) {
                        	// instanceof是Java的一个保留关键字，左边是对象，右边是类，返回类型是Boolean类型。
                        	//它的具体作用是测试左边的对象是否是右边类或者该类的子类创建的实例对象，
                        	//是，则返回true，否则返回false。                           
                        	if ( an instanceof Rabbit ) {
                                listRabbit.add((Rabbit)an);
                            }
                        }
                        if ( !listRabbit.isEmpty() ) {
                            Animal fed = animal.feed(listRabbit);
                            if ( fed != null ) {
                                theField.remove((Cell)fed);
                            }
                        }
                        
                        // breed
                        Animal baby = animal.breed();
                        if ( baby != null ) {
                            theField.placeRandomAdj(row, col, (Cell)baby);                     
                        }
                    } else {
                        theField.remove(row, col);

                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        FoxAndRabbit fnr = new FoxAndRabbit(30);
        // fnr.start(1000);
    }
}

