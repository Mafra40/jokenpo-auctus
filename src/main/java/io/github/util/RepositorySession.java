package io.github.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.model.Jogador;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class RepositorySession {

    private static RepositorySession aRepositorySession = new RepositorySession();

    public static RepositorySession getInstance(){
        if(RepositorySession.aRepositorySession == null){
            aRepositorySession = new RepositorySession();
        }

        return RepositorySession.aRepositorySession;
    }

    public static final String NM_ATT_SESSION_JOGADORES = "JOGADORES";
    public static final String NM_ATT_SESSION_JOGADORES_JSON = "JOGADORES_JSON";

    /* Mensagens de feed back e Atribrutos que precisam ser zerados depois de usados */
    private String aFeedBack = "";
    private String aJogadoresJson = "";
    private ArrayList<Jogador> aArrJogadores =  null;

    public String getJogadoresJson(){
        return this.aJogadoresJson;
    }

    public void setJogadoresJson(String pJSON){
         this.aJogadoresJson = pJSON;
    }

    public void setArraJogadores(ArrayList<Jogador> pArray){
        this.aArrJogadores = pArray;
    }

    public ArrayList<Jogador> getArraJogadores() {
        return this.aArrJogadores;
    }

    /**Popular jogadores padrão na sessão e salvar com JSON
     *
     * @param pSession
     */
    public void popularJogadoresPadrao(HttpSession pSession){

        Gson gson = new Gson();
        ArrayList<Jogador> arrJogadoresLocal;

        if ( pSession.getAttribute(NM_ATT_SESSION_JOGADORES) == null && ( this.getArraJogadores() == null && this.getJogadoresJson().equals("") )) {

            //lista de jogadores padrão
            arrJogadoresLocal = new  ArrayList<>();
            arrJogadoresLocal.add( new Jogador(1,"João" , "Pedra"));
            arrJogadoresLocal.add( new Jogador(2,"Maria", "Papel"));
            arrJogadoresLocal.add( new Jogador(2,"Maria Betancia", "Papel"));
            arrJogadoresLocal.add( new Jogador(3,"Roberto" , "Tesoura"));
            arrJogadoresLocal.add( new Jogador(4,"Luiz" , "Pedra"));
            arrJogadoresLocal.add( new Jogador(5,"Jhones" , "Tesoura"));
            arrJogadoresLocal.add( new Jogador(6,"Felipe" , "Papel"));
            arrJogadoresLocal.add( new Jogador(7,"Fabio" , "Papel"));

            this.aJogadoresJson =  gson.toJson(arrJogadoresLocal);
            this.aArrJogadores = arrJogadoresLocal;

            pSession.setAttribute(NM_ATT_SESSION_JOGADORES_JSON, gson.toJson(arrJogadoresLocal));
            pSession.setAttribute(NM_ATT_SESSION_JOGADORES, arrJogadoresLocal);
        } else {
            //recupera jogadores da Sessão se houver sessão
            boolean existeJogadoresSessao = false;

            if (pSession.getAttribute(NM_ATT_SESSION_JOGADORES_JSON) != null && pSession.getAttribute(NM_ATT_SESSION_JOGADORES) != null ) {
                this.setJogadoresJson(pSession.getAttribute(NM_ATT_SESSION_JOGADORES_JSON).toString());
                this.setArraJogadores((ArrayList<Jogador>) pSession.getAttribute(NM_ATT_SESSION_JOGADORES));
                existeJogadoresSessao = true;
            }

            if (!existeJogadoresSessao) {
                if(this.getArraJogadores() != null  && !this.getJogadoresJson().equals("") ) { //caso contrário não haver sessão porém ter os objetos na memória



                    //atualiza a sessão com o objeto em memória e cria a sessão
                    pSession.setAttribute(NM_ATT_SESSION_JOGADORES_JSON, this.getJogadoresJson());
                    pSession.setAttribute(NM_ATT_SESSION_JOGADORES, this.getArraJogadores());
                }
            }



        }
    }


    /** Adiciona um jogador na coleção de jogadores da sessão
     *
     * @param pSession
     * @param pJogador
     */
    public void addJogadorSession(HttpSession pSession, Jogador pJogador){
        Integer novoId = 0;
        Gson gson = new Gson();

        if (getArraJogadores() != null) {

            if (pJogador.getId() == null) {
                novoId = this.getArraJogadores().size() + 1;
                pJogador.setId(novoId);
            }
            this.getArraJogadores().add(pJogador);
            this.setJogadoresJson(gson.toJson(this.getArraJogadores()));

            //atualiza a session
            pSession.setAttribute(NM_ATT_SESSION_JOGADORES, this.getArraJogadores() );
            pSession.setAttribute(NM_ATT_SESSION_JOGADORES_JSON, this.getJogadoresJson() );
        }

    }

    /**Remover um jogar da sessão pelo ID.
     * TODO Aprimorar para não duplicar ID e remover o errado.
     *
     * @param pSession
     * @param pIdJogador
     */
    public void removerJogadorSession(HttpSession pSession , Integer pIdJogador){

        Gson gson = new Gson();
        boolean isRemovido = false;

        if (this.getArraJogadores() != null) {

            for (int i = 0; i < getArraJogadores().size(); i++) {

                //verifica se os ID são iguais.
                if( Integer.compare(pIdJogador,getArraJogadores().get(i).getId()) == 0 ) {
                    this.aFeedBack = "Removido: "+ gson.toJson(getArraJogadores().get(i));
                    this.getArraJogadores().remove(getArraJogadores().get(i)); //remove o jogador que tem o ID passado no paramentro
                    isRemovido = true;
                    break;
                }
            }

            if (!isRemovido){ //Implementar para Quando Não for removido gerar um código de retorno.
                this.aFeedBack = "ID " +pIdJogador+ " Não Encontrado";
                return;
            }
            //atualiza o json
            this.setJogadoresJson(gson.toJson(this.getArraJogadores()));
            pSession.setAttribute(NM_ATT_SESSION_JOGADORES, this.getArraJogadores() );
            pSession.setAttribute(NM_ATT_SESSION_JOGADORES_JSON,this.getJogadoresJson());
        }

    }



    /**Verifica se a sessão ta populada
     *
     * @param pSession
     */
    public void checarJogadoresSessao(HttpSession pSession){
        if (this.getArraJogadores() == null || this.getJogadoresJson().equals("")){
            this.popularJogadoresPadrao(pSession);
        }
    }

    /**Retorna todos ou somente 1 Jogador
     *
     * @param pSession
     * @param id
     * @return
     */
    public String consultarJogador(HttpSession pSession, Integer id ){
        this.checarJogadoresSessao(pSession);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if(id != null){
            for (int i = 0; i < this.getArraJogadores().size(); i++) {
                if(Integer.compare(this.getArraJogadores().get(i).getId(), id) == 0){
                    return gson.toJson(this.getArraJogadores().get(i));
                }
            }
        }
        else//retorna todos;
        if (!this.getArraJogadores().isEmpty()){
            return gson.toJson(this.getArraJogadores());
        }

        return "{vazio}";
    }


    public void resetarDados(HttpSession pSession){
        pSession.invalidate();
        this.aFeedBack = "";
        this.aJogadoresJson = "";
        aArrJogadores =  null;
    }

    public String getFeedBack() {
        return this.aFeedBack;
    }
}
