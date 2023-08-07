package dev.InnocentUdo.Static_web_application_with_SpringBoot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class WebController {

    @GetMapping("about")
    public ModelAndView about(){
        return new ModelAndView("about");
    }

    @GetMapping("contact")
    public ModelAndView contact(){
        return new ModelAndView("contact");
    }

    @GetMapping("index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("project")
    public ModelAndView project(){
        return new ModelAndView("project");
    }

    @GetMapping("service")
    public ModelAndView service(){
        return new ModelAndView("service");
    }

    @GetMapping("testimonial")
    public ModelAndView testimonial(){
        return new ModelAndView("testimonial");
    }
}
