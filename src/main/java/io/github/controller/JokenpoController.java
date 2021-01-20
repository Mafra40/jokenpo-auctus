package io.github.controller;

import io.github.model.Jogador;
import io.github.rn.RNGerarResultado;
import io.github.util.RepositorySession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class JokenpoController {



    //@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @RequestMapping("/app/jokenpo/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarJogador( HttpServletResponse pResponse, HttpSession pSession , Jogador pJogador) throws IOException {

        RepositorySession.getInstance().checarJogadoresSessao(pSession);
        RepositorySession.getInstance().addJogadorSession(pSession,pJogador );
        pResponse.sendRedirect("/app/jokenpo");
    }

    @RequestMapping(value = "/app/jokenpo/remover/{id}", method = RequestMethod.GET)
    public void removerJogador(@PathVariable Integer id , HttpServletResponse pResponse, HttpSession pSession) throws IOException {

        RepositorySession.getInstance().checarJogadoresSessao(pSession);
        RepositorySession.getInstance().removerJogadorSession(pSession, id);
        pResponse.sendRedirect("/app/jokenpo");
    }


    @RequestMapping("/app/jokenpo/partida")
    public RedirectView calcularPartida(HttpSession pSession , RedirectAttributes  pAttributes)  {
        String retorno;

        RepositorySession.getInstance().checarJogadoresSessao(pSession);

        retorno = RNGerarResultado.getInstance().gerarResultado(RepositorySession.getInstance().getArraJogadores()) ;
        pAttributes.addFlashAttribute("resultado" , retorno);

        return new RedirectView("/app/jokenpo");
    }


    @RequestMapping("/app/jokenpo/resetar")
    public void restarDados(HttpSession pSession, HttpServletResponse pResponse) throws IOException{
        RepositorySession.getInstance().resetarDados(pSession);
        pResponse.sendRedirect("/app/jokenpo");
    }

}
