package org.jespxml.rw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author Alasia Santiago y Coenda Francisco
 * 
 *         Clase que se ocupa de leer archivos XML y extraer la información que
 *         sea necesaria.
 * */

public class XMLRead {

	// Variables
	private ArrayList<String> vectorBug;
	private File xmlAParsear;
	private File xmlAComparar;
	private File xmlAuxiliar;

	public XMLRead() {
		vectorBug = new ArrayList<String>();
	}

	/**
	 * Metodo para obtener el ultimo xml generado en el directorio.
	 * 
	 * @param path
	 *            : ruta absoluta del directorio
	 * 
	 * @return true En caso de obtener el ultimo archivo modificado de
	 *         directorio que nos interesa
	 * 
	 * */
	private boolean obtenerArchivo(String path) {

		File directorio = new File(path);
		File[] archivos = directorio.listFiles();
		
		/*
		 * Se ordena el arreglo archivos ya que el metodo listFiles() no
		 * garantiza que los path se vayan a encontrar ordenados.
		 */
		
		Arrays.sort(archivos);

		if (archivos.length == 0)
			return false;
		else {
			xmlAParsear = archivos[0];

			for (int i = 0; i < archivos.length; i++) {
				if (archivos[i].isFile()) {
					xmlAComparar = archivos[i];
					if (xmlAParsear.lastModified() < xmlAComparar
							.lastModified()) {
						xmlAuxiliar = xmlAParsear;
						xmlAParsear = xmlAComparar;
						String nombreArchivo = xmlAParsear.getName();
						/*
						 * Se garantia que la variable contenga el path del xml
						 * a parsearse y no de algún otro archivo
						 */
						if (nombreArchivo.equalsIgnoreCase("status.txt")) {
							xmlAParsear = xmlAuxiliar;
						}
					}
				}
			}
		}
		
		return true;// vector_header;
	}

	/**
	 * Metodo para extraer la información necesaria del xml y retornarla.
	 * 
	 * @param path: Directorio donde se encuentra el archivo a parsear
	 * @return String vectorBug: Arreglo de strings que contiene los datos del
	 *         primer bug de la builder , indicada por el archivo de log
	 *         correspondiente
	 * */
	public ArrayList<String> cocinaXML(String path) {

		if (obtenerArchivo(path)) {
			// Variables
			Element nodoRaiz;
			Element nodoRaizAuxiliar;
			List<Element> listaCabecera;
			List<Element> listaCuerpo;
			String cadena = "";

			SAXBuilder builder = new SAXBuilder(); // Se crea el SAXBuilder para
													// parsear el archivo

			try {

				/*
				 * Esta parte del código me permite obtener la cabecera del xml
				 * que genera cruise control
				 */

				Document documento = (Document) builder.build(xmlAParsear);// Se
																			// crea
																			// el
																			// documento
																			// a
																			// traves
																			// del
																			// archivo
																			// xml

				nodoRaiz = documento.getRootElement();// Se obtiene la raiz
														// 'cruisecontrol'
				nodoRaizAuxiliar = nodoRaiz.getChild("info");/*
															 * Obtengo el tag
															 * "info" y lo
															 * almaceno en un
															 * nodo auxiliar
															 * para poder
															 * trabajar los
															 * sub-tags que
															 * obtengo.
															 */
				listaCabecera = nodoRaizAuxiliar.getChildren("property");// Obtengo
																			// los
																			// nodos
																			// "property"
																			// del
																			// tag
																			// info.

				vectorBug.add(listaCabecera.get(0).getAttributeValue("value")
						.toString());
				vectorBug.add(listaCabecera.get(3).getAttributeValue("value")
						.toString());
				vectorBug.add(listaCabecera.get(5).getAttributeValue("value")
						.toString());
				vectorBug.add(listaCabecera.get(8).getAttributeValue("value")
						.toString());
				vectorBug.add(listaCabecera.get(9).getAttributeValue("value")
						.toString());

				/*
				 * Esta parte del código me permite obtener el error/incidente
				 * que se genera cuando cruisecontrol reporta una construcción
				 * fallida
				 */

				nodoRaizAuxiliar = nodoRaiz.getChild("build");
				listaCuerpo = nodoRaizAuxiliar.getChildren("message");

				for (int i = 0; i < listaCuerpo.size(); i++) {
					if (listaCuerpo.get(i).getAttributeValue("priority")
							.equalsIgnoreCase("error")) {
						if (!listaCuerpo
								.get(i)
								.getValue()
								.equalsIgnoreCase(
										"[ERROR] compilation terminated.")) {
							cadena = listaCuerpo.get(i).getValue();
						} else
							break;
					}
				}

				vectorBug.add(cadena);

			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return vectorBug;
	}
}
