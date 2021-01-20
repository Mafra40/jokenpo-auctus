package io.github.rn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.model.Jogador;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Classe reponsável pela lógica do jogo
 */
public class RNGerarResultado {


    private static RNGerarResultado aRNGerarResultado = new RNGerarResultado();

    public static RNGerarResultado getInstance(){
        if(RNGerarResultado.aRNGerarResultado == null){
            aRNGerarResultado = new RNGerarResultado();
        }

        return RNGerarResultado.aRNGerarResultado;
    }

    /**Método que soma a pontuação de cada jogador contra outro; +1 se ganhar, -1 se perder.
     *
     * @param pArrJogador
     * @return
     */
    public String gerarResultado(ArrayList<Jogador> pArrJogador ){

        ArrayList<Jogador> arrJogadorLocal = new ArrayList<>(pArrJogador);



        //vai somando a pontuação de cada um
        ArrayList<String> jaValiados = new ArrayList<>();

        for (int i = 0; i < arrJogadorLocal.size() ; i++) {

         INTERNO: for (int j = 0; j < arrJogadorLocal.size() ; j++) {

                if (arrJogadorLocal.get(i) == arrJogadorLocal.get(j)){continue INTERNO;}//jogadores iguais não pode pontuar

                if (jaValiados.contains(arrJogadorLocal.get(j).getNome()+arrJogadorLocal.get(j).getId())) {continue INTERNO;}

                if(arrJogadorLocal.get(i).getJogada().equals("Pedra") && arrJogadorLocal.get(j).getJogada().equals("Tesoura")){
                    arrJogadorLocal.get(i).setPontuacao(+1);
                    arrJogadorLocal.get(j).setPontuacao(-1);
                    continue INTERNO;
                }

                if(arrJogadorLocal.get(i).getJogada().equals("Pedra") && arrJogadorLocal.get(j).getJogada().equals("Papel")){
                    arrJogadorLocal.get(i).setPontuacao(-1);
                    arrJogadorLocal.get(j).setPontuacao(+1);
                    continue INTERNO;
                }

                if(arrJogadorLocal.get(i).getJogada().equals("Tesoura") && arrJogadorLocal.get(j).getJogada().equals("Pedra")){
                    arrJogadorLocal.get(i).setPontuacao(-1);
                    arrJogadorLocal.get(j).setPontuacao(+1);
                    continue INTERNO;
                }

                if(arrJogadorLocal.get(i).getJogada().equals("Tesoura") && arrJogadorLocal.get(j).getJogada().equals("Papel")){
                    arrJogadorLocal.get(i).setPontuacao(+1);
                    arrJogadorLocal.get(j).setPontuacao(-1);
                    continue INTERNO;
                }

                if(arrJogadorLocal.get(i).getJogada().equals("Papel") && arrJogadorLocal.get(j).getJogada().equals("Pedra")){
                    arrJogadorLocal.get(i).setPontuacao(+1);
                    arrJogadorLocal.get(j).setPontuacao(-1);
                    continue INTERNO;
                }

                if(arrJogadorLocal.get(i).getJogada().equals("Papel") && arrJogadorLocal.get(j).getJogada().equals("Tesoura")){
                    arrJogadorLocal.get(i).setPontuacao(-1);
                    arrJogadorLocal.get(j).setPontuacao(+1);
                    continue INTERNO;
                }

            }
            //manter os jogadores que já foram avaliados
            jaValiados.add(arrJogadorLocal.get(i).getNome()+arrJogadorLocal.get(i).getId());

        }
         //ordenar o resultado pela pontuação
          Collections.sort(arrJogadorLocal , Collections.reverseOrder());
           Gson gson = new GsonBuilder().setPrettyPrinting().create();
          //retorna o jogador  com a pontuação mais alta
           Integer pontuacaoMaisAlta = new Integer(arrJogadorLocal.get(0).getPontuacao());

           //verifica se houve um empate com outros jogadores
            ArrayList<Jogador> arrJogadoresVencedores = new ArrayList<>();
            for (int i = 0; i < arrJogadorLocal.size(); i++) {
                if (Integer.compare(arrJogadorLocal.get(i).getPontuacao(),pontuacaoMaisAlta ) == 0){
                    arrJogadoresVencedores.add(arrJogadorLocal.get(i));
                }
            }

            String retorno;
            if (arrJogadoresVencedores.size() > 1){  //retorna os jogadores empatados
                retorno = "Empate entre: " + gson.toJson(arrJogadoresVencedores);
            }else {//retorna o único vencedor
                retorno = "Vencedor: "+ gson.toJson(arrJogadoresVencedores.get(0));
            }


        return retorno;

    }

}
