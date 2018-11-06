/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.TextComponent;

/**
 *
 * @author savantis
 */
class MyTextListener implements TextListener {
  String preface;

  public MyTextListener(String source) {
    preface = source + " text value changed.\n"
        + "   First 10 characters: \"";
  }
    @Override
  public void textValueChanged(TextEvent e) {
    TextComponent tc = (TextComponent) e.getSource();
    System.out.println("Typed value in TextComponent " + tc.getText());
  }

  
}