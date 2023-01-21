package tim24.projekat.uberapp.DTO;

import java.util.List;

public class StatisticsResponseDTO {

	private String type;
	private List<Double> data;
	private List<String> labels;
	private Double total;
	private Double average;
	
	
	public StatisticsResponseDTO(String type, List<Double> data, List<String> labels, Double total, Double average) {
		super();
		this.type = type;
		this.data = data;
		this.labels = labels;
		this.total = total;
		this.average = average;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	
	
	
}
