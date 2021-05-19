package com.javaee.ch17.dao;

import java.util.List;
import java.util.Map;

import com.javaee.ch17.po.Student;
import com.javaee.ch17.utils.PageBean;
import com.javaee.ch17.vo.FieldExistValidVO;

public interface StudentDao {

	public int insertStudent(Student student);
	public int updateStudent(Student student);
	public int deleteStudent(int id);
	public Student findStudentById(int id);
	public List<Student> manageStudents();
	public int getOtherSameCount(FieldExistValidVO fieldExistValidVO);
	public int getSameCount(FieldExistValidVO fieldExistValidVO);
	//不带条件的分页查询
	public List<Student> findStudentsByPage(Map<String,Object> map);
	public int getStudentsCount();
	//带条件的分页查询
	public List<Student> findStudentsByConditionPage(Map<String,Object> map);
	public int getStudentsCountByConditionPage(Map<String,Object> map);

}
