package bsu.rfe.java.group10.lab3.SELIUN.varC;

import javax.swing.table.AbstractTableModel;
import java.awt.*;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel
{
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients)
    {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return new Double(Math.ceil((to-from)/step)).intValue() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*rowIndex;
        if (columnIndex==0) {
            return x;
        } else if(columnIndex == 1){
            // Вычисление значения в точке по схеме Горнера.
            // Вспомнить 1-ый курс и реализовать
            // ...
            Double result = coefficients[coefficients.length - 1];
            Double slag;
            Double chlen = 0.0;
            for (int i = 0; i < coefficients.length - 1; i++)
            {
                slag = chlen + coefficients[i];
                chlen = slag * x;
            }
            result += chlen;
            return result;
        } else if(columnIndex == 2)
        {
            Float result = (float) (double)coefficients[coefficients.length - 1];
            Float slag;
            Float chlen = 0f;
            Float currentCoefficient;
            for (int i = 0; i < coefficients.length - 1; i++)
            {
                currentCoefficient = (float)(double)coefficients[i];
                slag = chlen + currentCoefficient;
                chlen = slag * (float) x;
            }
            result += chlen;
            return result;
        } else if (columnIndex == 3)
        {
            Double result = (double) getValueAt(rowIndex, 1) - (double) (float)getValueAt(rowIndex, 2);

            return result;
        }
        return null;
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex)
        {
            case 2:
                return Float.class;
            default:
                return Double.class;
        }

    }

    @Override
    public String getColumnName(int column) {
        switch (column)
        {
            case 0:
                return "Значение X";
            case 2:
                return "Значение многочлена Float";
            default:
                return "Значение многочлена";
        }
    }
}
