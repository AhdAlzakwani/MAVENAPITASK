package mavenApiEvaluation;

public class docs {
	private String document_type;
	private String section_name;
	byline byline;
	private String type_of_material;

	public String getType_of_material() {
		return type_of_material;
	}

	public void setType_of_material(String type_of_material) {
		this.type_of_material = type_of_material;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}

	public byline getByline() {
		return byline;
	}

	public void setByline(byline byline) {
		this.byline = byline;
	}
}
