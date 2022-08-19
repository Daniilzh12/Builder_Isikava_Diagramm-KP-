package com.daniilzyravlev.fishbone;

import com.daniilzyravlev.fishbone.blocks.Block;
import com.daniilzyravlev.fishbone.blocks.BlockType;
import com.daniilzyravlev.fishbone.blocks.Bone;
import com.daniilzyravlev.fishbone.blocks.Connection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller
 */
public class HelloController implements Initializable {
    public Button blockText;
    private final FileChooser explorer = new FileChooser();
    public ToggleButton cleaner;
    public AnchorPane workSpace;
    public ScrollPane root;
    Connections connections;
    Bone bone;
    Connections miniConnections;
    BlockList blockList;

    /**
     * @param url for resources
     * @param resourceBundle of resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockList = new BlockList(this::drawConnections);
        connections = new Connections();
        miniConnections = new Connections();
        bone = new Bone(BlockType.BONE,new Point2D(root.getWidth()*0.1,
                root.getHeight()*0.4 ),workSpace,"");
        workSpace.getChildren().add(bone);
        bone.draw();
        root.layoutBoundsProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                bone.pX = ((Bounds)t1).getWidth()*0.1;
                bone.pY = ((Bounds)t1).getHeight()*0.4;
                bone.height = ((Bounds)t1).getHeight()*0.05;
                bone.width = ((Bounds)t1).getWidth()*0.7;
                bone.draw();
                drawConnections();
            }
        });
    }

    /**
     * Click on AreaBlockButton
     */
    public void BlockClick()
    {
        Block block = blockList.setBlock(BlockType.STANDARD,new Point2D(30,30),"");
        drawShape(block);
        drawConnections();
    }

    /**
     * Click on ProblemBlockButton
     */
    public void ProblemClick()
    {
        Block block = blockList.setBlock(BlockType.PROBLEM,new Point2D(30,30),"Причина");
        drawShape(block);
    }

    /**
     * @param shape for drawing
     */
    private void drawShape(Block shape) {
        workSpace.getChildren().add(shape);
        shape.setOnMouseClicked(mouseEvent -> {
            if(cleaner.isSelected()) {
                workSpace.getChildren().remove(shape);
                blockList.getBlocks().remove(shape);
                drawConnections();
            }
        });
        shape.draw();
    }

    /**
     * Method for draw list of connections
     */
    private void drawConnections() {
        workSpace.autosize();

        for (Connection connecting : connections.getConnections()) {
            workSpace.getChildren().remove(connecting);
        }
        connections.getConnections().clear();

        for (Block block : blockList.getBlocks()) {
            if(block.type == BlockType.STANDARD)
                drawConnection(block);
        }
        drawMiniConnections();
    }

    /**
     * Method for draw list of miniConnections
     */
    private void drawMiniConnections() {
        workSpace.autosize();

        for (Connection connecting : miniConnections.getConnections()) {
            workSpace.getChildren().remove(connecting);
        }
        miniConnections.getConnections().clear();

        for (Block block : blockList.getBlocks()) {
            if(block.type == BlockType.PROBLEM)
              drawMiniConnection(block);
        }
    }

    /**
     * @param block created a connection
     */
    private void drawMiniConnection(Block block) {
        var points = getMiniArrowPoints(block);
        Line line = new Line(points.item1.getX(), points.item1.getY(), points.item2.getX(), points.item2.getY());
        Line arrow1 = new Line(0, 0, 2, 2);
        Line arrow2 = new Line(0, 0, 2, 2);

        Connection connection = new Connection(line, arrow1, arrow2,block);
        workSpace.getChildren().add(connection);
        miniConnections.getConnections().add(connection);
        connection.toBack();
    }

