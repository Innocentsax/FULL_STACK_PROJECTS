import React, { useEffect } from "react";
import { ResponsiveLine } from "@nivo/line";
import { useTheme } from "@mui/material";
import { monthlyData as data } from  "../utils/data"


// const OverviewChart = ({ isDashboard = false, view }) => {
const OverviewChart = () => {
  const theme = useTheme();
  // const data = monthlyData;

  // useEffect(() => {
  //   first
  // }, [third])
  

  // const [totalSalesLine, totalUnitsLine] = useMemo(() => {
  //   if (!data) return [];

  //   // const { monthlyData } = data;
  //   const totalSalesLine = {
  //     id: "totalSales",
  //     color: theme.palette.secondary.main,
  //     data: [],
  //   };
  //   const totalUnitsLine = {
  //     id: "totalUnits",
  //     color: theme.palette.secondary[600],
  //     data: [],
  //   };

  //   Object.values(monthlyData).reduce(
  //     (acc, { month, totalSales, totalUnits }) => {
  //       const curSales = acc.sales + totalSales;
  //       const curUnits = acc.units + totalUnits;

  //       totalSalesLine.data = [
  //         ...totalSalesLine.data,
  //         { x: month, y: curSales },
  //       ];
  //       totalUnitsLine.data = [
  //         ...totalUnitsLine.data,
  //         { x: month, y: curUnits },
  //       ];

  //       return { sales: curSales, units: curUnits };
  //     },
  //     { sales: 0, units: 0 }
  //   );

  //   return [[totalSalesLine], [totalUnitsLine]];
  // }, [data]); // eslint-disable-line react-hooks/exhaustive-deps
  console.log(data);

  const totalProfitLine = [
    {
      id: "totalProfit",
      color: "#7a51e3",
      data: data,
    }
  ];

  console.log(totalProfitLine);



  return (

    <ResponsiveLine
    // come back here
        data={totalProfitLine}
        margin={{ top: 20, right: 50, bottom: 50, left: 70 }}
        xScale={{ type: 'point' }}
        yScale={{
            type: 'linear',
            min: 'auto',
            max: 'auto',
            stacked: true,
            // stacked: false,
            reverse: false
        }}
        yFormat=" >-.2f"
        axisTop={null}
        axisRight={null}
        axisBottom={{
          // format come back here
            orient: 'bottom',
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            // come back here
            legend: 'Month',
            legendOffset: 36,
            legendPosition: 'middle'
        }}
        axisLeft={{
            orient: 'left',
            // tickValues: 5,
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            // come back here
            legend: 'Total Revenue for Year',
            legendOffset: -60,
            legendPosition: 'middle'
        }}
        pointSize={10}
        pointColor={{ theme: 'background' }}
        pointBorderWidth={2}
        pointBorderColor={{ from: 'serieColor' }}
        pointLabelYOffset={-12}
        enableArea={true}
        useMesh={true}
        // come back here 
        legends={[
            {
                anchor: 'bottom-right',
                direction: 'column',
                justify: false,
                translateX: 30,
                translateY: -40,
                itemsSpacing: 0,
                itemDirection: 'left-to-right',
                itemWidth: 80,
                itemHeight: 20,
                itemOpacity: 0.75,
                symbolSize: 12,
                symbolShape: 'circle',
                symbolBorderColor: 'rgba(0, 0, 0, .5)',
                effects: [
                    {
                        on: 'hover',
                        style: {
                            itemBackground: 'rgba(0, 0, 0, .03)',
                            itemOpacity: 1
                        }
                    }
                ]
            }
        ]}
    />
  
  )
}

export default OverviewChart