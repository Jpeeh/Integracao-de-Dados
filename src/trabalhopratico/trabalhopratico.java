package trabalhopratico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.xml.sax.SAXException;

//IMPLEMENTAR MAIS FUNÇÕES PARA ALTERAR OS DADOS DOS AUTORES E DAS OBRAS (NO .XML)
//IMPLEMENTAR MAIS PESQUISAS XPATH !
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

    public static String procuraDataNasc(String nome) throws IOException { //FUNCIONA SÓ PARA "OSCAR WILDE"
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Nascimento</td>";
        String er1 = "[<span style=\"white-space:nowrap;\">]?<a href=\"/wiki/[^\"]+#Nascimentos\" title=\"[^\"]+\">([^<]+)</a> de <a href=\"/wiki/[^\"]+\" title=\"[^\"]+\">([^<]+)</a>[[^<]+</span>]?<br />";        //STRING ER1 PRECISA DE SER CORRIGIDA, SÓ ESTÁ A DAR VALORES CORRCETOS PARA O AUTOR 'PABLO AUSTER' !!

        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);
        String data;

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p1.matcher(linha);
                    if (m2.find()) {
                        ler.close();
                        data = m2.group(1) + " de " + m2.group(2);
                        return data;
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procuraGeneros(String nome) throws FileNotFoundException, IOException { //SÓ FAZ 1 GÉNERO
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">Género literário</a></td>";
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

    public static String procura_Nacionalidade(String nome) throws FileNotFoundException, IOException {  //NÃO DÁ PARA "PAUL AUSTER"
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Nacionalidade</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"([^\"]+)\">";

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

    public static void adicionaAutor(Autor aux) {
        Element raiz;
        Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");

        if (doc == null) {
            raiz = new Element("Autores"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element autor = new Element("Autor");
        String id = Integer.toString(aux.getSequencia());
        Attribute a = new Attribute("id", id);
        autor.setAttribute(a);

        Element nome = new Element("Nome").addContent(aux.getNome());
        autor.addContent(nome);

        Element data_n = new Element("Data_Nascimento").addContent(aux.getData_nasc());
        autor.addContent(data_n);

        Element data_m = new Element("Data_Falecimento").addContent(aux.getData_morte());
        autor.addContent(data_m);

        Element nacionalidade = new Element("Nacionalidade").addContent(aux.getNacionalidade());
        autor.addContent(nacionalidade);

        Element generos = new Element("Generos").addContent(aux.getGeneros());
        autor.addContent(generos);

        Element link_foto = new Element("Link_Foto").addContent(aux.getLink_foto());
        autor.addContent(link_foto);

        raiz.addContent(autor);
        XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml");
    }

    public static void leituraWiki(String linha) throws IOException {
        String enome, d_nasc, d_morte, nac, gen, prem, local_nasc, link_foto;
        int id = 1;
        enome = procuraNome(linha);
        d_nasc = procuraDataNasc(linha);
        d_morte = procura_DataMorte(linha);
        nac = procura_Nacionalidade(linha);
        gen = procuraGeneros(linha);
        prem = procuraPremios(linha);
        //local_nasc = procura_localNasc(linha);
        link_foto = procuralink_foto(linha);

        Autor aux = new Autor(id, enome, d_nasc, d_morte, nac, gen, prem, link_foto);
        System.out.println(aux.getNome() + "\t" + aux.getData_nasc() + "\t" + aux.getData_morte() + "\t" + aux.getNacionalidade() + "\t" + aux.getGeneros() + "\t" + aux.getLink_foto());
        adicionaAutor(aux);
        id++;
    }

    public static void leituraWook(String linha) throws IOException {
        String pesquisa, enome, isbnw, autorw, editorw, precow, cod_autorw, titulow, anow;
        ArrayList<String> link = new ArrayList();

        link = procuraLinkWook(linha);
        if (link.size() > 0) {
            for (int i = 0; i < link.size(); i++) {
                autorw = linha;
                cod_autorw = procuraCod_Autor(linha);
                isbnw = procuraISBN(link.get(i));
                editorw = procuraEditor(link.get(i));
                precow = procuraPreco(link.get(i));
                titulow = procuraTitulo(link.get(i));
                anow = procuraAno(link.get(i));

                Obra aux = new Obra(cod_autorw, autorw, titulow, isbnw, anow, editorw, precow);
                System.out.println(aux.getAutor() + " , " + aux.getCod() + " , " + aux.getTitulo() + " , " + aux.getIsbn()
                        + " , " + aux.getAno() + " , " + aux.getEditor() + " , " + aux.getPreco());
                adicionaObra(aux);
            }
        } else {
            System.out.println("Não existem livros!\n");
        }
    }

    public static ArrayList<String> procuraLinkWook(String nome) throws FileNotFoundException, IOException {
        String link = "https://www.wook.pt/pesquisa/";
        ArrayList<String> res = new ArrayList();

        String er = "<a class=\"title-lnk\" href=\"([^\"]+)\"#productPageSectionComments>";
        HttpRequestFunctions2.httpRequest1(link, nome, "obras.html");

        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                res.add(m.group(1));
            }
        }

        ler.close();
        return res;
    }

    public static String procuraISBN(String nome) throws FileNotFoundException, IOException {
        String link = "https://www.wook.pt" + nome;
        String pesquisa = "";
        String er = "<span id=\"productPageSectionDetails-collapseDetalhes-content-isbn\" class=\"col-xs-12\">ISBN: <span class=\"info\">([^<]+)</span></span>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        ler.close();
        return null;
    }

    public static String procuraCod_Autor(String nome) throws IOException {  //RETORNA O VALOR NUMÉRICO QUE ESTÁ NO LINK DA PÁGINA DO AUTOR NO WOOK
        String link = "https://www.wook.pt/pesquisa/";
        String er = "de <a href=\"/autor/[^/]+/([^\"]+)\">[^<]+</a>(?:[^<])*</div>";

        HttpRequestFunctions2.httpRequest1(link, nome, "obras.html");

        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraPreco(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<div class=\"[^\"]*\" id=\"[^\"]*\">([^<]+)</div>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraAno(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<span id=\"[^\"]*\" class=\"[^\"]*\">Edição ou reimpressão: <span  class=\"[^\"]*\">([^<]+)</span></span>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        ler.close();
        return null;
    }

    public static String procuraTitulo(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<h1   id=\"productPageRightSectionTop-title-h1\">([^<]+)</h1>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraEditor(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<span  class=\"col-xs-12\">Editor: <span  class=\"info\">([^<]+)</span>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static void adicionaObra(Obra aux) {
        Element raiz;
        Document doc = XMLJDomFunctions.lerDocumentoXML("obras.xml");

        if (doc == null) {
            raiz = new Element("Obras"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element obra = new Element("Livro");

        Element cod_autor = new Element("Codigo_Autor").addContent(aux.getCod());
        Element autor = new Element("Autor").addContent(aux.getAutor());
        Element titulo = new Element("Titulo").addContent(aux.getTitulo());
        Element isbn = new Element("ISBN").addContent(aux.getIsbn());
        Element ano = new Element("Ano").addContent(aux.getAno());
        Element preco = new Element("Preco").addContent(aux.getPreco());
        Element editor = new Element("Editor").addContent(aux.getEditor());
        obra.addContent(cod_autor);
        obra.addContent(autor);
        obra.addContent(titulo);
        obra.addContent(isbn);
        obra.addContent(ano);
        obra.addContent(preco);
        obra.addContent(editor);
        raiz.addContent(obra);

        XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "obras.xml");
    }

    public static void validaDocumentoDTD(String xmlFile, String DTDFile) throws IOException, SAXException {
        Document doc = XMLJDomFunctions.lerDocumentoXML(xmlFile);
        File f = new File(DTDFile);

        if (doc != null && f.exists()) { //DTD e XML existem
            Element raiz = doc.getRootElement();
            //Atribuir DTD ao	ficheiro XML
            DocType dtd = new DocType(raiz.getName(), DTDFile);
            doc.setDocType(dtd);

            //Gravar o XML com as alterações em disco
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, xmlFile);

            //CHAMAR A FUNÇÃO DE VALIDAÇÃO por DTD
            Document docDTD = JDOMFunctions_Validar.validarDTD(xmlFile);
            if (docDTD == null) {
                System.out.println("INVALIDO");
            } else {
                System.out.println("VALIDO");
            }
        }
    }

    public static void validaDocumentoXSD(String xmlFile, String xsdFile) throws IOException, SAXException {
        Document doc = XMLJDomFunctions.lerDocumentoXML(xmlFile);
        File f = new File(xsdFile);
        if (doc != null && f.exists()) {//XSD e XML existem
            Element raiz = doc.getRootElement();
            //Atribuir XSD ao ficheiro XML
            Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

            raiz.addNamespaceDeclaration(XSI);

            raiz.setAttribute(new Attribute("noNamespaceSchemaLocation", xsdFile, Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));

            //Gravar o XML com as alterações em	disco
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, xmlFile);

            //CHAMAR A FUNÇÃO DE VALIDAÇÃO por XSD
            Document docXSD = JDOMFunctions_Validar.validarXSD(xmlFile);
            if (docXSD == null) {
                System.out.println("INVALIDO por XSD");
            } else {
                System.out.println("VALIDO por XSD");
            }
        }
    }
    
    public static void transformaHtml(String fxml, String fxsl)
    {
        Document doc = XMLJDomFunctions.lerDocumentoXML(fxml);
        if(doc!=null)
        {
            Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, fxml, fxsl);
            XMLJDomFunctions.escreverDocumentoParaFicheiro(novo,"fotos.html");
        }
    }
    public static void juntaXML(String fxml, String fxsl)
    {
        Document doc = XMLJDomFunctions.lerDocumentoXML(fxml);
        if(doc!=null)
        {
            Document novo = JDOMFunctions_XSLT.transformaDocumento(doc, fxml, fxsl);
            XMLJDomFunctions.escreverDocumentoParaFicheiro(novo,"junto.xml");
        }
    }

    public static String removeAutor(String procura) { //REMOVE AUTOR DE AUTORES.XML E AS SUAS OBRAS EM OBRAS.XML
        Element raiz;
        String res = null;
        Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");

        if (doc == null) {
            res = "O ficheiro não existe";
            return res;
        } else {
            raiz = doc.getRootElement();
        }

        List todosAutores = raiz.getChildren("Autor");
        boolean found = false;

        for (int i = 0; i < todosAutores.size(); i++) {
            Element autor = (Element) todosAutores.get(i);
            if (autor.getChild("Nome").getText().contains(procura)) {
                autor.getParent().removeContent(autor);
                res = "Escritor removido com sucesso!";
                found = true;
            }
        }

        if (!found) {
            res = "Nenhum escritor chamado " + procura + " foi encontrado";
        } else {
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml");
            doc = XMLJDomFunctions.lerDocumentoXML("obras.xml");
            if (doc == null) {
                res = "O ficheiro não existe";
                return res;
            } else {
                raiz = doc.getRootElement();
            }

            List todosObras = raiz.getChildren("Livro");
            found = false;
            for (int i = 0; i < todosObras.size(); i++) {
                Element livro = (Element) todosObras.get(i);
                if (livro.getChild("Autor").getText().contains(procura)) {
                    livro.getParent().removeContent(livro);
                    res = res + "\nLivro do escritor " + procura + " removido com sucesso!";
                    found = true;
                }
            }
            if (!found) {
                res = "Nenhum escritor chamado " + procura + " foi encontrado";
            }
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "obras.xml");
        }
        return res;
    }

    public static String mudaNomeAutor(String procura, String novoNome) {
        Element raiz;
        String res = null;
        Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");
        if (doc == null) {
            res = "O ficheiro não existe";
            return res;
        } else {
            raiz = doc.getRootElement();
        }

        List todosEscritores = raiz.getChildren("Autor");
        boolean found = false;

        for (int i = 0; i < todosEscritores.size(); i++) {
            Element escritor = (Element) todosEscritores.get(i);
            if (escritor.getChild("Nome").getText().contains(procura)) {
                escritor.getChild("Nome").setText(novoNome);
                res = "Nome do autor " + procura + " alterado para " + novoNome + " com sucesso!";
                found = true;
            }
        }
        if (!found) {
            res = "Nenhum autor chamado " + procura + " foi encontrado";
            return res;
        } else {
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml");
            doc = XMLJDomFunctions.lerDocumentoXML("obras.xml");
            if (doc == null) {
                res = "O ficheiro não existe";
                return res;
            } else {
                raiz = doc.getRootElement();
            }

            List todosObras = raiz.getChildren("Livro");
            found = false;

            for (int i = 0; i < todosObras.size(); i++) {
                Element livro = (Element) todosObras.get(i);
                if (livro.getChild("Autor").getText().contains(procura)) {
                    livro.getChild("Autor").setText(novoNome);
                    res = res + "\nNome do autor " + procura + " alterado com sucesso!";
                    found = true;
                }
            }
            if (!found) {
                res = "Nenhum autor chamado " + procura + " foi encontrado";
            }
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "obras.xml");
        }
        return res;
    }

    
    public static String mudaNacionalidade(String procura, String nova){
        
        Element	raiz;
        String res = null;
        Document doc=XMLJDomFunctions.lerDocumentoXML("escritores.xml");
        if (doc == null) {
            res="O ficheiro não existe";
            return res;
        } 
        else{
            raiz = doc.getRootElement();
        }
        
        List todosEscritores = raiz.getChildren("escritor"); 
        boolean found = false;
          
            for(int i=0;i<todosEscritores.size();i++)
            {
                Element escritor = (Element)todosEscritores.get(i);
                if(escritor.getChild("nome").getText().contains(procura)){
                    escritor.getChild("nacionalidade").setText(nova);
                    res="Nacionalidade do escritor " + procura + " alterada com sucesso!";
                    found=true;
                }
	    }	
	    if(!found)
                res="Nenhum escritor chamado " + procura + " foi encontrado";
          
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc,"escritores.xml");
            return res;
    }
    
      public static String mudaDataMorte(String procura, String nova){
        
        Element	raiz;
        String res = null;
        Document doc=XMLJDomFunctions.lerDocumentoXML("escritores.xml");
        if (doc == null) {
            res="O ficheiro não existe";
            return res;
        } 
        else{
            raiz = doc.getRootElement();
        }
        
        List todosEscritores = raiz.getChildren("escritor"); 
        boolean found = false;
          
            for(int i=0;i<todosEscritores.size();i++)
            {
                Element escritor = (Element)todosEscritores.get(i);
                if(escritor.getChild("nome").getText().contains(procura)){
                    escritor.getChild("data_morte").setText(nova);
                    res="Data de morte do escritor " + procura + " alterada com sucesso!";
                    found=true;
                }
	    }	
	    if(!found)
                res="Nenhum escritor chamado " + procura + " foi encontrado";
          
            XMLJDomFunctions.escreverDocumentoParaFicheiro(doc,"escritores.xml");   
            return res;
    }
      
      
    public static String pesquisaemFicheiro(String nome_ficheiro, String pesquisa) {
        Document doc = XMLJDomFunctions.lerDocumentoXML(nome_ficheiro);
        List res_pesquisa2 = JaxenFunctions_XPATH.pesquisaXPath(doc, pesquisa); //vai receber o doc XML e a pesquisa XPATH

        String resultados = JaxenFunctions_XPATH.listarResultado(res_pesquisa2);
        System.out.println(resultados);
        return resultados; //retorna os resultados 
    }

    public static String pesquisaemAutores(String pesquisa) {
        return pesquisaemFicheiro("autores.xml", pesquisa);
    }

    public static String pesquisaemObras(String pesquisa) {
        return pesquisaemFicheiro("obras.xml", pesquisa);
    }

    public static String pesquisaporNacionalidade(String nacionalidade) {
        return pesquisaemAutores("/autores/Autor[Nacionalidade=\"" + nacionalidade + "\"]/Nome"); //pesquisa a nacionalidade passada como argumento

    }

    public static String pesquisaporGenero(String genero) {
        return pesquisaemAutores("/autores/Autor[contains(.,\"" + genero + "\")]/Nome"); //pesquisa a nacionalidade passada como argumento

    }

    public static String obterEscritorporTituloouIsbn(String tituloIsbn) {
        return pesquisaemObras("/Obras/Livro[ISBN=\"" + tituloIsbn + "\" or Titulo=\"" + tituloIsbn + "\"]/Autor");
    }

    public static String mostraAutores() {
        return pesquisaemAutores("/autores/Autor");
    }

    public static String mostraObras() {
        return pesquisaemObras("/Obras/Livro");
    }

    public static void main(String[] args) throws IOException, SAXException {
        /*System.out.println("Autor a pesquisar: ");
         Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
         String linha;
         linha = ler.nextLine();
         leituraWook(linha);  //testar com o escritor "oscar wilde", por exemplo */
        
        Frame f = new Frame();
        f.setVisible(true);   
    }
}
