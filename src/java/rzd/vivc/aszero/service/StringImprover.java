/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Собраны методы для модификации строковых переменных
 * @author VVolgina
 */
public class StringImprover {
    /**
     * Возвращает строку обрезанную до заданной длины.
     * @param str начальная строка
     * @param length заданная длина
     * @return обрезанная строка
     */
    public String getCutedString(String str, int length){
         if (str.length() <= length) {
           return str;
        } else{
            return str.substring(0, length-1);
        }
    }
    
    public String getDateString(Date dat){
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        return dat==null?"Для формирования даты необходимо ввести информацию по всем видам ресурсов":format.format(dat);
    }
    
    public int getHour(Date dat){
        SimpleDateFormat format=new SimpleDateFormat("hh");
        return dat==null?-1:new Integer(format.format(dat));
    }
    
    /**
     * Находит последние 2 цифры года в дате
     * @param dat дата для извлечения года
     * @return 2 цифры года
     */
    public byte getYear(Date dat){
        SimpleDateFormat format=new SimpleDateFormat("yy");
        return dat==null?-1:new Byte(format.format(dat));
    }
    
    /**
     * Находит номер месяца в дате
     * @param dat дата
     * @return 2 номер месяца
     */
    public byte getMonth(Date dat){
        SimpleDateFormat format=new SimpleDateFormat("MM");
        return dat==null?-1:new Byte(format.format(dat));
    }
    
    
    /**
     * Возвращает дату по номеру месяца и 2 последним цифрам года
     * @param month номер месяца
     * @param year цифры года
     * @return дата
     */
    public Date getDateByMonthAndYear(byte month, byte year){
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yy");
        DecimalFormat format1 =new DecimalFormat("00");
        try {
            return format.parse("01."+format1.format(month)+"."+year);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    /**
     * Возвращает дату по номеру месяца и 4 цифрам года
     * @param month номер месяца
     * @param year 4 цифры года
     * @return дата
     */
    public Date getDateByMonthAndFullYear(int month, int year){
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat format1 =new DecimalFormat("00");
        try {
            return format.parse("01."+format1.format(month)+"."+year);
        } catch (ParseException ex) {
            return null;
        }
    }
    
     /**
     * Возвращает строку с годом и месяцем даты
     * @param dat дата
     * @return год и месяц
     */
    public String getMonthAndYear(Date dat){
        SimpleDateFormat format=new SimpleDateFormat("MM.yyyy");
        return dat==null?"":format.format(dat);
    }
    
    public String formatForEXCEL(float val){
         DecimalFormat f = new DecimalFormat("#0.00");
         return Math.abs(val)<0.001?"":f.format(val);
    }
    
    public String formatMoneyForEXCEL(float val){
        DecimalFormat f = new DecimalFormat("#0.000");
         return Math.abs(val)<0.0001?"":f.format(val);
    }
}
