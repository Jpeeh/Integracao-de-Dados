/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author abs
 */

public class HttpRequestFunctions2 {
     public static void httpRequest1(String link, String pesquisa, String outFile) throws IOException{
        URL url;
        try {
            //System.out.println(pesquisa);
            if (!pesquisa.isEmpty()) {
               //System.out.println(link + URLEncoder.encode(pesquisa,"UTF-8").replace("+","_"));//.replace("", "ñ"));
               //url = new URL(link + URLEncoder.encode(pesquisa,"UTF-8").replace("+","_") + URLEncoder.encode(pesquisa,"UTF-8").replace("%C3%B1","ñ"));
               url = new URL(link + URLEncoder.encode(pesquisa,"UTF-8").replace(" ","+")); //alterar replace 
            }
            else{
               //Criar URL simples
               // System.out.println(link);
               url = new URL(link);
            }
       
            //System.out.println(url);
            URLConnection ligacao = url.openConnection();

            //Ver User-Agent actual de um determinado browser : http://whatsmyuseragent.com
            ligacao.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16");

            BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = in.readLine()) != null) {
                sb.append(linha)
                  .append(System.getProperty("line.separator"));
            }
            //Escrever num ficheiro
            BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
            out.write(sb.toString());
            
            out.close();
            in.close();

        } catch (MalformedURLException ex) {
            //Logger.getLogger(HttpRequestFunctions.class.getName()).log(Level.SEVERE, null, ex);
            BufferedWriter out = new BufferedWriter(new FileWriter("erro.txt"));
            out.write("escritor " + pesquisa +" nao existe");
            out.close();
        } catch (IOException ex) {
            //Logger.getLogger(HttpRequestFunctions.class.getName()).log(Level.SEVERE, null, ex)
            BufferedWriter out = new BufferedWriter(new FileWriter("erro.txt"));
            out.write("escritor " + pesquisa +" nao existe");
            out.close();
            
                   
        } 

    
    }
     
}
