package com.daniilzyravlev.fishbone.blocks;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.Serializable;

/**
 * Main block on diagram
 */
public class Bone extends Group implements Serializable {
    transient public double width;
    transient public double height;
    transient protected Point2D point;
    transient TextField Header;
    public String headerText;
    transient public double pX;
    transient public double pY;
    transient public BlockType type;

    /**
     * @param type of block
     * @param point on pane
     * @param pane (parent)
     * @param text for header
     */
    public Bone(BlockType type, Point2D point, Pane pane,String text)
    {
        pX=point.getX();
        pY=point.getY();
        headerText = text;
        this.type=type;
        setTranslateX(point.getX());
        setTranslateY(point.getY());
        Header = new TextField(headerText);
        Header.setOnKeyTyped(e -> this.headerText=Header.getText());
        Header.setOnKeyReleased(e -> this.headerText=Header.getText());
        Header.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        Header.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Header.setAlignment(Pos.BASELINE_LEFT);
        getChildren().add(Header);
        this.point = point;
        pX = this.point.getX();
        pY = this.point.getY();
        this.width = pane.getLayoutBounds().getWidth() * 0.7;
        this.height = pane.getLayoutBounds().getHeight() * 0.05;

    }

    /**
     * method for updating text on textField
     */
    public void updateText()
    {
        Header.setText(headerText);
    }

    /**
     * Method for drawing on pane
     */
    public void draw()
    {
        getChildren().clear();
        Header.applyCss();
        Header.layout();
        Rectangle rect = new Rectangle(
                pX,
                pY,
                width ,
                height);
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(pX+width, pY-50,
                pX+width+50, pY+height/2,
                pX+width, pY+height+50);
        point = new Point2D(pX, pY);

        rect.setFill(Color.BLACK);
        arrow.setFill(Color.BLACK);
        getChildren().add(rect);
        getChildren().add(arrow);
        getChildren().add(Header);

        Header.setTranslateX(pX+width-30);
        Header.setTranslateY(pY+height+50);
    }

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
     * @return minY
     */
    public double minY() {
        return pY;
    }

    /**
     * @return minX
     */
    public double minX() {
        return pX;
    }

    /**
     * @return maxY
     */
    public double maxY() {
        return pY + getHeight();
    }

    /**
     * @return maxX
     */
    public double maxX() {
        return pX + getWidth();
    }

    /**
     * @param block for connecting
     * @return Point
     */
    public Point2D getPoint(Block block) {
        double x,y;
        if((block.pX+block.width/2)+15 >= minX() && block.pX+block.width/2 <= maxX())
            x = (block.pX+block.width/2)+15;
        else if(block.pX+block.width/2 > maxX()) x = maxX();
        else x = minX();
        if(block.pY>maxY()) y = maxY();
        else y = minY();
        return new Point2D(x,y);
    }
}
