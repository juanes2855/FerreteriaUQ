package Ferreteria.app.Persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Esta clase teine metodo estaticos que permite usarlos sin crear instancias de
 * la clase Lo que se hizo fue crear esta libreria para el manejo de los
 * archivos
 * 
 * @author Admin
 *
 */
public class ArchivoUtil {

	static String fechaSistema = "";

	/**
	 * Este metodo recibe una cadena con el contenido que se quiere guardar en
	 * el archivo
	 * 
	 * @param ruta
	 *            es la ruta o path donde esta ubicado el archivo
	 * @throws IOException
	 */
	public static void guardarArchivo(String ruta, String contenido, Boolean flagSobreEscribir) throws IOException {

		FileWriter fw = new FileWriter(ruta, flagSobreEscribir);
		BufferedWriter bfw = new BufferedWriter(fw);
		bfw.write(contenido);
		bfw.close();
		fw.close();
	}

	/**
	 * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la
	 * lista de cadenas.
	 * 
	 * @param ruta
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> leerArchivo(String ruta) throws IOException {

		ArrayList<String> contenido = new ArrayList<String>();
		FileReader fr = new FileReader(ruta);
		BufferedReader bfr = new BufferedReader(fr);
		String linea = "";
		while ((linea = bfr.readLine()) != null) {
			contenido.add(linea);
		}
		bfr.close();
		fr.close();
		return contenido;
	}

	public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
		String log = "";
		Logger LOGGER = Logger.getLogger(ArchivoUtil.class.getName());
		FileHandler fileHandler = null;

		cargarFechaSistema();

		try {

			fileHandler = new FileHandler(rutaArchivo, true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);

			switch (nivel) {
			case 1:
				LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			case 2:
				LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			case 3:
				LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
				break;

			default:
				break;
			}

		} catch (SecurityException e) {

			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {

			fileHandler.close();
		}

	}

	private static void cargarFechaSistema() {

		String diaN = "";
		String mesN = "";
		String añoN = "";

		Calendar cal1 = Calendar.getInstance();

		int dia = cal1.get(Calendar.DATE);
		int mes = cal1.get(Calendar.MONTH) + 1;
		int año = cal1.get(Calendar.YEAR);
		int hora = cal1.get(Calendar.HOUR);
		int minuto = cal1.get(Calendar.MINUTE);
		int segundo = cal1.get(Calendar.SECOND);

		if (dia < 10) {
			diaN += "0" + dia;
		} else {
			diaN += "" + dia;
		}
		if (mes < 10) {
			mesN += "0" + mes;
		} else {
			mesN += "" + mes;
		}

		// fecha_Actual+= año+"-"+mesN+"-"+ diaN;
		// fechaSistema = año+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;

		fechaSistema = año + "-" + mesN + "-" + diaN;

		// horaFechaSistema = hora+"-"+minuto;
	}

	public static String cargarFechaSistema2() {

		String diaN = "";
		String mesN = "";
		String añoN = "";
		String horaN = "";
		String segundoN= "";
		String minutoN= "";
		
		Calendar cal1 = Calendar.getInstance();

		int dia = cal1.get(Calendar.DATE);
		int mes = cal1.get(Calendar.MONTH) + 1;
		int año = cal1.get(Calendar.YEAR);
		int hora = cal1.get(Calendar.HOUR_OF_DAY);
		int minuto = cal1.get(Calendar.MINUTE);
		int segundo = cal1.get(Calendar.SECOND);

		if (dia < 10) {
			diaN += "0" + dia;
		} else {
			diaN += "" + dia;
		}
		if (mes < 10) {
			mesN += "0" + mes;
		} else {
			mesN += "" + mes;
		}
		if(año<10){
			añoN += "0"+año;
		}else{
			añoN+= ""+año;
		}
		if(hora<10){
			horaN += "0"+hora;
		}else{
			horaN+= ""+hora;
		}
		if(minuto<10){
			minutoN += "0"+minuto;
		}else{
			minutoN+= ""+minuto;
		}
		if(segundo<10){
			segundoN += "0"+segundo;
		}else{
			segundoN+= ""+segundo;
		}

		return fechaSistema = diaN + mesN + añoN + "_" + horaN + "_" + minutoN + "_" + segundoN;

	}

	// ------------------------------------SERIALIZACIÓN y XML
	/**
	 * Escribe en el fichero que se le pasa el objeto que se le envia
	 * 
	 * @param rutaArchivo
	 *            path del fichero que se quiere escribir
	 * @throws IOException
	 */

	@SuppressWarnings("unchecked")
	public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
		Object aux = null;
		// Empresa empresa = null;
		ObjectInputStream ois = null;
		try {
			// Se crea un ObjectInputStream
			ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

			aux = ois.readObject();

		} catch (Exception e2) {
			throw e2;
		} finally {
			if (ois != null)
				ois.close();
		}
		return aux;
	}

	// public static void generarRespaldo(String ruta, Object object, String
	// rutaArchivoBin, String rutaXml) throws Exception{
	// String fecha = cargarFechaSistema2();
	// String rutaTotal=ruta+fecha+".txt";
	//
	// ObjectOutputStream respaldo = null;
	//
	// try{
	// respaldo = new ObjectOutputStream(new FileOutputStream(rutaTotal));
	// respaldo.writeObject(object);
	// }
	//
	//
	// }

	public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws Exception {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
			oos.writeObject(object);
		} catch (Exception e) {
			throw e;
		} finally {
			if (oos != null)
				oos.close();
		}
	}

	public static void generarRespaldoBinario(String ruta, Object object) throws Exception {
		ObjectOutputStream respaldo = null;
		try {
			respaldo = new ObjectOutputStream(new FileOutputStream(ruta));
			respaldo.writeObject(object);
		} catch (Exception e) {
			throw e;
		} finally {
			if (respaldo != null)
				respaldo.close();
		}
	}

	public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

		XMLDecoder decodificadorXML;
		Object objetoXML;

		decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
		objetoXML = decodificadorXML.readObject();
		decodificadorXML.close();
		return objetoXML;

	}

	public static void generarRespaldoXml(String ruta, Object object) throws Exception {
		XMLEncoder codificadorXML;

		codificadorXML = new XMLEncoder(new FileOutputStream(ruta));
		codificadorXML.writeObject(object);
		codificadorXML.close();
	}

	public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

		XMLEncoder codificadorXML;

		codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
		codificadorXML.writeObject(objeto);
		codificadorXML.close();

	}

}
