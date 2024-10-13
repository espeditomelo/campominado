package br.com.souprogramador.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import org.junit.platform.commons.util.ToStringBuilder;

import br.com.souprogramador.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhanca = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhanca.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhanca.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	void alternarMarcacao() {		
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	void minar() {
		minado = true;		
	}
	
	boolean abrir() {
		
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				// chamada recursiva para abrir()
				vizinhanca.forEach(vizinho -> vizinho.abrir());
			}
			
			return true;
		} else {
			return false;			
		}
		
	}
	
	boolean vizinhancaSegura() {
		return vizinhanca
				.stream()
				.noneMatch(vizinho -> vizinho.minado);
	}

	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;			
	}
	
	long minasNaVizinhaca() {
		return vizinhanca.stream().filter(vizinho -> vizinho.minado).count();
	}
	
	void reinciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String ToString() {
		if(marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if(aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		} else if(aberto) {
			return " ";
		} else {
			return "?";
		}
	}
}
