package com.example.popup.fragments

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.popup.R
import com.example.popup.databinding.FragmentTrendsBinding
import com.example.popup.model.items
import com.example.popup.prefs
import com.example.popup.utils.XAxisValueFormatter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.skydoves.bindables.BindingFragment
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [TrendsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrendsFrag : BindingFragment<FragmentTrendsBinding>(R.layout.fragment_trends) {

    private lateinit var chart: PieChart
    private lateinit var lineChart: LineChart
    lateinit var itemsList: ArrayList<items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState) // we should call `super.onCreateView`.
        return binding {



        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart = binding.chart1
        lineChart = binding.lineChart1

        try {

            itemsList = ArrayList()

            itemsList.addAll(prefs.getArrayList())

            pieChart()
            lineChart()

        } catch (e: NullPointerException){

        }


    }

    private fun pieChart(){

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5f, 10f, 5f, 5f);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setRotationAngle(0f);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1400, Easing.EaseInOutQuad);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        chart.setEntryLabelColor(Color.WHITE)
        //chart.setEntryLabelTypeface(tfRegular)
        chart.setEntryLabelTextSize(12f)

        setPieData()
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
//        for (i in 0 until count) {
//            entries.add(
//                PieEntry(
//                    (Math.random() * range + range / 5) as Float,
//                    parties.get(i % parties.length),
//                    resources.getDrawable(R.drawable.star)
//                )
//            )
//        }

    }

    private fun setPieData(){

        val entries: ArrayList<PieEntry> = ArrayList()

            for (item in itemsList){
                entries.add(
                    PieEntry(item.price.toFloat(),item.name)
                )
            }

            val dataSet = PieDataSet(entries, "Election Results")

            dataSet.setDrawIcons(false)

            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            // add a lot of colors
            val colors: ArrayList<Int> = ArrayList()

            for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)

            for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)

            for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)

            for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)

            for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

            colors.add(ColorTemplate.getHoloBlue())

            dataSet.colors = colors


            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)
            //data.setValueTypeface(tfLight)
            chart.data = data

            chart.highlightValues(null)

            chart.invalidate()

    }

    private fun lineChart(){

        // background color
        lineChart.setBackgroundColor(Color.WHITE)

        // disable description text
        lineChart.description.isEnabled = false

        // enable touch gestures
        lineChart.setTouchEnabled(true)

        // set listeners
        // chart.setOnChartValueSelectedListener(this)
        lineChart.setDrawGridBackground(false)

        // enable scaling and dragging
        lineChart.setDragEnabled(true)
        lineChart.setScaleEnabled(true)

        // force pinch zoom along both axis
        lineChart.setPinchZoom(true)

        // // X-Axis Style // //
        var xAxis: XAxis = lineChart.xAxis

        val position = XAxisPosition.BOTTOM
        xAxis.position = position
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.labelRotationAngle = 45f
        xAxis.setValueFormatter(XAxisValueFormatter(itemsList));

        lineChart.getDescription().setEnabled(true);
        val description =  Description();
        description.setText("Purchase Trends");
        description.setTextSize(15f);

        // Y-Axis Style // //
        var yAxis: YAxis = lineChart.getAxisLeft()

        // disable dual axis (only use LEFT axis)
        lineChart.getAxisRight().setEnabled(false)

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)

        // axis range
        yAxis.axisMaximum = 200f
        yAxis.axisMinimum = -50f
          // // Create Limit Lines // //
        val llXAxis =  LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
            //llXAxis.setTypeface(tfRegular);

            val ll1 =  LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
            //ll1.setTypeface(tfRegular);

            val ll2 =  LimitLine(-30f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);
            //ll2.setTypeface(tfRegular);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);


        setLineChartData()

        // draw points over time
        lineChart.animateX(1500)

        // get the legend (only possible after setting data)
        val l = lineChart.legend

        // draw legend entries as lines
        l.form = LegendForm.LINE

    }

    private fun setLineChartData(){
        val values: ArrayList<Entry> = ArrayList()

        for (item in itemsList){
            values.add(
                Entry(item.price.toFloat(),item.price.toFloat()
                )
            )
        }

        val set1: LineDataSet
        if (lineChart.getData() != null &&
            lineChart.getData().getDataSetCount() > 0
        ) {
            set1 = lineChart.getData().getDataSetByIndex(0) as LineDataSet
            set1.values = values
            lineChart.getData().notifyDataChanged()
            lineChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "Total volume")
            set1.setDrawCircles(true)
            set1.enableDashedLine(10f, 0f, 0f)
            set1.enableDashedHighlightLine(10f, 0f, 0f)
            set1.color = resources.getColor(R.color.teal_700)
            set1.setCircleColor(resources.getColor(R.color.purple_200))
            set1.lineWidth = 2f //line size
            set1.circleRadius = 5f
            set1.setDrawCircleHole(true)
            set1.valueTextSize = 10f
            set1.setDrawFilled(true)
            set1.formLineWidth = 5f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 5f

            set1.fillColor = Color.WHITE

            set1.setDrawValues(true)
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1)
            val data = LineData(dataSets)
            lineChart.setData(data)
        }

    }

    private fun convertDate(date: Long): String {

        val sdf = SimpleDateFormat("MMM dd,yyyy ")
        val resultdate = Date(date)
        return sdf.format(resultdate)

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *

         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = TrendsFrag()


    }
}