package com.javaee.ch17.service;

import java.util.List;
import java.util.Map;

import com.javaee.ch17.po.Student;
import com.javaee.ch17.utils.PageBean;
import com.javaee.ch17.vo.FieldExistValidVO;

public interface StudentService {

	public int insertStudent(Student student);
	public int updateStudent(Student student);
	public int deleteStudent(int id);
	public Student findStudentById(int id);
	public List<Student> manageStudents();	
	public int getOtherSameCount(FieldExistValidVO fieldExistValidVO);
	public int getSameCount(FieldExistValidVO fieldExistValidVO);
	public PageBean<Student> findStudentsByPage(int currentPage, int pageSize);
	public int getStudentsCount();
	public PageBean<Student> findStudentsByConditionPage(Student student, int currentPage, int pageSize);
	public PageBean<Student> findStudentsByConditionPage(Student student, int currentPage, int pageSize,Map<String,Object> dateMap);
}
