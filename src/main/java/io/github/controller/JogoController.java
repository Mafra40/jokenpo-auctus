package io.github.controller;

import io.github.model.Jogador;
import io.github.util.RepositorySession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class JogoController {

    @GetMapping("/app/jokenpo")
    public String index(ModelMap pModel , HttpSession pSession) {

        //ADDICIONAR JOGADORES PADRÃO
        RepositorySession.getInstance().popularJogadoresPadrao(pSession);

        pModel.addAttribute("jogadores",RepositorySession.getInstance().getArraJogadores() );
        pModel.addAttribute("jogador",new Jogador(0,"Roberta" , "Papel") );
        return "jogoIndex";
    }



}