package trabalhopratico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class trabalhopratico {

    public static String procuraNome(String nome) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        String er_nome = "<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"pt\">([^<]+)</h1>";
        String er_nac = "<a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p1 = Pattern.compile(er_nome);
        //Pattern p3 = Pattern.compile(er_generos);
        Matcher m;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        ler.close();
        return null;
    }

    public static StringBuilder procuraDataNasc(String nome) throws IOException {
        String er = "<a href=\"/wiki/([^>]+)\" class=\"mw-redirect\" title=\"([^>]+)\">([^<]+)</a> de <a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p = Pattern.compile(er);
        StringBuilder data = new StringBuilder();  //ano de nascimento 

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                data.append(m.group(3)).append(" de ").append(m.group(6));
            }
        }
        ler.close();
        return data;
    }

    public static String procuraGeneros(String nome) throws FileNotFoundException, IOException { //SÓ FAZ 1 GÉNERO
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a></td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">(Romance histórico|Drama|Conto|Romance|Crônica|Fábula|Suspense|Thriller psicológico|Romance policial|Diálogo|Literatura gótica|Erotismo|Fantasia|Terror|Literatura gótica)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);
        String generos;

        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                linha = ler.nextLine();
                Matcher m1 = p1.matcher(linha);
                if (m1.find()) {
                    ler.close();
                    generos = m1.group(1);
                    return generos;
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procura_Nacionalidade(String nome) throws FileNotFoundException, IOException {  //FEITO!!
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Nacionalidade</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a></td>";

        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String nacionalidade;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p1.matcher(linha);
                    if (m2.find()) {
                        ler.close();
                        nacionalidade = m2.group(1);
                        nacionalidade = HttpRequestFunctions.capitalize(nacionalidade); //mete a 1ª letra da palavra capitalizada
                        return nacionalidade;
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procura_DataMorte(String nome) throws IOException { //FEITO!!
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Morte</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><span style=\"white-space:nowrap;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a> de <a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er);
        Pattern p2 = Pattern.compile(er1);
        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String morte;

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p2.matcher(linha);
                    if (m2.find()) {
                        morte = m2.group(1) + " de " + m2.group(2);
                        ler.close();
                        return morte;
                    } else {
                        ler.close();
                        return "ainda em actividade";
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procuraPremios(String nome) throws IOException {  //SÓ ENCONTRA O 1º PRÉMIO DE CADA AUTOR
        //ER1 PARA VER SE O AUTOR TEM PRÉMIOS OU NÃO 
        String er1 = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Pr[êé]mios</td>";

        String er2 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a> ([^<]+)<br />";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er1);
        Pattern p1 = Pattern.compile(er2);

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String linha;
        String premios;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {  //SE ENCONTRAR PRÉMIOS
                linha = ler.nextLine();
                Matcher m1 = p1.matcher(linha);
                if (m1.find()) {
                    ler.close();
                    premios = m1.group(1);
                    return premios;
                }
            }
        }
        return null;
    }

    public static String procuralink_foto(String nome) throws IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        String er = "<div class=\"floatnone\"><a href=\"/wiki/[^\"]*\" class=\"image\" title=\"[^\"]*\"><img alt=\"[^\"]*\" src=\"([^\"]*)\" width=\"[^\"]*\" height=\"[^\"]*\" srcset=\"[^\"]*\" data-file-width=\"[^\"]*\" data-file-height=\"[^\"]*\" /></a></div>";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));

        Pattern p = Pattern.compile(er);
        String res;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                res = m.group(1);
                return res;
            }
        }
        ler.close();
        return null;
    }

    public static Document adicionaAutor(Autor aux) {
        Element raiz;
        Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");

        if (doc == null) {
            raiz = new Element("autores"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }
        Element autor = new Element("autor");
        String id = String.valueOf(aux.getSequencia());
        Attribute a = new Attribute("id", id);
        autor.setAttribute(a);

        Element nome = new Element("nome").addContent(aux.getNome());
        autor.addContent(nome);

        String data = String.valueOf(aux.getData_nasc());
        Element data_nasc = new Element("data_nascimento").addContent(data);
        autor.addContent(data_nasc);

        String genero = String.valueOf(aux.getGeneros());
        Element generos = new Element("generos").addContent(genero);
        autor.addContent(generos);

        raiz.addContent(autor);

        return doc;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Autor a pesquisar: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();
        String aux = procuralink_foto(linha);
        System.out.println(aux);

        /*
         System.out.println("Nome: " + aux.getNome() + " Data: " + aux.getData_nasc() + " Generos: " + aux.getGeneros());
         Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");
         doc = criaElemento(aux, doc);
         XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml"); */
    }
}
