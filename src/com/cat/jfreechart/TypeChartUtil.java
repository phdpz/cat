package com.cat.jfreechart;

import java.awt.Font;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

import com.cat.orderManage.model.GoodsStatus;

public class TypeChartUtil {
	private static CategoryDataset getCategoryDataset(List<GoodsStatus> list,String type){
		String series="";
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++){
			series = list.get(i).getLocation()+list.get(i).getFruitType();
			if(type.equals("weight"))		
				dataset.addValue(list.get(i).getTotalWeight(), series, "");
			else
				dataset.addValue(list.get(i).getTotalFee(), series, "");
			
		}
		return dataset;
	}
	
	public static JFreeChart getJFreeChart(List<GoodsStatus> list,String type){
		CategoryDataset dataset = getCategoryDataset(list,type);
		JFreeChart chart;
		if(type.equals("weight"))
			chart = ChartFactory.createBarChart("销售情况", "水果", "销售量(单位：克)", dataset,PlotOrientation.VERTICAL,true,false,false);
		else
			chart = ChartFactory.createBarChart("销售情况", "水果", "营业额(单位：元)", dataset,PlotOrientation.VERTICAL,true,false,false);
		updatePlot(chart,list);
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
	
	public static void updatePlot(JFreeChart chart,List<GoodsStatus> list){
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			//设置注解
			BarRenderer renderer = (BarRenderer)categoryPlot.getRenderer();
			renderer.setBaseItemLabelsVisible(true);
			renderer	
            .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
	                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
			/* ChartFrame frame = new ChartFrame("柱状图", chart);
		     frame.pack();
		     frame.setVisible(true);*/
		
	}
}
