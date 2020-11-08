package bsu.rfe.java.group10.lab3.SELIUN.varC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private int WIDTH = 1080, HEIGHT = 720;

    private JTextField XFromTextField;
    private JTextField XToTextField;
    private JTextField StepTextField;

    private JButton solve;
    private JButton clearAll;

    private JMenuBar menuBar;

    public MainFrame()
    {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu nestedMenu = new JMenu("Экспортировать данные");
        fileMenu.add(nestedMenu);

        JMenuItem openFileMenuItem = fileMenu.add("Октрыть");
        openFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File("."));
        //int result = fileChooser.showSaveDialog(MainFrame.this);
        //File selectedFile = fileChooser.getSelectedFile();

        XFromTextField = new JTextField("0.0", 10);
        XToTextField = new JTextField("0.0", 10);
        StepTextField = new JTextField("0.0", 10);
        XFromTextField.setMaximumSize(XFromTextField.getPreferredSize());
        XToTextField.setMaximumSize(XToTextField.getPreferredSize());
        StepTextField.setMaximumSize(StepTextField.getPreferredSize());

        Box xPreferencesBox = Box.createHorizontalBox();
        xPreferencesBox.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        xPreferencesBox.add(Box.createHorizontalGlue());
        xPreferencesBox.add(new JLabel("X изменяется на интервале от:"));
        xPreferencesBox.add(Box.createHorizontalStrut(10));
        xPreferencesBox.add(XFromTextField);
        xPreferencesBox.add(Box.createHorizontalStrut(20));
        xPreferencesBox.add(new JLabel("до:"));
        xPreferencesBox.add(Box.createHorizontalStrut(10));
        xPreferencesBox.add(XToTextField);
        xPreferencesBox.add(Box.createHorizontalStrut(20));
        xPreferencesBox.add(new JLabel("с шагом:"));
        xPreferencesBox.add(Box.createHorizontalStrut(10));
        xPreferencesBox.add(StepTextField);
        xPreferencesBox.add(Box.createHorizontalGlue());
//Добавление всех коробок, в основное окно(контейнер)
        //Box contentBox = Box.createVerticalBox();
        //contentBox.add(myLayout);
        setJMenuBar(menuBar);
        getContentPane().add(xPreferencesBox, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
	// write your code here
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
