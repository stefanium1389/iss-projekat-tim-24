package tim24.projekat.uberapp.DTO;

import java.util.List;

/*{
  type: ChartType;
  data: Array<any>;
  labels: Array<any>;
  options: any;
  total: string;
  average: string;
}*/
public class StatisticsResponseDTO {

	private String type;
	private List<Integer> data;
	private List<String> labels;
	private String total;
	private String average;
	
	
	public StatisticsResponseDTO(String type, List<Integer> data, List<String> labels, String total, String average) {
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
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	
	
	
}
