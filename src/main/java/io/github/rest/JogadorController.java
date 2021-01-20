package io.github.rest;

import io.github.model.Jogador;
import io.github.util.RepositorySession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/rest")
public class JogadorController {

    /**Adicionar Jogador
     *
     * @param pJogador
     * @param pSession
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jogador addRest(@RequestBody  @Valid Jogador pJogador , HttpSession pSession){
        //Como não existe repositório será armazenado na sessão do servidor e na memória da aplicação
        RepositorySession.getInstance().checarJogadoresSessao(pSession);
        RepositorySession.getInstance().addJogadorSession(pSession,pJogador );

        return pJogador;
    }

    /**remover
     *
     * @param id
     * @param pSession
     * @return
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deletar(@PathVariable Integer id , HttpSession pSession) {
        RepositorySession.getInstance().checarJogadoresSessao(pSession);
        RepositorySession.getInstance().removerJogadorSession(pSession ,id);

    return RepositorySession.getInstance().getFeedBack();
    }

    /**Consultar por id
     *
     * @param id
     * @param pSession
     * @return
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getJogadores(@PathVariable @Valid Integer id , HttpSession pSession) {
        return RepositorySession.getInstance().consultarJogador(pSession,id);
    }

    /**Retorna todos os JOgadores JSonPretty
     *
     * @param pSession
     * @return
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getTodosJogadores( HttpSession pSession) {
        return RepositorySession.getInstance().consultarJogador(pSession, null);
    }



}
