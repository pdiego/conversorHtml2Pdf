package com.conversor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.internet.HttpLeer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ConvertHTMLToPDF {

	private static final Logger log = Logger.getLogger( ConvertHTMLToPDF.class.getName() );
	
	 public static void main(String[] args) {

	  ConvertHTMLToPDF convertHTMLToPDF = new ConvertHTMLToPDF();
	  convertHTMLToPDF.crearPDF();
	  
	 }
	 
private void crearPDF() {
		
	try {
    	Date fechaActual = new Date();
    	DateFormat formatoFecha = new SimpleDateFormat("YYYYMMdd_HHmmss");
    	String sufijoFichero = formatoFecha.format(fechaActual);
    	String strFicheroSalida = "d:\\temp\\generado" + sufijoFichero + ".pdf";
    	String strFicheroAuxiliar = "d:\\temp\\aux" + sufijoFichero + ".html";
	    String url = "http://www.w3.org/";
		 
		Document document = new Document();
		
		PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(strFicheroSalida));
		document.open();
		   
		HttpLeer leido = new HttpLeer();
		String result = leido.GetPageContent(url);

	    if (result != null && !result.isEmpty()) {
	    
	    	File fichero = new File (strFicheroAuxiliar);
	    	
	    	BufferedWriter writerWriter = new BufferedWriter(new OutputStreamWriter(
	    			new FileOutputStream(strFicheroAuxiliar), "utf-8"));
	    	writerWriter.write(result);
	    	
	    	writerWriter.close();
	    
	    	InputStream inputstream = new FileInputStream(strFicheroAuxiliar);
	    	
	    	XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputstream);

	    	document.close();
	    	if (fichero.exists()) {
	    		System.out.println("Fichero auxiliar generado correctamente: " + strFicheroAuxiliar);
	    		log.log(Level.FINE, "Fichero auxiliar generado correctamente: " + strFicheroAuxiliar);
	    	}
	    		
	    } else {
	    	System.out.println(" ... No ha podido recuperar la URL...");
	    }
	}
	catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  } catch (DocumentException e) {
		   e.printStackTrace();
		  } 
	}
}
