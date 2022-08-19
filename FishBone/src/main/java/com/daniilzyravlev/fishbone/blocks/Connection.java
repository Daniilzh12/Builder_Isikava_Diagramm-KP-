package com.daniilzyravlev.fishbone.blocks;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.io.Serializable;

/**
 * Class for connection of blocks
 */
public class Connection extends Group implements Serializable {
    transient private final Line line;
    public Block block;
    private static final double arrowLength = 20;
    private static final double arrowWidth = 15;

    /**
     * @param line mainline
     * @param arrow1 part of arrow
     * @param arrow2 part of arrow
     * @param block for connecting
     */
    public Connection(Line line, Line arrow1, Line arrow2,Block block) {
        super(line, arrow1, arrow2);
        this.block=block;
        this.line = line;
        int lineWidth =2;
        if(block.type == BlockType.STANDARD) lineWidth = 7;
        this.line.setStrokeWidth(lineWidth);
        arrow1.setStrokeWidth(lineWidth);
        arrow2.setStrokeWidth(lineWidth);

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double hypo = Math.hypot(sx - ex, sy - ey);
                double factor = arrowLength / hypo;
                double factorO = arrowWidth / hypo;

                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    /**
     * @return StartX
     */
    public final double getStartX() {
        return line.getStartX();
    }

    /**
     * @return startXProperty
     */
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    /**
     * @return StartY
     */
    public final double getStartY() {
        return line.getStartY();
    }

    /**
     * @return startYProperty
     */
    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    /**
     * @return EndX
     */
    public final double getEndX() {
        return line.getEndX();
    }

    /**
     * @return endXProperty
     */
    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    /**
     * @return EndY
     */
    public final double getEndY() {
        return line.getEndY();
    }

    /**
     * @return endYProperty
     */
    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

    /**
     * @param block for connecting
     * @return MinPoint
     */
    public Point2D getMinPoint(Block block)
    {
        double x,y,start,end,stX,endX;
        if(getStartY() < getEndY()) { start = getStartY(); end = getEndY(); stX = getStartX(); endX = getEndX();}
        else { start = getEndY(); end = getStartY(); stX = getEndX(); endX = getStartX();}
        if(block.pY + block.height - 6f >= start && block.pY + block.height - 6f <= end)
            y = block.pY + block.height - 6f;
        else if(block.pY + block.height - 6f < start) y = start;
        else y = end;
        x = stX + (endX-stX) * (y-start)/(end-start);
        return new Point2D(x,y);
    }
}
