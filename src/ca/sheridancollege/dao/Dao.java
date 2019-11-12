package ca.sheridancollege.dao;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ca.sheridancollege.beans.Tasks;




public class Dao {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();
    /**
     * This method is used to save or update an existing Task
     * @param customer
     */
	public void saveOrUpdateTask(Tasks task) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(task);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * This method retrieves all tasks from the database in the form a List
	 */
	public List<Tasks> getTaskList() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Tasks> criteria = criteriaBuilder.createQuery(Tasks.class);
		Root<Tasks> root = criteria.from(Tasks.class);
		List<Tasks> taskList = session.createQuery(criteria).getResultList();
		session.getTransaction().commit();
		session.close();
		return taskList;
	}
	/**
	 * This mehtod is used to select a task by passing id as a parameter
	 * @param id
	 * @return list of customer with matching id
	 */
	public List<Tasks> getTaskById(int id) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createNamedQuery("Tasks.byId");
		query.setParameter("id", id);
		List<Tasks> taskList = query.getResultList();
		session.getTransaction().commit();
		session.close();
		return taskList;

	}
	public List<Tasks> getTaskByName(String name) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createNamedQuery("Tasks.name");
		query.setParameter("name", name);
		List<Tasks> taskList = query.getResultList();
		session.getTransaction().commit();
		session.close();
		return taskList;

	}
    /**
     * This method is used to delete a task by passing id as a parameter
     * @param id
     */
	 public void deleteTaskById(int id) {
		    Session session = sessionFactory.openSession();
			session.beginTransaction();
			Tasks toDelete = (Tasks) session.get(Tasks.class, id);
			session.delete(toDelete);
			session.getTransaction().commit();
			session.close();
	 }

}
