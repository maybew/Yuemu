package yuemu.jfreechart;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;

public class EncodeProcesser {

	private void encodingForTitle(JFreeChart chart) {
		// Total Title
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 15));
		// legend
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("黑体", Font.BOLD, 15));
	}

	public void encodingForCP(JFreeChart chart) {
		encodingForTitle(chart);
		// plot
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();// domain - x
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();// range - y
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
	}

	public void encodingForPP(JFreeChart chart) {
		encodingForTitle(chart);
		//plot
		PiePlot plot = (PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
	}
	
}