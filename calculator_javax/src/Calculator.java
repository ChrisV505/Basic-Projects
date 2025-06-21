package src;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customThistle = new Color(224, 187, 228);
    Color customLavenderPurple = new Color(149, 125, 173);
    Color customPastelViolet = new Color(210, 145, 188);
    Color customCottonCandy = new Color(254, 200, 216);

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customLavenderPurple);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground((customLavenderPurple));
        frame.add(buttonsPanel);

        for(int i = 0; i< buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);

            if(Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customPastelViolet);
                button.setForeground(Color.white);
            } 
            else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customThistle);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customCottonCandy);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)) {

                    }
                    else if(Arrays.asList(topSymbols).contains(buttonValue)) {

                    } else { //digits or .                        
                        if(buttonValue == ".") {
                            if(!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(buttonValue);
                            }
                        } 
                        else if("0123456789".contains(buttonValue)) {
                            if(displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText().concat(buttonValue));
                            }
                        }
                    }


                }
            });

        }



        

    }


}
