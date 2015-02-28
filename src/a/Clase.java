package a;

public class Clase {
	
	private String cadena1 = "";
	private String cadena2 = "";

	public void obtenerElementos (String elementos){
		cadena1 = elementos;
	}

	private String calculador(){
		return cadena1 + " Hola";
	}
	
}
