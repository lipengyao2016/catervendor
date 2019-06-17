package com.vendor.testserver;

import com.vendor.utils.GsonUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomTest {

    public static void main(String[] args) {

        int base = (int)Math.pow(10,6);
        System.out.println("base:" + base);

        Map<Integer,Integer> dataSet = new HashMap<>();

        for (int i = 0 ;i<100;i++)
        {
            while (true)
            {
                int nRet = RandomUtils.generateNumber(6);
                if(dataSet.containsKey(nRet))
                {
                    dataSet.replace(nRet,dataSet.get(nRet) + 1);
                    continue;
                }
                else
                {
                    dataSet.put(nRet,1);
                    break;
                }
                //System.out.println("nRet:" + nRet);
            }
         //   System.out.println((int)((Math.random()*9+1)*100000));
        }

        for (Map.Entry<Integer,Integer>  entry:dataSet.entrySet()) {
            //if(entry.getValue() > 1)
            {
                System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
            }

        }

        System.out.println("end");
    }
}
