package analyser.controller;

import analyser.dto.PageData;
import analyser.dto.PageRequest;
import analyser.service.PageAnalyserService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/data")
public class PageAnalyserController {

    @Autowired
    PageAnalyserService pageAnalyserService;

    @RequestMapping(method = RequestMethod.POST, headers = {
            "content-type=application/json"})
    public PageData parsePage(@Valid @RequestBody PageRequest pageRequest) throws InvalidArgumentException {
        return pageAnalyserService.parsePage(pageRequest.getUrl());
    }

    @RequestMapping(method = RequestMethod.GET, headers = {
            "content-type=application/json"})
    public PageData findByURL(@Valid @RequestParam String url) throws InvalidArgumentException {
        return pageAnalyserService.findByUrl(url);
    }
}
