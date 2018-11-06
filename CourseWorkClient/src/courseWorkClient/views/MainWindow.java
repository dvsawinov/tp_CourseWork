package courseWorkClient.views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.Time;

import courseWorkClient.common.DataBaseInterface;
import courseWorkClient.common.WrongTimeValues;
import javax.swing.JCheckBox;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnDep1;
	private JButton btnDep2;
	private JButton btnDep3;
	private JButton btnInterpol;
	private JButton btnClear;
	private JComboBox<String> cmbbxChooseLane;
	private JComboBox<Date> cmbbxChooseDate;
	private JComboBox<Integer> cmbbxChooseTimeFirst;
	private JComboBox<Integer> cmbbxChooseTimeSecond;
	private JPanel pDeps;
	private JPanel pNormalDep;
	private JCheckBox chckbxChooseTime;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initComponents();
		createEvents();
	}
	
	//*Initialize components of a frame**
	void initComponents()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 902, 817);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		pDeps = new JPanel();
		pDeps.setBackground(Color.GRAY);
		pNormalDep = new JPanel();
		pNormalDep.setBackground(Color.GRAY);
		
		
		btnDep1 = new JButton("Dep1");
		btnDep2 = new JButton("Dep2");
		btnDep3 = new JButton("Dep3");
		btnInterpol = new JButton("Interpol");
		btnClear = new JButton("Clear");
		
		cmbbxChooseLane = new JComboBox<String>();
		cmbbxChooseDate = new JComboBox<Date>();
		cmbbxChooseTimeFirst = new JComboBox<Integer>();
		cmbbxChooseTimeSecond = new JComboBox<Integer>();
		fillComboBoxes();
		
		cmbbxChooseTimeFirst.setEnabled(false);
		cmbbxChooseTimeSecond.setEnabled(false);
		chckbxChooseTime = new JCheckBox("Choose time");

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(pNormalDep, GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(cmbbxChooseLane, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(cmbbxChooseDate, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbbxChooseTimeFirst, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbbxChooseTimeSecond, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(chckbxChooseTime)
									.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
									.addComponent(btnDep1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDep2)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDep3)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnInterpol)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClear))
								.addComponent(pDeps, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
							.addGap(1)))
					.addGap(37))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbbxChooseLane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbbxChooseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDep1)
						.addComponent(btnDep2)
						.addComponent(btnDep3)
						.addComponent(btnInterpol)
						.addComponent(btnClear)
						.addComponent(cmbbxChooseTimeFirst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbbxChooseTimeSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxChooseTime))
					.addGap(18)
					.addComponent(pDeps, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(pNormalDep, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
					.addGap(47))
		);
		pDeps.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(gl_contentPane);
		
	}
	
	//*Initialize events for a frame**
	void createEvents()
	{	
		//*Event handler for pressing button Dep1**
		btnDep1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBaseInterface dbInterface = new DataBaseInterface();
				String sql = buildSqlQuery("intensity","occupancy");
				XYDataset dataset = getDataSet(sql, dbInterface);
				if (dataset!=null)
				{
					JFreeChart chart = plotPoints(dataset, "intensity", "occupancy");
					ChartPanel chartPanel = new ChartPanel(chart);
					chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				    chartPanel.setBackground(Color.white);
				    pDeps.removeAll();
				    pDeps.add(chartPanel, BorderLayout.CENTER);
					pDeps.validate();
				}
				dbInterface.disconnect();
			}
		});
		
		btnDep2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnDep3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		chckbxChooseTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbbxChooseTimeFirst.isEnabled() == true)
				{
					cmbbxChooseTimeFirst.setEnabled(false);
					cmbbxChooseTimeSecond.setEnabled(false);
				}
				else
				{
					cmbbxChooseTimeFirst.setEnabled(true);
					cmbbxChooseTimeSecond.setEnabled(true);
				}
			}
		});
		
	}
	
	//*Method that fills all combo boxes**
	private void fillComboBoxes()
	{
		DataBaseInterface dbInterface = new DataBaseInterface();
		String sql_lanes = "SELECT DISTINCT name FROM coursework.lane";
		String sql_dates = "SELECT DISTINCT (CONVERT (lane.date, DATE)) FROM coursework.lane ORDER BY lane.date";
		try
		{
			PreparedStatement pstmt = dbInterface.connect().prepareStatement(sql_lanes);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){cmbbxChooseLane.addItem(rs.getString(1));}
			pstmt.close();
			rs.close();
			
			pstmt = dbInterface.connect().prepareStatement(sql_dates);
			rs = pstmt.executeQuery();
			while(rs.next()){cmbbxChooseDate.addItem(rs.getDate(1));}
			pstmt.close();
			rs.close();
			
			for (int i = 1; i<=24; i++)
			{
				cmbbxChooseTimeFirst.addItem(i);
				cmbbxChooseTimeSecond.addItem(i);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		dbInterface.disconnect();
	}
	
	//*Method that builds sql query**
	private String buildSqlQuery(String x, String y)
	{
		String sql_query = "SELECT " + x + " , " + y + " FROM coursework.lane WHERE name = ?";
		if (cmbbxChooseDate.getSelectedItem().toString() == "All")
			return sql_query;
		else
		{
			sql_query+= " AND CONVERT(lane.date, DATE) = ?";
			if (chckbxChooseTime.isSelected())
			{
				sql_query+= " AND CONVERT(lane.date, TIME) BETWEEN ? AND ? ";
			}
			return sql_query;
		}
	}
	
	//*Method that plots a graph**
	private JFreeChart plotPoints(XYDataset dataset, String xAxis, String yAxis)
	{
		JFreeChart chart = ChartFactory.createScatterPlot(
                "", 
                xAxis, 
                yAxis, 
                dataset
        );
		XYPlot plot = chart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, false);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;
	}
	
	//*Method that executes sql query and creates dataset from resultset**
	private XYDataset getDataSet(String sql, DataBaseInterface dbInterface)
	{
		XYSeriesCollection dataset = null;
		try
		{
			if ((Integer)cmbbxChooseTimeFirst.getSelectedItem() > (Integer)cmbbxChooseTimeSecond.getSelectedItem())
				throw new WrongTimeValues();
			PreparedStatement pstmt = dbInterface.connect().prepareStatement(sql);
			pstmt.setString(1, cmbbxChooseLane.getSelectedItem().toString());
			pstmt.setString(2, cmbbxChooseDate.getSelectedItem().toString());
			if (chckbxChooseTime.isSelected()==true)
			{
				long mlsc_t = (Integer)cmbbxChooseTimeFirst.getSelectedItem() * 3600000; 
				Time t1 = new Time(mlsc_t);
				pstmt.setString(3, t1.toString());
				mlsc_t = (Integer)cmbbxChooseTimeSecond.getSelectedItem() * 3600000; 
				t1.setTime(mlsc_t);						
				pstmt.setString(4, t1.toString());
			}

			ResultSet rs = pstmt.executeQuery();
			XYSeries series = new XYSeries("Your orders");
			while (rs.next())
			{
				series.add(rs.getFloat(1), rs.getFloat(2));
			}
	        dataset = new XYSeriesCollection();
	        dataset.addSeries(series);
			pstmt.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		catch(WrongTimeValues ex)
		{
			JOptionPane.showMessageDialog(null, "You have selected wrong time interval", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		return dataset;
	}
	
}
