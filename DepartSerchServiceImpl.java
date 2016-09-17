package sys.base.spring.service.alarminfo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sys.base.spring.domain.alarminfo.AreaEntity;
import sys.base.spring.domain.alarminfo.DepartEntity;
import sys.base.spring.domain.alarminfo.UnitEntity;
import sys.base.spring.domain.alarminfo.UnittypeEntity;
import sys.base.spring.repository.alarminfo.AreaRepository;
import sys.base.spring.repository.alarminfo.DepartRepository;
import sys.base.spring.repository.alarminfo.UnitRepository;
import sys.base.spring.repository.alarminfo.UnittypeReposity;


@Service
public class DepartSerchServiceImpl implements DepartSerchService {
	
	 private static final Logger logger = LoggerFactory.getLogger(DepartSerchServiceImpl.class);

	@Autowired
    private AreaRepository areaRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private DepartRepository departRepository;
	@Autowired
	private UnittypeReposity unittypeReposity;
	
	@Override
	public List<AreaEntity> findAllArea() {
		List<AreaEntity> areaList = areaRepository.findAll();
		logger.info("Getting all areas");
		return areaList;
	}

	@Override
	public List<UnitEntity> findAllUnit(String area_guid) {
		List<UnitEntity> unitlist = unitRepository.findAllUnit(area_guid);
		return unitlist;
	}

	@Override
	public List<DepartEntity> findAllDepart(String unit_guid) {
		List<DepartEntity> departlist = departRepository.findAllDepart(unit_guid); 
		return departlist;
	}

	@Override
	public List<UnitEntity> findAllUnitByUt(String unitttype_guid) {
		List<UnitEntity> unitlist = unitRepository.findAllUnitByUt(unitttype_guid);
		return unitlist;
	}

	@Override
	public List<UnittypeEntity> findAllUnittype() {
		List<UnittypeEntity> unittypeList = unittypeReposity.findAll();
		return unittypeList;
	}

}
