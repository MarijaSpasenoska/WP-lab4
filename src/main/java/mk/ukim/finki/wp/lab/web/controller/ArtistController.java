package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArtistController {
    private String selectedId = "";
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist")
    public String getArtist(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("listedArtists", this.artistService.listArtists());
        model.addAttribute("trackId", selectedId);
        model.addAttribute("bodyContent", "artistList");
        return "master-template";
    }

    @PostMapping("/artist")
    public String postArtist(@RequestParam String trackId){
        this.selectedId = trackId;
        return "redirect:/artist";
    }
}
