import java.net .*;
import java.io.*;
import java.util.Scanner;

public class ClienteTCP{
	public static void main(String args[]){

		try{
			int puerto = 5003;
			String palabras = "";
			Socket s = new Socket(args[0], puerto); //Ip destino y puerto Destino
			Scanner leer = new Scanner( System.in ); //Objeto de tipo Scanner que ocupamos para leer los datos que ingresa el usuario
			String name;
			DataInputStream entrada = new DataInputStream(s.getInputStream());
			System.out.println("Ingrese el nombre del archivo que desea enviar: ");
			name = leer.nextLine(); //Guardamos el nombre del archivo que el cliente quiere enviar
			int a;
			a = busqueda(name); //Buscamos el archivo.	
			 //Si el metodo devuelve 1 quiere decir que el archivo si existe
			if(a==1) {
				String ac = "archivos/";
				String b = ac+name;
				File file = new File(b);
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				OutputStream os = s.getOutputStream();
				//Read File Contents into contents array
				byte[] contents;
				long fileLength = file.length();
				long current = 0;
				long start = System.nanoTime();
				while(current!=fileLength){
					int size = 10000;
					if(fileLength - current >= size)
						current += size;
					else{
						size = (int)(fileLength - current);
						current = fileLength;
					}
					contents = new byte[size];
					bis.read(contents, 0, size);
					os.write(contents);
				}
				os.flush();
			}
			
			//De lo contrario, mostramos un mensaje diciendo que el archivo no existe
			else {
				System.out.println("El archivo solicitado no existe");
						
			}
			//palabras = entrada.readUTF();
			//System.out.println("El archivo enviado tiene "+ palabras + "palabras");
			s.close();
			

		}catch(Exception e){
			System.out.println("Socket: "+e.getMessage());
		}
		
	}

		public static int busqueda(String archivo){
			String files;	
			//Creamos un archivo file y le pasamos la direccion de donde buscara los archivos solicitados por los clientes
			File file = new File("archivos");
			//Creamos un array de archivos para almacenar todos los archivos existentes en la carpeta especifica
			File[] list = file.listFiles();
			//Hacemos un for para recorrer todos los archivos que se encuentran en la carpeta
			for(int i=0; i<list.length; i++){
				if(list[i].isFile()){
					files =list[i].getName();
				//Comparamos los archivos existentes con el que el cliente desea descargar
				if(archivo.equals(files)){
				//Si el archivo existe retornamos 1 y terminamos el metodo porque ya encontramos el archivo que el cliente desea
					return 1;
				}
			}
		}//Si no se encuentra el archivo que el cliente desea, retornamos 0
					return 0;
			}
}
