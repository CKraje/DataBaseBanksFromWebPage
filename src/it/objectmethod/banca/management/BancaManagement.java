package it.objectmethod.banca.management;

import it.objectmethod.banca.dao.BancaDao;
import it.objectmethod.banca.dao.impl.BancaDaoImpl;
import it.objectmethod.banca.domain.IstitutoDiCredito;
import it.objectmethod.banca.domain.Filiale;
import it.objectmethod.webpage.reading.ExtractDataFromPage;

public class BancaManagement {

	public static void main(String[] args) {

		String url="https://www.comuniecitta.it";
		String linkENomiBanche=ExtractDataFromPage.getHrefAbiCode();
		String[] linkEBanche = linkENomiBanche.split(" delimitatore ");
		String[] nomiBanche = linkEBanche[1].split("       ");
		String[] hrefArray= linkEBanche[0].split(" ");
		String urlPreciso;
		String comuniAbiCabMaxPage=null;
		String[] comuniAbiCab;
		BancaDao bancaDao = new BancaDaoImpl();		
		String datiFiliale="";
		String [] arrayDatiFiliale;
		for(int i=541;i<nomiBanche.length;i++) {
			IstitutoDiCredito istitutoDiCredito = new IstitutoDiCredito();
			String name = nomiBanche[i]; 
			istitutoDiCredito.setNome(name);
			System.out.print(name+"\n");
			String link= hrefArray[i];
			String abi = (hrefArray[i].substring(5)).trim();
			istitutoDiCredito.setAbiCode(abi);
			String rootFiliale=url+ link+"-cab-";
			urlPreciso= url+link;
			comuniAbiCabMaxPage = ExtractDataFromPage.getComuniAbiCabMaxPages(urlPreciso);
			char numeroMaxPagine = comuniAbiCabMaxPage.charAt(comuniAbiCabMaxPage.length()-1);
			int maxPagine= numeroMaxPagine-'0';
			for(int d=1;d<=maxPagine;d++) {
				String comune="";
				String urlTabellaPagina=urlPreciso+"?pg="+d;	
				try {
					comuniAbiCab=ExtractDataFromPage.getArrayComuniAbiCabFromPage(urlTabellaPagina,comuniAbiCabMaxPage);
					for(int e=0;e< comuniAbiCab.length;e++) {
						if(e%3==0) {
							comune=comuniAbiCab[e];
						}
						if(e%3==2) {
							Filiale filiale = new Filiale();
							filiale.setFilialeCabCode(comuniAbiCab[e].trim());
							filiale.setComune(comune);
							System.out.print(" AbiCode : "+istitutoDiCredito.getAbiCode()+" Comune :"+filiale.getComune()+"\n");
							System.out.print(comuniAbiCab[e]+"\n");
							datiFiliale=ExtractDataFromPage.informazioniFiliale(rootFiliale+comuniAbiCab[e]);
							arrayDatiFiliale=datiFiliale.split("    ");
							for(int h=0;h<arrayDatiFiliale.length;h++) {
								if(h%4==0) {
									filiale.setIndirizo(arrayDatiFiliale[h]);
									System.out.print(arrayDatiFiliale[h]+"  ");
								}
								if(h%4==1) {
									filiale.setRegione(arrayDatiFiliale[h]);
									System.out.print(arrayDatiFiliale[h]+"  ");
								}
								if(h%4==2) {
									filiale.setProvincia(arrayDatiFiliale[h]);
									System.out.print(arrayDatiFiliale[h]+"  ");
								}
								if(h%4==3) {
									filiale.setDataApertura(arrayDatiFiliale[h]);
									System.out.print(arrayDatiFiliale[h]+"\n");
								}
							}
							int row = bancaDao.inserisciBanca(istitutoDiCredito, filiale);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.print("Finito");
	}
}
