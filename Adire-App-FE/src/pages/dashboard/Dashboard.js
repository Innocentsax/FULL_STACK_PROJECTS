import React from 'react'
import { useEffect } from 'react';
import { StatsContainer, Loading, ChartsContainer, DashboardHeader, OverviewChart } from '../../components';
import { useDispatch, useSelector } from 'react-redux';
import { Box, Typography } from '@mui/material';
import LineChart from '../../components/LineChart';
import { fetchDashboardInfo } from '../../features/dashboard/dashboardSlice';


const Dashboard = () => {
  const dispatch = useDispatch();
  const { user } = useSelector((store) => store.user)
  
  const { isLoading, monthlyChart } = useSelector(
    (store) => store.dashboard
  );

  
  useEffect(() => {
    dispatch(fetchDashboardInfo());
  }, []);


  return (
    <>
      <DashboardHeader name={user.firstName} title={'dashboard'} />
      <StatsContainer />
      <Box backgroundColor="#ffffff" mt="30px" 
        sx={{
          boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
        }} 
      >
        <div className='chart-header'>
          <h4>showing monthly chart</h4>
          <p className='sub-heading'>Insights showing current month</p>
        </div>
        <Box height={"65vh"} paddingLeft="20px">
          <LineChart />
        </Box>
      </Box>
      {/* {monthlyApplications.length > 0 && <ChartsContainer />} */}
    </>
  );


  // return (
  //   <div>
  //     <Modal buttonText="Open Modal">
  //       <h2>Modal Title</h2>
  //       <p>Modal Content Goes Here</p>
  //     </Modal>
  //   </div>
  // )
}

export default Dashboard