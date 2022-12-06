package tim24.projekat.uberapp.DTO;

import java.util.ArrayList;
import java.util.List;

public class DtoList<T> {

	int totalCount;
	List results;
	
	public DtoList(int totalCount, List<T> results) {
		super();
		this.totalCount = totalCount;
		this.results = results;
	}

	public DtoList() {
		super();
		this.totalCount = 0;
		this.results = new ArrayList<T>();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
	public void add(T element) {
		results.add(element);
	}
}
