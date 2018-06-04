package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.Attendance;
import pl.edu.agh.ki.mwo.model.Course;
import pl.edu.agh.ki.mwo.model.DegreeCourse;
import pl.edu.agh.ki.mwo.model.Group;
import pl.edu.agh.ki.mwo.model.Student;

public class DatabaseConnector {
	
	protected static DatabaseConnector instance = null;
	
	public static DatabaseConnector getInstance() {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}
		
		Session session;
	
		protected DatabaseConnector() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void teardown() {
		session.close();
		HibernateUtil.shutdown();
		instance = null;
	}
	
	public Iterable<DegreeCourse> getSchools() {
		String hql = "FROM DegreeCourse";
		Query query = session.createQuery(hql);
		List schools = query.list();
		
		return schools;
	}
	
	public void addSchool(DegreeCourse school) {
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}
	
	public void deleteSchool(String schoolId) {
		String hql = "FROM DegreeCourse S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<DegreeCourse> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (DegreeCourse s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	public Iterable<Group> getSchoolClasses() {
		String hql = "FROM Group";
		Query query = session.createQuery(hql);
		List schoolClasses = query.list();
		
		return schoolClasses;
	}
	
	
	
	public void addSchoolClass(Group schoolClass, String schoolId) {
		String hql = "FROM DegreeCourse S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<DegreeCourse> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(schoolClass);
		} else {
			DegreeCourse school = results.get(0);
			school.addGroup(schoolClass);
			session.save(school);
		}
		transaction.commit();
	}
	
	public void deleteSchoolClass(String schoolClassId) {
		String hql = "FROM Group S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<Group> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Group s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	//Students
	public Iterable<Student> getStudents() {
		String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List students = query.list();
		
		return students;
	}
	
	public Student getStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id="+studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		return results.get(0);
	}
	
	public void addStudent(Student student, String schoolClassId) {
		String hql = "FROM Group S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<Group> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(student);
		} else {
			Group schoolClass = results.get(0);
			schoolClass.addStudent(student);
			session.save(schoolClass);
		}
		transaction.commit();
	}
	
	public void saveModifiedStudent(Student student, String schoolClassId) {
		String hql = "FROM Group S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<Group> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(student);
		} else {
			Group schoolClass = results.get(0);
			schoolClass.addStudent(student);
			session.save(schoolClass);
		}
		transaction.commit();
	}
	
	
	public long getSpecificStudentClass(String studentId) {
		String hql = "SELECT S FROM Group S  INNER JOIN S.students student WHERE student.id="+studentId;
		Query query = session.createQuery(hql);
		Group sclass = (Group) query.list().get(0);
		return sclass.getId();
	}

	
	public void deleteStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Student s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	
	
//GROUP
	public void addGroup(Group gr) {
		Transaction transaction = session.beginTransaction();
		session.save(gr);
		transaction.commit();
	}

	public Iterable<Group> getGroups() {
		String hql = "FROM Group";
		Query query = session.createQuery(hql);
		List groups = query.list();
		return groups;
		
	}

	public void deleteGroup(String groupId) {
		String hql = "FROM Group G WHERE G.id=" + groupId;
		Query query = session.createQuery(hql);
		List<Group> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Group g : results) {
			session.delete(g);
		}
		transaction.commit();
	}
//
	public Iterable<Group> getCourses() {
		String hql = "FROM Course";
		Query query = session.createQuery(hql);
		List courses = query.list();
		return courses;
	}

	public Course getCourse(String courseId) {
		String hql = "FROM Course C WHERE C.id="+courseId;
		Query query = session.createQuery(hql);
		List<Course> results = query.list();
		return results.get(0);
	}

	public void addCourse(Course course, String courseId) {
		String hql = "FROM Course C WHERE C.id=" + courseId;
		Query query = session.createQuery(hql);
		List<Course> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(course);
		} else {
			Course c = results.get(0);
			course.addCourse(course);
			session.save(schoolClass);
		}
		transaction.commit();
		
	}

	public void deleteCourse(String courseId) {
		String hql = "FROM Course C WHERE C.id=" + courseId;
		Query query = session.createQuery(hql);
		List<Course> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Course c : results) {
			session.delete(c);
		}
		transaction.commit();
		
	}

	public Iterable<Attendance> getAttendances() {
		String hql = "FROM Attendance";
		Query query = session.createQuery(hql);
		List attendances = query.list();
		return attendances;
	}

	public Attendance getAttendance(String attendanceId) {
		String hql = "FROM Attendance A WHERE A.id="+attendanceId;
		Query query = session.createQuery(hql);
		List<Attendance> results = query.list();
		return results.get(0);
	}

	public void addAttendance(Attendance at, String id) {
		String hql = "FROM Attendance A WHERE A.id=" + id;
		Query query = session.createQuery(hql);
		List<Attendance> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(at);
		} else {
			Attendance a = results.get(0);
			a.addAttendance(at);
			session.save(at);
		}
		transaction.commit();
		
	}

	public void deleteAttendance(String id) {
		String hql = "FROM Attendance A WHERE A.id=" + id;
		Query query = session.createQuery(hql);
		List<Attendance> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Attendance a : results) {
			session.delete(a);
		}
		transaction.commit();
		
	}




}


