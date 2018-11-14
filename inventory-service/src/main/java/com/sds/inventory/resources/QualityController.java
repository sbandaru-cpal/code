package com.sds.inventory.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.inventory.service.QualityService;

@RequestMapping(value = "/calculate", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class QualityController {
	
	@Autowired
	private QualityService qualityService;

	@RequestMapping(method = RequestMethod.POST, value="/days/{days}")
    public @ResponseBody Integer calculate(@RequestBody List<String> items, @PathVariable Integer days) {
		return qualityService.calculateItemsValue(items, days);
    }

}
