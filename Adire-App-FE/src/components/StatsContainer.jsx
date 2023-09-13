import StatItem from './StatItem';
import Wrapper from '../assets/wrappers/StatsContainer';
import { useSelector } from 'react-redux';
import { ReceiptIcon, ProfitIcon, PeopleIcon } from '../utils/SVGIcons';

const StatsContainer = () => {
  const { numOfOrders, numOfCustomers, totalRevenue } = useSelector((store) => store.dashboard);



  const defaultStats = [
    {
        item: 'customers',
        title: 'Customers',
        //   count: stats.pending || 0,
        count:  numOfCustomers,
        icon: <PeopleIcon />,
        color: '#5925dc',
        bcg: '#ded3f8',
    },
    {
        item: 'profit',
        title: 'Profit made so far',
        //   count: stats.interview || 0,
        count:  Math.floor(totalRevenue).toLocaleString("en-NG", {style: "currency", currency: "NGN", minimumFractionDigits: 0}),
        icon: <ProfitIcon />,
        color: '#5925dc',
        bcg: '#ded3f8',
    },
    {
        item: 'receipts',
        title: 'orders placed',
        //   count: stats.declined || 0,
        count:  numOfOrders,
        icon: <ReceiptIcon />,
        color: '#5925dc',
        bcg: '#ded3f8',
    },
  ];

  return (
    <Wrapper>
      {defaultStats.map((item, index) => {
        return <StatItem key={index} {...item} />;
      })}
    </Wrapper>
  );
};
export default StatsContainer;