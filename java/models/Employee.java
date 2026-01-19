package models;

import java.math.BigDecimal;
import java.util.List;

public record Employee(String name, int id, List<String> skills, Double salary) {

}
