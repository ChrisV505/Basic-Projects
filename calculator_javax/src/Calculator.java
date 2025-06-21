package src;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customThistle = new Color(224, 187, 228);
    Color customLavenderPurple = new Color(149, 125, 173);
    Color customPastelViolet = new Color(210, 145, 188);
    Color customCottonCandy = new Color(254, 200, 216);

    JFrame frame = new JFrame("Calculator");

    Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }


}
