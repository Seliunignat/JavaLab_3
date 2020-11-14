package bsu.rfe.java.group10.lab3.SELIUN.varC;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {
    private  String needle = null;

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
    private DecimalFormat formatterFloat = (DecimalFormat) NumberFormat.getInstance();

    private JCheckBox checkBox;

    public GornerTableCellRenderer()
    {

        formatter.setMaximumFractionDigits(8);
        formatter.setGroupingUsed(false);

        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);


        formatterFloat.setMaximumFractionDigits(8);
        formatterFloat.setGroupingUsed(false);

        DecimalFormatSymbols dottedFloat = formatter.getDecimalFormatSymbols();
        dottedFloat.setDecimalSeparator('.');
        formatterFloat.setDecimalFormatSymbols(dottedFloat);
        // Разместить надпись внутри панели
        //panel.add(label);
        // Установить выравнивание надписи по левому краю панели
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        checkBox = new JCheckBox("check", true);
        checkBox.setLocation(0, 0);
        checkBox.setSize(checkBox.getMaximumSize());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(column!=2) {
            // Преобразовать число в строку с помощью форматировщика
            String formattedDouble = formatter.format(value);
            // Установить текст надписи равным строковому представлению числа
            label.setText(formattedDouble);
            if (column == 1 && needle != null && needle.equals((formattedDouble))) {
                // Номер столбца = 1 (т.е. второй столбец)
                // + иголка не null (т.е. мы что-то ищем)
                // + значение иголки совпадает со значением ячейки таблицы -
                // окрасить задний фон панели в красный цвет
                //panel.setBackground(Color.RED);
                panel.removeAll();
                panel.add(checkBox);
            } else {
                panel.removeAll();
                panel.add(label);
                panel.setBackground(Color.WHITE);
            }
            return panel;
        }
        else
        {
            String formattedFloat = formatterFloat.format(value);
            label.setText(formattedFloat);
            panel.setBackground(Color.WHITE);
            panel.removeAll();
            panel.add(label);
            return panel;
        }
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }

    public String getNeedle() {
        return needle;
    }
}
