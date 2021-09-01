package ru.bulldog.eshop.service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SpecificationService<T> {

	public abstract Specification<T> buildSpecification(Map<String, String> paramsMap);

	protected class SpecificationBuilder {

		private final MultiValueMap<SpecRule, Specification<T>> specificationMap;

		public SpecificationBuilder() {
			this.specificationMap = new LinkedMultiValueMap<>();
		}

		public void addSpecification(SpecRule rule, Specification<T> specification) {
			List<Specification<T>> specifications = specificationMap.getOrDefault(rule, new ArrayList<>());
			specifications.add(specification);
			specificationMap.put(rule, specifications);
		}

		public Specification<T> build() {
			Specification<T> specification = Specification.where(null);
			for (Map.Entry<SpecRule, List<Specification<T>>> entry : specificationMap.entrySet()) {
				SpecRule rule = entry.getKey();
				List<Specification<T>> specifications = entry.getValue();
				for (Specification<T> spec : specifications) {
					switch (rule) {
						case AND:
							specification = specification.and(spec);
						case OR:
							specification = specification.or(spec);
					}
				}
			}
			return specification;
		}
	}

	public enum SpecRule {
		AND,
		OR
	}
}