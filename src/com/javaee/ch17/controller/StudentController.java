package com.javaee.ch17.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.javaee.ch17.utils.PageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaee.ch17.po.Student;
import com.javaee.ch17.service.StudentService;
import com.javaee.ch17.utils.RandomValidateCode;
import com.javaee.ch17.vo.FieldExistValidVO;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;

	private int pageSize = 3;

	/*
	@RequestMapping("manageStudents")
	public String manageStudents(HttpServletRequest request) {
		List<Student> listStudents = studentService.manageStudents();
		request.setAttribute("listStudents", listStudents);
		return "manageStudents";
	}
	 */
	/*
	@RequestMapping("manageStudents")// 不考虑查询条件的分页
	public String manageStudents(HttpServletRequest request,@RequestParam(defaultValue = "0",required = false) int currentPage) {
		int pageSize = 3;
		PageBean pageBean = studentService.findStudentsByPage(currentPage,pageSize);
		List<Student> listStudents = studentService.manageStudents();
		request.setAttribute("pageBean", pageBean);
		return "manageStudents";
	}
	 */

	@RequestMapping("toInsertStudent")
	public String toInsertStudent() {
		return "insertStudent";
	}
	@RequestMapping("doInsertStudent")
	public String doInsertStudent(HttpServletRequest request, Student student) {
		String theMessage = "";
		if(studentService.insertStudent(student)==1) {
			theMessage = "添加成功！";
		}else {
			theMessage = "<script>alert('添加失败！');</script>";
		}
		request.setAttribute("theMessage", theMessage);
		return "forward:toInsertStudent";
	}

	@RequestMapping("toUpdateStudent")
	public String toUpdateStudent(HttpServletRequest request, int id) {
		Student student = studentService.findStudentById(id);
		request.setAttribute("student", student);
		return "updateStudent";
	}
	@RequestMapping("doUpdateStudent")
	public String doUpdateStudent(HttpServletRequest request, Student student, String checkCode, Date theEntryTimeOriginal, Date theModifyTimeOriginal) {
		String theMessage = "";
		if(checkCode.equalsIgnoreCase((String)request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY))) { // 验证码正确
			if(student.getCreationTime()==null) {// 提交时未额外设置录入时间(输入框是留空)，则表示不变
				student.setCreationTime(theEntryTimeOriginal);
			}
			if(student.getLastChangeTime()==null) {// 提交时未设置修改时间，则表示不变(曾修改过)或用当前日期值填入(第一次修改)
				if(theModifyTimeOriginal == null) { // 旧的修改时间是空，表明之前未修改过。此次提交修改时又未新指定值，当然要将修改时间改成实时时间
					student.setLastChangeTime(new Date());
				} else { // 旧的修改时间不是空，则表示之前修改过，此次更新操作不打算修改这个lastChangeTime字段值。
					student.setLastChangeTime(theModifyTimeOriginal);
				}
			}
			if(studentService.updateStudent(student)==1) {
				theMessage = "更新成功！";
			}else {
				theMessage = "<script>alert('更新失败！');</script>";
			}

		} else { // 验证码错误！
			theMessage = "<script>alert('验证码错误！');</script>";
		}
		request.setAttribute("theMessage", theMessage);
		return "forward:toUpdateStudent?id="+student.getId();
	}
	@RequestMapping("deleteStudent")
	public String deleteStudent(HttpServletRequest request, int id) {
		String theMessage ="";
		if(studentService.deleteStudent(id)==1) {
			theMessage = "删除id为“" + id + "”的记录成功！";
		} else {
			theMessage = "<script>alert('删除id为“" + id + "”的记录失败！');</script>";
		}
		request.setAttribute("theMessage", theMessage);
		return "forward:/manageStudents";
	}
	@ResponseBody
	@RequestMapping("isOtherExistSame")
	public boolean isOtherExistSame(@RequestBody FieldExistValidVO fieldExistValidVO) {
		
		return studentService.getOtherSameCount(fieldExistValidVO)>0;
	}
	@ResponseBody
	@RequestMapping("isExistSame")
	public boolean isExistSame(@RequestBody FieldExistValidVO fieldExistValidVO) {
		
		return studentService.getSameCount(fieldExistValidVO)>0;
	}

	@RequestMapping("studentDetail")
	public String studentDetail(HttpServletRequest request, int id) {
		request.setAttribute("student", studentService.findStudentById(id));
		return "studentDetail";
	}

	@RequestMapping("manageStudents") // 考虑了查询条件的分页查询
	public String manageStudentsByConditions(HttpServletRequest request, @RequestParam(value = "currentPage",defaultValue = "0",required = false) int currentPage){
		HttpSession session = request.getSession();
		if (currentPage <= 0){
			session.removeAttribute("queryStudentCondition");
			session.removeAttribute("addTimeStart");
			session.removeAttribute("addTimeEnd");
			session.removeAttribute("changeTimeStart");
			session.removeAttribute("changeTimeEnd");
			currentPage = 1;
			request.setAttribute("pageBean",studentService.findStudentsByPage(currentPage,pageSize));
		}else {
			Date addTimeStart = (Date)session.getAttribute("addTimeStart");
			Date addTimeEnd = (Date)session.getAttribute("addTimeEnd");
			Date changeTimeStart = (Date)session.getAttribute("changeTimeStart");
			Date changeTimeEnd = (Date)session.getAttribute("changeTimeEnd");
			Student student = (Student) session.getAttribute("queryStudentCondition");
			if (student == null){
				request.setAttribute("pageBean",studentService.findStudentsByPage(currentPage,pageSize));
			}else if(addTimeStart!=null||addTimeEnd!=null||changeTimeEnd!=null||changeTimeStart!=null){
				Map<String,Object> dateMap = new HashMap<>();
				dateMap.put("addTimeStart",addTimeStart);
				dateMap.put("addTimeEnd",addTimeEnd);
				dateMap.put("changeTimeStart",changeTimeStart);
				dateMap.put("changeTimeEnd",changeTimeEnd);
				request.setAttribute("pageBean",studentService.findStudentsByConditionPage(student,currentPage,pageSize,dateMap));
			}else {
				request.setAttribute("pageBean", studentService.findStudentsByConditionPage(student, currentPage, pageSize)); //回显分页数据
			}
		}
		return "manageStudents";
	}


	@RequestMapping("findStudentByIdOrSomeStudentsWithPage")
	public String findStudentByIdOrSomeStudentsWithPage(HttpServletRequest request, Student student,
														Date addTimeStart,Date addTimeEnd,Date changeTimeStart,Date changeTimeEnd){
		int id = student.getId();
		if (id>0){// 用户在查询表单中输入了id，表示是精确查询
			return "redirect:studentDetail?id="+id;
		}else if (addTimeStart!=null||addTimeEnd!=null||changeTimeEnd!=null||changeTimeStart!=null){
			request.getSession().setAttribute("queryStudentCondition", student);
			Map<String,Object> dateMap = new HashMap<>();
			dateMap.put("addTimeStart",addTimeStart);
			dateMap.put("addTimeEnd",addTimeEnd);
			dateMap.put("changeTimeStart",changeTimeStart);
			dateMap.put("changeTimeEnd",changeTimeEnd);
//			request.setAttribute("dateMap",dateMap);
			request.getSession().setAttribute("addTimeStart",addTimeStart);
			request.getSession().setAttribute("addTimeEnd",addTimeEnd);
			request.getSession().setAttribute("changeTimeStart",changeTimeStart);
			request.getSession().setAttribute("changeTimeEnd",changeTimeEnd);
			PageBean<Student> pageBean = studentService.findStudentsByConditionPage(student,1,pageSize,dateMap);
			request.setAttribute("pageBean",pageBean);
			System.out.println(pageBean.getLists().get(0).getUserName());
			System.out.println(pageBean.getLists().get(1).getUserName());
			System.out.println(pageBean.getLists().get(2).getUserName());
			return "manageStudents";
		}else {// 提交时，id输入框中无值，表示模糊查询 不管id输入框的值
			request.getSession().setAttribute("queryStudentCondition", student);
			request.setAttribute("pageBean", studentService.findStudentsByConditionPage(student, 1, pageSize));
			return "manageStudents";
		}
	}
}
