package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rong on 4/15/2017.
 */
public class LineChart_AWT extends ApplicationFrame {

    public LineChart_AWT(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Rank", "Frequency",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //sort HashMap based on value
        ValueComparator bvc = new ValueComparator(Main.myResult);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        //System.out.println("unsorted map: " + myWordsCount);
        sorted_map.putAll(Main.myResult);
        //System.out.println("results: " + sorted_map);
        List<Integer> allFrequency = new ArrayList<Integer>();
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            int value=entry.getValue();
            allFrequency.add(value);
        }

        int[] ret = new int[allFrequency.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = allFrequency.get(i).intValue();
        }

        int[] res1=removeDuplicates(ret);




        //print the first 10 elements of sorted map
         /*int count = 0;
        System.out.println("The top 20 words(word : frequency) are listed below:");
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            String key = entry.getKey();
            int valuex = entry.getValue();
            System.out.printf("%s : %d\n", key, valuex);
            count++;
            if (count > 10 - 1)
                break;
        }*/
        int freCount=1;
        for (int i=0; i<res1.length; i++) {
            String ss= freCount+"";
        dataset.addValue(res1[i], "rank", ss);
        freCount++; }
        return dataset;
    }

    public static int[] removeDuplicates(int[] s){
        int result[] = new int[s.length], j=0;
        for (int i : s) {
            if(!isExists(result, i))
                result[j++] = i;
        }
        return result;
    }
    private static boolean isExists(int[] array, int value){
        for (int i : array) {
            if(i==value)
                return true;
        }
        return false;
    }
}
