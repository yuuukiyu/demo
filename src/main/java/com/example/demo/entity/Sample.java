package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sample {
  @Id @GeneratedValue
  private Long id;

  private String name;
}
