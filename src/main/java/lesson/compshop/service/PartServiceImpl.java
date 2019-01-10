package lesson.compshop.service;

import lesson.compshop.PartnameNotFoundException;
import lesson.compshop.dao.PartDao;
import lesson.compshop.model.Part;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {
    private PartDao partDao;

    public void setPartDao(PartDao partDao) {
        this.partDao = partDao;
    }

    @Override
    @Transactional
    public void addPart(Part part) {
        this.partDao.addPart(part);
    }

    @Override
    @Transactional
    public void updatePart(Part part) {
        this.partDao.updatePart(part);
    }

    @Override
    @Transactional
    public void removePart(int id) {
        this.partDao.removePart(id);
    }

    @Override
    @Transactional
    public Part getPartById(int id) {
        return this.partDao.getPartById(id);
    }

    @Override
    @Transactional
    public List<Part> listParts() {
        return this.partDao.listParts();
    }

    @Override
    @Transactional
    public List<Part> listParts(String param) {
        return this.partDao.listParts(param);
    }

    @Override
    @Transactional
    public Part loadPartByPartname(String partname) throws PartnameNotFoundException, DataAccessException {
        return this.partDao.findPartByName(partname);
    }
}
