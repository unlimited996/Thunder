package thunder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("雷电I");//创建一个JFrame对象，设置标题
        test test = new test();
        JLabel l=new JLabel(new ImageIcon("src/thunder/bj.jpg"));
        JLabel m=new JLabel(new ImageIcon("src/thunder/Bee.png"));
        //icon.setImage(icon.getImage().getScaledInstance(200, 300,Image.SCALE_DEFAULT ));
        test.add(l);//在窗口输出标签
        test.add(m);
        frame.add(test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗口关闭规则
        frame.setSize(480,780);//窗口大小
        frame.setLocationRelativeTo(null); //设置窗口相对于指定组件的位置，如果组件当前未显示或者为 null，则此窗口将置于屏幕的中央
        frame.setVisible(true); //窗口可视化,尽快调用paint方法
        //test.action();
    }

}

