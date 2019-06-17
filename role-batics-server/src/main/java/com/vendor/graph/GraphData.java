package com.vendor.graph;

import java.util.*;

public class GraphData {


    private Map<Integer,GraphDot>  graphDotMaps= new HashMap<>();
    private List<GraphEdge>  edgeLists = new ArrayList<>();
    private Integer dotCount  =0;
    private Integer edgetCount= 0;
    private List<GraphDot> graphDotList = new ArrayList<>();
    private List<GraphDot> unColorGraphDotList = new ArrayList<>();
    private Integer curColorNo = 0;

    public GraphDot addDot(int dotNo)
    {
        if(!graphDotMaps.containsKey(dotNo))
        {
            GraphDot dot = new GraphDot();
            dot.setDotNo(dotNo);
            dot.setColorNo(null);
            List<GraphDot>  graphDots = new ArrayList<>();
            dot.setNearDots(graphDots);
            graphDotMaps.put(dotNo,dot);
            return dot;
        }
        else
        {
            return graphDotMaps.get(dotNo);
        }
    }


    public void addInputEdge(InputEdge inputEdge)
    {
        GraphDot firstDot  = this.addDot(inputEdge.getFirstDotNo());
        GraphDot secondDot = this.addDot(inputEdge.getSecondDotNo());

        firstDot.getNearDots().add(secondDot);
        secondDot.getNearDots().add(firstDot);

        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setFirstDot(firstDot);
        graphEdge.setSecondDot(secondDot);

    }

    public void Init(int dotCount,int edgetCount,List<InputEdge> edgeLists)
    {
        this.dotCount = dotCount;
        this.edgetCount = edgetCount;
        for (InputEdge  inputEdge : edgeLists)
        {
            this.addInputEdge(inputEdge);
        }

        for (Map.Entry<Integer,GraphDot> entry : graphDotMaps.entrySet()) {
            graphDotList.add(entry.getValue()) ;
        }

        Collections.sort(graphDotList, new Comparator<GraphDot>() {
            @Override
            public int compare(GraphDot o1, GraphDot o2) {
                if(o1.getDotNo() > o2.getDotNo())
                {
                    return  1;
                }
                else if(o1.getDotNo() < o2.getDotNo())
                {
                    return  -1;
                }
                else
                {
                    return  0;
                }
            }
        });

        unColorGraphDotList.addAll(graphDotList);

    }

    protected boolean isNearColorDots(GraphDot graphDot,List<GraphDot> hasDrawColorDots )
    {
        for (GraphDot dot:hasDrawColorDots)
        {
            if(dot.isExistNear(graphDot.getDotNo()))
            {
                return true;
            }
        }
        return false;
    }

    protected boolean isExistInHasDrawColorDots(GraphDot graphDot,List<GraphDot> hasDrawColorDots)
    {
        for (GraphDot dot:hasDrawColorDots)
        {
            if(dot.getDotNo() == graphDot.getDotNo())
            {
                return true;
            }
        }
        return false;
    }

    public void startDrawColor()
    {
        this.drawColor(this.unColorGraphDotList);
        int colorCnt = this.curColorNo +1;
        System.out.println(colorCnt);
        for (GraphDot dot:this.graphDotList)
        {
            System.out.println(dot.getColorNo() + " ");
        }
    }

    public int drawColor(List<GraphDot> unColorGraphDotList)
    {
          Collections.sort(unColorGraphDotList, new Comparator<GraphDot>() {
              @Override
              public int compare(GraphDot o1, GraphDot o2) {
                  int curUnColorCnt = o1.getUnColorCount();
                  int objUnColorCnt = o2.getUnColorCount();
                  if(curUnColorCnt > objUnColorCnt)
                  {
                      return  -1;
                  }
                  else if(curUnColorCnt< objUnColorCnt)
                  {
                      return  1;
                  }
                  else
                  {
                      return  0;
                  }
              }
          });
          List<GraphDot> hasDrawColorDots = new ArrayList<>();
          for (GraphDot graphDot:unColorGraphDotList)
          {
               if(!this.isNearColorDots(graphDot,hasDrawColorDots))
               {
                   graphDot.setColorNo(this.curColorNo);
                   hasDrawColorDots.add(graphDot);
               }
          }

          System.out.println(" curColorNo:" + this.curColorNo);

          this.curColorNo++;

        List<GraphDot> unHasDrawColorDots = new ArrayList<>();
        for (GraphDot graphDot:unColorGraphDotList)
        {
              if(!this.isExistInHasDrawColorDots(graphDot,hasDrawColorDots))
              {
                  unHasDrawColorDots.add(graphDot);
              }
        }

        if(unHasDrawColorDots.size() > 0)
        {
            this.drawColor(unHasDrawColorDots);
        }
        return 0;
    }

}
