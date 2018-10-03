package it.objectmethod.banca.domain;

public class Filiale {
private String indirizzo;
private String provincia;
private String comune;
private String dataApertura;
private String regione;
private String filialeCabCode;

public String getFilialeCabCode() {
	return filialeCabCode;
}
public void setFilialeCabCode(String filialeCabCode) {
	this.filialeCabCode = filialeCabCode;
}
public String getIndirizo() {
	return indirizzo;
}
public void setIndirizo(String indirizo) {
	this.indirizzo = indirizo;
}
public String getProvincia() {
	return provincia;
}
public void setProvincia(String provincia) {
	this.provincia = provincia;
}
public String getComune() {
	return comune;
}
public void setComune(String comune) {
	this.comune = comune;
}
public String getDataApertura() {
	return dataApertura;
}
public void setDataApertura(String dataApertura) {
	this.dataApertura = dataApertura;
}
public String getRegione() {
	return regione;
}
public void setRegione(String regione) {
	this.regione = regione;
}

}
