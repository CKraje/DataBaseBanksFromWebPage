package it.objectmethod.banca.dao;

import it.objectmethod.banca.domain.IstitutoDiCredito;
import it.objectmethod.banca.domain.Filiale;

public interface BancaDao {

		public int inserisciBanca(IstitutoDiCredito istitutoDiCredito, Filiale filiale);
}
