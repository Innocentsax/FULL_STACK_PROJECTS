import { DashboardSVG, CustomersSVG, OrdersSVG, LogoutSVG } from './SVGIcons'

const links = [
  { id: 1, text: 'overview', path: null, icon: null },
  { id: 2, text: 'dashboard', path: '/', icon: <DashboardSVG /> },
  { id: 3, text: 'customers', path: 'customers', icon: <CustomersSVG /> },
  { id: 4, text: 'orders', path: 'orders', icon: <OrdersSVG /> },
  { id: 5, text: 'others', path: null, icon: null },
  { id: 6, text: 'account settings', path: 'account-settings', icon: null },
  { id: 7, text: 'logout', path: null, icon: <LogoutSVG /> },
  // { id: 3, text: 'create-customer', path: 'orders', icon: <CustomersSVG/> },
  // { id: 3, text: 'account settings', path: 'account-settings', icon: <OrdersSVG /> },
];

export default links;