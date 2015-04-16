package com.gc22;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Garry Craig on 06/04/2015.
 */
public class Example {
    private RedBlackTree<String, String> exampleTree = null;

    public Example() {
        createTree();
        createWindow();
    }

    private void createTree() {
        exampleTree = new RedBlackTree<String, String>();

        addNode(new RedBlackTreeNode<String, String>("A", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("B", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("C", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("D", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("E", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("F", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("G", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
        addNode(new RedBlackTreeNode<String, String>("H", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//        addNode(new RedBlackTreeNode<String, String>("Z", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//        addNode(new RedBlackTreeNode<String, String>("J", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//        addNode(new RedBlackTreeNode<String, String>("X", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//
//        addNode(new RedBlackTreeNode<String, String>("1", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//
//        addNode(new RedBlackTreeNode<String, String>("2", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//        addNode(new RedBlackTreeNode<String, String>("3", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
//        addNode(new RedBlackTreeNode<String, String>("4", "Hello", RedBlackTreeNode.Color.Black), exampleTree);
    }

    public static void addNode(RedBlackTreeNode<String, String> node, RedBlackTree<String, String> tree){
        tree.addNode(node);
        printNodes(tree);
    }

    public static void printNodes(RedBlackTree<String, String> tree){
        for(RedBlackTreeNode<String, String> node : tree) {
            System.out.println("   Node " + node + " Left " + node.getLeft() + " Right " + node.getRight());
        }
    }

    public static void main(String[] args) {
        Example example = new Example();
        //example.createWindow();
    }

    public interface ResultWindow{
        public void write(String s);
    }

    public ResultWindow getWindow(){
        ResultWindow x = new ResultWindow(){
            @Override
            public void write(String s) {

            }
        };
        return x;
    }

    public void createWindow() {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        final Canvas canvas = new Canvas(shell,SWT.None);
        canvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle clientArea = canvas.getClientArea();
                GC gc = e.gc;
                gc.setInterpolation(SWT.ON);
                gc.setAntialias(SWT.ON);
                gc.setAdvanced(true);
                paintNodes(gc, clientArea);
            }
        });
        //Combo combo = new Combo(shell, SWT.None);
//        Composite composite = new Composite(shell, SWT.None);
//        composite.setLayout(new RowLayout());
//        final Button button = new Button(composite, SWT.PUSH);
//        button.setText("Add Node");
//        button.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent selectionEvent) {
//                System.out.println(button.getText());
//            }
//        });
        //StyledText text = new StyledText(shell, SWT.None);
        shell.open();
        while(!shell.isDisposed()){
            if(display.readAndDispatch()){
                display.sleep();
            }
        }
    }

    enum Direction{
        Left,
        Right,
        Center,
    }

    private void paintNodes(GC gc, Rectangle clientArea) {
        int width = 50;
        int height = 50;

        class GraphicalNode {
            Direction direction;
            Rectangle rectangle;
            RedBlackTreeNode<String, ?> node;
            int amount;

            public GraphicalNode(RedBlackTreeNode<String, ?> node, Rectangle rectangle, Direction direction, int amount){
                this.node = node;
                this.rectangle = rectangle;
                this.direction = direction;
                this.amount = amount;
            }
        }
        Queue<GraphicalNode> nodesToProgress = new LinkedList<GraphicalNode>();
        nodesToProgress.add(new GraphicalNode(this.exampleTree.getRoot(), new Rectangle(clientArea.width/2 - width /2, 0, width, height), Direction.Center, width));

        while(!nodesToProgress.isEmpty()) {
            GraphicalNode graphicalNode = nodesToProgress.remove();
            RedBlackTreeNode<String, ?> node = graphicalNode.node;
            Rectangle rectangleCurrent = graphicalNode.rectangle;

            drawNode(gc, node, rectangleCurrent);
            //wait(500);

            RedBlackTreeNode<String, ?> left = node.getLeft();
            if (left != null) {
                int amount = width;
                switch (graphicalNode.direction){
                    case Left:
                        amount = graphicalNode.amount;
                        break;
                    case Center:
                        amount = width * 2;
                        break;
                }
                Point from = new Point(rectangleCurrent.x, rectangleCurrent.y + rectangleCurrent.height / 3);
                Point to = new Point(from.x - amount - 15, from.y + height + 15);
                nodesToProgress.add(new GraphicalNode(left, new Rectangle(to.x - width + 15, to.y - 15, width, height), Direction.Left, amount));
                drawConnection(gc, from, to);
                //wait(1000);
            }
            RedBlackTreeNode<String, ?> right = node.getRight();
            if (right != null) {
                int amount = width;
                switch (graphicalNode.direction){
                    case Right:
                        amount = graphicalNode.amount + 10;
                        break;
                    case Center:
                        amount = width * 2;
                        break;
                }
                Point from = new Point(rectangleCurrent.x + rectangleCurrent.width, rectangleCurrent.y + rectangleCurrent.height / 3);
                Point to = new Point(from.x + amount + 15, from.y + rectangleCurrent.height);
                nodesToProgress.add(new GraphicalNode(right, new Rectangle(to.x - 15, to.y, width, height), Direction.Right, amount));
                drawConnection(gc, from, to);
                //wait(1000);
            }
        }
    }

    private void wait(int num){
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawConnection(GC gc, Point from, Point to){
        gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        gc.drawLine(from.x, from.y, to.x, to.y);
    }

    private void drawNode(GC gc, RedBlackTreeNode<String,?> node, Rectangle rectangle){
        if(node.getColor() == RedBlackTreeNode.Color.Black) {
            gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        }else{
            gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
        }
        gc.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        String text = node.getKey();
        Point extent = gc.stringExtent(text);
        gc.drawText(text, rectangle.x + rectangle.width/2 - extent.x/2, rectangle.y + rectangle.height/2 - extent.y/2, true);
    }
}
