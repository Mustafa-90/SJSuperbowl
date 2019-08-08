package com.example.Superbowl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class VideoController {

    @Autowired
    VideoRepository vr;

    @Autowired
    GenreRepository gr;

    static String now = "";

    String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl2;user=dbadmin;password=123123";

    @GetMapping("/login")
    public String getLogin(HttpSession session, Model model) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> allVideos = vr.getAllVideos();
            Collections.shuffle(allVideos);
            model.addAttribute("allVideos", allVideos);
            return "search";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String postLogin(Model model, HttpSession session, @RequestParam String username, @RequestParam String password) {
        String check = vr.loginMethod(username, password);

        if (check.equals("SBindex")) {
            session.setAttribute("username", username);
            model.addAttribute("userLogin", username);

            List<Video> allVideos = vr.getAllVideos();
            Collections.shuffle(allVideos);
            model.addAttribute("allVideos", allVideos);
        }
        return check;
    }

    @GetMapping("/")
    public String getHomepage(HttpSession session, Model model) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> allVideos = vr.getAllVideos();
            Collections.shuffle(allVideos);
            model.addAttribute("allVideos", allVideos);

            return "SBindex";
        } else {
            return "login";
        }
    }

    @GetMapping("/search")
    public String getSearch(HttpSession session, Model model) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> allVideos = vr.getAllVideos();
            model.addAttribute("allVideos", allVideos);
            return "search";
        } else {
            return "login";
        }
    }

    @PostMapping("/search")
    public String postSearch(Model model, @RequestParam String searchBar) {

        List<Video> listOfVideos = vr.searchVideo(searchBar);
        model.addAttribute("listOfVideos", listOfVideos);
        now = searchBar;

        int page = 0;
        int pageSize = 4;
        int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
        List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

        int[] pages = toArray(pageCount);
        model.addAttribute("vid", vid);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("showPrev", page > 1);
        model.addAttribute("showNext", page < pageCount);


        return "search";
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/genre/{genre}")
    public String getActionGenre(Model model, @PathVariable String genre) {
        List<Video> listOfVideos = gr.getGenreVideos(genre);
        model.addAttribute("listOfVideos", listOfVideos);

        return "search";

    }

    @GetMapping("/search/{page}")
    public String book(Model model, @PathVariable Integer page) {

        List<Video> listOfVideos = vr.searchVideo(now);

        int pageSize = 4;
        int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
        List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

        int[] pages = toArray(pageCount);

        model.addAttribute("listOfVideos", listOfVideos);
        model.addAttribute("vid", vid);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("showPrev", page > 1);
        model.addAttribute("showNext", page < pageCount);
        model.addAttribute("page", page);
        return "search";
    }

    private int[] toArray(int num) {
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = i + 1;
        }
        return result;
    }

}
