package thunder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("�׵�I");//����һ��JFrame�������ñ���
        test test = new test();
        JLabel l=new JLabel(new ImageIcon("src/thunder/bj.jpg"));
        JLabel m=new JLabel(new ImageIcon("src/thunder/Bee.png"));
        //icon.setImage(icon.getImage().getScaledInstance(200, 300,Image.SCALE_DEFAULT ));
        test.add(l);//�ڴ��������ǩ
        test.add(m);
        frame.add(test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ڹرչ���
        frame.setSize(480,780);//���ڴ�С
        frame.setLocationRelativeTo(null); //���ô��������ָ�������λ�ã���������ǰδ��ʾ����Ϊ null����˴��ڽ�������Ļ������
        frame.setVisible(true); //���ڿ��ӻ�,�������paint����
        //test.action();
    }

}

