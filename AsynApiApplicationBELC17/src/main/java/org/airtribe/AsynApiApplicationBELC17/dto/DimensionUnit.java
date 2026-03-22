package org.airtribe.AsynApiApplicationBELC17.dto;

public class DimensionUnit {
  private String id;
  private String description;
  private String title;
  private String category;

  public DimensionUnit(String id, String description, String title, String category) {
    this.id = id;
    this.description = description;
    this.title = title;
    this.category = category;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
