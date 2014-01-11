package yuemu.jfreechart;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

public class CategoryChart {
	EncodeProcesser processer = new EncodeProcesser();
	
	private final int WIDTH = 700;
	private final int HEIGHT = 500;

	// 创建一个柱状图
	public JFreeChart getChart(String[] rowKeys, String[] columnKeys,
			double[][] data, String title, String domain, String number) {
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);
		JFreeChart chart = ChartFactory.createBarChart(title, domain, number,
				dataset, PlotOrientation.VERTICAL, true, true, false);
		
		//获得柱状图 图表绘图对象
		CategoryPlot plot = chart.getCategoryPlot();
		//处理x坐标轴的样式
		setXDomainAxis(plot.getDomainAxis()) ;
		//处理Y坐标轴的样式
		setYValueAxis(plot.getRangeAxis());
		//处理每个柱子的样式
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		setBarRenderer(renderer);
		//处理中文字符
		processer.encodingForCP(chart);
		
		return chart;
	}
	
	private void setXDomainAxis(CategoryAxis domainAxis){
		
	}
	
	private void setYValueAxis(ValueAxis valueAxis) {
		
	}

	private void setBarRenderer(BarRenderer renderer) {
		//renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱子边框可见
		//renderer.setDrawBarOutline(true);

		//renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelsVisible(true);
	}

	// 根据得到的图形获得其URL地址
	public String getURL(JFreeChart chart, HttpSession session)
			throws IOException {
		return ServletUtilities.saveChartAsPNG(chart, WIDTH, HEIGHT, null, session);
	}
}
