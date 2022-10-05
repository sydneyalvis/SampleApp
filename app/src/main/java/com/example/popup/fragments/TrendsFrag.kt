package com.example.popup.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.popup.R
import com.example.popup.databinding.FragmentTrendsBinding
import com.example.popup.model.items
import com.example.popup.prefs
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
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

        var xAxis: XAxis
        {   // // X-Axis Style // //
            xAxis = lineChart.xAxis

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
            xAxis.labelRotationAngle = 45f
        }

        var yAxis: YAxis
        {   // // Y-Axis Style // //
            yAxis = lineChart.getAxisLeft()

            // disable dual axis (only use LEFT axis)
            lineChart.getAxisRight().setEnabled(false)

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = 200f
            yAxis.axisMinimum = -50f
        }

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

        var set1: LineDataSet = LineDataSet(values, "Trends")
        set1.notifyDataSetChanged()
        lineChart.data.notifyDataChanged()
        lineChart.notifyDataSetChanged()
        set1.setDrawIcons(false);

        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);
        // text size of values
        set1.setValueTextSize(9f);

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1) // add the data sets

        val data = LineData(dataSets)

        val xAxis = lineChart.xAxis
        xAxis.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
               return "Today"
            }

            val decimalDigits: Int
                get() = 0
        })

        lineChart.setData(data)




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