package bsu.rfe.java.group10.lab3.SELIUN.varC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class MainFrame extends JFrame {

    private int WIDTH = 700, HEIGHT = 500;

    private Double[] coefficients;

    private JTextField XFromTextField;
    private JTextField XToTextField;
    private JTextField StepTextField;

    private JButton solve;
    private JButton clearAll;

    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToCSVMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem aboutProgramMenuItem;
    private JMenuItem searchPolindromMenuItem;


    private Box hBoxResult;

    private Icon MyPhoto = new ImageIcon("C:\\Documents\\Programming\\Java\\JavaLab_3\\MyPhoto.jpg");

    // Визуализатор ячеек таблицы
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    // Модель данных с результатами вычислений
    private GornerTableModel data;


    public MainFrame(Double[] coefficients)
    {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients = coefficients;
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
        JMenu infoMenu = new JMenu("Справка");
        menuBar.add(infoMenu);

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
        // Добавить соответствующий пункт подменю в меню "Сохранить в текстовый файл"
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToTextMenuItem.setEnabled(false);

        Action saveToCSVAction = new AbstractAction("Сохранить в CSV файл") {
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
                    // CSV файл
                    saveToCSVFile(fileChooser.getSelectedFile());
            }
        };
        // Добавить соответствующий пункт подменю в меню "Сохранить в текстовый файл"
        saveToCSVMenuItem = fileMenu.add(saveToCSVAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToCSVMenuItem.setEnabled(false);

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
        // Добавить соответствующий пункт подменю в меню "Сохранить данные для построения графика"
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToGraphicsMenuItem.setEnabled(false);

        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                // Установить введенное значение в качестве иголки в визуализаторе
                renderer.setNeedle(value);
                // Обновить таблицу
                getContentPane().repaint();
            }
        };
        //Добавить действие в меню "Таблица"
        searchValueMenuItem = tableMenu.add(searchValueAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        searchValueMenuItem.setEnabled(false);



        Action searchPolindromAction = new AbstractAction("Найти палиндромы") {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setSearchPolindrom(true);
                getContentPane().repaint();
            }
        };
        searchPolindromMenuItem = tableMenu.add(searchPolindromAction);
        searchPolindromMenuItem.setEnabled(false);


        Action showInfoAction = new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Селюн И. А., 10 группа", "О программе", JOptionPane.NO_OPTION, MyPhoto);
            }
        };

        aboutProgramMenuItem = infoMenu.add(showInfoAction);
        searchValueMenuItem.setEnabled(true);

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

        getContentPane().add(xPreferencesBox, BorderLayout.NORTH);


        // Создать кнопку "Вычислить"
        JButton buttonCalc = new JButton("Вычислить");
        // Задать действие на нажатие "Вычислить" и привязать к кнопке
        buttonCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    // Считать значения границ отрезка, шага из полей ввода
                    Double from = Double.parseDouble(XFromTextField.getText());
                    Double to = Double.parseDouble(XToTextField.getText());
                    Double step = Double.parseDouble(StepTextField.getText());
                    // На основе считанных данных создать модель таблицы
                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
                    // Создать новый экземпляр таблицы
                    JTable table = new JTable(data);
                    // Установить в качестве визуализатора ячеек для класса
                    // Double разработанный визуализатор
                    table.setDefaultRenderer(Double.class, renderer);
                    table.getColumn("Значение многочлена Float").setCellRenderer(renderer);
                    // Установить размер строки таблицы в 30 пикселов
                    table.setRowHeight(30);
                    // Удалить все вложенные элементы из контейнера hBoxResult
                    hBoxResult.removeAll();
                    // Добавить в hBoxResult таблицу, "обѐрнутую" в панель с
                    // полосами прокрутки
                    hBoxResult.add(new JScrollPane(table));
                    // Обновить область содержания главного окна
                    getContentPane().validate();
                    // Пометить ряд элементов меню как доступных
                    saveToTextMenuItem.setEnabled(true);
                    saveToCSVMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                    searchPolindromMenuItem.setEnabled(true);
                }catch(NumberFormatException ex)
                {
                    // В случае ошибки преобразования показать сообщение об ошибке
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Создать кнопку "Очистить поля"
        JButton buttonReset = new JButton("Очистить поля");
        // Задать действие на нажатие "Очистить поля" и привязать к кнопке
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Установить в полях ввода значения по умолчанию
                XFromTextField.setText("0.0");
                XToTextField.setText("1.0");
                StepTextField.setText("0.1");
                // Удалить все вложенные элементы контейнера hBoxResult
                hBoxResult.removeAll();
                // Добавить в контейнер пустую панель
                hBoxResult.add(new JPanel());
                // Пометить элементы меню как недоступные
                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                // Обновить область содержания главного окна
                getContentPane().validate();
            }
        });

        // Поместить созданные кнопки в контейнер
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        // Установить предпочтительный размер области равным удвоенному
        // минимальному, чтобы при компоновке окна область совсем не сдавили
        hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2));
        // Разместить контейнер с кнопками в нижней (южной) области компоновки
        getContentPane().add(hboxButtons, BorderLayout.SOUTH);

        // Область для вывода результата пока что пустая
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
// Установить контейнер hBoxResult в главной области компоновки
        getContentPane().add(hBoxResult, BorderLayout.CENTER);



