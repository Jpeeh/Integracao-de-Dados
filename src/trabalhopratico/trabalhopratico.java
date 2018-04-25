
package trabalhopratico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trabalhopratico {
    
     static ArrayList<Autor> procuraAutor(String nome) throws FileNotFoundException, IOException{
        ArrayList<Autor> lista = new ArrayList();
        String link = "https://pt.wikipedia.org/wiki/";
        
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");
        
        String er1 = "<li><i>([^<]+)</i>"; //([^<]+) Vai apanhar tudo até ao fim da tag HTML
        String er2 = "<li><i><a([\\s])href=\"/wiki/([a-zA-Z0-9\\_]+)\"([\\s])title=\"([^<]+)\">([^<]+)</a></i>";
        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS
        
        Pattern p1 = Pattern.compile(er1);
        Pattern p2 = Pattern.compile(er2);
        Matcher m1,m2;
        String linha;
        
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            m2 = p2.matcher(linha);
            while (m1.find() && m2.find()) {
                String nome1 = m1.group(0);
                String nome2 = m2.group(5);
                System.out.println("Obra:"+nome1+"Obra2:"+nome2);
                //Autor aut = new Autor(texto.toString());
                //lista.add(aut);
            }
        }
        return lista;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Autor a pesquisa: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();
        //linha = linha.toLowerCase();  // ALTERA OS ESPAÇOS PARA UNDERSCORES E METE TUDO EM LETRA MINUSCULA
        
        ArrayList<Autor> res = new ArrayList();
        res = procuraAutor(linha);

        System.out.println("Listagem de " + res.size() + " produtos encontrados");
        for (int i = 0; i < res.size(); i++) {
            System.out.println("Nome: " + res.get(i).getNome());
           }
    }
   }

