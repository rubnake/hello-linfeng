package pattern.behavior.Observer;

import java.util.Observable;

public class BlogUser extends Observable {
	
	public void publishBlog(String articleTitle,String articleContent) {
		Article art = new Article();
		art.setArticleTitle(articleTitle);
		art.setArticleContent(articleContent);
		System.out.println("����:���������£����±���:" + articleTitle + ",��������:" + articleContent);
		this.setChanged();
		this.notifyObservers(art);
	}
}
