package com.nicolasgarnier.particles;

import java.io.Serializable;

import com.nicolasgarnier.particles.model.JaipurModel;

public class JaipurClientServerMessage implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public final JaipurModel model;
  public final String logMsg;
  
  public JaipurClientServerMessage(final JaipurModel model, final String logMsg) {
    this.model = model;
    this.logMsg = logMsg;
  }

}
