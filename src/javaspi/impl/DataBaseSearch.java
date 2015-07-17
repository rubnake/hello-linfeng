package javaspi.impl;

import java.util.List;

import javaspi.interfaces.Search;

public class DataBaseSearch implements Search {

	public DataBaseSearch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> search(String keyword) {

		System.out.println("now use db search. keyword:" + keyword);
		return null;
	}

}
