package com.appsdeveloperblog.ws.clients.photoappwebclient.controllers;

import com.appsdeveloperblog.ws.clients.photoappwebclient.response.AlbumRest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
public class AlbumsController {

    final OAuth2AuthorizedClientService oAuth2ClientService;
    final WebClient webClient;

    public AlbumsController(OAuth2AuthorizedClientService oAuth2ClientService, WebClient webClient) {
        this.oAuth2ClientService = oAuth2ClientService;
        this.webClient = webClient;
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
        String url = "http://localhost:8082/albums";

        List<AlbumRest> albums = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>() {
                })
                .block();

        model.addAttribute("albums", albums);

        return "albums";
    }

}
