package com.vendor.graph;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphColorTest {

    private static GraphData data = new GraphData();
    private static int dotCount  =0;
    private static int edgetCount = 0;
    private static List<InputEdge> edgeLists = new ArrayList<>();

    public static void loadData(String filePath)
    {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;

            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                String[] strs = tempString.split(" ");
                if(line == 0)
                {
                   dotCount = Integer.parseInt(strs[0]);
                   edgetCount = Integer.parseInt(strs[1]);
                }
                else
                {
                    InputEdge inputEdge = new InputEdge();
                    inputEdge.setFirstDotNo(Integer.parseInt(strs[0]));
                    inputEdge.setSecondDotNo(Integer.parseInt(strs[1]));
                    edgeLists.add(inputEdge);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void main(String[] args) {

        List<InputEdge> inputEdges = new ArrayList<>();

        GraphColorTest.loadData("E:\\workData\\data\\personal\\li\\gc_100_5");
        data.Init(dotCount,edgetCount,edgeLists);
        data.startDrawColor();


    }
}
