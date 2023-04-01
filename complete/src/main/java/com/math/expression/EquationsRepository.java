package main.java.com.math.expression;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquationsRepository extends CrudRepository<Equations, Integer> {
    List<Equations> findByRoot(String root);
}
