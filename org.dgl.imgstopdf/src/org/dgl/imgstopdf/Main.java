/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgl.imgstopdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {
        Document document = new Document();
        ArrayList<String> input;
        String output = ".\\out.pdf";
        try {
            FileOutputStream fos = new FileOutputStream(output);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
            Image image;
            JFileChooser fc = new JFileChooser(".\\");
            fc.setMultiSelectionEnabled(true);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES", "jpg", "jpeg", "gif", "png", "bmp");
            fc.setFileFilter(filter);
            int userOption = fc.showOpenDialog(null);
            if (userOption == JFileChooser.APPROVE_OPTION) {
                input = new ArrayList<String>();
                for (File f : fc.getSelectedFiles()) {
                    input.add(f.getAbsolutePath());
                }
                if (fc.getSelectedFile() != null) {
                    input.add(fc.getSelectedFile().getAbsolutePath());
                }
            } else {
                return;
            }
            if (input.size() == 0) {
                return;
            }
            pdfWriter.open();
            document.open();
            for (Object fileName : input.toArray()) {
                image = Image.getInstance((String) fileName);
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / image.getWidth()) * 100;
                image.scalePercent(scaler);
                document.add(image);
            }
            document.close();
            pdfWriter.close();
            JOptionPane.showMessageDialog(null, "OK", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString(), "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
