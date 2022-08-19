package com.daniilzyravlev.fishbone;

import com.daniilzyravlev.fishbone.blocks.Area;
import com.daniilzyravlev.fishbone.blocks.Block;
import com.daniilzyravlev.fishbone.blocks.BlockType;
import com.daniilzyravlev.fishbone.blocks.Problem;
import javafx.geometry.Point2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List of blocks
 */
public class BlockList implements Serializable {
     private final List<Block> blocks;
    transient DragListener onDrag;

    /**
     * @param onDrag listener
     */
    public BlockList(DragListener onDrag)
    {
        this.onDrag = onDrag;
        blocks = new ArrayList<>();
    }

    /**
     * @return list of blocks
     */
    public List<Block> getBlocks()
    {
        return blocks;
    }

    /**
     * @param type of block
     * @param point2D on pane
     * @param text for textField
     * @return block
     */
    public Block setBlock(BlockType type, Point2D point2D, String text) {
        Block block;
        switch (type) {
            case STANDARD -> block = new Area(type, point2D, text, onDrag);
            case PROBLEM -> block = new Problem(type,point2D,text,onDrag);
            default -> block = new Block(type, point2D, text, onDrag);
        }
        blocks.add(block);
        return block;
    }
}
