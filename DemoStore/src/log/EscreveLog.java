package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class EscreveLog {
	
	Path workingDirectory=Paths.get(".").toAbsolutePath();
	String separador =  File.separator;
	public String pathArquivoLog = workingDirectory.toString().replace(".","")  + "src" + separador + "evidencias" + separador;
	public String nomeArquivoLog;
	
	
	//Gera nome do arquivo de LOG
	public void geraNomeArquivoLog(){
		
		Locale locale = new Locale("pt","BR");
		GregorianCalendar calendar = new GregorianCalendar();
		Date horaAtual = calendar.getTime();
		
		//Gera nome do arquivo
		SimpleDateFormat dataHoraArquivoLog = new SimpleDateFormat("ddMMyyyyHHmmss", locale);
		nomeArquivoLog = dataHoraArquivoLog.format(horaAtual) + ".txt";
	}
	
	
	//Gera log de evidência
	public void logEvidencia(String tipoLog, String textoLog){
		
		Locale locale = new Locale("pt","BR");
		GregorianCalendar calendar = new GregorianCalendar();
		Date horaAtual = calendar.getTime();
		
		//Formato texto log
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH':'mm':'ss ", locale);
		String dataHora = formatador.format(horaAtual) + tipoLog + " - ";
		
		//Console
		System.out.println(dataHora + textoLog);
		//Log
		geraArquivoLog(dataHora + textoLog);
		
	}
	
	
	//Cria e Escreve arquivo de log
	public void criaEscreveArquivoLog(String textoLog, boolean arqExistente){
		
    	//Abre arquivo procurado
        //FileWriter arquivo;
		BufferedWriter arquivo = null;
		 
		try {
			
			if (arqExistente == true){
				//Arquivo existente
		    	arquivo = new BufferedWriter(new FileWriter(pathArquivoLog + nomeArquivoLog, true));
			}
			else if (arqExistente == false)
			{
				//Cria novo arquivo
		    	arquivo = new BufferedWriter(new FileWriter(pathArquivoLog + nomeArquivoLog));
			}
			
	        arquivo.write(textoLog);
	        arquivo.newLine();
	        arquivo.flush();
	        arquivo.close();
	        
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
            
	}
	
	
	//Gera arquivo de log
	public void geraArquivoLog(String textoLog){
		
		File diretorio = new File(pathArquivoLog); // ajfilho é uma pasta!
		
        //Valida a busca do arquivo
        boolean arquivoEncontrado = false;
		
		try { 
			if (!diretorio.exists()) {
			   
				diretorio.mkdir(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
	            
				//Cria e escreve arquivo
				criaEscreveArquivoLog(textoLog, false);
				
			} 
			else 
			{
				  
	            // diretório que será listado.
	            File dirLog = new File(pathArquivoLog);
	            
	            //obtem a lista de arquivos
	            File[] arquivos = dirLog.listFiles();
	            
	            //Localiza arquivo
	            for (int i = 0; i < arquivos.length; i++) {
	            	
	                File file = arquivos[i];
	                
	                if (file.getPath().endsWith(".txt")) {
	                	
	                    String nome = file.getName();
	                    
	                    if(nome.equals(nomeArquivoLog)){
	        				//Cria e escreve arquivo
	        				criaEscreveArquivoLog(textoLog, true);
		                    arquivoEncontrado = true;
		                    break;
	                    }
	                    
	                }
	            }
	            
	            //Cria arquivo caso não exista
	            if (arquivoEncontrado == false){
    				//Cria e escreve arquivo
    				criaEscreveArquivoLog(textoLog, false);
	            }
				
			}
		 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	
	//Cria XML utilizado pelo visualizador de evidencias
	public void geraXML(){
		
		//Arquivo existente
		try {
			
			BufferedWriter xml = new BufferedWriter(new FileWriter(pathArquivoLog + "modulo_" + nomeArquivoLog.replace("txt", "xml"), true));
			
			xml.write("<?xml version='1.0'?>");
			xml.write("<evidence>");
			xml.write("<module>EmulatorCommodity</module>");
			xml.write("<method>SendOrder</method>");
			xml.write("<log>" + pathArquivoLog + nomeArquivoLog + "</log>");
			xml.write("<screenshot>" + pathArquivoLog + nomeArquivoLog.replace("txt", "png") + "</screenshot>");
			xml.write("</evidence>");
			xml.flush();
			xml.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
