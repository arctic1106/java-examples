package models;

public abstract sealed class Shape permits Square, Rectangle, Triangle, Circle {
}