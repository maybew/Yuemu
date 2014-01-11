package yuemu.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

@SuppressWarnings("unused")
public class LineChart {

	EncodeProcesser processer = new EncodeProcesser();

	private final int WIDTH = 700;
	private final int HEIGHT = 500;

	public JFreeChart getChart(String[] rowKeys, String[] columnKeys,
			double[][] data, String title, String domain, String number) {
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);
		JFreeChart chart = ChartFactory.createLineChart(title, domain, number,
				dataset, PlotOrientation.VERTICAL, true, true, false);

		// 获得柱状图 图表绘图对象
		// XYPlot plot = (XYPlot)chart.getPlot();
		// // 处理x坐标轴的样式
		// setXDomainAxis(plot.getDomainAxis());
		// // 处理Y坐标轴的样式
		// setYValueAxis(plot.getRangeAxis());
		// // 处理每个柱子的样式
		// XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
		// .getRenderer();
		// setRenderer(renderer);
		// 处理中文字符
		processer.encodingForCP(chart);

		return chart;
	}

	private void setRenderer(XYLineAndShapeRenderer renderer) {

	}

	private void setXDomainAxis(ValueAxis domain) {

	}

	private void setYValueAxis(ValueAxis number) {

	}
}
