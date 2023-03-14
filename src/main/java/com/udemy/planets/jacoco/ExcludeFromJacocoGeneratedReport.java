package com.udemy.planets.jacoco;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcludeFromJacocoGeneratedReport {
  // Dessa forma, o Jacoco vai desconsiderar todo método que
  // tiver a anotação ExcludeFromJacocoGeneratedReport
}
