package ru.bulldog.eshop.statistics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Service
public class StatisticsService {

	private Map<Class<?>, Long> timeMap;

	@PostConstruct
	private void postInit() {
		this.timeMap = new HashMap<>();
	}

	private void addTime(Class<?> clazz, long time) {
		long timeTotal = timeMap.getOrDefault(clazz, 0L) + time;
		timeMap.put(clazz, timeTotal);
	}

	@Around("execution(public * ru.bulldog.eshop.service.*.*(..))")
	public Object serviceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object out = joinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		Class<?> source = joinPoint.getSignature().getDeclaringType();
		addTime(source, duration);
		return out;
	}

	public Map<String, Long> getTimeStatistics() {
		Map<String, Long> statisticsMap = new HashMap<>();
		timeMap.forEach((clazz, time) -> statisticsMap.put(clazz.getName(), time));
		return statisticsMap;
	}
}
