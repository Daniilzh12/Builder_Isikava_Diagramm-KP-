package com.daniilzyravlev.fishbone.blocks;

import com.daniilzyravlev.fishbone.DragListener;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Parent Block class
 */
public class Block extends Group implements Serializable {
    /**
     * list of listeners
     */
    transient public ArrayList<DragListener> listeners = new ArrayList<>();
    /**
     * field with text
     */
    transient protected TextField blockText;
    /**
     * text in textField
     */
    public String text;
    /**
     * width of block
     */
    protected double width;
    /**
     * height of block
     */
    protected double height;
    /**
     * point on pane
     */
    transient protected Point2D point;
    /**
     * position on X
     */
    public double pX;
    /**
     * position on Y
     */
    public double pY;
    /**
     * type of block
     */
    public BlockType type;
    /**
     * anchor of mouse on X
     */
    private double mouseAnchorX;
    /**
     * anchor of mouse on Y
     */
    private double mouseAnchorY;

    /**
     * @param type of block
     * @param point on pane
     * @param text on textField
     * @param onDrag listener
     */
    public Block(BlockType type, Point2D point,String text, DragListener onDrag)
    {
        this.text=text;
        pX=point.getX();
        pY=point.getY();
        this.type=type;
        setTranslateX(point.getX());
        setTranslateY(point.getY());

        blockText = new TextField(text);
        blockText.setOnKeyTyped(e -> this.text=blockText.getText());
        blockText.setOnKeyReleased(e -> this.text=blockText.getText());
        if(this.type == BlockType.PROBLEM)
            blockText.setStyle("-fx-focus-color: -fx-control-inner-background; -fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        else blockText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        blockText.setFont(Font.font("Verdana", 9));
        blockText.setAlignment(Pos.BASELINE_CENTER);

        this.point = point;
        pX = this.point.getX();
        pY = this.point.getY();
        this.width = computePrefWidth(-1);
        this.height = computePrefHeight(-1);


        getChildren().add(blockText);

        setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });
        listeners.add(onDrag);

        setOnMouseDragged(mouseEvent -> {
            setTranslateX(Math.max(getTranslateX() + mouseEvent.getX() - mouseAnchorX, 0));
            setTranslateY(Math.max(getTranslateY() + mouseEvent.getY() - mouseAnchorY, 0));

            this.point = new Point2D(getTranslateX(), getTranslateY());
            pX=this.point.getX();
            pY=this.point.getY();
            for (DragListener listener : listeners) {
                listener.onDrag();
            }

        });
    }

    /**
     * Method for drawing on pane
     */
    public void draw(){}

    /**
     * @return Height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return Width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return Position
     */
    public Point2D getPosition() {
        return point;
    }

    /**
     * @return minY
     */
    public double minY() {
        return getPosition().getY();
    }

    /**
     * @return maxY
     */
    public double maxY() {
        return getPosition().getY() + getHeight();
    }

    /**
     * @return maxX
     */
    public double maxX() {
        return getPosition().getX() + getWidth();
    }

    /**
     * @return Array Of Min and Max Points
     */
    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        if(type != BlockType.PROBLEM)
            fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, maxY()-6f));
        return fromPoints;
    }
}
