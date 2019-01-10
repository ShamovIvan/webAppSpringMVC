package lesson.compshop.dao;

import lesson.compshop.PartnameNotFoundException;
import lesson.compshop.model.Part;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartDaoImpl implements PartDao{
    private static final Logger logger= LoggerFactory.getLogger(PartDaoImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addPart(Part part) {
        Session session=this.sessionFactory.getCurrentSession();
        session.persist(part);
        logger.info("Part successfully updated. Part details: " + part);
    }

    @Override
    public void updatePart(Part part) {
        Session session=this.sessionFactory.getCurrentSession();
        session.update(part);
        logger.info("Part successfully updated.Part details: "+ part);
    }

    @Override
    public void removePart(int id) {
        Session session=this.sessionFactory.getCurrentSession();
        Part part=(Part)session.load(Part.class,new Integer(id));
        if(part!=null){
            session.delete(part);
            logger.info("Part successfully removed. Part details: "+ part);

        }
    }

    @Override
    public Part getPartById(int id) {
        Session session=this.sessionFactory.getCurrentSession();
        Part part = (Part)session.load(Part.class, new Integer(id));
        logger.info("Part successfully loaded. Part details: " + part);
        return part;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Part> listParts() {
        Session session=this.sessionFactory.getCurrentSession();
        List<Part> partList = session.createQuery("from Part").list();
        for(Part part:partList){
            logger.info("Part list: "+ part);
        }
        return partList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Part> listParts(String param) {
        String query;

        if (param == null || param.equals("all")) {
            query = "FROM Part";
        } else if (param.equals("needOnly")) {
            query = "FROM Part E WHERE E.isNeed = true";
        } else if (param.equals("optionOnly")) {
            query = "FROM Part E WHERE E.isNeed = false";
        } else {
            query = "FROM Part E WHERE E.partName like '%"+ param +"%'";
        }

        Session session = this.sessionFactory.getCurrentSession();
        List<Part> partList = session.createQuery(query).list();

        for (Part part : partList) {
            logger.info("Part list: " + part);
        }

        return partList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Part findPartByName(String partname) {

        Part part = null;

        Session session=this.sessionFactory.getCurrentSession();
        try {
            System.out.println("partname in partdaoimpl is:" + partname);
            List<Part> partList = session.createQuery("FROM Part E WHERE E.name like '%"+ partname +"%'").list();
            if(partList.size()==0){
                throw new PartnameNotFoundException("Sorry....part :" + partname + ": not found");
            }
            else part = partList.get(0);

        }
        catch (PartnameNotFoundException e){
            e.printStackTrace();
        }
        logger.info("Part successfully find by Name. Part details: "+ part);

        return part;
    }
}
