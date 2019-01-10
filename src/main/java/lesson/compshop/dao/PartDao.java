package lesson.compshop.dao;

import lesson.compshop.model.Part;

import java.util.List;

public interface PartDao {
    void addPart(Part part);
    void updatePart(Part part);
    void removePart(int id);
    Part getPartById(int id);
    List<Part> listParts();
    List<Part> listParts(String param);
    Part findPartByName(String partname);
}
