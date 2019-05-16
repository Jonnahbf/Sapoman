import java.net.*;
import java.io.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class servidor {
	public static void main(String args[]) {
		try {
			int puertoServicio = 5003;
			ServerSocket escuchandoSocket = new ServerSocket(puertoServicio);
			while(true) {
				Socket socketCliente = escuchandoSocket.accept();
				Conexion c = new Conexion(socketCliente);
			}
			}
			catch(Exception exc) {
				System.out.println(exc.getMessage());
			}
		}
}
		class Conexion extends Thread {
			DataInputStream entrada;
			DataOutputStream salida;
			Socket socketCliente;
	
			public Conexion(Socket unSocketCliente) {
				try {
					socketCliente = unSocketCliente;
					entrada = new DataInputStream(socketCliente.getInputStream());
					salida = new DataOutputStream(socketCliente.getOutputStream());
					this.start();
				}
				catch(Exception exc) {
					System.out.println(exc.getMessage());
				}
			}
			public void run() {
				try {
					String a = "recibidos/";
					String b = a + "prueba.txt";
					byte[] contents = new byte[10000];
					FileOutputStream fos = new FileOutputStream(b);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					InputStream is = socketCliente.getInputStream();
					int bytesRead = 0;
					while((bytesRead=is.read(contents))!=-1)
					bos.write(contents, 0, bytesRead);
					bos.flush();
					String conteo = contarPalabras();
					System.out.println("El archivo tiene " + conteo + " palabras");
					//salida.writeUTF(conteo);
					//socketCliente.close();
				}
				catch(Exception exc) {
					System.out.println(exc.getMessage());
				}
			}

		
		public String contarPalabras(){
		        File archivo = null;
			String enteroString = "";
			try {
				archivo = new File("recibidos/prueba.txt");//"prueba.txt" es el archivo que nos envio el cliente
				String linea;
				FileReader fr = new FileReader (archivo);
				BufferedReader br = new BufferedReader(fr);
				int i,j,a=0;
				while((linea=br.readLine())!=null) {
					for(i=0;i<linea.length();i++){
						if(i==0){
							if(linea.charAt(i)!=' ')
		    					a++;
						   }
		   				else{
							if(linea.charAt(i-1)==' ')
							     if(linea.charAt(i)!=' ')	
							       a++;
		    
		   				}	
					}
				}
				fr.close();
				enteroString = Integer.toString(a);
				
			}
			catch(IOException a){
				System.out.println(a);
			}
			return enteroString;
		}

	}

