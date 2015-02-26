package org.jespxml.rw;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.jespxml.JespXML;
import org.jespxml.modelo.Atributo;
import org.jespxml.modelo.Tag;

public class XMLWriter {
          
	private String bug_nombre = "";
	private int hola = 0;
          
	public void writeXML(List<String> bug) {
		try {
				
			JespXML archivo = new JespXML("/usr/local/maven-parser/xml/XML_Maven.xml");
			
			/*Se arma el nodo raiz del xml con los campos obligatrios que exige
			el modelo bugzilla.dtd*/
			Tag cabecera = new Tag("bugzilla"); 
			cabecera.addAtributo(new Atributo("version", "4.2.4"));
			cabecera.addAtributo(new Atributo("urlbase","/usr/local/bugzilla-4.2.4/bugzilla.dtd"));
			cabecera.addAtributo(new Atributo("maintainer",
					"piddef4211@gmail.com"));
			cabecera.addAtributo(new Atributo("exporter",
					"piddef4211@gmail.com"));
  

			/*Se arma el cuerpo del xml con los campos necesarios según modelo
			 * bugzilla.dtd*/
			Tag cuerpo = new Tag ("bug"); 

            Tag bug_id = new Tag ("bug_id");
            Tag creation_ts = new Tag ("creation_ts");
            Tag short_desc = new Tag ("short_desc");
            Tag delta_ts = new Tag ("delta_ts");
            Tag reporter_accessible = new Tag ("reporter_accessible");
            Tag cclist_accessible = new Tag ("cclist_accessible");
            Tag classification_id = new Tag ("classification");
            Tag classification = new Tag ("classification");
            Tag product = new Tag ("product"); 
            Tag component = new Tag ("component");
            Tag version = new Tag ("version");
            Tag bug_status = new Tag ("bug_status");
            Tag priority = new Tag ("priority"); 
            Tag bug_severity = new Tag ("bug_severity"); 
            Tag reporter= new Tag ("reporter"); 
           	
            //Se genera la descripción del bug.
            bug_nombre = bug.get(0) + ": " + bug.get(1) + "  " + bug.get(2);
            
            //Se agrega el contenido al cuerpo.
            bug_id.addContenido("");
            creation_ts.addContenido(bug.get(1));
            short_desc.addContenido(bug_nombre);
            delta_ts.addContenido(bug.get(1));
            reporter_accessible.addContenido("1");
            cclist_accessible.addContenido("1");
            classification_id.addContenido("1");
            classification.addContenido("Unclassified");
            product.addContenido(bug.get(0));
            component.addContenido(bug.get(0));
            version.addContenido("unspecified");
            bug_status.addContenido("CONFIRMED");
            priority.addContenido("---");
            bug_severity.addContenido("");
            reporter.addContenido("piddef4211@gmail.com");
            reporter.addAtributo(new Atributo("name","root"));
                
            //Se carga el mensage de error.
            Tag long_desc = new Tag ("long_desc");
            long_desc.addAtributo(new Atributo("isprivate", "0"));
            
            Tag commentid = new Tag ("commentid"); 
            Tag who = new Tag ("who"); 
            Tag bug_when = new Tag ("bug_when"); 
            Tag thetext = new Tag ("thetext");
            
            commentid.addContenido("");
            
            who.addContenido("piddef4211@gmail.com");
            who.addAtributo(new Atributo("name", "root"));
            
            bug_when.addContenido(bug.get(1));
            thetext.addContenido(bug.get(5));
            
            
            //Se arma el contenido de log_desc
            long_desc.addTagHijo(commentid);
            long_desc.addTagHijo(who);
            long_desc.addTagHijo(bug_when);
            long_desc.addTagHijo(thetext);

            
            //Se arma el resto del cuerpo de bug
			cuerpo.addTagHijo(bug_id);
			cuerpo.addTagHijo(creation_ts);
			cuerpo.addTagHijo(short_desc);
			cuerpo.addTagHijo(delta_ts);
			cuerpo.addTagHijo(reporter_accessible);
			cuerpo.addTagHijo(cclist_accessible);
			cuerpo.addTagHijo(classification_id);
			cuerpo.addTagHijo(classification);
			cuerpo.addTagHijo(product);
			cuerpo.addTagHijo(component);
			cuerpo.addTagHijo(version);
			cuerpo.addTagHijo(bug_status);
			cuerpo.addTagHijo(priority);
			cuerpo.addTagHijo(bug_severity);
			cuerpo.addTagHijo(reporter);
			cuerpo.addTagHijo(long_desc);

			//Se escribe el xml del incidente
			cabecera.addTagHijo(cuerpo);
			archivo.escribirXML(cabecera);
			
		} catch (ParserConfigurationException ex) {
			Logger.getLogger(org.jespxml.updater.Updater.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (TransformerConfigurationException ex) {
			Logger.getLogger(org.jespxml.updater.Updater.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(org.jespxml.updater.Updater.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (TransformerException ex) {
			Logger.getLogger(org.jespxml.updater.Updater.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}
}
