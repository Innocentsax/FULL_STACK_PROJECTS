import React from "react";
import ReactApexChart from "react-apexcharts";

const Graph = () => {
  const data = [
    { x: "Jan", balance: 150000, credit: 75000, debit: 50000 },
    { x: "Feb", balance: 110000, credit: 100000, debit: 75000 },
    { x: "Mar", balance: 210000, credit: 145000, debit: 120000 },
    { x: "Apr", balance: 100000, credit: 90000, debit: 75000 },
    { x: "May", balance: 180000, credit: 170000, debit: 160000 },
    { x: "Jun", balance: 140000, credit: 120000, debit: 110000 },
    { x: "Jul", balance: 180000, credit: 170000, debit: 160000 },
  ];

  const series = [
    {
      name: "balance",
      data: data.map((item) => item.balance),
    },
    {
      name: "credit",
      data: data.map((item) => item.credit),
    },
    {
      name: "debit",
      data: data.map((item) => item.debit),
    },
  ];

  const options = {
    chart: {
      height: 350,
      type: "area",
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "smooth",
    },
    xaxis: {
      type: "category",
      categories: [
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec",
      ],
    },
    yaxis: {
      min: 0,
      max: 300000,
      tickAmount: 6,
      labels: {
        formatter: function (value) {
          return value.toFixed(0); // Display y-axis labels as integers
        },
      },
    },
    tooltip: {
      x: {
        format: "MMM",
      },
    },
  };

  return (
    <>
      <ReactApexChart
        options={options}
        series={series}
        type="area"
        height={350}
      />
    </>
  );
};

export default Graph;
