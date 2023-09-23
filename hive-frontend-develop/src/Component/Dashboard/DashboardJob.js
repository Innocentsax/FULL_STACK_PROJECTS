import './dashboard.css'
import View from "./DashboardView";
import Ongoing from './Ongoing';


import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import CompletedTab from "./CompletedTab";
import PendingApprovalTab from "./PendingApprovalTab";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function BasicTabs() {
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className='dashboard-job'>
         <Box>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                <Tab label="New Jobs" {...a11yProps(0)} />
                <Tab label="Ongoing Jobs" {...a11yProps(1)} />
                <Tab label="Pending Approval" {...a11yProps(2)} />
                <Tab label="Completed" {...a11yProps(3)} />
                </Tabs>
            </Box>
            <TabPanel value={value} index={0}>
                <View />
            </TabPanel>
            <TabPanel value={value} index={1}>
                <Ongoing />
            </TabPanel>
            <TabPanel value={value} index={2}>
                <PendingApprovalTab />
            </TabPanel>
            <TabPanel value={value} index={3}>
                <CompletedTab />
            </TabPanel>
      
    </Box>
    </div>
   
  );
}