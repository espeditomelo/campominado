package br.com.souprogramador.cm.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.souprogramador.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campoReal;
	
	@BeforeEach
	void iniciarCampo() {
		campoReal = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoEsquerda() {
		Campo vizinhoEsquerda = new Campo(3, 2);
		boolean resultadoEsquerda = campoReal.adicionarVizinho(vizinhoEsquerda);		
		assertTrue(resultadoEsquerda);		
	}
	
	@Test
	void testeVizinhoDireita() {
		Campo vizinhoDireita = new Campo(3, 4);
		boolean resultadoDireita = campoReal.adicionarVizinho(vizinhoDireita);
		assertTrue(resultadoDireita);		
	}
	
	@Test
	void testeVizinhoAcima() {
		Campo vizinhoAcima = new Campo(2, 3);
		boolean resultadoAcima = campoReal.adicionarVizinho(vizinhoAcima);
		assertTrue(resultadoAcima);
	}
	
	@Test
	void testeVizinhoAbaixo() {
		Campo vizinhoAbaixo = new Campo(4, 3);
		boolean resultadoAbaixo = campoReal.adicionarVizinho(vizinhoAbaixo);
		assertTrue(resultadoAbaixo);
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campoReal.isMarcado());
	}
	
	@Test
	void testeAlternartMarcacao() {
		campoReal.alternarMarcacao();
		assertTrue(campoReal.isMarcado());
		
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campoReal.alternarMarcacao();
		campoReal.alternarMarcacao();
		assertFalse(campoReal.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campoReal.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campoReal.alternarMarcacao();
		assertFalse(campoReal.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campoReal.alternarMarcacao();
		campoReal.minar();
		assertFalse(campoReal.abrir());
	}
	
	//parei aqui
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campoReal.minar();
		
		assertThrows(ExplosaoException.class, () ->{
			campoReal.abrir();
		});			
	}
	
	@Test
	void testeAbrirComVizinhos1() {
		Campo campo11 = new Campo(1, 1);
		
		Campo campo22 = new Campo(2, 2);		
		campo22.adicionarVizinho(campo11);
		
		campoReal.adicionarVizinho(campo22);
		
		campoReal.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());				
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);		
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campoReal.adicionarVizinho(campo22);
		
		campoReal.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());				
	}
}
