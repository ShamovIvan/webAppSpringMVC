package lesson.compshop.service;

import lesson.compshop.PartnameNotFoundException;
import lesson.compshop.model.Part;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PartService {
    void addPart(Part part);
    void  updatePart(Part part);
    void removePart(int id);
    Part getPartById(int id);
    List<Part> listParts();
    List<Part> listParts(String param);

    Part loadPartByPartname(String partname)throws PartnameNotFoundException, DataAccessException;
}
