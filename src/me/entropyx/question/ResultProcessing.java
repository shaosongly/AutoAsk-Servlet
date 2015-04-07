package me.entropyx.question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by shaosong on 14/12/23.
 */
public class ResultProcessing {
    private String[] numCol= {"价格","最高车速","官方加速","车身长度","车身高度","车身宽度"};
    private Map<String, String> unitMap = null;

    public ResultProcessing(String path) {
        unitMap = new HashMap<String, String>();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            if (reader != null) {
                String tempString = null;
                while ((tempString = reader.readLine()) != null) {
                    String[] temp=tempString.split(":");
                    unitMap.put(temp[0], temp[1]);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getAnswer(Map<String, ArrayList<String>> querys, Map<String, ArrayList<String>> results, int num) {
        String ans = "";
        System.out.println(results);

        if(querys.size() == 0) {//没有要查询的内容，是判断类型
            boolean flag = true;
            for(String key:results.keySet()) {
                if(results.get(key).size()==0) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                ans = "是的，是这样的";
            } else {
                ans = "不是这样的";
            }

        } else {//有查询内容
            for(String key:results.keySet()) {
                ArrayList<String> data = results.get(key);
                String name = key.substring(0, key.length() - 1);
                String unit = unitMap.get(name);
                int type = getQueryType(name);
                if(type == 1 && data.size()>9) {//查询的是数字，返回结果较多，需要转换为范围
                    ans+=name+"是"+getRange(data)+(unit == null?"":unit)+"<br>";
                } else {
                    ans+=name+"是"+removeDuplicates(data).toString()+(unit == null?"":unit)+"<br>";
                }
            }

        }
        return ans;
    }

    private int getQueryType(String name) {
        for(int i = 0; i<numCol.length;i++) {
            if(name.compareTo(numCol[i])==0)
                return 1;
        }
        return 0;
    }
    private String getRange(ArrayList<String> data) {
        double min = Double.parseDouble(data.get(0)), max = min;
        for(int i=1;i<data.size();i++) {
            double val = Double.parseDouble(data.get(i));
            if(val>max) {
                max = val;
            } else if(val<min) {
                min = val;
            }
        }
        return min+"-"+max;
    }
    private ArrayList<String> removeDuplicates(ArrayList<String> data) {
        ArrayList<String> newData = new ArrayList<String>();
        for(String d:data) {
            if(!newData.contains(d)) {
                newData.add(d);
            }
        }
        return newData;
    }

}
