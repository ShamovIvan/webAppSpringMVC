package lesson.compshop.controller;
import lesson.compshop.PartnameNotFoundException;
import lesson.compshop.model.Part;
import lesson.compshop.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PartController {
    public PartService partService;

    private static String currentParam = "all";
    private static Integer currentPage = 1;
    private static int currentId = 0;

    @Autowired(required = true)
    @Qualifier(value = "PartService")
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @RequestMapping(value = "/parts/add",method = RequestMethod.POST)
    public String addPart(@ModelAttribute("part") Part part){
        if(part.getId()==0){
            this.partService.addPart(part);
        }
        else {
            this.partService.updatePart(part);
        }
        return "redirect:/parts";
    }

    @RequestMapping("/remove/{id}")
    public String removePart(@PathVariable("id") int id){
        this.partService.removePart(id);
        return "redirect:/parts";
    }
    @RequestMapping("/edit/{id}")
    public String editPart(@PathVariable("id")int id, Model model){
        model.addAttribute("part",this.partService.getPartById(id));
        model.addAttribute("listParts", this.partService.listParts());
        return "parts";
    }
    @RequestMapping(value = "/partdata/{id}",method = RequestMethod.GET)
    public String partData(@PathVariable("id")int id, Model model){
        model.addAttribute("part",this.partService.getPartById(id));
        return "partdata";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String searchPart (HttpServletRequest request, Model model)throws PartnameNotFoundException {
        String partname = request.getParameter("partname");
        System.out.println("Username in controller: "+partname );
        Part part = this.partService.loadPartByPartname(partname);
        String result;
        if(part==null){
            result="notfound";
        }
        else result="partdata";
        model.addAttribute("part", part);
        return result;
    }
    /*
    @RequestMapping(value="/parts",method = RequestMethod.GET)
    public ModelAndView listOfParts(@RequestParam(required = false) Integer page) {
        ModelAndView modelAndView = new ModelAndView("parts");
        modelAndView.addObject("part",new Part());
        List<Part> parts = partService.listParts();
        System.out.println("Parts-list:"+parts);
        PagedListHolder<Part> pagedListHolder = new PagedListHolder<Part>(parts);
        pagedListHolder.setPageSize(10);
        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());


        if(page==null || page < 1 || page > pagedListHolder.getPageCount())
            page = 1;


        modelAndView.addObject("page", page);

        if(page>pagedListHolder.getPageCount()){
            pagedListHolder.setPage(page);
            modelAndView.addObject("listParts", pagedListHolder.getPageList());
        }

        else if(page <= pagedListHolder.getPageCount()) {

            pagedListHolder.setPage(page-1);
            modelAndView.addObject("listParts", pagedListHolder.getPageList());
        }

        return modelAndView;
    }
    */

    @RequestMapping(value="/parts")
    public ModelAndView listOfParts(@RequestParam(required = false) Integer page, @RequestParam(required = false) String param) {
        ModelAndView modelAndView = new ModelAndView("parts");

        if (currentId != 0) {
            modelAndView.addObject("part", this.partService.getPartById(currentId));
            currentId = 0;
        } else {
            modelAndView.addObject("part", new Part());
        }

        if (page!=null) {
            currentPage = page;
        }

        if (param != null) {
            if (param.equals("")) {
                param = "all";
            }
            currentParam = param;
            currentPage = 1;
        }

        int availableComputers = countComputers();

        modelAndView.addObject("availableComputers", availableComputers);


        List<Part> parts = partService.listParts(currentParam);

        PagedListHolder<Part> pagedListHolder = new PagedListHolder<>(parts);
        pagedListHolder.setPageSize(10);
        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());

        if(currentPage == null || currentPage < 1 || currentPage > pagedListHolder.getPageCount()){
            currentPage=1;
        }
        modelAndView.addObject("page", currentPage);
        if(currentPage == null || currentPage < 1 || currentPage > pagedListHolder.getPageCount()){
            pagedListHolder.setPage(0);
            modelAndView.addObject("listParts", pagedListHolder.getPageList());
        }
        else if(currentPage <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(currentPage-1);
            modelAndView.addObject("listParts", pagedListHolder.getPageList());
        }

        return modelAndView;
    }

    private int countComputers() {
        List<Part> needParts = partService.listParts("needOnly");
        int count = 0;
        if (!needParts.isEmpty()) {
            count = needParts.get(0).getNumber();
            for (Part part : needParts) {
                if (part.getNumber() < count) {
                    count = part.getNumber();
                }
            }
        }

        return count;
    }
}
