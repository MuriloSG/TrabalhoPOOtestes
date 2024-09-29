/*
 * Authors: Murilo Santos e Caio Silva.
 * Classe para calculos de nucleotideos.
 */

package org.example;
import java.io.*;

public class CalculaNucleotideos {
  public int[] calculaNucleotideos(String filePath) throws FileNotFoundException {
    int[] contagemNucleotideos = new int[5];

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String linha = reader.readLine();
      if (linha == null || linha.isEmpty()) {
        return contagemNucleotideos;
      }

      int totalCaracteres = linha.length();
      int erros = 0;

      for (char c : linha.toCharArray()) {
        switch (c) {
          case 'A': contagemNucleotideos[0]++; break;
          case 'C': contagemNucleotideos[1]++; break;
          case 'G': contagemNucleotideos[2]++; break;
          case 'T': contagemNucleotideos[3]++; break;
          default: erros++; break;
        }
      }
      contagemNucleotideos[4] = erros;
      if ((double) erros / totalCaracteres > 0.1) {
        return null;
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Arquivo não encontrado: " + filePath);
    } catch (IOException e) {
      System.err.println("Erro ao ler o arquivo: " + e.getMessage());
      e.printStackTrace();
    }

    return contagemNucleotideos;
  }
}

