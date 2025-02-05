package cell;

import java.awt.Graphics;

/*
------------接口------------
•接口是纯抽象类
•接口所有的成员函数都是抽象函数，因此写代码时不必写上abstract
•接口所有的成员变量都是public static final
•普通的类表达的是一种具体的东西，接口表达的是概念和规范
•
*/
public interface Cell {
    void draw(Graphics g, int x, int y, int size);
}
