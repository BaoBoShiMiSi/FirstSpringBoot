package com.example.wenda.controller;

import com.example.wenda.aspect.LogAspect;
import com.example.wenda.modle.Question;
import com.example.wenda.modle.ViewObject;
import com.example.wenda.sevice.QuestionService;
import com.example.wenda.sevice.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @RequestMapping(path = {"/user/{userCode}"}, method = RequestMethod.GET)
    //@ResponseBody
    public String indexByUserId(Model model, @PathVariable("userCode") int userId) {

        model.addAttribute("ovs", getViewObjectList(userId, 0, 10));
        return "index";
    }

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    //@ResponseBody
    public String index(Model model) {

        model.addAttribute("ovs", getViewObjectList(0, 0, 10));
        return "index";
    }

    private List<ViewObject> getViewObjectList(int userId, int offset, int limit) {

        List<Question> questions = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> ovs = new ArrayList<>();
        for (Question question : questions) {
            ViewObject ov = new ViewObject();
            ov.set("question", question);
            ov.set("user", userService.getUser(question.getUserId()));
            ovs.add(ov);
        }
        return ovs;
    }
}
