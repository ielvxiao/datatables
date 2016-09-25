package sys.base.spring.service.alarminfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import sys.base.spring.custom.SecurityUser;
import sys.base.spring.domain.alarminfo.ReturnResult;
import sys.base.spring.utils.*;
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public ReturnResult reportYJXXQSTCount(String startTime, String endTime, String unitType) {
		List<String> timeList = null;
		try {
			timeList = DateUtil.fomart(startTime, endTime);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		List<Map<String, Object>> reportList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < timeList.size() - 1; i++) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT '" + timeList.get(i) + "' AS time,count(*) AS count from netward n");
			sql.append(" LEFT JOIN userinfo ui ON n.user_guid=ui.guid");
			sql.append(" LEFT JOIN unit u ON ui.unit_guid=u.guid");
			sql.append(" LEFT JOIN unittype ut ON ut.guid=u.unittype_guid");
			sql.append(" WHERE 1=1");
			sql.append(" AND n.time>'" + timeList.get(i) + "' and n.time<'" + timeList.get(i + 1) + "' and u.unittype_guid='"
					+ unitType + "'");
			List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql.toString());

			reportList.addAll(resultSet);
		}
		
		ReturnResult result = new ReturnResult();
		result.setRows(reportList);
		return result;
	}

	@Override
	public ReturnResult reportUnit(String startTime, String endTime, String unitType, String keywordType) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.name AS unit,COUNT(*) AS count FROM netward n  ");
		sql.append(" LEFT JOIN userinfo ui ON n.user_guid=ui.guid");
		sql.append(" LEFT JOIN unit u ON u.guid=ui.unit_guid");
		sql.append(" LEFT JOIN keyword k ON n.kw_guid=k.guid WHERE 1=1");
		sql.append(" AND n.time>'" + startTime + "' and n.time<'" + endTime +"' and u.unittype_guid='"+ unitType+"'");
		if(keywordType != null){
			sql.append(" AND k.kwt_guid='"+keywordType+"'");
		}
		sql.append(" group by u.`name` order by count desc");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		
		ReturnResult result = new ReturnResult();
		result.setRows(list);
		return result;
	}

	@Override
	public ReturnResult ReportAreaCount(String area_guid) {
		ReturnResult returnResult = new ReturnResult();
		List<Map<String, Object>> reportList = new ArrayList<Map<String, Object>>();
		StringBuilder sql1 = new StringBuilder();
		sql1.append("SELECT ty.unittype_guid,ty.`name`,ty.unitnum,COUNT(n.guid) as filenum");
		sql1.append(" FROM netward n");
		sql1.append(" LEFT JOIN userinfo ui ON n.user_guid=ui.guid");
		sql1.append(" LEFT JOIN unit u1 ON u1.guid=ui.unit_guid");
		sql1.append(" LEFT JOIN");
		sql1.append(" (SELECT u.unittype_guid,ut.`name`,count(*) as unitnum FROM unit u");
		sql1.append(" LEFT JOIN unittype ut ON ut.guid=u.unittype_guid");
		sql1.append(" GROUP BY u.unittype_guid) ty");
		sql1.append(" ON ty.unittype_guid=u1.unittype_guid");
		sql1.append(" LEFT JOIN area a ON u1.area_guid=a.guid");
		sql1.append(" LEFT JOIN dealwith d ON n.guid=d.netward_guid");
		sql1.append(" where d.dealwith=1 AND d.deleted=0 ");
		if (area_guid != null) {
			sql1.append(" and a.guid=" + area_guid);
		}
		sql1.append(" and u1.unittype_guid=1");
		List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1.toString());
		Map map1 = new HashMap<String, Object>();
		Object obj1_1 = list1.get(0).get("unitnum");
		Object obj1_2 = list1.get(0).get("filenum");
		map1.put("unitnum", obj1_1!=null ? obj1_1 : "0");
		map1.put("filenum",obj1_2!=null ? obj1_2 : "0");
		reportList.add(map1);
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("SELECT ty.unittype_guid,ty.`name`,ty.unitnum,COUNT(n.guid) as filenum");
		sql2.append(" FROM netward n");
		sql2.append(" LEFT JOIN userinfo ui ON n.user_guid=ui.guid");
		sql2.append(" LEFT JOIN unit u1 ON u1.guid=ui.unit_guid");
		sql2.append(" LEFT JOIN");
		sql2.append(" (SELECT u.unittype_guid,ut.`name`,count(*) as unitnum FROM unit u");
		sql2.append(" LEFT JOIN unittype ut ON ut.guid=u.unittype_guid");
		sql2.append(" GROUP BY u.unittype_guid) ty");
		sql2.append(" ON ty.unittype_guid=u1.unittype_guid");
		sql2.append(" LEFT JOIN area a ON u1.area_guid=a.guid");
		sql2.append(" LEFT JOIN dealwith d ON n.guid=d.netward_guid");
		sql2.append(" where d.dealwith=1 AND d.deleted=0 ");
		if (area_guid != null) {
			sql2.append(" and a.guid=" + area_guid);
		}
		sql2.append(" and u1.unittype_guid=2");
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2.toString());
		
		Map map2 = new HashMap<String, Object>();
		Object obj2_1 = list2.get(0).get("unitnum");
		Object obj2_2 = list2.get(0).get("filenum");
		map2.put("unitnum", obj2_1!=null ? obj2_1 : "0");
		map2.put("filenum",obj2_2!=null ? obj2_2 : "0");
		reportList.add(map2);
		
		StringBuilder sql3 = new StringBuilder();
		sql3.append("SELECT ty.unittype_guid,ty.`name`,ty.unitnum,COUNT(n.guid) as filenum");
		sql3.append(" FROM netward n");
		sql3.append(" LEFT JOIN userinfo ui ON n.user_guid=ui.guid");
		sql3.append(" LEFT JOIN unit u1 ON u1.guid=ui.unit_guid");
		sql3.append(" LEFT JOIN");
		sql3.append(" (SELECT u.unittype_guid,ut.`name`,count(*) as unitnum FROM unit u");
		sql3.append(" LEFT JOIN unittype ut ON ut.guid=u.unittype_guid");
		sql3.append(" GROUP BY u.unittype_guid) ty");
		sql3.append(" ON ty.unittype_guid=u1.unittype_guid");
		sql3.append(" LEFT JOIN area a ON u1.area_guid=a.guid");
		sql3.append(" LEFT JOIN dealwith d ON n.guid=d.netward_guid");
		sql3.append(" where d.dealwith=1 AND d.deleted=0 ");
		if (area_guid != null) {
			sql3.append(" and a.guid=" + area_guid);
		}
		sql3.append(" and u1.unittype_guid=3");
		List<Map<String, Object>> list3 = jdbcTemplate.queryForList(sql3.toString());
		
		Map map3 = new HashMap<String, Object>();
		Object obj3_1 = list3.get(0).get("unitnum");
		Object obj3_2 = list3.get(0).get("filenum");
		map3.put("unitnum", obj3_1!=null ? obj3_1 : "0");
		map3.put("filenum",obj3_2!=null ? obj3_2 : "0");
		reportList.add(map3);
		
		StringBuilder sql4_1 = new StringBuilder();
		StringBuilder sql4_2 = new StringBuilder();
		sql4_1.append("SELECT COUNT(*) AS allmessage FROM netward n LEFT JOIN userinfo ui ON n.user_guid=ui.guid WHERE 1=1");
		if (area_guid != null) {
		sql4_1.append(" AND ui.area_guid='"+area_guid+"'");
		}
		List<Map<String, Object>> list4_1 = jdbcTemplate.queryForList(sql4_1.toString());
		
		sql4_2.append("SELECT COUNT(*) AS dealmessage FROM dealwith d LEFT JOIN netward n ON d.netward_guid=n.guid");
		sql4_2.append(" LEFT JOIN userinfo ui ON ui.guid=n.user_guid WHERE 1=1 ");
		if (area_guid != null) {
		sql4_2.append(" AND ui.area_guid ='"+area_guid+"'");
		}
		List<Map<String, Object>> list4_2 = jdbcTemplate.queryForList(sql4_2.toString());
		
		Map mm1 = list4_1.get(0);
		Object obj1 = mm1.get("allmessage");
		Map mm2 = list4_2.get(0);
		Object obj2 =mm2.get("dealmessage");
		//
		
		returnResult.setAlltotal(Long.parseLong(obj1.toString()));
		returnResult.setTotal(Long.parseLong(obj2.toString()));
		
		StringBuilder sql5 = new StringBuilder();
		sql5.append("");
		returnResult.setRows(reportList);
		return returnResult;
	}

	@Override
	public DataTableView userDealWithCount(String startTime, String endTime, String start, String length, String draw, String name) {
		DataTableView dataTableView = new DataTableView();
		StringBuilder sql1 = new StringBuilder();
	    StringBuilder sql4 = new StringBuilder();
	    StringBuilder sql5 = new StringBuilder();
	    StringBuilder sql6 = new StringBuilder();
		//处理的涉密信息
		sql1.append("select d.duser_guid as userid,u.user_name,count(*) as sm from netward n ");
		sql1.append(" left join dealwith d on n.guid=d.netward_guid left join base_user u on u.id=d.duser_guid where d.dealwith = 1 and d.deleted=0");
		if(startTime != null && startTime !=""){
			sql4.append(" and d.time >'"+startTime+" 00:00:00'");
		}
		if(endTime != null && endTime !=""){
			sql4.append(" and d.time <'"+endTime+ " 23:59:59'");
		}
		if(name != null && name != ""){
			sql4.append(" and u.user_name like '%"+name+"%'");
		}
//       if(name != null && name !=""){
//    	   sql4.append(" ");
//       }
		sql4.append(" group by d.duser_guid limit "+start+","+length);
		sql1.append(sql4);
		System.out.println(sql1);
		//处理的非涉密信息
		String sql2 = "select d.duser_guid as userid,u.user_name,count(*) as fsm from netward n " 
				+" left join dealwith d" 
				+" on n.guid=d.netward_guid" 
				+" left join base_user u"
				+" on u.id=d.duser_guid"
				+" where d.dealwith = 2 and d.deleted=0";
		sql2+=sql4;
		System.out.println(sql2);
		//处理的删除操作
		String sql3 = "select d.duser_guid as userid,u.user_name,count(*) as del from netward n " 
				+" left join dealwith d" 
				+" on n.guid=d.netward_guid" 
				+" left join base_user u"
				+" on u.id=d.duser_guid"
				+" where d.deleted = 1";
		sql3+=sql4;
		sql5.append("SELECT DISTINCT d.duser_guid FROM dealwith d left join base_user u on u.id=d.duser_guid where 1=1");
		if(startTime != null && startTime !=""){
			sql6.append(" and d.time >'"+startTime+" 00:00:00'");
		}
		if(endTime != null && endTime !=""){
			sql6.append(" and d.time <'"+endTime+ " 23:59:59'");
		}
		if(name != null && name != ""){
			sql6.append(" and u.user_name like '%"+name+"%'");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1.toString());
		List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql2.toString());
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql3.toString());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list3 =jdbcTemplate.queryForList(sql5.toString());
		List<Map<String, Object>> list4 =jdbcTemplate.queryForList(sql5.append(sql6).toString());
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Map<String, Object> _map = new HashMap<String, Object>();
			
			_map.put("userid", map.get("userid"));
			_map.put("username", map.get("user_name"));
			_map.put("sm", map.get("sm"));
			_map.put("fsm", "0");
			_map.put("del", "0");
			result.add(_map);
		}
		
		for (int i = 0; i < list1.size(); i++) {
			boolean flig = false;
			Map<String, Object> map = list1.get(i);
			Map<String, Object> _map = new HashMap<String, Object>();
			for (int j = 0; j < result.size(); j++) {
				if(map.get("userid").equals(result.get(j).get("userid"))){
					System.out.println(3);
					flig = true;
					_map = result.get(j);
					_map.put("fsm", map.get("fsm"));
					break;
				}
			}
			
			if(!flig){
				System.out.println(4);
				_map.put("userid", map.get("userid"));
				_map.put("username", map.get("user_name"));
				_map.put("sm", "0");
				_map.put("fsm", map.get("fsm"));
				_map.put("del", "0");
				result.add(_map);
			}
			
		}
		
		for (int i = 0; i < list2.size(); i++) {
			boolean flig = false;
			Map<String, Object> map = list2.get(i);
			Map<String, Object> _map = new HashMap<String, Object>();
			for (int j = 0; j < result.size(); j++) {
				System.out.println(5);
				if(map.get("userid").equals(result.get(j).get("userid"))){
					flig = true;
					_map = result.get(j);
					_map.put("del", map.get("del"));
					break;
				}
			}
			
			if(!flig){
				System.out.println(6);
				_map.put("userid", map.get("userid"));
				_map.put("username", map.get("user_name"));
				_map.put("sm", "0");
				_map.put("fsm", "0");
				_map.put("del", map.get("del"));
				result.add(_map);
			}
		}
		System.out.println("drawm是"+draw);
		if(draw !=null && draw !=""){
			dataTableView.setDraw(Integer.parseInt(draw)); //系统获取什么就返回什么
		}
		dataTableView.setRecordsFiltered(list4.size()); //记录过滤后的条数
		dataTableView.setRecordsTotal(list3.size());      //记录没有过滤的条数
		dataTableView.setData(result);           //获得的数据
		return dataTableView;
}
	

}
