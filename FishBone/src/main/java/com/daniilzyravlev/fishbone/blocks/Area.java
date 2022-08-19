package com.daniilzyravlev.fishbone.blocks;

import com.daniilzyravlev.fishbone.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class for blockType(Area)
 */
public class Area extends Block{
    /**
     * @param type of block
     * @param point on pane
     * @param text in textBlock
     * @param onDrag listener
     */
    public Area(BlockType type, Point2D point, String text, DragListener onDrag) {
        super(type, point, text, onDrag);
    }

    /**
     * Method for drawing on pane
     */
    public void draw()
    {
        float offset = 10f;
        float lineWidth = 2f;
        blockText.applyCss();
        blockText.layout();

        getChildren().clear();

        var textWidth = blockText.prefWidth(-1);
        var textHeight = blockText.prefHeight(-1);

        Rectangle rect = new Rectangle(
                0,
                0,
                textWidth + offset,
                textHeight + offset);

        Rectangle clip = new Rectangle(
                lineWidth,
                lineWidth,
                textWidth + offset - lineWidth * 2,
                textHeight + offset - lineWidth * 2);


        point = new Point2D(getTranslateX(), getTranslateY());
        width = rect.prefWidth(-1);
        height = rect.prefHeight(-1);

        rect.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        getChildren().add(rect);
        getChildren().add(clip);
        getChildren().add(blockText);

        blockText.setTranslateX(offset * 0.5f);
        blockText.setTranslateY(offset * 0.5f);

    }
}
