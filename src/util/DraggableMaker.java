package util;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view_client.ChessViewController;


public class DraggableMaker {
    private double startX=0.0,startY=0.0;
    private boolean inRange(double l, double m, double r){
        return l<=m && m<=r;
    }
    public void makeDraggable(Node node, Pane picesPane, ChessViewController controller){
        startX = node.getLayoutX();
        startY = node.getLayoutY();



        node.setOnMouseDragged(evt->{
            node.toFront();
            if(startX==0.0){
                startX= evt.getSceneX();
                startY= evt.getSceneY();
            }
            node.setLayoutX( evt.getSceneX()-20);
            node.setLayoutY( evt.getSceneY()-20);
        });

        node.setOnMouseReleased(evt->{




            double x = node.getLayoutX()+25;
            double y = node.getLayoutY()+25;
            if(!inRange(150,x,550) || !inRange(100,y,500)){
//            account for the node being outside the grid
                node.setLayoutX(startX);
                node.setLayoutY(startY);

            }else{

                int snapX =((int) Math.floor((x-150)/50))*50+155;
                int snapY =((int) Math.floor((y-100)/50))*50+105;

                for( Node p : picesPane.getChildren()){

                    if( p.getLayoutX()==snapX && p.getLayoutY()==snapY && p!=node) {

                        if( ((ImageView)node).getImage().getUrl().contains("white")== ((ImageView)p).getImage().getUrl().contains("white")){
//                        you are tying to move the piece to the filed with YOUR another piece so its not allowed
                            node.setLayoutX(startX);
                            node.setLayoutY(startY);
                            return;
                        }

                        picesPane.getChildren().remove(p);
                        break;
                    }
                }

                node.setLayoutX(snapX);
                node.setLayoutY(snapY);
                startX = snapX;
                startY = snapY;
                controller.sendNotation(FENParser.calculateFieldName(snapX,snapY));

            }


        });

    }
}
