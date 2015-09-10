package com.cat.jfreechart;

import java.awt.Font;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.cat.orderManage.model.GoodsStatus;


public class LineChartUtil {
	private static CategoryDataset getCategoryDataset(LinkedHashMap<String,Double> map,String region,String from,String to){
		String series="1";
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Set<Entry<String, Double>> set1 = map.entrySet();
		Iterator<Entry<String, Double>> s1=set1.iterator();
		
		while (s1.hasNext()) {  
		    Entry<String, Double> en = s1.next();  
		    String key = en.getKey();  
		    dataset.addValue(en.getValue(), series, key);
		}
		return dataset;
	}
	
	public static JFreeChart getJFreeChart(LinkedHashMap<String,Double> map,String region,String from,String to){
		CategoryDataset dataset = getCategoryDataset(map,region,from,to);
		JFreeChart chart;
		
		chart = ChartFactory.createLineChart("销售情况", "时间", "销售量(单位：克)", dataset,PlotOrientation.VERTICAL,true,false,false);
		updatePlot(chart,map);
		updateFont(chart);
		return chart;
	}
	
	public static void updateFont(JFreeChart chart){
		//标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体",Font.PLAIN,20));
		//说明(水果类型)
		LegendTitle legendTitle=chart.getLegend();
		legendTitle.setItemFont(new Font("宋体",Font.PLAIN,14));
		
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		CategoryAxis categroyAxis = categoryPlot.getDomainAxis();
		//X轴字体
		categroyAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,14));
		categroyAxis.setLabelFont(new Font("宋体",Font.PLAIN,14));
		ValueAxis valueAxis = categoryPlot.getRangeAxis();
		
		//Y轴字体
		valueAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,14));
		valueAxis.setLabelFont(new Font("宋体",Font.PLAIN,14));
	}
	
	public static void updatePlot(JFreeChart chart,LinkedHashMap<String,Double> map){
			/*CategoryPlot categoryPlot = chart.getCategoryPlot();
			//设置注解
			BarRenderer renderer = (BarRenderer)categoryPlot.getRenderer();
			renderer.setBaseItemLabelsVisible(true);
			renderer	
            .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
	                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));*/
			/* ChartFrame frame = new ChartFrame("柱状图", chart);
		     frame.pack();
		     frame.setVisible(true);*/
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			LineAndShapeRenderer renderer = (LineAndShapeRenderer)categoryPlot.getRenderer();
			renderer.setSeriesShapesVisible(0, true);
	}
}
