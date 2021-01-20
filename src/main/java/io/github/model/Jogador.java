package io.github.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Jogador implements  Comparable< Jogador > {

  @JsonProperty( value="id", required = true)
  @NotNull(message = "Obrigatório")
  @Positive(message = "Somente Números positivos")
  private  Integer id;

  @JsonProperty("nome")
  private String nome;

  @JsonProperty("jogada")
  private String jogada;

  @JsonProperty("pontuacao")
  @NotNull(message = "Obrigatório")
  private Integer pontuacao = 0;

    /**
     *
     * @param pId
     * @param pNome
     * @param pJogada
     */
    public Jogador(Integer pId, String pNome, String pJogada){
        this.id = pId;
        this.nome = pNome;
        this.jogada = pJogada;
    }

    /**
     *
     * @param pId
     * @param pNome
     * @param pJogada
     * @param pPontuacao
     */
    public Jogador(Integer pId, String pNome, String pJogada, Integer pPontuacao){
        this.id = pId;
        this.nome = pNome;
        this.jogada = pJogada;
        this.pontuacao = pPontuacao;
    }

  public Integer getId() {
        return id;
  }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJogada() {
        return jogada;
    }

    public void setJogada(String jogada) {
        this.jogada = jogada;
    }


    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pPontuacao) {
        this.pontuacao = this.pontuacao + pPontuacao;
    }



    @Override
    public int compareTo(Jogador o) {
        return this.getPontuacao().compareTo(o.getPontuacao());
    }
}
