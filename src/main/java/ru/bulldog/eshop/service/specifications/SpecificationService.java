package ru.bulldog.eshop.service.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SpecificationService<T> {

	public abstract Specification<T> buildSpecification(Map<String, String> paramsMap);

	protected class SpecificationBuilder {

		private final List<Specification<T>> specifications;

		public SpecificationBuilder() {
			this.specifications = new ArrayList<>();
		}

		public void addSpecification(Specification<T> specification) {
			specifications.add(specification);
		}

		public Specification<T> build() {
			AtomicReference<Specification<T>> specification = new AtomicReference<>(Specification.where(null));
			specifications.forEach(spec -> specification.set(specification.get().and(spec)));
			return specification.get();
		}
	}
}
