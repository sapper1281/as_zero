/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 *
 * @author VVolgina
 */
public class Propetries {

    public static void main(String[] args) throws FileNotFoundException {
        Properties pr = new Properties();

            // определяем текущий каталог


            File currentDir = new File(".");

            try {

                
                String sFilePath = currentDir.getCanonicalPath() + File.separator + "news"+File.separator + "news.properties";
            String absolutePath = currentDir.getAbsolutePath();
            String path = currentDir.getPath();
            // создаем поток для чтения из файла

                FileInputStream ins = new FileInputStream(sFilePath);


                pr.load(ins);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String mes_new = pr.getProperty("developers");

        }
    }
