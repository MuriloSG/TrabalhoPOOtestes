/*
 * Authors: Murilo Santos e Caio Silva.
 * Classe para efetuar os testes.
 */

import org.example.CalculaNucleotideos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CalculaNucleotideosTest {

  private File tempFile;

  @org.junit.jupiter.api.BeforeEach
  void criarArquivoTemporario() throws IOException {
    tempFile = File.createTempFile("sequencia", ".txt");
  }

  @org.junit.jupiter.api.AfterEach
  void excluirArquivoTemporario() {
    if (tempFile.exists()) {
      assertTrue(tempFile.delete(), "Erro ao deletar o arquivo temporário");
    }
  }

  @Test
  @DisplayName("Valida sequência correta sem erros")
  void deveRetornarContagemCorretaQuandoSequenciaValida() throws IOException {
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("AAAGTCTGAC");
    }

    CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
    int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

    int[] esperado = {4, 2, 2, 2, 0};
    assertArrayEquals(esperado, resultado);
  }

  @Test
  @DisplayName("sequência com erros menores que 10%")
  void deveRetornarContagemComErrosMinimos() throws IOException {
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("AACTGTCGBA");
    }

    CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
    int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

    int[] esperado = {3, 2, 2, 2, 1};
    assertArrayEquals(esperado, resultado);
  }

  @Test
  @DisplayName("sequência com erros superiores a 10%")
  void deveRetornarNuloQuandoErrosExcedem10Porcento() throws IOException {
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("ABC TEM FALHAA");
    }

    CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
    int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

    assertNull(resultado);
  }

  @Test
  @DisplayName("comportamento para arquivo vazio")
  void deveRetornarContagemZeroQuandoArquivoVazio() throws IOException {
    CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
    int[] resultado = calculaNucleotideos.calculaNucleotideos(tempFile.getPath());

    int[] esperado = {0, 0, 0, 0, 0};
    assertArrayEquals(esperado, resultado);
  }

  @Test
  @DisplayName("exceção quando arquivo não encontrado")
  void deveLancarExcecaoQuandoArquivoInexistente() {
    String caminho = "caminho/test.txt";

    CalculaNucleotideos calculaNucleotideos = new CalculaNucleotideos();
    FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> calculaNucleotideos.calculaNucleotideos(caminho));

    assertEquals("Arquivo não encontrado: " + caminho, exception.getMessage());
  }
}
