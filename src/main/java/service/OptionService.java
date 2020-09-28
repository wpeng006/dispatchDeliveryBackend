package service;

import java.util.Arrays;
import java.util.List;

import External.GoogleMapAPIClient;
import model.*;
import dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
	private final String DEFAULT_STATION = "San Francisco";
	private final String DEFAULT_DRONE = "DRONE";
	private final String DEFAULT_ROBOT = "ROBOT";
	private final GoogleMapAPIClient googleMapAPIClient = new GoogleMapAPIClient();

	@Autowired
	private final CarrierDao carrierDao;

	@Autowired
	private final OptionDao optionDao;


	private Option getDroneOption(String from, String to) {
		//create route

		//calculate time
		Route route = googleMapAPIClient.getDroneRoute(from, to, DEFAULT_STATION);
		if (route == null) {
			return null;
		}
		//add into heap


		return getOption(route, DEFAULT_DRONE);
	}

	private Option getRobotOption(String from, String to) {

		Route route = googleMapAPIClient.getRobotRoute(from, to, DEFAULT_STATION);
		if (route == null) {
			return null;
		}
		return getOption(route, DEFAULT_ROBOT);
	}

	private Option getOption(Route route, String carrierType) {
		Integer carrierID = carrierDao.getAvailableCarrierId(carrierType);
		if (carrierID == null) {
			return null;
		}

		carrierDao.setStatus(carrierID, "Busy");
		//add into heap
		Option option = Option.builder()
				.carrierType(carrierType)
				.carrierId(carrierID)
				.startAddress(route.getStartAddress())
				.endAddress(route.getEndAddress())
				.fee(route.getFee())
				.route(route)
				.build();
		return option;
	}

	public Option[] getOptions(String from, String to) {
		Option[] options = new Option[2];
		options[0] = getDroneOption(from, to);
		options[1] = getRobotOption(from, to);
		return options;
	}

	//add selected option
	public void addSelectedOption(int optionId){
		Option option = optionDao.getOptionById(optionId);
		carrierDao.setStatus(option.getCarrierId(), "Busy");
		//add into heap
	}

	public Option getOptionById(int id) {
		Option option = optionDao.getOptionById(id);
		return option;
	}

}
