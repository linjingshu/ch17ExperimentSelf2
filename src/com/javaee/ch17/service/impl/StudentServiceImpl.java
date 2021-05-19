package com.javaee.ch17.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaee.ch17.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.ch17.dao.StudentDao;
import com.javaee.ch17.po.Student;
import com.javaee.ch17.service.StudentService;
import com.javaee.ch17.vo.FieldExistValidVO;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;
	
	@Override
	public int insertStudent(Student student) {
		
		return studentDao.insertStudent(student);
	}

	@Override
	public int updateStudent(Student student) {
		return studentDao.updateStudent(student);
	}

	@Override
	public int deleteStudent(int id) {
		return studentDao.deleteStudent(id);
	}

	@Override
	public Student findStudentById(int id) {
		return studentDao.findStudentById(id);
	}

	@Override
	public List<Student> manageStudents() {
		return studentDao.manageStudents();
	}

	@Override
	public int getOtherSameCount(FieldExistValidVO fieldExistValidVO) {
		return studentDao.getOtherSameCount(fieldExistValidVO);
	}

	@Override
	public int getSameCount(FieldExistValidVO fieldExistValidVO) {
		return studentDao.getSameCount(fieldExistValidVO);
	}

	@Override
	public PageBean<Student> findStudentsByPage(int currentPage, int pageSize) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		PageBean<Student> pageBean = new PageBean<Student>();

		//每页显示的数据
		//int pageSize=2; // 固定，不太好。有的记录在不同情景模块中，每页允许显示的最大记录数很可能不同。
		pageBean.setPageSize(pageSize);

		//封装总记录数
		int totalCount = getStudentsCount();// 将来要考虑分页显示出模糊查询的结果集
		pageBean.setTotalCount(totalCount);

		//封装总页数
		double tc = totalCount;
		Double num =Math.ceil(tc/pageSize);//向上取整  28/10 = 2.8 最终是3
		int totalPage = num.intValue();
		pageBean.setTotalPage(totalPage);

		//封装当前页数  (要注意对删除操作后又返回分页页面时对当前页currentPage的影响，即拉回最新的[1, totalPage]。这才是合法的。)
		if(totalPage <= 0) {//结果集数为0
			currentPage = 1;
		}else{
			if(currentPage < 1){
				currentPage = 1;
			}else if (currentPage>totalPage){
				currentPage = totalPage;
			}
		}

		pageBean.setCurrPage(currentPage);

		map.put("start",(currentPage-1)*pageSize);
		map.put("size", pageSize);
		//封装每页显示的数据
		List<Student> lists = studentDao.findStudentsByPage(map);

		pageBean.setLists(lists);

		return pageBean;
	}

	@Override
	public int getStudentsCount() {
		return studentDao.getStudentsCount();
	}

	@Override
	public PageBean<Student> findStudentsByConditionPage(Student student, int currentPage, int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean<Student> pageBean = new PageBean<>();
		//每页显示的数据
		//int pageSize=2; // 固定，不太好。有的记录在不同情景模块中，每页允许显示的最大记录数很可能不同。
		pageBean.setPageSize(pageSize);
		//封装总记录数
		map.put("student", student);
		int totalCount = studentDao.getStudentsCountByConditionPage(map);// 将来要考虑分页显示出模糊查询的结果集
		System.out.println("总记录数："+totalCount);
		pageBean.setTotalCount(totalCount);
		if(totalCount>0) {
			//封装总页数
			double tc = totalCount;
			Double num =Math.ceil(tc/pageSize);//向上取整  28/10 = 2.8 最终是3
			int totalPage = num.intValue();
			pageBean.setTotalPage(totalPage);

			//封装当前页数  (要注意对删除操作后又返回分页页面时对当前页currentPage的影响，即拉回最新的[1, totalPage]。这才是合法的。)
			if(currentPage<1)
				currentPage=1;
			else if(currentPage > totalPage) {
				currentPage=totalPage;
			}

			pageBean.setCurrPage(currentPage);

			System.out.println("当前页号："+currentPage);
			map.put("start",(currentPage-1)*pageSize);
			map.put("size", pageBean.getPageSize());
			//封装每页显示的数据
			List<Student> lists = studentDao.findStudentsByConditionPage(map);
			pageBean.setLists(lists);
		} else{
			pageBean.setLists(null);
		}
		return pageBean;
	}

	@Override
	public PageBean<Student> findStudentsByConditionPage(Student student, int currentPage, int pageSize, Map<String, Object> dateMap) {
		Map<String,Object> map = new HashMap<>();
		PageBean<Student> pageBean = new PageBean<>();
		//每页显示的数据
		//int pageSize=2; // 固定，不太好。有的记录在不同情景模块中，每页允许显示的最大记录数很可能不同。
		pageBean.setPageSize(pageSize);
		//封装总记录数
		map.put("student", student);
		map.putAll(dateMap);
		int totalCount = studentDao.getStudentsCountByConditionPage(map);// 将来要考虑分页显示出模糊查询的结果集
		System.out.println("总记录数："+totalCount);
		pageBean.setTotalCount(totalCount);
		if(totalCount>0) {

			//封装总页数
			double tc = totalCount;
			Double num =Math.ceil(tc/pageSize);//向上取整  28/10 = 2.8 最终是3
			int totalPage = num.intValue();
			pageBean.setTotalPage(totalPage);

			//封装当前页数  (要注意对删除操作后又返回分页页面时对当前页currentPage的影响，即拉回最新的[1, totalPage]。这才是合法的。)
			if(currentPage<1)
				currentPage=1;
			else if(currentPage > totalPage) {
				currentPage=totalPage;
			}

			pageBean.setCurrPage(currentPage);

			System.out.println("当前页号："+currentPage);
			map.put("start",(currentPage-1)*pageSize);
			map.put("size", pageBean.getPageSize());

			//封装每页显示的数据
			List<Student> lists = studentDao.findStudentsByConditionPage(map);
			pageBean.setLists(lists);
		} else{
			pageBean.setLists(null);
		}
		return pageBean;
	}


}
