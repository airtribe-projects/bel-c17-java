package org.airtribe.AsynApiApplicationBELC17.dto;

import java.util.List;


public class Dimension {
  private List<DimensionUnit> products;

  public Dimension(List<DimensionUnit> products) {
    this.products = products;
  }

  public List<DimensionUnit> getProducts() {
    return products;
  }

  public void setProducts(List<DimensionUnit> products) {
    this.products = products;
  }
}
