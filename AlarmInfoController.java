package sys.base.spring.controller.alarminfo;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.base.spring.service.alarminfo.MainInfoService;

@Controller
@RequestMapping("/alarminfo")
public class AlarmInfoController {
	 private static final Logger logger = LoggerFactory.getLogger(AlarmInfoController.class);
	@Autowired
   private MainInfoService mainInfoService;
	
    /**
     * jdbcTemplate 使用sql语句查询数据
     * @return
     */
//    @RequestMapping("/alarmsearch")
//    @ResponseBody
//    public Object getInfoBySql(){
//    	
//    	StringBuilder sql1 = new StringBuilder();
//    	
//    	List<Map<String , Object>> list = jdbcTemplate.queryForList(
//"SELECT n.id,n.guid,n.time,a.name AS area,ut.name AS unittype,u.name AS unit,d.name AS depart,ui.user_name AS name,n.summary,n.filename,n.fileaction"
//+" FROM netward n"
//+" LEFT JOIN userinfo ui ON ui.guid=n.user_guid"
//+" LEFT JOIN depart d ON ui.unit_guid=d.guid"
//+" LEFT JOIN unit u ON u.guid=d.unit_guid"
//+" LEFT JOIN unittype ut ON u.unit_type_guid=ut.guid"
//+" LEFT JOIN area a ON a.guid=u.area_guid"
//+" LEFT JOIN keyword k ON k.depart_guid=d.guid");
//    	return list;
//    }
    
    @RequestMapping(value="/alarmsearch", method = RequestMethod.GET)
    @ResponseBody
    public Object getInfoBySql(String dealwith, String mark, String deleted, String draw, String start, String length,String time,String title){
 
    	return mainInfoService.findMainInfo(dealwith, mark, deleted,draw,start,length,time,title);
    }
    

    
    @RequestMapping(value="/unconfirms", method = RequestMethod.GET)
    @ResponseBody
    public Object unConfirmSecret(String ids){
    	logger.info("单个或者多个ID:"+ids);
    	return mainInfoService.unConfirmSecret(ids);
    }
    
    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(String ids){
    	logger.info("单个或者多个ID:"+ids);
    	return mainInfoService.deleteChecked(ids);
    }
    //获取用户状态
    @RequestMapping(value="/userstatusinfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserstatus(String onlineflag, String depart_guid, String username, String area_guid, String unit_guid){
    	logger.info("在线状态:"+onlineflag);
    	logger.info("用户名:"+username);
    	logger.info("地区GUID:"+area_guid);
    	logger.info("单位GUID:"+unit_guid);
    	logger.info("部门UGID:"+depart_guid);
    	return mainInfoService.online(onlineflag, depart_guid, username, area_guid, unit_guid);
    }
    
    @RequestMapping(value="/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object getDetail(String guid){
    	logger.info("信息guid:"+guid);
    	return mainInfoService.detail(guid);
    }
    
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    @ResponseBody
    public void edit(String ids, String dealwith, String deleted){
    	String[] idlist = ids.split(",");
    	for (int i = 0; i < idlist.length; i++) {
			if(mainInfoService.isDealWith(idlist[i])){
				mainInfoService.updateDealWith(idlist[i],dealwith,deleted);
			}else{
				mainInfoService.insterDealWith(idlist[i],dealwith,deleted);
			}
		}
    }
    
}