//Добавление всех коробок, в основное окно(контейнер)

    }

    private void saveToCSVFile(File selectedFile) {
        try {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
            formatter.setMaximumFractionDigits(8);
            formatter.setGroupingUsed(false);

            DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
            dottedDouble.setDecimalSeparator('.');
            formatter.setDecimalFormatSymbols(dottedDouble);

// Создать новый символьный поток вывода, направленный в указанный файл
            PrintStream out = new PrintStream(selectedFile);
            for (int i = 0; i < data.getRowCount(); i++)
            {
                for (int j = 0; j < data.getColumnCount();j++)
                {
                    out.print(formatter.format(data.getValueAt(i, j)));
                    if(j != data.getColumnCount() - 1)
                    {
                        out.print(",");
                    }
                }
                out.println("");
            }
// Закрыть поток
            out.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    protected void saveToTextFile(File selectedFile) {
        try {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
            formatter.setMaximumFractionDigits(8);
            formatter.setGroupingUsed(false);

            DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
            dottedDouble.setDecimalSeparator('.');
            formatter.setDecimalFormatSymbols(dottedDouble);

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
                out.println("Значение в точке " + formatter.format(data.getValueAt(i, 0)) +
                        " равно " + formatter.format(data.getValueAt(i, 1)));
            }
// Закрыть поток
            out.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    protected void saveToGraphicsFile(File selectedFile)
    {
        try
        {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
            formatter.setMaximumFractionDigits(8);
            formatter.setGroupingUsed(false);

            DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
            dottedDouble.setDecimalSeparator('.');
            formatter.setDecimalFormatSymbols(dottedDouble);

            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for(int i = 0; i < data.getRowCount(); i++)
            {
                // Записать в поток вывода значение X в точке
                out.writeDouble(Double.parseDouble(formatter.format(data.getValueAt(i,0))));
                // значение многочлена в точке
                out.writeDouble((Double)data.getValueAt(i,1));
            }
            out.close();
        }
        catch(Exception e)
        {
        }
    }

    public static void main(String[] args) {
	// write your code here
        if(args.length==0)
        {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного кэффициента!");
            System.exit(-1);
        }
        // Зарезервировать места в массиве коэффициентов столько,
        // сколько аргументов командной строки
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            // Перебрать все аргументы, пытаясь преобразовать их в Double
            for (String arg: args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        }
        catch (NumberFormatException ex) {
            // Если преобразование невозможно - сообщить об ошибке и завершиться
            System.out.println("Ошибка преобразования строки '" + args[i] +
                    "' в число типа Double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
