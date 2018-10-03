package it.objectmethod.webpage.reading;

import java.io.IOException;
import java.net.MalformedURLException;

public class ExtractDataFromPage {

	public static String getHrefAbiCode() {	

		String url="https://www.comuniecitta.it/elenco-banche-per-codice-abi";
		String br =null;
		try {
			br = ReadFromWebPage.readFromWebPage(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int indiceDiTbody= br.indexOf("<tbody>");
		String stringaDaTbody = br.substring(indiceDiTbody+1);
		String senzaSpaziAggiuntivi= stringaDaTbody.trim().replaceAll(" +", " ").trim();
		int indiceDiTagTr= senzaSpaziAggiuntivi.indexOf("<tr>");
		String senzaSpaziESenzaPrimoTr= senzaSpaziAggiuntivi.substring(indiceDiTagTr+4).trim();
		String[] isoloTagDati = senzaSpaziESenzaPrimoTr.split("<td class=\"tvMiddle\"><img class=\""
				+ "img-responsive\" src=\"/immagini/loghi-banche/");
		//int inizioNome=0;
		String insiemeNomiBanche="";
		String hrefContainer="";
		for(int i=1;i<isoloTagDati.length;i++) {
			int inizio = isoloTagDati[i].indexOf("png");
			String partoDaPng = isoloTagDati[i].substring(inizio);
			String stringaInizioNome= partoDaPng.substring(32);
			int inizioStringaHref = stringaInizioNome.indexOf("</td>");
			String inizioHrefDopoTagClasse= stringaInizioNome.substring(inizioStringaHref+5);
			int primaOccorenzaHref = inizioHrefDopoTagClasse.indexOf("/abi-");
			String inizioConHref = inizioHrefDopoTagClasse.substring(primaOccorenzaHref);
			String href=inizioConHref.substring(0, 10);		
			hrefContainer+=href+" ";
			int chiusuraTagTd= stringaInizioNome.indexOf("</td>");
			String nome=stringaInizioNome.substring(0,chiusuraTagTd);	
			insiemeNomiBanche+=nome+"       ";
		}
		return (hrefContainer+" delimitatore "+insiemeNomiBanche);
	}
	public static String  getComuniAbiCabMaxPages(String url) {
		String str =null;
		try {
			str = ReadFromWebPage.readFromWebPage(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int indiceIniziale = str.indexOf("<table class=\"table table-condensed");
		String preInizio = str.substring(indiceIniziale);
		int indiceDiTbody= preInizio.indexOf("<tbody>");
		String stringaDaTbody = preInizio.substring(indiceDiTbody+1);
		String senzaSpaziAggiuntivi= stringaDaTbody.trim().replaceAll(" +", " ");
		String tolgoTagTrApertura = senzaSpaziAggiuntivi.replaceAll("<tr>", "");
		String tolgoTdChiusura =tolgoTagTrApertura.replaceAll("</td>", "");
		String tolgoTagTdApertura =tolgoTdChiusura.replaceAll("<td class=\"tvMiddle\">", " ");
		String tolgoTagTdPerturaCodice = tolgoTagTdApertura.replaceAll("<td class=\"tvMiddle text-center\">", " ");
		String tolgoHref= tolgoTagTdPerturaCodice.replaceAll("  <a href=\"/abi-", " ");
		String tolgoTagClasseVuoto= tolgoHref.replaceAll("\" class=\"\">", " ");
		int fineTabella = tolgoTagClasseVuoto.indexOf("</tbody>");
		String tolgoFineTagLinkETr = tolgoTagClasseVuoto.replaceAll("</a> </tr>   ", "  ");
		String valoriTabellaConHrefEDatiPagine= tolgoFineTagLinkETr.substring(9, fineTabella);
		int indiceUltimiTag = valoriTabellaConHrefEDatiPagine.indexOf("</a>");
		String senzaTag = valoriTabellaConHrefEDatiPagine.substring(0, indiceUltimiTag);
		String unSoloAbiCode= senzaTag.replaceAll("[0-9][0-9][0-9][0-9][0-9]-cab-[0-9][0-9][0-9][0-9][0-9]", "");
		String result = unSoloAbiCode+" ";
		int inizioNotazioneDiNumeroPagine = tolgoFineTagLinkETr.indexOf("Pagina ");
		int maxNumeroPagine = Integer.parseInt((tolgoFineTagLinkETr.substring(inizioNotazioneDiNumeroPagine+12, inizioNotazioneDiNumeroPagine+13)).trim());
		return ((result+maxNumeroPagine).trim());
	}

	public static String[] getArrayComuniAbiCabFromPage(String url,String comuniAbiCabMaxpage) {
		char chracter = comuniAbiCabMaxpage.charAt(comuniAbiCabMaxpage.length()-1);
		int indiceChar = comuniAbiCabMaxpage.lastIndexOf(chracter);
		String nienteNumeroPagina = comuniAbiCabMaxpage.substring(0,comuniAbiCabMaxpage.lastIndexOf(comuniAbiCabMaxpage.charAt(indiceChar)));
		String[] comuniAbiCab= nienteNumeroPagina.split("  ");
		return comuniAbiCab;
	}

	public static String informazioniFiliale(String url ) {
		String str=null;
		try {
			str = ReadFromWebPage.readFromWebPage(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int inizioTabella= str.indexOf("<tbody>")+7;
		int fineTabella= str.indexOf("</tbody>");
		String elementiDentroTabella = (str.substring(inizioTabella, fineTabella)).trim();
		String puliziaTagTrApertura = elementiDentroTabella.replaceAll("<tr>", "");
		String puliziaTagTrChiusura = puliziaTagTrApertura.replaceAll("</tr>", "");
		String puliziaSpazi = puliziaTagTrChiusura.replaceAll(" {2,}", "");
		String tolgoTagTd = (puliziaSpazi.replaceAll("<td>", "")).replaceAll("</td>", " ");
		int indiceTagProvincia= tolgoTagTd.indexOf("<th>Provincia</th>")+18;
		int indiceTagRegione = tolgoTagTd.indexOf("<th>Regione</th>")+16;
		int indiceTagDataApertura = tolgoTagTd.indexOf("<th>Data apertura</th>")+22;
		int indiceTagVia = tolgoTagTd.indexOf("<th>Indirizz")+18;
		int indiceIniziodata= tolgoTagTd.indexOf("operativa dal ")+14;
		int indiceFinedata=tolgoTagTd.indexOf("</em>");
		String data = tolgoTagTd.substring(indiceIniziodata, indiceFinedata);
		String provincia = tolgoTagTd.substring(indiceTagProvincia, indiceTagRegione-16);
		String regione = tolgoTagTd.substring(indiceTagRegione, indiceTagDataApertura-22);
		String via =tolgoTagTd.substring(indiceTagVia,tolgoTagTd.indexOf("<th>Comune</th>") );		
		String indirizzo= via;
		if(tolgoTagTd.contains("<ul class=\"")) {
			int inizioTagUlLista= tolgoTagTd.indexOf("<ul class=\"");
			String iniziodaTagUl= tolgoTagTd.substring(inizioTagUlLista);
			int fineTagAperturaUl= iniziodaTagUl.indexOf(">");
			iniziodaTagUl=iniziodaTagUl.substring(fineTagAperturaUl+1);
			indirizzo= iniziodaTagUl.replaceAll("<li>", ""); 
			indirizzo=indirizzo.replaceAll("</li>", " ");
			indirizzo=indirizzo.replaceAll("</ul>", "");
			indirizzo= indirizzo.substring(0, indirizzo.indexOf("<th>Comune"));
		}
		if(indirizzo.contains('&'+"")) {
			indirizzo=indirizzo.replaceAll("grave", "");
			int indiceSimbolo =indirizzo.indexOf('&'+"");
			char d = indirizzo.charAt(indiceSimbolo+1);
			char c= indirizzo.charAt(indiceSimbolo);
			String finale=indirizzo.substring(indiceSimbolo+2, indirizzo.length());
			indirizzo=indirizzo.substring(0,indirizzo.indexOf(c));
			String ponte= d+"\'";
			indirizzo=indirizzo+ponte;
			indirizzo=indirizzo+finale;
		}
		String result= indirizzo+"    "+regione+"    "+provincia+"    "+data;
		return result;
	}
}
