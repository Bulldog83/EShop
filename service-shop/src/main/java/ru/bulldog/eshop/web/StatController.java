package ru.bulldog.eshop.web;

import ru.bulldog.eshop.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatController {

	private final StatisticsService statisticsService;

	@Autowired
	public StatController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping
	public Map<String, Long> getTimeStatistics() {
		return statisticsService.getTimeStatistics();
	}
}
