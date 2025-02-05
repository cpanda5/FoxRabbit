package animal;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import cell.Cell;

//一个类从多个父类得到继承叫作多继承
public class Fox extends Animal implements Cell {
    public Fox() {
        super(20,4);
    }

    // Fox实现了Cell接口，就必须去Override这个Cell中的所有函数，即实现Cell中所有的函数
    @Override
    public void draw(Graphics g, int x, int y, int size) {
        int alpha = (int)((1-getAgePercent())*255);
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(x, y, size, size);
    }

    @Override
    public Animal breed() {
        Animal ret = null;
        if ( isBreedable() && Math.random() < 0.05 ) {
            ret = new Fox();
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Fox:"+super.toString();
    }

    @Override
    public Animal feed(ArrayList<Animal> neighbour) {
        Animal ret = null;
        if ( Math.random()< 0.2 ) {
            ret =neighbour.get((int)(Math.random()*neighbour.size()));
            longerLife(2);
        }
        return ret;
    }
}
