package bsu.rfe.java.group10.lab3.SELIUN.varC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {

    private int WIDTH = 1080, HEIGHT = 720;

    private Double[] coefficients;

    private JTextField XFromTextField;
    private JTextField XToTextField;
    private JTextField StepTextField;

    private JButton solve;
    private JButton clearAll;

    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;

    private Box hBoxResult;

    // Визуализатор ячеек таблицы
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    // Модель данных с результатами вычислений
    private GornerTableModel data;


    public MainFrame()
    {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);

        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser==null)
                {
                    // Если диалоговое окно "Открыть файл" ещѐ не создано,
                    // то создать его
                    fileChooser = new JFileChooser();
                    // и инициализировать текущей директорией
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    // Если результат его показа успешный, сохранить данные в
                    // текстовый файл
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        // Добавить соответствующий пункт подменю в меню "Файл"
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToTextMenuItem.setEnabled(false);

        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser==null)
                {
                    // Если диалоговое окно "Открыть файл" ещѐ не создано,
                    // то создать его
                    fileChooser = new JFileChooser();
                    // и инициализировать текущей директорией
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    // Если результат его показа успешный, сохранить данные в
                    // текстовый файл
                    saveToGraphicsFile(fileChooser.getSelectedFile());
            }
        };
        // Добавить соответствующий пункт подменю в меню "Файл"
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToGraphicsMenuItem.setEnabled(false);

        /*
        JMenuItem openFileMenuItem = fileMenu.add("Октрыть");
            openFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        */

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
        getContentPane().add(xPreferencesBox, BorderLayout.NORTH);

    }

    protected void saveToTextFile(File selectedFile) {
        try {
// Создать новый символьный поток вывода, направленный в указанный файл
            PrintStream out = new PrintStream(selectedFile);
// Записать в поток вывода заголовочные сведения
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for (int i = 0; i < coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" + (coefficients.length - i - 1));
                if (i != coefficients.length - 1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " +
                    data.getTo() + " с шагом " + data.getStep());
            out.println("================================================");
// Записать в поток вывода значения в точках
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println("Значение в точке " + data.getValueAt(i, 0) +
                        " равно " + data.getValueAt(i, 1));
            }
// Закрыть поток
            out.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    private void saveToGraphicsFile(File selectedFile)
    {
        try
        {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for(int i = 0; i < data.getRowCount(); i++)
            {
                // Записать в поток вывода значение X в точке
                out.writeDouble((Double)data.getValueAt(i,0));
                // значение многочлена в точке
                out.writeDouble((Double)data.getValueAt(i,0));
            }
            out.close();
        }
        catch(Exception e)
        {
        }
    }

    public static void main(String[] args) {
	// write your code here
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
