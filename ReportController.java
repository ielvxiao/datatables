package sys.base.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.base.spring.service.alarminfo.DepartSerchService;
import sys.base.spring.service.alarminfo.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private DepartSerchService departSerchService;
	@Autowired
	private ReportService reportService;
	
    @RequestMapping(value = "/report", method = RequestMethod.GET)
	public String toMap(){
		return "/4mapmanage/report";
	}
    
	@RequestMapping(value = "/getAllUnittype", method = RequestMethod.GET)
	@ResponseBody
	public Object findAllUnittype() {
		return departSerchService.findAllUnittype();
	}
	
	@RequestMapping(value = "/reportcount", method = RequestMethod.GET)
	@ResponseBody
	public Object reportYJXXQSTCount(String startTime, String endTime, String unitType){
		return reportService.reportYJXXQSTCount(startTime, endTime, unitType);
	}
	@RequestMapping(value = "/reportunit", method = RequestMethod.GET)
	@ResponseBody
	public Object reportUnit(String startTime, String endTime, String unitType, String keywordType){
		return reportService.reportUnit(startTime, endTime, unitType, keywordType);
	}
	//个人统计页面跳转
	 @RequestMapping(value = "/personal", method = RequestMethod.GET)
		public String toPersonal(){
			return "/4mapmanage/personal";
		}
	
	//个人统计信息
   @RequestMapping(value="/reportpersonal", method = RequestMethod.GET)
   @ResponseBody
   public Object reportPensonal(String startTime, String endTime, String start, String length, String draw, String name){
	   return reportService.userDealWithCount(startTime, endTime, start, length, draw, name);
   }
}
