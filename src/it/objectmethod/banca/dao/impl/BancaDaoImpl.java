package it.objectmethod.banca.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import it.objectmethod.banca.config.ConnectionToDb;
import it.objectmethod.banca.dao.BancaDao;
import it.objectmethod.banca.domain.IstitutoDiCredito;
import it.objectmethod.banca.domain.Filiale;

public class BancaDaoImpl implements BancaDao{

	@Override
	public int inserisciBanca(IstitutoDiCredito banca,Filiale filiale) {
		Connection conn = ConnectionToDb.getConnection();
		PreparedStatement stmt = null;
		int rows=0;
		try {
			String sql = "INSERT INTO bank (Name,Abicode,CabCode,Regione,Comune,Provincia,Indirizzo,DataApertura)VALUES(?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, banca.getNome());
			stmt.setString(2, banca.getAbiCode());
			stmt.setString(3, filiale.getFilialeCabCode());
			stmt.setString(4, filiale.getRegione());
			stmt.setString(5, filiale.getComune());
			stmt.setString(6, filiale.getProvincia());
			stmt.setString(7, filiale.getIndirizo());
			stmt.setString(8, filiale.getDataApertura());
			rows=stmt.executeUpdate();			
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rows;
	}
}
