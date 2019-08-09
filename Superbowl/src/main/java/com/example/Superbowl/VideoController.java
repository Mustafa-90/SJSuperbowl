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

    @Autowired
    CategoryRepository cr;

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

            now = "";

            int page = 0;
            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(allVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, allVideos);

            int[] pages = toArray(pageCount);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
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

            now = "";

            int page = 0;
            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(allVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, allVideos);

            int[] pages = toArray(pageCount);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);

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

        String url = "search/";

        List<Video> listOfVideos = vr.searchVideo(searchBar);
        model.addAttribute("listOfVideos", listOfVideos);
        now = searchBar;

        int page = 1;
        int pageSize = 4;
        int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
        List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

        int[] pages = toArray(pageCount);
        model.addAttribute("vid", vid);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("showPrev", page > 1);
        model.addAttribute("showNext", page < pageCount);

        model.addAttribute("url", url);

        return "search";
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/genre/{genre}")
    public String getGenre(Model model, HttpSession session, @PathVariable String genre) {
        String url = "genre/" + genre;

        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            now = genre;
            List<Video> listOfVideos = gr.getGenreVideos(now);
            int pageSize = 4;
            int page = 1;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

            int[] pages = toArray(pageCount);


            List<Video> theRightVids = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int z = ((page - 1) * 4) + i;

                if (z < listOfVideos.size()) {
                    theRightVids.add(listOfVideos.get(z));
                }
            }

            model.addAttribute("listOfVideos", theRightVids);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/category/{category}")
    public String getCategory(Model model, HttpSession session, @PathVariable String category) {
        String url = "category/" + category;

        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            now = category;
            List<Video> listOfVideos = cr.getCategoryVideos(now);
            int pageSize = 4;
            int page = 1;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);
            int[] pages = toArray(pageCount);

            List<Video> theRightVids = new ArrayList<>();
            model.addAttribute("listOfVideos", listOfVideos);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/decade/{decade}")
    public String getDecade(Model model, HttpSession session, @PathVariable String decade) {
        String url = "decade/" + decade;
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            now = decade;
            List<Video> listOfVideos = vr.getVideosByDecade(now);
            int pageSize = 4;
            int page = 1;
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


            model.addAttribute("url", url);


            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/search/{page}")
    public String paginationSearch(Model model, HttpSession session, @PathVariable Integer page) {
        String url = "search/";
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> listOfVideos = vr.searchVideo(now);

            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

            int[] pages = toArray(pageCount);

            List<Video> theRightVids = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int z = ((page - 1) * 4) + i;

                if (z < listOfVideos.size()) {
                    theRightVids.add(listOfVideos.get(z));
                }
            }

            model.addAttribute("listOfVideos", theRightVids);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/decade/{year}/{page}")
    public String paginationSearchDecade(Model model, HttpSession session, @PathVariable Integer year, @PathVariable Integer page) {

        String url = "decade/" + year + "/";

        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> listOfVideos = vr.getVideosByDecade(now);

            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

            int[] pages = toArray(pageCount);

            List<Video> theRightVids = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int z = ((page - 1) * 4) + i;

                if (z < listOfVideos.size()) {
                    theRightVids.add(listOfVideos.get(z));
                }
            }

            model.addAttribute("listOfVideos", theRightVids);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }

    private int[] toArray(int num) {
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = i + 1;
        }
        return result;
    }


    /**
     * METODER FÖR PAGINATION
     */

    @GetMapping("/genre/{genre}/{page}")
    public String paginationGenreSearch(Model model, HttpSession session, @PathVariable String genre, @PathVariable Integer page) {

        String url = "genre/" + genre + "/";

        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> listOfVideos = gr.getGenreVideos(now);

            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

            int[] pages = toArray(pageCount);

            List<Video> theRightVids = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int z = ((page - 1) * 4) + i;

                if (z < listOfVideos.size()) {
                    theRightVids.add(listOfVideos.get(z));
                }
            }

            model.addAttribute("listOfVideos", theRightVids);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/category/{category}/{page}")
    public String paginationCategorySearch(Model model, HttpSession session, @PathVariable String category, @PathVariable Integer page) {

        String url = "category/" + category + "/";

        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            List<Video> listOfVideos = cr.getCategoryVideos(now);

            int pageSize = 4;
            int pageCount = (int) Math.ceil(new Double(listOfVideos.size()) / pageSize);
            List<Video> vid = vr.getPage(page - 1, pageSize, listOfVideos);

            int[] pages = toArray(pageCount);

            List<Video> theRightVids = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int z = ((page - 1) * 4) + i;

                if (z < listOfVideos.size()) {
                    theRightVids.add(listOfVideos.get(z));
                }
            }

            model.addAttribute("listOfVideos", theRightVids);
            model.addAttribute("vid", vid);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
            model.addAttribute("showPrev", page > 1);
            model.addAttribute("showNext", page < pageCount);
            model.addAttribute("page", page);

            model.addAttribute("url", url);

            return "search";
        } else {
            return "login";
        }
    }
}
