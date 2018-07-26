package com.nicolasgarnier.particles.view;

public class Box {
  
  public double originX;
  public double originY;
  public double width;
  public double height;
  
  public Box(final double originX, final double originY, final double width, final double height) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
  }
  
  public boolean contains(final int x, final int y) {
    if (x >= originX && x <= originX + width && y >= originY && y <= originY + height) return true;
    else return false;
  }
  
}
