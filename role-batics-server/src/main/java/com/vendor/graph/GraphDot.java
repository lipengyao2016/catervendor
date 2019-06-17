package com.vendor.graph;

import java.util.List;

public class GraphDot{
    public Integer getDotNo() {
        return dotNo;
    }

    public void setDotNo(Integer dotNo) {
        this.dotNo = dotNo;
    }

    public Integer getColorNo() {
        return colorNo;
    }

    public void setColorNo(Integer colorNo) {
        this.colorNo = colorNo;
    }

    private Integer  dotNo;
    private Integer  colorNo;

    public List<GraphDot> getNearDots() {
        return nearDots;
    }

    public void setNearDots(List<GraphDot> nearDots) {
        this.nearDots = nearDots;
    }

    private List<GraphDot>  nearDots;

    public Integer getUnColorCount()
    {
        int unColorCnt = 0;
        for(GraphDot dot: nearDots)
        {
            if(dot.getColorNo() == null)
            {
                unColorCnt++;
            }
        }
        return  unColorCnt;
    }




    public boolean isExistNear(int dotNo)
    {
        for (GraphDot dot:this.getNearDots())
        {
            if(dot.getDotNo() == dotNo)
            {
                return true;
            }
        }
        return false;
    }
}
