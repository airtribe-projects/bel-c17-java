package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@Component
@Qualifier("dieselEngine")
public class DieselEngine extends Engine {
  public DieselEngine(@Value("${dieselEngine.engineType}")String engineType,
      @Value("${dieselEngine.enginePower}")String enginePower) {
    super(engineType, enginePower);
  }
}