    /**
     * @param block created a connection
     * @return line
     */
    private PointLiner<Point2D, Point2D> getMiniArrowPoints(Block block) {
        var blockPoints = block.getArrayOfMinMaxPoints();

        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        double lowestDistance = Double.POSITIVE_INFINITY;

        for(Connection con : connections.getConnections())
            for (Point2D fromPoint : blockPoints) {
                var newDistance = fromPoint.distance(con.getMinPoint(block));
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = con.getMinPoint(block);
                    lowestDistance = newDistance;
                }
            }

        return new PointLiner<>(pointFromFinal, pointToFinal);
    }

    /**
     * @param block created a connection
     */
    private void drawConnection(Block block) {
        var points = getArrowPoints(block);
        Line line = new Line(points.item1.getX(), points.item1.getY(), points.item2.getX(), points.item2.getY());
        Line arrow1 = new Line(0, 0, 2, 2);
        Line arrow2 = new Line(0, 0, 2, 2);

        Connection connection = new Connection(line, arrow1, arrow2,block);
        workSpace.getChildren().add(connection);
        connections.getConnections().add(connection);
        connection.toBack();
    }

    /**
     * @param block created a connection
     * @return line
     */
    private PointLiner<Point2D, Point2D> getArrowPoints(Block block) {
        var blockPoints = block.getArrayOfMinMaxPoints();

        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        double lowestDistance = Double.POSITIVE_INFINITY;

        for (Point2D fromPoint : blockPoints) {
                var newDistance = fromPoint.distance(bone.getPoint(block));
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = bone.getPoint(block);
                    lowestDistance = newDistance;
                }
            }

        return new PointLiner<>(pointFromFinal, pointToFinal);
    }

    /**
     * Information
     */
    public void About()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, """
                Левая панель: элементы диаграммы (нажать для размещения, перетаскивать для перемещения)
                Ластик - выбрать, затем нажать на элемент для удаления;
                Верхняя панель: Файл - (Экспорт, импорт, сохранение в png);
                Изменить - Очистка рабочего пространства\s""");
        alert.setTitle("Справка");
        alert.setHeaderText("Краткое руководство");
        alert.show();
    }

    /**
     * Save a png file
     */
    public void saveImage()
    {
        explorer.getExtensionFilters().clear();
        explorer.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        File file = explorer.showSaveDialog(null);
        if(file != null){
            try {
                WritableImage snapShot = root.snapshot(new SnapshotParameters(), null);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapShot, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }

    /**
     * export diagram in .bn format
     */
    public void exportDiagram()
    {
        explorer.setTitle("Выберите папку для сохранения...");
        explorer.setInitialFileName("Diagram");
        explorer.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл диаграммы", "*.bn"));
        File file = explorer.showSaveDialog(root.getScene().getWindow());
        if (file != null)
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream outStream = new ObjectOutputStream(fos);
                outStream.writeObject(blockList);
                outStream.writeObject(bone.headerText);
                outStream.flush();
                outStream.close();
                System.out.println("Сохранено!");
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
    }

    /**
     * import diagram from .bn format
     * @throws IOException for missing file
     * @throws ClassNotFoundException for broken class
     */
    public void importDiagram() throws IOException, ClassNotFoundException {
        explorer.setTitle("Выбрать файл проекта");
        explorer.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл диаграммы", "*.bn"));
        File file = explorer.showOpenDialog(root.getScene().getWindow());
        if (file!=null) {
            clearArea();
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            BlockList tempList = (BlockList) inputStream.readObject();
            bone.headerText = (String) inputStream.readObject();
            bone.updateText();
            for (Block block:tempList.getBlocks())
                drawShape(blockList.setBlock(block.type,new Point2D(block.pX,block.pY),block.text));
        }
        drawConnections();
    }

    /**
     * Delete objects on diagram
     */
    public void clearArea()
    {
        for (Block block: blockList.getBlocks())
            workSpace.getChildren().remove(block);
        blockList.getBlocks().clear();
        drawConnections();
        drawMiniConnections();
        bone.headerText = "";
        bone.updateText();
    }
}