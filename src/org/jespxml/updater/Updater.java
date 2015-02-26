package org.jespxml.updater;

import java.util.ArrayList;
import java.util.List;

import org.jespxml.rw.XMLRead;
import org.jespxml.rw.XMLWriter;

/**
 *
 * @author Alasia Santiago y Coenda Francisco
 */
public class Updater {
	/**
	 * Variable hardcodeada que se utiliza para realizar pruebas de manera local
	 * con el parser. La versión final del código recibe como parámetro
	 * (externo, por consola) el path del directorio que debe leer.
	 */

	public static void main(String log_dir[]) {
		
		XMLRead Lector = new XMLRead();
		XMLWriter Escritor = new XMLWriter();		
		
		List<String> bug = new ArrayList<String>();
		bug=Lector.cocinaXML(log_dir[0]);
		Escritor.writeXML(bug);
		
	}
}