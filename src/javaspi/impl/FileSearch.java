package javaspi.impl;

import java.util.List;

import javaspi.interfaces.Search;

public class FileSearch implements Search {

	public FileSearch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> search(String keyword) {

		System.out.println("now use file search. keyword:" + keyword);
		return null;
	}

}
