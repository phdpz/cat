package com.cat.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class schoolChartUtil {
	private static PieDataset getPieDataset(Map<String, Object> map){
		//创建一个饼图的数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		@SuppressWarnings("unchecked")
		List<String> school=(List<String>)map.get("school");
		if(map.containsKey("num")){
			@SuppressWarnings("unchecked")
			List<Integer> list=(List<Integer>)map.get("num");
			for(int i=0;i<list.size();i++){
				dataset.setValue(school.get(i), list.get(i));
			}
		}
		if(map.containsKey("money")){
			@SuppressWarnings("unchecked")
			List<Double> list=(List<Double>)map.get("money");
			for(int i=0;i<list.size();i++){
				dataset.setValue(school.get(i), list.get(i));
			}
		}
		return dataset;
	}
	
	public static JFreeChart getJFreeChart(Map<String, Object> map){
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		//设置标题字体
		mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
		//设置轴向字体
		mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		//设置图例字体
		mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		//应用主题样式
		ChartFactory.setChartTheme(mChartTheme);
		
		//获取数据集
		PieDataset dataset=getPieDataset(map);
		//生成JFreeChart对象
		JFreeChart chart=null;
		if(map.containsKey("num"))
		   chart=ChartFactory.createPieChart("水果销量学校分析", dataset,true,true,false);
		if(map.containsKey("money"))
		   chart=ChartFactory.createPieChart("水果营业额学校分析", dataset,true,true,false);
		setPiePlotNum(chart);
		//设置背景图片
		//setBackgroundImage(chart,"E:\\图片\\597594320.jpeg");
		//设置背景色
		setBackgroundColor(chart);
		return chart;
	}
	
   public static void setPiePoltFont(JFreeChart chart){
	   //图表（饼图）
	   PiePlot piePlot=(PiePlot)chart.getPlot();
	   piePlot.setLabelFont(new Font("宋体",Font.PLAIN,14));
      //标题
	   TextTitle textTitle=chart.getTitle();
	   textTitle.setFont(new Font("宋体",Font.BOLD,20));
	   //图例
	   LegendTitle legendTitle=chart.getLegend();
	   legendTitle.setItemFont(new Font("宋体",Font.PLAIN,12));
   }
   
   public static void setPiePlotNum(JFreeChart chart){
	   //图表（饼图）
	   PiePlot piePlot=(PiePlot)chart.getPlot();
	   //设置饼图标签显示
	   piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));
   }
   
   public static void setBackgroundImage(JFreeChart chart,String imgPath){
	   Image image=null;
	   try{
		   //读取图片
		   image=ImageIO.read(new FileInputStream(imgPath));
	   }catch(IOException e){
		   e.printStackTrace();
	   }
	   PiePlot piePlot=(PiePlot)chart.getPlot();
	   //设置饼背景图
	   piePlot.setBackgroundImage(image);
	   //设置背景图的透明度，值（0-1）越小，图片越模糊
	   piePlot.setBackgroundImageAlpha(0.3f);
   }
   
   public static void setBackgroundColor(JFreeChart chart){
	   PiePlot piePlot=(PiePlot)chart.getPlot();
	   //设置饼图背景色
	   piePlot.setBackgroundPaint(Color.white);
   }
   
}
