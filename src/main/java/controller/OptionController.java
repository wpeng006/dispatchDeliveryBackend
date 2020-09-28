package controller;


import model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.OptionService;

@Controller
public class OptionController {

    @Autowired
    private final OptionService optionService;


    @RequestMapping(value = "/option", method = RequestMethod.GET)
    public Option[] getOptions(@RequestParam("from") String from, @RequestParam("to") String to) {
        return optionService.getOptions(from, to);
    }

}

