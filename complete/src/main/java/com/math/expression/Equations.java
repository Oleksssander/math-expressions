package main.java.com.math.expression;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Equations {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String expression;

	private String root;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	public String toString() {
		return "id: " + getId() + ", expression: " + getExpression();
	}
}
