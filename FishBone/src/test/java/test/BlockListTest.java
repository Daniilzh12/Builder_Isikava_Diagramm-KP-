package test;

import com.daniilzyravlev.fishbone.BlockList;
import com.daniilzyravlev.fishbone.blocks.Block;
import com.daniilzyravlev.fishbone.blocks.BlockType;
import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class BlockListTest {

    BlockList blockList;
    @BeforeEach
    void setUp()
    {
        JFXPanel fxPanel = new JFXPanel();
        blockList = new BlockList(() -> {});
    }
    @Test
    void getBlocks() {

        blockList.setBlock(BlockType.BONE,new Point2D(2,4),"fdf");
        blockList.setBlock(BlockType.BONE,new Point2D(3,4),"dsf");
        Assert.assertEquals(2,blockList.getBlocks().size());
    }

    @Test
    void setBlock() {
        BlockList blockList = new BlockList(() -> {});
        Block block = blockList.setBlock(BlockType.BONE,new Point2D(2,4),"fdf");
        Assert.assertEquals(block,blockList.getBlocks().get(0));
    }
}