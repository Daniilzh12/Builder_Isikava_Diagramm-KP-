package com.daniilzyravlev.fishbone.blocks;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class ConnectionTest {

    @Test
    void getMinPoint() {
        JFXPanel fxPanel = new JFXPanel();
        Line line = new Line(2,5,76,95);
        Connection connection = new Connection(line,new Line(),new Line(),new Block(BlockType.STANDARD,new Point2D(2,2),"",null));
        Point2D point2D = connection.getMinPoint(new Block(BlockType.BONE,new Point2D(100,232),"",null));
        Assert.assertEquals(new Point2D(76,95),point2D);
    }
}