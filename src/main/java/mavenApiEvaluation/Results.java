package mavenApiEvaluation;

public class Results {
	
	private String section;
	private String title;
	private String published_date;
	private String item_type;
	private String book_author;

	
	public String getBook_author() {
		return book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}
	public String getPublished_date() {
		return published_date;
	}
	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}
	public String getSections() {
		return section;
	}
	public void setSections(String sections) {
		this.section = sections;
	}
	public String getBook_title() {
		return title;
	}
	public void setBook_title(String book_title) {
		this.title = book_title;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

}
